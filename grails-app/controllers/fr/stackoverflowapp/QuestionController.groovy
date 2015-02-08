package fr.stackoverflowapp

import org.springframework.security.access.annotation.Secured

class QuestionController {
	
	//def tmpQuestion

	QuestionService questionService
	ProfileService profileService
	def springSecurityService
    
    
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
	def createQuestion(){
		
		if(!session.data){
			def user = springSecurityService.getCurrentUser()			
			def res = Profile.executeQuery("select a from Profile a " +
				"where a.user.id = ?",
				[user.id])			
			session.identifier = res[0].id
			session.firstName = res[0].firstName
			session.lastName = res[0].lastName
		}
		
		[tags: Tags.list(params)]
	}
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
	def save(){
		print 'save question'
		def question = questionService.save(params, session)
		if(question != null){
			redirect(action: "show",  params: [identifier: question.id])			
		}
		else{
			redirect(action: "createQuestion")
		}
	}
	
	@Secured('permitAll')
	def show(){
		print params.identifier
		def question = questionService.get(params.identifier)
		if(question != null){
			//call service to get edit profile
			def editProfile = questionService.getEditProfile(params.identifier)			
			//get tags list 
			def listTagsJson = question.tags.name.join(',')
			//get answers
			def answers = questionService.getAnswers(params.identifier)
			print 'fin answers '
			//get user badges
			print question.asker.id
			def askerBadgesLevel = profileService.getBadgesLevel(question.asker.id)
			print 'controller asker ' + askerBadgesLevel
			
			def editorBadgesLevel
			if(question.editor != null)
				editorBadgesLevel = profileService.getBadgesLevel(question.editor.id)
			
			print 'controller editor ' + editorBadgesLevel
			
			[myQuestion: question, editedProfile: editProfile, listTagsJson: listTagsJson, answers: answers,
			askerBadgesLevel: askerBadgesLevel, editorBadgesLevel: editorBadgesLevel]
		}
		else{
			redirect(action: "createQuestion")
		}
	}
	
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
	def edit(){
		
		if(!session.data){
			def user = springSecurityService.getCurrentUser()			
			def res = Profile.executeQuery("select a from Profile a " +
				"where a.user.id = ?",
				[user.id])			
			session.identifier = res[0].id
			session.firstName = res[0].firstName
			session.lastName = res[0].lastName
		}
		def question = Question.get(params.identifier)
		if(question != null){
			//call service to get edit profile
			[question: question, 
				editedProfile: Profile.get(session.identifier),
				tags: Tags.list(params)]
		}
		else{
			redirect(action: "createQuestion")
		}
	}
	
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
	def update(){
		
		//call update question service
		def question = questionService.edit(params, session)
				
		if (!question) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'askQuestion.label', default: 'ask Question')], params.id)
			
			redirect(action: "show",  params: [identifier: question.id])
			return
		}
						
		redirect(action: "show",  params: [identifier: question.id])
	}
	
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
	def delete(){
		
		//call delete service by id		
		questionService.delete(params.identifier)
		
		redirect(action: "questions")
	}
	
	@Secured('permitAll')
	def questions(){
		params.max = 5
		def questionsOrderByDate = questionService.getQuestionsSortedByDate(params)
		def questionsOrderByVotes= questionService.getQuestionsSortedByVotes(params)
		
		[questionsOrderByDate: questionsOrderByDate, 
		questionsOrderByVotes: questionsOrderByVotes,
		total: Question.count()?:0, tags: Tags.list(), params: params,nbQuestions: questionService.getQuestionNum()]
	}
	
	
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
	def upVote(){		
		print "controller : upvote " + session.identifier
		def question = questionService.upVote(params.identifier, session.identifier)
		render question.votesNumber
	}
	
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
	def downVote() {
		
		def question = questionService.downVote(params.identifier, session.identifier)
		render question.votesNumber
	}
	
}

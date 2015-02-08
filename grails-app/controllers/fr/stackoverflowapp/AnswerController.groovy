package fr.stackoverflowapp

import org.springframework.security.access.annotation.Secured

class AnswerController {

	AnswerService answerService
	def springSecurityService
	
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def index() {
    
    }
	
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
	def delete(){
		
		answerService.delete(params.idAnswer)
		redirect(controller: 'question', action:'show', params: [identifier: params.idQuestion])
	}
	
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
	def save(){		
		
		if(!session.data){
			def user = springSecurityService.getCurrentUser()			
			def res = Profile.executeQuery("select a from Profile a " +
				"where a.user.id = ?",
				[user.id])			
			session.identifier = res[0].id
			session.firstName = res[0].firstName
			session.lastName = res[0].lastName
		}
		answerService.save(params, session)
		
		redirect(controller: 'question', action:'show', params: [identifier: params.idQuestion])
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
		[answer: Answer.get(params.idAnswer), idQuestion: params.idQuestion]
	}
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
	def update(){		
		
		answerService.edit(params, session)
		redirect(controller: 'question', action:'show', params: [identifier: params.idQuestion])
	}
	
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
	def upVote(){		
		
		print 'up vote' + params.identifier
		def answer = answerService.upVote(params.identifier, session.identifier)
				print 'fin'
		render answer.votesNumber
	}
	
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
	def downVote() {
		
		print 'up down' + params.identifier
		def answer = answerService.downVote(params.identifier, session.identifier)
				print 'fin'
		render answer.votesNumber
	}
	
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
	def valideAnswer(){
		
		def validate = answerService.validate(params.identifier, session.identifier) 
		print 'fin'
		if(validate)
			render "<img src='${resource(dir:'images',file:'valide.png')}'  width='20' height='20' />"
		
			
	}
}

package fr.stackoverflowapp

import org.springframework.security.access.annotation.Secured

import fr.stackoverflowapp.secureapp.SecUser;

class ProfileController {
	
	def springSecurityService
	ProfileService profileService
	GetConnectUser getConnectUser
	
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
	def index (){
		
		if(!session.data){
			def user = springSecurityService.getCurrentUser()			
			def res = Profile.executeQuery("select a from Profile a " +
				"where a.user.id = ?",
				[user.id])			
			session.identifier = res[0].id
			session.firstName = res[0].firstName
			session.lastName = res[0].lastName
		}
		//get 5 firstquestions 
		def getFewQuestions = profileService.getFewQuestions(session.identifier);
		//get user badge profile level
		def profileBadgesLevel = profileService.getBadgesLevel(session.identifier)
		//get user answers 
		def getFewAnswers = profileService.getFewAnswers(session.identifier)
		//get user responces 
		def getFewResponses = profileService.getFewResponses(session.identifier)
		//get profile badges 
		def getBadges = profileService.getBadges(session.identifier)
		//get profile tags 
		def getTags = profileService.getTags(session.identifier)
		//get few reputation 
		def getFewReputation = profileService.getFewReputation(session.identifier)
				
		//get all profile questions 
		def getQuestions = profileService.getQuestions(session.identifier)
		//get all profile responses 
		def getResponses = profileService.getResponses(session.identifier)
		//get all profile answers 
		def getAnswers = profileService.getAnswers(session.identifier)
		//get profile reputation 
		def getReputation = profileService.getReputation(session.identifier)
		
		
		
		[profile: Profile.get(session.identifier), profileBadgesLevel: profileBadgesLevel,
		getFewQuestions: getFewQuestions, getFewAnswers: getFewAnswers, getFewResponses: getFewResponses,
		getFewReputation :getFewReputation,
		getBadges: getBadges, getTags: getTags, getReputation: getReputation,
		getQuestions: getQuestions, getResponses: getResponses, getAnswers: getAnswers]
	}
	
	@Secured('permitAll')
	def createProfile(){		
		
		if(profileService.checkUsernameAvailable(params.username)){
			profileService.save(params)
			
			redirect(controller: 'login', action:'auth')
		}
	}
	
	
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
	def editProfile(){
		
		[profile: Profile.get(session.identifier)]
	}
	
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
	def updateProfile(){
		
		//call save service
		profileService.edit(params.firstName, params.lastName, params.birthDate, 
		params.aboutMe, params.idProfile)
		
		redirect(action: "index")
		
	}
}

package fr.stackoverflowapp

	
class EVENT_TAG {
		final static QUESTION_IS_UPVOTED = "upvote" 
		final static QUESTION_IS_DOWNVOTED = "downvote" 
		final static ANSWER_IS_DOWNVOTED = "downvote"
		final static ANSWER_IS_UPVOTED = "upvote"
		final static ANSWER_IS_ACCEPTED = "accept"
		final static ACCEPT_ANSWER = "accept" 
		
		public EVENT_TAG(){}
		
		}

class Event {

	int		point
	String 	tag
	Date	date
	
	Question question
	static belongsTo = [reputation: Reputation]

	Event(event_type, Question in_question) {
		date	 = new Date()
		question = in_question
		switch (event_type) {
        case QUESTION_IS_UPVOTED:
            point	= event_type
            tag		= EVENT_TAG.QUESTION_IS_UPVOTED
            break
		case QUESTION_IS_DOWNVOTED:
            point	= event_type
            tag		= EVENT_TAG.QUESTION_IS_DOWNVOTED
            break
		case ANSWER_IS_DOWNVOTED:
            point	= event_type
            tag		= EVENT_TAG.ANSWER_IS_DOWNVOTED
            break
        case ANSWER_IS_UPVOTED:
            point	= event_type
            tag		= EVENT_TAG.ANSWER_IS_UPVOTED
            break
        case ANSWER_IS_ACCEPTED:
            point	= event_type
            tag		= EVENT_TAG.ANSWER_IS_ACCEPTED
            break
        case ACCEPT_ANSWER:
            point	= event_type
            tag		= EVENT_TAG.ACCEPT_ANSWER
            break
    
		}
	
	}
	
	final static QUESTION_IS_UPVOTED = 5 
	final static QUESTION_IS_DOWNVOTED = -2
	final static ANSWER_IS_DOWNVOTED = -2
	final static ANSWER_IS_UPVOTED = 10
	final static ANSWER_IS_ACCEPTED = 15
	final static ACCEPT_ANSWER = 2 

	def afterInsert() {
		//Event.withNewSession {
			reputation.value += point
		//	reputation.save()
		//}
		}
	
}


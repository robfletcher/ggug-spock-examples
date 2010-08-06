package ggug.async

import java.util.concurrent.*
import spock.lang.*
import spock.util.concurrent.*
import ggug.interactions.*

class MessageQueueSpec extends Specification {

	def queue = new MessageQueue()

	def "consumer picks up message from queue"() {
		given:
		queue.recipient = "dlq@energizedwork.com"
		queue.mailer = Mock(Mailer)
		
		and:
		def email = new BlockingVariables()
		queue.mailer.sendMessage(_, _) >> { recipient, message ->
			email.recipient = recipient
			email.message = message
	 	}
		
		when:
		queue.sendMessage("o hai")
		
		then:
		email.recipient == "dlq@energizedwork.com"
		email.message == "o hai"
	}

}
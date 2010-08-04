package ggug.async

import java.util.concurrent.*
import spock.lang.*
import spock.util.concurrent.*
import ggug.interactions.*

class AsyncInteractionSpec extends Specification {

	def queue = new MessageQueue()

	def "consumer picks up message from queue"() {
		given:
		queue.recipient = "dlq@energizedwork.com"
		queue.mailer = Mock(Mailer)
		
		and:
		def email = new BlockingVariable()
		queue.mailer.sendMessage(_, _) >> { recipient, message ->
			email.set((recipient): message)
	 	}
		
		when:
		queue.sendMessage("o hai")
		
		then:
		email.get() == ["dlq@energizedwork.com": "o hai"]
	}

}
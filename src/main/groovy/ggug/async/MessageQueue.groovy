package ggug.async

import java.util.concurrent.*
import ggug.interactions.*

class MessageQueue  {

	String recipient
	Mailer mailer

	private final ExecutorService executor = Executors.newSingleThreadExecutor()
	
	void sendMessage(String message) {
		executor.execute {
			TimeUnit.MILLISECONDS.sleep(250)
			mailer.sendMessage(recipient, message)
		}
	}

}
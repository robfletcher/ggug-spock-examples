package ggug.interactions

import static java.util.Collections.*

class Register {
	
	def directory
	def mailer
	private randomizer = new Random()

	void addPerson(String firstName, String lastName, String email) {
		directory.insertRecord("$firstName $lastName")
		mailer.sendMessage(email, "Welcome $firstName!")
	}
	
	List<String> findPeople(String query) {
		try {
			directory.findRecords(query)
		} catch (IllegalArgumentException e) {
			EMPTY_LIST
		}
	}
	
	String randomUser() {
		directory.findRecord(randomizer.nextInt())
	}

}
package ggug.interactions

class Register {
	
	def directory
	def mailer

	void addPerson(String firstName, String lastName, String email) {
		directory.insertRecord("$firstName $lastName")
		mailer.sendMessage(email, "Welcome $firstName!")
	}
	
	List<String> findPeople(String query) {
		directory.findRecords(query)
	}

}
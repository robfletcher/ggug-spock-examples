package ggug.interactions

import spock.lang.*

class InteractionSpec extends Specification {
	
	def register = new Register()
	
	def directory = Mock(Directory)
	def mailer = Mock(Mailer)
	
	def setup() {
		register.directory = directory
		register.mailer = mailer
	}
	
	def "can verify interactions take place"() {
		when:
		register.addPerson("Rob", "Fletcher", "rob@energizedwork.com")
		
		then:
		1 * directory.insertRecord("Rob Fletcher")
		1 * mailer.sendMessage("rob@energizedwork.com", "Welcome Rob!")
	}
	
	def "unspecified interactions are ignored"() {
		when:
		register.addPerson("Rob", "Fletcher", "rob@energizedwork.com")
		
		then:
		1 * directory.insertRecord("Rob Fletcher")
	}
	
	def "interactions can use wildcards"() {
		when:
		register.addPerson("Rob", "Fletcher", "rob@energizedwork.com")
		
		then:
		_ * directory.insertRecord(_)
		1 * _.sendMessage("rob@energizedwork.com", "Welcome Rob!")
	}
	
	def "interactions can use ranges for cardinality"() {
		when:
		register.addPerson("Rob", "Fletcher", "rob@energizedwork.com")
		
		then:
		(1.._) * directory.insertRecord("Rob Fletcher")
		(_..1) * mailer.sendMessage("rob@energizedwork.com", "Welcome Rob!")
	}
	
	def "can verify that only the specified interactions take place"() {
		when:
		register.addPerson("Rob", "Fletcher", "rob@energizedwork.com")
		
		then:
		1 * directory.insertRecord("Rob Fletcher")
		0 * directory._
	}
	
	def "can mock the return value of an interaction"() {
		when:
		def names = register.findPeople("Ro*")
		
		then:
		1 * directory.findRecords("Ro*") >> ["Rob Fletcher", "Ronald McDonald"]
		
		and:
		names == ["Rob Fletcher", "Ronald McDonald"]
	}
	
	def "can specify that no further interactions take place"() {
		when:
		def names = register.findPeople("Ro*")
		
		then:
		1 * directory.findRecords("Ro*") >> ["Rob Fletcher", "Ronald McDonald"]
		0 * _
	}
	
	def "mocked methods return default values if not specified"() {
		when:
		def names = register.findPeople("Ro*")
		
		then:
		1 * directory.findRecords("Ro*")
		
		and:
		names == null
	}
	
}
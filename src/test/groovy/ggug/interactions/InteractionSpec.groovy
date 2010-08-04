package ggug.interactions

import java.util.regex.*
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
		1 * directory.insert("Rob Fletcher")
		1 * mailer.sendMessage("rob@energizedwork.com", "Welcome Rob!")
	}
	
	def "unspecified interactions are ignored"() {
		when:
		register.addPerson("Rob", "Fletcher", "rob@energizedwork.com")
		
		then:
		1 * directory.insert("Rob Fletcher")
	}
	
	def "interactions can use wildcards and argument matching syntax"() {
		when:
		register.addPerson("Rob", "Fletcher", "rob@energizedwork.com")
		
		then:
		_ * directory.insert(_)
		1 * _.sendMessage(!null, { it =~ /^Welcome/ })
	}
	
	def "interactions can use ranges for cardinality"() {
		when:
		register.addPerson("Rob", "Fletcher", "rob@energizedwork.com")
		
		then:
		(1.._) * directory.insert("Rob Fletcher")
		(_..1) * mailer.sendMessage("rob@energizedwork.com", "Welcome Rob!")
	}
	
	def "can verify that only the specified interactions take place"() {
		when:
		register.addPerson("Rob", "Fletcher", "rob@energizedwork.com")
		
		then:
		1 * directory.insert("Rob Fletcher")
		0 * directory._
	}
	
	def "can mock the return value of an interaction"() {
		when:
		def names = register.findPeople("Ro")
		
		then:
		1 * directory.search("Ro") >> ["Rob Fletcher", "Ronald McDonald"]
		
		and:
		names == ["Rob Fletcher", "Ronald McDonald"]
	}
	
	def "can stub an interaction"() {
		given:
		directory.search("Ro") >> ["Rob Fletcher", "Ronald McDonald"]
		directory.search("Gu") >> ["Gus Power", "Gustavo Potenza"]
		
		expect:
		register.findPeople("Ro") == ["Rob Fletcher", "Ronald McDonald"]
	}
	
	def "can specify that no further interactions take place"() {
		when:
		def names = register.findPeople("Ro")
		
		then:
		1 * directory.search("Ro") >> ["Rob Fletcher", "Ronald McDonald"]
		0 * _
	}
	
	def "mocked methods return default values if not specified"() {
		when:
		def names = register.findPeople("Ro")
		
		then:
		1 * directory.search("Ro")
		
		and:
		names == null
	}
	
	def "mocked methods can have custom return values"() {
		given:
		def allUsers = ["Rob Fletcher", "Gus Power", "Glenn Saqui"]
		
		when:
		def names = register.findPeople("Ro")
		
		then:
		1 * directory.search(_) >> { query -> allUsers.findAll { it.startsWith(query) } }
	
		and:
		names == ["Rob Fletcher"]
	}
	
	def "mocked methods can throw exceptions"() {
		when:
		def names = register.findPeople("Ro")
		
		then:
		1 * directory.search("Ro") >> { throw new IllegalArgumentException() }
	
		and:
		names == []
	}
	
	def "mocked methods can be given a series of return values"() {
		setup:
		directory.lookup(_) >>> ["Rob Fletcher", "Gus Power", "Glenn Saqui"]
		
		expect:
		register.randomUser() == "Rob Fletcher"
		register.randomUser() == "Gus Power"
		register.randomUser() == "Glenn Saqui"
		register.randomUser() == "Glenn Saqui"
	}
	
}
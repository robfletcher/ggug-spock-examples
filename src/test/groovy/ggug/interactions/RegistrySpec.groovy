package ggug.interactions

import java.util.regex.*
import spock.lang.*

class RegistrySpec extends Specification {
	
	def registry = new Registry()
	
	def directory = Mock(Directory)
	def mailer = Mock(Mailer)
	
	def setup() {
		registry.directory = directory
		registry.mailer = mailer
	}
	
	def "can verify interactions take place"() {
		when:
		registry.addPerson("Rob", "Fletcher", "rob@energizedwork.com")
		
		then:
		1 * directory.insert("Rob Fletcher")
		1 * mailer.sendMessage("rob@energizedwork.com", "Welcome Rob!")
	}
	
	def "can specify interactions must occur in order"() {
		when:
		registry.addPerson("Rob", "Fletcher", "rob@energizedwork.com")
		
		then:
		1 * directory.insert("Rob Fletcher")
		
		then:
		1 * mailer.sendMessage("rob@energizedwork.com", "Welcome Rob!")
	}
	
	def "unspecified interactions are ignored"() {
		when:
		registry.addPerson("Rob", "Fletcher", "rob@energizedwork.com")
		
		then:
		1 * directory.insert("Rob Fletcher")
	}
	
	def "interactions can use wildcards and argument matching syntax"() {
		when:
		registry.addPerson("Rob", "Fletcher", "rob@energizedwork.com")
		
		then:
		_ * directory.insert(_)
		1 * _.sendMessage(!null, { it =~ /^Welcome/ })
	}
	
	def "interactions can use ranges for cardinality"() {
		when:
		registry.addPerson("Rob", "Fletcher", "rob@energizedwork.com")
		
		then:
		(1.._) * directory.insert("Rob Fletcher")
		(_..1) * mailer.sendMessage("rob@energizedwork.com", "Welcome Rob!")
	}
	
	def "can verify that only the specified interactions take place"() {
		when:
		registry.addPerson("Rob", "Fletcher", "rob@energizedwork.com")
		
		then:
		1 * directory.insert("Rob Fletcher")
		0 * directory._
	}
	
	def "can mock the return value of an interaction"() {
		when:
		def names = registry.findPeople("Ro")
		
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
		registry.findPeople("Ro") == ["Rob Fletcher", "Ronald McDonald"]
	}
	
	def "can specify that no further interactions take place"() {
		when:
		def names = registry.findPeople("Ro")
		
		then:
		1 * directory.search("Ro") >> ["Rob Fletcher", "Ronald McDonald"]
		0 * _
	}
	
	def "mocked methods return default values if not specified"() {
		when:
		def names = registry.findPeople("Ro")
		
		then:
		1 * directory.search("Ro")
		
		and:
		names == null
	}
	
	def "mocked methods can have custom return values"() {
		given:
		def allUsers = ["Rob Fletcher", "Gus Power", "Glenn Saqui"]
		directory.search(_) >> { query -> allUsers.findAll { it.startsWith(query) } }
	
		when:
		def names = registry.findPeople("Ro")
		
		then:
		names == ["Rob Fletcher"]
	}
	
	def "mocked methods can throw exceptions"() {
		when:
		def names = registry.findPeople("Ro")
		
		then:
		1 * directory.search("Ro") >> { throw new IllegalArgumentException() }
	
		and:
		names == []
	}
	
	def "mocked methods can be given a series of return values"() {
		setup:
		directory.lookup(_) >>> ["Rob Fletcher", "Gus Power", "Glenn Saqui"]
		
		expect:
		registry.randomUser() == "Rob Fletcher"
		registry.randomUser() == "Gus Power"
		registry.randomUser() == "Glenn Saqui"
		registry.randomUser() == "Glenn Saqui"
	}
	
}
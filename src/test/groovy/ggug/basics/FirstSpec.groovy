package ggug.basics

import spock.lang.*
import static ggug.basics.Product.*

class FirstSpec extends Specification {
	
	def totalizer = new Totalizer()
	
	def "the initial total is zero"() {
		expect: totalizer.total == 0
	}
	
	def "adding null causes an exception"() {
		when:
		totalizer.add(null)
		
		then:
		thrown(NullPointerException)
	}
	
	def "adding an item to an empty total increments the total"() {
		when: "a product is added"
		totalizer.add(WIDGET)
		
		then: "the new total reflects the product's price"
		totalizer.total == WIDGET.price
	}
	
	def "adding an item to an existing total increments the total"() {
		given: "some existing products"
		totalizer.add(WIDGET)
		totalizer.add(WOTSIT)
		
		when: "another product is added"
		totalizer.add(GIZMO)
		
		then: "the new total reflects the product's price"
		totalizer.total == old(totalizer.total) + GIZMO.price
	}
	
	def "adding items increments the total"() {
		when: "some products are added"
		products.each {
			totalizer.add(it)
		}
		
		then: "the total is the sum of all the prices"
		totalizer.total == products.sum { it.price }
		
		where:
		products << [[WIDGET], Product.values()]
	}
	
}
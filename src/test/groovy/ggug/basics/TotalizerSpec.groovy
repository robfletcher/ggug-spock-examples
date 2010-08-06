package ggug.basics

import spock.lang.*
import static ggug.basics.Product.*

class TotalizerSpec extends Specification {
	
	def totalizer = new Totalizer()
	
	// Example: a simple assertion
	def "the initial total is zero"() {
		expect:
		totalizer.total == 0
	}
	
	// Example: using when and then blocks
	def "adding an item to an empty total increments the total"() {
		when: "a product is added"
		totalizer.add(WIDGET)
		
		then: "the total reflects the product's price"
		totalizer.total == WIDGET.price
	}

	// Example: expecting an exception
	def "adding null causes an exception"() {
		when:
		totalizer.add(null)
		
		then:
		thrown(NullPointerException)
	}
	
	// Example: using old() to access the previous state of a value prior to the where block
	def "adding an item to an existing total increments the total"() {
		given: "some existing products"
		totalizer.add(WIDGET)
		totalizer.add(WOTSIT)
		
		when: "another product is added"
		totalizer.add(GIZMO)
		
		then: "the new total reflects the product's price"
		totalizer.total == old(totalizer.total) + GIZMO.price
	}
	
	// Example: paramaterizing the spec using the where block
	def "adding any item increments the total"() {
		when: "a products is added"
		totalizer.add(product)
		
		then: "the total reflects the product's price"
		totalizer.total == product.price
		
		where:
		product << [WIDGET, GIZMO, WOTSIT]
	}
	
	// Example: derived values in the where block
	def "adding multiple items increments the total"() {
		when: "some products are added"
		products.each {
			totalizer.add(it)
		}
		
		then: "the total is the sum of all the prices"
		totalizer.total == expectedTotal
		
		where:
		products << [[GIZMO, WOTSIT], [WIDGET, WIDGET, GIZMO], [WOTSIT, GIZMO, WIDGET]]
		expectedTotal = products.sum { it.price }
	}
	
}
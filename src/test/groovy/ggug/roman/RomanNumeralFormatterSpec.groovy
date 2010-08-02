package ggug.roman

import spock.lang.*

class RomanNumeralFormatterSpec extends Specification {
	
	def formatter = new RomanNumeralFormatter()
	
	@Unroll("cannot format #value as a roman numeral")
	def "cannot format a non-positive integer as a roman numeral"() {
		when: formatter.format(value)
		then: thrown(IllegalArgumentException)
		where: value << [-1, 0]
	}
	
	@Unroll("#value formatted as a roman numeral is '#expected'")
	def "can format a positive integer as a roman numeral"() {
		expect: formatter.format(value) == expected
		where: 
		value | expected
		1     | "I"
		2     | "II"
		4     | "IV"
		5     | "V"
		7     | "VII"
		9     | "IX"
		10    | "X"
	}
	
}
package ggug.roman

import org.junit.*
import org.junit.runner.*
import org.junit.runners.*
import org.junit.runners.Parameterized.Parameters
import static org.junit.Assert.*
import static org.hamcrest.Matchers.*

@RunWith(Parameterized)
class RomanNumeralFormatterParamTest {
	
	def formatter = new RomanNumeralFormatter()
	int number
	String numeral

	RomanNumeralFormatterParamTest(int number, String numeral) {
		this.number = number
		this.numeral = numeral
	}
	
	@Parameters static Collection<Object[]> data() {
		[
			[1, "I"] as Object[],
			[2, "II"] as Object[],
			[4, "IV"] as Object[],
			[5, "V"] as Object[],
			[7, "VII"] as Object[],
			[9, "IX"] as Object[],
			[10, "X"] as Object[]
		]
	} 
	
	@Test void positiveIntegerCanBeFormattedAsRomanNumeral() {
		assertThat "roman numeral value", formatter.format(number), equalTo(numeral)
	}
	
}
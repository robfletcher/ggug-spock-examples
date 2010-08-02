package ggug.roman

import org.junit.*
import org.junit.runner.*
import org.junit.experimental.theories.*
import static org.junit.Assert.*
import static org.junit.Assume.*
import static org.hamcrest.Matchers.*

@RunWith(Theories)
class RomanNumeralFormatterTheoryTest {
	
	@DataPoints static Map.Entry<Integer, String>[] data() {
		[
			(1): "I",
			(2): "II",
			(4): "IV",
			(5): "V",
			(7): "VII",
			(9): "IX",
			(10): "X"
		].entrySet() as Object[]
	} 
	
	def formatter = new RomanNumeralFormatter()

	@Theory void positiveIntegerCanBeFormattedAsRomanNumeral(Map.Entry<Integer, String> intToRoman) {
		assumeThat intToRoman.key, greaterThan(0)
		assertThat "roman numeral value", formatter.format(intToRoman.key), equalTo(intToRoman.value)
	}
	
	@Test(expected = IllegalArgumentException)
	void nonPositiveIntegerCannotBeFormattedAsRomanNumeral() {
		formatter.format(0)
	}
	
}
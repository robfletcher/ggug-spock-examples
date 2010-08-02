package ggug.roman

import static org.junit.Assert.*
import org.junit.runner.*
import org.junit.experimental.theories.*
import static org.hamcrest.Matchers.*

@RunWith(Theories)
class RomanNumeralFormatterTheoryTest {
	
	@DataPoints static Map.Entry<Integer, String>[] data() {
		[(1): "I",
		(2): "II",
		(4): "IV",
		(5): "V",
		(7): "VII",
		(9): "IX",
		(10): "X"].entrySet() as Object[]
	} 
	
	def formatter = new RomanNumeralFormatter()

	@Theory void positiveIntegerCanBeFormattedAsRomanNumeral(Map.Entry<Integer, String> intToRoman) {
		assertThat "roman numeral value", formatter.format(intToRoman.key), equalTo(intToRoman.value)
	}
	
}
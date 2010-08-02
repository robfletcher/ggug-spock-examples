package ggug.roman

class RomanNumeralFormatter {
	
	private static final Map<Integer, String> NUMERALS = [
		(50): "L",
		(40): "XL",
		(10): "X",
		(9): "IX",
		(5): "V",
		(4): "IV",
		(1): "I"
	].asImmutable()
	
	int parse(String romanNumerals) {
		0
	}
	
	String format(int value) {
		if (value < 1) throw new IllegalArgumentException("value $value is out of range")
		def result = new StringBuilder()
		def digits = NUMERALS.keySet() as List
		while (value > 0) {
			digits.removeAll { it > value }
			def n = digits.max()
			result << NUMERALS[n]
			value -= n
		}
		result as String
	}
	
}
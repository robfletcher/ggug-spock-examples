package ggug.seasons

import spock.lang.*

class SeasonSpec extends Specification {
	
	@Unroll("#season in the #hemisphere hemisphere is 3 months long")
	def "seasons vary in different hemispheres"() {
		expect: hemisphere.getMonthsFor(season).size() == 3
		
		where:
		[season, hemisphere] << [Season.values(), Hemisphere.values()].combinations()
	}
	
}
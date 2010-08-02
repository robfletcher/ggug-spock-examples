package ggug.seasons

import static ggug.seasons.Season.*

enum Hemisphere {
	NORTHERN([(SPRING): ["Mar", "Apr", "May"], (SUMMER): ["Jun", "Jul", "Aug"], (AUTUMN): ["Sep", "Oct", "Nov"], (WINTER): ["Dec", "Jan", "Feb"]]), 
	SOUTHERN([(SPRING): ["Sep", "Oct", "Nov"], (SUMMER): ["Dec", "Jan", "Feb"], (AUTUMN): ["Mar", "Apr", "May"], (WINTER): ["Jun", "Jul", "Aug"]])
	
	private final Map<Season, List<String>> seasons
	
	private Hemisphere(Map<Season, List<String>> seasons) {
		this.seasons = seasons.asImmutable()
	}
	
	List<String> getMonthsFor(Season season) {
		seasons[season].asImmutable()
	}
}
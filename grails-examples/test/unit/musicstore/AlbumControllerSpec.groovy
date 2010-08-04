package musicstore

import spock.lang.*
import grails.plugin.spock.*

class AlbumControllerSpec extends ControllerSpecification {
	
	@Shared albums
	
	def setupSpec() {
		albums = [
			new Album(artist: "The Hold Steady", title: "Heaven is Whenever", year: "2010"),
			new Album(artist: "Wolf Parade", title: "Expo '86", year: "2010"),
			new Album(artist: "Arcade Fire", title: "The Suburbs", year: "2010"),
			new Album(artist: "Les Savy Fav", title: "Root For Ruin", year: "2010")
		]
	} 
	
    def "list displays all albums"() {
		given: "some albums exist"
		mockDomain Album, albums
		
		when: "the list action is invoked"
		def model = controller.list()
		
		then: "all albums are displayed"
		model.albumInstanceList == albums
    }

}

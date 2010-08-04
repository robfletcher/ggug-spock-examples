package musicstore

class Album {
	
	String artist
	String title
	String year

    static constraints = {
		artist blank: false, unique: "title"
		title blank: false
		year nullable: true, matches: /\d{4}/
    }

	boolean equals(obj) {
		if (obj == null) return false
		if (obj.is(this)) return true
		if (!Album.isAssignableFrom(obj)) return false
		obj.artist == artist && obj.title == title
	}
	
	int hashCode() {
		int hash = 17
		hash += 37 * artist.hashCode()
		hash += 37 * title.hashCode()
		hash
	}

	String toString() {
		"$title by $artist"
	}
}

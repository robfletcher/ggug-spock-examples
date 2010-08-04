package ggug.interactions

interface Directory {
	
	void insert(String name)
	
	List<String> search(String query)
	
	String lookup(int id)
	
}
package ggug.interactions

interface Directory {
	void insertRecord(String name)
	List<String> findRecords(String query)
}
package ggug.basics

class Totalizer {
	
	private int total = 0
	
	void add(Product product) {
		total += product.price
	}
	
	int getTotal() {
		total
	}
	
	String toString() {
		"Totalizer[total=$total]"
	}
}

enum Product {
	
	WIDGET(10), GIZMO(5), WOTSIT(25)
	
	final int price
	
	private Product(int price) {
		this.price = price
	}
	
}
package bookstorepkg;

public class details {
	String name;
	int quant;
	String price;
	
	details(String name, int quant, String price) {
		this.name = name;
		this.quant = quant;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	
	public int getQuantity() {
		return quant;
	}
	
	public String getPrice() {
		return price;
	}
	
	public String toString() {
		return name + " " + quant + " " + price;
	}
}

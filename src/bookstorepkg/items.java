package bookstorepkg;

public class items {
	private String bid;
	private int quant;
	
	items(String bid, int quant) {
		this.bid = bid;
		this.quant = quant;
	}
	
	public String getBid() {
		return bid;
	}
	
	public int getQuant() {
		return quant;
	}
	
	public String toString() {
		return bid + " " + quant;
	}
}

package bookstorepkg;

import java.util.Hashtable;
import java.util.Vector;

public class karttable {
	public Hashtable<String, Vector<items>> kart;
	private static karttable k = new karttable();
	
	karttable() {
		kart = new Hashtable<String, Vector<items>>();
	}
	
	public static karttable getKart() {
		return k;
	}
}

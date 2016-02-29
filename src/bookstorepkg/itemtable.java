package bookstorepkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

public class itemtable {
	private Connection con;
	private PreparedStatement pstGetBooks;
	static Hashtable<String, details> books;
	
	public itemtable() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
    		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kart", "root", "dmatics");
    		
    		pstGetBooks = con.prepareStatement("Select * from books");
    		books = new Hashtable<String, details>();
    		
			ResultSet rs = pstGetBooks.executeQuery();
			
			while (rs.next()) {
				books.put(rs.getString("bid"), new details(rs.getString("name"), Integer.parseInt(rs.getString("qoh")), rs.getString("price")));
			}
    		
			/*
    		for (String k : books.keySet()) {
    			System.out.println(books.get(k));
    		}
    		
    		System.out.println(books.get("3"));
    		*/
		} catch (ClassNotFoundException e) {
			System.err.println("Driver not found");
		} catch (SQLException e) {
			System.err.println("SQL Error : " + e.getMessage());
		}
	}
}

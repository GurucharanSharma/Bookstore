package bookstorepkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class shopping
 */
@WebServlet("/shopping")
public class shopping extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String MESSAGE;
    private Connection con;
    private PreparedStatement pstGetBooks;
    private Hashtable<String, String> price;
    private Hashtable<String, String> quant;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public shopping() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		price = new Hashtable<String, String>();
		quant = new Hashtable<String, String>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
    		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kart", "root", "dmatics");
    		
    		pstGetBooks = con.prepareStatement("Select * from books");
		} catch (ClassNotFoundException e) {
			System.err.println("Driver not found");
		} catch (SQLException e) {
			System.err.println("SQL Error : " + e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		MESSAGE = request.getParameter("MESSAGE");
		String html = "";
		
		
		try {
			ResultSet rs = pstGetBooks.executeQuery();
			
			Cookie cks[] = request.getCookies();
			if (cks == null) {
				while (rs.next()) {
					price.put(rs.getString("bid"), rs.getString("price"));
					quant.put(rs.getString("bid"), rs.getString("qoh"));
				}
				rs.beforeFirst();
			} else {
				table t = table.getTableContext();
				
				for (String id : t.books.keySet()) {
					details d = t.books.get(id);
					price.put(id, d.price);
					quant.put(id, String.valueOf(d.quant));
				}
			}
			
			html = ""
				+ "<!DOCTYPE html>"
				+ "<html>"
					+ "<head>"
						+ "<title>Shopping Center</title>"
					+ "</head>"
					+ "<body>"
						+ "<div align=\"center\">"
						+ "<table bgcolor=\"teal\" width=\"80%\">"
							+ "<tr align=\"center\">"
								+ "<td colspan=\"4\">"
									+ "<div>"
										+ "<font style=\"arial\" size=10 color=\"white\"><b>My Cart</b></font>"
									+ "</div>"
								+ "</td>"
							+ "</tr>"
							+ "<tr align=center>"
								+ "<td colspan=4>"
									+ "<div>"
										+ "<a href=\"viewkart\">View Kart</a> | <a href=\"#\">Close</a>"
									+ "</div>"
									+ "<div>"
										+ "<br/><br/>"
									+ "</div>"
								+ "</td>"
							+ "</tr>";
							
							if (MESSAGE != null) {
								html += ""
									+ "<tr>"
										+ "<td align=center colspan=4>"
											+ "<div>"
												+ MESSAGE
											+ "</div>"
											+ "<div>"
												+ "<br/>"
											+ "</div>"
										+ "</td>"
									+ "</tr>";
							}
							
							
							while (true) {
								html += "<tr>";
								
								if (rs.next()) {
									html += ""
										+ "<td>"
											+ "<div align=center>"
												+ "<a href=\"ekart?id=" + rs.getString("bid") + "&quant=" + quant.get(rs.getString("bid")) + "\"><img src=\"b" + rs.getString("bid") + ".jpg\" /></a>"
											+ "</div>"
											+ "<div align=center>"
												+ "<h3><font style=\"arial\" color=\"white\">Rs " + price.get(rs.getString("bid")) + " | " + quant.get(rs.getString("bid")) +  " available</h3></font>"
											+ "</div>"
											+ "<div>"
												+ "<br/>"
											+ "</div>"
										+ "</td>";
								} else {
									html += "</tr>";
									break;
								}
								
								if (rs.next()) {
									html += ""
										+ "<td>"
											+ "<div align=center>"
												+ "<a href=\"ekart?id=" + rs.getString("bid") + "&quant=" + quant.get(rs.getString("bid")) + "\"><img src=\"b" + rs.getString("bid") + ".jpg\" /></a>"
											+ "</div>"
											+ "<div align=center>"
												+ "<h3><font style=\"arial\" color=\"white\">Rs " + price.get(rs.getString("bid")) + " | " + quant.get(rs.getString("bid")) +  " available</h3></font>"
											+ "</div>"
											+ "<div>"
												+ "<br/>"
											+ "</div>"
										+ "</td>";
								} else {
									html += "</tr>";
									break;
								}
								
								if (rs.next()) {
									html += ""
										+ "<td>"
											+ "<div align=center>"
												+ "<a href=\"ekart?id=" + rs.getString("bid") + "&quant=" + quant.get(rs.getString("bid")) + "\"><img src=\"b" + rs.getString("bid") + ".jpg\" /></a>"
											+ "</div>"
											+ "<div align=center>"
												+ "<h3><font style=\"arial\" color=\"white\">Rs " + price.get(rs.getString("bid")) + " | " + quant.get(rs.getString("bid")) +  " available</h3></font>"
											+ "</div>"
											+ "<div>"
												+ "<br/>"
											+ "</div>"
										+ "</td>";
								} else {
									html += "</tr>";
									break;
								}
								
								if (rs.next()) {
									html += ""
										+ "<td>"
											+ "<div align=center>"
												+ "<a href=\"ekart?id=" + rs.getString("bid") + "&quant=" + quant.get(rs.getString("bid")) + "\"><img src=\"b" + rs.getString("bid") + ".jpg\" /></a>"
											+ "</div>"
											+ "<div align=center>"
												+ "<h3><font style=\"arial\" color=\"white\">Rs " + price.get(rs.getString("bid")) + " | " + quant.get(rs.getString("bid")) +  " available</h3></font>"
											+ "</div>"
											+ "<div>"
												+ "<br/>"
											+ "</div>"
										+ "</td>";
								} else {
									html += "</tr>";
									break;
								}
								
								html += "</tr>";
							}
							
					html += ""
				   		+ "</table>"
						+ "</div>"
					+ "</body>"
				+ "</html>";
		} catch (SQLException e) {
			System.err.println("SQL Error : " + e);
		}
		
		pw.println(html);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

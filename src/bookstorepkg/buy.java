package bookstorepkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class buy
 */
@WebServlet("/buy")
public class buy extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String MESSAGE;
    private Connection con;
	private PreparedStatement pstGetBooks;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public buy() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
    		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kart", "root", "dmatics");
    		
    		pstGetBooks = con.prepareStatement("Select * from books where bid = ?");
		} catch (ClassNotFoundException e) {
			System.err.println("Driver not found");
		} catch (SQLException e) {
			System.err.println("SQL Error : " + e.getMessage());
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String id = request.getParameter("id");
		String quant = request.getParameter("num");
		String html = "";
		
		int visit = 0;
		Cookie cks[] = request.getCookies();
		String bCid = "", bVis = "";
		
		if(cks != null)
		{
			for(Cookie c : cks)
			{
				switch(c.getName())
				{
					case "cid" :	
						bCid=c.getValue();
						break;
					case "visit" :
						bVis=c.getValue();
						visit=Integer.parseInt(bVis);
						break;
				}
			}			
		}
		
		if (visit == 0) {
			html = ""
				+ "<!DOCTYPE html>"
				+ "<html>"
					+ "<head>"
						+ "<title>Register</title>"
					+ "</head>"
					+ "<body>"
						+ "<div align=\"center\">"
						+ "<table bgcolor=\"teal\" width=\"40%\">"
							+ "<tr align=\"center\">"
								+ "<td>"
									+ "<div>"
										+ "<h1><font style=\"arial\" color=\"white\">My Cart</font></h1>"
									+ "</div>"
									+ "<div>"
										+ "<br/><br/>"
									+ "</div>"
								+ "</td>"
							+ "</tr>"
							+ "<tr align=\"center\">"
								+ "<form action=\"cookiecls\" method=\"post\">"
									+ "<td>"
										+ "<div>"
											+ "<input type=\"text\" name=\"cid\" placeholder=\"Enter Id\" />"
											+ "<input type=\"submit\" name=\"submit\" value=\"Submit\" />"
										+ "</div>"
										+ "<div>"
											+ "<br/><br/>"
										+ "</div>"
									+ "</td>"
								+ "</form>"
							+ "</tr>"
						+ "</table>"
						+ "</div>"
					+ "</body>"
				+ "</html>";
			
			pw.println(html);
		} else {
			table t = table.getTableContext();
			details d = t.books.get(id);
			int q = d.quant;
			
			karttable k = karttable.getKart();
			
			try {
				pstGetBooks.setString(1, id);
				ResultSet rs = pstGetBooks.executeQuery();
				rs.next();
				
				if (q < Integer.parseInt(quant)) {
					MESSAGE = "Not enough books in the store";
				} else {
					q -= Integer.parseInt(quant);
					
					if (k.kart.get(bCid) == null) {
						Vector<items> vct = new Vector<items>();
						vct.add(new items(id, Integer.parseInt(quant)));
						k.kart.put(bCid, vct);
					} else {
						Vector<items> vct = k.kart.get(bCid);
						vct.add(new items(id, Integer.parseInt(quant)));
						k.kart.put(bCid, vct);
						//TODO
						//update the entry of the book if it already exists.
					}
					
					MESSAGE = "Book purchased successfully";
				}
				
				t.books.put(id, new details(rs.getString("name"), q, rs.getString("price")));
				
				System.out.println(t.books.get(id));
			} catch (SQLException e) {
				System.err.println("SQL Error : " + e);
			}
			
			Cookie c2 = new Cookie("visit", String.valueOf(visit + 1));
			c2.setMaxAge(200);
           			
			response.addCookie(c2);
			
			response.sendRedirect("shopping?MESSAGE=" + MESSAGE);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

package bookstorepkg;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ekart
 */
@WebServlet("/ekart")
public class ekart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ekart() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String id = request.getParameter("id");
		String quant = request.getParameter("quant");
		
		String html = ""
			+ "<!DOCTYPE html>"
			+ "<html>"
				+ "<head>"
					+ "<title>Shopping cart</title>"
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
							+ "<td>"
								+ "<div>"
									+ "<img src=\"b" + id + ".jpg\" / >"
								+ "</div>"
								+ "<div>"
									+ "<br/>"
								+ "</div>"
							+ "</td>"
						+ "</tr>";
		
						if (!quant.equals("0")) {
							html += ""
								+ "<tr align=\"center\">"
									+ "<form action=\"buy\" method=\"post\">"
										+ "<td>"
											+ "<div>"
												+ "<input type=\"hidden\" value=\"" + id + "\" name=\"id\" />"
												+ "<input type=\"text\" name=\"num\" placeholder=\"Enter quantity\" />"
												+ "<input type=\"submit\" name=\"submit\" value=\"Buy\" />"
											+ "</div>"
											+ "<div>"
												+ "<br/><br/>"
											+ "</div>"
										+ "</td>"
									+ "</form>"
								+ "</tr>";
						} else {
							html += ""
								+ "<tr align=\"center\">"
									+ "<td>"
										+ "<div align=center>"
											+ "<b><font size=5>Book out of Stock</font></b>"
										+ "</div>"
										+ "<div>"
											+ "<br/><br/>"
										+ "</div>"
									+ "</td>"
								+ "</tr>";
						}
						
					html += ""
						+ "</table>"
					+ "</div>"
				+ "</body>"
			+ "</html>";

		pw.println(html);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

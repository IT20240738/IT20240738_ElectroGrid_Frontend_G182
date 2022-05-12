package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchAPI
 */
@WebServlet("/SearchAPI")
public class SearchAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
    	Connection con = null; 
		try {
			PrintWriter out = response.getWriter();
//			Payment paymentObj = new Payment();  
			
			String paymentNo2 = request.getParameter("paymentNo");
			Class.forName("com.mysql.jdbc.Driver"); 
			con = DriverManager.getConnection( "jdbc:mysql://127.0.0.1:3306/electrogrid2", "root", "hotel*123");
			
			 String paymentNo = Integer.toString(getInt("paymentNo"));
			
			PreparedStatement ps = (PreparedStatement) con.prepareStatement("select *  from payment where paymentNo =' " + paymentNo2 + "'" );
			ResultSet rs = (ResultSet) ps.executeQuery();
			
			out.print("<table>");
			 while (rs.next())
			 {
				
				 // Add into the html table
				 out.println("<tr><td>Payment No</td>"
				 		+ "<td><td>customerID</td>"
				 		+ "<td><td>customerName</td>"
				 		+ "<td><td>paymentType</td>"
				 		+ "<td><td>cardNo</td>"
				 		+ "<td><td><td>paymentAmount</td></td> "
				 		+ "<td><td>paymentDate</td></td>"
				 		+ "<td><td>BillNo</td></tr>");
		 
				 out.println("<tr><td>"+rs.getString(1)+"</td>"
				 		+ "<td><td>"+rs.getString(2)+"</td>"
				 		+ "<td><td>"+rs.getString(3)+"</td>"
				 		+ "<td><td>"+rs.getString(4)+"</td>"
				 	    + "<td><td>"+rs.getString(5)+"</td>"
				 	    + "<td><td><td>"+rs.getString(6)+"</td>"
				 	    + "<td><td>"+rs.getString(7)+"</td>"
				 	    + "<td><td>"+rs.getString(8)+"</td></tr>");
				 
			 }
			 con.close();
			 out.println("Payment Details:");
			 out.println("         ");
			 out.println("          ");
			 out.println("</table>");
		}
		catch(Exception e) {
			e.printStackTrace();
			   
		}
	}


	private int getInt(String string) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

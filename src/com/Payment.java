package com;

import java.sql.*;




public class Payment {
	
	//Database Connect
	private Connection connect() 
	{ 
		Connection con = null; 
		try
		{ 
			Class.forName("com.mysql.jdbc.Driver"); 
			con = DriverManager.getConnection( "jdbc:mysql://127.0.0.1:3306/electrogrid2", "root", "hotel*123"); 
		} 
		catch (Exception e) 
		{ 
			e.printStackTrace(); 
		} 
		return con; 
	} 
	
	
	//Insert payment detail 
	public String insertPaymentDetails(String customerID, String customerName, String paymentType, String cardNo, String amount, String date, String billNo)
	{
		String output = "";
		
		try
		{
			Connection con = connect(); 
			 if (con == null) 
			 { 
				 return "Error while connecting to the database for inserting."; 
			 } 
		   
			 // create a prepared statement
			 String query = " insert into payment (`paymentNo`,`customerID`,`customerName`,`paymentType`,`cardNo`,`paymentAmount`,`paymentDate`,`billNo`)"
			 + " values (?, ?, ?, ?, ?, ?, ?, ?)";
			 
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, customerID);
			preparedStmt.setString(3, customerName);
			preparedStmt.setString(4, paymentType);
			preparedStmt.setString(5, cardNo);
			preparedStmt.setDouble(6, Double.parseDouble(amount));
			preparedStmt.setString(7, date);
			preparedStmt.setString(8, billNo);
//			preparedStmt.setInt(8, Integer.parseInt(billNo));
			
			
			
//			int customerId = Integer.parseInt(customerID);
//			int cardNO = Integer.parseInt(cardNo);
//			double payAmount = Double.parseDouble(amount);
//			int billNO = Integer.parseInt(billNo);
//			String type = String(paymentType);
			
			
//			
//			//form validation
//		    int x = cardNo.length();
//		    int i = customerName.length();
//		    int a = paymentType.length();
//		    int b = amount.length();
//		    int c = date.length();
//		    int d = billNo.length();
//		    int e = customerID.length();
//		    
//			//cannot enter minus value
//			if(customerId < 0 || cardNO < 0 || payAmount < 0 || billNO < 0) {
//				output="Please enter correct positive values.";
//			}
//			//customer ID validation
//			else if( e < 4 || e > 4  ){
//				output = "Please enter correct customer ID, Customer ID should have 4 digits. ";
//			}
//			//empty feild validation
//			else if(i<=0 || a<=0 || b<=0 || c<=0 || e <= 0 || x <= 0){
//				output = "cannot empty field";
//			}
//			//card no validation
//			else if(x>6) {
//				output = "card no have maximum 6 digit only";
//			}
//			else if(x<6) {
//				output="card No should have 6 digits";
//			}
//			else if(billNo.length() <= 0) {
//				output = "Invalid bill no";
//			}
			
//			else {
			// execute the statement
			preparedStmt.execute();
			con.close();
			//output = "Payment detail inserted successfully";
			String newPayment = readPaymentDetails(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newPayment + "\"}"; 
//		}
			
		}catch (Exception e)
		 {
			 //output = "Error while inserting the payment detail.";
			 output = "{\"status\":\"error\", \"data\":\"Error while inserting the item.\"}";
			 System.err.println(e.getMessage());
		 }
		    return output;
	}
	
	
//	private String String(String paymentType) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	

	//Read payment list
		public String readPaymentDetails()
	    {
		   String output = "";
		   
		   try
		   {
			   Connection con = connect(); 
			   if (con == null) 
			   { 
				   return "Error while connecting to the database for reading."; 
			   } 
			 
		     // Prepare the html table to be displayed
		     output = "<table border='1'><tr><th>Customer ID</th><th>Customer Name</th>" +
					   "<th>Payment Type</th>" +
					   "<th>Card No</th>" +
					   "<th>Payment Amount</th>" +
					   "<th>Payment Date</th>" +
					   "<th>Bill No</th>" +
					   "<th>Update</th><th>Remove</th></tr>";
		     

			 String query = "select * from payment";
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 
			 
			 // iterate through the rows in the result set
			 while (rs.next())
			 {
				 String paymentNo = Integer.toString(rs.getInt("paymentNo"));
				 String customerID = rs.getString("customerID");
				 String customerName = rs.getString("customerName");
				 String paymentType = rs.getString("paymentType");
				 String cardNo = rs.getString("cardNo");
				 String paymentAmount = Double.toString(rs.getDouble("paymentAmount"));
				 String paymentDate = rs.getString("paymentDate");
				 String billNo = rs.getString("billNo");
				 
				 // Add into the html table
				 output += "<tr><td>" + customerID + "</td>";
				 output += "<td>" + customerName + "</td>";
				 output += "<td>" + paymentType + "</td>";
				 output += "<td>" + cardNo + "</td>";
				 output += "<td>" + paymentAmount + "</td>";
				 output += "<td>" + paymentDate + "</td>";
				 output += "<td>" + billNo + "</td>";
				 
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' "
						 + "class='btnUpdate btn btn-secondary' data-paymentno='" + paymentNo + "'></td>"
						 + "<td><input name='btnRemove' type='button' value='Remove' "
						 + "class='btnRemove btn btn-danger' data-paymentno='" + paymentNo + "'></td></tr>"; 
			 }
			 
			 con.close();
			 
			 // Complete the html table
			 output += "</table>";
		   }
		   catch (Exception e)
		   {
			 output = "Error while reading the payment details.";
			 System.err.println(e.getMessage());
		   }
		   
		   return output;
	    }
		
		
		//Update payment details
		
		public String updatePaymentDetails(String paymentNo,String customerID, String customerName, String paymentType, String cardNo, String amount, String date, String billNo)
		{
			String output = "";
			
			try
			{
				Connection con = connect(); 
				 if (con == null) 
				 { 
					 return "Error while connecting to the database for updating."; 
				 } 
				
				// create a prepared statement
				String query = "UPDATE payment SET customerID=?,customerName=?,paymentType=?,cardNo=?, paymentAmount=?, paymentDate=?, billNo=? WHERE paymentNo=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
		 
				// binding values
		
				preparedStmt.setString(1, customerID);
				preparedStmt.setString(2, customerName);
				preparedStmt.setString(3, paymentType);
				preparedStmt.setString(4, cardNo);
				preparedStmt.setDouble(5, Double.parseDouble(amount));
				preparedStmt.setString(6, date);
				preparedStmt.setString(7,(billNo));
				preparedStmt.setInt(8, Integer.parseInt(paymentNo));
//				
//				int customerId = Integer.parseInt(customerID);
//				int cardNO = Integer.parseInt(cardNo);
//				double payAmount = Double.parseDouble(amount);
//				int billNO = Integer.parseInt(billNo);
//				String type = String(paymentType);
				
				
				
//				//form validation
//			    int x = cardNo.length();
//			    int i = customerName.length();
//			    int a = paymentType.length();
//			    int b = amount.length();
//			    int c = date.length();
//			    int d = billNo.length();
//			    int e = customerID.length();
//			    
//				
//				if(customerId<0 || cardNO < 0 || payAmount <0 || billNO<0) {
//					output="Please enter correct values";
//				}
//				else if(x>6) {
//					output = "card no have maximum 6 digit only";
//				}
//				else if(x<6) {
//					output="card No should have 6 digits";
//				}
//				
//				else if( e < 4 || e > 4  ){
//					output = "Please enter correct customer ID ";
//				}	
//				
//				else if(i<=0 || a<=0 || b<=0 || c<=0 || d<=0 || e<= 0){
//					output = "cannot empty field";
//					
//				}
//		
//				else {
				 
				 // execute the statement
				 preparedStmt.execute();
				 con.close();
				 
				 //output = "Payment detail updated successfully";
				 String newPayments = readPaymentDetails(); 
				 output = "{\"status\":\"success\", \"data\": \"" + 
				 newPayments + "\"}"; 
				 
//		        }
			}catch (Exception e)
			{
				 //output = "Error while updating the payment details.";
				output = "{\"status\":\"error\", \"data\": \"Error while updating the payment.\"}"; 

				 System.err.println(e.getMessage());
		    }
			
		   return output;
		   
		  }
		

		//Delete Payment Details
		public String deletePaymentDetails(String paymentNo) 
		 { 
		 String output = ""; 
		 try
		 { 
		 Connection con = connect(); 
		 if (con == null) 
		 { 
		 return "Error while connecting to the database for deleting."; 
		 } 
		 // create a prepared statement
		 String query = "delete from payment where paymentNo=?"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 // binding values
		 preparedStmt.setInt(1, Integer.parseInt(paymentNo)); 
		 // execute the statement
		 preparedStmt.execute(); 
		 con.close(); 
		 String newPayment = readPaymentDetails(); 
		 output = "{\"status\":\"success\", \"data\": \"" + 
		 newPayment + "\"}"; 
		 } 
		 catch (Exception e) 
		 { 
		 output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}"; 
		 System.err.println(e.getMessage()); 
		 } 
		 return output; 
		 } 
		

		
		
	//get payment detail using paymentNo
		
//		public String PaymentDetails(String paymentNo1)
//	    {
//		   String output = "";
//		   
//		   try
//		   {
//			   Connection con = connect(); 
//				 if (con == null) 
//				 { 
//					 return "Error while connecting to the database for reading."; 
//				 } 
//			 
//		     // Prepare the html table to be displayed
//		     output = "<table border='1'><tr><th>Customer ID</th><th>Customer Name</th>" +
//					   "<th>Payment Type</th>" +
//					   "<th>Card No</th>" +
//					   "<th>Payment Amount</th>" +
//					   "<th>Payment Date</th>" +
//					   "<th>Bill No</th>" +
//					   "<th>Update</th><th>Remove</th></tr>";
//		     
//
//		     String query = "select *  from payment where paymentNo =' " + paymentNo1 + "'" ;
//			 Statement stmt = con.createStatement();
//			 ResultSet rs = stmt.executeQuery(query);
//			 
//			 
//			 // iterate through the rows in the result set
//			 while (rs.next())
//			 {
//				 String paymentNo = Integer.toString(rs.getInt("paymentNo"));
//				 String customerID = rs.getString("customerID");
//				 String customerName = rs.getString("customerName");
//				 String paymentType = rs.getString("paymentType");
//				 String cardNo = rs.getString("cardNo");
//				 String paymentAmount = Double.toString(rs.getDouble("paymentAmount"));
//				 String paymentDate = rs.getString("paymentDate");
//				 String billNo = rs.getString("billNo");
//				 
//				 // Add into the html table
//				 output += "<tr><td>" + customerID + "</td>";
//				 output += "<td>" + customerName + "</td>";
//				 output += "<td>" + paymentType + "</td>";
//				 output += "<td>" + cardNo + "</td>";
//				 output += "<td>" + paymentAmount + "</td>";
//				 output += "<td>" + paymentDate + "</td>";
//				 output += "<td>" + billNo + "</td>";
//				 
//				 // buttons
//				 output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
//							 + "<td><form method='post' action='search.jsp'>"
//							 + "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
//							 + "<input name='paymentNo' type='hidden' value='" + paymentNo
//							 + "'>" + "</form></td></tr>";
//			 }
//			 
//			 con.close();
//			 
//			 // Complete the html table
//			 output += "</table>";
//		   }
//		   catch (Exception e)
//		   {
//			 output = "Error while reading the payment details.";
//			 System.err.println(e.getMessage());
//		   }
//		   
//		   return output;
//	    }
//		
//
//		//Get payment history
//		public String PaymentHistory(String customerId)
//	    {
//		   String output = "";
//		   
//		   try
//		   {
//			   Connection con = connect(); 
//				 if (con == null) 
//				 { 
//					 return "Error while connecting to the database for reading."; 
//				 } 
//			 
//		     // Prepare the html table to be displayed
//		     output = "<table border='1'><tr><th>Customer ID</th><th>Customer Name</th>" +
//					   "<th>Payment Type</th>" +
//					   "<th>Card No</th>" +
//					   "<th>Payment Amount</th>" +
//					   "<th>Payment Date</th>" +
//					   "<th>Bill No</th>" +
//					   "<th>Update</th><th>Remove</th></tr>";
//		     
//
//		     String query = "select *  from payment where customerID =' " + customerId + "'" ;
//			 Statement stmt = con.createStatement();
//			 ResultSet rs = stmt.executeQuery(query);
//			 
//			 
//			 // iterate through the rows in the result set
//			 while (rs.next())
//			 {
//				 
//				 String paymentNo = Integer.toString(rs.getInt("paymentNo"));
//				 String customerID = rs.getString("customerID");
//				 String customerName = rs.getString("customerName");
//				 String paymentType = rs.getString("paymentType");
//				 String cardNo = rs.getString("cardNo");
//				 String paymentAmount = Double.toString(rs.getDouble("paymentAmount"));
//				 String paymentDate = rs.getString("paymentDate");
//				 String billNo = rs.getString("billNo");
//				 
//				 // Add into the html table
//				 output += "<tr><td>" + customerID + "</td>";
//				 output += "<td>" + customerName + "</td>";
//				 output += "<td>" + paymentType + "</td>";
//				 output += "<td>" + cardNo + "</td>";
//				 output += "<td>" + paymentAmount + "</td>";
//				 output += "<td>" + paymentDate + "</td>";
//				 output += "<td>" + billNo + "</td>";
//				 
//				 // buttons
//				 output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
//							 + "<td><form method='post' action='payment.jsp'>"
//							 + "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
//							 + "<input name='itemID' type='hidden' value='" + paymentNo
//							 + "'>" + "</form></td></tr>";
//			 }
//			 
//			 con.close();
//			 
//			 // Complete the html table
//			 output += "</table>";
//		   }
//		   catch (Exception e)
//		   {
//			 output = "Error while reading the payment details.";
//			 System.err.println(e.getMessage());
//		   }
//		   
//		   return output;
//	    }
		
		
		

	
}

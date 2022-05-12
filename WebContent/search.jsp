<%@page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search Payment Details</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>

</head>
<body>

<div class="container"><div class="row"><div class="col-12"> 
<br>
<h3> Search Payment Details </h3>

	<form action="SearchAPI" method="get">
	
	
	
		 <br>Payment Number:<br><br>
		<input id="paymentNo" name="paymentNo" type="text" class="form-control form-control-sm col-4" >
  
		<br><br>
		<input type="submit" value="Search" class="btn btn-success" >
		
		

	</form>
	
	
			
			<br><br>
			
			
	</div>
	</div>
	</div>




</body>
</html>
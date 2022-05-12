/**
 * 
 */
 
$(document).ready(function() 
{ 
	if ($("#alertSuccess").text().trim() == "") 
	 { 
	 	$("#alertSuccess").hide(); 
	 } 
	 $("#alertError").hide(); 
}); 


// SAVE ============================================
	$(document).on("click", "#btnSave", function(event) 
{ 
// Clear alerts---------------------
 $("#alertSuccess").text(""); 
 $("#alertSuccess").hide(); 
 $("#alertError").text(""); 
 $("#alertError").hide(); 
// Form validation-------------------
var status = validateformPayment(); 
if (status != true) 
 { 
 $("#alertError").text(status); 
 $("#alertError").show(); 
 return; 
 } 
 
 
 
// If valid------------------------
var type = ($("#hidPaymentNoSave").val() == "") ? "POST" : "PUT"; 
 $.ajax( 
 { 
	 url : "PaymentsAPI", 
	 type : type, 
	 data : $("#formPayment").serialize(), 
	 dataType : "text", 
	 
 complete : function(response, status) 
 { 
 	onPaymentSaveComplete(response.responseText, status); 
 } 
 }); 
});


function onPaymentSaveComplete(response, status) 
{ 
if (status == "success") 
 { 
	 var resultSet = JSON.parse(response); 
	 if (resultSet.status.trim() == "success") 
 { 
	 $("#alertSuccess").text("Successfully saved."); 
	 $("#alertSuccess").show(); 
	 $("#divPaymnetsGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
	 $("#alertError").text(resultSet.data); 
	 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
	 $("#alertError").text("Error while saving."); 
	 $("#alertError").show(); 
 } else
 { 
	 $("#alertError").text("Unknown error while saving.."); 
	 $("#alertError").show(); 
 } 
 
	 $("#hidPaymentNoSave").val(""); 
	 $("#formPayment")[0].reset(); 
}


//update====================
$(document).on("click", ".btnUpdate", function(event) 
{ 
$("#hidPaymentNoSave").val($(this).data("paymentno")); 
 $("#customerID").val($(this).closest("tr").find('td:eq(0)').text()); 
 $("#customerName").val($(this).closest("tr").find('td:eq(1)').text()); 
 $("#paymentType").val($(this).closest("tr").find('td:eq(2)').text()); 
 $("#cardNo").val($(this).closest("tr").find('td:eq(3)').text()); 
 $("#paymentAmount").val($(this).closest("tr").find('td:eq(4)').text()); 
 $("#paymentDate").val($(this).closest("tr").find('td:eq(5)').text()); 
 $("#billNo").val($(this).closest("tr").find('td:eq(6)').text()); 
})



// CLIENT-MODEL================================================================
	function validateformPayment() 
	{ 
		// CUSTOMER ID
		if ($("#customerID").val().trim() == "") 
 		{ 
			 return "Insert Customer ID."; 
 		} 
		// NAME
			if ($("#customerName").val().trim() == "") 
			 { 
 				return "Insert Customer Name."; 
 			} 
 			
 			// Payment type
			if ($("#paymentType").val().trim() == "") 
			 { 
 				return "Insert Payment Type."; 
 			} 
 
 // Card No-------------------------------
		if ($("#cardNo").val().trim() == "") 
 		{ 
 			return "Insert Card Number."; 
		 } 
		// is numerical value
		var tmpPrice = $("#cardNo").val().trim(); 
		if (!$.isNumeric(tmpPrice)) 
 		{ 
 			return "Insert a Correct Card Numer."; 
 		} 
 
// PRICE-------------------------------
		if ($("#paymentAmount").val().trim() == "") 
 		{ 
 			return "Insert Payment Amount."; 
		 } 
		// is numerical value
		var tmpPrice = $("#paymentAmount").val().trim(); 
		if (!$.isNumeric(tmpPrice)) 
 		{ 
 			return "Insert a numerical value for Payment Amount."; 
 		} 
		// convert to decimal price
 		$("#paymentAmount").val(parseFloat(tmpPrice).toFixed(2)); 
 		
// payment Date------------------------
		if ($("#paymentDate").val().trim() == "") 
 		{ 
 			return "Insert Payment Date."; 
 		} 
		
	// Bill no------------------------
		if ($("#billNo").val().trim() == "") 
 		{ 
 			return "Insert Bill No."; 
 		} 
 		// is numerical value
		var tmpPrice = $("#billNo").val().trim(); 
		if (!$.isNumeric(tmpPrice)) 
 		{ 
 			return "Insert a numerical value for Bill Number."; 
 		} 
		return true; 
	}
	
	
//DELETE
$(document).on("click", ".btnRemove", function(event) 
{ 
 $.ajax( 
 { 
 url : "PaymentsAPI", 
 type : "DELETE", 
 data : "paymentNo=" + $(this).data("paymentno"),
 dataType : "text", 
 complete : function(response, status) 
 { 
 onItemDeleteComplete(response.responseText, status); 
 } 
 }); 
});


function onItemDeleteComplete(response, status) 
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully deleted."); 
 $("#alertSuccess").show(); 
 $("#divPaymnetsGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while deleting."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while deleting.."); 
 $("#alertError").show(); 
 } 
 }


<?php
require "startconnection.php";


$in_itemname = $_POST['itemname'];
$in_stocknum = $_POST['itemstock'];
$in_sellerid = $_POST['sellerid'];
$in_price = $_POST['price'];
$in_description = $_POST['description'];

$in_itemname = mysql_real_escape_string($in_itemname);
$in_stocknum = mysql_real_escape_string($in_stocknum);
$in_sellerid = mysql_real_escape_string($in_sellerid);
$in_price = mysql_real_escape_string($in_price);
$in_description = mysql_real_escape_string($in_description);

$sql_check_name = "SELECT itemid from item where itemname like '$in_itemname';";
$check_name = mysqli_query($conn,$sql_check_name);
 if(mysqli_num_rows($check_name)==0){
	 echo "Item not available. Check item name again?";
	}
else
{
	$row=mysqli_fetch_assoc($check_name);
	$in_itemid = $row['itemid'];
	$sql_check_stock = "SELECT * from itemstock where itemid like '$in_itemid';";
	$check_stock = mysqli_query($conn,$sql_check_stock);
	
	if(mysqli_num_rows($check_stock)==0){
		
		$sql_insert = "INSERT INTO itemstock (sellerid, itemid, quantity, price, description)
			VALUES ('$in_sellerid', '$in_itemid', '$in_stocknum', '$in_price', '$in_description')";
		
		if (mysqli_query($conn,$sql_insert)) {
				echo "Success";
			}
			else {
				echo "Error: " . $sql . "<br>" . mysqli_error($conn);
			}
		
	}
	else
	{
		$row2 = mysqli_fetch_assoc($check_stock);
		$in_stockid = $row2['stockid'];
		$sql_update = "UPDATE itemstock SET quantity='$in_stocknum' WHERE stockid='$in_stockid'";
		
		if (mysqli_query($conn,$sql_update)) {
				echo "Success";
			}
			else {
				echo "Error: " . $sql . "<br>" . mysqli_error($conn);
			}
		
		
	}
    
}


$conn->close();

?>
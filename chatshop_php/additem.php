<?php
require "startconnection.php";


$in_itemname = $_POST['itemname'];
$in_description = $_POST['itemdescription'];
$in_image = $_POST['image'];

$in_itemname = mysql_real_escape_string($in_itemname);
$in_description = mysql_real_escape_string($in_description);
$in_image = mysql_real_escape_string($in_image);


$sql_check_name = "SELECT itemid from item where itemname like '$in_itemname';";

$sql_insert = "INSERT INTO item (itemname, itemdescription, image)
				VALUES ('$in_itemname', '$in_description', '$in_image')";

$check_name = mysqli_query($conn,$sql_check_name);

 if(mysqli_num_rows($check_name)>0){
	 echo "Item name not available.";
	}
	 else
	 {
	  if(mysqli_query($conn,$sql_insert)) {
				echo "Success";
			}
			else {
				echo "Error: " . $sql . "<br>" . mysqli_error($conn);
			}
	 }
    
$conn->close();

?>
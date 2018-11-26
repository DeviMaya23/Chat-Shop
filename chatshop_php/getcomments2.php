<?php
require "startconnection.php";

$in_itemid = $_POST['itemid'];

$sql_getcomment = "SELECT * from comment where itemid like '$in_itemid';";
$getcomment = mysqli_query($conn,$sql_getcomment);

	if (mysqli_num_rows($getcomment)==0)
		die("No one has commented on this yet!");
	
	echo "Success";
	
    while($row = mysqli_fetch_assoc($getcomment))
    {
		$userid = $row['userId'];
		$sql_getusername = "SELECT * from user where userid like '$userid';";
		$getusername = mysqli_query($conn, $sql_getusername);
		$row2 = mysqli_fetch_assoc($getusername);
		
        echo "{" . $row2['name']. "|" . $row['date'] . "|" .
		$row['comment'] . "}";
    }
?>
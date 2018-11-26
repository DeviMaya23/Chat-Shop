<?php
require "startconnection.php";

$in_itemid = "1";

$sql_getstock = "SELECT * from comment where itemid like '$in_itemid';";
$getstock = mysqli_query($conn,$sql_getstock);

	if (mysqli_num_rows($getstock)==0)
		die("No one has commented on this yet!");
	
    while($row = mysqli_fetch_assoc($getstock))
    {
		$userid = $row['userId'];
		$sql_getusername = "SELECT * from user where userid like '$userid';";
		$getusername = mysqli_query($conn, $sql_getusername);
		$row2 = mysqli_fetch_assoc($getusername);
		
        echo "{" . $row2['name']. "|" .$row['commentId'] . "|" . $row['itemId'] . "|" . $row['userId'] . "|" . $row['date'] . "|" .
		$row['comment'] . "} <br>";
    }
?>
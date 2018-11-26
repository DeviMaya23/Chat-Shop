<?php
require "startconnection.php";

$in_itemid = "2";

$sql_getstock = "SELECT * from itemstock where itemid like '$in_itemid';";
$getstock = mysqli_query($conn,$sql_getstock);

	if (mysqli_num_rows($getstock)==0)
		die("No one is selling this item at the moment, sorry!");
	
    while($row = mysqli_fetch_assoc($getstock))
    {
		$sellerid = $row['sellerid'];
		$sql_getsellername = "SELECT * from user where userid like '$sellerid';";
		$getsellername = mysqli_query($conn, $sql_getsellername);
		$row2 = mysqli_fetch_assoc($getsellername);
		
        echo "{" . $row2['name']. "|" .$row['stockid'] . "|" . $row['sellerid'] . "|" . $row['quantity'] . "|" . $row['price'] . "|" .
		$row['description'] . "} <br>";
    }
?>
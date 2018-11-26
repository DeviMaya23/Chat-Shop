<?php
require "startconnection.php";

$in_itemname = "Pikachu Pokedoll (2017)";

$sql_getitemid = "SELECT * from item where itemname like '$in_itemname';";
$getitemid = mysqli_query($conn,$sql_getitemid);
if (mysqli_num_rows($getitemid)==0)
	die ("wrong item name");
$row3 = mysqli_fetch_assoc($getitemid);
$itemid = $row3['itemid'];

$sql_getstock = "SELECT * from itemstock where itemid like '$itemid';";
$getstock = mysqli_query($conn,$sql_getstock);


	if (mysqli_num_rows($getstock)==0)
		echo "No one is selling this item at the moment, sorry!";
	
	
	else {
		echo "Success";
    while($row = mysqli_fetch_assoc($getstock))
    {
		$sellerid = $row['sellerid'];
		$sql_getsellername = "SELECT * from user where userid like '$sellerid';";
		$getsellername = mysqli_query($conn, $sql_getsellername);
		$row2 = mysqli_fetch_assoc($getsellername);
		
        echo "{" . $row2['name']. "|" .$row['stockid'] . "|" . $row['sellerid'] . "|" . $row['quantity'] . "|" . $row['price'] . "|" .
		$row['description'] . "} <br>";
    }
	}
?>
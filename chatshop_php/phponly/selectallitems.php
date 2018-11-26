<?php
require "startconnection.php";

$sql_selectall = "SELECT * from item;";
$selectall = mysqli_query($conn,$sql_selectall);

	if (mysqli_num_rows($selectall)<1)
		die("Cannot get items");
	
	echo "Success";
	
    while($row = mysqli_fetch_assoc($selectall))
    {
        echo "{" . $row['itemid'] . "|" . $row['itemname'] . "|" . $row['itemdescription'] . "|" .
		$row['image'] . "|" . $row['status'] ."}";
    }
?>
<?php
require "startconnection.php";
$in_itemid = "1";
$in_userid = "18";
$in_comment = "Kalau mau cari yang versi 2006 dimana ya?";

$date = date('Y-m-d H:i:s');

$sql_insert = "INSERT INTO comment (itemId, userId, date, comment)
				VALUES ('$in_itemid', '$in_userid', '$date', '$in_comment')";
				
				$insert = mysqli_query($conn,$sql_insert);

if ($insert) {
	echo "Added";
	}
else {
	echo "Error: " . $sql . "<br>" . mysqli_error($conn);
	}
	
	echo mysqli_insert_id($conn);
$conn->close();
?>
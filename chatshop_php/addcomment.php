<?php
require "startconnection.php";
$in_itemid = $_POST['itemid'];
$in_userid = $_POST['userid'];
$in_comment = $_POST['comment'];

$date = date('Y-m-d H:i:s');

$sql_insert = "INSERT INTO comment (itemId, userId, date, comment)
				VALUES ('$in_itemid', '$in_userid', '$date', '$in_comment')";

if (mysqli_query($conn,$sql_insert)) {
	echo "Success";
	}
else {
	echo "Error: " . $sql . "<br>" . mysqli_error($conn);
	}
$conn->close();
?>
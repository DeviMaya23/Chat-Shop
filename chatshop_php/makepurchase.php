<?php
require "startconnection.php";

$in_buyerid = $_POST['buyerid'];
$in_sellerid = $_POST['sellerid'];
$in_stockid = $_POST['stockid'];
$in_quantity = $_POST['quantity'];

$new_trid ="";

$status = "1";
$date = date('Y-m-d H:i:s');


$sql_in_transaction = "INSERT INTO transaction (sellerId, buyerId, status)
						VALUES ('$in_sellerid', '$in_buyerid', '$status');";

$in_transaction = mysqli_query($conn,$sql_in_transaction);
if ($in_transaction)
{
	$new_trid = mysqli_insert_id($conn);
}
else
	die("Cannot make transaction; try again later.");


$sql_in_trhist = "INSERT INTO transactionhistory (transactionID, date, action)
						VALUES ('$new_trid', '$date', '$status');";
						
$sql_in_tritem = "INSERT INTO transactionitems (transactionID, stockId, quantity)
						VALUES ('$new_trid', '$in_stockid', '$in_quantity');";						

$in_trhist = mysqli_query($conn,$sql_in_trhist);
$in_tritem = mysqli_query($conn,$sql_in_tritem);

echo "Success";

$conn->close();
?>
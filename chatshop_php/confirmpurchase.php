<?php
require "startconnection.php";

$in_trid = $_POST['trid'];
$in_userid = $_POST['userid'];
$status = "1";
$newstatus = "2";
$date = date('Y-m-d H:i:s');

$sql_gettransaction = "SELECT * from transaction where transactionid like '$in_trid' AND status like '$status' AND sellerId like '$in_userid';";
$gettransaction = mysqli_query($conn,$sql_gettransaction);

$sql_updatetransaction = "UPDATE transaction SET status='$newstatus' where transactionid like '$in_trid';";
$sql_in_trhist = "INSERT INTO transactionhistory (transactionID, date, action)
						VALUES ('$in_trid', '$date', '$newstatus');";

if(mysqli_num_rows($gettransaction)==1)
	{
		echo "Success";
		mysqli_query($conn,$sql_updatetransaction);
		mysqli_query($conn,$sql_in_trhist);
	}
	else
		echo "Wrong transaction ID?";


$conn->close();
?>
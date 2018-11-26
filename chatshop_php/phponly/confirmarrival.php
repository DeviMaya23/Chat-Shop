<?php
require "startconnection.php";

$in_trid = "2";
$in_userid = "17";
$status = "3";
$newstatus = "4";
$date = date('Y-m-d H:i:s');

$sql_gettransaction = "SELECT * from transaction where transactionid like '$in_trid' AND status like '$status' AND buyerId like '$in_userid';";
$gettransaction = mysqli_query($conn,$sql_gettransaction);

$sql_updatetransaction = "UPDATE transaction SET status='$newstatus' where transactionid like '$in_trid';";
$sql_in_trhist = "INSERT INTO transactionhistory (transactionID, date, action)
						VALUES ('$in_trid', '$date', '$newstatus');";
						
$sql_getitembought = "SELECT * from transactionitems where transactionID like '$in_trid';";



$stockid="";
$stockqty="";
$qtybought="";


if(mysqli_num_rows($gettransaction)==1)
	{
		$getitembought = mysqli_query($conn,$sql_getitembought);
		if ($getitembought)
		{
			
			$row = mysqli_fetch_assoc($getitembought);
			$qtybought=$row['quantity'];
			$stockid=$row['stockId'];
			
			
			
			$sql_getitemstock = "SELECT * from itemstock where stockid like '$stockid'";
			$getitemstock = mysqli_query($conn,$sql_getitemstock);
			if ($getitemstock)
			{
				$row2 = mysqli_fetch_assoc($getitemstock);
				$stockqty = $row2['quantity'];
				$newqty = $stockqty-$qtybought;
				
				$sql_updatestock = "UPDATE itemstock SET quantity='$newqty' where stockid like '$stockid';";
				$updatestock = mysqli_query($conn,$sql_updatestock);
				$in_trhist = mysqli_query($conn,$sql_in_trhist);
				$updatetrs = mysqli_query($conn,$sql_updatetransaction);
				if($updatestock)
					echo "Success";
				else
					echo "Failed";
				
			
			}
			else
				echo "Failed";
		}
		else
			echo "Failed";
		
		
		

	}
	else
		echo "Failed";


$conn->close();
?>
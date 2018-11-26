<?php
require "startconnection.php";

$in_userid = "18";

$sql_asseller = "SELECT transaction.transactionid, transaction.buyerid, transaction.status, transactionitems.quantity, item.itemname, itemstock.description, itemstock.price from transaction \n"
    . "join transactionitems on transaction.transactionid=transactionitems.transactionID\n"
    . "join itemstock on transactionitems.stockId=itemstock.stockid\n"
    . "join item on itemstock.itemid=item.itemid \n"
    . "where transaction.sellerid='$in_userid' LIMIT 0, 30 ";
	
$sql_asbuyer = "SELECT * from transaction where buyerId like '$in_userid';";
$asseller = mysqli_query($conn,$sql_asseller);

	if (mysqli_num_rows($asseller)==0)
		echo "No Transaction";
	else {
	
		echo "Success";
		while($row = mysqli_fetch_assoc($asseller))
		{
			$buyerid = $row['buyerid'];
			$sql_getbuyername = "SELECT * from user where userid like '$buyerid';";
			$getbuyername = mysqli_query($conn, $sql_getbuyername);
			$row2 = mysqli_fetch_assoc($getbuyername);
			
			echo "{" . $row['transactionid'] . "|" . $row2['name'] . "|" . $row['itemname'] . "|" . $row['description'] . 
			"|" . $row['price'] . "|" . $row['price'] . "}";
			
			
		}
		
	}
?>
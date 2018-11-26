<?php
require "startconnection.php";

$in_userid = $_POST['userid'];

$sql_asbuyer = "SELECT transaction.transactionid, transaction.sellerid, transaction.status, transactionitems.quantity, item.itemname, itemstock.description, itemstock.price from transaction \n"
    . "join transactionitems on transaction.transactionid=transactionitems.transactionID\n"
    . "join itemstock on transactionitems.stockId=itemstock.stockid\n"
    . "join item on itemstock.itemid=item.itemid \n"
    . "where transaction.buyerid='$in_userid' LIMIT 0, 30 ";
	
$asbuyer = mysqli_query($conn,$sql_asbuyer);

	if (mysqli_num_rows($asbuyer)==0)
		echo "No Transaction";
	else {
	
		echo "Success";
		while($row = mysqli_fetch_assoc($asbuyer))
		{
			$sellerid = $row['sellerid'];
			$sql_getsellername = "SELECT * from user where userid like '$sellerid';";
			$getsellername = mysqli_query($conn, $sql_getsellername);
			$row2 = mysqli_fetch_assoc($getsellername);
			
			echo "{" . $row['transactionid'] . "|" . $row2['name'] . "|" . $row['itemname'] . "|" . $row['description'] . "|" . $row['status'] . 
			"|" . $row['quantity'] . "|" . $row['price'] . "}";
			
			
		}
		
	}
?>
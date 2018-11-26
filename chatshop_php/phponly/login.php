<?php
require "startconnection.php";

$in_username = $_POST['username'];
$in_password = $_POST['password'];

$sql_check_login = "SELECT * from user where username like '$in_username' AND password like '$in_password';";
$check_login = mysqli_query($conn,$sql_check_login);

	if(mysqli_num_rows($check_login)==1)
	{
		$row=mysqli_fetch_assoc($check_login);
		
		echo "Success"."{" . $row['name'] . "," .
			$row['email'] . "," .
			$row['address'] . ",".
		$row['userid'] . "}"
		;
	}
	else
		echo "Failed";

?>
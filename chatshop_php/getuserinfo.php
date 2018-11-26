<?php
require "startconnection.php";

$logged_user = $_POST['username'];

	$sql_check_login = "SELECT * from user where username like '$logged_user';";
	$check_login = mysqli_query($conn,$sql_check_login);

	$row=mysqli_fetch_assoc($check_login);
	//send user infos, mungkin nanti jadiin variabel
	echo "Name : " . $row['name'] . "Email : " . $row['email'] . "Address : " . $row['address'];
	
	$conn->close();


?>
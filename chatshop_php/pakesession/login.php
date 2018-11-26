<?php
	
	session_start();
//if already logged in, go to home
	if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {
    header("Location: testpage.php"); 
	
	} else {
	require "startconnection.php";
	
	$in_username = "lest";
	$in_password = "dummy";
	
	
	$sql_check_login = "SELECT * from user where username like '$in_username' AND password like '$in_password';";
	$check_login = mysqli_query($conn,$sql_check_login);
	
	if(mysqli_num_rows($check_login)==1)
	{
		$row=mysqli_fetch_assoc($check_login);
		
		$_SESSION['loggedin'] = true;
		$_SESSION['username'] = $in_username;
		$_SESSION['name'] = $row['name'];		
	
		echo "LoggedIn";
	}
	else
		echo "Failed";
	
}

?>
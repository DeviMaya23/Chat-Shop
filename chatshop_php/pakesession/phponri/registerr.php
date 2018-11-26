<?php
require "startconnection.php";


$in_username = $_POST['first_name']; 
$in_email = $_POST['last_name']; 

$in_password = "dummy";
$in_name = "namedummy2";
$in_address = "address";

$in_username = mysql_real_escape_string($in_username);
$in_email = mysql_real_escape_string($in_email);

$in_password = mysql_real_escape_string($in_password);
$in_name = mysql_real_escape_string($in_name);
$in_address = mysql_real_escape_string($in_address);


$sql_check_email = "SELECT userid from user where email like '$in_email';";
$sql_check_name = "SELECT userid from user where username like '$in_username';";

$sql_insert = "INSERT INTO user (username, password, email, name, address)
				VALUES ('$in_username', '$in_password', '$in_email', '$in_name', '$in_address')";

$check_email = mysqli_query($conn,$sql_check_email);
$check_name = mysqli_query($conn,$sql_check_name);

 if(mysqli_num_rows($check_email)>0){
	 echo "Same email";
    }
	else {
		if(mysqli_num_rows($check_name)>0){
			echo "Same username";
		}
		else{
			
			if (mysqli_query($conn,$sql_insert)) {
				echo "Registered!";
			}
			else {
				echo "Error: " . $sql . "<br>" . mysqli_error($conn);
			}
			
		}
	}

$conn->close();

?>
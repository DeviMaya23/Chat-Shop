<?php
require "startconnection.php";


$testname = 'lest';

$sql_check_name = "SELECT * from user where username like '$testname';";
$check_name = mysqli_query($conn,$sql_check_name);

$myArray = array();
if (mysqli_num_rows($check_name)>0) {

    while($row = $check_name->fetch_array(MYSQL_ASSOC)) {
        echo "id: " . $row["userid"]. " - Name: " . $row["username"]. " " . $row["name"]. "<br>";
			
    }
}

$conn->close();


?>
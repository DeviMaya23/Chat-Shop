<?php
require "startconnection.php";

$sql_check_login = "SELECT * from user;";
$check_login = mysqli_query($conn,$sql_check_login);

    while($row = mysqli_fetch_assoc($check_login))
    {
        echo $row['username'] . " ";
    }
?>
<?php
$DB_HOST = "localhost";
$DB_USER = "root";
$DB_PASSWORD = "";
$DB_DATABASE = "olshop";

// Create connection
$conn = new mysqli($DB_HOST, $DB_USER, $DB_PASSWORD, $DB_DATABASE);
// Check connection
if ($conn->connect_error) {
    die("Connection failed");
} 
else

?>


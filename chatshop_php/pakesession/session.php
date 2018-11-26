<?php
session_start();
if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {

	}
	else{
		//go back to home
		echo "NotLoggedIn";
	}

?>
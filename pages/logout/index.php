<?php
session_start();

//include server
include '../root.php';

if(isset($_SESSION['AD_Id'])) {
	session_destroy();
	unset($_SESSION['AD_Id']);
	unset($_SESSION['AD_Username']);
	header("Location: $root");
} else {
	header("$root/authentication/");
}
?>
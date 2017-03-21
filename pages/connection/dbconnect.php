<?php
define('MYHOST', 'localhost');
define('MYDATABASE', 'bus_tracker_ukm');
define('MYUSERNAME', 'root');
define('MYPASSWORD', '');

// Create connection
$con = new mysqli(MYHOST, MYUSERNAME, MYPASSWORD, MYDATABASE);

// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 
?>
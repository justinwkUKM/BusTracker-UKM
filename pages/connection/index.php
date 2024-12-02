<?php
session_start();
if ($_SESSION['AD_Username'] != null) {
  $_SESSION['AD_Username'] == '';
//    echo "Your session is running " . $_SESSION['AD_Username']."<br>";
//    echo "<img alt='Admin Picture' src='http://sprep.me/bus_tracker_ukm/upload/admin_images/".$_SESSION['AD_Avatar']."'/>"; 
    
    header("Location: /pages/");
} 

if (!isset($_SESSION['AD_Username']) || $_SESSION['AD_Username'] == ''){
    header("Location: /pages/authentication/");
}
?>
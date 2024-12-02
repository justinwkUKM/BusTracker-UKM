<?php
 
date_default_timezone_set('Asia/Kuala_Lumpur');
$now = new DateTime();
$now = $now->format('y/m/d H:i:s');

include_once '../connection/dbconnect.php';
 
$url = $_SERVER['SERVER_NAME'].$_SERVER['REQUEST_URI'];


//Update
if (isset($_POST['update'])) {
   function check_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
  }
  $userstat = check_input($_POST['userstat']);
  $oldsid = check_input($_POST['oldsid']);    
    
$sql = "UPDATE `users` SET `U_Active` = '".$userstat."' WHERE `users`.`U_Id` = '".$oldsid."'";
  
  $result = $con->query($sql);
    
//set into action table
$action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'UPDATE', '".$url."', '[user status : ".$userstat."]', CURRENT_TIMESTAMP)";
 
$results = $con->query($action);
  header("Location: index.php");
}
 
//Edit
if (isset($_GET['did'])) {
   
  $sid = htmlspecialchars($_GET['did']);
  $sql = "select * from users where U_Id = '".$sid."'";
  $result = $con->query($sql);
  $editrow = $result->fetch_array();
}
?>
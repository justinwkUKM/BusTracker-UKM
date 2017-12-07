<?php
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
  
  $stationname = check_input($_POST['stationname']);
  $code = check_input($_POST['code']);
  $description = check_input($_POST['description']);
  $load = check_input($_POST['load']);
  $status = check_input($_POST['status']);
  $oldsid = check_input($_POST['oldsid']);   
    
   $sql = "UPDATE `station` SET `S_Id` = 'NULL', `S_Name` = '".$stationname."', `S_Code` = '".$code."', `S_Description` = '".$description."', `S_Loadlevel` = '".$load."', `S_Active` = '".$status."' WHERE `station`.`S_Id` = '".$oldsid."'";
 
  $result = $con->query($sql);
    
     //set into action table
$action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'UPDATE', '".$url."', '[station name : ".$stationname."] [code : ".$code."] [description : ".$description."] [load : ".$load."] [status : ".$status."]', CURRENT_TIMESTAMP)";
 
$results = $con->query($action);
  header("Location: index.php");
}
 
//Edit
if (isset($_GET['edit'])) {
   
  $sid = htmlspecialchars($_GET['edit']);
  $sql = "SELECT * FROM `station` WHERE S_Id = '".$sid."'";
  $result = $con->query($sql);
  $editrow = $result->fetch_array();
}
 
?>
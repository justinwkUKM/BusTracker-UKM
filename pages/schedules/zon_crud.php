<?php
date_default_timezone_set('Asia/Kuala_Lumpur');
$now = new DateTime();
$now = $now->format('y/m/d H:i:s');
 
include_once '../connection/dbconnect.php';
 
$url = $_SERVER['SERVER_NAME'].$_SERVER['REQUEST_URI'];

//Create
if (isset($_POST['create'])) {
    
 function check_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
  }

  $code = check_input($_POST['code']);
  $type = check_input($_POST['type']);    
   
 $sql =  "INSERT INTO `zon_log` (`Z_Id`, `Z_Name`, `Z_Type`, `Z_Created`, `Z_Updated_By`, `Z_Updated_Ad`) VALUES (NULL, '".$code."', '".$type."', CURRENT_TIMESTAMP, '', NULL)";
 
  $result = $con->query($sql);
    
 //set into action table
$action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'INSERT', '".$url."', '[code : ".$code."] [type : ".$type."]', CURRENT_TIMESTAMP)";
 
$results = $con->query($action);
 
  if (!$result) {
 
    die("SQL Error Message: ".$conn->error);
  }
}

//Update
if (isset($_POST['update'])) {
    
  function check_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
  }
    
  $code = check_input($_POST['code']);
  $type = check_input($_POST['type']);
  $oldsid = check_input($_POST['oldsid']);   
    
$sql = "UPDATE `zon_log` SET `Z_Name` = '".$code."', `Z_Type` = '".$type."', `Z_Updated_By` = '".$_SESSION['AD_Username']."', `Z_Updated_Ad` = '".$now."' WHERE `zon_log`.`Z_Id` = '".$oldsid."'";
 
  $result = $con->query($sql);
    
    //set into action table
$action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'UPDATE', '".$url."', '[code : ".$code."] [type : ".$type."]', CURRENT_TIMESTAMP)";
 
$results = $con->query($action);
}
 
//Delete
if (isset($_GET['delete'])) {
 
  $sid = htmlspecialchars($_GET['delete']);
  $sql = "delete from `zon_log` where Z_Id = '".$sid."'";
  $result = $con->query($sql);
    
    //set into action table
  $action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'DELETE', '".$url."', 'empty', CURRENT_TIMESTAMP)";
 
$results = $con->query($action);
 
  header("Location: add_zon.php");
}
 
//Edit
if (isset($_GET['edit'])) {
   
  $sid = htmlspecialchars($_GET['edit']);
  $sql = "SELECT * FROM `zon_log` WHERE Z_Id = '".$sid."'";
  $result = $con->query($sql);
  $editrow = $result->fetch_array();
}
 
?>
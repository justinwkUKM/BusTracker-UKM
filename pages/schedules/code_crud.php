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
  $description = check_input($_POST['description']);
    
    $sql =  "INSERT INTO `bus_code` (`BC_Id`, `BC_Code`, `BC_Type`, `BC_Desc`, `BC_Created`, `BC_Update_By`, `BC_Update_At`) VALUES (NULL, '".$code."', '".$type."', '".$description."', '".$now."', '', NULL)";
    
  $result = $con->query($sql);
    
 //set into action table
$action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'INSERT', '".$url."', '[code : ".$code."] [type : ".$type."] [description] : ".$description."]', CURRENT_TIMESTAMP)";
 
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
  $description = check_input($_POST['description']);
  $oldsid = check_input($_POST['oldsid']);   
    
 $sql =  "UPDATE `bus_code` SET `BC_Code` = '".$code."', `BC_Type` = '".$type."', `BC_Desc` = '".$description."', `BC_Update_By` = '".$_SESSION['AD_Username']."', `BC_Update_At` = '".$now."' WHERE `bus_code`.`BC_Id` = '".$oldsid."'";
    
  $result = $con->query($sql);
    
    //set into action table
$action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'UPDATE', '".$url."', '[code : ".$code."] [type : ".$type."] [description] : ".$description."]', CURRENT_TIMESTAMP)";
 
$results = $con->query($action);
}
 
//Delete
if (isset($_GET['delete'])) {
 
  $sid = htmlspecialchars($_GET['delete']);
  $sql = "delete from `bus_code` where BC_Id = '".$sid."'";
  $result = $con->query($sql);
    
    //set into action table
  $action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'DELETE', '".$url."', 'empty', CURRENT_TIMESTAMP)";
 
$results = $con->query($action);
 
  header("Location: add_code.php");
}
 
//Edit
if (isset($_GET['edit'])) {
   
  $sid = htmlspecialchars($_GET['edit']);
  $sql = "SELECT * FROM `bus_code` WHERE BC_Id = '".$sid."'";
  $result = $con->query($sql);
  $editrow = $result->fetch_array();
}
 
?>
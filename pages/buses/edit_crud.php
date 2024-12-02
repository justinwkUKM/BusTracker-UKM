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
    
  $driverId = check_input($_POST['driverId']);
  $zon = check_input($_POST['zon']);
  $plateNum = check_input($_POST['plateNum']);
  $deviceId = check_input($_POST['deviceId']);
  $update = check_input($_POST['update']);
  $oldsid = check_input($_POST['oldsid']);   
    
  $sql = " UPDATE `registered_bus` SET `RB_Driver_Id` = '".$driverId."', `RB_Name` = '".$zon."', `RB_Plate_Number` = '".$plateNum."', `RB_Device_Id` = '".$deviceId."', `RB_Updated` = '".$now."', `RB_Updated_By` = '".$_SESSION['AD_Username']."' WHERE `registered_bus`.`RB_Id` = '".$oldsid."'";
 
  $result = $con->query($sql);
    
    //set into action table
  $action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'UPDATE', '".$url."', '[driver id : ".$driverId."] [zon : ".$zon."] [plate num : ".$plateNum."] [device id : ".$deviceId."]', CURRENT_TIMESTAMP)";
 
  $results = $con->query($action);
  header("Location: index.php");
}
 
//Edit
if (isset($_GET['edit'])) {
   
  $sid = htmlspecialchars($_GET['edit']);
  $sql = "SELECT * FROM `registered_bus` WHERE RB_Id = '".$sid."'";
  $result = $con->query($sql);
  $editrow = $result->fetch_array();
    
 $driver = $editrow['RB_Driver_Id'];
 $zonname = $editrow['RB_Name'];
}
 
?>
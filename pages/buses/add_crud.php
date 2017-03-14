<?php
('Asia/Kuala_Lumpur');
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
  
  $driverId = check_input($_POST['driverId']);
  $zon = check_input($_POST['zon']);
  $plateNum = check_input($_POST['plateNum']);
  $deviceId = check_input($_POST['deviceId']);
    
   $sql = "INSERT INTO `registered_bus` (`RB_Driver_Id`, `RB_Name`, `RB_Plate_Number`, `RB_Device_Id`, `RB_Created`, `RB_Created_By`) VALUES ('".$driverId."', '".$zon."', '".$plateNum."', '".$deviceId."', '".$now."', '".$_SESSION['AD_Username']."')";
 
  $result = $con->query($sql);
    
    //set into action table
  $action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'INSERT', '".$url."', '[driver id : ".$driverId."] [zon : ".$zon."] [plate num : ".$plateNum."] [device id : ".$deviceId."]', CURRENT_TIMESTAMP)";
 
  $results = $con->query($action);
    
    header("Location: index.php");
 
  if (!$result) {
 
    die("SQL Error Message: ".$con->error);
  }
}

?>
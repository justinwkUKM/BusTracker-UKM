<?php
 date_default_timezone_set('Asia/Kuala_Lumpur');
$now = new DateTime();
$now = $now->format('y/m/d H:i:s');

include_once '../connection/dbconnect.php';

$url = $_SERVER['SERVER_NAME'].$_SERVER['REQUEST_URI'];
 
//Create
if (isset($_POST['create'])) {

  $season = $_POST['season'];
  $type = $_POST['type'];
  $day = $_POST['day'];
  $driver = $_POST['driver']; 
    

    $sql =  "INSERT INTO `bus_schedule` (`BS_Id`, `BS_Session`, `BS_Bus_Type`, `BS_Day`, `BS_Driver_Num`, `BS_Created`, `BS_Updated_By`, `BS_Updated_At`, `BS_Start`, `BS_End`) VALUES (NULL, '".$season."', '".$type."', '".$day."', '".$driver."', CURRENT_TIMESTAMP, NULL, NULL, NULL, NULL)";
 
  $result = $con->query($sql);
    
//set into action table
$action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'INSERT', '".$url."', '[season : ".$season."] [day : ".$day."] [type : ".$type."] [driver num : ".$driver."]', CURRENT_TIMESTAMP)";
 
$results = $con->query($action);
  header("Location: index.php");
 
  if (!$result) {
 
    die("SQL Error Message: ".$conn->error);
  }
}

//Update
if (isset($_POST['update'])) {
  
  $season = $_POST['season'];
  $day = $_POST['day'];
  $type = $_POST['type'];
  $driver = $_POST['driver'];
  $oldpid = $_POST['oldpid'];   
    
$sql = "UPDATE `bus_schedule` SET `BS_Session` = '".$season."', `BS_Bus_Type` = '".$type."', `BS_Day` = '".$day."', `BS_Driver_Num` = '".$driver."', `BS_Updated_By` = '".$_SESSION['AD_Username']."', `BS_Updated_At` = '".$now."' WHERE `bus_schedule`.`BS_Id` = '".$oldpid."'";
    
  $result = $con->query($sql);
    
//set into action table
$action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'UPDATE', '".$url."', '[season : ".$season."] [day : ".$day."] [type : ".$type."] [driver num : ".$driver."]', CURRENT_TIMESTAMP)";
 
$results = $con->query($action);
  header("Location: index.php");
}
 
//Edit
if (isset($_GET['edit'])) {
   
  $sid = $_GET['edit'];
  $sql = "SELECT * FROM `bus_schedule` WHERE BS_Id = '".$sid."'";
  $result = $con->query($sql);
  $editrow = $result->fetch_array();
}
 
?>
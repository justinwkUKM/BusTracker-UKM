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
      
  $sname =  check_input($_POST['sname']);
  $uname = check_input($_POST['uname']);
  $type = check_input($_POST['adminType']);
  $status = check_input($_POST['adminStatus']);
  $phone = check_input($_POST['phone']);
  $email = check_input($_POST['email']);
  $position = check_input($_POST['position']);
  $desc = check_input($_POST['desc']);
  $oldsid = check_input($_POST['oldsid']); 
    
$sql = "UPDATE `admin` SET `AD_Name` = '".$sname."', `AD_Description` = '".$desc."',`AD_Username` = '".$uname."', `AD_Type` = '".$type."', `AD_Active` = '".$status."', `AD_Email` = '".$email."', `AD_Phone` = '".$phone."', `AD_Position` = '".$position."', `AD_Updated` = '".$now."', `AD_Updated_By` = '".$_SESSION['AD_Username']."' WHERE `admin`.`AD_Id` = '".$oldsid."'";
    
$result = $con->query($sql);
    
//set into action table
$action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'UPDATE', '".$url."', '[name : ".$sname."] [type : ".$type."] [status : ".$status."] [phone num : ".$phone."] [email : ".$email."] [position : ".$position."] [description : ".$desc."]', CURRENT_TIMESTAMP)";
 
$results = $con->query($action);
    
header("Location: index.php");
}
 
//Edit
if (isset($_GET['edit'])) {
   
  $sid = htmlspecialchars($_GET['edit']);
  $sql = "select * from admin where AD_Id = '".$sid."'";
  $result = $con->query($sql);
  $editrow = $result->fetch_array();
}
 
?>
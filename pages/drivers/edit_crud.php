<?php
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
  $driverName = check_input($_POST['driverName']);
  $gender = check_input($_POST['gender']);
  $phoneNum = check_input($_POST['phoneNum']);
  $status = check_input($_POST['status']);
  $email = check_input($_POST['email']);
  $address = check_input($_POST['address']);
   
  $sql = "INSERT into `driver_info` (";
  $sql = $sql."DI_Name, ";
  $sql = $sql."DI_Gender, ";
  $sql = $sql."DI_Phone, ";
  $sql = $sql."DI_Active, ";
  $sql = $sql."DI_Email, ";
  $sql = $sql."DI_Address) values(";
  $sql = $sql."'".$driverName."', ";
  $sql = $sql."'".$gender."', ";
  $sql = $sql."'".$phoneNum."', ";
  $sql = $sql."'".$status."', ";
  $sql = $sql."'".$email."', ";
  $sql = $sql."'".$address."')";
 
  $result = $con->query($sql);
    
    //set into action table
$action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'INSERT', '".$url."', '[driver name : ".$driverName."] [gender : ".$gender."] [phone num  : ".$phoneNum."] [status : ".$status."] [email : ".$email."] [address : ".$address."]', CURRENT_TIMESTAMP)";
 
$results = $con->query($action);
  header("Location: index.php");
 
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
    
  $driverName = check_input($_POST['driverName']);
  $gender = check_input($_POST['gender']);
  $phoneNum = check_input($_POST['phoneNum']);
  $status = check_input($_POST['status']);
  $email = check_input($_POST['email']);
  $address = check_input($_POST['address']);
  $oldpid = check_input($_POST['oldpid']);   
    
$sql = "UPDATE `driver_info` SET `DI_Name` = '".$driverName."', `DI_Gender` = '".$gender."', `DI_Phone` = '".$phoneNum."', `DI_Active` = '".$status."', `DI_Email` = '".$email."', `DI_Address` = '".$address."' WHERE `driver_info`.`DI_Id` = '".$oldpid."'";
 
  $result = $con->query($sql);
    
//set into action table
$action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'UPDATE', '".$url."', '[driver name : ".$driverName."] [gender : ".$gender."] [phone num  : ".$phoneNum."] [status : ".$status."] [email : ".$email."] [address : ".$address."]', CURRENT_TIMESTAMP)";
 
$results = $con->query($action);
  header("Location: index.php");
}
 
//Delete
if (isset($_GET['delete'])) {
 
  $sid = htmlspecialchars($_GET['delete']);
  $sql = "delete from `driver_info` where DI_Id = '".$sid."'";
  $result = $con->query($sql);
 
  header("Location: index.php");
}
 
//Edit
if (isset($_GET['edit'])) {
   
  $sid = htmlspecialchars($_GET['edit']);
  $sql = "SELECT * FROM `driver_info` WHERE DI_Id = '".$sid."'";
  $result = $con->query($sql);
  $editrow = $result->fetch_array();
}
 
?>
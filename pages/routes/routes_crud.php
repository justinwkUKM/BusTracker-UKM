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
    
 $rname = check_input($_POST['rname']);
  $buses = check_input($_POST['buses']);
  $status = check_input($_POST['status']);
  $checkbox1=check_input($_POST['route']);  
    $chk=array();
    
    foreach($checkbox1 as $chk1)  
       {  
        array_push($chk,$chk1);
       }  
    $chk = json_encode($chk);
   
 $sql =  "INSERT INTO `routes` (`Ro_Id`, `Ro_Route`, `Ro_Name`, `Ro_Route_Busses`, `Ro_Created`, `Ro_Created_By`, `Ro_Active`) VALUES (NULL, '".$chk."', '".$rname."', '".$buses."', '".$now."', '".$_SESSION['AD_Username']."', '".$status."');";
 
  $result = $con->query($sql);
    
//set into action table
$action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'INSERT', '".$url."', '[route name : ".$rname."] [buses : ".$buses."] [status : ".$status."] [route : ".$chk."]', CURRENT_TIMESTAMP)";
 
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
    
  $rname = check_input($_POST['rname']);
  $buses = check_input($_POST['buses']);
  $status = check_input($_POST['status']);
  $checkbox1=check_input($_POST['route']);  
    $chk=array();
    
    foreach($checkbox1 as $chk1)  
       {  
        array_push($chk,$chk1);
       }  
    $chk = json_encode($chk);
  $oldsid = check_input($_POST['oldsid']);   
    
$sql = "UPDATE `routes` SET `Ro_Route` = '".$chk."', `Ro_Name` = '".$rname."', `Ro_Route_Busses` = '".$buses."', `Ro_Active` = '".$status."' WHERE `routes`.`Ro_Id` = '".$oldsid."'";
 
  $result = $con->query($sql);
    
    //set into action table
$action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'UPDATE', '".$url."', '[route name : ".$rname."] [buses : ".$buses."] [status : ".$status."] [route : ".$chk."]', CURRENT_TIMESTAMP)";
 
$results = $con->query($action);
  header("Location: index.php");
}
 
//Delete
if (isset($_GET['delete'])) {
 
  $sid = htmlspecialchars($_GET['delete']);
  $sql = "delete from `routes` where Ro_Id = '".$sid."'";
  $result = $con->query($sql);
 
  header("Location: index.php");
}
 
//Edit
if (isset($_GET['edit'])) {
   
  $sid = htmlspecialchars($_GET['edit']);
  $sql = "SELECT * FROM `routes` WHERE Ro_Id = '".$sid."'";
  $result = $con->query($sql);
  $editrow = $result->fetch_array();
}
 
?>
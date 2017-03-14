<?php
 date_default_timezone_set('Asia/Kuala_Lumpur');
$now = new DateTime();
$now = $now->format('y/m/d H:i:s');

include_once '../connection/dbconnect.php';

$url = $_SERVER['SERVER_NAME'].$_SERVER['REQUEST_URI'];

//get number of row from set_schedule.php
$_SESSION["time"];
$_SESSION["BS_Driver_Num"];
 
//Create
if (isset($_POST['times'])) {
 
function check_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
  }
    
  $start = check_input($_POST['start']);
  $end = check_input($_POST['end']);
  $timeInt = check_input($_POST['timeInt']);
  $addtime = check_input($_POST['addtime']);
   
 $sql =  "UPDATE `bus_schedule` SET `BS_Start` = '".$start."', `BS_End` = '".$end."', `BS_TimeInterval` = '".$timeInt."', `BS_Midnight` = '".$addtime."' WHERE `bus_schedule`.`BS_Id` = '".$_SESSION["sid"]."';";  
 
  $result = $con->query($sql);
    
    //set into action table
$action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'UPDATE', '".$url."', '[start : ".$start."] [end : ".$end."]', CURRENT_TIMESTAMP)";
 
$results = $con->query($action);
    
  if (!$result) {
 
    die("SQL Error Message: ".$conn->error);
  }
}

//save
if (isset($_POST['save'])) {
    function check_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
  }
    //loop time
    for ($x=1;$x<=$_SESSION["time"];$x++){
    //loop status and bus code
    for ($y=1;$y<=$_SESSION["BS_Driver_Num"];$y++){
        
  $id = check_input($_POST['id-'.$x.'-'.$y]);
  $time = check_input($_POST['time-'.$x]);
  $bus = check_input($_POST['bus-'.$y]);
  $status = check_input($_POST['status-'.$x.'-'.$y]);
  $oldpid = check_input($_POST['oldpid']);   
    
$sql = "UPDATE `schedule_detail` SET `SD_Time` = '".$time."', `SD_Bus` = '".$bus."', `SD_Status` = '".$status."' WHERE `schedule_detail`.`SD_id` = ".$id;
//        echo $sql; exit();
    
  $result = $con->query($sql);
    //status and bus code
    }
    //loop time
    }
    
    //set into action table
    $action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'UPDATE', '".$url."', '[time : ".$time."] [bus num : ".$bus."] [status : ".$status."]', CURRENT_TIMESTAMP)";

    $results = $con->query($action);
    
    if (!$result) {
 
    die("SQL Error Message: ".$conn->error);
  }
}
 
//Delete
if (isset($_GET['delete'])) {
 
  $sid = $_GET['delete'];
  $sql = "delete from `bus_schedule` where BS_Id = '".$sid."'";
  $result = $con->query($sql);
    
   //set into action table
  $action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'DELETE', '".$url."', 'empty', CURRENT_TIMESTAMP)";
 
$results = $con->query($action);
 
  header("Location: index.php");
}

//Set
if (isset($_GET['set'])) {
 
  $sql = "SELECT * FROM `schedule_detail` WHERE `SD_Schedule_Id` = '".$_SESSION["sid"]."'";
  $result = $con->query($sql);
  echo $_SESSION["sid"];
  header("Location: index.php");
}

?>
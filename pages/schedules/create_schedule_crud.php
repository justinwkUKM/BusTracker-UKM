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
$action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'UPDATE', '".$url."', '[start : ".$start."] [end : ".$end."] [time interval : ".$timeInt."] [midnight time : ".$addtime."]', CURRENT_TIMESTAMP)";
 
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
        
  $time = check_input($_POST['time-'.$x]);
  $bus = check_input($_POST['bus-'.$y]);
  $status = check_input($_POST['status-'.$x.'-'.$y]);
  $oldpid = check_input($_POST['oldpid']);   
    
$sql = "INSERT INTO `schedule_detail` (`SD_id`, `SD_Schedule_Id`, `SD_Time`, `SD_Bus`, `SD_Status`) VALUES (NULL, '".$_SESSION["sid"]."', '".$time."', '".$bus."', '".$status."')";
    
  $result = $con->query($sql);
    //status and bus code
    }
    //loop time
    }
    
    //midnight
    for ($j=1;$j<=$_SESSION["BS_Driver_Num"];$j++){
      $time1 = check_input($_POST['midnight_time']);
      $bus1 = check_input($_POST['bus-'.$j]);
      $status1 = check_input($_POST['midnight_status-'.$j]);
      $oldpid = check_input($_POST['oldpid']); 

      $sql1 = "INSERT INTO `schedule_detail` (`SD_id`, `SD_Schedule_Id`, `SD_Time`, `SD_Bus`, `SD_Status`) VALUES (NULL, '".$_SESSION["sid"]."', '".$time1."', '".$bus1."', '".$status1."')";
        
      $result1 = $con->query($sql1);
    }
    //set into action table
    $action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'UPDATE', '".$url."', '[time : ".$time."] [bus num : ".$bus."] [status : ".$status."]', CURRENT_TIMESTAMP)";

    $results = $con->query($action);
    
    if (!$result) {
 
    die("We are sorry");
  }
}
 
//Delete
if (isset($_GET['delete'])) {
 
  $sid = htmlspecialchars($_GET['delete']);
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
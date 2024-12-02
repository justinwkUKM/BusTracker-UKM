<?php
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
    
  $poiname = check_input($_POST['poiname']);
  $address = check_input($_POST['address']);
  $lat = check_input($_POST['lat']);
  $lng = check_input($_POST['lng']);
  $type = check_input($_POST['type']);
  $checkbox1=$_POST['sid'];  
  $chk="";  
  foreach($checkbox1 as $chk1)  
    {  
    $chk .= $chk1.", ";  
  }  
  $oldsid = check_input($_POST['oldsid']);   
    
$sql = "UPDATE `point_of_interest` SET `POI_Station_Id` = '".$chk."', `POI_Name` = '".$poiname."', `POI_Address` = '".$address."', `POI_Latitud` = '".$lat."', `POI_Longitud` = '".$lng."', `POI_Type` = '".$type."' WHERE `point_of_interest`.`POI_Id` = '".$oldsid."'";
 
  $result = $con->query($sql);
    
    //set into action table
$action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'UPDATE', '".$url."', '[poi name : ".$poiname."] [address : ".$address."] [lat : ".$lat."] [lng : ".$lng."] [type : ".$type."] [nearest station : ".$chk."]', CURRENT_TIMESTAMP)";
 
$results = $con->query($action);
  header("Location: index.php");
}
 

 
//Edit
if (isset($_GET['edit'])) {
   
  $sid = htmlspecialchars($_GET['edit']);
  $sql = "SELECT * FROM `point_of_interest` WHERE POI_Id = '".$sid."'";
  $result = $con->query($sql);
  $editrow = $result->fetch_array();
}
 
?>
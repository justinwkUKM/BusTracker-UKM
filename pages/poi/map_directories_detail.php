<?php
session_start();
date_default_timezone_set('Asia/Kuala_Lumpur');
$now = new DateTime();
$now = $now->format('y/m/d H:i:s');

include_once '../connection/dbconnect.php';

$url = $_SERVER['SERVER_NAME'].$_SERVER['REQUEST_URI'];

################ Save & delete markers #################
if($_POST) //run only if there's a post data
{
	//make sure request is comming from Ajax
	$xhr = $_SERVER['HTTP_X_REQUESTED_WITH'] == 'XMLHttpRequest'; 
	if (!$xhr){ 
		header('HTTP/1.1 500 Error: Request must come from Ajax!'); 
		exit();	
	}
	
	// get marker position and split it for database
	$mLatLang	= explode(',',$_POST["latlang"]);
	$mLat 		= filter_var($mLatLang[0], FILTER_VALIDATE_FLOAT);
	$mLng 		= filter_var($mLatLang[1], FILTER_VALIDATE_FLOAT);
	
	//Delete Marker
	if(isset($_POST["del"]) && $_POST["del"]==true)
	{
		$results = $con->query("DELETE FROM point_of_interest WHERE POI_Latitud=$mLat AND POI_Longitud=$mLng");
        
       //set into action table
        $action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'DELETE', '".$url."', 'empty', CURRENT_TIMESTAMP)";

        $result = $con->query($action);
        
		if (!$results) {  
		  header('HTTP/1.1 500 Error: Could not delete Markers!'); 
		  exit();
		} 
		exit("Done!");
	}
	
	$mName 		= filter_var($_POST["name"], FILTER_SANITIZE_STRING);
	$mAddress 	= filter_var($_POST["address"], FILTER_SANITIZE_STRING);
	$mType		= filter_var($_POST["type"], FILTER_SANITIZE_STRING);
	
	$results = $con->query("INSERT INTO `point_of_interest` (`POI_Station_Id`, `POI_Name`, `POI_Address`, `POI_Created`, `POI_Created_By`, `POI_Latitud`, `POI_Longitud`, `POI_Description`, `POI_Type`) VALUES (NULL, '$mName', '$mAddress', '".$now."', '".$_SESSION['AD_Username']."', '$mLat', '$mLng', NULL, '$mType')");
    
    //set into action table
        $action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'INSERT', '".$url."', '[name : ".$mName."] [address : ".$mAddress."] [type : ".$mType."]', CURRENT_TIMESTAMP)";

        $result = $con->query($action);
    
	if (!$results) {  
		  header('HTTP/1.1 500 Error: Could not create marker!'); 
		  exit();
	} 
	
	$output = '<h1 class="marker-heading">'.$mName.'</h1><p>'.$mAddress.'</p>';
	exit($output);
}


################ Continue generating Map XML #################

//Create a new DOMDocument object
$dom = new DOMDocument("1.0");
$node = $dom->createElement("point_of_interest"); //Create new element node
$parnode = $dom->appendChild($node); //make the node show up 

// Select all the rows in the markers table
$results = $con->query("SELECT * FROM `point_of_interest` WHERE `POI_Id` = '".$_SESSION['poi_id']."'");
if (!$results) {  
	header('HTTP/1.1 500 Error: Could not get markers!'); 
	exit();
} 

//set document header to text/xml
header("Content-type: text/xml"); 

// Iterate through the rows, adding XML nodes for each
while($obj = $results->fetch_object())
{
  $node = $dom->createElement("marker");  
  $newnode = $parnode->appendChild($node);   
  $newnode->setAttribute("name",$obj->POI_Name);
  $newnode->setAttribute("address", $obj->POI_Address);  
  $newnode->setAttribute("lat", $obj->POI_Latitud);  
  $newnode->setAttribute("lng", $obj->POI_Longitud);  
  $newnode->setAttribute("type", $obj->POI_Type);	
}

echo $dom->saveXML();
?>
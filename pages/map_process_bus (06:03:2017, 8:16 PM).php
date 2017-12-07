<?php
include_once 'connection/dbconnect.php';


if (mysqli_connect_errno()) 
{
	header('HTTP/1.1 500 Error: Could not connect to db!'); 
	exit();
}


################ Continue generating Map XML #################

//Create a new DOMDocument object
$dom = new DOMDocument("1.0");
$node = $dom->createElement("bus_location"); //Create new element node
$parnode = $dom->appendChild($node); //make the node show up 

// Select all the rows in the markers table
$results = $con->query("SELECT DISTINCT `BLL_Bus_Id`, `BLL_Ro_id`, `BLL_RB_Location`, `BLL_Latitud`, `BLL_Longitud` FROM bus_location_log");
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
  $newnode->setAttribute("bus",$obj->BLL_Bus_Id);
  $newnode->setAttribute("route", $obj->BLL_Ro_id);  
  $newnode->setAttribute("lat", $obj->BLL_Latitud);  
  $newnode->setAttribute("lon", $obj->BLL_Longitud);  
  $newnode->setAttribute("driver", $obj->BLL_RB_Location);	
}

echo $dom->saveXML();

<?php

	error_reporting( ~E_NOTICE ); // avoid notice
	
	require_once 'dbconfig.php';
	
	if(isset($_POST['btnsave']))
	{
		//$username = $_POST['user_name'];// user name
		//$userjob = $_POST['user_job'];// user email
		
		$imgFile = $_FILES['user_image']['name'];
		$tmp_dir = $_FILES['user_image']['tmp_name'];
		$imgSize = $_FILES['user_image']['size'];
		
		
		if(empty($username)){
			$errMSG = "Please Enter Username.";
		}
		else if(empty($userjob)){
			$errMSG = "Please Enter Your Job Work.";
		}
		else if(empty($imgFile)){
			$errMSG = "Please Select Image File.";
		}
        if(empty($imgFile)){
			$errMSG = "Please Select Image File.";
		}
        
		else
		{
			$upload_dir = 'admin_images/'; // upload directory
	
			$imgExt = strtolower(pathinfo($imgFile,PATHINFO_EXTENSION)); // get image extension
		
			// valid image extensions
			$valid_extensions = array('jpeg', 'jpg', 'png'); // valid extensions
		
			// rename uploading image
			$userpic = rand(1000,1000000).".".$imgExt;
				
			// allow valid image file formats
			if(in_array($imgExt, $valid_extensions)){			
				// Check file size '5MB'
				if($imgSize < 5000000)				{
					move_uploaded_file($tmp_dir,$upload_dir.$userpic);
				}
				else{
					$errMSG = "Sorry, your file is too large.";
				}
			}
			else{
				$errMSG = "Sorry, only JPG, JPEG, PNG & GIF files are allowed.";		
			}
		}
		
		
		// if no error occured, continue ....
		if(!isset($errMSG))
		{
			$stmt = $DB_con->prepare('INSERT INTO admin(AD_Avatar) WHERE () VALUES( :upic)');
			
			$stmt->bindParam(':upic',$userpic);
			
			if($stmt->execute())
			{
				$successMSG = "new record succesfully inserted ...";
				header("refresh:5;index.php"); // redirects image view page after 5 seconds.
			}
			else
			{
				$errMSG = "error while inserting....";
			}
            }
            //{
//			$stmt = $DB_con->prepare('INSERT INTO admin(AD_Username,AD_Type,AD_Avatar) VALUES(:uname, :ujob, :upic)');
//			$stmt->bindParam(':uname',$username);
//			$stmt->bindParam(':ujob',$userjob);
//			$stmt->bindParam(':upic',$userpic);
//			
//			if($stmt->execute())
//			{
//				$successMSG = "new record succesfully inserted ...";
//				header("refresh:5;index.php"); // redirects image view page after 5 seconds.
//			}
//			else
//			{
//				$errMSG = "error while inserting....";
//			}
//		}
	}
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Setting</title>

<link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css">

<!-- CSS styles -->
<link rel='stylesheet' type='text/css' href='../css/huraga-blue.css'>
		
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDB4Emcey7j7XO57Pr4LCL4DzUOzIygqWI&sensor=false"></script>

<!-- Optional theme -->
<!--<link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css">-->

</head>
<body>
<!--		header here-->
		<?php include('../nav-bar.php'); ?>
		
<!--
<div class="navbar navbar-default navbar-static-top" role="navigation">
    <div class="container">
 
       
    </div>
</div>
-->

<div class="container">


	<div class="page-header">
    	<h1 class="h2">add new user. <a class="btn btn-default" href="index.php"> <span class="glyphicon glyphicon-eye-open"></span> &nbsp; view all </a></h1>
    </div>
    

	<?php
	if(isset($errMSG)){
			?>
            <div class="alert alert-danger">
            	<span class="glyphicon glyphicon-info-sign"></span> <strong><?php echo $errMSG; ?></strong>
            </div>
            <?php
	}
	else if(isset($successMSG)){
		?>
        <div class="alert alert-success">
              <strong><span class="glyphicon glyphicon-info-sign"></span> <?php echo $successMSG; ?></strong>
        </div>
        <?php
	}
	?>   

<form method="post" enctype="multipart/form-data" class="form-horizontal">
	    
	<table class="table table-bordered table-responsive">
	
    <tr>
    	<td><label class="control-label">Username</label></td>
        <td><input class="form-control" type="text" name="user_name" value="<?php echo $_SESSION['AD_Username']; ?>" disabled /></td>
    </tr>
    
    <tr>
    	<td><label class="control-label">Admin Level</label></td>
        <td><input class="form-control" type="text" name="user_job" value="<?php echo $_SESSION['AD_Type']; ?>" disabled /></td>
    </tr>
    
    <tr>
    	<td><label class="control-label">Profile Img.</label></td>
        <td><input class="input-group" type="file" name="user_image" accept="image/*" /></td>
    </tr>
    
    <tr>
        <td colspan="2"><button type="submit" name="btnsave" class="btn btn-default">
        <span class="glyphicon glyphicon-save"></span> &nbsp; save
        </button>
        </td>
    </tr>
    
    </table>
    
</form>

</div>

<!-- Latest compiled and minified JavaScript -->
<script src="bootstrap/js/bootstrap.min.js"></script>


</body>
</html>
<?php
session_start();

if(isset($_SESSION['AD_Id'])) {
	header("Location: register.php");
}

include_once 'dbconnect.php';

//set validation error flag as false
$error = false;

//check if form is submitted
if (isset($_POST['signup'])) {
	$ad_name = mysqli_real_escape_string($con, $_POST['AD_Name']);
	$ad_username = mysqli_real_escape_string($con, $_POST['AD_Username']);
    $ad_email = mysqli_real_escape_string($con, $_POST['AD_Email']);
	$ad_password = mysqli_real_escape_string($con, $_POST['AD_Password']);
	$ad_cpassword = mysqli_real_escape_string($con, $_POST['cpassword']);
	
	//name can contain only alpha characters and space
	if (!preg_match("/^[a-zA-Z ]+$/",$ad_name)) {
		$error = true;
		$ad_name_error = "Name must contain only alphabets and space";
	}
    
	if(!filter_var($ad_email,FILTER_VALIDATE_EMAIL)) {
		$error = true;
		$ad_email_error = "Please Enter Valid Email ID";
	}
    
    	if (!preg_match("/^[a-zA-Z ]+$/",$ad_username)) {
		$error = true;
		$ad_username_error = "Username Invalid";
	}
    
	if(strlen($ad_password) < 6) {
		$error = true;
		$ad_password_error = "Password must be minimum of 6 characters";
	}
	if($ad_password != $ad_cpassword) {
		$error = true;
		$ad_cpassword_error = "Password and Confirm Password doesn't match";
	}
	if (!$error) {
		if(mysqli_query($con, "INSERT INTO admin(AD_Name,AD_Email,AD_Username,AD_Password) VALUES('" . $ad_name . "','" . $ad_email . "', '" . $ad_username . "', '" . md5($ad_password) . "')")) {
			$successmsg = "Successfully Registered! <a href='login.php'>Click here to Login</a>";
		} else {
			$errormsg = "Error in registering...Please try again later!";
		}
	}
}
?>

<!DOCTYPE html>
<html>
<head>
	<title>Admin Registration </title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" >
	<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
	<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body>

<nav class="navbar navbar-default" role="navigation">
	<div class="container-fluid">
		<!-- add header -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar1">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.php">Bus Tracker</a>
		</div>
		<!-- menu items -->
		<div class="collapse navbar-collapse" id="navbar1">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="login.php">Login</a></li>
				<li class="active"><a href="register.php">Sign Up</a></li>
			</ul>
		</div>
	</div>
</nav>

<div class="container">
	<div class="row">
		<div class="col-md-4 col-md-offset-4 well">
			<form role="form" action="<?php echo $_SERVER['PHP_SELF']; ?>" method="post" name="signupform">
				<fieldset>
					<legend>Sign Up</legend>

					<div class="form-group">
						<label for="name">Name</label>
						<input type="text" name="AD_Name" placeholder="Enter Full Name" required value="<?php if($error) echo $ad_name; ?>" class="form-control" />
						<span class="text-danger"><?php if (isset($ad_name_error)) echo $ad_name_error; ?></span>
					</div>
					
					<div class="form-group">
						<label for="name">Email</label>
						<input type="text" name="AD_Email" placeholder="Enter Your Email" required value="<?php if($error) echo $ad_email; ?>" class="form-control" />
						<span class="text-danger"><?php if (isset($ad_email_error)) echo $ad_email_error; ?></span>
					</div>
					
					<div class="form-group">
						<label for="name">Username</label>
						<input type="text" name="AD_Username" placeholder="username" required value="<?php if($error) echo $ad_username; ?>" class="form-control" />
						<span class="text-danger"><?php if (isset($ad_username_error)) echo $ad_username_error; ?></span>
					</div>

					<div class="form-group">
						<label for="name">Password</label>
						<input type="password" name="AD_Password" placeholder="Password" required class="form-control" />
						<span class="text-danger"><?php if (isset($ad_password_error)) echo $ad_password_error; ?></span>
					</div>

					<div class="form-group">
						<label for="name">Confirm Password</label>
						<input type="password" name="cpassword" placeholder="Confirm Password" required class="form-control" />
						<span class="text-danger"><?php if (isset($ad_cpassword_error)) echo $ad_cpassword_error; ?></span>
					</div>

					<div class="form-group">
						<input type="submit" name="signup" value="Sign Up" class="btn btn-primary" />
					</div>
				</fieldset>
			</form>
			<span class="text-success"><?php if (isset($successmsg)) { echo $successmsg; } ?></span>
			<span class="text-danger"><?php if (isset($errormsg)) { echo $errormsg; } ?></span>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4 col-md-offset-4 text-center">	
		Already Registered? <a href="login.php">Login Here</a>
		</div>
	</div>
</div>
<script src="js/jquery-1.10.2.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>




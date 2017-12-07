<?php
session_start();
require_once 'class.user.php';
include_once '../connection/dbconnect.php';
include '../root.php';
$url = $_SERVER['SERVER_NAME'].$_SERVER['REQUEST_URI'];
$user = new USER();



if(isset($_POST['btn-submit']))
{
	$email = $_POST['txtemail'];
	
	$stmt = $user->runQuery("SELECT AD_Id FROM admin WHERE AD_Email=:email LIMIT 1");
	$stmt->execute(array(":email"=>$email));
	$row = $stmt->fetch(PDO::FETCH_ASSOC);	
	if($stmt->rowCount() == 1)
	{
		$id = base64_encode($row['AD_Id']);
		$code = md5(uniqid(rand()));
		
		$stmt = $user->runQuery("UPDATE admin SET AD_Token=:token WHERE AD_Email=:email");
		$stmt->execute(array(":token"=>$code,"email"=>$email));
		
		$message= "
				   Hello , $email
				   <br /><br />
				   We got requested to reset your password, if you do this then just click the following link to reset your password, if not just ignore this email,
				   <br /><br />
				   Click Following Link To Reset Your Password 
				   <br /><br />
				   <a href='$root/registration/resetpass.php?id=$id&code=$code'>click here to reset your password</a>
				   <br /><br />
				   thank you, <br /><br />
                   Your Bus tracker team
				   ";
		$subject = "Password Reset";
		
		$user->send_mail($email,$message,$subject);
		
		$msg = "<div class='alert alert-success'>
					<button class='close' data-dismiss='alert'>&times;</button>
					We've sent an email to $email.
                    Please click on the password reset link in the email to generate new password. 
			  	</div>";
	}
	else
	{
		$msg = "<div class='alert alert-danger'>
					<button class='close' data-dismiss='alert'>&times;</button>
					<strong>Sorry!</strong>  this email not found. 
			    </div>";
	}
}
?>

<!DOCTYPE html>
<html>
  <head>
    <title>Forgot Password</title>
    <!-- Bootstrap -->
    <link href="../../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="../../../../vendor/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
    <link href="../../../dist/assets/styles.css" rel="stylesheet" media="screen">
     <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
      <script>
        function goBack() {
            window.history.back();
        }
      </script>
  </head>
  <body id="login">
    <div class="container">

      <form class="form-signin" method="post">
        <h2 class="form-signin-heading">Forgot Password</h2><hr />
        
        	<?php
			if(isset($msg))
			{
				echo $msg;
			}
			else
			{
				?>
              	<div class='alert alert-info'>
				Please enter your email address. You will receive a link to create a new password via email.!
				</div>  
                <?php
			}
			?>
        
        <input type="email" class="input-block-level" placeholder="Email address" name="txtemail" required />
     	<hr />
       
        <button class="btn btn-danger btn-primary" type="submit" name="btn-submit">Generate new Password</button>
        <a class="btn btn-primary" onclick="goBack()" style="float: right;">back</a>
      </form>

    </div> <!-- /container -->
    <script src="../../../vendor/jquery/jquery-1.9.1.min.js"></script>
    <script src="../../../vendor/bootstrap/js/bootstrap.min.js"></script>
  </body>
</html>
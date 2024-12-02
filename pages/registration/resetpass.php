<?php
require_once 'class.user.php';
$user = new USER();
include '../root.php';

if(empty($_GET['id']) && empty($_GET['code']))
{
	$user->redirect('index.php');
}

if(isset($_GET['id']) && isset($_GET['code']))
{
	$id = base64_decode($_GET['id']);
	$code = $_GET['code'];
	
	$stmt = $user->runQuery("SELECT * FROM admin WHERE AD_Id=:uid AND AD_Token=:token");
	$stmt->execute(array(":uid"=>$id,":token"=>$code));
	$rows = $stmt->fetch(PDO::FETCH_ASSOC);
	
	if($stmt->rowCount() == 1)
	{
		if(isset($_POST['btn-reset-pass']))
		{
            function check_input($data) {
            $data = trim($data);
            $data = stripslashes($data);
            $data = htmlspecialchars($data);
            return $data;
            }
            
			$pass = check_input($_POST['pass']);
			$cpass = check_input($_POST['confirm-pass']);
			
			if($cpass!==$pass)
			{
				$msg = "<div class='alert alert-block'>
						<button class='close' data-dismiss='alert'>&times;</button>
						<strong>Sorry!</strong>  Password Doesn't match. 
						</div>";
			}
			else
			{
				$password = md5($cpass);
				$stmt = $user->runQuery("UPDATE admin SET AD_Password=:upass WHERE AD_Id=:uid");
				$stmt->execute(array(":upass"=>$password,":uid"=>$rows['AD_Id']));
				
				$msg = "<div class='alert alert-success'>
						<button class='close' data-dismiss='alert'>&times;</button>
						Password Changed.
						</div>";
                
                 //set into action table
            $action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'CHANGE PASSWORD', '".$url."', '[admin ID : ".$id."]', CURRENT_TIMESTAMP)";

            $results = $con->query($action);
                
				header("refresh:1;$root");
			}
		}	
	}
	else
	{
		$msg = "<div class='alert alert-success'>
				<button class='close' data-dismiss='alert'>&times;</button>
				No Account Found, Try again
				</div>";
	}
}

?>
<!DOCTYPE html>
<html>
  <head>
    <title>Password Reset</title>
    <!-- Bootstrap -->
    <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="../../vendor/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
    <link href="../../dist/assets/styles.css" rel="stylesheet" media="screen">
     <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
  </head>
  <body id="login">
    <div class="container">
    	<div class='alert alert-success'>
			<strong>Hello !</strong>  <?php echo $rows['AD_Name'] ?> you are here to reset your forgetton password.
		</div>
        <form class="form-signin" method="post">
        <h3 class="form-signin-heading">Password Reset.</h3><hr />
        <?php
        if(isset($msg))
		{
			echo $msg;
		}
		?>
        <input type="password" class="input-block-level" placeholder="New Password" name="pass" required />
        <input type="password" class="input-block-level" placeholder="Confirm New Password" name="confirm-pass" required />
     	<hr />
        <button class="btn btn-large btn-primary" type="submit" name="btn-reset-pass">Reset Your Password</button>
        
      </form>

    </div> <!-- /container -->
   <script src="../../vendor/jquery/jquery-1.9.1.min.js"></script>
    <script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>
  </body>
</html>
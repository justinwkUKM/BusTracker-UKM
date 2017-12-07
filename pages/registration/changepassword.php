<?php
session_start();
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
	$id_sendemail = $_GET['id'];
	$code = $_GET['code'];
    $email = $_SESSION['email'];
    $name = $_SESSION['AD_Name'];
    
	
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
				$msg = "<div class='alert alert-danger'>
						<button class='close' data-dismiss='alert'>&times;</button>
						<strong>Sorry!</strong>  Password Doesn't match. 
						</div>";
			}
			else
			{
				$password = md5($cpass);
				$stmt = $user->runQuery("UPDATE admin SET AD_Password=:upass WHERE AD_Id=:uid");
				$stmt->execute(array(":upass"=>$password,":uid"=>$rows['AD_Id']));
				
                $message= "
				   Hello, $name
				   <br /><br />
				   The password for the Bus Tracker account was just changed.
                   If this was you, then you can safely ignore this email.
                   If this wasn't you, your account has been compromised. Please follow these steps:
				   <br /><br />
				   Click Following Link To Reset Your Password 
				   <br /><br />
				   <a href='$root/registration/resetpass.php?id= $id_sendemail&code=$code'>click here to reset your password</a>
				   <br /><br />
				   thank you, <br /><br />
                   Your Bus tracker team
				   ";
		$subject = "Password Reset";
		
		$user->send_mail($email,$message,$subject);
                
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
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Change Password</title>

    <!-- Bootstrap Core CSS -->
    <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../../vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="../../vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="../../vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../../dist/css/sb-admin-2.min.css" rel="stylesheet">
    <link href="../../dist/css/styleheader.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../../vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    

</head>

<body id="login">

    <div id="wrapper">

        <!-- Navigation -->
        <?php include '../layout/nav-bar.php' ?>

        <div id="page-wrapper">
           
            <!--            breadcrumb-->
            <div class="row">
                <div class="col-lg-12">
                   
                        <div class="panel-body">
                            <div class="row">
                                
                                <ul class="breadcrumb">
                                <li><a href="<?php echo $root ?>/"><i class="glyphicon glyphicon-home"></i> Home</a> <span class="divider"></span></li>               
                                <li class="active">Password Reset</li>        
                                </ul>
            
                        </div>
                        <!-- /.row -->
                    </div>
                    <!-- /.panel-body -->
                    </div>
             <!-- /.col-lg-12 -->
            </div>
 <!--           // breadcrumb-->
            
  <!--            Routes Table-->
          <div class="row">
          <div class="col-sm-12">
          
           
<!--
    	<div class='alert alert-success'>
			<strong>Hello !</strong>  <?php echo $rows['AD_Name'] ?> you are here to reset your forgetton password.
		</div>
-->
        <form class="form-signin form-horizontal" method="post">
        <h3 class="form-signin-heading">Password Reset</h3><hr />
        <?php
        if(isset($msg))
		{
			echo $msg;
		}
		?>
<!--
        <div class="form-group">
            <div class="col-sm-2">
              <input name="old-pass" type="password" class="form-control" placeholder="Old Password" required>
            </div>
        </div>
-->
        <div class="form-group">
            <div class="col-sm-2">
              <input name="pass" type="password" class="form-control" placeholder="New Password" required>
            </div><a href="fpass.php">Forgot password</a>
        </div>
          <div class="form-group">
            <div class="col-sm-2">
              <input name="confirm-pass" type="password" class="form-control" placeholder="Confirm New Password" required>
            </div>
        </div>
        
     	<hr />
        <div class="form-group">
            <div class="col-sm-2">
              <button class="btn btn-large btn-primary" type="submit" name="btn-reset-pass">Reset Your Password</button>
            </div>
        </div>
      </form>
              
      <!--     end table-->
              </div>
      <!--        col-md-12-->
              </div>   
        <!-- /#page-wrapper -->
        </div>

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="../../vendor/jquery/jquery-1.12-0.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../../vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../../dist/js/sb-admin-2.js"></script>
    

</body>

</html>
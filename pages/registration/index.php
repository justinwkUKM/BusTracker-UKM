<?php
session_start();
require_once 'class.user.php';
include_once '../connection/dbconnect.php';

//include server
include '../root.php';

$url = $_SERVER['SERVER_NAME'].$_SERVER['REQUEST_URI'];

//validate admin type
if($_SESSION['AD_Type'] == 'Officer' || 'Superadmin'){
//    echo "in";
} else {
//    echo "out";
    header("Location: ../alert.php");
}

$reg_user = new USER();

if($reg_user->is_logged_in()!="")
{
	$reg_user->redirect('home.php');
}


if(isset($_POST['btn-signup']))
{
    function check_input($data) {
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
    }
    
	$fname = check_input($_POST['fname']);
	$uname = check_input($_POST['txtuname']);
	$email = check_input($_POST['txtemail']);
	$upass = check_input($_POST['txtpass']);
	$code = md5(uniqid(rand()));
	
	$stmt = $reg_user->runQuery("SELECT * FROM admin WHERE AD_Email=:email_id");
	$stmt->execute(array(":email_id"=>$email));
	$row = $stmt->fetch(PDO::FETCH_ASSOC);
	
	if($stmt->rowCount() > 0)
	{
		$msg = "
		      <div class='alert alert-warning'>
				<button class='close' data-dismiss='alert'>&times;</button>
					<strong>Sorry !</strong>  email allready exists , Please Try another one
			  </div>
			  ";
	}
	else
	{
		if($reg_user->register($fname,$uname,$email,$upass,$code))
		{			
			$id = $reg_user->lasdID();		
			$key = base64_encode($id);
			$id = $key;
			
			$message = "					
						Hello $uname,
						<br /><br />
						Welcome to UKM Bus Tracker!<br/>
						To complete your registration  please click following link<br/>
						<br /><br />
						<a href='$root/registration/verify.php?id=$id&code=$code'>Click HERE to Activate</a>
						<br /><br />
						thank you, <br /><br />
                        Your Bus tracker team";
						
			$subject = "Confirm Registration";
						
			$reg_user->send_mail($email,$message,$subject);	
			$msg = "
					<div class='alert alert-success'>
						<button class='close' data-dismiss='alert'>&times;</button>
						<strong>Success!</strong>  We've sent an email to $email.
                    Please wait and check your email, click on the confirmation link in the email to create your account. 
			  		</div>
					";
            
             //set into action table
            $action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'ADD', '".$url."', '[name : ".$fname."] [username : ".$uname."] [email : ".$email."]', CURRENT_TIMESTAMP)";

            $results = $con->query($action);
		}
		else
		{
			echo "sorry , Query could no execute...";
		}		
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

    <title>Signup</title>

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
                                <li><a href="<?php echo $root ?>/manageadmins/"><i class="glyphicon glyphicon-th-list"></i> Manage Admins</a> <span class="divider"></span></li>             
                                <li class="active">Registration</li>        
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
          <div class="col-md-12">
          
           <?php if(isset($msg)) echo $msg;  ?>
              <form class="form-signin form-horizontal" method="post">
                <h2 class="form-signin-heading">Registration</h2><hr />
                <div class="form-group">
                    <div class="col-sm-6">
                      <input name="fname" type="text" class="form-control" id="adminname" placeholder="Full Name" required>
                    </div>
                </div>
                  <div class="form-group">
                    <div class="col-sm-6">
                      <input name="txtuname" type="text" class="form-control" id="adminname" placeholder="Username" required>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-6">
                      <input name="txtemail" type="email" class="form-control" id="phonenumber" placeholder="Email address" required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-6">
                      <input name="txtpass" type="password" class="form-control" id="emailaddress" placeholder="Password" required>
                    </div>
                </div>
                
                <button class="btn btn-large btn-primary" type="submit" name="btn-signup">Sign Up</button>
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
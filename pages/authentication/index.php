<?php
session_start();

if(isset($_SESSION['AD_Id'])!="") {
    header("Location: /pages/");
}

$url = $_SERVER['SERVER_NAME'].$_SERVER['REQUEST_URI'];

date_default_timezone_set('Asia/Kuala_Lumpur');
$now = new DateTime();
$now = $now->format('y/m/d H:i:s');
//echo $now;

include_once '../connection/dbconnect.php';

//check if form is submitted
if (isset($_POST['login'])) {

    $username = mysqli_real_escape_string($con, $_POST['username']);
    $password = mysqli_real_escape_string($con, $_POST['password']);
    $result = mysqli_query($con, "SELECT * FROM admin WHERE AD_Username = '" . $username. "' and AD_Password = '" . md5($password) . "'");
   

    if ($row = mysqli_fetch_array($result)) {
        $_SESSION['AD_Id'] = $row['AD_Id'];
        $_SESSION['AD_Username'] = $row['AD_Username'];
        $_SESSION['AD_Name'] = $row['AD_Name'];
        $_SESSION['AD_Avatar'] = $row['AD_Avatar'];
        $_SESSION['AD_Type'] = $row['AD_Type'];
        $_SESSION['AD_Token'] = $row['AD_Token'];
        $_SESSION['AD_Token'] = $row['AD_Token'];
        $_SESSION['email'] = $row['AD_Email'];
        
        $result = mysqli_query($con, "UPDATE  `bus_tracker_ukm`.`admin` SET  `AD_Last_Login` =  '".$now."' WHERE  `admin`.`AD_Id` ='".$_SESSION['AD_Id']."';");
        
//        echo "UPDATE  `bus_tracker_ukm`.`admin` SET  `AD_Last_Login` =  '".$now."' WHERE  `admin`.`AD_Id` ='".$_SESSION['AD_Id']."';";
        
    //set into action table
        $action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'LOGIN', '".$url."', '[name : ".$_SESSION['AD_Name']."]', CURRENT_TIMESTAMP)";

        $results = $con->query($action);
        
        
        header("Location: http://localhost/ukm-cms/pages");
    } else {
        $errormsg = "Incorrect Username or Password!!!";
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

    <title>Login</title>

    <!-- Bootstrap Core CSS -->
    <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../../vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../../dist/css/sb-admin-2.min.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../../vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
        body{
            font-family: 'Roboto',sans-serif;
        }
        #auth{
            background: #d70000;
            padding: 24px 16px;
        }
        #auth .panel-title{
            color: white;
            font-size: 24px;
            font-weight: 200;
            text-align: center;
        }
        .btn-block {
            display: block;
            width: 100%;
            background: #3F51B5;
            color: #fff;
            border-radius: 0;
            border: 1px #3F51B5 solid;
            transition: 500ms  background ease;
        }
        .btn-block:hover, .btn-block:focus{
            color: #3F51B5;
            background: white;
            border: 1px #3F51B5 solid;
        }
        .btn-block:active{
            background: transparent;
        }
        .form-control{
            border-radius: 0;
            background: #eee;
            font-size:16px;
            border: 0;
            padding: 24px 16px;
            box-shadow: none;
            text-align: center;
        }
        .panel-default{
            border: 0;
            box-shadow: 0px 2px 0px 0px rgba(0,0,0,0.1);
        }
        .ukm-logo{
            max-width: 224px;
            width: 100%;
            margin: 16px auto;
            display: block;
            margin-top: 90px;
        }
        .login-panel{
            margin-top: 24px;
        }
    </style>
</head>

<body>

    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <img src="http://www.ftsm.ukm.my/ftsmhandbook/img/UKM.png" alt="" class="ukm-logo">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading" id="auth">
                        <h3 class="panel-title">Login</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form "method="post" action="<?php echo $_SERVER['PHP_SELF'] ?>" name="loginform">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Username" name="username" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value="">
                                </div>
                                <?php if (isset($errormsg)){ ?><div>
                                    <label class="text-danger">
<!--									<input id="optionsCheckbox" type="checkbox" value="option1"> Remember me-->
                                        <?php if (isset($errormsg)) { echo $errormsg; } ?>
								</label>
                                </div><?php }?>
                            
                                <!-- Change this to a button or input when using this as a form -->
<!--                                <a href="index.html" class="btn btn-lg btn-success btn-block">Login</a>-->
                            <a href="../registration/fpass.php/">Lost your Password ? </a>
                                
                            </fieldset>
                            <hr /> 
                              <div class="form-actions">
                            <button class="btn btn-lg btn-success btn-block"  name="login" value="Login" type="submit"><span class="awe-signin"></span> Log in</button>
                        </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

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

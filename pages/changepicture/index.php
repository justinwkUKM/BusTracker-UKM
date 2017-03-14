<?php
session_start();
include_once '../connection/dbconnect.php';
$url = $_SERVER['SERVER_NAME'].$_SERVER['REQUEST_URI'];

//include server
include '../root.php';

error_reporting( ~E_NOTICE );
	
	require_once 'dbconfig.php';
	
	$id = $_SESSION['AD_Id'];
		$stmt_edit = $DB_con->prepare('SELECT AD_Username, AD_Type, AD_Avatar FROM admin WHERE AD_Id =:uid');
		$stmt_edit->execute(array(':uid'=>$id));
		$edit_row = $stmt_edit->fetch(PDO::FETCH_ASSOC);
		extract($edit_row);

	
	if(isset($_POST['btn_save_updates']))
	{
//		$username = $_POST['AD_Username'];// user name
//		$userjob = $_POST['AD_Type'];// user email
			
		$imgFile = $_FILES['user_image']['name'];
		$tmp_dir = $_FILES['user_image']['tmp_name'];
		$imgSize = $_FILES['user_image']['size'];
					
		if($imgFile)
		{
			$upload_dir = '../img/admin/'; // upload directory	
			$imgExt = strtolower(pathinfo($imgFile,PATHINFO_EXTENSION)); // get image extension
			$valid_extensions = array('jpeg', 'jpg', 'png', 'gif'); // valid extensions
			$userpic = rand(1000,1000000).".".$imgExt;
			if(in_array($imgExt, $valid_extensions))
			{			
				if($imgSize < 5000000)
				{
					unlink($upload_dir.$edit_row['AD_Avatar']);
					move_uploaded_file($tmp_dir,$upload_dir.$userpic);
				}
				else
				{
					$errMSG = "Sorry, your file is too large it should be less then 5MB";
				}
			}
			else
			{
				$errMSG = "Sorry, only JPG, JPEG, PNG & GIF files are allowed.";		
			}	
		}
		else
		{
			// if no image selected the old image remain as it is.
			$userpic = $edit_row['AD_Avatar']; // old image from database
		}	
						
		
		// if no error occured, continue ....
		if(!isset($errMSG))
		{
			$stmt = $DB_con->prepare('UPDATE admin 
									     SET AD_Avatar=:upic 
								       WHERE AD_Id=:uid');
			$stmt->bindParam(':upic',$userpic);
			$stmt->bindParam(':uid',$id);
            
            //set into action table
            $action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'UPDATE', '".$url."', '[change picture : ".$userpic."] ', CURRENT_TIMESTAMP)";

            $results = $con->query($action);
				
			if($stmt->execute()){
				?>
                <script>
				alert('Successfully Updated ...');
				window.location.href='index.php';
				</script>
                <?php
                $_SESSION['AD_Avatar'] = $userpic;
			}
			else{
				$errMSG = "Sorry Data Could Not Updated !";
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

    <title>Edit</title>

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

<style>
    .table>tbody>tr>td{border: 0px;}
    
</style>
</head>

<body>

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
                                <li><a href="<?php echo $root ?>/"><i class="glyphicon glyphicon-home"></i> Home</a> <span class="divider"></span></li> <li class="active">Change Picture</li>       
                                </ul>
            
                        </div>
                        <!-- /.row -->
                    </div>
                    <!-- /.panel-body -->
                    </div>
             <!-- /.col-lg-12 -->
            </div>
 <!--           // breadcrumb-->
           
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Change Picture</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
            <!--            edit user-->
 <div class="container">
<div class="row">
    <div class="col-xs-12 col-sm-12 col-md-10">
        <div class="well well-sm">
            <div class="row">
                <div class="col-md-8">
                    <div class="col-md-8">
                        <form method="post" enctype="multipart/form-data" class="form-horizontal">
                    <?php
                    if(isset($errMSG)){
                    ?>
                    <div class="alert alert-danger">
                      <span class="glyphicon glyphicon-info-sign"></span> &nbsp; <?php echo $errMSG; ?>
                    </div>
                    <?php
                    }
                    ?>
                         <table class="table table-responsive">
                        <tr>

                        <td>
                            <p><img src="../img/admin/<?php echo $_SESSION['AD_Avatar']; ?>" height="250" width="250" /></p>
                            <input class="input-group" type="file" name="user_image" accept="image/*" />
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2"><button type="submit" name="btn_save_updates" class="btn btn-success">
                        <span class="glyphicon glyphicon-save"></span> Update
                        </button>

                        <a class="btn btn-danger" href="index.php"> <span class="glyphicon glyphicon-backward"></span> cancel </a>

                        </td>
                    </tr>
                    
                    </table>

                </form>
                 <p><i class="glyphicon glyphicon-info-sign"></i> Click "Choose file" to search picture from your computer</p>
                <p><i class="glyphicon glyphicon-info-sign"></i> Click "Update" to change the picture</p>

                  </div>
                </div>
<!--                        right col-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--         end edit user-->

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

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    
</body>

</html>

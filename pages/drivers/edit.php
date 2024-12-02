<?php
session_start();
include_once '../connection/dbconnect.php';
include 'edit_crud.php';

//include server
include '../root.php';

error_reporting( ~E_NOTICE );
	
	require_once '../changepicture/dbconfig.php';
	
    $id = $_GET['edit'];
    $stmt_edit = $DB_con->prepare('SELECT DI_Name, DI_Phone, DI_Avatar FROM driver_info WHERE DI_Id =:uid');
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
			$upload_dir = '../img/driver/'; // upload directory	
			$imgExt = strtolower(pathinfo($imgFile,PATHINFO_EXTENSION)); // get image extension
			$valid_extensions = array('jpeg', 'jpg', 'png', 'gif'); // valid extensions
			$userpic = rand(1000,1000000).".".$imgExt;
			if(in_array($imgExt, $valid_extensions))
			{			
				if($imgSize < 5000000)
				{
					unlink($upload_dir.$edit_row['DI_Avatar']);
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
			echo $userpic = $edit_row['DI_Avatar']; // old image from database
		}	
        
		// if no error occured, continue ....
		if(!isset($errMSG))
		{
			$stmt = $DB_con->prepare('UPDATE `driver_info` SET `DI_Avatar` = :upic WHERE `driver_info`.`DI_Id` = :uid;');
			$stmt->bindParam(':upic',$userpic);
			$stmt->bindParam(':uid',$id);
            
            //set into action table
            $action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'UPDATE', '".$url."', '[change picture : ".$userpic."] ', CURRENT_TIMESTAMP)";

            $results = $con->query($action);
				
			if($stmt->execute()){
				?>
                <script>
				alert('Successfully Updated ...');
				window.location.href='edit.php?edit=<?php echo $_GET['edit']; ?>';
				</script>
                <?php
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
                                <li><a href="<?php echo $root ?>/"><i class="glyphicon glyphicon-home"></i> Home</a> <span class="divider"></span></li> 
                                <li><a href="<?php echo $root ?>/drivers/"><i class="fa fa-users"></i> Drivers</a> <span class="divider"></span></li>            
                                <li class="active">Edit</li>        
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
                    <h1 class="page-header">Drivers</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
          <div class="container">
                <div class="row">
                    <div class="col-md-4 well well-sm text-center">
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
<!--change picture-->
                        <td>
                            <p><img src="../img/driver/user-placeholder.jpg" height="250" width="250" /></p>
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
<!--                    edit information-->
                    <div class="col-md-8">
                      <div class="panel panel-default" style="padding: 40px;">
                      <form action="edit.php?edit=<?php echo $_GET['edit']; ?>" method="post" class="form-horizontal">
                     <div class="form-group">
                        <label for="driverName" class="col-sm-3 control-label">Driver Name</label>
                        <div class="col-sm-9">
                          <input name="driverName" type="text" class="form-control" id="driverName" placeholder="Driver Name" value="<?php if(isset($_GET['edit'])) echo $editrow['DI_Name']; ?>" required>
                        </div>
                    </div>
                     <div class="form-group">
                          <label for="Gender" class="col-sm-3 control-label">Gender</label>
                          <div class="col-sm-9">
                          <div class="radio">
                            <label>
                            <input name="gender" type="radio" id="gender" value="male" <?php if(isset($_GET['edit'])) if($editrow['DI_Gender']=="male") echo "checked"; ?> required> Male
                            </label>
                          </div>
                          <div class="radio">
                            <label>
                               <input name="gender" type="radio" id="gender" value="female" <?php if(isset($_GET['edit'])) if($editrow['DI_Gender']=="female") echo "checked"; ?>> Female
                            </label>
                            </div>
                          </div>
                    </div>
                      <div class="form-group">
                        <label for="phoneNum" class="col-sm-3 control-label">Phone Number</label>
                        <div class="col-sm-9">
                          <input name="phoneNum" type="text" class="form-control" id="phoneNum" placeholder="Phone Number" value="<?php if(isset($_GET['edit'])) echo $editrow['DI_Phone']; ?>" required>
                        </div>
                    </div>
                    <div class="form-group">
                          <label for="status" class="col-sm-3 control-label">Status</label>
                          <div class="col-sm-9">
                          <div class="radio">
                            <label>
                            <input name="status" type="radio" id="status" value="1" <?php if(isset($_GET['edit'])) if($editrow['DI_Active']=="1") echo "checked"; ?> required> Active
                            </label>
                          </div>
                          <div class="radio">
                            <label>
                               <input name="status" type="radio" id="status" value="0" <?php if(isset($_GET['edit'])) if($editrow['DI_Active']=="0") echo "checked"; ?>> Inactive
                            </label>
                            </div>
                          </div>
                    </div>

                    <div class="form-group">
                        <label for="email" class="col-sm-3 control-label">Email</label>
                        <div class="col-sm-9">
                          <input name="email" type="email" class="form-control" id="email" placeholder="Email" value="<?php if(isset($_GET['edit'])) echo $editrow['DI_Email']; ?>" required>
                        </div>
                    </div>
                     <div class="form-group">
                        <label for="address" class="col-sm-3 control-label">Address</label>
                        <div class="col-sm-9">
                          <input name="address" type="text" class="form-control" id="address" placeholder="Address" value="<?php if(isset($_GET['edit'])) echo $editrow['DI_Address']; ?>" required>
                        </div>
                    </div>

                    <div class="form-group">
                          <div class="col-sm-offset-3 col-sm-9">
                          <?php if (isset($_GET['edit'])) { ?>
                          <input type="hidden" name="oldpid" value="<?php echo $editrow['DI_Id']; ?>">
                          <button class="btn btn-default" type="submit" name="update"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Update</button>
                          <?php } else { ?>
                          <button class="btn btn-default" type="submit" name="create"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Create</button>
                          <?php } ?>
                        </div>
                      </div>

                </form>
                  </div>
                </div>
            </div>
        </div>
            
      
         <!-- /.row -->
        <!--         //Manage Roles table-->
            </div>
            <!-- /#page-wrapper -->

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

<?php
session_start();
include_once '../connection/dbconnect.php';
include 'edit_crud.php';

//validate admin type
if($_GET['edit'] == $_SESSION['AD_Id']){
//    echo "in";
} else {
//    echo "out";
    header("Location: ../alert.php");
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
                                <li><a href="<?php echo $root ?>/"><i class="glyphicon glyphicon-home"></i> Home</a> <span class="divider"></span></li> 
                                <li><a href="<?php echo $root ?>/profile/"><i class="fa fa-user fa-fw"></i> Profile</a> <span class="divider"></span></li>            
                                <li class="active">Setting</li>        
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
                    <h1 class="page-header">Setting</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
            <!--            edit user-->
 <div class="container">
<div class="row">
    <div class="col-xs-12 col-sm-12 col-md-10">
        <div class="well well-sm">
            <div class="row">
              
<!--                left col-->
                <div class="col-sm-6 col-md-6">
                    <form action="edit.php" method="post" class="form-horizontal">
    
                     <div class="form-group">
                        <label for="sname" class="col-sm-3 control-label">Admin Name</label>
                        <div class="col-sm-9">
                          <input name="sname" type="text" class="form-control" id="adminname" placeholder="Admin Name" value="<?php if(isset($_GET['edit'])) echo $editrow['AD_Name']; ?>" required>
                        </div>
                    </div>
                      <div class="form-group">
                        <label for="uname" class="col-sm-3 control-label">Username</label>
                        <div class="col-sm-9">
                          <input name="uname" type="text" class="form-control" id="adminname" placeholder="Admin Username" value="<?php if(isset($_GET['edit'])) echo $editrow['AD_Username']; ?>" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="phone" class="col-sm-3 control-label">Phone Number</label>
                        <div class="col-sm-9">
                          <input name="phone" type="tel" class="form-control" id="phonenumber" placeholder="Phone Number" value="<?php if(isset($_GET['edit'])) echo $editrow['AD_Phone']; ?>" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email" class="col-sm-3 control-label">Email Address</label>
                        <div class="col-sm-9">
                          <input name="email" type="email" class="form-control" id="emailaddress" placeholder="Email Address" value="<?php if(isset($_GET['edit'])) echo $editrow['AD_Email']; ?>" required>
                        </div>
                    </div>
                    <div class="form-group">
                          <div class="col-sm-offset-3 col-sm-9">
                      <?php if (isset($_GET['edit'])) { ?>
                      <input type="hidden" name="oldsid" value="<?php echo $editrow['AD_Id']; ?>">
                          <button class="btn btn-default" type="submit" name="update"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Update</button>
                          <?php } ?>
                      </div>
                      </div>
                    </form>
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

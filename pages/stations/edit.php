<?php
session_start();
include_once '../connection/dbconnect.php';
include 'edit_crud.php'
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
                                <li><a href="<?php echo $root ?>/stations/"><i class="glyphicon glyphicon-flag"></i> Stations</a> <span class="divider"></span></li>            
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
                    <h1 class="page-header">Edit Stations</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
  <form action="edit.php" method="post" class="form-horizontal">
    
       <div class="form-group">
        <label for="sname" class="col-sm-3 control-label">Station Name</label>
        <div class="col-sm-9">
          <input name="stationname" type="text" class="form-control" id="stationname" placeholder="Station Name" value="<?php if(isset($_GET['edit'])) echo $editrow['S_Name']; ?>" required>
        </div>
    </div>
      <div class="form-group">
        <label for="code" class="col-sm-3 control-label">Station Code</label>
        <div class="col-sm-9">
          <input name="code" type="text" class="form-control" id="code" placeholder="Station Code" value="<?php if(isset($_GET['edit'])) echo $editrow['S_Code']; ?>" required>
        </div>
    </div>

    <div class="form-group">
        <label for="lat" class="col-sm-3 control-label">Load Level</label>
        <div class="col-sm-9">
          <input name="load" type="number" class="form-control" id="load" placeholder="Load Level" value="<?php if(isset($_GET['edit'])) echo $editrow['S_Loadlevel']; ?>" required>
        </div>
    </div>    
    <div class="form-group">
          <label for="status" class="col-sm-3 control-label">Status</label>
          <div class="col-sm-9">
          <div class="radio">
            <label>
            <input name="status" type="radio" id="status" value="1" <?php if(isset($_GET['edit'])) if($editrow['S_Active']=="1") echo "checked"; ?> required> Active
            </label>
          </div>
          <div class="radio">
            <label>
               <input name="status" type="radio" id="status" value="0" <?php if(isset($_GET['edit'])) if($editrow['S_Active']=="0") echo "checked"; ?>> Inactive
            </label>
            </div>
          </div>
    </div>
    <div class="form-group">
        <label for="status" class="col-sm-3 control-label">Description</label>
        <div class="col-sm-9">
          <input name="description" type="text" class="form-control" id="description" placeholder="Description" value="<?php if(isset($_GET['edit'])) echo $editrow['S_Description']; ?>">
        </div>
    </div>
    
    <div class="form-group">
          <div class="col-sm-offset-3 col-sm-9">
      <?php if (isset($_GET['edit'])) { ?>
      <input type="hidden" name="oldsid" value="<?php echo $editrow['S_Id']; ?>">
          <button class="btn btn-default" type="submit" name="update"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Update</button>
          <?php } ?>
      </div>
      </div>
</form>
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

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    

</body>

</html>

<?php
session_start();
include_once '../connection/dbconnect.php';
include 'edit_crud.php';

//include server
include '../root.php';

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
                                <li><a href="<?php echo $root ?>/buses/"><i class="glyphicon glyphicon-bed"></i> Buses</a> <span class="divider"></span></li>            
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
                    <h1 class="page-header">Edit Bus</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
  <form action="edit.php" method="post" class="form-horizontal">
    
     <div class="form-group">
          <label for="driverId" class="col-sm-3 control-label">Driver Name</label>
              <div class="col-sm-9">
                 <select name="driverId" class="form-control" id="driverId" required>
                    <option value="">Please select</option>
                
                    <?php 
                    $sql = "SELECT * FROM `driver_info` ORDER BY `driver_info`.`DI_Id` ASC";
                      $result = $con->query($sql);
                      while ($readrow = $result->fetch_array()) {
                      ?>   
                        <option value="<?php echo $readrow['DI_Name']; ?>" <?php if(isset($_GET['edit'])) if($editrow['RB_Driver_Id']=="$driver") echo "selected"; ?>><?php echo $readrow['DI_Name']; ?></option>

                      <?php } ?>   
                  </select>
            </div>
        </div>  
    
    <div class="form-group">
     <label for="zon" class="col-sm-3 control-label">Bus Type</label>
      <div class="col-sm-9">
     <select name="zon" class="form-control" id="type" required>
        <?php 
            $sql = "SELECT * FROM `bus_code` WHERE `BC_Type` = 'bas'";
              $result = $con->query($sql);
              while ($readrow = $result->fetch_array()) {
              ?>   
                <option value="<?php echo $readrow['BC_Code']; ?>" <?php if(isset($_GET['edit'])) if($readrow['BC_Code']== "$zonname") echo "selected"; ?>><?php echo $readrow['BC_Code'] ?></option>

              <?php } ?>  
      </select>
    </div>
    </div> 

    <div class="form-group">
        <label for="plateNum" class="col-sm-3 control-label">Plate Number</label>
        <div class="col-sm-9">
          <input name="plateNum" type="text" class="form-control" id="plateNum" placeholder="Plate Number" value="<?php if(isset($_GET['edit'])) echo $editrow['RB_Plate_Number']; ?>" required>
        </div>
    </div>
     <div class="form-group">
        <label for="deviceId" class="col-sm-3 control-label">Device Id</label>
        <div class="col-sm-9">
          <input name="deviceId" type="text" class="form-control" id="deviceId" placeholder="Device Id" value="<?php if(isset($_GET['edit'])) echo $editrow['RB_Device_Id']; ?>" required>
        </div>
    </div>
    
    <div class="form-group">
          <div class="col-sm-offset-3 col-sm-9">
      <?php if (isset($_GET['edit'])) { ?>
      <input type="hidden" name="oldsid" value="<?php echo $editrow['RB_Id']; ?>">
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

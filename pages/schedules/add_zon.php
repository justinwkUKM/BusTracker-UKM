<?php
session_start();
include_once '../connection/dbconnect.php';
include 'zon_crud.php'
?>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Add Zone</title>

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
                                <li><a href="<?php echo $root ?>/schedules/"><i class="glyphicon glyphicon-calendar"></i> schedules</a> <span class="divider"></span></li>            
                                <li class="active">Add Zone</li>        
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
                    <h1 class="page-header">Add Zone</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!--         // Add Zon-->
            
                  <form action="add_zon.php" method="post" class="form-horizontal">
                     
                              <div class="form-group">
                                <label for="code" class="col-sm-3 control-label">Bus Code</label>
                                <div class="col-sm-9">
                                  <input name="code" type="text" class="form-control" id="code" placeholder="Bus Code" value="" required>
                                </div>
                            </div>
                           
                            <div class="form-group">
                                 <label for="type" class="col-sm-3 control-label">Type</label>
                                  <div class="col-sm-9">
                                 <select name="type" class="form-control" id="type" required>
                                   <option value="">Please select type</option>
                                    <?php 
                                    $sql = "SELECT * FROM `bus_code` WHERE `BC_Type` = 'bas'";
                                      $result = $con->query($sql);
                                      while ($readrow = $result->fetch_array()) {
                                      ?>   
                                        <option value="<?php echo $readrow['BC_Code']; ?>" <?php if(isset($_GET['edit'])) if($editrow['Z_Type']=="BC_Code") echo "selected"; ?>><?php echo $readrow['BC_Code']; ?></option>

                                      <?php } ?>  
                                  </select>
                                </div>
                            </div>                      
                      
                     <div class="form-group">
                      <div class="col-sm-offset-3 col-sm-9">
                      <?php if (isset($_GET['edit'])) { ?>
                      <input type="hidden" name="oldsid" value="<?php echo $editrow['Z_Id']; ?>">
                      <button class="btn btn-default" type="submit" name="update"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Update</button>
                      <?php } else { ?>
                      <button class="btn btn-default" type="submit" name="create"><span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> save</button>
                      <?php } ?>
                      <button class="btn btn-default" type="reset"><span class="glyphicon glyphicon-erase" aria-hidden="true"></span> Clear</button>
                    </div>
                  </div>
                      
                </form>
     <!-- /.row -->
            
    <!--         // Add zon-->
       
        <!--            Zon Table-->
  
        <br />
       <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-zon">
             <thead>         
                <tr>
                  <th style="width: 5%">Zon Id</th>
                  <th style="width: 20%">Name</th>
                  <th style="width: 20%">Type</th>
                  <th style="width: 30%">Action</th>
                </tr>
             </thead>
              <tbody>
                   <?php
                  $sql = "SELECT * FROM `zon_log` ORDER BY `Z_Type` DESC";
                  $result = $con->query($sql);
                  while ($readrow = $result->fetch_array()) {
                  ?>   
             <tr>
                <td><?php echo $readrow['Z_Id']; ?></td>
                <td><?php echo $readrow['Z_Name']; ?></td>
                <td><?php echo $readrow['Z_Type']; ?></td>
                <td >
                  <a href="edit_zon.php?edit=<?php echo $readrow['Z_Id']; ?>" class="btn btn-link btn-xs" role="button"><i class='glyphicon glyphicon-edit'></i> Edit </a>
                  <a href="add_zon.php?delete=<?php echo $readrow['Z_Id']; ?>" onclick="return confirm('Are you sure to delete?');" class="btn btn-link btn-xs" role="button"><i class='glyphicon glyphicon-trash'></i> Delete</a>
                </td>
             </tr>
              <?php } ?>
                </tbody>
             </table>
<!--                            end table-->
       
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

    <!-- DataTables JavaScript -->
    <script src="../../vendor/datatables/js/jquery.dataTables.min.js"></script>
    <script src="../../vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
    <script src="../../vendor/datatables-responsive/dataTables.responsive.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../../dist/js/sb-admin-2.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
     <script>
    $(document).ready(function() {
        $('#dataTables-zon').DataTable({
            responsive: true
        });
    });
    </script>
    
</body>

</html>

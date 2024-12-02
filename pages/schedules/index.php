<?php
session_start();
include_once '../connection/dbconnect.php';
include 'schedule_crud.php';

//include server
include '../root.php';

//Delete
if (isset($_GET['delete'])) {

$sid = $_GET['delete'];
$sql = "delete from bus_schedule where BS_Id = '".$sid."'";
$result = $con->query($sql);
   
//set into action table
 $action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'DELETE', '".$url."', 'empty', CURRENT_TIMESTAMP)";
 
$results = $con->query($action);

header("Location: $root/schedules/");
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

    <title>Schedule</title>

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
<link rel="stylesheet" type="text/css" href="../../dist/css/ukm_bus.css"> 
    
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
                                <li class="active">schedules</li>        
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
                <div class="col-md-12">
                    <table class="col-md-12">
                        <tr>
                            <th class="col-md-6"><h1>Schedule</h1></th>
                        </tr>
                    </table>
                </div>
            </div>
            
  <!--            Schedule Table-->
          <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-schedule">
             <thead>         
                <tr>
                  <th style="width: 5%">Schedule Id</th>
                  <th style="width: 15%">Session</th>
                  <th style="width: 15%">Type</th>
                  <th style="width: 15%">Day</th>
                  <th style="width: 5%">Driver</th>
                  <th style="width: 45%">Action</th>
                </tr>
             </thead>
              <tbody>
                   <?php
                  $sql = "SELECT * FROM `bus_schedule` ORDER BY `BS_Id` DESC";
                  $result = $con->query($sql);
                  while ($readrow = $result->fetch_array()) {
                  ?>   
             <tr>
               <td><?php echo $readrow['BS_Id']; ?></td>
                <td><?php echo $readrow['BS_Session']; ?></td>
                <td><?php echo $readrow['BS_Bus_Type']; ?></td>
                <td><?php echo $readrow['BS_Day']; ?></td>
                <td><?php echo $readrow['BS_Driver_Num']; ?></td>
                <td >
                  <a href="edit_schedule.php?edit=<?php echo $readrow['BS_Id']; ?>" class="btn btn-link btn-xs" role="button"><i class='glyphicon glyphicon-edit'></i> Edit </a>
                  <a href="set_schedule.php?edit=<?php echo $readrow['BS_Id']; ?>" class="btn btn-link btn-xs" role="button"><i class='glyphicon glyphicon-transfer'></i> Set Schedule </a>
                  
                  <a href="index.php?delete=<?php echo $readrow['BS_Id']; ?>" onclick="return confirm('Are you sure to delete?');" class="btn btn-link btn-xs" role="button"><i class='glyphicon glyphicon-trash'></i> Delete</a>
                </td>
             </tr>
              <?php } ?>
                </tbody>
             </table>
<!--                            end table-->
       <hr>
           
           <div class="row">
                <div class="col-lg-4 col-md-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-calendar fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div>New Schedule!</div>
                                </div>
                            </div>
                        </div>
                        <a href="add_schedule.php">
                            <div class="panel-footer">
                                <span class="pull-left">Add New</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6">
                    <div class="panel panel-green">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-th-list fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div>New Code!</div>
                                    <div></div>
                                </div>
                            </div>
                        </div>
                        <a href="add_code.php">
                            <div class="panel-footer">
                                <span class="pull-left">Add Code</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6">
                    <div class="panel panel-yellow">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-car fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div>New Zone!</div>
                                    <div></div>
                                </div>
                            </div>
                        </div>
                        <a href="add_zon.php">
                            <div class="panel-footer">
                                <span class="pull-left">Add Zone</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
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
    
     <!-- DataTables JavaScript -->
    <script src="../../vendor/datatables/js/jquery.dataTables.min.js"></script>
    <script src="../../vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
    <script src="../../vendor/datatables-responsive/dataTables.responsive.js"></script>

    <script>
    $(document).ready(function() {
        $('#dataTables-schedule').DataTable({
            responsive: true
        });
    });
    </script>
    
    <!--    session for total of row-->
  
    
    

</body>

</html>
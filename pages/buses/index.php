<?php
session_start();
include_once '../connection/dbconnect.php';
include 'edit_crud.php';

//include server
include '../root.php';

//Delete
if (isset($_GET['delete'])) {

$sid = htmlspecialchars($_GET['delete']);
$sql = "delete from registered_bus where RB_Id = '".$sid."'";
$result = $con->query($sql);
    
//set into action table
  $action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'DELETE', '".$url."', 'empty', CURRENT_TIMESTAMP)";
 
$results = $con->query($action);

header("Location: index.php");
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

    <title>Buses</title>

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
                                <li class="active">Buses</li>        
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
                            <th class="col-md-6"><h1>Buses</h1></th>
                            <th  style="padding-right:25px;" class="col-md-6"><a class="pull-right" href="add.php"><i class="glyphicon glyphicon-plus"></i>&nbsp; Add</a></th>
                        </tr>
                    </table>
                </div>
            </div>
           
            <div class="row">
                <div class="col-md-12">
                 <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-bus">
                 <thead>         
                    <tr>
                      <th style="width: 5%">Bus Id</th>
                      <th style="width: 17%">Driver Name</th>
                      <th style="width: 17%">Name</th>
                      <th style="width: 17%">Device Id</th>                    
                      <th style="width: 17%">Plate Number</th>
                      <th style="width: 24%">Action</th>
                    </tr>
                 </thead>
                  <tbody>
                       <?php
                      $sql = "SELECT * FROM `registered_bus` ORDER BY `RB_Id` DESC";
                      $result = $con->query($sql);
                      while ($readrow = $result->fetch_array()) {
                      ?>
                 <tr>
                    <td><?php echo $readrow['RB_Id']; ?></td>
                    <td><?php echo $readrow['RB_Driver_Id']; ?></td>
                    <td><?php echo $readrow['RB_Name']; ?></td>
                    <td><?php echo $readrow['RB_Device_Id']; ?></td>
                    <td><?php echo $readrow['RB_Plate_Number']; ?></td>
                    <td >
                      <a href="detail.php?did=<?php echo $readrow['RB_Id']; ?>" class="btn btn-link btn-xs" role="button"><i class='glyphicon glyphicon-list-alt'></i> Details</a>
                      <a href="edit.php?edit=<?php echo $readrow['RB_Id']; ?>" class="btn btn-link btn-xs" role="button"><i class='glyphicon glyphicon-edit'></i> Edit </a>
                      <a href="index.php?delete=<?php echo $readrow['RB_Id']; ?>" onclick="return confirm('Are you sure to delete?');" class="btn btn-link btn-xs" role="button"><i class='glyphicon glyphicon-trash'></i> Delete</a>
                    </td>
                 </tr>
                  <?php } ?>
                </tbody>
             </table>
<!--                            end table-->
                </div></div>
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
        $('#dataTables-bus').DataTable({
            responsive: true
        });
    });
    </script>
    

</body>

</html>

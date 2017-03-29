<?php
session_start();
include_once '../connection/dbconnect.php';
include 'edit_crud.php';

// This is in the PHP file and sends a Javascript alert to the client

//echo "<script type='text/javascript'>alert('wrong answer');</script>";

//Delete
if (isset($_GET['delete'])) {

  $sid = htmlspecialchars($_GET['delete']);
  $sql = "delete from admin where AD_Id = '".$sid."'";
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

    <title>Manage Admins</title>

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
    
    <style>
    #del {
    visibility: hidden;
    }
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
                                <li class="active">Manage Admins</li>        
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
                            <th class="col-md-6"><h1>Manage Admins</h1></th>
                            <th  style="padding-right:25px;" class="col-md-6"><a class="pull-right" href="../registration/"><i class="glyphicon glyphicon-plus"></i>&nbsp; Add Admin</a></th>
                        </tr>
                    </table>
                </div>
            </div>
            
<!--            Manage Roles Table-->
  
        
             <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-manage">
             <thead>         
                <tr>
                 <th style="width: 5%">Id</th>
                 <th style="width: 30%">Name</th>
                 <th style="width: 15%">Role</th>
                 <th style="width: 10%">Status</th>
                 <th style="width: 20%">Last Login</th>
                 <th style="width: 15%">Action</th>
                </tr>
             </thead>
              <tbody>
                   <?php
                  $sql = "SELECT * FROM `admin` ORDER BY `AD_Id` DESC";
                  $result = $con->query($sql);
                  while ($readrow = $result->fetch_array()) {
                  ?>   
             <tr>
                <td ><?php echo $readrow['AD_Id']; ?></td>
                <td ><?php echo $readrow['AD_Name']; ?></td>
                <td ><?php echo $readrow['AD_Type']; ?></td>
                <td>
                    <?php          
                    if ($readrow['AD_Active'] == "1") {
                    echo "<p  style='display:inline; color:green;'> <i class='glyphicon glyphicon-ok'></i>Active</p>";
                    } else {
                    echo "<p style='display:inline; color:red;'> <i class='glyphicon glyphicon-remove'></i>Inactive</p>";
                    }
                    ?>
                </td>
                <td ><?php echo $readrow['AD_Last_Login']; ?></td>
                <td >
                  <a href="detail.php?did=<?php echo $readrow['AD_Id']; ?>" class="btn btn-link btn-xs" role="button"><i class='glyphicon glyphicon-list-alt'></i> Details</a>
                  <a href="edit.php?edit=<?php echo $readrow['AD_Id']; ?>" class="btn btn-link btn-xs" role="button"><i class='glyphicon glyphicon-edit'></i> Edit </a>
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
        $('#dataTables-manage').DataTable({
            responsive: true
        });
    });
        
    </script>

</body>

</html>
<?php
session_start();
include_once '../connection/dbconnect.php';
include 'edit_crud.php';

//include server
include '../root.php';

//Delete
if (isset($_GET['delete'])) {

$sid = htmlspecialchars($_GET['delete']);
$sql = "delete from users where U_Id = '".$sid."'";
$result = $con->query($sql);
    
//set into action table
$action = "INSERT INTO `action` (`AC_Id`, `AC_Admin_Name`, `AC_Action`, `AC_Page`, `AC_Decsription`, `AC_Time`) VALUES (NULL, '".$_SESSION['AD_Username']."', 'DELETE', '".$url."', 'empty', CURRENT_TIMESTAMP)";
 
$results = $con->query($action);

header("Location: $root/users/");
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

    <title>Users</title>

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
                                <li class="active">Users</li>        
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
                    <h1 class="page-header">Users</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
  <!--            Manage Roles Table-->
  
        
       <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-user">
             <thead>         
                <tr>
                  <th style="width: 5%">User Id</th>
                  <th style="width: 15%">User Name</th>
                  <th style="width: 15%">Email</th>
                  <th style="width: 15%">Phone Number</th>                    
                  <th style="width: 15%">Occupation</th>                    
                  <th style="width: 10%">Status</th>
                  <th style="width: 10%">Active</th>
                  <th style="width: 20%">Action</th>
                </tr>
             </thead>
              <tbody>
                   <?php
                  $sql = "SELECT * FROM `users` ORDER BY `U_Id` DESC";
                  $result = $con->query($sql);
                  while ($readrow = $result->fetch_array()) {
                  ?>   
             <tr>
               <td><?php echo $readrow['U_Id']; ?></td>
                <td><?php echo $readrow['U_Name']; ?></td>
                <td><?php echo $readrow['U_Email']; ?></td>
                <td><?php echo $readrow['U_Phone']; ?></td>
                <td><?php echo $readrow['U_Occup']; ?></td>
                <td>
                    <?php          
                    if ($readrow['U_In_Journey'] == "1") {
                    echo "<p  style='display:inline; color:green;'> <i class='glyphicon glyphicon-ok'></i>In Journey</p>";
                    } else {
                    echo "<p style='display:inline; color:red;'> <i class='glyphicon glyphicon-remove'></i>Not In Journey</p>";
                    }
                    ?>
                </td>
                <td>
                    <?php          
                    if ($readrow['U_Active'] == "1") {
                    echo "<p  style='display:inline; color:green;'> <i class='glyphicon glyphicon-ok'></i>Active</p>";
                    } else {
                    echo "<p style='display:inline; color:red;'> <i class='glyphicon glyphicon-remove'></i>Deactive</p>";
                    }
                    ?>
                </td>
                <td >
                  <a href="detail.php?did=<?php echo $readrow['U_Id']; ?>" class="btn btn-link btn-xs" role="button"><i class='glyphicon glyphicon-list-alt'></i> Details</a>
                  <a href="index.php?delete=<?php echo $readrow['U_Id']; ?>" onclick="return confirm('Are you sure to delete?');" class="btn btn-link btn-xs" role="button"><i class='glyphicon glyphicon-trash'></i> Delete</a>
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
        $('#dataTables-user').DataTable({
            responsive: true
        });
    });
    </script>
    
</body>

</html>

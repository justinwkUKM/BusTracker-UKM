<?php
session_start();
include_once '../connection/dbconnect.php';

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

    <title>Detail</title>

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
    
    <style>
        .glyphicon {  margin-bottom: 10px;margin-right: 10px;}

        small {
        display: block;
        line-height: 1.428571429;
        color: #999;
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
                                <li><a href="<?php echo $root ?>/buses/"><i class="glyphicon glyphicon-bed"></i> Buses</a> <span class="divider"></span></li>            
                                <li class="active">Detail</li>        
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
                    <h1 class="page-header">Bus Details</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
         <?php
        $sql = "select * from registered_bus where RB_Id = '".$_GET['did']."'";
        $result = $con->query($sql);
        $readrow = $result->fetch_array();
        ?>
                
                <div class="row">
                    <div class="col-xs-12 col-sm-4 col-md-4">
                      <div class="panel panel-default">
                      <div class="panel-heading">Below are specifications of the Bus</div>
                      <table class="table">
                        <tr>
                          <td class="col-xs-4 col-sm-4 col-md-4"><strong>Bus ID</strong></td>
                          <td><?php echo $readrow['RB_Id'] ?></td>
                        </tr>
                        <tr>
                          <td ><strong>Driver Id</strong></td>
                          <td><?php echo $readrow['RB_Driver_Id'] ?></td>
                        </tr>
                        <tr>
                          <td><strong>Name</strong></td>
                          <td><?php echo $readrow['RB_Name'] ?></td>
                        </tr>
                        <tr>
                          <td><strong>Device Id</strong></td>
                          <td><?php echo $readrow['RB_Device_Id'] ?></td>
                        </tr>
                          <tr>
                          <td><strong>Plate Number</strong></td>
                          <td><?php echo $readrow['RB_Plate_Number'] ?></td>
                        </tr>
                        <tr>
                          <td><strong>Created</strong></td>
                          <td><?php echo $readrow['RB_Created'] ?></td>
                        </tr>
                        <tr>
                          <td><strong>Created By</strong></td>
                          <td><?php echo $readrow['RB_Created_By'] ?></td>
                        </tr>
                        
                          <tr>
                          <td><strong>Updated</strong></td>
                          <td><?php echo $readrow['RB_Updated'] ?></td>
                        </tr>
                          <tr>
                          <td><strong>Updated By</strong></td>
                          <td><?php echo $readrow['RB_Updated_By'] ?></td>
                        </tr>
                    </table>
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

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    

</body>

</html>

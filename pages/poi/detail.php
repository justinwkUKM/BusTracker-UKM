<?php
session_start();
include_once '../connection/dbconnect.php';

$_SESSION['poi_id'] = $_GET['did'];
?>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Details</title>

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
                                <li><a href="<?php echo $root ?>/poi/"><i class="glyphicon glyphicon-map-marker"></i> POI's</a> <span class="divider"></span></li>            
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
                    <h1 class="page-header">POI Details</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
             <!--            google_map-->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="row">
                                <div class="data-container">
                                    <section >
                                    <div id="google_map"></div>
                                    </section>
						        </div>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
<!--            google_map-->
            
             <?php
            $sql = "select * from point_of_interest where POI_Id = '".$_GET['did']."'";
            $result = $con->query($sql);
            $readrow = $result->fetch_array();
            ?>
                
                <div class="row">
                    <div class="col-xs-12 col-sm-5 col-md-5">
                      <div class="panel panel-default">
                      <div class="panel-heading">Below are specifications of the POI</div>
                      <table class="table">
                        <tr>
                          <td class="col-xs-4 col-sm-4 col-md-4"><strong>POI ID</strong></td>
                          <td><?php echo $readrow['POI_Id'] ?></td>
                        </tr>
                        <tr>
                          <td ><strong>Nearest Station</strong></td>
                          <td><?php echo $readrow['POI_Station_Id'] ?></td>
                        </tr>
                        <tr>
                          <td><strong>Name</strong></td>
                          <td><?php echo $readrow['POI_Name'] ?></td>
                        </tr>
                        <tr>
                          <td><strong>Address</strong></td>
                            <td><?php echo $readrow['POI_Address'] ?></td>
                        </tr>
                        <tr>
                          <td><strong>Created</strong></td>
                          <td><?php echo $readrow['POI_Created'] ?></td>
                        </tr>
                        <tr>
                          <td><strong>Created By</strong></td>
                          <td><?php echo $readrow['POI_Created_By'] ?></td>
                        </tr>
                        <tr>
                          <td><strong>Latitude</strong></td>
                          <td><?php echo $readrow['POI_Latitud'] ?></td>
                        </tr>
                          <tr>
                          <td><strong>Longitude</strong></td>
                          <td><?php echo $readrow['POI_Longitud'] ?></td>
                        </tr>
                          <tr>
                          <td><strong>Description</strong></td>
                          <td><?php echo $readrow['POI_Description'] ?></td>
                        </tr>
                          <tr>
                          <td><strong>Type</strong></td>
                          <td><?php echo $readrow['POI_Type'] ?></td>
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
    
    <!--		google map-->

    <script type="text/javascript" src="../../dist/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDB4Emcey7j7XO57Pr4LCL4DzUOzIygqWI&sensor=false"></script>
    <script type="text/javascript" src="../../dist/js/map_directories_detail.js"></script>
    <link rel='stylesheet' type='text/css' href='../../dist/css/map.css'>

</body>

</html>

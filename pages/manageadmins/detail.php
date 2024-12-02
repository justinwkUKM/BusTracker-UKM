<?php
session_start();
include_once '../connection/dbconnect.php';
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
        .table>tbody>tr>td {border-top: 0px;}
        .glyphicon {  
            margin-bottom: 10px;margin-right: 10px;
        }

        small {
        display: block;
        line-height: 1.428571429;
            
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
                                <li><a href="<?php echo $root ?>/manageadmins/"><i class="glyphicon glyphicon-th-list"></i> Manage Admins</a> <span class="divider"></span></li>            
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
                    <h1 class="page-header">Admin Details</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
         <?php
        $sql = "select * from admin where AD_ID = '".$_GET['did']."'";
        $result = $con->query($sql);
        $readrow = $result->fetch_array();
        ?>
                <div class="container">
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-10">
                        <div class="well well-sm">
                            <div class="row">
                                <div class="col-md-4">
                                    <?php if ($readrow['AD_Avatar'] == "" ) 
                                        {
                                          echo "<img src='$root/img/admin/user-placeholder.jpg' class='' height='250px' width='250px'>";
                                        }
                                          else { ?> 
                                <img src="<?php echo $root ?>/img/admin/<?php echo $readrow['AD_Avatar'] ?>" class="img-rounded img-responsive">
                                            <?php } ?>
                                </div>
                                <div class="col-md-6">
                                    <h4>
                                        <?php echo $readrow['AD_Name'] ?><hr>
                                    </h4>
                                    <table class="table">
                                    
                                    <tr>
                                      <td col-xs-2 col-sm-2 col-md-2><strong>Email</strong></td>
                                      <td><?php echo $readrow['AD_Email'] ?></td>
                                    </tr>
                                    <tr>
                                      <td ><strong>Phone Number</strong></td>
                                      <td><?php echo $readrow['AD_Phone'] ?></td>
                                    </tr>
                                        <tr>
                                      <td ><strong>Admin Level</strong></td>
                                      <td><?php echo $readrow['AD_Type'] ?></td>
                                    </tr>
                                        <tr>
                                      <td ><strong>Position</strong></td>
                                      <td><?php echo $readrow['AD_Position'] ?></td>
                                    </tr>
                                        <tr>
                                      <td ><strong>Description</strong></td>
                                      <td><?php echo $readrow['AD_Description'] ?></td>
                                    </tr>
                                        <tr>
                                      <td ><strong>Status</strong></td>
                                      <td>
                                      <?php 
                                        
                                        if ($readrow['AD_Active'] == "1") {
                                            echo "<p  style='display:inline; color:green;'> <i class='glyphicon glyphicon-ok'></i>Active</p>";
                                        } else {
                                            echo "<p style='display:inline; color:red;'> <i class='glyphicon glyphicon-remove'></i>Inactive</p>";
                                        }
                                    ?>
                                    </td>
                                    </tr>
                                        <tr>
                                      <td ><strong>Update By</strong></td>
                                      <td><?php echo $readrow['AD_Updated_By'] ?></td>
                                    </tr>
                                        <tr>
                                      <td ><strong>Update At</strong></td>
                                      <td><?php echo $readrow['AD_Updated'] ?></td>
                                    </tr>
                                    </table>
                                    <!-- Split button -->
<!--
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-primary"> <i class="glyphicon glyphicon-envelope"></i>Inbox</button>
                                        
                                    </div>
-->
                                </div>
                            </div>
                        </div>
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

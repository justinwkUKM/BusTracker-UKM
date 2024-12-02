<?php
session_start();
include_once '../connection/dbconnect.php';
include 'edit_crud.php';
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
                                <li><a href="<?php echo $root ?>/reports/"><i class="fa fa-edit fa-fw"></i> Reports</a> <span class="divider"></span></li>            
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
                    <h1 class="page-header">Report Details</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
         <?php
        $sql = "select * from report where R_Id = '".$_GET['did']."'";
        $result = $con->query($sql);
        $readrow = $result->fetch_array();
        ?>
            
        <div class="row">
            <div class="col-xs-12 col-sm-4 col-md-4">
              <div class="panel panel-default">
              <div class="panel-heading">Below are specifications of the Report</div>
              <table class="table">
                <tr>
                  <td class="col-xs-4 col-sm-4 col-md-4"><strong>Report ID</strong></td>
                  <td><?php echo $readrow['R_Id'] ?></td>
                </tr>
                <tr>
                  <td ><strong>Reporter Id</strong></td>
                  <td><?php echo $readrow['R_Reporter_Id'] ?></td>
                </tr>
                <tr>
                  <td><strong>Subject</strong></td>
                  <td><?php echo $readrow['R_Subject'] ?></td>
                </tr>
                  <tr>
                  <td><strong>Message</strong></td>
                  <td><?php echo $readrow['R_Message'] ?></td>
                </tr>
                <tr>
                  <td><strong> Report Type</strong></td>
                  <td><?php echo $readrow['R_Report_Type'] ?></td>
                </tr>
                  <tr>
                  <td><strong>Created At</strong></td>
                  <td><?php echo $readrow['R_Created'] ?></td>
                </tr>
                 <tr>
                  <td><strong>Status</strong></td>
                  <td>
                       <?php          
                        if ($readrow['R_Status'] == "1") {
                        echo "<p  style='display:inline; color:green;'> <i class='glyphicon glyphicon-ok'></i> Solved</p>";
                        } else {
                        echo "<p style='display:inline; color:red;'> <i class='glyphicon glyphicon-remove'></i> Pending</p>";
                        }
                        ?>
                </td>
                </tr>
              </table>
                  </div>
                </div>
            </div>
                
<!--                set action-->
               <hr>
               <form action="detail.php" method="post" class="form-horizontal">
                  <div class="form-group">
                  <label for="reportstat" class="col-sm-1 control-label">Status</label>
                  <div class="col-sm-6">
                  <div class="radio">
                    <label>
                    <input name="reportstat" type="radio" id="reportstat" value="1" <?php if(isset($_GET['did'])) if($editrow['R_Status']=="1") echo "checked"; ?> required> Solved
                    </label>
                  </div>
                  <div class="radio">
                    <label>
                       <input name="reportstat" type="radio" id="reportstat" value="0" <?php if(isset($_GET['did'])) if($editrow['R_Status']=="0") echo "checked"; ?>> Pending
                    </label>
                    </div>
                  </div>
                </div>
                
              <div class="form-group">
              <div class="col-sm-6">
              <?php if (isset($_GET['did'])) { ?>
              <input type="hidden" name="oldsid" value="<?php echo $editrow['R_Id']; ?>">
              <button class="btn btn-success" type="submit" name="update">Update</button>
              <?php } else { ?>
              <button class="btn btn-success" type="submit" name="save"> Save</button>
              <?php } ?>
            </div>
          </div>
        </form>
        
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

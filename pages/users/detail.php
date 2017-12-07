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
                                <li><a href="<?php echo $root ?>/users/"><i class="glyphicon glyphicon-user"></i>User</a> <span class="divider"></span></li>            
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
                    <h1 class="page-header">User Details</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
             <?php
            $sql = "select * from users where U_ID = '".$_GET['did']."'";
            $result = $con->query($sql);
            $readrow = $result->fetch_array();
            ?>
              
            <div class="container">
            <div class="row">
                <div class="col-md-4 well well-sm text-center" style="padding: 20px;">
                  <?php if ($readrow['DI_Avatar'] == "" ) {
                    echo "<img src='$root/img/users/user-placeholder.jpg' class='' height='250px' width='250px'>";
                  }
                  else { ?>
                  <img src="<?php echo $readrow['U_Avatar'] ?>" class="" height="250px" width="250px">
                  <?php } ?>
                </div>
                <div class="col-md-5">
                  <div class="panel panel-default">
                  <div class="panel-heading">Below are specifications of the Driver</div>
                  <table class="table">
                   <tr>
                  <td col-xs-2 col-sm-2 col-md-2><strong>Email</strong></td>
                  <td><?php echo $readrow['U_Email'] ?></td>
                </tr>
                <tr>
                  <td ><strong>Registration Date</strong></td>
                  <td><?php echo $readrow['U_Created'] ?></td>
                </tr>
                    <tr>
                  <td ><strong>Phone Number</strong></td>
                  <td><?php echo $readrow['U_Phone'] ?></td>
                </tr>
                    <tr>
                  <td ><strong>Address</strong></td>
                  <td><?php echo $readrow['U_Address'] ?></td>
                </tr>
                    <tr>
                  <td ><strong>Occupation</strong></td>
                  <td><?php echo $readrow['U_Occup'] ?></td>
                </tr>
                   <tr>
                  <td ><strong>Device Id</strong></td>
                  <td><?php echo $readrow['U_Device_Id'] ?></td>
                </tr>
                   <tr>
                  <td ><strong>Last Login</strong></td>
                  <td><?php echo $readrow['U_Last_Login'] ?></td>
                </tr>
                   <tr>
                  <td ><strong>Last Latitude</strong></td>
                  <td><?php echo $readrow['U_Last_Latitud'] ?></td>
                </tr>
                   <tr>
                  <td ><strong>Last Longitude</strong></td>
                  <td><?php echo $readrow['U_Last_Longitud'] ?></td>
                </tr>
                   <tr>
                  <td ><strong>Last Origin</strong></td>
                  <td><?php echo $readrow['U_Last_Origin'] ?></td>
                </tr>
                   <tr>
                  <td ><strong>Last Destination</strong></td>
                  <td><?php echo $readrow['U_Last_Destination'] ?></td>
                </tr>
                   <tr>
                  <td ><strong>Update at</strong></td>
                  <td><?php echo $readrow['U_Update'] ?></td>
                </tr>
                   <tr>
                  <td ><strong>Update By</strong></td>
                  <td><?php echo $readrow['U_Updated_by'] ?></td>
                </tr>
                   <tr>
                  <td ><strong>Point</strong></td>
                  <td><?php echo $readrow['U_Point'] ?></td>
                </tr>
                   <tr>
                  <td ><strong>Refer Id</strong></td>
                  <td><?php echo $readrow['U_Refer_Id'] ?></td>
                </tr>
                   <tr>
                  <td ><strong>Fav Origin</strong></td>
                  <td><?php echo $readrow['U_Fav_Origin'] ?></td>
                </tr>
                   <tr>
                  <td ><strong>Destination</strong></td>
                  <td><?php echo $readrow['U_Fav_Destination'] ?></td>
                </tr>
                    <tr>
                  <td ><strong>Nationality</strong></td>
                  <td><?php echo $readrow['U_Nationality'] ?></td>
                </tr>
                    <tr>
                  <td ><strong>Status</strong></td>
                  <td>
                  <?php 

                    if ($readrow['U_Active'] == "1") {
                        echo "<p  style='display:inline; color:green;'> <i class='glyphicon glyphicon-ok'></i>Active</p>";
                    } else {
                        echo "<p style='display:inline; color:red;'> <i class='glyphicon glyphicon-remove'></i>Inactive</p>";
                    }
                ?>
                </td>
                </tr>
                   <tr>
                  <td ><strong>Status</strong></td>
                  <td>
                  <?php 

                    if ($readrow['U_Gender'] == "1") {
                        echo "<p  style='display:inline; color:green;'> <i class='glyphicon glyphicon-user'></i>Male</p>";
                    } else {
                        echo "<p style='display:inline; color:red;'> <i class='glyphicon glyphicon-user'></i>Female</p>";
                    }
                ?>
                </td>
                </tr>
                   <tr>
                  <td ><strong>Status</strong></td>
                  <td>
                  <?php 

                    if ($readrow['U_In_Journey'] == "1") {
                        echo "<p  style='display:inline; color:green;'> <i class='glyphicon glyphicon-ok'></i>In Journey</p>";
                    } else {
                        echo "<p style='display:inline; color:red;'> <i class='glyphicon glyphicon-remove'></i>Not In Journey</p>";
                    }
                ?>
                </td>
                </tr>
                </table>
                  </div>
                </div>
            </div>
        </div>
           <!--                set action-->
               <hr>
               <form action="detail.php" method="post" class="form-horizontal">
                  <div class="form-group">
                  <label for="userstat" class="col-sm-1 control-label">Status</label>
                  <div class="col-sm-6">
                  <div class="radio">
                    <label>
                    <input name="userstat" type="radio" id="userstat" value="1" <?php if(isset($_GET['did'])) if($editrow['U_Active']=="1") echo "checked"; ?> required> Active
                    </label>
                  </div>
                  <div class="radio">
                    <label>
                       <input name="userstat" type="radio" id="userstat" value="0" <?php if(isset($_GET['did'])) if($editrow['U_Active']=="0") echo "checked"; ?>> Deactive
                    </label>
                    </div>
                  </div>
                </div>
                
              <div class="form-group">
              <div class="col-sm-6">
              <?php if (isset($_GET['did'])) { ?>
              <input type="hidden" name="oldsid" value="<?php echo $editrow['U_Id']; ?>">
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

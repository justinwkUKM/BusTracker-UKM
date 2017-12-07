<?php
session_start();
include_once '../connection/dbconnect.php';
include 'schedule_crud.php'
?>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Edit</title>

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
                                <li><a href="<?php echo $root ?>/schedules/"><i class="glyphicon glyphicon-calendar"></i> Schedule</a> <span class="divider"></span></li>            
                                <li class="active">Edit</li>        
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
                    <h1 class="page-header">Edit Schedule</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!--         // Edit Schedule-->
            
                  <form action="edit_schedule.php" method="post" class="form-horizontal">
                            
                            <div class="form-group">
                                 <label for="type" class="col-sm-3 control-label">Season</label>
                                 <?php if(isset($_GET['edit'])) $schedule = $editrow['BS_Session'];?>
                                  <div class="col-sm-9">
                                 <select name="season" class="form-control" id="type" required>
                                   <option value="">Please select type</option>
                                    <?php 
                                    $sql = "SELECT * FROM `bus_code` WHERE `BC_Type` = 'season'";
                                      $result = $con->query($sql);
                                      while ($readrow = $result->fetch_array()) {
                                      ?>   
                                        <option value="<?php echo $readrow['BC_Code']; ?>" <?php if(isset($_GET['edit'])) if($readrow['BC_Code']== "$schedule") echo "selected"; ?>><?php echo $readrow['BC_Code']; ?></option>
                                      <?php } ?>  
                                  </select>
                                </div>
                            </div>  
                             <div class="form-group">
                              <label for="day" class="col-sm-3 control-label">Day</label>
                              <div class="col-sm-9">
                             <select name="day" class="form-control" id="day" required>
                                <option value="">Please select</option>
                                <option value="sunday" <?php if(isset($_GET['edit'])) if($editrow['BS_Day']=="sunday") echo "selected"; ?>>Sunday</option>
                                <option value="monday" <?php if(isset($_GET['edit'])) if($editrow['BS_Day']=="monday") echo "selected"; ?>>Monday</option>
                                <option value="tuesday" <?php if(isset($_GET['edit'])) if($editrow['BS_Day']=="tuesday") echo "selected"; ?>>Tuesday</option>
                                <option value="wednesday" <?php if(isset($_GET['edit'])) if($editrow['BS_Day']=="wednesday") echo "selected"; ?>>Wednesday</option>
                                <option value="thursday" <?php if(isset($_GET['edit'])) if($editrow['BS_Day']=="thursday") echo "selected"; ?>>Thursday</option>
                                <option value="friday" <?php if(isset($_GET['edit'])) if($editrow['BS_Day']=="friday") echo "selected"; ?>>Friday</option>
                                <option value="saturday" <?php if(isset($_GET['edit'])) if($editrow['BS_Day']=="saturday") echo "selected"; ?>>Saturday</option>
                              </select>
                            </div>
                            </div>  
                            
                            <div class="form-group">
                             <label for="type" class="col-sm-3 control-label">Bus Type</label>
                             <?php if(isset($_GET['edit'])) $type = $editrow['BS_Bus_Type'];?>
                              <div class="col-sm-9">
                             <select name="type" class="form-control" id="type" required>
                                <?php 
                                    $sql = "SELECT * FROM `bus_code` WHERE `BC_Type` = 'bas'";
                                      $result = $con->query($sql);
                                      while ($readrow = $result->fetch_array()) {
                                      ?>   
                                        <option value="<?php echo $readrow['BC_Code']; ?>" <?php if(isset($_GET['edit'])) if($editrow['BS_Bus_Type']== "$type") echo "selected"; ?>><?php echo $readrow['BC_Code'] ?></option>

                                      <?php } ?>  
                              </select>
                            </div>
                            </div>
                          
                          <div class="form-group">
                        <label for="driver" class="col-sm-3 control-label">No of Driver</label>
                        <div class="col-sm-9">
                          <input name="driver" type="number" class="form-control" id="driver" placeholder="No of Driver" value="<?php if(isset($_GET['edit'])) echo $editrow['BS_Driver_Num']; ?>" required>
                        </div>
                    </div>
                      
                     <div class="form-group">
                      <div class="col-sm-offset-3 col-sm-9">
                      <?php if (isset($_GET['edit'])) { ?>
                      <input type="hidden" name="oldpid" value="<?php echo $editrow['BS_Id']; ?>">
                      <button class="btn btn-default" type="submit" name="update"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Update</button>
                      <?php } else { ?>
                      <button class="btn btn-default" type="submit" name="create"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Create</button>
                      <?php } ?>
                      <button class="btn btn-default" type="reset"><span class="glyphicon glyphicon-erase" aria-hidden="true"></span> Clear</button>
                    </div>
                  </div>
                      
                </form>
     <!-- /.row -->
            
    <!--         // Edit Schedule-->
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

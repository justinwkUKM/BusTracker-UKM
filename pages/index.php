<?php
session_start();
include_once 'connection/dbconnect.php';

date_default_timezone_set('Asia/Kuala_Lumpur');
$time = new DateTime();
$time = $time->format('H:i');

//get time bus zon 2
$sql = "SELECT * FROM `bus_schedule` WHERE `BS_Bus_Type` = 'zon 2'";
$result = $con->query($sql);
$readrow = $result->fetch_array();
{
  $zon2schedule = $readrow['BS_Id'];
}

//get closest time bus zon 2
$sql = "SELECT * FROM schedule_detail WHERE `SD_Schedule_Id`= '".$zon2schedule."' AND SD_Time > NOW() ORDER BY SD_Time LIMIT 1";
$result = $con->query($sql);
$readrow = $result->fetch_array();
{
    $zon2now = $readrow['SD_Time'];  
    $zon2now = date('h:i A', strtotime($zon2now));
}

//get time bus zon 3
$sql = "SELECT * FROM `bus_schedule` WHERE `BS_Bus_Type` = 'zon 3u'";
$result = $con->query($sql);
$readrow = $result->fetch_array();
{
  $zon3uschedule = $readrow['BS_Id'];
}

//get closest time bus zon 3
$sql = "SELECT * FROM schedule_detail WHERE `SD_Schedule_Id`= '".$zon3uschedule."' AND SD_Time > NOW() ORDER BY SD_Time LIMIT 1";
$result = $con->query($sql);
$readrow = $result->fetch_array();
{
    $zon3unow = $readrow['SD_Time']; 
    $zon3unow = date('h:i A', strtotime($zon3unow));
    
}//get time bus zon 6
$sql = "SELECT * FROM `bus_schedule` WHERE `BS_Bus_Type` = 'zon 6'";
$result = $con->query($sql);
$readrow = $result->fetch_array();
{
  $zon6schedule = $readrow['BS_Id'];
}

//get closest time bus zon 6
$sql = "SELECT * FROM schedule_detail WHERE `SD_Schedule_Id`= '".$zon6schedule."' AND SD_Time > NOW() ORDER BY SD_Time LIMIT 1";
$result = $con->query($sql);
$readrow = $result->fetch_array();
{
    $zon6now = $readrow['SD_Time'];  
    $zon6now = date('h:i A', strtotime($zon6now));
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

    <title>Dashboard</title>

    <!-- Bootstrap Core CSS -->
    <link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.min.css" rel="stylesheet">
    <link href="../dist/css/styleheader.css" rel="stylesheet">
    
    <!-- DataTables CSS -->
    <link href="../vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="../vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="../vendor/morrisjs/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <!--		google map-->

<script type="text/javascript" src="../dist/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDB4Emcey7j7XO57Pr4LCL4DzUOzIygqWI&sensor=false"></script>
<script type="text/javascript" src="../dist/js/map_bus.js"></script>
<link rel='stylesheet' type='text/css' href='../dist/css/map.css'>
<link rel="stylesheet" type="text/css" href="../dist/css/ukm_bus.css">


</head>

<body>

    <div id="wrapper">

       <!--		header here-->
		<?php include('layout/nav-bar.php'); ?>        

        <div id="page-wrapper">
        
           
           
  <!--            breadcrumb-->
            <div class="row">
                <div class="col-lg-12">
                   
                        <div class="panel-body">
                            <div class="row">
                                
                                <ul class="breadcrumb">
                                <li><a href="<?php echo $root ?>/"><i class="glyphicon glyphicon-home"></i> Home</a> <span class="divider"></span></li>            
                                <li class="active">Dashboard</li>        
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
                    <h1 class="page-header">Dashboard</h1>
                     
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            
            
            
<!--            google_map-->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default" id="gmap-container"">
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
           
            <!-- /.row -->
            
<!--         bus schedule table-->
               <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Bus Schedule
                        </div>
                        <div class="panel-body">
<!--                           schedule time-->
                            <div class="row">
                          <div class="col-sm-4">
                            <div class="card">
                              <div class="card-block">
                                <h3 class="card-title">Zon 2</h3>
                                <h4 class="card-text">Next : <?php  echo $zon2now ?></h4>
                              </div>
                            </div>
                          </div>
                          <div class="col-sm-4">
                            <div class="card">
                              <div class="card-block">
                                <h3 class="card-title">Zon 3u</h3>
                                <h4 class="card-text">Next : <?php  echo $zon3unow ?></h4>
                              </div>
                            </div>

                        </div><div class="col-sm-4">
                            <div class="card">
                              <div class="card-block">
                                <h3 class="card-title">Zon 6</h3>
                                <h4 class="card-text">Next : <?php  echo $zon6now ?></h4>
                              </div>
                            </div>
                          </div>
                        </div>
                           <hr>
                            <div class="row">
                                                      
                           <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-busSchedule">
                               <thead>
                               
                            <tr>
                            <th style='width: 5%'>Schedule Id</th>
                            <th style='width: 20%'>Session</th>
                            <th style='width: 20%'>Type</th>
                            <th style='width: 20%'>Day</th>
                            <th style='width: 10%'>No of Driver</th>
                             </tr>
                  
                                </thead>
                               <tbody>
                                <?php
                              $sql = "SELECT * FROM `bus_schedule` ORDER BY `BS_Id` DESC";
                              $result = $con->query($sql);
                              while ($readrow = $result->fetch_array()) {
                              ?>   
                              <tr class='odd gradeX'>
                                <td><?php echo $readrow['BS_Id']; ?></td>
                                <td><?php echo $readrow['BS_Session']; ?></td>
                                <td><?php echo $readrow['BS_Bus_Type']; ?></td>
                                <td><?php echo $readrow['BS_Day']; ?></td>
                                <td><?php echo $readrow['BS_Driver_Num']; ?></td>
                               
                              </tr>
                              <?php } ?>
                                </tbody>
                            </table>
<!--                            end table-->
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
    <!--         //bus schedule table-->
    
    <!--         bus schedule table-->
               <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Report
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-reports">
                               <thead>  
                            <tr>
                                <th>Report Id</th>
                                <th>Reporter Id</th>
                                <th>Subject</th>
                                <th>Message</th>                    
                                <th>Report Type</th>
                                <th>Date Created</th>
                            </tr>
                  
                                </thead>
                               <tbody>
                                <?php
                              $sql = "SELECT * FROM `report` ORDER BY `R_Id` DESC";
                              $result = $con->query($sql);
                              while ($readrow = $result->fetch_array()) {
                              ?>   
                              <tr class='odd gradeX'>
                                <td><?php echo $readrow['R_Id']; ?></td>
                                <td><?php echo $readrow['R_Reporter_Id']; ?></td>
                                <td><?php echo $readrow['R_Subject']; ?></td>
                                <td><?php echo $readrow['R_Message']; ?></td>
                                <td><?php echo $readrow['R_Report_Type']; ?></td>
                                <td><?php echo $readrow['R_Created']; ?></td>
                               
                              </tr>
                              <?php } ?>
                                </tbody>
                            </table>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
    <!--         //bus schedule table-->



        <div> 
        <h2>Summary Version</h2>
        <br>

        <ol class="timeline timeline--summary">
            <li class="timeline__step done">
                <input class="timeline__step-radio" id="trigger1{{identifier}}" name="trigger{{identifier}}" type="radio">

                <label class="timeline__step-label" for="trigger1{{identifier}}">
                    <span class="timeline__step-content">
                        12 May 2013</span>
                </label>
                
                <span class="timeline__step-title">
                    KTM</span>
                
                <i class="timeline__step-marker">1</i>
            </li>
            <li class="timeline__step done">
                <input class="timeline__step-radio" id="trigger2{{identifier}}" name="trigger{{identifier}}" type="radio">
                
                <label class="timeline__step-label" for="trigger2{{identifier}}">
                    <span class="timeline__step-content">
                        14 May 2013</span>
                </label>
                
                <span class="timeline__step-title">
                    KKM</span>

                <i class="timeline__step-marker">2</i>
            </li>
            <li class="timeline__step">
                <input class="timeline__step-radio" id="trigger3{{identifier}}" name="trigger{{identifier}}" type="radio">
                
                <label class="timeline__step-label" for="trigger3{{identifier}}">
                    <span class="timeline__step-content">
                        15 May 2013</span>
                </label>
                
                <span class="timeline__step-title">
                    KPZ</span>
                
                <i class="timeline__step-marker">3</i>
            </li>
            <li class="timeline__step">
                <input class="timeline__step-radio" id="trigger4{{identifier}}" name="trigger{{identifier}}" type="radio">
                
                <label class="timeline__step-label" for="trigger4{{identifier}}">
                    <span class="timeline__step-content">
                        16 May 2013</span>
                </label>
                
                <span class="timeline__step-title">
                    KIY</span>
                
                <i class="timeline__step-marker">4</i>
            </li>
        </ol>
        </div>
    </div>
    <!-- /#wrapper -->
    
     </div>
        <!-- /#page-wrapper -->

    <!-- jQuery -->
    <script src="../vendor/jquery/jquery-1.12-0.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../vendor/metisMenu/metisMenu.min.js"></script>
<!-- DataTables JavaScript -->
    <script src="../vendor/datatables/js/jquery.dataTables.min.js"></script>
    <script src="../vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
    <script src="../vendor/datatables-responsive/dataTables.responsive.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        $('#dataTables-busSchedule').DataTable({
            responsive: true
        });
    });
        
        $(document).ready(function() {
        $('#dataTables-reports').DataTable({
            responsive: true
        });
    });
        
    </script>
</body>

</html>
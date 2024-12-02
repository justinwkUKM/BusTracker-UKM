<?php
session_start();
include_once '../connection/dbconnect.php';
include 'set_schedule_crud.php';


$sid = htmlspecialchars($_GET['edit']);
$sql = "SELECT * FROM `bus_schedule` WHERE BS_Id = '".$sid."'";
$result = $con->query($sql);
while ($readrow = $result->fetch_array()) {


$bus = $readrow['BS_Driver_Num'];
$type = $readrow['BS_Bus_Type'];
$starts = $readrow['BS_Start'];
$ends = $readrow['BS_End'];
$timeInts = $readrow['BS_TimeInterval'];
$midnight = $readrow['BS_Midnight'];
    
$_SESSION["sid"] = $sid;
$_SESSION["BS_Driver_Num"] = $readrow['BS_Driver_Num'];
}

if ($midnight != 1) { $mid= "disabled"; $hidemidnight = "midnightrow";}
    else {$mid="required";}

//edit schedeule
$sql = "SELECT * FROM `schedule_detail` WHERE `SD_Schedule_Id` = '".$sid."' order by SD_id ASC, SD_Time ASC, SD_Bus ASC";
$result = $con->query($sql);


?>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Schedule</title>

    <!-- Bootstrap Core CSS -->
    <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../../vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="../../vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="../../vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../../dist/css/sb-admin-2.css" rel="stylesheet">
    
    <link href="../../dist/css/bootstrap-select.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../../vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="../../dist/js/bootstrap-select.js"></script>
  
  <style>
 
.bootstrap-select > .dropdown-toggle {    width: 70%;
    padding-right: 5px;
    z-index: 1;
}
      
   table.dataTable thead > tr > th {
     padding-left: 5px; 
     padding-right: 10px; 
}
      
      th {
    text-align: center;
}
      #midnightrow{
          visibility: hidden;
      }
      
</style>

</head>

<body>

    <div id="wrapper">

         <!-- Navigation -->
        <?php include '../layout/nav-bar.php' ?>

        
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                   <div class="panel-body">
                            <div class="row">
                                
                               <ul class="breadcrumb">
                                <li><a href="<?php echo $root ?>/"><i class="glyphicon glyphicon-home"></i> Home</a> <span class="divider"></span></li> 
                                <li><a href="<?php echo $root ?>/schedules/"><i class="glyphicon glyphicon-calendar"></i> schedules</a> <span class="divider"></span></li>            
                                <li class="active">Set Schedule</li>        
                                </ul>
            
                        </div>
                        <!-- /.row -->
                    </div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Set Schedule
                            
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                <!-- start form -->


<!--                create schedule button-->
                 <a href="create_schedule.php?create=<?php echo $_GET['edit']; ?>" class="btn btn-link btn-xs" role="button" style="float: right;font-size: larger;"><i class='glyphicon glyphicon-plus'></i> Create Schedule </a>
                 <h4>Please insert time to create table</h4>
                  
                    <form action="set_schedule.php?edit=<?php echo $_SESSION["sid"]; ?>" method="post" class="form-horizontal">
                     
                    <div class="form-group">
                    <label for="start" class="col-sm-3 control-label">Start time:</label>
                    <div class="col-sm-3 offset-6">
                      <input name="start" type="time" class="form-control" value="<?php echo $starts?>" required>
                    </div>
                    </div>

                    <div class="form-group">
                    <label for="end" class="col-sm-3 control-label">End time:</label>
                    <div class="col-sm-3 offset-6">
                      <input name="end" type="time" class="form-control" value="<?php echo $ends?>" required>
                    </div>
                    </div> 

                    <div class="form-group">
                    <label for="time" class="col-sm-3 control-label">Time Interval:</label>
                    <div class="col-sm-3 offset-6">
                         <input name="timeInt" type="number" class="form-control" id="timeInt" placeholder="In minutes" value="<?php echo $timeInts?>" required>
                    </div>
                    </div> 
                    
                    <div class="form-group">
                    <label for="addtime" class="col-sm-3 control-label"></label>
                    <div class="col-sm-3 offset-6">
                          <input type="checkbox" name="addtime" value="1"> 12:00 AM<br>
                    </div>
                    </div>  
                     <div class="form-group">
                      <div class="col-sm-offset-3 col-sm-9">
                      <button class="btn btn-default" type="submit" name="times" value="Submit"><span class="glyphicon glyphicon-plus"></span> Create</button>
                    </div>
                  </div>
                      
                </form>
                  
                  <br><br><br>

                  <form action="set_schedule.php?edit=<?php echo $_SESSION["sid"]; ?>" method="post" class="form-horizontal">

                    <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-setSchedule">
                       <thead>
                                 <tr>
                                 
                    <th style="width: 10%">Time</th>
                    <?php

                for ($j=1;$j<=$bus;$j++){ ?>
                         <th style='width: 10%'>
                               
                             
                               <div class="form-group">
                                <div class="col-sm-9">
                               <input name="<?php echo "bus-".$j;?>" type="text" class="form-control" id="<?php echo "bus-".$j?>" placeholder="Number of bus" value="<?php echo "bus-".$j?>" >
                              </div>
                              </div>
                           
                             </th>
                    <?php  }  ?>
                           </tr>
                  </thead>
                 <tbody>

                  <?php

                  $tStart = strtotime($starts);
                  $tEnd = strtotime($ends);
                  $tNow = $tStart;
                     $total = 0;
                
                 while(($tNow <= $tEnd)&&($readrow = $result->fetch_array()))
                  {    
                     $ty = $readrow['SD_Status'];
                    $total ++; ?>
                     <tr id="<?php echo "time-".$total ?>" class='odd gradeX'>
                     <input type="hidden" name="<?php echo "Time-id-".$total ?>" value="<?php echo $readrow['SD_id']; ?>">
                  <td><input name="<?php echo "time-".$total; ?>" type="time" class="form-control" id="<?php echo "time-".$total ?>" value="<?php echo $readrow['SD_Time']; ?>" ></td> 
                    

                  <?php
                 for ($j=1;$j<=$bus;$j++)
                  {     ?>

                 <td name="" id="<?php echo "status-".$total."-".$j ?>" class='center'>
                   <div class="form-group">
                    <div class="col-sm-9">
                    <input type="hidden" name="<?php echo "id-".$total."-".$j ?>" value="<?php echo $readrow['SD_id']; ?>">
                   <select name="<?php echo "status-".$total."-".$j ?>" class="form-control" id="type" required>
                   <option value="">Please select type</option>
                     
                       <?php
                        $sqlbc = "SELECT * FROM `bus_code` WHERE `BC_Type` = 'status'";
                      $resultbc = $con->query($sqlbc);
                      while ($readrowbc = $resultbc->fetch_array()) {
                        ?>
                         <option value="<?php 
                         echo $readrowbc['BC_Code']; ?>" <?php if(isset($_GET['edit'])) if($readrowbc['BC_Code']== $ty) echo "selected"; ?>><?php echo $readrowbc['BC_Code']; ?></option>
                      <?php }
                       ?>  
                  </select>
                  </div>
                  </div>
                          
                    </td>
                    
                    <?php if ($j<>$bus) { $readrow = $result->fetch_array(); $ty = $readrow['SD_Status']; }
                  } ?>           
                   </tr>
                   
                   
                   <?php } ?>   
                  
 
                                  
                         </tbody>
                        </table>
                        <!-- /.table-responsive -->
                        <div class="well">
                            <h4>Note </h4>
                            <p></p>
                        </div>
                         <div class="form-group">
                          <div class="col-sm-offset-9 col-sm-3">
                          <?php if (isset($_GET['edit'])) { ?>
                          <input type="hidden" name="oldpid" value="<?php echo $_SESSION["BS_Id"]; ?>">
                          <button class="btn btn-default" type="submit" name="save"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> save</button>
                          <?php } ?>
                          <button class="btn btn-default" type="reset"><span class="glyphicon glyphicon-erase" aria-hidden="true"></span> Clear</button>
                        </div>
                        </div>

                    </form>
                            
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
            
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="../../vendor/jquery/jquery.min.js"></script>

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
    
<!--    script for Scroll - horizontal and vertical-->
    <script>
    $(document).ready(function() {
        $('#dataTables-setSchedule').DataTable({
            "scrollX": true,
            "ordering": false,
            "bPaginate": false
            
           
        });
    });
    </script>
    <!--    session for total of row-->
    <?php $_SESSION["time"] = $total; 

//    echo $start;
//    echo $end;
    
    echo $_SESSION["stt"];
    ?>
</body>

</html>
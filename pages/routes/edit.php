<?php
session_start();
include_once '../connection/dbconnect.php';

if(isset($_POST['route']) || isset($_GET['edit'])){
    include 'routes_crud.php';
}else{
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
    <style>.alert {
    padding: 10px;
    background-color: #f44336;
    color: white;
    opacity: 1;
    transition: opacity 0.6s;
    margin-bottom: 0px;
}

.alert.success {background-color: #4CAF50;}
.alert.info {background-color: #2196F3;}
.alert.warning {background-color: #ff9800;}

.closebtn {
    margin-left: 15px;
    color: white;
    font-weight: bold;
    float: right;
    font-size: 22px;
    line-height: 20px;
    cursor: pointer;
    transition: 0.3s;
}

.closebtn:hover {
    color: black;
}</style>

<script>
var close = document.getElementsByClassName("closebtn");
var i;

for (i = 0; i < close.length; i++) {
    close[i].onclick = function(){
        var div = this.parentElement;
        div.style.opacity = "0";
        setTimeout(function(){ div.style.display = "none"; }, 600);
    }
}
</script>

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
                                <li><a href="<?php echo $root ?>/routes/"><i class="glyphicon glyphicon-road"></i> Routes</a> <span class="divider"></span></li>            
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
          
            <!--            alert to tick nearest station-->
             <div class="alert warning">
              <span class="closebtn">&times;</span>  
              <strong>Warning!</strong> Please make sure you tick the nearest station to make changes.
            </div>
           
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Route</h1>
                   
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
  <form action="edit.php" method="post" class="form-horizontal">

     <div class="form-group">
        <label for="rname" class="col-sm-3 control-label">Road Name</label>
        <div class="col-sm-9">
          <input name="rname" type="text" class="form-control" id="rname" placeholder="Road Name" value="<?php if(isset($_GET['edit'])) echo $editrow['Ro_Name']; ?>" required>
        </div>
    </div>
                
      <div class="form-group">
      <label for="route" class="col-sm-3 control-label">Route</label>
      <div class="col-sm-9">
           <table>
            <?php 
         $sql = "SELECT * FROM `station` ORDER BY `S_Id` ASC";
          $result = $con->query($sql);
          while ($readrow = $result->fetch_array()) {
          ?>   
       <tr>  
          <td><?php echo $readrow['S_Name']; ?></td>  
          <td>&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="route[]" value="<?php echo $readrow['S_Code']; ?>"></td>  
       </tr>
       <?php } ?>
        </table>  
      </div>
      </div>
                 
        <div class="form-group">
        <label for="buses" class="col-sm-3 control-label">Buses</label>
        <div class="col-sm-9">
          <input name="buses" type="text" class="form-control" id="buses" placeholder="Number of buses" value="<?php if(isset($_GET['edit'])) echo $editrow['Ro_Route_Busses']; ?>" required>
        </div>
        </div>
                  
        <div class="form-group">
          <label for="status" class="col-sm-3 control-label">Status</label>
          <div class="col-sm-9">
          <div class="radio">
            <label>
            <input name="status" type="radio" id="status" value="1" <?php if(isset($_GET['edit'])) if($editrow['Ro_Active']=="1") echo "checked"; ?> required> Active
            </label>
          </div>
          <div class="radio">
            <label>
               <input name="status" type="radio" id="status" value="0" <?php if(isset($_GET['edit'])) if($editrow['Ro_Active']=="0") echo "checked"; ?>> Inactive
            </label>
            </div>
          </div>
    </div>
                   
   
    
    <div class="form-group">
          <div class="col-sm-offset-3 col-sm-9">
          <?php if (isset($_GET['edit'])) { ?>
          <input type="hidden" name="oldsid" value="<?php echo $editrow['Ro_Id']; ?>">
          <button class="btn btn-default" type="submit" name="update"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Update</button>
          <?php } else { ?>
          <button class="btn btn-default" type="submit" name="create"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Create</button>
          <?php } ?>
          <button class="btn btn-default" type="reset"><span class="glyphicon glyphicon-erase" aria-hidden="true"></span> Clear</button>
        </div>
      </div>
</form>
     <!-- /.row -->
    <!--         //Manage Roles table-->
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

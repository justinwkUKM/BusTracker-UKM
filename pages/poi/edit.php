<?php
session_start();
include_once '../connection/dbconnect.php';
if(isset($_POST['sid']) || isset($_GET['edit'])){
    include 'edit_crud.php';
}else{

    header("Location:index.php");
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

    <!-- Custom Fonts -->
    <link href="../../vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <style>
.alert {
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
                    <h1 class="page-header">Edit POI</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
  <form action="edit.php" method="post" class="form-horizontal">
    
     
      <div class="form-group">
        <label for="sname" class="col-sm-3 control-label">POI Name</label>
        <div class="col-sm-9">
          <input name="poiname" type="text" class="form-control" id="poiname" placeholder="POI Name" value="<?php if(isset($_GET['edit'])) echo $editrow['POI_Name']; ?>" required>
        </div>
    </div>
      <div class="form-group">
        <label for="address" class="col-sm-3 control-label">Code</label>
        <div class="col-sm-9">
          <input name="address" type="text" class="form-control" id="address" placeholder="POI Address" value="<?php if(isset($_GET['edit'])) echo $editrow['POI_Address']; ?>">
        </div>
    </div>

    <div class="form-group">
        <label for="lat" class="col-sm-3 control-label">Latitude</label>
        <div class="col-sm-9">
          <input name="lat" type="text" class="form-control" id="lat" placeholder="latitude" value="<?php if(isset($_GET['edit'])) echo $editrow['POI_Latitud']; ?>" required>
        </div>
    </div>
     <div class="form-group">
        <label for="lng" class="col-sm-3 control-label">Longitude</label>
        <div class="col-sm-9">
          <input name="lng" type="text" class="form-control" id="lng" placeholder="longitude" value="<?php if(isset($_GET['edit'])) echo $editrow['POI_Longitud']; ?>" required>
        </div>
    </div>
    
    <div class="form-group">
          <label for="type" class="col-sm-3 control-label">Type</label>
          <div class="col-sm-9">
         <select name="type" class="form-control" id="type" required>
            <option value="">Please select</option>
            <option value="center" <?php if(isset($_GET['edit'])) if($editrow['POI_Type']=="center") echo "selected"; ?>>Center</option>
            <option value="college" <?php if(isset($_GET['edit'])) if($editrow['POI_Type']=="college") echo "selected"; ?>>College</option>
            <option value="hall" <?php if(isset($_GET['edit'])) if($editrow['POI_Type']=="hall") echo "selected"; ?>>Hall</option>
            <option value="library" <?php if(isset($_GET['edit'])) if($editrow['POI_Type']=="library") echo "selected"; ?>>Library</option>
            <option value="health center" <?php if(isset($_GET['edit'])) if($editrow['POI_Type']=="health center") echo "selected"; ?>>Health center</option>
            <option value="faculty" <?php if(isset($_GET['edit'])) if($editrow['POI_Type']=="faculty") echo "selected"; ?>>Faculty</option>
            <option value="institute" <?php if(isset($_GET['edit'])) if($editrow['POI_Type']=="institute") echo "selected"; ?>>Institute</option>
            <option value="recreation" <?php if(isset($_GET['edit'])) if($editrow['POI_Type']=="recreation") echo "selected"; ?>>Recreation</option>
          </select>
        </div>
        </div>  
        
    
    <div class="form-group">
      <label for="route" class="col-sm-3 control-label">Nearest Station</label>
      <div class="col-sm-9">
           <table>
            <?php 
         $sql = "SELECT * FROM `station` ORDER BY `S_Id` ASC";
          $result = $con->query($sql);
          while ($readrow = $result->fetch_array()) {
          ?>   
       <tr>  
          <td><?php echo $readrow['S_Name'] ?></td>  
          <td>&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="sid[]" value="<?php echo $readrow['S_Name']; ?>"></td>  
       </tr>
       <?php } ?>
        </table>  
      </div>
      </div>
    
    <div class="form-group">
          <div class="col-sm-offset-3 col-sm-9">
      <?php if (isset($_GET['edit'])) { ?>
      <input type="hidden" name="oldsid" value="<?php echo $editrow['POI_Id']; ?>">
          <button class="btn btn-default" type="submit" name="update"  ><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Update</button>
          <?php } ?>
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
    
<!--    Alert to tick nearest station-->
    
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
   <!--    //Alert to tick nearest station-->
    

</body>

</html>

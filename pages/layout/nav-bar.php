
<?php
//include server
$root = "http://bt.sprep.me/pages";

if ($_SESSION['AD_Username'] != null) {
  $_SESSION['AD_Username'] == '';
//    echo "Your session is running " . $_SESSION['AD_Username']."<br>";
//    echo "<img alt='Admin Picture' src='http://sprep.me/bus_tracker_ukm/upload/admin_images/".$_SESSION['AD_Avatar']."'/>"; 
} 

if (!isset($_SESSION['AD_Username']) || $_SESSION['AD_Username'] == ''){
    header("Location: $root/authentication/");
}

$id = $_SESSION['AD_Id'];
$code = $_SESSION['AD_Token'];

date_default_timezone_set('Asia/Kuala_Lumpur');
$now = new DateTime();
$now = $now->format('y/m/d H:i:s');

//$logo = "$root/img/template_logo.png";
$img =  "$root/img/admin/".$_SESSION['AD_Avatar'].""; 
//$img2 = "$root/img/admin/user-placeholder.jpg";
?>
      <style>
    .navbar-brand{
        padding: 15px 15px;
    }
</style>
       
       <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="<?php $root ?>">UKM Bus Tracker</a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
               
                
               
                <!-- .dropdown -->
                <li class="dropdown">
                    
                    
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
	                		<img src="<?php echo $img ?>" width="25"> <?php echo $_SESSION['AD_Name']; ?> <b class="caret"></b>
                    </a>
                    
                     <ul class="dropdown-menu dropdown-user">
                        <li><a href="<?php echo $root ?>/profile/"><i class="fa fa-user fa-fw"></i> User Profile</a>
                        </li>
                        <li><a href="<?php echo $root ?>/changepicture/"><i class="fa fa-camera"></i>&nbsp; Change Picture</a>
                        </li>
                        <li><a href="<?php echo $root ?>/registration/changepassword.php?id=<?php echo base64_encode($_SESSION['AD_Id']) ?>&code=<?php echo  $_SESSION['AD_Token']?>"><i class="glyphicon glyphicon-lock"></i> Change Password</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="<?php echo $root ?>/logout"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li>
                            <a href="<?php echo $root ?>/"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
                        </li>
                        <li>
                            <a href="<?php echo $root ?>/manageadmins/"><i class="fa fa-sitemap fa-fw"></i> Manage Admins</a>
                        </li>
                        
                        <li>
                            <a href="../#"><i class="glyphicon glyphicon-qrcode"></i> POIs & Station <span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="<?php echo $root ?>/poi/"><i class="glyphicon glyphicon-map-marker "></i> POIs</a>
                                </li>
                                <li>
                                    <a href="<?php echo $root ?>/stations/"><i class="glyphicon glyphicon-flag"></i> Stations</a>
                                </li>
                                
                            </ul>
                            
                        </li>
                         <li>
                            <a href="../#"><i class="fa fa-info-circle"></i> Bus Information <span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="<?php echo $root ?>/buses/"><i class="glyphicon glyphicon-bed"></i>&nbsp; Buses</a>
                                </li>
                                <li>
                                    <a href="<?php echo $root ?>/drivers/"><i class="fa fa-users"></i>&nbsp; Drivers</a>
                                </li>
                                <li>
                                    <a href="<?php echo $root ?>/schedules/"><i class="glyphicon glyphicon-calendar"></i>&nbsp; Schedules</a>
                                </li>
                                <li>
                                    <a href="<?php echo $root ?>/routes/"><i class="glyphicon glyphicon-road"></i>&nbsp; Routes</a>
                                </li>
                            </ul>
                            
                        </li>
                        <!-- /.nav-second-level -->
                        <li>
                        <a href="<?php echo $root ?>/reports/"><i class="fa fa-edit fa-fw"></i> Reports</a>
                        </li>
                        <li>
                        <a href="<?php echo $root ?>/users/"><i class="glyphicon glyphicon-user"></i> Users</a>
                        </li>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>
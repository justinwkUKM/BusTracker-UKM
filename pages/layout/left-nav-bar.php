
   <html>
    
    <head></head>
    <body>
    
 <!-- Left (navigation) side -->
    <div class="navigation-block">

        <!-- User profile -->
        <section class="user-profile">
            <figure>
                <?php 

                if($_SESSION['AD_Avatar']){
                     echo "<img alt='Admin Picture' src='http://sprep.me/bus_tracker_ukm/upload/admin_images/".$_SESSION['AD_Avatar']."'/>"; 

                } else{

               echo "<img alt='Admin Picture' src='http://sprep.me/bus_tracker_ukm/upload/admin_images/663264.jpg'/>";
                }

                ?>


                <figcaption>
                    <strong><a href="#" class=""><?php echo $_SESSION['AD_Username']; ?></a></strong>
                    <em><?php echo $_SESSION['AD_Type']; ?></em>
                    <ul>
                        <li><a class="btn btn-primary btn-flat" href="http://sprep.me/bus_tracker_ukm/setting/" title="Account settings">settings</a></li>
                        <li><a class="btn btn-primary btn-flat" href="#" title="Message inbox">inbox</a></li>
                    </ul>
                </figcaption>
            </figure>
        </section>
        <!-- /User profile -->

        <!-- Sample left search bar -->
<!--
        <form class="side-search">
            <input type="text" class="rounded" placeholder="To search type and hit enter">
        </form>
-->
        <!-- /Sample left search bar -->

        <!-- Main navigation -->
        <nav class="main-navigation" role="navigation">
            <ul>
                <li class="current"><a href="http://sprep.me/bus_tracker_ukm/index.php" class="no-submenu"><span class="awe-home"></span>Dashboard</a></li>
                <li><a href="http://sprep.me/bus_tracker_ukm/manageroles/" class="no-submenu"><span class="awe-table"></span>Manage Roles</a></li>
                <li>
                    <a href="http://sprep.me/bus_tracker_ukm/poi/"><span class="awe-map-marker"></span>POIs</a>
                    <ul>
                        <li><a href="http://sprep.me/bus_tracker_ukm/directories/">Directories </a></li>
                        <li><a href="http://sprep.me/bus_tracker_ukm/stations/">Stations</a></li>
                    </ul>
                </li>
               <li>
                    <a href="#"><span class="awe-info-sign"></span>Bus Information</a>
                    <ul>
                        <li><a href="http://sprep.me/bus_tracker_ukm/buses/">Buses</a></li>
                        <li><a href="http://sprep.me/bus_tracker_ukm/drivers/">Drivers</a></li>
                        <li><a href="http://sprep.me/bus_tracker_ukm/schedules/">Schedules</a></li>
                        <li><a href="http://sprep.me/bus_tracker_ukm/realtime/">Real Time</a></li>
                    </ul>
                </li>
                <li><a href="#" class="no-submenu"><span class="awe-user"></span>Users</a></li>
                <li><a href="http://sprep.me/bus_tracker_ukm/report/" class="no-submenu"><span class="awe-book"></span>Reports (TBA)</a></li>

            </ul>
        </nav>
        <!-- /Main navigation -->

        <!-- Sample side note -->
<!--
        <section class="side-note">
            <div class="side-note-container">
                <h2>Sample Side Note</h2>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus quis erat dui, quis purus.</p>
            </div>
            <div class="side-note-bottom"></div>
        </section>
-->
        <!-- /Sample side note -->

    </div>
    <!-- Left (navigation) side -->
			
  </body>
    
</html>
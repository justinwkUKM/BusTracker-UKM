$(document).ready(function() {

	var mapCenter = new google.maps.LatLng(2.925587, 101.781526); //Google map Coordinates
	var map;
	
	map_initialize(); // initialize google map
	
	//############### Google Map Initialize ##############
	function map_initialize()
	{
			var googleMapOptions = 
			{ 
				center: mapCenter, // map center
				zoom: 14, //zoom level, 0 = earth view to higher value
				maxZoom: 19,
				minZoom: 14,
				zoomControlOptions: {
				style: google.maps.ZoomControlStyle.SMALL //zoom control size
			},
				scaleControl: true, // enable scale control
				mapTypeId: google.maps.MapTypeId.ROADMAP // google map type
			};
		
		   	map = new google.maps.Map(document.getElementById("google_map"), googleMapOptions);	
	}
	
	//############### Create Marker Function ##############
	function create_marker(MapPos, MapTitle, MapDesc,  InfoOpenDefault, DragAble, Removable, iconPath)
	{	  	  		  
		
		//new marker
		var marker = new google.maps.Marker({
			position: MapPos,
			map: map,
			draggable:DragAble,
			//animation: google.maps.Animation.DROP,
			title:"UKM Bus Tracker",
			icon: iconPath
		});
        
        return marker;
	}
	
	
	 markerRefresh();
    var busMarker;
    var busss;
    var bus
            
     function markerRefresh() {
         
         
         
       if (busMarker != null){
        busMarker.setMap(null);
        }     

         setTimeout ( function(){  
         //Load Markers from the XML File, Check (map_process_bus.php)
			$.get("http://bt.sprep.me/pages/map_process_bus.php", function (data) {
                
				$(data).find("marker").each(function () {
					   bus = $(this).attr('bus');
//                    console.log(bus);
                    if (bus == "3"){
                
                        busss = "http://bt.sprep.me/pages/img/icons/marker1.png";
                        console.log(4);
                    }
                    else if (bus == "6") {
                        busss = "http://bt.sprep.me/pages/img/icons/marker2.png";
                        console.log(6);
                    }
                    else if (bus == "2") {
                        busss = "http://bt.sprep.me/pages/img/icons/marker3.png";
                        console.log(6);
                    }
                    
					  var route 	= '<p>'+ $(this).attr('route') +'</p>';
					  var driver 		= $(this).attr('driver');
					  var point 	= new google.maps.LatLng(parseFloat($(this).attr('lat')),parseFloat($(this).attr('lon')));
					busMarker = create_marker(point, bus, route, false, false, false, busss);
                }, function(status){
				});
			});	
             
             
            markerRefresh();
        },3000);}
    
    });

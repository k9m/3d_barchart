<?php
$names = array("Other","Media","TV","Papers","Internet");
$values = array("100","1000","2000","3005","1236");
$zoom = "1";
$units = "100";


$names = implode(",",$names);
$values = implode(",",$values);


?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>BarChart</title>

<link type="text/css" href="css/Style.css" rel="stylesheet" />
<link type="text/css"
	href="css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script type="text/javascript" src="js/jQuery.js"></script>
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>

<style>
#demo-frame>div.demo {
	padding: 10px !important;
}
</style>
<script>
	$(document).ready(function() {

		//Slider for Zoom
		$( "#slider" ).slider({
			value:1,
			min: 1,
			max: 10,			
			animate: true,
			orientation : 'vertical',
			slide: function( event, ui ) {
				$( "#amount" ).val( ui.value );				
			}
		});		
		$( "#amount" ).val( $( "#slider" ).slider( "value" ) );

		//Slider for Units
		$( "#sliderunit" ).slider({
			value:10,
			min: 0,
			max: 1000,
			step: 100,
			animate: true,
			orientation : 'vertical',
			slide: function( event, ui ) {
				$( "#amountunit" ).val( ui.value );				
			}
		});
		$( "#amountunit" ).val( $( "#sliderunit" ).slider( "value" ) );
			     
	    //if submit button is clicked
	    $('#submit').click(function () {         
	    	doAjax();	    	
	    });

		//Helper method do Ajax Request
	    function doAjax() {    	        
			
	    	//start the ajax
	        $.ajax({
	            //this is the php file that processes the data and send mail
	            url: "chart.php",
	             
	            //GET method is used
	           type: "POST",
	 
	            //pass the data        
	            data: {	            	
	            	zoom : $( "#slider" ).slider( "value" ),
	            	units : $( "#sliderunit" ).slider( "value" ),
	            	<?php
	            	
	            	//echo 'zoom : "'.$zoom.'",';
	            	//echo 'units : "'.$units.'",';
	            	echo 'names : "'.$names.'",';
	            	echo 'values : "'.$values.'" ';
	            	?>
	            },    
	             
	            //Do not cache the page
	            cache: false,
	             
	            //success
	            success: function (html) {             
	                $('#Results').html(html);	                
	            }      
	        });
	         
	        //cancel the submit button default behaviours
	        return false;
	    	}
	});     
	</script>

</head>
<body>

	<div id="BodyWrapper">

		<div id="SliderPanel">
			<a id="submit" href="#">SUBMIT</a>
			<div style="float: left; margin-bottom: 20px;">
				<p>
					<label for="amount">Zoom:</label> <input type="text" id="amount"
						style="border: 0; color: #f6931f; font-weight: bold;" />
				</p>

				<div id="slider" ></div>
			</div>

			<div style="float: left;"></div>
			<p>
				<label for="amountunit">Units:</label> <input type="text"
					id="amountunit"
					style="border: 0; color: #f6931f; font-weight: bold;" />
			</p>
			<div id="sliderunit"></div>
		</div>

		<div id="Main">
			<div id="Results"></div>
		</div>




	</div>
</body>
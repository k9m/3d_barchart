$(document).ready(function() {

	$( "#slider" ).slider({
		value:10,
		min: 1,
		max: 10,
		step: 1,
		orientation : 'vertical',
		slide: function( event, ui ) {
			$( "#amount" ).val( ui.value );
			doAjax();
		}
	});
	$( "#amount" ).val( $( "#slider" ).slider( "value" ) );

	$( "#sliderunit" ).slider({
		value:10,
		min: 10,
		max: 100,
		step: 10,
		orientation : 'vertical',
		slide: function( event, ui ) {
			$( "#amountunit" ).val( ui.value );
			doAjax();
		}
	});
	$( "#amountunit" ).val( $( "#sliderunit" ).slider( "value" ) );

	//if submit button is clicked
	$('#submit').click(function () {         
		doAjax();	    	
	});

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


<?php 
$units = $_POST['units'];
$zoom = $_POST['zoom'];
$values = explode (",",$_POST['values']);
$names = explode (",",$_POST['names']);
?>
<object type="application/x-java-applet" name="accessName" width="400"
height="700">
<param name="code" value="ChartApplet.class" />
<param name="archive" value="applet.jar" />
<param name="scriptable" value="true" />
<param name="mayscript" value="true" />

<PARAM name="backgroundR" value="255">
<PARAM name="backgroundG" value="255">
<PARAM name="backgroundB" value="255">

<PARAM name="units" value="<?php echo $units?>">
<PARAM name="zoom" value="<?php echo $zoom?>">

<?php 
foreach($names as $key=>$value){
	echo '<PARAM name="columnName'.$key.'" value="'.$value.'">';
}
?>

<?php 
foreach($values as $key=>$value){
	echo '<PARAM name="columnValue'.$key.'" value="'.$value.'">';
}
?>

</object>


<?php
/* @var $this GpsdataController */
/* @var $model Gpsdata */

$this->breadcrumbs=array(
	'Gpsdatas'=>array('index'),
	'Create',
);

$this->menu=array(
	array('label'=>'List Gpsdata', 'url'=>array('index')),
	array('label'=>'Manage Gpsdata', 'url'=>array('admin')),
);
?>

<h1>Create Gpsdata</h1>

<?php $this->renderPartial('_form', array('model'=>$model)); ?>
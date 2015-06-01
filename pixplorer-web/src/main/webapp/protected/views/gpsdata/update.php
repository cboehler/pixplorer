<?php
/* @var $this GpsdataController */
/* @var $model Gpsdata */

$this->breadcrumbs=array(
	'Gpsdatas'=>array('index'),
	$model->id=>array('view','id'=>$model->id),
	'Update',
);

$this->menu=array(
	array('label'=>'List Gpsdata', 'url'=>array('index')),
	array('label'=>'Create Gpsdata', 'url'=>array('create')),
	array('label'=>'View Gpsdata', 'url'=>array('view', 'id'=>$model->id)),
	array('label'=>'Manage Gpsdata', 'url'=>array('admin')),
);
?>

<h1>Update Gpsdata <?php echo $model->id; ?></h1>

<?php $this->renderPartial('_form', array('model'=>$model)); ?>
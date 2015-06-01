<?php
/* @var $this TrophiesController */
/* @var $model Trophies */

$this->breadcrumbs=array(
	'Trophies'=>array('index'),
	$model->name,
);

$this->menu=array(
	array('label'=>'List Trophies', 'url'=>array('index')),
	array('label'=>'Create Trophies', 'url'=>array('create')),
	array('label'=>'Update Trophies', 'url'=>array('update', 'id'=>$model->id)),
	array('label'=>'Delete Trophies', 'url'=>'#', 'linkOptions'=>array('submit'=>array('delete','id'=>$model->id),'confirm'=>'Are you sure you want to delete this item?')),
	array('label'=>'Manage Trophies', 'url'=>array('admin')),
);
?>

<h1>View Trophies #<?php echo $model->id; ?></h1>

<?php $this->widget('zii.widgets.CDetailView', array(
	'data'=>$model,
	'attributes'=>array(
		'id',
		'name',
		'description',
		'code',
	),
)); ?>

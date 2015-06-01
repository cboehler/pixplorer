<?php
/* @var $this UsertrophymappingController */
/* @var $model Usertrophymapping */

$this->breadcrumbs=array(
	'Usertrophymappings'=>array('index'),
	$model->id,
);

$this->menu=array(
	array('label'=>'List Usertrophymapping', 'url'=>array('index')),
	array('label'=>'Create Usertrophymapping', 'url'=>array('create')),
	array('label'=>'Update Usertrophymapping', 'url'=>array('update', 'id'=>$model->id)),
	array('label'=>'Delete Usertrophymapping', 'url'=>'#', 'linkOptions'=>array('submit'=>array('delete','id'=>$model->id),'confirm'=>'Are you sure you want to delete this item?')),
	array('label'=>'Manage Usertrophymapping', 'url'=>array('admin')),
);
?>

<h1>View Usertrophymapping #<?php echo $model->id; ?></h1>

<?php $this->widget('zii.widgets.CDetailView', array(
	'data'=>$model,
	'attributes'=>array(
		'id',
		'user_id',
		'trophy_id',
	),
)); ?>

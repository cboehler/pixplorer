<?php
/* @var $this WiUserController */
/* @var $model WiUser */

$this->breadcrumbs=array(
	'Wi Users'=>array('index'),
	$model->id,
);

$this->menu=array(
	array('label'=>'List WiUser', 'url'=>array('index')),
	array('label'=>'Create WiUser', 'url'=>array('create')),
	array('label'=>'Update WiUser', 'url'=>array('update', 'id'=>$model->id)),
	array('label'=>'Delete WiUser', 'url'=>'#', 'linkOptions'=>array('submit'=>array('delete','id'=>$model->id),'confirm'=>'Are you sure you want to delete this item?')),
	array('label'=>'Manage WiUser', 'url'=>array('admin')),
);
?>

<h1>View WiUser #<?php echo $model->id; ?></h1>

<?php $this->widget('zii.widgets.CDetailView', array(
	'data'=>$model,
	'attributes'=>array(
		'id',
		'username',
		'password',
		'email',
	),
)); ?>

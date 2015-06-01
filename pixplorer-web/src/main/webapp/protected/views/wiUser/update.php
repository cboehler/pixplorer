<?php
/* @var $this WiUserController */
/* @var $model WiUser */

$this->breadcrumbs=array(
	'Wi Users'=>array('index'),
	$model->id=>array('view','id'=>$model->id),
	'Update',
);

$this->menu=array(
	array('label'=>'List WiUser', 'url'=>array('index')),
	array('label'=>'Create WiUser', 'url'=>array('create')),
	array('label'=>'View WiUser', 'url'=>array('view', 'id'=>$model->id)),
	array('label'=>'Manage WiUser', 'url'=>array('admin')),
);
?>

<h1>Update WiUser <?php echo $model->id; ?></h1>

<?php $this->renderPartial('_form', array('model'=>$model)); ?>
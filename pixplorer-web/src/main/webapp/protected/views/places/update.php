<?php
/* @var $this PlacesController */
/* @var $model Places */

$this->breadcrumbs=array(
	'Places'=>array('index'),
	$model->name=>array('view','id'=>$model->id),
	'Update',
);

$this->menu=array(
	array('label'=>'List Places', 'url'=>array('index')),
	array('label'=>'Create Places', 'url'=>array('create')),
	array('label'=>'View Places', 'url'=>array('view', 'id'=>$model->id)),
	array('label'=>'Manage Places', 'url'=>array('admin')),
);
?>

<h1>Update Places <?php echo $model->id; ?></h1>

<?php $this->renderPartial('_form', array('model'=>$model)); ?>
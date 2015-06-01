<?php
/* @var $this TrophiesController */
/* @var $model Trophies */

$this->breadcrumbs=array(
	'Trophies'=>array('index'),
	$model->name=>array('view','id'=>$model->id),
	'Update',
);

$this->menu=array(
	array('label'=>'List Trophies', 'url'=>array('index')),
	array('label'=>'Create Trophies', 'url'=>array('create')),
	array('label'=>'View Trophies', 'url'=>array('view', 'id'=>$model->id)),
	array('label'=>'Manage Trophies', 'url'=>array('admin')),
);
?>

<h1>Update Trophies <?php echo $model->id; ?></h1>

<?php $this->renderPartial('_form', array('model'=>$model)); ?>
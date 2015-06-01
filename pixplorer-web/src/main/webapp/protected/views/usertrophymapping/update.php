<?php
/* @var $this UsertrophymappingController */
/* @var $model Usertrophymapping */

$this->breadcrumbs=array(
	'Usertrophymappings'=>array('index'),
	$model->id=>array('view','id'=>$model->id),
	'Update',
);

$this->menu=array(
	array('label'=>'List Usertrophymapping', 'url'=>array('index')),
	array('label'=>'Create Usertrophymapping', 'url'=>array('create')),
	array('label'=>'View Usertrophymapping', 'url'=>array('view', 'id'=>$model->id)),
	array('label'=>'Manage Usertrophymapping', 'url'=>array('admin')),
);
?>

<h1>Update Usertrophymapping <?php echo $model->id; ?></h1>

<?php $this->renderPartial('_form', array('model'=>$model)); ?>
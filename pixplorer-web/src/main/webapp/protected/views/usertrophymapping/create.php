<?php
/* @var $this UsertrophymappingController */
/* @var $model Usertrophymapping */

$this->breadcrumbs=array(
	'Usertrophymappings'=>array('index'),
	'Create',
);

$this->menu=array(
	array('label'=>'List Usertrophymapping', 'url'=>array('index')),
	array('label'=>'Manage Usertrophymapping', 'url'=>array('admin')),
);
?>

<h1>Create Usertrophymapping</h1>

<?php $this->renderPartial('_form', array('model'=>$model)); ?>
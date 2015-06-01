<?php
/* @var $this TrophiesController */
/* @var $model Trophies */

$this->breadcrumbs=array(
	'Trophies'=>array('index'),
	'Create',
);

$this->menu=array(
	array('label'=>'List Trophies', 'url'=>array('index')),
	array('label'=>'Manage Trophies', 'url'=>array('admin')),
);
?>

<h1>Create Trophies</h1>

<?php $this->renderPartial('_form', array('model'=>$model)); ?>
<?php
/* @var $this WiUserController */
/* @var $model WiUser */

$this->breadcrumbs=array(
	'Wi Users'=>array('index'),
	'Create',
);

$this->menu=array(
	array('label'=>'List WiUser', 'url'=>array('index')),
	array('label'=>'Manage WiUser', 'url'=>array('admin')),
);
?>

<h1>Create WiUser</h1>

<?php $this->renderPartial('_form', array('model'=>$model)); ?>
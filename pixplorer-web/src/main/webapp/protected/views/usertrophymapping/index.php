<?php
/* @var $this UsertrophymappingController */
/* @var $dataProvider CActiveDataProvider */

$this->breadcrumbs=array(
	'Usertrophymappings',
);

$this->menu=array(
	array('label'=>'Create Usertrophymapping', 'url'=>array('create')),
	array('label'=>'Manage Usertrophymapping', 'url'=>array('admin')),
);
?>

<h1>Usertrophymappings</h1>

<?php $this->widget('zii.widgets.CListView', array(
	'dataProvider'=>$dataProvider,
	'itemView'=>'_view',
)); ?>

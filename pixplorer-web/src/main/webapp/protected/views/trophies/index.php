<?php
/* @var $this TrophiesController */
/* @var $dataProvider CActiveDataProvider */

$this->breadcrumbs=array(
	'Trophies',
);

$this->menu=array(
	array('label'=>'Create Trophies', 'url'=>array('create')),
	array('label'=>'Manage Trophies', 'url'=>array('admin')),
);
?>

<h1>Trophies</h1>

<?php $this->widget('zii.widgets.CListView', array(
	'dataProvider'=>$dataProvider,
	'itemView'=>'_view',
)); ?>

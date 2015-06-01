<?php
/* @var $this GpsdataController */
/* @var $dataProvider CActiveDataProvider */

$this->breadcrumbs=array(
	'Gpsdatas',
);

$this->menu=array(
	array('label'=>'Create Gpsdata', 'url'=>array('create')),
	array('label'=>'Manage Gpsdata', 'url'=>array('admin')),
);
?>

<h1>Gpsdatas</h1>

<?php $this->widget('zii.widgets.CListView', array(
	'dataProvider'=>$dataProvider,
	'itemView'=>'_view',
)); ?>

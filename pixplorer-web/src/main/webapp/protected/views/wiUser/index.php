<?php
/* @var $this WiUserController */
/* @var $dataProvider CActiveDataProvider */

$this->breadcrumbs=array(
	'Wi Users',
);

$this->menu=array(
	array('label'=>'Create WiUser', 'url'=>array('create')),
	array('label'=>'Manage WiUser', 'url'=>array('admin')),
);
?>

<h1>Wi Users</h1>

<?php $this->widget('zii.widgets.CListView', array(
	'dataProvider'=>$dataProvider,
	'itemView'=>'_view',
)); ?>

<?php
/* @var $this FavouritesController */
/* @var $dataProvider CActiveDataProvider */

$this->breadcrumbs=array(
	'Favourites',
);

$this->menu=array(
	array('label'=>'Create Favourites', 'url'=>array('create')),
	array('label'=>'Manage Favourites', 'url'=>array('admin')),
);
?>

<h1>Favourites</h1>

<?php $this->widget('zii.widgets.CListView', array(
	'dataProvider'=>$dataProvider,
	'itemView'=>'_view',
)); ?>

<?php
/* @var $this FavouritesController */
/* @var $model Favourites */

$this->breadcrumbs=array(
	'Favourites'=>array('index'),
	$model->id,
);

$this->menu=array(
	array('label'=>'List Favourites', 'url'=>array('index')),
	array('label'=>'Create Favourites', 'url'=>array('create')),
	array('label'=>'Update Favourites', 'url'=>array('update', 'id'=>$model->id)),
	array('label'=>'Delete Favourites', 'url'=>'#', 'linkOptions'=>array('submit'=>array('delete','id'=>$model->id),'confirm'=>'Are you sure you want to delete this item?')),
	array('label'=>'Manage Favourites', 'url'=>array('admin')),
);
?>

<h1>View Favourites #<?php echo $model->id; ?></h1>

<?php $this->widget('zii.widgets.CDetailView', array(
	'data'=>$model,
	'attributes'=>array(
		'id',
		'user_id',
		'place_id',
	),
)); ?>

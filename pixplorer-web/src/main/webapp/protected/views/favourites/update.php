<?php
/* @var $this FavouritesController */
/* @var $model Favourites */

$this->breadcrumbs=array(
	'Favourites'=>array('index'),
	$model->id=>array('view','id'=>$model->id),
	'Update',
);

$this->menu=array(
	array('label'=>'List Favourites', 'url'=>array('index')),
	array('label'=>'Create Favourites', 'url'=>array('create')),
	array('label'=>'View Favourites', 'url'=>array('view', 'id'=>$model->id)),
	array('label'=>'Manage Favourites', 'url'=>array('admin')),
);
?>

<h1>Update Favourites <?php echo $model->id; ?></h1>

<?php $this->renderPartial('_form', array('model'=>$model)); ?>
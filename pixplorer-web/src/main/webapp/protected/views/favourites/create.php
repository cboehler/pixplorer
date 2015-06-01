<?php
/* @var $this FavouritesController */
/* @var $model Favourites */

$this->breadcrumbs=array(
	'Favourites'=>array('index'),
	'Create',
);

$this->menu=array(
	array('label'=>'List Favourites', 'url'=>array('index')),
	array('label'=>'Manage Favourites', 'url'=>array('admin')),
);
?>

<h1>Create Favourites</h1>

<?php $this->renderPartial('_form', array('model'=>$model)); ?>
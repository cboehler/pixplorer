<?php

/* @var $this PlacesController */
/* @var $model Places */

$this->breadcrumbs = array(
	'Places' => array('index'),
	$model->name,
);

$this->menu = array(
	array('label' => 'List Places', 'url' => array('index')),
	array('label' => 'Create Places', 'url' => array('create')),
	array('label' => 'Update Places', 'url' => array('update', 'id' => $model->id)),
	array('label' => 'Delete Places', 'url' => '#', 'linkOptions' => array('submit' => array('delete', 'id' => $model->id), 'confirm' => 'Are you sure you want to delete this item?')),
	array('label' => 'Manage Places', 'url' => array('admin')),
);
?>

<h1>View Places #<?php echo $model->id;?></h1>

<?php $this->widget('zii.widgets.CDetailView', array(
	'data' => $model,
	'attributes' => array(
		'id',
		'name',
		'wikilink',
		'picture',
		'score',
		'modification_date',
		'featured',
		'users_found',
		'gpsdata',
		'category',
	),
));?>

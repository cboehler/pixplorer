<?php
/* @var $this PlacesController */
/* @var $data Places */
?>

<div class="view">

	<b><?php echo CHtml::encode($data->getAttributeLabel('id'));?>:</b>
	<?php echo CHtml::link(CHtml::encode($data->id), array('view', 'id' => $data->id));?>
	<br />

	<b><?php echo CHtml::encode($data->getAttributeLabel('name'));?>:</b>
	<?php echo CHtml::encode($data->name);?>
	<br />

	<b><?php echo CHtml::encode($data->getAttributeLabel('wikilink'));?>:</b>
	<?php echo CHtml::encode($data->wikilink);?>
	<br />

	<b><?php echo CHtml::encode($data->getAttributeLabel('picture'));?>:</b>
	<?php echo CHtml::encode($data->picture);?>
	<br />

	<b><?php echo CHtml::encode($data->getAttributeLabel('score'));?>:</b>
	<?php echo CHtml::encode($data->score);?>
	<br />

	<b><?php echo CHtml::encode($data->getAttributeLabel('modification_date'));?>:</b>
	<?php echo CHtml::encode($data->modification_date);?>
	<br />

	<b><?php echo CHtml::encode($data->getAttributeLabel('featured'));?>:</b>
	<?php echo CHtml::encode($data->featured);?>
	<br />

	<?php /*
<b><?php echo CHtml::encode($data->getAttributeLabel('users_found')); ?>:</b>
<?php echo CHtml::encode($data->users_found); ?>
<br />

<b><?php echo CHtml::encode($data->getAttributeLabel('gpsdata')); ?>:</b>
<?php echo CHtml::encode($data->gpsdata); ?>
<br />

<b><?php echo CHtml::encode($data->getAttributeLabel('category')); ?>:</b>
<?php echo CHtml::encode($data->category); ?>
<br />

 */?>

</div>
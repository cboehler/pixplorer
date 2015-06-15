<?php
/* @var $this UsersController */
/* @var $data Users */
?>

<div class="view">

	<b><?php echo CHtml::encode($data->getAttributeLabel('id')); ?>:</b>
	<?php echo CHtml::link(CHtml::encode($data->id), array('view', 'id'=>$data->id)); ?>
	<br />

	<b><?php echo CHtml::encode($data->getAttributeLabel('google_id')); ?>:</b>
	<?php echo CHtml::encode($data->google_id); ?>
	<br />

	<b><?php echo CHtml::encode($data->getAttributeLabel('user_type')); ?>:</b>
	<?php echo CHtml::encode($data->user_type); ?>
	<br />

	<b><?php echo CHtml::encode($data->getAttributeLabel('score')); ?>:</b>
	<?php echo CHtml::encode($data->score); ?>
	<br />

	<b><?php echo CHtml::encode($data->getAttributeLabel('admin')); ?>:</b>
	<?php echo CHtml::encode($data->admin); ?>
	<br />


</div>
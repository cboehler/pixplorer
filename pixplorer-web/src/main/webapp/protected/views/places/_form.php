<?php
/* @var $this PlacesController */
/* @var $model Places */
/* @var $form CActiveForm */
?>

<div class="form">

<?php $form = $this->beginWidget('CActiveForm', array(
	'id' => 'places-form',
	// Please note: When you enable ajax validation, make sure the corresponding
	// controller action is handling ajax validation correctly.
	// There is a call to performAjaxValidation() commented in generated controller code.
	// See class documentation of CActiveForm for details on this.
	'enableAjaxValidation' => false,
));?>

	<p class="note">Fields with <span class="required">*</span> are required.</p>

	<?php echo $form->errorSummary($model);?>

	<div class="row">
		<?php echo $form->labelEx($model, 'name');?>
		<?php echo $form->textArea($model, 'name', array('rows' => 6, 'cols' => 50));?>
		<?php echo $form->error($model, 'name');?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model, 'wikilink');?>
		<?php echo $form->textArea($model, 'wikilink', array('rows' => 6, 'cols' => 50));?>
		<?php echo $form->error($model, 'wikilink');?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model, 'picture');?>
		<?php echo $form->textArea($model, 'picture', array('rows' => 6, 'cols' => 50));?>
		<?php echo $form->error($model, 'picture');?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model, 'score');?>
		<?php echo $form->textField($model, 'score');?>
		<?php echo $form->error($model, 'score');?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model, 'modification_date');?>
		<?php echo $form->textField($model, 'modification_date');?>
		<?php echo $form->error($model, 'modification_date');?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model, 'featured');?>
		<?php echo $form->checkBox($model, 'featured');?>
		<?php echo $form->error($model, 'featured');?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model, 'users_found');?>
		<?php echo $form->textField($model, 'users_found');?>
		<?php echo $form->error($model, 'users_found');?>
	</div>

	<!--<div class="row">
		<?php echo $form->labelEx($model, 'gpsdata');?>
		<?php echo $form->textField($model, 'gpsdata');?>
		<?php echo $form->error($model, 'gpsdata');?>
	</div>-->
	<div class="row">
		<?php echo CHtml::label('latitude', 'Latitude');?>
		<?php echo CHtml::textField('latitude');?>
	</div>
	<div class="row">
		<?php echo CHtml::label('longitude', 'Longitude');?>
		<?php echo CHtml::textField('longitude');?>
	</div>
	<div class="row">
		<?php echo $form->labelEx($model, 'category');?>
		<?php
$role_list = CHtml::listData(Categories::model()->findAll(), 'id', 'name');
$options = array(
	'tabindex' => '0',
	'empty' => '(not set)',
);
?>
		<?php echo $form->dropDownList($model, 'category', $role_list, $options);?>
		<?php echo $form->error($model, 'category');?>
	</div>

	<div class="row buttons">
		<?php echo CHtml::submitButton($model->isNewRecord ? 'Create' : 'Save');?>
	</div>

<?php $this->endWidget();?>

</div><!-- form -->
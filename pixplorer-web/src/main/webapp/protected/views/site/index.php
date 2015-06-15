<?php
/* @var $this SiteController */

$this->pageTitle = Yii::app()->name;
?>

<h1>Welcome to <i><?php echo CHtml::encode(Yii::app()->name);?></i></h1>


<p> What would you like to change? </p>

<ul>
	<li> <a href="http://localhost/pixplorer/index.php?r=Places">Places</a> </li>
	<li> <a href="http://localhost/pixplorer/index.php?r=WiUser">WiUser</a> </li>
	<li> <a href="http://localhost/pixplorer/index.php?r=Categories">Categories</a> </li>
	<li> <a href="http://localhost/pixplorer/index.php?r=Trophies">Trophies</a> </li>

</ul>

<!--

<p>Congratulations! You have successfully created your Yii application.</p>

<p>You may change the content of this page by modifying the following two files:</p>
<ul>
	<li>View file: <code><?php echo __FILE__;?></code></li>
	<li>Layout file: <code><?php echo $this->getLayoutFile('main');?></code></li>
</ul>



<p>For more details on how to further develop this application, please read
the <a href="http://localhost/pixplorer/index.php?r=Places">documentation</a>.
Feel free to ask in the <a href="http://www.yiiframework.com/forum/">forum</a>,
should you have any questions.</p>

-->

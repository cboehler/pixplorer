<?php

/**
 * This is the model class for table "places".
 *
 * The followings are the available columns in table 'places':
 * @property integer $id
 * @property string $name
 * @property string $wikilink
 * @property string $picture
 * @property integer $score
 * @property string $modification_date
 * @property boolean $featured
 * @property integer $users_found
 * @property integer $gpsdata
 * @property integer $category
 *
 * The followings are the available model relations:
 * @property Categories $category0
 * @property Gpsdata $gpsdata0
 * @property History[] $histories
 * @property Favourites[] $favourites
 */
class Places extends CActiveRecord {
	/**
	 * @return string the associated database table name
	 */
	public function tableName() {
		return 'places';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules() {
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('name, wikilink, picture, score, modification_date, featured, users_found, category', 'required'),
			array('score, users_found', 'numerical', 'integerOnly' => true),
			// The following rule is used by search().
			// @todo Please remove those attributes that should not be searched.
			array('id, name, wikilink, picture, score, modification_date, featured, users_found, gpsdata, category', 'safe', 'on' => 'search'),
		);
	}

	/**
	 * @return array relational rules.
	 */
	public function relations() {
		// NOTE: you may need to adjust the relation name and the related
		// class name for the relations automatically generated below.
		return array(
			'category0' => array(self::BELONGS_TO, 'Categories', 'category'),
			'gpsdata0' => array(self::BELONGS_TO, 'Gpsdata', 'gpsdata'),
			'histories' => array(self::HAS_MANY, 'History', 'place_id'),
			'favourites' => array(self::HAS_MANY, 'Favourites', 'place_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels() {
		return array(
			'id' => 'ID',
			'name' => 'Name',
			'wikilink' => 'Wikilink',
			'picture' => 'Picture',
			'score' => 'Score',
			'modification_date' => 'Modification Date',
			'featured' => 'Featured',
			'users_found' => 'Users Found',
			'gpsdata' => 'Gpsdata',
			'category' => 'Category',
		);
	}

	/**
	 * Retrieves a list of models based on the current search/filter conditions.
	 *
	 * Typical usecase:
	 * - Initialize the model fields with values from filter form.
	 * - Execute this method to get CActiveDataProvider instance which will filter
	 * models according to data in model fields.
	 * - Pass data provider to CGridView, CListView or any similar widget.
	 *
	 * @return CActiveDataProvider the data provider that can return the models
	 * based on the search/filter conditions.
	 */
	public function search() {
		// @todo Please modify the following code to remove attributes that should not be searched.

		$criteria = new CDbCriteria;

		$criteria->compare('id', $this->id);
		$criteria->compare('name', $this->name, true);
		$criteria->compare('wikilink', $this->wikilink, true);
		$criteria->compare('picture', $this->picture, true);
		$criteria->compare('score', $this->score);
		$criteria->compare('modification_date', $this->modification_date, true);
		$criteria->compare('featured', $this->featured);
		$criteria->compare('users_found', $this->users_found);
		$criteria->compare('gpsdata', $this->gpsdata);
		$criteria->compare('category', $this->category);

		return new CActiveDataProvider($this, array(
			'criteria' => $criteria,
		));
	}

	/**
	 * Returns the static model of the specified AR class.
	 * Please note that you should have this exact method in all your CActiveRecord descendants!
	 * @param string $className active record class name.
	 * @return Places the static model class
	 */
	public static function model($className = __CLASS__) {
		return parent::model($className);
	}
}

--
-- Script for creating the EquisseDB and its relations
-- Created by CBO
-- ATTENTION: Do not generate this script with Enterprise Architect!!!!!
-- The result will not be the same.
-- 

-- create the equisse user if it doesn't exist.
do 
$body$
declare 
  num_users integer;
begin
   SELECT count(*) 
     into num_users
   FROM pg_user
   WHERE usename = 'equisse';

   IF num_users = 0 THEN
	  CREATE ROLE equisse LOGIN
		  ENCRYPTED PASSWORD 'md5f3beebb3fd7dab8f98dae6b4723fafe4'
		  NOSUPERUSER NOINHERIT CREATEDB NOCREATEROLE NOREPLICATION;
   END IF;
end
$body$
;

-- Drop the database if it is already existing 
DROP DATABASE IF EXISTS "EquisseDB";

-- now create a new database and set the owner to equisse
CREATE DATABASE "EquisseDB"
  WITH OWNER = equisse
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       CONNECTION LIMIT = -1;

-- connect after creating to add all sequences, tables, ...
\connect "EquisseDB"

-- create all sequences (needed for the automatic generation of a new primary key)
-- and grant all permissions to the equisse user
CREATE SEQUENCE cuvettes_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE cuvettes_id_seq TO equisse;

CREATE SEQUENCE cuvettewaterblanks_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE cuvettewaterblanks_id_seq TO equisse;

CREATE SEQUENCE waterblankmapping_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE waterblankmapping_id_seq TO equisse;

CREATE SEQUENCE departments_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE departments_id_seq TO equisse;

CREATE SEQUENCE doctors_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE doctors_id_seq TO equisse;

CREATE SEQUENCE hospitals_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE hospitals_id_seq TO equisse;

CREATE SEQUENCE messageprotocol_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE messageprotocol_id_seq TO equisse;

CREATE SEQUENCE patients_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE patients_id_seq TO equisse;

CREATE SEQUENCE testresults_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE testresults_id_seq TO equisse;

CREATE SEQUENCE samples_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE samples_id_seq TO equisse;

CREATE SEQUENCE calibrationresults_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE calibrationresults_id_seq TO equisse;

CREATE SEQUENCE calibrationdefinition_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE calibrationdefinition_id_seq TO equisse;

CREATE SEQUENCE calibratorconcentrationmatrix_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE calibratorconcentrationmatrix_id_seq TO equisse;

CREATE SEQUENCE calibrator_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE calibrator_id_seq TO equisse;

CREATE SEQUENCE control_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE control_id_seq TO equisse;

CREATE SEQUENCE controlconcentrationmatrix_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE controlconcentrationmatrix_id_seq TO equisse;

CREATE SEQUENCE qualitycontrolresults_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE qualitycontrolresults_id_seq TO equisse;

CREATE SEQUENCE qualitycontroldefinition_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE qualitycontroldefinition_id_seq TO equisse;

CREATE SEQUENCE calculatedtests_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE calculatedtests_id_seq TO equisse;

CREATE SEQUENCE carryover_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE carryover_id_seq TO equisse;

CREATE SEQUENCE profilemapping_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE profilemapping_id_seq TO equisse;

CREATE SEQUENCE calresultsmapping_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE calresultsmapping_id_seq TO equisse;

CREATE SEQUENCE profiles_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE profiles_id_seq TO equisse;

CREATE SEQUENCE reagents_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE reagents_id_seq TO equisse;

CREATE SEQUENCE referencecalculatedtestmapping_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE referencecalculatedtestmapping_id_seq TO equisse;

CREATE SEQUENCE referencemapping_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE referencemapping_id_seq TO equisse;

CREATE SEQUENCE testreferences_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE testreferences_id_seq TO equisse;

CREATE SEQUENCE tests_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE tests_id_seq TO equisse;

CREATE SEQUENCE users_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE users_id_seq TO equisse;

CREATE SEQUENCE analyzers_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE analyzers_id_seq TO equisse;

CREATE SEQUENCE isemodules_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE isemodules_id_seq TO equisse;

CREATE SEQUENCE isecomponents_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE isecomponents_id_seq TO equisse;

CREATE SEQUENCE isecalibrationresults_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE isecalibrationresults_id_seq TO equisse;

-- create a db_version table that stores the database version.
-- this table is read only
CREATE TABLE db_version
( 
	id integer PRIMARY KEY,
	version text NOT NULL
);

ALTER TABLE db_version OWNER TO equisse;
INSERT INTO db_version VALUES (1, '0.9.0'); -- TODO: create a constant for the version string
GRANT SELECT ON TABLE db_version TO equisse;

-- create all tables and all constraints, set the table owner toname equisse and grant all

CREATE TABLE analyzers 
( 
	id integer DEFAULT nextval(('analyzers_id_seq'::text)::regclass) PRIMARY KEY,
	name text,
	serialnumber text,	
	lastconnection bigint
);

ALTER TABLE analyzers OWNER TO equisse;
GRANT ALL ON TABLE analyzers TO equisse;

CREATE TABLE isemodules 
( 
	id integer DEFAULT nextval(('isemodules_id_seq'::text)::regclass) PRIMARY KEY,
	analyzer integer NOT NULL,	
	reagentpack_lotnumber text NOT NULL,
	reagentpack_installed bigint NOT NULL,	
	reagentpack_serialnumber text NOT NULL,
	reagentpack_expirationdate bigint NOT NULL,	
	reagentpack_onboardstability integer NOT NULL,
	reagentpack_actuallevela real NOT NULL,	
	reagentpack_actuallevelb real NOT NULL,
    timestamp_lastcleancycle bigint,
	timestamp_lastmeasurmentcalibration bigint,
    timestamp_lastmaintenancecycle bigint,
	timestamp_lastpumpcalibration bigint,	
	timestamp_lastbubblecalibration bigint,		
	timestamp_lastpurgecycle bigint,			
	timestamp_installed bigint,	
	timestamp_lastmeasurement bigint,	
	
	CONSTRAINT FK_isemodules_analyzer FOREIGN KEY (analyzer) REFERENCES analyzers (id) ON DELETE NO ACTION
);

ALTER TABLE isemodules OWNER TO equisse;
GRANT ALL ON TABLE isemodules TO equisse;

CREATE TABLE isecomponents 
( 
	id integer DEFAULT nextval(('isecomponents_id_seq'::text)::regclass) PRIMARY KEY,
	componenttype integer NOT NULL,
	name text,
	ratedhours integer,
	ratedtests integer,	
	installed bigint,
	lastcalibration bigint,
	lastcalibrationid integer,	
	actualworkinghours integer,
	actualtests integer,	
	isemodule integer NOT NULL,
	CONSTRAINT FK_isecomponents_isemodules FOREIGN KEY (isemodule) REFERENCES isemodules (id) ON DELETE NO ACTION
);

ALTER TABLE isecomponents OWNER TO equisse;
GRANT ALL ON TABLE isecomponents TO equisse;

CREATE TABLE isecalibrationresults 
( 
	id integer DEFAULT nextval(('isecalibrationresults_id_seq'::text)::regclass) PRIMARY KEY,
	calibrationtimestamp bigint,
	resultvalue real,
	resultinformation text,	
	component integer NOT NULL,
	CONSTRAINT FK_isecalibrationresults_iisecomponents FOREIGN KEY (component) REFERENCES isecomponents (id) ON DELETE NO ACTION
);

ALTER TABLE isecalibrationresults OWNER TO equisse;
GRANT ALL ON TABLE isecalibrationresults TO equisse;

CREATE TABLE users 
( 
	id integer DEFAULT nextval(('users_id_seq'::text)::regclass) PRIMARY KEY,
	name text NOT NULL,
	password bytea NOT NULL,
	expirationdate bigint,
	firstname text,
	lastname text,
	description text,
	shortname text,
	textkey text,
	userlevel integer NOT NULL,
	archive boolean NOT NULL
);

ALTER TABLE users OWNER TO equisse;
GRANT ALL ON TABLE users TO equisse;

CREATE TABLE hospitals 
( 
	id integer DEFAULT nextval(('hospitals_id_seq'::text)::regclass) PRIMARY KEY,
	name text NOT NULL,
	address text NOT NULL,
	zipcode text NOT NULL,
	town text NOT NULL,
	country text NOT NULL,
	telnumber text NOT NULL,
	archive boolean NOT NULL
);

ALTER TABLE hospitals OWNER TO equisse;
GRANT ALL ON TABLE hospitals TO equisse;

CREATE TABLE departments 
( 
	id integer DEFAULT nextval(('departments_id_seq'::text)::regclass) PRIMARY KEY,
	name text NOT NULL,
	hospital integer NOT NULL,
	telnumber text NOT NULL,
	archive boolean NOT NULL,	
	CONSTRAINT FK_departments_hospitals FOREIGN KEY (hospital) REFERENCES hospitals (id) ON DELETE NO ACTION
);

ALTER TABLE departments OWNER TO equisse;
GRANT ALL ON TABLE departments TO equisse;

CREATE TABLE doctors 
( 
	id integer DEFAULT nextval(('doctors_id_seq'::text)::regclass) PRIMARY KEY,
	name text NOT NULL,
	department integer NOT NULL,
	telnumber text NOT NULL,
	archive boolean NOT NULL,	
	CONSTRAINT FK_doctors_department FOREIGN KEY (department) REFERENCES departments (id) ON DELETE NO ACTION
);

ALTER TABLE doctors OWNER TO equisse;
GRANT ALL ON TABLE doctors TO equisse;

CREATE TABLE messageprotocol 
( 
	id integer DEFAULT nextval(('messageprotocol_id_seq'::text)::regclass) PRIMARY KEY,
	message_id text NOT NULL,
	message_code text NOT NULL,	
	message_timestamp bigint NOT NULL,	
	deactive_timestamp bigint,	
	seen_timestamp bigint,	
	acked_timestamp bigint,
	acked_user integer,	
	messageparameters bytea,
	CONSTRAINT FK_messageprotocol_users FOREIGN KEY (acked_user) REFERENCES users (id) ON DELETE NO ACTION	
);

ALTER TABLE messageprotocol OWNER TO equisse;
GRANT ALL ON TABLE messageprotocol TO equisse;

CREATE TABLE patients 
( 
	id integer DEFAULT nextval(('patients_id_seq'::text)::regclass) PRIMARY KEY,
	patient_id text NOT NULL,
	name text,
	firstname text,
	birthday bigint,
	gender integer NOT NULL,
	bloodtype integer NOT NULL,
	bloodcharacteristic integer NOT NULL,
	sentdepartment integer,
	sentdoctor integer,
	treateddepartment integer,
	treateddoctor integer,	
	roomnumber text,
	bednumber text,
	socialinsurancenumber text,
	medicalrecordnumber text,
	diagnosis text,
	aspirationtimestamp bigint,
	registrationtimestamp bigint,	
	CONSTRAINT FK_patients_departments1 FOREIGN KEY (sentdepartment) REFERENCES departments (id) ON DELETE NO ACTION,
	CONSTRAINT FK_patients_doctors1 FOREIGN KEY (sentdoctor) REFERENCES doctors (id) ON DELETE NO ACTION,
	CONSTRAINT FK_patients_departments2 FOREIGN KEY (treateddepartment) REFERENCES departments (id) ON DELETE NO ACTION,
	CONSTRAINT FK_patients_doctors2 FOREIGN KEY (treateddoctor) REFERENCES doctors (id) ON DELETE NO ACTION	
);

ALTER TABLE patients OWNER TO equisse;
GRANT ALL ON TABLE patients TO equisse;

CREATE TABLE qualitycontroldefinition 
( 
	id integer DEFAULT nextval(('qualitycontroldefinition_id_seq'::text)::regclass) PRIMARY KEY,
	westguard bigint NOT NULL,
	cumulatedsum bigint NOT NULL,
	qcinterval integer NOT NULL,
	replicate integer NOT NULL
);

ALTER TABLE qualitycontroldefinition OWNER TO equisse;
GRANT ALL ON TABLE qualitycontroldefinition TO equisse;

CREATE TABLE calibrationdefinition 
( 
	id integer DEFAULT nextval(('calibrationdefinition_id_seq'::text)::regclass) PRIMARY KEY,
	rule integer NOT NULL,
	sensitivity real NOT NULL,
	standarddeviation integer NOT NULL,
	errorlimit real NOT NULL,
	replicate integer NOT NULL,
	factor real NOT NULL	
);

ALTER TABLE calibrationdefinition OWNER TO equisse;
GRANT ALL ON TABLE calibrationdefinition TO equisse;

CREATE TABLE reagents 
( 
	id integer DEFAULT nextval(('reagents_id_seq'::text)::regclass) PRIMARY KEY,
	name text,	
	testid integer,
	testrev integer,
	serialnumber text NOT NULL,
	lotnumber text NOT NULL,	
	expirationdate integer NOT NULL,
	userposition integer,
	actuallevel real NOT NULL,	
	bottletype integer NOT NULL,
	reagenttype integer NOT NULL,
	onboardstability integer NOT NULL,
	nooftests integer NOT NULL,
	firstusagetimestamp bigint,
	userdefined boolean NOT NULL,
	archive boolean NOT NULL
);

ALTER TABLE reagents OWNER TO equisse;
GRANT ALL ON TABLE reagents TO equisse;

CREATE TABLE calibrationresults 
( 
	id integer DEFAULT nextval(('calibrationresults_id_seq'::text)::regclass) PRIMARY KEY,
    caltype integer NOT NULL,      
    caltimestamp bigint,
    calorigtimestamp bigint,
    testid integer NOT NULL,
	reagent1 integer NOT NULL,
	reagent2 integer,
	status integer NOT NULL,
	analyzerserialnumber text NOT NULL,
    sd real NOT NULL,
    error real NOT NULL,	
    rule integer NOT NULL,       
	segmentcount integer NOT NULL,    
	r0 real,                        
    a real,
    b real,    
	c real,        
	d real,            
	k real,                    
	r01 real,                        
	a1 real,
	b1 real,    
	c1 real,        
	d1 real,   
	k1 real,
	r02 real,                        
	a2 real,
	b2 real,    
	c2 real,        
	d2 real, 
	k2 real,
	r03 real,                        
	a3 real,
	b3 real,    
	c3 real,        
	d3 real,
	k3 real,	
	r04 real,                        
	a4 real,
	b4 real,    
	c4 real,        
	d4 real, 
	k4 real,
	splinedetails bytea,	
	archive boolean NOT NULL,
	CONSTRAINT FK_calibrationresults_reagents_r1 FOREIGN KEY (reagent1) REFERENCES reagents (id) ON DELETE NO ACTION,		
	CONSTRAINT FK_calibrationresults_reagents_r2 FOREIGN KEY (reagent2) REFERENCES reagents (id) ON DELETE NO ACTION				
);

ALTER TABLE calibrationresults OWNER TO equisse;
GRANT ALL ON TABLE calibrationresults TO equisse;


CREATE TABLE tests 
( 
	id integer DEFAULT nextval(('tests_id_seq'::text)::regclass) PRIMARY KEY,
	shortname text NOT NULL,
	testid integer NOT NULL,
	testrev integer NOT NULL,
	name text NOT NULL,	
	printname text NOT NULL,		
	measurementtype integer NOT NULL,
	direction integer NOT NULL,	
	mainwavelength integer NOT NULL,
	subwavelength integer,
	subreadingstart integer,
	subreadingend integer,
	mainreadingstart integer,
	mainreadingend integer,	
	samplevolume real NOT NULL,
	r1volume real NOT NULL,
	r2volume real,
	odlimit real,
	linearitymin real,
	linearitymax real,
	linearitycheck real,
	rbl real,
	rbh real,
	abl real,
	abh real,
	dilutionmode integer NOT NULL,	
	dilutionratio real,
	dilutionsolution integer NOT NULL,	
	calibrationrule integer NOT NULL,
	unit1 integer NOT NULL,
	precision1 integer NOT NULL,
	unit2 integer,
	precision2 integer,
	unitrate real,	
	slope real,
	intercept real,	
	sampletypes text,
	mixer1speed integer NOT NULL,
	mixer2speed integer NOT NULL,
	codeonlis text,
	qcconfiguration integer,
	calconfiguration integer,
	actualcalresult integer,	
	isetest boolean NOT NULL,
	archive boolean NOT NULL,
	CONSTRAINT FK_tests_qualitycontroldefinition FOREIGN KEY (qcconfiguration) REFERENCES qualitycontroldefinition (id) ON DELETE NO ACTION,
	CONSTRAINT FK_tests_calibrationdefinition FOREIGN KEY (calconfiguration) REFERENCES calibrationdefinition (id) ON DELETE NO ACTION,
	CONSTRAINT FK_tests_calibrationresult FOREIGN KEY (actualcalresult) REFERENCES calibrationresults (id) ON DELETE NO ACTION
);

ALTER TABLE tests OWNER TO equisse;
GRANT ALL ON TABLE tests TO equisse;

CREATE TABLE cuvettes 
( 
	id integer DEFAULT nextval(('cuvettes_id_seq'::text)::regclass) PRIMARY KEY,
	usage_counter bigint NOT NULL,
	status integer NOT NULL,
	position integer NOT NULL,
	lasttest integer,
    waterBlankIntensitySum real NOT NULL,
    waterBlankIntensityCount integer NOT NULL,
   	CONSTRAINT FK_cuvettes_tests FOREIGN KEY (lasttest) REFERENCES tests (id) ON DELETE NO ACTION
);

ALTER TABLE cuvettes OWNER TO equisse;
GRANT ALL ON TABLE cuvettes TO equisse;

CREATE TABLE cuvettewaterblanks 
( 
	id integer DEFAULT nextval(('cuvettewaterblanks_id_seq'::text)::regclass) PRIMARY KEY,
	cuvette integer NOT NULL,
	waterblank_timestamp bigint NOT NULL,
	waterblank_value real NOT NULL
);

ALTER TABLE cuvettewaterblanks OWNER TO equisse;
GRANT ALL ON TABLE cuvettewaterblanks TO equisse;

CREATE TABLE waterblankmapping 
( 
	id integer DEFAULT nextval(('waterblankmapping_id_seq'::text)::regclass) PRIMARY KEY,
	cuvette integer NOT NULL,
	waterblank integer NOT NULL,
	CONSTRAINT FK_waterblankmapping_cuvettewaterblanks FOREIGN KEY (waterblank) REFERENCES cuvettewaterblanks (id) ON DELETE CASCADE,
	CONSTRAINT FK_waterblankmapping_cuvettes FOREIGN KEY (cuvette) REFERENCES cuvettes (id) ON DELETE CASCADE
);

ALTER TABLE waterblankmapping OWNER TO equisse;
GRANT ALL ON TABLE waterblankmapping TO equisse;

CREATE TABLE samples 
( 
	id integer DEFAULT nextval(('samples_id_seq'::text)::regclass) PRIMARY KEY,
	type integer,
	doctor integer,
	patient integer,
	sample_id text NOT NULL,
	dilution integer,	
	volume integer,
	position integer,
	tube_type integer,
	requesttimestamp bigint NOT NULL,
	archive boolean NOT NULL,	
	CONSTRAINT FK_samples_doctors FOREIGN KEY (doctor) REFERENCES doctors (id) ON DELETE NO ACTION,
	CONSTRAINT FK_samples_patients FOREIGN KEY (patient) REFERENCES patients (id) ON DELETE NO ACTION
);

ALTER TABLE samples OWNER TO equisse;
GRANT ALL ON TABLE samples TO equisse;

CREATE TABLE testresults 
( 
	id integer DEFAULT nextval(('testresults_id_seq'::text)::regclass) PRIMARY KEY,
	testtype integer NOT NULL,	
	testid integer NOT NULL,	
	testrev integer NOT NULL,
	requesttype integer NOT NULL,
	sample integer,
    analytename text,
    analytelotnumber text,	
    analytesd real,		
	testtimestamp bigint,	
	status integer NOT NULL,	
	measurementtype integer NOT NULL,
	mainwavelength integer,
	subwavelength integer,
	subreadingstart integer,
	subreadingend integer,
	mainreadingstart integer,
	mainreadingend integer,	
	calpoint integer,	
	conc real,
	response real,
	responseorig real,	
	responsesampleblank real,
	resultvalue real,
	resultvalueorig real,	
	flags bigint NOT NULL,
	remark text,
	tester integer,
	authorizer integer,
	printed boolean NOT NULL,
	printedfirst_timestamp bigint,	
	senttolist boolean NOT NULL,	
	dilutionration real,
	unit integer NOT NULL,
	refmin real,
	refmax real,
	pathmin real,
	pathmax real,
	reagent1 integer,
	reagent2 integer,
	calibration integer,
	reactioncurve bytea,	
    calculated boolean NOT NULL,
	retest boolean NOT NULL,
	hasretest boolean NOT NULL,
	testidbeforeatreagentneedles integer,
	testidbeforeincuvette integer,
	testidbeforeatmixer integer,
	archive boolean NOT NULL,	
	CONSTRAINT FK_testresults_users_tester FOREIGN KEY (tester) REFERENCES users (id) ON DELETE NO ACTION,
	CONSTRAINT FK_testresults_users_authorizer FOREIGN KEY (authorizer) REFERENCES users (id) ON DELETE NO ACTION,
	CONSTRAINT FK_testresults_samples FOREIGN KEY (sample) REFERENCES samples (id) ON DELETE NO ACTION,	
	CONSTRAINT FK_testresults_reagents_r1 FOREIGN KEY (reagent1) REFERENCES reagents (id) ON DELETE NO ACTION,
	CONSTRAINT FK_testresults_reagents_r2 FOREIGN KEY (reagent2) REFERENCES reagents (id) ON DELETE NO ACTION
);

ALTER TABLE testresults OWNER TO equisse;
GRANT ALL ON TABLE testresults TO equisse;

CREATE TABLE calibrator 
( 
	id integer DEFAULT nextval(('calibrator_id_seq'::text)::regclass) PRIMARY KEY,
	name text NOT NULL,	
	calibratorid integer NOT NULL,
	expirationdate bigint NOT NULL,
	lotnumber text NOT NULL,
	dilution integer,
	volume integer,
	position integer,
	tube_type integer,
	archive boolean NOT NULL	
);

ALTER TABLE calibrator OWNER TO equisse;
GRANT ALL ON TABLE calibrator TO equisse;

CREATE TABLE calibratorconcentrationmatrix 
( 
	id integer DEFAULT nextval(('calibratorconcentrationmatrix_id_seq'::text)::regclass) PRIMARY KEY,
	testid integer NOT NULL,
	testrevs text NOT NULL,		
	calibrator integer NOT NULL,
	test integer NOT NULL,	
	concentration real NOT NULL,
	unit integer NOT NULL,
	activ boolean NOT NULL,	
	calibrationpoint integer NOT NULL,
	position integer NOT NULL,
	CONSTRAINT FK_calibratorconcentrationmatrix_calibratordefinition FOREIGN KEY (calibrator) REFERENCES calibrator (id) ON DELETE NO ACTION,
	CONSTRAINT FK_calibratorconcentrationmatrix_tests FOREIGN KEY (test) REFERENCES tests (id) ON DELETE NO ACTION		
);

ALTER TABLE calibratorconcentrationmatrix OWNER TO equisse;
GRANT ALL ON TABLE calibratorconcentrationmatrix TO equisse;

CREATE TABLE calresultsmapping 
( 
	id integer DEFAULT nextval(('calresultsmapping_id_seq'::text)::regclass) PRIMARY KEY,
	calresult integer NOT NULL,
	testresult integer NOT NULL,
	CONSTRAINT FK_calresultsmapping_calresult FOREIGN KEY (calresult) REFERENCES calibrationresults (id) ON DELETE CASCADE,
	CONSTRAINT FK_calresultsmapping_testresult FOREIGN KEY (testresult) REFERENCES testresults (id) ON DELETE CASCADE
);

ALTER TABLE calresultsmapping OWNER TO equisse;
GRANT ALL ON TABLE calresultsmapping TO equisse;

CREATE TABLE control
( 
	id integer DEFAULT nextval(('control_id_seq'::text)::regclass) PRIMARY KEY,	
	name text NOT NULL,	
	controlid integer NOT NULL,
	expirationdate bigint NOT NULL,
	lotnumber text NOT NULL,
	dilution integer,
	volume integer,
	position integer,
	tube_type integer,
	archive boolean NOT NULL
);

ALTER TABLE control OWNER TO equisse;
GRANT ALL ON TABLE control TO equisse;

CREATE TABLE controlconcentrationmatrix
( 
	id integer DEFAULT nextval(('controlconcentrationmatrix_id_seq'::text)::regclass) PRIMARY KEY,
    testid integer NOT NULL,
	testrevs text NOT NULL,		
	control integer NOT NULL,
	test integer NOT NULL,	
	concentration real NOT NULL,
	sd real NOT NULL,
	unit integer NOT NULL,	
	activ boolean NOT NULL,
	position integer NOT NULL,	
	CONSTRAINT FK_controlconcentrationmatrix_control FOREIGN KEY (control) REFERENCES control (id) ON DELETE NO ACTION,
	CONSTRAINT FK_controlconcentrationmatrix_tests FOREIGN KEY (test) REFERENCES tests (id) ON DELETE NO ACTION
);

ALTER TABLE controlconcentrationmatrix OWNER TO equisse;
GRANT ALL ON TABLE controlconcentrationmatrix TO equisse;

CREATE TABLE qualitycontrolresults 
( 
	id integer DEFAULT nextval(('qualitycontrolresults_id_seq'::text)::regclass) PRIMARY KEY
);

ALTER TABLE qualitycontrolresults OWNER TO equisse;
GRANT ALL ON TABLE qualitycontrolresults TO equisse;

CREATE TABLE calculatedtests 
( 
	id integer DEFAULT nextval(('calculatedtests_id_seq'::text)::regclass) PRIMARY KEY,
	shortname text NOT NULL,	
	testid integer NOT NULL,
	testrev integer NOT NULL,	
	name text,	
	precision1 integer NOT NULL,
	unit1 integer NOT NULL,
	codeonlis text,
	formula text NOT NULL
);

ALTER TABLE calculatedtests OWNER TO equisse;
GRANT ALL ON TABLE calculatedtests TO equisse;

CREATE TABLE carryover 
( 
	id integer DEFAULT nextval(('carryover_id_seq'::text)::regclass) PRIMARY KEY,
	testid_before integer,
	testid_after integer,
	co_mode integer NOT NULL,
	co_focus integer NOT NULL
);

ALTER TABLE carryover OWNER TO equisse;
GRANT ALL ON TABLE carryover TO equisse;

CREATE TABLE profiles 
( 
	id integer DEFAULT nextval(('profiles_id_seq'::text)::regclass) PRIMARY KEY,
	name text,
	profiletype bigint NOT NULL
);

ALTER TABLE profiles OWNER TO equisse;
GRANT ALL ON TABLE profiles TO equisse;

CREATE TABLE profilemapping 
( 
	id integer DEFAULT nextval(('profilemapping_id_seq'::text)::regclass) PRIMARY KEY,
	profile integer NOT NULL,
	test integer NOT NULL,
	CONSTRAINT FK_profilemapping_profiles FOREIGN KEY (profile) REFERENCES profiles (id) ON DELETE CASCADE,
	CONSTRAINT FK_profilemapping_test FOREIGN KEY (test) REFERENCES tests (id) ON DELETE CASCADE
);

ALTER TABLE profilemapping OWNER TO equisse;
GRANT ALL ON TABLE profilemapping TO equisse;

CREATE TABLE testreferences 
( 
	id integer DEFAULT nextval(('testreferences_id_seq'::text)::regclass) PRIMARY KEY,
	gender integer NOT NULL,
	sampletype integer NOT NULL,
	agemin integer NOT NULL,
	agemax integer NOT NULL,
	ageunit integer NOT NULL,
	refmin real NOT NULL,
	refmax real NOT NULL,
	pathalogicalmin real NOT NULL,
	pathalogicalmax real NOT NULL
);

ALTER TABLE testreferences OWNER TO equisse;
GRANT ALL ON TABLE testreferences TO equisse;

CREATE TABLE referencecalculatedtestmapping 
( 
	id integer DEFAULT nextval(('referencecalculatedtestmapping_id_seq'::text)::regclass) PRIMARY KEY,
	calculatedtest integer NOT NULL,
	reference integer NOT NULL,
	CONSTRAINT FK_referencecalculatedtestmapping_calculatedtests FOREIGN KEY (calculatedtest) REFERENCES calculatedtests (id) ON DELETE CASCADE,
	CONSTRAINT FK_referencecalculatedtestmapping_testreferences FOREIGN KEY (reference) REFERENCES testreferences (id) ON DELETE CASCADE
);

ALTER TABLE referencecalculatedtestmapping OWNER TO equisse;
GRANT ALL ON TABLE referencecalculatedtestmapping TO equisse;

CREATE TABLE referencemapping 
( 
	id integer DEFAULT nextval(('referencemapping_id_seq'::text)::regclass) PRIMARY KEY,
	test integer NOT NULL,
	reference integer NOT NULL,
	CONSTRAINT FK_referencemapping_testreferences FOREIGN KEY (reference) REFERENCES testreferences (id) ON DELETE CASCADE,
	CONSTRAINT FK_referencemapping_tests FOREIGN KEY (test) REFERENCES tests (id) ON DELETE CASCADE
);

ALTER TABLE referencemapping OWNER TO equisse;
GRANT ALL ON TABLE referencemapping TO equisse;


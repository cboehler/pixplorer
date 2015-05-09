-- create pixplorer user if it doesn't exist
do 
$body$
declare 
  num_users integer;
begin
   SELECT count(*) 
     into num_users
   FROM pg_user
   WHERE usename = 'pixplorer';

   IF num_users = 0 THEN
	  CREATE ROLE pixplorer LOGIN
		  PASSWORD 'pixplorer'
		  NOSUPERUSER NOINHERIT CREATEDB NOCREATEROLE NOREPLICATION;
   END IF;
end
$body$
;

-- Drop the database if it is already existing 
DROP DATABASE IF EXISTS "pixplorer";

-- now create a new database and set the owner to equisse
CREATE DATABASE "pixplorer"
  WITH OWNER = pixplorer
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       CONNECTION LIMIT = -1;

-- connect after creating to add all sequences, tables, ...
\connect "pixplorer"

-- create all sequences (needed for the automatic generation of a new primary key)
-- and grant all permissions to the pixplorer user
CREATE SEQUENCE places_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE places_id_seq TO pixplorer;

CREATE SEQUENCE gpsdata_id_seq INCREMENT 1 START 1;
GRANT ALL ON SEQUENCE gpsdata_id_seq TO pixplorer;

CREATE SEQUENCE favourites_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE favourites_id_seq TO pixplorer;

CREATE SEQUENCE history_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE history_id_seq TO pixplorer;

CREATE SEQUENCE categories_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE categories_id_seq TO pixplorer;

CREATE SEQUENCE users_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE users_id_seq TO pixplorer;

CREATE SEQUENCE trophies_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE trophies_id_seq TO pixplorer;

CREATE SEQUENCE usertrophymapping_id_seq INCREMENT 1 START 1;
GRANT ALL ON TABLE usertrophymapping_id_seq TO pixplorer;

-- create all tables and all constraints, set the table owner toname equisse and grant all

CREATE TABLE gpsdata 
( 
	id integer DEFAULT nextval(('gpsdata_id_seq'::text)::regclass) PRIMARY KEY,
	longitude double precision not null,
	latitude double precision not null
);

ALTER TABLE gpsdata OWNER TO pixplorer;
GRANT ALL ON TABLE gpsdata TO pixplorer;

CREATE TABLE categories 
( 
	id integer DEFAULT nextval(('categories_id_seq'::text)::regclass) PRIMARY KEY,
	name text not null
);

ALTER TABLE categories OWNER TO pixplorer;
GRANT ALL ON TABLE categories TO pixplorer;

CREATE TABLE places 
( 
	id integer DEFAULT nextval(('places_id_seq'::text)::regclass) PRIMARY KEY,
	name text not null,
	wikilink text not null,
	picture text not null,
	score integer not null,
	modification_date bigint not null,
	featured boolean not null,
	users_found integer not null,
	gpsdata integer not null,
	category integer not null,
	
	CONSTRAINT fk_category FOREIGN KEY (category) REFERENCES categories (id) ON DELETE NO ACTION,
	CONSTRAINT fk_gpsdata FOREIGN KEY (gpsdata) REFERENCES gpsdata (id) ON DELETE CASCADE
);

ALTER TABLE places OWNER TO pixplorer;
GRANT ALL ON TABLE places TO pixplorer;

CREATE TABLE users 
( 
	id integer DEFAULT nextval(('	'::text)::regclass) PRIMARY KEY,
	google_id text NOT NULL,
	user_type integer not null,
	score integer not null,
	admin boolean not null
);

ALTER TABLE users OWNER TO pixplorer;
GRANT ALL ON TABLE users TO pixplorer;

CREATE TABLE trophies 
( 
	id integer DEFAULT nextval(('trophies_id_seq'::text)::regclass) PRIMARY KEY,
	name text NOT NULL,
	description text not null,
	code text not null
);

ALTER TABLE trophies OWNER TO pixplorer;
GRANT ALL ON TABLE trophies TO pixplorer;

CREATE TABLE history 
( 
	id integer DEFAULT nextval(('history_id_seq'::text)::regclass) PRIMARY KEY,
	user_id integer NOT NULL,
	place_id integer NOT NULL,
	CONSTRAINT fk_history_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
	CONSTRAINT fk_history_place FOREIGN KEY (place_id) REFERENCES places(id) ON DELETE CASCADE
);

ALTER TABLE history OWNER TO pixplorer;
GRANT ALL ON TABLE history TO pixplorer;

CREATE TABLE favourites 
( 
	id integer DEFAULT nextval(('favourites_id_seq'::text)::regclass) PRIMARY KEY,
	user_id integer NOT NULL,
	place_id integer NOT NULL,
	CONSTRAINT fk_fav_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
	CONSTRAINT fk_fav_place FOREIGN KEY (place_id) REFERENCES places(id) ON DELETE CASCADE
);

ALTER TABLE favourites OWNER TO pixplorer;
GRANT ALL ON TABLE favourites TO pixplorer;

CREATE TABLE usertrophymapping 
( 
	id integer DEFAULT nextval(('usertrophymapping_id_seq'::text)::regclass) PRIMARY KEY,
	user_id integer NOT NULL,
	trophy_id integer NOT NULL,
	CONSTRAINT fk_ut_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
	CONSTRAINT fk_ut_trophy FOREIGN KEY (trophy_id) REFERENCES trophies(id) ON DELETE CASCADE
);

ALTER TABLE usertrophymapping OWNER TO pixplorer;
GRANT ALL ON TABLE usertrophymapping TO pixplorer;


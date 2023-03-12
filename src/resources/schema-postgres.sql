DROP DATABASE IF EXISTS dbliteon;

CREATE DATABASE dbliteon;

CREATE TABLE IF NOT EXISTS dbliteon.public."user" (
   	id serial NOT NULL PRIMARY KEY,
	email VARCHAR unique not null,
	hash_key VARCHAR not null,
    access_token VARCHAR,
    refresh_token VARCHAR,
    modified_datetime TIMESTAMPTZ not null
);

CREATE TABLE IF NOT EXISTS dbliteon.public.notification (
   	id serial PRIMARY KEY,
	user_id INTEGER,
	"message" VARCHAR,
    datetime TIMESTAMPTZ not null
)WITH (
  OIDS=FALSE
);

CREATE TABLE IF NOT EXISTS dbliteon.public.location (
   	id serial PRIMARY KEY,
    description VARCHAR NOT NULL
)WITH (
  OIDS=FALSE
);

CREATE TABLE IF NOT EXISTS dbliteon.public.event (
   	tagname VARCHAR(25) PRIMARY KEY,
	location_id INTEGER NOT NULL,
    description VARCHAR,
	alert_upper decimal,
	alert_lower decimal,
	unit CHAR(5),
	FOREIGN KEY(location_id) REFERENCES location(id)
);

CREATE TABLE IF NOT EXISTS dbliteon.public.history (
   	id serial PRIMARY KEY,
    datetime TIMESTAMPTZ not null,
	event_tagname VARCHAR(25) not null,
	value decimal not null,
	FOREIGN KEY(event_tagname) REFERENCES event(tagname)
)WITH (
  OIDS=FALSE
);

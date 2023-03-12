INSERT INTO dbliteon.public."user"
(email, hash_key, access_token, refresh_token, modified_datetime)
VALUES
('super_admin@test.com','a0252f05fcea604efb19d3132877922f9f0a1ac56f7715368aadde4bd03538dc',null, null, Now())
 
 INSERT into dbliteon.public."location"(description) values('S01'),('S02')
 
 INSERT INTO dbliteon.public."event"
 (tagname, location_id, description, alert_upper, alert_lower, unit)
 values
 ('S01_Charged_Energry',
 (select * from  dbliteon.public."location" where description = 'S01'),
  'S01_Charged_Energry', 90, 10, 'kWh'),
 ('S02_Charged_Energry',
 (select * from  dbliteon.public."location" where description = 'S02'),
  'S02_Charged_Energry', 80, 20, 'kWh')


 
 
 
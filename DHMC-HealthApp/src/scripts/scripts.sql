SELECT * FROM app.users;

select * from app.user_account;

CREATE TABLE healthapp.login(id int AUTO_INCREMENT, username VARCHAR(20), password VARCHAR(200));

/*drop TABLE healthapp.LOGIN;*/
--Drop table healthapp.Medication;
--Drop table healthapp.HealthInfo;
--Drop table healthapp.Treats;
--Drop table healthapp.Caregiver;
--Drop table healthapp.Patient;
--Drop table healthapp.MedicalStaff;
--
--Drop table healthapp.Administrator;
--Drop table healthapp.Event;
--Drop table healthapp.Schedule;
--Drop table healthapp.Location;
--Drop table healthapp.Contact;
--Drop table healthapp.User_Account;




CREATE TABLE healthapp.User_Account(
	firstname VARCHAR(20),
	lastname VARCHAR(20),
	User_ID int Primary Key
	);
CREATE TABLE healthapp.Contact(
	User_ID int,
	phone VARCHAR(20),
	email VARCHAR(20),
	address VARCHAR(100),
	FOREIGN KEY(User_ID) REFERENCES healthapp.User_Account(User_ID)
	);
CREATE TABLE healthapp.Location(
	name VARCHAR(20),
	location_type VARCHAR(20),
	User_ID int,
	FOREIGN KEY(User_ID) REFERENCES healthapp.User_Account(User_ID)
	);
CREATE TABLE healthapp.Schedule(
	Schedule_ID int Primary Key,
	User_ID int,
	FOREIGN KEY(User_ID) REFERENCES healthapp.User_Account(User_ID)
	);
CREATE TABLE healthapp.Event(
	Event_ID int,
	name VARCHAR(20),
	Event_Date DATE,
	category VARCHAR(20),
	Schedule_ID int,
	FOREIGN KEY(Schedule_ID) REFERENCES healthapp.Schedule(Schedule_ID)
	);
CREATE TABLE healthapp.Administrator(
	Administrator_ID int Primary KEY,
	User_ID int,
	FOREIGN KEY(User_ID) REFERENCES healthapp.User_Account(User_ID)
	);
CREATE TABLE healthapp.MedicalStaff(
	Medical_ID int Primary Key,
	User_ID int,
	role VARCHAR(20),
	FOREIGN KEY(User_ID) REFERENCES healthapp.User_Account(User_ID)
	);
CREATE TABLE healthapp.Patient(
	User_ID int Primary Key,
	FOREIGN KEY(User_ID) REFERENCES healthapp.User_Account(User_ID)
	);
CREATE TABLE healthapp.Caregiver(
	User_ID int,
	name VARCHAR(20),
	phone VARCHAR(20),
	email VARCHAR(20),
	address VARCHAR(100),
	FOREIGN KEY(User_ID) REFERENCES healthapp.Patient(User_ID)
	--constraint caregiver_ID primary key (User_ID,name)
	);
CREATE TABLE healthapp.Treats(
	User_ID int,
	Medical_ID int,
	FOREIGN KEY(User_ID) REFERENCES healthapp.Patient(User_ID),
	FOREIGN KEY(Medical_ID) REFERENCES healthapp.MedicalStaff(Medical_ID)
	);
CREATE TABLE healthapp.HealthInfo(
	User_ID int,
	height int,
	weight double,
	excerciseFrequency int,
	dateRecorded date,
	FOREIGN KEY(User_ID) REFERENCES healthapp.Patient(User_ID)
	);
CREATE TABLE healthapp.Medication(
	User_ID int,
	Medical_ID int,
	name VARCHAR(20),
	dosage VARCHAR(20),
	directions VARCHAR(100),
	refills int,
	nextRefillDate date,
	FOREIGN KEY(User_ID) REFERENCES healthapp.Patient(User_ID),
	FOREIGN KEY(Medical_ID) REFERENCES healthapp.MedicalStaff(Medical_ID)
	);
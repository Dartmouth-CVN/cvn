/*TABLES CREATION*/
Drop table app.Medication;
Drop table app.HealthInfo;
Drop table app.Treats;
Drop table app.Caregiver;
Drop table app.Patient;
Drop table app.MedicalStaff;

Drop table app.Administrator;
Drop table app.Event;
Drop table app.Schedule;
Drop table app.Location;
Drop table app.Contact;
Drop table app.User_Account;




CREATE TABLE app.User_Account(
	firstname VARCHAR(20),
	lastname VARCHAR(20),
	User_ID int Primary Key
	);
CREATE TABLE app.Contact(
	User_ID int,
	phone VARCHAR(20),
	email VARCHAR(20),
	address VARCHAR(100),
	FOREIGN KEY(User_ID) REFERENCES app.User_Account(User_ID)
	);
CREATE TABLE app.Location(
	name VARCHAR(20),
	location_type VARCHAR(20),
	User_ID int,
	FOREIGN KEY(User_ID) REFERENCES app.User_Account(User_ID)
	);
CREATE TABLE app.Schedule(
	Schedule_ID int Primary Key,
	User_ID int,
	FOREIGN KEY(User_ID) REFERENCES app.User_Account(User_ID)
	);
CREATE TABLE app.Event(
	Event_ID int,
	name VARCHAR(20),
	Event_Date DATE,
	category VARCHAR(20),
	Schedule_ID int,
	FOREIGN KEY(Schedule_ID) REFERENCES app.Schedule(Schedule_ID)
	);
CREATE TABLE app.Administrator(
	Administrator_ID int Primary KEY,
	User_ID int,
	FOREIGN KEY(User_ID) REFERENCES app.User_Account(User_ID)
	);
CREATE TABLE app.MedicalStaff(
	Medical_ID int Primary Key,
	User_ID int,
	role VARCHAR(20),
	FOREIGN KEY(User_ID) REFERENCES app.User_Account(User_ID)
	);
CREATE TABLE app.Patient(
	User_ID int Primary Key,
	FOREIGN KEY(User_ID) REFERENCES app.User_Account(User_ID)
	);
CREATE TABLE app.Caregiver(
	User_ID int,
	name VARCHAR(20),
	phone VARCHAR(20),
	email VARCHAR(20),
	address VARCHAR(100),
	FOREIGN KEY(User_ID) REFERENCES app.Patient(User_ID)
	--constraint caregiver_ID primary key (User_ID,name)
	);
CREATE TABLE app.Treats(
	User_ID int,
	Medical_ID int,
	FOREIGN KEY(User_ID) REFERENCES app.Patient(User_ID),
	FOREIGN KEY(Medical_ID) REFERENCES app.MedicalStaff(Medical_ID)
	);
CREATE TABLE app.HealthInfo(
	User_ID int,
	height int,
	weight double,
	excerciseFrequency int,
	dateRecorded date,
	FOREIGN KEY(User_ID) REFERENCES app.Patient(User_ID)
	);
CREATE TABLE app.Medication(
	User_ID int,
	Medical_ID int,
	name VARCHAR(20),
	dosage VARCHAR(20),
	directions VARCHAR(100),
	refills int,
	nextRefillDate date,
	FOREIGN KEY(User_ID) REFERENCES app.Patient(User_ID),
	FOREIGN KEY(Medical_ID) REFERENCES app.MedicalStaff(Medical_ID)
	);
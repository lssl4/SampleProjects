DROP TABLE IF EXISTS `Hospital`;
DROP TABLE IF EXISTS `EmergencyDepartment`;
DROP TABLE IF EXISTS `PeerGroup`;
DROP TABLE IF EXISTS `ElectiveSurgery`;
DROP TABLE IF EXISTS `EDStats`;
DROP TABLE IF EXISTS `WaitingTimeED`;
DROP TABLE IF EXISTS `FourHourArrivalDeparture`;
DROP TABLE IF EXISTS `NinetyPercentDeparture`;
DROP TABLE IF EXISTS `ElectiveSurgeryStats`;
DROP TABLE IF EXISTS `WithinTimeES`;
DROP TABLE IF EXISTS `WaitingTimeES`;
DROP TABLE IF EXISTS `HospitalServices`;
DROP TABLE IF EXISTS `EDTreatment`;
DROP TABLE IF EXISTS `PeerGroup`;



CREATE TABLE `Hospital`(
	`HospitalID` VARCHAR(4) NOT NULL,
	`Name` VARCHAR(64),
	`NumberBeds` ENUM('<50', '50-99','100-199','200-500','>500'),
	`Type` ENUM('GENERAL', 'SPECIALISED','DISTRICT','TEACHING','CLINICS'),
	`PublicPrivate`	ENUM('PRIVATE', 'PUBLIC'),
	`StreetAddress` VARCHAR(32),
	`City` VARCHAR(32),
	`State` VARCHAR(3),
	`PostCode` INT(4) UNSIGNED, 
	`PhoneNumber` VARCHAR(12),
	`URL` VARCHAR(50),
	`Latitude` DOUBLE,
	`Longitude` DOUBLE,
	`HasED` ENUM('Yes','No'),
	PRIMARY KEY(`HospitalID`)
);



CREATE TABLE `PeerGroup`(
	`PeerGroupID` VARCHAR(4) NOT NULL,
	`NumberBeds` ENUM('<50', '50-99','100-199','200-500','>500'),
	`PeerGroupType` VARCHAR(64),
	`PeerGroupDescription` VARCHAR(1000),
	`PeerGroupCode`	VARCHAR(3),
	PRIMARY KEY(`PeerGroupID`)
);




CREATE TABLE `WaitingTimeED`(
	`HospitalID` VARCHAR(4) NOT NULL,
	`Period` VARCHAR(15) NOT NULL,
	`TriageCode` ENUM('1','2','3','4','5') NOT NULL,
	`NumberPatients`	INT UNSIGNED,
	`WithinTimePeriod`	INT(3) UNSIGNED,
	`PeerGroupAverageWaitTimeED`	INT(3) UNSIGNED,
	CONSTRAINT `pk_waitingtimeED` PRIMARY KEY (`HospitalID`, `TriageCode`,`Period`)
);

CREATE TABLE `FourHourArrivalDeparture`(
	`HospitalID` VARCHAR(4) NOT NULL,
	`Period` VARCHAR(15) NOT NULL,
	`PatientType` ENUM('All','1','2','3','4','5','Admitted', 'Discharged') NOT NULL,
	
	`NumberPatients` INT UNSIGNED,
	`WithinFourHours` INT(3) UNSIGNED,
	`PeerGroupAverageFHAD` INT(3) UNSIGNED,
	CONSTRAINT `pk_fourhourarrivalED` PRIMARY KEY (`HospitalID`, `PatientType`,`Period`)

);


CREATE TABLE `NinetyPercentDeparture`(
	`HospitalID` VARCHAR(4) NOT NULL,
	`Period` VARCHAR(15) NOT NULL,
	`NumberPatients` INT UNSIGNED, 
	`TimeMostPatientsLeft` TIME,
	`PeerGroupAverageNPD` TIME,
	CONSTRAINT `pk_fourhourarrivalED` PRIMARY KEY (`HospitalID`,`Period`)

);


CREATE TABLE `WithinTimeES`(
	`HospitalID` VARCHAR(4) NOT NULL,
	`Urgency` ENUM('Urgent', 'Semi-urgent', 'Non-urgent') NOT NULL,
	`Period` VARCHAR(15) NOT NULL,
	`NumberSurgeries`	INT UNSIGNED,
	`WithinTimePeriod`	INT(3) UNSIGNED,
	`PeerGroupAverageWithTimeES`	INT(3) UNSIGNED,
	CONSTRAINT `pk_withintimeES` PRIMARY KEY (`HospitalID`, `Urgency`,`Period`)
);

CREATE TABLE `WaitingTimeES`(
	`HospitalID` VARCHAR(4) NOT NULL,
	`Urgency` ENUM('Urgent', 'Semi-urgent', 'Non-urgent') NOT NULL,
	`Period` VARCHAR(15) NOT NULL,
	`NumberSurgeries`	INT UNSIGNED,
	`MedianWaitingTime`	INT UNSIGNED,
	`PeerGroupAverageWaitTimeES`	INT UNSIGNED,
	CONSTRAINT `pk_waitingtimeES` PRIMARY KEY (`HospitalID`, `Urgency`,`Period`)
);


CREATE TABLE `HospitalServices`(
	`HospitalID` VARCHAR(4) NOT NULL,
	`Service` VARCHAR(80),
	CONSTRAINT `pk_waitingtimeES` PRIMARY KEY (`HospitalID`, `Service`)
);






/*
Peer group description and names are based on the IHPA website. http://www.ihpa.gov.au/internet/ihpa/publishing.nsf/Content/nhcdc-cost-rep-2010-2011.htm~4-appendixB
*/









################### for fremantle hospital #############

#hospital profile

INSERT INTO `Hospital` values('1000', 'Fremantle Hospital', '200-500', 'GENERAL','PUBLIC', 'Alma Street','Fremantle', 'WA', 6160, '08 9431 3333', 
		'http://www.fhhs.health.wa.gov.au/', -32.0582630000, 115.7514480000, 'Yes');



#Peer group

INSERT INTO `PeerGroup` values('2000', '200-500', 'Large major city', 'Large metropolitan hospitals are major city acute hospitals with more than 10,000 (casemix-adjusted) separations per year. ', 'B1');

#waiting times for ed treatments

INSERT INTO `WaitingTimeED` values('1000', '2012-13', '1', 509, 100, 100);
INSERT INTO `WaitingTimeED` values('1000', '2011-12', '1',  474, 100, 100);


INSERT INTO `WaitingTimeED` values('1000', '2012-13', '2', 8800, 80, 81);
INSERT INTO `WaitingTimeED` values('1000', '2011-12', '2', 8101, 79, 80);

INSERT INTO `WaitingTimeED` values('1000', '2012-13', '3',17845, 37, 64);
INSERT INTO `WaitingTimeED` values('1000', '2011-12', '3',20544, 38, 62);

INSERT INTO `WaitingTimeED` values('1000', '2012-13', '4',21247, 53, 70);
INSERT INTO `WaitingTimeED` values('1000', '2011-12', '4',20756, 63, 68);

INSERT INTO `WaitingTimeED` values('1000', '2012-13',  '5', 6411, 85, 89);
INSERT INTO `WaitingTimeED` values('1000', '2011-12', '5', 5071, 93, 88);


#4 hour time spent in ed

INSERT INTO `FourHourArrivalDeparture` values('1000',  '2012-13', 'All',57019, 67, 58);
INSERT INTO `FourHourArrivalDeparture` values('1000',  '2011-12', 'All',56234, 74, 54);

INSERT INTO `FourHourArrivalDeparture` values('1000', '2012-13', '1', 510, 50, 51);
INSERT INTO `FourHourArrivalDeparture` values('1000', '2011-12', '1', 477, 50, 48);


INSERT INTO `FourHourArrivalDeparture` values('1000', '2012-13','2',  8838, 58, 45);
INSERT INTO `FourHourArrivalDeparture` values('1000', '2011-12','2',  8159, 64, 40);

INSERT INTO `FourHourArrivalDeparture` values('1000', '2012-13',  '3',18412, 58, 49);
INSERT INTO `FourHourArrivalDeparture` values('1000', '2011-12', '3',20892, 67, 44);

INSERT INTO `FourHourArrivalDeparture` values('1000',  '2012-13', '4', 22278, 72, 66);
INSERT INTO `FourHourArrivalDeparture` values('1000',  '2011-12', '4', 21309, 81, 62);

INSERT INTO `FourHourArrivalDeparture` values('1000',  '2012-13', '5', 6981, 88, 85);
INSERT INTO `FourHourArrivalDeparture` values('1000',  '2011-12', '5',5397, 93, 82);

INSERT INTO `FourHourArrivalDeparture` values('1000',  '2012-13','Admitted', 22057, 45, 33);
INSERT INTO `FourHourArrivalDeparture` values('1000',  '2011-12', 'Admitted',22410, 55, 26);

INSERT INTO `FourHourArrivalDeparture` values('1000',  '2012-13','Discharged', 34958, 81, 71);
INSERT INTO `FourHourArrivalDeparture` values('1000',  '2011-12', 'Discharged',33819, 87, 70);



#90% departure

INSERT INTO `NinetyPercentDeparture` values('1000', '2012-13', 22057, '14:47:00', '14:27:00');
INSERT INTO `NinetyPercentDeparture` values('1000', '2011-12', 22410, '11:56:00', '14:58:00');




#within time for es data


INSERT INTO `WithinTimeES` values('1000', 'Urgent', '2012-13', 2872, 94, 95);
INSERT INTO `WithinTimeES` values('1000', 'Urgent', '2011-12', 2328, 84, 92);

INSERT INTO `WithinTimeES` values('1000', 'Semi-urgent', '2012-13', 1780, 75, 76);
INSERT INTO `WithinTimeES` values('1000', 'Semi-urgent', '2011-12', 1540, 69, 76);


INSERT INTO `WithinTimeES` values('1000', 'Non-urgent', '2012-13', 580, 89, 90);
INSERT INTO `WithinTimeES` values('1000', 'Non-urgent', '2011-12', 1175, 93, 90);


#median waiting time for es

INSERT INTO `WaitingTimeES` values('1000', 'Urgent', '2012-13', 2872, 9, 10);
INSERT INTO `WaitingTimeES` values('1000', 'Urgent', '2011-12', 2328, 10, 11);

INSERT INTO `WaitingTimeES` values('1000', 'Semi-urgent', '2012-13', 1780, 51, 54);
INSERT INTO `WaitingTimeES` values('1000', 'Semi-urgent', '2011-12', 1540, 65, 53 );

INSERT INTO `WaitingTimeES` values('1000', 'Non-urgent', '2012-13', 580, 113, 144 );
INSERT INTO `WaitingTimeES` values('1000', 'Non-urgent', '2011-12', 1175, 30, 139 );







#hospital services


INSERT INTO `HospitalServices` values('1000', 'Acute renal dialysis unit');
INSERT INTO `HospitalServices` values('1000', 'Cardiac surgery unit');
INSERT INTO `HospitalServices` values('1000', 'Coronary care unit');
INSERT INTO `HospitalServices` values('1000', 'Diabetes unit');
INSERT INTO `HospitalServices` values('1000', 'Emergency department');
INSERT INTO `HospitalServices` values('1000', 'Geriatric assessment unit');
INSERT INTO `HospitalServices` values('1000', 'Infectious diseases unit');
INSERT INTO `HospitalServices` values('1000', 'Intensive care unit');
INSERT INTO `HospitalServices` values('1000', 'Maintenance renal dialysis unit');
INSERT INTO `HospitalServices` values('1000', 'Major plastic or reconstructive surgery unit');
INSERT INTO `HospitalServices` values('1000', 'Oncology unit');
INSERT INTO `HospitalServices` values('1000', 'Paediatric service');
INSERT INTO `HospitalServices` values('1000', 'Psychiatric unit');
INSERT INTO `HospitalServices` values('1000', 'Rehabilitation unit');









#########Royal Perth Hospital Wellington Street Campus##########

#hospital profile

INSERT INTO `Hospital` values('1100', 'Royal Perth Hospital Wellington Street Campus', 
		'>500', 'GENERAL','PUBLIC', 'Wellington Street','Perth', 'WA', '6000', '08 9224 2244', 
		'http://www.rph.wa.gov.au/', -31.9542420000, 115.8663000000, 'Yes');


#Peer group

INSERT INTO `PeerGroup` values('2100', '>500', 'Large major city', 'Large metropolitan hospitals are major city acute hospitals with more than 10,000 (casemix-adjusted) separations per year. ', 'B1');



#waiting times for ed treatments

INSERT INTO `WaitingTimeED` values('1100', '2012-13', '1', 1535, 100, 100);
INSERT INTO `WaitingTimeED` values('1100', '2011-12', '1',  1391, 100, 100);


INSERT INTO `WaitingTimeED` values('1100', '2012-13', '2', 12416, 80, 81);
INSERT INTO `WaitingTimeED` values('1100', '2011-12', '2', 12232, 80, 80);

INSERT INTO `WaitingTimeED` values('1100', '2012-13', '3',27572, 54, 64);
INSERT INTO `WaitingTimeED` values('1100', '2011-12', '3',25930, 57, 62);

INSERT INTO `WaitingTimeED` values('1100', '2012-13', '4',30414, 72, 70);
INSERT INTO `WaitingTimeED` values('1100', '2011-12', '4',28992, 74, 68);

INSERT INTO `WaitingTimeED` values('1100', '2012-13',  '5', 5099, 95, 89);
INSERT INTO `WaitingTimeED` values('1100', '2011-12', '5', 4818, 94, 88);


#4 hour time spent in ed

INSERT INTO `FourHourArrivalDeparture` values('1100',  '2012-13', 'All',78089, 72, 58);
INSERT INTO `FourHourArrivalDeparture` values('1100',  '2011-12', 'All',74174, 72, 54);

INSERT INTO `FourHourArrivalDeparture` values('1100', '2012-13', '1', 1536, 64, 51);
INSERT INTO `FourHourArrivalDeparture` values('1100', '2011-12', '1', 1391, 63, 48);


INSERT INTO `FourHourArrivalDeparture` values('1100', '2012-13','2',  12463, 66, 45);
INSERT INTO `FourHourArrivalDeparture` values('1100', '2011-12','2',  12272, 64, 40);

INSERT INTO `FourHourArrivalDeparture` values('1100', '2012-13',  '3',27852, 64, 49);
INSERT INTO `FourHourArrivalDeparture` values('1100', '2011-12', '3',26121, 65, 44);

INSERT INTO `FourHourArrivalDeparture` values('1100',  '2012-13', '4', 30933, 80, 66);
INSERT INTO `FourHourArrivalDeparture` values('1100',  '2011-12', '4', 29397, 80, 62);

INSERT INTO `FourHourArrivalDeparture` values('1100',  '2012-13', '5', 5304, 92, 85);
INSERT INTO `FourHourArrivalDeparture` values('1100',  '2011-12', '5',4993, 91, 82);

INSERT INTO `FourHourArrivalDeparture` values('1100',  '2012-13','Admitted', 33810, 48, 33);
INSERT INTO `FourHourArrivalDeparture` values('1100',  '2011-12', 'Admitted',34183, 51, 26);

INSERT INTO `FourHourArrivalDeparture` values('1100',  '2012-13','Discharged', 44268, 91, 71);
INSERT INTO `FourHourArrivalDeparture` values('1100',  '2011-12', 'Discharged',39983, 91, 70);


#90% departure

INSERT INTO `NinetyPercentDeparture` values('1100', '2012-13', 33810, '8:20:00', '14:27:00');
INSERT INTO `NinetyPercentDeparture` values('1100', '2011-12', 34183, '8:24:00', '14:58:00');



#within time for es data


INSERT INTO `WithinTimeES` values('1100', 'Urgent', '2012-13', 3415, 93, 95);
INSERT INTO `WithinTimeES` values('1100', 'Urgent', '2011-12', 3196, 87, 92);

INSERT INTO `WithinTimeES` values('1100', 'Semi-urgent', '2012-13', 2818, 68, 76);
INSERT INTO `WithinTimeES` values('1100', 'Semi-urgent', '2011-12', 2425, 68, 76);


INSERT INTO `WithinTimeES` values('1100', 'Non-urgent', '2012-13', 871, 79, 90);
INSERT INTO `WithinTimeES` values('1100', 'Non-urgent', '2011-12', 777, 84, 90);


#median waiting time for es

INSERT INTO `WaitingTimeES` values('1100', 'Urgent', '2012-13', 3415, 13, 10);
INSERT INTO `WaitingTimeES` values('1100', 'Urgent', '2011-12', 3196, 13, 11);

INSERT INTO `WaitingTimeES` values('1100', 'Semi-urgent', '2012-13', 2818, 56, 54);
INSERT INTO `WaitingTimeES` values('1100', 'Semi-urgent', '2011-12', 2425, 55, 53 );

INSERT INTO `WaitingTimeES` values('1100', 'Non-urgent', '2012-13', 871, 219, 144 );
INSERT INTO `WaitingTimeES` values('1100', 'Non-urgent', '2011-12', 777, 161, 139 );



#hospital services


INSERT INTO `HospitalServices` values('1100', 'Acute renal dialysis unit');
INSERT INTO `HospitalServices` values('1100', 'Cardiac surgery unit');
INSERT INTO `HospitalServices` values('1100', 'Coronary care unit');
INSERT INTO `HospitalServices` values('1100', 'Diabetes unit');
INSERT INTO `HospitalServices` values('1100', 'Emergency department');
INSERT INTO `HospitalServices` values('1100', 'Geriatric assessment unit');
INSERT INTO `HospitalServices` values('1100', 'Infectious diseases unit');
INSERT INTO `HospitalServices` values('1100', 'Intensive care unit');
INSERT INTO `HospitalServices` values('1100', 'Maintenance renal dialysis unit');
INSERT INTO `HospitalServices` values('1100', 'Major plastic or reconstructive surgery unit');
INSERT INTO `HospitalServices` values('1100', 'Oncology unit');
INSERT INTO `HospitalServices` values('1100', 'Pancreas transplantation unit');
INSERT INTO `HospitalServices` values('1100', 'Paediatric service');
INSERT INTO `HospitalServices` values('1100', 'Psychiatric unit');
INSERT INTO `HospitalServices` values('1100', 'Rehabilitation unit');
INSERT INTO `HospitalServices` values('1100', 'Neurosurgical unit');
INSERT INTO `HospitalServices` values('1100', 'Sleep centre');












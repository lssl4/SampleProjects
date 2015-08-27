#Name: Siong Leong

#Student Number: 20753794

#select statement to view all of the statistics involving elective surgery

select * from Hospital join (WithinTimeES as WithTimeES join WaitingTimeES as WaitTimeES using (HospitalID, Urgency,Period)) using(HospitalID); 


#creating an appropriate view for all the statistics of elective surgery, ESStats

create view AllESStatsView as select `Name`,Urgency,Period,WaitTimeES.NumberSurgeries,WithinTimePeriod,PeerGroupAverageWithTimeES as PGAWithTimeES, MedianWaitingTime, PeerGroupAverageWaitTimeES as PGAWaitTimeES from Hospital join (WithinTimeES as WithTimeES join WaitingTimeES as WaitTimeES using (HospitalID, Urgency,Period)) using(HospitalID); 

#select statements to query the ALLESStatsView

select * from allesstatsview;

select `name`, urgency, period from allesstatsview where numberSurgeries>1500;

select `name`, urgency, period, withintimeperiod, medianWaitingtime from allesstatsview where medianWaitingtime <50 and withintimeperiod >75;





#select statement for the percentage of elective surgeries received within the clinically recommended time

select `Name`, Urgency, Period, numberSurgeries, WithinTimePeriod, PeerGroupAverageWithTimeES from Hospital join WithinTimeES as WithTimeES using(HospitalID);

#creating an appropriate view for percentage of surgeries received within time

create view WithinTimeESView as 
select `Name`, Urgency, Period, numberSurgeries, WithinTimePeriod, PeerGroupAverageWithTimeES from Hospital join WithinTimeES as WithTimeES using(HospitalID);


#select statements to query the withintimeesview

select * from withintimeesview;

select * from withintimeesview where numberSurgeries > 2000;

select `name`, urgency, period, withinTimeperiod from withintimeesview where numberSurgeries <2000 order by withinTimeperiod asc;





#select statement for the median waiting times of elective surgeries

select `Name`, Urgency, Period, numberSurgeries, medianWaitingTime, PeerGroupAverageWaitTimeES as PGAWaitTimeES from Hospital natural join WaitingTimeES;

#creating an appropriate view for the median waiting times of elective surgeries

create view WaitTimeESView as
select `Name`, Urgency, Period, numberSurgeries, medianWaitingTime, PeerGroupAverageWaitTimeES as PGAWaitTimeES from Hospital natural join WaitingTimeES;


#select statements to query the waittimeesview

select * from waittimeesview;

select `name`, urgency, period, medianWaitingtime from waittimeesview where medianWaitingtime<20;

select `name`, urgency, period, medianWaitingtime from waittimeesview where medianWaitingtime > 40 order by medianWaitingtime desc;






#creating a procedure for all estats to view by hospital name only

delimiter ++ 

create procedure ESQueryPro(in HospName varchar(64))
begin



select * from allesstatsview where `Name` like HospName;

end++

delimiter ;

#example of calling the procedure

call esquerypro('Fremantle Hospital');



#this league table focuses on urgent surgeries ordered by year and descending withintimeperiod percentages. The larger the percentage in a period, the better the hospital to give service to patients on time. The peer group averages are provided for comparision 

select `Name`, urgency, period, numbersurgeries, WithinTimePeriod, PGAWithTimeES from allesstatsview where urgency = 'Urgent' order by Period, WithinTimePeriod desc;

#this league table focuses on semi-urgent surgeries ordered by year and descending withintimeperiod percentages. The larger the percentage in a period, the better the hospital to give service to patients on time. The peer group averages are provided for comparision 


select `Name`, urgency, period, numbersurgeries, WithinTimePeriod, PGAWithTimeES from allesstatsview where urgency = 'Semi-urgent' order by Period, WithinTimePeriod desc;

#this league table focuses on non-urgent surgeries ordered by year and descending withintimeperiod percentages. The larger the percentage in a period, the better the hospital to give service to patients on time. The peer group averages are provided for comparision 


select `Name`, urgency, period, numbersurgeries, WithinTimePeriod, PGAWithTimeES from allesstatsview where urgency = 'Non-urgent' order by Period, WithinTimePeriod desc;






#this league table focuses on urgent surgeries ordered by year and ascending median waiting times. The less days of waiting time there is in a period, the better the hospital to provide their services with less delay. The peer group averages are provided for comparision 


select `Name`, urgency, period, numbersurgeries, medianwaitingtime, PGAWaitTimeES from allesstatsview where urgency = 'Urgent' order by Period, medianwaitingtime asc;


#this league table focuses on semi-urgent surgeries ordered by year and ascending median waiting times. The less days of waiting time there is in a period, the better the hospital to provide their services with less delay. The peer group averages are provided for comparision 


select `Name`, urgency, period, numbersurgeries, medianwaitingtime, PGAWaitTimeES from allesstatsview where urgency = 'Semi-urgent' order by Period, medianwaitingtime asc;


#this league table focuses on non-urgent surgeries ordered by year and ascending median waiting times. The less days of waiting time there is in a period, the better the hospital to provide their services with less delay. The peer group averages are provided for comparision 


select `Name`, urgency, period, numbersurgeries, medianwaitingtime, PGAWaitTimeES from allesstatsview where urgency = 'Non-urgent' order by Period, medianwaitingtime asc;





#The two league table procedures for querying below are based on elective surgery attributes. I tried to pass an attribute parameter instead of making two league tables but the parameter wouldn't register as a attribute in the select statement. 

#procedure for querying league tables based on percentagaes of service received within time period. 

delimiter ++ 

create procedure leagueTableWithinTime(in urgencyParam varchar(64))
begin


select `Name`, urgency, period, numbersurgeries, withinTimePeriod, PGAWithTimeES from allesstatsview where urgency = urgencyParam order by Period, medianwaitingtime asc;



end++

delimiter ;





#procedure for querying league tables based on median waiting time periods of elective surgeries. 

delimiter ++ 

create procedure leagueTableWaitingTime(in urgencyParam varchar(64))
begin


select `Name`, urgency, period, numbersurgeries, MedianWaitingTime, PGAWaitTimeES from allesstatsview where urgency = urgencyParam order by Period, medianwaitingtime asc;



end++

delimiter ;

#examples of procedure calls of the last two procedures

call leagueTableWithinTime('Urgent');
call leagueTableWithinTime('Non-urgent');









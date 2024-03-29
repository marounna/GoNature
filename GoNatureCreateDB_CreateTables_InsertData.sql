drop database gonaturedb;
create database gonaturedb;
use gonaturedb;

CREATE TABLE gonaturedb.users (
    UserId VARCHAR(255) PRIMARY KEY,
    Fname VARCHAR(255),
    Lname VARCHAR(255),
    Username VARCHAR(255),
    Password VARCHAR(255),
    Email VARCHAR(255),
    PhoneNumber VARCHAR(255),
    TypeUser VARCHAR(255),
    IsLogged VARCHAR(255) DEFAULT "0"
);

CREATE TABLE gonaturedb.external_users (
    UserId VARCHAR(255),
    PRIMARY KEY (UserId)
);

CREATE TABLE gonaturedb.sales (
    SaleType VARCHAR(255) PRIMARY KEY,
    SalePercentage VARCHAR(255)
);

INSERT INTO gonaturedb.sales (SaleType, SalePercentage)
VALUES 
('personal', '15'),
('group', '25'),
('casual_group', '10'),
('casual_personal', '0');



CREATE TABLE gonaturedb.park (
    Parkname VARCHAR(255) PRIMARY KEY,
    CapacityOfVisitors VARCHAR(255),
    PricePerPerson VARCHAR(255),
    AvailableSpot VARCHAR(255),
    ParkMangerId VARCHAR(255),
    visitTimeLimit VARCHAR(255) DEFAULT '4' 
);

INSERT INTO gonaturedb.park (Parkname, CapacityOfVisitors, PricePerPerson,ParkMangerId)
VALUES 
('Safari', '80', '20','3148138267'),
('Gan-Tanahi', '80', '15','314813829'),
('Yaar-Hakofim', '120', '25','314813826');

CREATE TABLE gonaturedb.park_used_capacity_Total (
    Parkname VARCHAR(255),
    date DATE,
    t8 INT DEFAULT 0,
    t9 INT DEFAULT 0,
    t10 INT DEFAULT 0,
    t11 INT DEFAULT 0,
    t12 INT DEFAULT 0,
    t13 INT DEFAULT 0,
    t14 INT DEFAULT 0,
    t15 INT DEFAULT 0,
    t16 INT DEFAULT 0,
    t17 INT DEFAULT 0,
    t18 INT DEFAULT 0,
    t19 INT DEFAULT 0,
    FOREIGN KEY (ParkName) REFERENCES park(Parkname)
);

CREATE TABLE gonaturedb.park_used_capacity_individual (
    Parkname VARCHAR(255),
    date DATE,
    t8 INT DEFAULT 0,
    t9 INT DEFAULT 0,
    t10 INT DEFAULT 0,
    t11 INT DEFAULT 0,
    t12 INT DEFAULT 0,
    t13 INT DEFAULT 0,
    t14 INT DEFAULT 0,
    t15 INT DEFAULT 0,
    t16 INT DEFAULT 0,
    t17 INT DEFAULT 0,
    t18 INT DEFAULT 0,
    t19 INT DEFAULT 0,
    FOREIGN KEY (ParkName) REFERENCES park(Parkname)
);
CREATE TABLE gonaturedb.park_used_capacity_groups (
    Parkname VARCHAR(255),
    date DATE,
    t8 INT DEFAULT 0,
    t9 INT DEFAULT 0,
    t10 INT DEFAULT 0,
    t11 INT DEFAULT 0,
    t12 INT DEFAULT 0,
    t13 INT DEFAULT 0,
    t14 INT DEFAULT 0,
    t15 INT DEFAULT 0,
    t16 INT DEFAULT 0,
    t17 INT DEFAULT 0,
    t18 INT DEFAULT 0,
    t19 INT DEFAULT 0,
    FOREIGN KEY (ParkName) REFERENCES park(Parkname)
);

CREATE TABLE gonaturedb.orders (
    OrderId int auto_increment PRIMARY KEY,
    ParkName VARCHAR(255),
    UserId VARCHAR(255),
    dateOfVisit date,
    TimeOfVisit VARCHAR(255),
    NumberOfVisitors VARCHAR(255),
    IsConfirmed VARCHAR(255),
    IsVisit VARCHAR(255),
    IsCanceled VARCHAR(255),
    IsCanceledAuto VARCHAR(255) DEFAULT 'NO',
    TotalPrice VARCHAR(255),
    IsInWaitingList VARCHAR(255),
    Email VARCHAR(255),
    FOREIGN KEY (Parkname) REFERENCES park(Parkname)
);

INSERT INTO gonaturedb.orders ( ParkName, UserId, dateOfVisit,TimeOfVisit, NumberOfVisitors, IsConfirmed, IsVisit, IsCanceled, TotalPrice, IsInWaitingList,Email) 
VALUES
( 'Safari',             '900000001','2024-04-30','10:00', '5', 'YES', 'NO', 'NO', '300', 'NO',"Email01@gmail.com"),
( 'Safari',             '900000002','2024-04-30','10:00', '5', 'YES', 'NO', 'NO', '300', 'NO',"Email02@gmail.com"),
( 'Gan-Tanahi',         '900000003','2024-04-30','13:00', '3', 'YES', 'NO', 'NO', '300', 'NO',"Email05@gmail.com"),
( 'Gan-Tanahi',         '900000004','2024-04-30','12:00', '7', 'YES', 'NO', 'NO', '300', 'NO',"Email07@gmail.com"),
( 'Gan-Tanahi',         '900000005','2024-04-30','10:00', '5', 'YES', 'NO', 'NO', '300', 'NO',"Email08@gmail.com"),
( 'Safari',             '900000006','2024-04-30','17:00', '5', 'YES', 'NO', 'NO', '300', 'NO',"Email06@gmail.com"),
( 'Yaar-Hakofim',       '900000007','2024-04-30','11:00', '1', 'YES', 'NO', 'NO', '300', 'NO',"Email03@gmail.com"),
( 'Yaar-Hakofim',       '900000008','2024-04-30','12:00', '3', 'YES', 'NO', 'NO', '300', 'NO',"Email04@gmail.com");

INSERT INTO gonaturedb.park_used_capacity_individual (Parkname, date, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19)
VALUES
    ('Safari', '2024-03-17', 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 0),
    ('Gan-Tanahi', '2024-03-17', 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 0);


INSERT INTO gonaturedb.park_used_capacity_groups (Parkname, date, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19)
VALUES
    ('Safari', '2024-03-17', 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0),
    ('Gan-Tanahi', '2024-03-17', 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0);

CREATE TABLE gonaturedb.report_visit (
    ReportId int auto_increment PRIMARY KEY,
    ParkName VARCHAR(255),
    Year VARCHAR(255),
    Month VARCHAR(255),
    NumberOfVisitorsTotal VARCHAR(255),
    NumberOfVisitorsGroups	VARCHAR(255),
	NumberOfVisitorsIndividual	VARCHAR(255),
    FOREIGN KEY (ParkName) REFERENCES park(Parkname)
);

CREATE TABLE gonaturedb.report_cancellation (
    ReportId VARCHAR(255) PRIMARY KEY,
    ParkName VARCHAR(255),
    Year VARCHAR(255),
    Month VARCHAR(255),
    NumberOfCanceledOrders VARCHAR(255),
    OrdersNotVisited VARCHAR(255),
    FOREIGN KEY (ParkName) REFERENCES park(Parkname)
);


CREATE TABLE gonaturedb.visit_time_max_table (
    Parkname VARCHAR(255),
    visit_time VARCHAR(255),
    max_cap VARCHAR(255)
);



-- this data is for teseting only it doesn't make any sense
INSERT INTO gonaturedb.park_used_capacity_total (Parkname, date, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19)
VALUES 
    ('Safari', '2024-03-01', 10, 18, 12, 20, 15, 22, 17, 25, 10, 19, 23, 16),
    ('Safari', '2024-03-02', 19, 11, 15, 23, 14, 21, 13, 9, 18, 12, 8, 17),
    ('Safari', '2024-03-03', 7, 14, 19, 10, 8, 20, 22, 16, 11, 24, 13, 25),
    ('Safari', '2024-03-04', 21, 13, 8, 18, 9, 24, 16, 12, 23, 20, 10, 17),
    ('Safari', '2024-03-05', 16, 20, 9, 14, 17, 22, 11, 15, 8, 13, 24, 21),
    ('Safari', '2024-03-06', 12, 9, 23, 17, 10, 25, 14, 21, 16, 19, 8, 13),
    ('Safari', '2024-03-07', 18, 15, 10, 21, 11, 19, 16, 23, 12, 14, 25, 17),
    ('Safari', '2024-03-08', 8, 12, 22, 16, 25, 13, 20, 9, 17, 21, 11, 14),
    ('Safari', '2024-03-09', 14, 19, 11, 9, 20, 16, 10, 24, 15, 8, 23, 17),
    ('Safari', '2024-03-10', 22, 8, 20, 12, 14, 17, 18, 11, 23, 9, 13, 25),
    ('Safari', '2024-03-11', 9, 21, 13, 16, 22, 12, 24, 19, 14, 17, 10, 8),
    ('Safari', '2024-03-12', 15, 10, 18, 25, 13, 16, 14, 21, 11, 7, 19, 22),
    ('Safari', '2024-03-13', 13, 16, 24, 11, 18, 10, 9, 17, 21, 14, 8, 12),
    ('Safari', '2024-03-14', 20, 9, 14, 22, 10, 18, 16, 12, 25, 15, 11, 19),
    ('Safari', '2024-03-15', 11, 24, 19, 8, 21, 13, 15, 23, 10, 18, 16, 12),
    ('Safari', '2024-03-16', 23, 8, 16, 20, 12, 17, 11, 14, 24, 9, 22, 15),
    ('Safari', '2024-03-17', 17, 12, 25, 14, 16, 9, 21, 10, 13, 18, 20, 11),
    ('Safari', '2024-03-18', 10, 22, 15, 13, 24, 11, 19, 8, 17, 16, 21, 12),
    ('Safari', '2024-03-19', 16, 18, 11, 25, 9, 20, 23, 12, 14, 13, 17, 22),
    ('Safari', '2024-03-20', 12, 15, 9, 22, 18, 10, 14, 23, 11, 16, 21, 8),
    ('Safari', '2024-03-21', 21, 10, 17, 12, 15, 8, 19, 11, 24, 14, 22, 16),
    ('Safari', '2024-03-22', 14, 23, 8, 19, 10, 16, 13, 15, 21, 11, 20, 12),
    ('Safari', '2024-03-23', 8, 19, 12, 15, 22, 11, 14, 9, 20, 16, 25, 10),
    ('Safari', '2024-03-24', 25, 13, 10, 16, 12, 8, 21, 14, 19, 11, 17, 20),
    ('Safari', '2024-03-25', 17, 21, 9, 14, 10, 20, 18, 13, 11, 8, 16, 22),
    ('Safari', '2024-03-26', 9, 14, 24, 16, 12, 10, 18, 15, 22, 11, 19, 8),
    ('Safari', '2024-03-27', 22, 8, 13, 10, 19, 16, 11, 20, 14, 17, 9, 12),
    ('Safari', '2024-03-28', 13, 20, 8, 15, 10, 9, 22, 17, 12, 14, 11, 18),
    ('Safari', '2024-03-29', 10, 18, 15, 12, 25, 13, 16, 11, 22, 14, 8, 19),
    ('Safari', '2024-03-30', 21, 10, 14, 16, 9, 12, 18, 20, 15, 11, 13, 22),
    ('Safari', '2024-03-31', 15, 13, 9, 11, 22, 14, 17, 10, 20, 12, 18, 16);




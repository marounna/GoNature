drop database gonaturedb;
create database gonaturedb;
use gonaturedb;

CREATE TABLE gonaturedb.user_types (
    TypeUser VARCHAR(255) PRIMARY KEY
);

INSERT INTO gonaturedb.user_types (TypeUser) VALUES 
('customer'), 
('department manager'), 
('park manager'), 
('guide'), 
('service employee'),
('park employee');

CREATE TABLE gonaturedb.users (
    UserId VARCHAR(255) PRIMARY KEY,
    Fname VARCHAR(255),
    Lname VARCHAR(255),
    Username VARCHAR(255),
    Password VARCHAR(255),
    Email VARCHAR(255),
    PhoneNumber VARCHAR(255),
    TypeUser VARCHAR(255),
    IsLogged VARCHAR(255) DEFAULT "0",
    FOREIGN KEY (TypeUser) REFERENCES user_types(TypeUser)
);

CREATE TABLE gonaturedb.external_users (
    UserId VARCHAR(255),
    PRIMARY KEY (UserId)
);

INSERT INTO gonaturedb.users (UserId, Fname, Lname, Username, Password, Email, PhoneNumber, TypeUser)
VALUES 
(1, 'customer', 'customeri', 'customer', '123456', 'customer@gmail.com', '12345645', 'customer'),
(314813825, 'Hamza', 'Abunimer', 'hamzaabunimer', '123456', 'hamzaabunimer@gmail.com', '0521111111', 'customer'),
(314813826, 'Ava', 'Smith', 'depm', '123456', 'avasmith@gmail.com', '0522222222', 'department manager'),
(314813827, 'Liam', 'Johnson', 'safarim', '123456', 'liamjohnson@gmail.com', '0523333333', 'park manager'),
(314813828, 'Olivia', 'Williams', 'service', '123456', 'oliviawilliams@gmail.com', '0524444444', 'service employee'),
(314813829, 'Noah', 'Brown', 'guide', '123456', 'noahbrown@gmail.com', '0525555555', 'guide'),
(314813830, 'Emma', 'Jones', 'guide2', '123456', 'emmajones@gmail.com', '0526666666', 'guide'),
(314813831, 'Oliver', 'Garcia', 'guide3', '123456', 'olivergarcia@gmail.com', '0527777777', 'guide'),
(314813832, 'Isabella', 'Martinez', 'customer1', '123456', 'isabellamartinez@gmail.com', '0528888888', 'customer'),
(314813833, 'Mason', 'Rodriguez', 'customer2', '123456', 'masonrodriguez@gmail.com', '0529999999', 'customer'),
(314813834, 'Sophia', 'Lopez', 'customer3', '123456', 'sophialopez@gmail.com', '0530000000', 'customer'),
(5, 'israel', 'israeli', 'parke', '123456', 'parke@gmail.com', '02522555546', 'park employee');


INSERT INTO gonaturedb.external_users(UserId)
VALUES
('209001981'),
('209001982');


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
    visitTimeLimit VARCHAR(255) DEFAULT '4', -- default is 4
    FOREIGN KEY (ParkMangerId) REFERENCES users(UserId)
);

INSERT INTO gonaturedb.park (Parkname, CapacityOfVisitors, PricePerPerson,ParkMangerId)
VALUES 
('Safari', '100', '20','314813827'),
('Gan-Tanahi', '80', '15','314813827'),
('Yaar-Hakofim', '120', '25','314813827'),
('Hay-Park', '90', '18','314813827'),
('Mitspe-Tat-Yami', '70', '22','314813827');


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
    SaleType VARCHAR(255),
    FOREIGN KEY (Parkname) REFERENCES park(Parkname),
    FOREIGN KEY (UserId) REFERENCES users(UserId),
    FOREIGN KEY (SaleType) REFERENCES sales(SaleType)
);



INSERT INTO gonaturedb.orders ( ParkName, UserId, dateOfVisit,TimeOfVisit, NumberOfVisitors, IsConfirmed, IsVisit, IsCanceled, TotalPrice, IsInWaitingList,SaleType) 
VALUES
( 'Safari',            '314813825','2024-03-13','10:00', '5', 'YES', 'YES', 'NO', '300', 'NO',"casual_personal"),
( 'Mitspe-Tat-Yami',   '314813832','2024-03-13','10:00', '5', 'YES', 'YES', 'NO', '300', 'NO',"casual_personal"),
( 'Hay-Park',          '314813833','2024-03-12','13:00', '3', 'YES', 'YES', 'NO', '300', 'NO',"casual_group"),
( 'Yaar-Hakofim',      '314813831','2024-03-12','12:00', '7', 'YES', 'YES', 'NO', '300', 'NO',"group"),
( 'Gan-Tanahi',        '314813832','2024-03-11','10:00', '5', 'YES', 'YES', 'NO', '300', 'NO',"personal"),
( 'Safari',            '314813833','2024-03-11','17:00', '5', 'YES', 'YES', 'NO', '300', 'NO',"casual_group"),
( 'Safari',            '314813834','2024-03-11','11:00', '1', 'YES', 'YES', 'NO', '300', 'NO',"casual_personal"),
( 'Yaar-Hakofim',      '314813831','2024-03-11','12:00', '3', 'YES', 'YES', 'NO', '300', 'NO',"casual_group");



INSERT INTO gonaturedb.park_used_capacity_individual (Parkname, date, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19)
VALUES
    ('Safari', '2024-03-17', 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 0),
    ('Mitspe-Tat-Yami', '2024-03-17', 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 0);


INSERT INTO gonaturedb.park_used_capacity_groups (Parkname, date, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19)
VALUES
    ('Safari', '2024-03-17', 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0),
    ('Mitspe-Tat-Yami', '2024-03-17', 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0);

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

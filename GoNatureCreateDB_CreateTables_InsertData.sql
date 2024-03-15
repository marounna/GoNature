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
    IsLogged VARCHAR(255),
    FOREIGN KEY (TypeUser) REFERENCES user_types(TypeUser)
);

CREATE TABLE gonaturedb.external_users (
    UserId VARCHAR(255),
    PRIMARY KEY (UserId)
);

INSERT INTO gonaturedb.users (UserId, Fname, Lname, Username, Password, Email, PhoneNumber, TypeUser, IsLogged) 
VALUES 
('314813825', 'Hamza', 'Abunimer', 'hamzaabunimer', 'Pwd12345', 'hamzaabunimer@gmail.com', '0521111111', 'customer', '0'),
('314813826', 'Ava', 'Smith', 'departmentmanager', 'Pwd12345', 'avasmith@gmail.com', '0522222222', 'department manager', '0'),
('314813827', 'Liam', 'Johnson', 'parkmanager', 'Pwd12345', 'liamjohnson@gmail.com', '0523333333', 'park manager', '0'),
('314813828', 'Olivia', 'Williams', 'service', 'Pwd12345', 'oliviawilliams@gmail.com', '0524444444', 'service employee', '0'),
('314813829', 'Noah', 'Brown', 'noahbrown', 'Pwd12345', 'noahbrown@gmail.com', '0525555555', 'guide', '0'),
('314813830', 'Emma', 'Jones', 'emmajones', 'Pwd12345', 'emmajones@gmail.com', '0526666666', 'guide', '0'),
('314813831', 'Oliver', 'Garcia', 'olivergarcia', 'Pwd12345', 'olivergarcia@gmail.com', '0527777777', 'guide', '0'),
('314813832', 'Isabella', 'Martinez', 'isabellamartinez', 'Pwd12345', 'isabellamartinez@gmail.com', '0528888888', 'customer', '0'),
('314813833', 'Mason', 'Rodriguez', 'masonrodriguez', 'Pwd12345', 'masonrodriguez@gmail.com', '0529999999', 'customer', '0'),
('314813834', 'Sophia', 'Lopez', 'sophialopez', 'Pwd12345', 'sophialopez@gmail.com', '0530000000', 'customer', '0');


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
('family', '15'),
('group', '25'),
('casual_group', '10'),
('casual_personal', '0'),
('casual_family', '0');



CREATE TABLE gonaturedb.park (
    Parkname VARCHAR(255) PRIMARY KEY,
    CapacityOfVisitors VARCHAR(255),
    PricePerPerson VARCHAR(255),
    AvailableSpot VARCHAR(255),
    visitTimeLimit VARCHAR(255) DEFAULT '4' COMMENT 'Visit time limit in hours default value = 4'
);

INSERT INTO gonaturedb.park (Parkname, CapacityOfVisitors, PricePerPerson)
VALUES 
('Safari', '100', '20'),
('Gan-Tanahi', '80', '15'),
('Yaar-Hakofim', '120', '25'),
('Hay-Park', '90', '18'),
('Mitspe-Tat-Yami', '70', '22');


CREATE TABLE gonaturedb.park_used_capacity (
    Parkname VARCHAR(255),
    date DATE,
    value1 INT DEFAULT 0,
    value2 INT DEFAULT 0,
    value3 INT DEFAULT 0,
    value4 INT DEFAULT 0,
    value5 INT DEFAULT 0,
    value6 INT DEFAULT 0,
    value7 INT DEFAULT 0,
    value8 INT DEFAULT 0,
    value9 INT DEFAULT 0,
    value10 INT DEFAULT 0,
    value11 INT DEFAULT 0,
    value12 INT DEFAULT 0,
    FOREIGN KEY (ParkName) REFERENCES park(Parkname)
);


CREATE TABLE gonaturedb.orders (
    OrderId VARCHAR(255) PRIMARY KEY,
    ParkName VARCHAR(255),
    UserId VARCHAR(255),
    dateOfVisit date,
    TimeOfVisit VARCHAR(255),
    NumberOfVisitors VARCHAR(255),
    IsConfirmed VARCHAR(255),
    IsVisit VARCHAR(255),
    IsCanceled VARCHAR(255),
    TotalPrice VARCHAR(255),
    IsInWaitingList VARCHAR(255),
    SaleType VARCHAR(255),
    FOREIGN KEY (Parkname) REFERENCES park(Parkname),
    FOREIGN KEY (UserId) REFERENCES users(UserId),
    FOREIGN KEY (SaleType) REFERENCES sales(SaleType)
);



INSERT INTO gonaturedb.orders (OrderId, ParkName, UserId, dateOfVisit,TimeOfVisit, NumberOfVisitors, IsConfirmed, IsVisit, IsCanceled, TotalPrice, IsInWaitingList) 
VALUES
('111110', 'Safari',            '314813825','2024-03-13','10:00', '5', 'YES', 'YES', 'NO', '300', 'NO'),
('111111', 'Mitspe-Tat-Yami',   '314813832','2024-03-13','10:00', '5', 'YES', 'YES', 'NO', '300', 'NO'),
('111112', 'Hay-Park',          '314813833','2024-03-12','13:00', '3', 'YES', 'YES', 'NO', '300', 'NO'),
('111113', 'Yaar-Hakofim',      '314813831','2024-03-12','12:00', '7', 'YES', 'YES', 'NO', '300', 'NO'),
('111114', 'Gan-Tanahi',        '314813832','2024-03-11','10:00', '5', 'YES', 'YES', 'NO', '300', 'NO'),
('111115', 'Safari',            '314813833','2024-03-11','17:00', '5', 'YES', 'YES', 'NO', '300', 'NO'),
('111116', 'Safari',            '314813834','2024-03-11','11:00', '1', 'YES', 'YES', 'NO', '300', 'NO'),
('111117', 'Yaar-Hakofim',      '314813831','2024-03-11','12:00', '3', 'YES', 'YES', 'NO', '300', 'NO');



CREATE TABLE gonaturedb.report_visit (
    ReportId VARCHAR(255) PRIMARY KEY,
    ParkName VARCHAR(255),
    Year VARCHAR(255),
    Month VARCHAR(255),
    NumberOfVisitors VARCHAR(255),
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



INSERT INTO gonaturedb.report_visit (ReportId, ParkName, Year, Month, NumberOfVisitors) 
VALUES 
('1000', 'Yaar-Hakofim',       '2023', '10', '1'),
('1001', 'Safari',             '2023', '09', '1'),
('1002', 'Mitspe-Tat-Yami',    '2022', '04', '1'),
('1003', 'Yaar-Hakofim',       '2023', '04', '5'),
('1004', 'Safari',             '2022', '11', '3'),
('1005', 'Safari',             '2022', '12', '7'),
('1006', 'Yaar-Hakofim',       '2022', '03', '12'),
('1007', 'Gan-Tanahi',         '2023', '09', '6'),
('1008', 'Hay-Park',           '2023', '04', '15'),
('1009', 'Safari',             '2022', '05', '1'),
('1010', 'Safari',             '2022', '04', '4'),
('1011', 'Safari',             '2022', '06', '2'),
('1012', 'Mitspe-Tat-Yami',    '2022', '07', '1'),
('1013', 'Gan-Tanahi',         '2023', '10', '7'),
('1014', 'Safari',             '2022', '05', '2'),
('1015', 'Safari',             '2023', '09', '6'),
('1016', 'Yaar-Hakofim',       '2023', '12', '6'),
('1017', 'Gan-Tanahi',         '2022', '02', '7'),
('1018', 'Hay-Park',           '2022', '11', '14'),
('1019', 'Yaar-Hakofim',       '2022', '04', '4');










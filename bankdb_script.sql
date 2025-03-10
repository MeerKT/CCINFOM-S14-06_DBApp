SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

/*
Note: fix insert values for account type table 

03-05: need to add stuff for irl grp members
       need to add account transaction history of members
*/

CREATE SCHEMA IF NOT EXISTS `bank_db` DEFAULT CHARACTER SET utf8;
USE `bank_db`;

DROP TABLE IF EXISTS `customer_records`;
CREATE TABLE IF NOT EXISTS `bank_db`.`Customer_Records` (
`customer_ID` INT NOT NULL, 
`first_name` VARCHAR(45) NOT NULL,
`last_name` VARCHAR(45) NOT NULL, 
`birthdate` DATE NOT NULL,
`phone_number`  VARCHAR(15) NOT NULL,
`email_address` VARCHAR(45) NOT NULL,

PRIMARY KEY (`customer_ID`),
UNIQUE INDEX `customer_ID_UNIQUE` (`customer_ID` ASC) VISIBLE)
ENGINE = InnoDB;

INSERT INTO `bank_db`.`customer_records`
(`customer_ID`, `first_name`, `last_name`, `birthdate`, `phone_number`, `email_address`)
VALUES
(1, 'Josep',	  'Gobles',    '1997-10-29', '09563662240', 'jgobles@gmail.com'), 
(2, 'Lalatina',	  'Dustiness', '2000-01-14', '09566177681', 'dustiness16@gmail.com'),
(3, 'Ido',		  'Bondrewd',  '1989-04-10', '09170286264', 'bondrewd@gmail.com'),
(4, 'Belisarius', 'Cawl',      '2003-11-16', '09564311080', 'notezekiel_sedayne@gmail.com'),
(5, 'Orion', 	  'Pax',       '1985-12-25', '09176565226', 'optimus_prime85@gmail.com'),

(6,  'Jirkniv', 'Janovich',  '1995-12-23', '09172986734',  'jano123@gmail.com'),
(7,  'Natsuki', 'Subaru',    '2003-04-01', '09561817370',  'natsukisubaru@gmail.com'),
(8,  'Epsilus', 'Mygelion',   '1999-05-15', '09562387614',  'epsilus_mygelion40@gmail.com'),
(9,  'Konrad',  'Curze',     '1997-06-16', '09174573620',  'nighthaunter@gmail.com'),
(10, 'John',    'MacTavish', '1997-11-06', '09565392270',  'soapmactavish@gmail.com'),

(11, 'Bokoen',  'Braun',      '1994-12-19', '09563780739', 'denmark_bokoen1@gmail.com'); 
/*
(12, 'Enzo',    'Abanes',     '2003-10-31', '09563171150', 'enzoabanes03@gmail.com'),
(13, 'Althea',  'Brillantes', '2003-10-30', '09266899812', 'altheakaitlin03@gmail.com'),
(14, 'Jullian', 'Cruz',       '200'),
(15, 'Luis',    'Biacora',    '2003-12-13', '09163780185', 'gab.biacora@gmail.com');
*/

DROP TABLE IF EXISTS `account_records`;
CREATE TABLE IF NOT EXISTS `bank_db`.`Account_Records` (
`account_ID` INT NOT NULL,
`customer_ID` INT NOT NULL,
`current_balance` FLOAT NOT NULL, -- bigint for larger values
`account_type` ENUM('Personal', 'Business', 'Special') NOT NULL,
`date_opened` DATE NOT NULL,
`date_closed` DATE NULL,
`account_status` ENUM('Active', 'Closed', 'Frozen') NOT NULL,

INDEX `fk_account_records_customer_records_idx` (`customer_ID` ASC) VISIBLE,
PRIMARY KEY (`account_ID`),
UNIQUE INDEX `customer_ID_UNIQUE` (`customer_ID` ASC) VISIBLE, 
UNIQUE INDEX `account_ID_UNIQUE` (`account_ID` ASC) VISIBLE, 
CONSTRAINT `fk_account_records_customer_records`
	FOREIGN KEY (`customer_ID`)
    REFERENCES `bank_db`.`customer_records` (`customer_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `bank_db`.`account_records`
(`account_ID`, `customer_ID`, `current_balance`, `account_type`, `date_opened`, `account_status`)
VALUES
(1, 1,   10458557.00,  'Business', '2005-05-24', '2025-04-25', 'Closed'), 
(2, 2,   5223630.00,   'Personal', '2018-01-14', 'Active'), 
(3, 3,   291606216.00, 'Business', '2007-09-06', 'Frozen'),
(4, 4,   42500.00,     'Personal', '2020-10-31', 'Active'),
(5, 5,   343081796.00, 'Special',  '2005-08-08', 'Active'),
(6, 6,   758930.50,    'Personal', '2019-03-15', 'Active'),
(7, 7,   13450200.75,  'Business', '2012-07-22', '2025-07-15', 'Closed'),
(8, 8,   1876543.00,   'Special',  '2016-11-30', 'Frozen'),
(9, 9,   305000.00,    'Personal', '2021-06-25', 'Active'),
(10, 10, 67894321.00,  'Business', '2010-12-05', '2025-12-28', 'Closed'),
(11, 11, 254100.00,    'Special',  '2022-09-18', 'Active'),
(12, 12, 9976500.00,   'Business', '2015-05-07', 'Frozen'),
(13, 13, 123500.00,    'Personal', '2023-01-10', 'Active'),
(14, 14, 84350000.00,  'Special',  '2008-04-21', 'Active'),
(15, 15, 159000.00,    'Personal', '2020-08-15', '2025-09-30', 'Closed');

DROP TABLE IF EXISTS `account_type`;
CREATE TABLE IF NOT EXISTS `bank_db`.`Account_Type` (
`account_ID` INT NOT NULL, 
`interest_rate` FLOAT NOT NULL,
`min_balance` FLOAT NOT NULL,

PRIMARY KEY (`account_ID`),
UNIQUE INDEX `account_ID_UNIQUE` (`account_ID` ASC) VISIBLE,
CONSTRAINT `fk_account_type_account_records`
	FOREIGN KEY (`account_ID`)
    REFERENCES `bank_db`.`account_records` (`account_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `bank_db`.`account_type`
(`account_ID`, `interest_rate`, `min_balance`)
VALUES
(1, 0.5, 1000.00),
(2, 1.2, 5000.00),
(3, 0.8, 2500.00),
(4, 1.5, 10000.00),
(5, 0.6, 1500.00),
(6, 2.0, 20000.00),
(7, 1.8, 15000.00),
(8, 1.0, 7500.00),
(9, 0.7, 3000.00),
(10, 1.3, 12000.00),
(11, 0.9, 4000.00),
(12, 2.2, 25000.00),
(13, 1.1, 8500.00),
(14, 1.7, 18000.00),
(15, 0.4, 800.00);

DROP TABLE IF EXISTS `account_transaction_history`;
CREATE TABLE IF NOT EXISTS `bank_db`.`Account_Transaction_History` (
`transaction_ID` INT NOT NULL AUTO_INCREMENT, 
`amount` FLOAT NOT NULL,
`transaction_date` DATE NOT NULL,
`transaction_status` ENUM('Successful', 'Failed', 'Pending', 'Cancelled') NOT NULL,
`sender_acc_ID` INT NOT NULL,
`receiver_acc_ID` INT NOT NULL,

PRIMARY KEY (`transaction_ID`),
UNIQUE INDEX `transaction_ID_UNIQUE` (`transaction_ID` ASC) VISIBLE,
CONSTRAINT `fk_sender_account`
    FOREIGN KEY (`sender_acc_ID`) 
    REFERENCES `bank_db`.`Accounts` (`account_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    
CONSTRAINT `fk_receiver_account`
    FOREIGN KEY (`receiver_acc_ID`) 
    REFERENCES `bank_db`.`Accounts` (`account_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

INSERT INTO `bank_db`.`account_transaction_history`
(`amount`, `transaction_date`, `transaction_status`, `sender_acc_ID`, `receiver_acc_ID`)
VALUES
(5000.00, 	'2024-01-15', 'Successful', 1, 2), -- Josep to Lalatina
(2500.50, 	'2024-01-20', 'Pending', 	3, 4), -- Ido to Belisarius
(100000.00, '2024-02-10', 'Successful', 5, 1), -- Orion to Josep
(750.25, 	'2024-02-15', 'Failed', 	2, 3), -- Lalatina to Ido
(20000.00, 	'2024-03-01', 'Cancelled', 	4, 5), -- Belisarius to Orion

(3500.75,  '2024-03-10', 'Successful',  6, 7),  -- Jirkniv to Natsuki
(45000.00, '2024-03-22', 'Pending',     8, 9),  -- Epsilus to Konrad
(900.99,   '2024-04-05', 'Failed',      10, 11),-- John to Bokoen
(15000.50, '2024-04-15', 'Successful',  11, 1), -- Bokoen to Josep
(500.00,   '2024-04-20', 'Cancelled',   9, 3);  -- Konrad to Ido

/*
(455.50,  '2025-02-10', 'Successful',  15, 12),  -- Luis to Enzo
(15000.03, '2025-02-22', 'Pending',     7, 13),  -- Natsuki to Althea
(600.80,   '2025-03-05', 'Failed',      12, 14),-- Enzo to Jullian
(143.50, '2024-03-15', 'Successful',  14, 15), -- Jullian to Luis
(530.00,   '2024-03-20', 'Cancelled',   13, 10);  -- Althea to John 
*/

DROP TABLE IF EXISTS `loan_transaction_history`;
CREATE TABLE IF NOT EXISTS `bank_db`.`Loan_Transaction_History` (
`loan_transaction_ID` INT NOT NULL AUTO_INCREMENT, 
`loan_amount` FLOAT NOT NULL,
`loan_transaction_date` DATE NOT NULL,
`loan_transaction_status` ENUM('Successful', 'Failed', 'Pending', 'Cancelled') NOT NULL,
`lender_acc_ID` INT NOT NULL,
`borrower_acc_ID` INT NOT NULL,

PRIMARY KEY (`loan_transaction_ID`),
UNIQUE INDEX `loan_transaction_ID_UNIQUE` (`loan_transaction_ID` ASC) VISIBLE,

CONSTRAINT `fk_lender_account`
    FOREIGN KEY (`lender_acc_ID`) 
    REFERENCES `bank_db`.`Account_Records` (`account_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    
CONSTRAINT `fk_borrower_account`
    FOREIGN KEY (`borrower_acc_ID`) 
    REFERENCES `bank_db`.`Account_Records` (`account_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

INSERT INTO `bank_db`.`loan_transaction_history`
(`loan_amount`, `loan_transaction_date`, `loan_transaction_status`, `lender_acc_ID`, `borrower_acc_ID`)
VALUES
(500000.00,  '2023-12-01', 'Successful', 1, 2), -- Josep to Lalatina
(75000.00, 	 '2023-12-15', 'Pending', 	 3, 4), -- Ido to Belisarius
(2500000.00, '2024-01-10', 'Successful', 5, 1), -- Orion to Josep
(60000.00, 	 '2024-01-25', 'Failed', 	 2, 3), -- Lalatina to Ido
(120000.00,  '2024-02-05', 'Cancelled',  4, 5), -- Belisarius to Orion

(600000.00,  '2024-02-20', 'Successful',  9, 7),  -- Konrad to Natsuki
(92000.00,   '2024-03-05', 'Pending',     8, 10), -- Epsilus to John
(135000.00,  '2024-03-18', 'Failed',      6, 11), -- Jirkniv to Bokoen
(2000000.00, '2024-03-30', 'Successful',  11, 5), -- Bokoen to Orion
(56000.75,   '2024-04-12', 'Cancelled',   2, 8);  -- Lalatina to Epsilus

/*
(800000.00,  '2025-01-20', 'Successful',  10, 6),  -- John to Jirkniv
(92000.00,   '2025-03-25', 'Pending',     7, 12), -- Natsuki to Enzo
(185000.00,  '2025-02-18', 'Failed',      12, 13), -- Enzo to Althea
(1360000.60, '2025-03-31', 'Successful',  13, 14), -- Althea to Julllian
(9000000.90,   '2025-01-12', 'Cancelled',   14, 15);  -- Julllian to Luis

*/

DROP TABLE IF EXISTS `Loan_Options`;
CREATE TABLE IF NOT EXISTS `bank_db`.`Loan_Options` (
`loan_option_ID` INT NOT NULL, 
`loan_option_type` ENUM('Business Loan', 'Personal Loan', 'Special Loan', 'Mortgage Loan', 'Auto Loan', 'Education Loan') NOT NULL,
`interest_rate` FLOAT NOT NULL,
`loan_duration_month` INT NOT NULL,
`max_loan_amt` FLOAT NOT NULL,
`min_loan_amt` FLOAT NOT NULL,

PRIMARY KEY(`loan_option_ID`))
ENGINE = InnoDB;

INSERT INTO `bank_db`.`loan_options`
(`loan_option_ID`, `loan_option_type`, `interest_rate`, `loan_duration_month`, `max_loan_amt`, `min_loan_amt`)
VALUES
(1, 'Business Loan',  5.5, 24,  5000000.00,  50000.00),
(2, 'Personal Loan',  7.0, 12,  1500000.00,  25000.00),
(3, 'Special Loan',   3.2, 36,  10000000.00, 100000.00),
(4, 'Mortgage Loan',  4.5, 60,  8000000.00,  100000.00),
(5, 'Auto Loan',      5.2, 48,  3000000.00,  50000.00),
(6, 'Education Loan', 3.8, 72,  1000000.00,  20000.00);

DROP TABLE IF EXISTS `availed_loans`;
CREATE TABLE IF NOT EXISTS `bank_db`.`Availed_Loans` (
`loan_ID` INT NOT NULL,
`loan_option_ID` INT NOT NULL, 
`principal_amt` FLOAT NOT NULL,
`first_month_principal_amortization` FLOAT NOT NULL,
`succeding _principal_amortization` FLOAT NOT NULL,  
`interest_amortization` FLOAT NOT NULL,
`principal_balance` FLOAT NOT NULL,
`interest_balance` FLOAT NOT NULL,
`start_date` DATE NOT NULL, 
`end_date` DATE NOT NULL,
`month_payment_day` DATE NOT NULL,
`loan_status` ENUM('Fully Paid', 'Unpaid', 'Ongoing') NOT NULL,
`customer_ID` INT NOT NULL,

UNIQUE INDEX `loan_ID_UNIQUE` (`loan_ID` ASC) VISIBLE, 
PRIMARY KEY (`loan_ID`),

CONSTRAINT `fk_customer_ID`
    FOREIGN KEY (`customer_ID`) 
    REFERENCES `bank_db`.`Customer_Records` (`customer_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;

INSERT INTO `bank_db`.`availed_loans`
(`loan_ID`, `loan_option_ID`, `principal_amt`, `first_month_principal_amortization`, 
 `succeding _principal_amortization`, `interest_amortization`, `principal_balance`, 
 `interest_balance`, `start_date`, `end_date`, `month_payment_day`, `loan_status`, `customer_ID`)
VALUES
(1, 1, 1000000.00, 50000.00, 40000.00, 10000.00, 800000.00, 200000.00, '2024-01-01', '2025-01-01', '2024-02-01', 'Ongoing', 1),     -- Josep (Business Loan)
(2, 2, 250000.00, 20000.00, 15000.00, 5000.00, 175000.00, 75000.00, '2024-01-15', '2025-01-15', '2024-02-15', 'Ongoing', 2),        -- Lalatina (Personal Loan)
(3, 3, 5000000.00, 200000.00, 180000.00, 20000.00, 3500000.00, 1500000.00, '2023-12-10', '2024-12-10', '2024-01-10', 'Unpaid', 3),  -- Ido (Special Loan)
(4, 1, 300000.00, 25000.00, 20000.00, 5000.00, 200000.00, 100000.00, '2024-02-05', '2025-02-05', '2024-03-05', 'Ongoing', 4),       -- Belisarius (Business Loan)
(5, 2, 1500000.00, 100000.00, 90000.00, 10000.00, 900000.00, 600000.00, '2023-11-20', '2024-11-20', '2023-12-20', 'Fully Paid', 5), -- Orion (Personal Loan)
(6, 4, 6000000.00, 250000.00, 230000.00, 20000.00, 4500000.00, 1500000.00, '2024-03-01', '2029-03-01', '2024-04-01', 'Ongoing', 6), -- Jirkniv (Mortgage Loan)
(7, 5, 1200000.00, 70000.00, 65000.00, 5000.00, 850000.00, 350000.00, '2024-02-15', '2028-02-15', '2024-03-15', 'Unpaid', 7),       -- Natsuki (Auto Loan)
(8, 6, 500000.00, 25000.00, 20000.00, 5000.00, 400000.00, 100000.00, '2024-04-10', '2030-04-10', '2024-05-10', 'Ongoing', 8),       -- Epsilus (Education Loan)
(9, 3, 3000000.00, 180000.00, 150000.00, 30000.00, 2000000.00, 1000000.00, '2023-10-25', '2026-10-25', '2023-11-25', 'Ongoing', 9), -- Konrad (Special Loan)
(10, 2, 500000.00, 35000.00, 30000.00, 5000.00, 300000.00, 200000.00, '2024-05-01', '2025-05-01', '2024-06-01', 'Unpaid', 10),      -- John (Personal Loan)
(11, 1, 2000000.00, 100000.00, 85000.00, 15000.00, 1500000.00, 500000.00, '2024-06-01', '2026-06-01', '2024-07-01', 'Ongoing', 11); -- Bokoen (Business Loan); 

/*
(12, 6, 10000000.00, 76000.00, 64000.00, 50000.00, 900000.00, 300000.00, '2025-10-23', '2030-02-15', '2025-12-23', 'Unpaid', 12),       -- Enzo(Education Loan)
(13, 6, 75000000.00, 23000.00, 60000.00, 9000.00, 900000.00, 600000.00, '2024-04-10', '2031-04-10', '2024-05-10', 'Ongoing', 13),       -- Althea (Education Loan)
(14, 6, 5000000.00, 190000.00, 670000.00, 70000.00, 8000000.00, 9000000.00, '2023-10-25', '2027-10-25', '2023-11-25', 'Ongoing', 14), -- Jullian (Education Loan)
(15, 6, 600000.00, 36000.00, 38000.00, 1000.00, 200000.00, 500000.00, '2024-05-01', '2029-05-01', '2024-06-01', 'Unpaid', 15),      -- Luis (Education Loan)
*/

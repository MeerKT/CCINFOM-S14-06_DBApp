SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

/*
Note: fix insert values for account type table 
*/

CREATE SCHEMA IF NOT EXISTS `bank_db` DEFAULT CHARACTER SET utf8;
USE `bank_db`;

DROP TABLE IF EXISTS `customer_records`;
CREATE TABLE IF NOT EXISTS `bank_db`.`Customer_Records` (
`customer_ID` INT NOT NULL, 
`first_name` VARCHAR(45) NOT NULL,
`last_name` VARCHAR(45) NOT NULL, 
`birthdate` DATE NOT NULL,
`phone_number` BIGINT NOT NULL,
`email_address` VARCHAR(45) NOT NULL,

PRIMARY KEY (`customer_ID`),
UNIQUE INDEX `customer_ID_UNIQUE` (`customer_ID` ASC) VISIBLE)
ENGINE = InnoDB;

INSERT INTO `bank_db`.`customer_records`
(`customer_ID`, `first_name`, `last_name`, `birthdate`, `phone_number`, `email_address`)
VALUES
(1, 'Josep',	  'Gobles',    '1997-10-29', 09563662240, 'jgobles@gmail.com'), 
(2, 'Lalatina',	  'Dustiness', '2000-01-14', 09566177681, 'dustiness16@gmail.com'),
(3, 'Ido',		  'Bondrewd',  '1989-04-10', 09170286264, 'bondrewd@gmail.com'),
(4, 'Belisarius', 'Cawl',      '2003-11-16', 09564311080, 'notezekiel_sedayne@gmail.com'),
(5, 'Orion', 	  'Pax',       '1985-12-25', 09176565226, 'optimus_prime85@gmail.com');

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
(1, 1, 10458557.00,  'Business', '2005-05-24', 'Closed'), 
(2, 2, 5223630.00, 	 'Personal', '2018-01-14', 'Active'), 
(3, 3, 291606216.00, 'Business', '2007-09-06', 'Frozen'),
(4, 4, 42500.00,  	 'Personal', '2020-10-31', 'Active'),
(5, 5, 343081796.00, 'Special',	 '2005-08-08', 'Active');

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
(1, 1.2, 10.00),
(2, 2.3, 20.00),
(3, 3.4, 30.00),
(4, 4.5, 40.00),
(5, 5.6, 50.00);

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

/*acc-trans-his INSERT INTO here*/

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

/*loan-trans-his INSERT INTO here*/

DROP TABLE IF EXISTS `availed_loans`;
CREATE TABLE IF NOT EXISTS `bank_db`.`Availed_Loans` (
`loan_ID` INT NOT NULL,
`loan_option_ID` INT NOT NULL, 
`principal_amt` FLOAT NOT NULL,
`fmpa` FLOAT NOT NULL, -- first month principal amortization
`spa` FLOAT NOT NULL, -- succeding principal amortization
`interest_amortization` FLOAT NOT NULL,
`principal_balance` FLOAT NOT NULL,
`interest_balance` FLOAT NOT NULL,
`start_date` DATE NOT NULL, 
`end_date` DATE NOT NULL,
`month_payment_day` DATE NOT NULL,
`loan_status` ENUM('Fully Paid', 'Unpaid', 'Ongoing') NOT NULL,
`customer_ID` INT NOT NULL,

UNIQUE INDEX `loan_option_ID_UNIQUE` (`loan_option_ID` ASC) VISIBLE, 


CONSTRAINT `fk_customer_ID`
    FOREIGN KEY (`customer_ID`) 
    REFERENCES `bank_db`.`Customer_Records` (`customer_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;

/* availed loans INSERT INTO here*/

DROP TABLE IF EXISTS `Loan_Options`;
CREATE TABLE IF NOT EXISTS `bank_db`.`Loan_Options` (
`loan_option_ID` INT NOT NULL, 
`loan_option_type` ENUM('Business Loan', 'Personal Loan', 'Special Loan') NOT NULL,
`interest_rate` FLOAT NOT NULL,
`loan_duration_month` INT NOT NULL,
`max_loan_amt` FLOAT NOT NULL,
`min_loan_amt` FLOAT NOT NULL,

CONSTRAINT `fk_loan_option_ID`
    FOREIGN KEY (`loan_option_ID`) 
    REFERENCES `bank_db`.`Availed_Loans` (`loan_option_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

/*loan options INSERT ONTO here*/

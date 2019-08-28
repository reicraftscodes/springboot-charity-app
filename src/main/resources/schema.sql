SET MODE MySQL;
SET IGNORECASE=TRUE;

-- -----------------------------------------------------
-- Table `charity`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `charity` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `registration_id` VARCHAR(45) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `purpose` VARCHAR(500) NOT NULL,
  `logo_file_name` VARCHAR(100) NULL,
  `acronym` VARCHAR (10) NULL,
  is_active VARCHAR(1) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `charity_total`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `charity_total` (
  `charity_id` INT UNSIGNED NOT NULL,
  `total_before_gift_aid` INT UNSIGNED NOT NULL,
  `total_gift_aid` INT UNSIGNED NOT NULL,
  `total_after_gift_aid` INT UNSIGNED NOT NULL,
   `last_updated_date` TIMESTAMP NOT NULL,
  PRIMARY KEY (`charity_id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `gift_aid_rate`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `gift_aid_rate` (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `rate_percentage` DOUBLE UNSIGNED NOT NULL,
   `start_date` DATE NOT NULL,
   `end_date` DATE,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sponsor_form`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `sponsor_form` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `fundraiser_name` VARCHAR(200) NOT NULL,
  `charity_id` INT UNSIGNED NOT NULL,
  `fundraising_action` VARCHAR(1000) NOT NULL,
  `date_created` DATE NOT NULL,
  `first_valid_day` DATE NOT NULL,
  `last_valid_day` DATE NOT NULL,
  `furl` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

create table IF NOT EXISTS sponsor_form_temp (
	id INT,
	fundraiser_name VARCHAR(50),
	charity_id VARCHAR(5),
	fundraising_action TEXT,
	date_created varchar(15),
	first_valid_day varchar(15),
	last_valid_day varchar(15),
	furl VARCHAR(50))
ENGINE = InnoDB;

create table IF NOT EXISTS donations_temp (
	id INT,
	amount_in_pence INT,
	donation_date varchar(15),
	is_own_money INT,
	has_no_benefit_to_donor INT,
	wishes_to_gift_aid INT,
	donor_id VARCHAR(4),
	sponsor_form_id VARCHAR(8),
	charity_id VARCHAR(5)
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bank_account`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `bank_account` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `charity_id` INT UNSIGNED NOT NULL,
  `account_number` INT(11) NOT NULL,
  `sort_code` INT(11) NOT NULL,
  `last_valid_day` DATE ,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `address`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `address` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `building_name` VARCHAR(45) NULL,
  `building_number` INT(11) NULL,
  `street` VARCHAR(100) NULL,
  `district` VARCHAR(100) NULL,
  `city` VARCHAR(100) NOT NULL,
  `postal_code` VARCHAR(12) NOT NULL,
  `country_iso_code` VARCHAR(4) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `donor`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `donor` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(100) NOT NULL,
  `last_name` VARCHAR(100) NOT NULL,
  `address_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `donation`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `donation` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `amount_in_pence` INT NOT NULL,
  donation_date DATE NOT NULL,
  is_own_money INT,
	has_no_benefit_to_donor INT,
	wishes_to_gift_aid INT,
  `donor_id` INT UNSIGNED NOT NULL,
  `sponsor_form_id` INT UNSIGNED NULL,
  `charity_id` INT UNSIGNED NOT NULL,
  `payment_reference` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `gift_aid_donation`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `gift_aid_donation` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `donation_id` INT UNSIGNED NOT NULL,
  `amount_in_pence` INT NOT NULL,
  donation_date DATE NOT NULL,
  is_own_money INT,
	has_no_benefit_to_donor INT,
	wishes_to_gift_aid INT,
  `donor_id` INT UNSIGNED NOT NULL,
  `sponsor_form_id` INT UNSIGNED NULL,
  `charity_id` INT UNSIGNED NOT NULL,
  `payment_reference` VARCHAR(30) NOT NULL,
  `gift_aid_amount` DOUBLE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


ALTER TABLE `bank_account`
CHANGE COLUMN `last_valid_day` `last_valid_day` DATE NULL ;



CREATE TABLE IF NOT EXISTS `bank_account_temp` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `charity_id` INT UNSIGNED NOT NULL,
  `account_number` INT(11) NOT NULL,
  `sort_code` INT(11) NOT NULL,
  `last_valid_day` VARCHAR(15),
  PRIMARY KEY (`id`))
ENGINE = InnoDB;
/*
SQLyog Community v11.23 (32 bit)
MySQL - 5.1.59-community : Database - yesaccount
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`yesaccount` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `yesaccount`;

/*Table structure for table `account_feature` */

DROP TABLE IF EXISTS `account_feature`;

CREATE TABLE `account_feature` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `active_interset_calculation` bit(1) NOT NULL,
  `allow_cash_accounts_in_journals` bit(1) NOT NULL,
  `allow_expenses_fixed_assets_in_purchase_accounts` bit(1) NOT NULL,
  `allow_income_accounts_in_sale_vouchers` bit(1) NOT NULL,
  `allow_multiple_currency` bit(1) NOT NULL,
  `allow_others_details` bit(1) NOT NULL,
  `company_id` bigint(20) NOT NULL,
  `date_format` bit(1) NOT NULL,
  `decimal_character_to_use_in_amount` bit(1) NOT NULL,
  `enable_cheque_printing` bit(1) NOT NULL,
  `for_nontrading_account_also` bit(1) NOT NULL,
  `income_expense_statement_instead_ofpl` bit(1) NOT NULL,
  `maintain_bill_wise_details` bit(1) NOT NULL,
  `show_balance_as_on_voucher_date` bit(1) NOT NULL,
  `show_ledger_balance` bit(1) NOT NULL,
  `showbillwise_details` bit(1) NOT NULL,
  `unique_key` int(11) NOT NULL,
  `use_address_for_ledger_accounts` bit(1) NOT NULL,
  `use_alias_name_along_with_namein_forms` bit(1) NOT NULL,
  `use_alias_name_in_report` bit(1) NOT NULL,
  `use_contacts_details_for_ledger_accounts` bit(1) NOT NULL,
  `use_cr_dr_instead_of_to_by` bit(1) NOT NULL,
  `use_debit_credit_notes` bit(1) NOT NULL,
  `use_name_in_report` bit(1) NOT NULL,
  `use_payment_recepit_as_contra` bit(1) NOT NULL,
  `use_perfix_symbol_fo_credit_amount` bit(1) NOT NULL,
  `use_perfix_symbol_for_debit_amount` bit(1) NOT NULL,
  `use_postfix_symbol_for_credit_amount` bit(1) NOT NULL,
  `use_postfix_symbol_for_debit_amount` bit(1) NOT NULL,
  `use_single_entry_mode_for_contra` bit(1) NOT NULL,
  `use_single_entry_mode_for_credit` bit(1) NOT NULL,
  `use_single_entry_mode_for_debit` bit(1) NOT NULL,
  `use_single_entry_mode_for_payment` bit(1) NOT NULL,
  `use_single_entry_mode_for_purchase` bit(1) NOT NULL,
  `use_single_entry_mode_for_recepit` bit(1) NOT NULL,
  `use_single_entry_mode_for_sale` bit(1) NOT NULL,
  `used_advance_parameters` bit(1) NOT NULL,
  `warn_on_negative_cash_balance` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_n5adhltblsfk6nmp03q0xd1hs` (`company_id`),
  CONSTRAINT `FK_n5adhltblsfk6nmp03q0xd1hs` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `account_feature` */

insert  into `account_feature`(`id`,`version`,`active_interset_calculation`,`allow_cash_accounts_in_journals`,`allow_expenses_fixed_assets_in_purchase_accounts`,`allow_income_accounts_in_sale_vouchers`,`allow_multiple_currency`,`allow_others_details`,`company_id`,`date_format`,`decimal_character_to_use_in_amount`,`enable_cheque_printing`,`for_nontrading_account_also`,`income_expense_statement_instead_ofpl`,`maintain_bill_wise_details`,`show_balance_as_on_voucher_date`,`show_ledger_balance`,`showbillwise_details`,`unique_key`,`use_address_for_ledger_accounts`,`use_alias_name_along_with_namein_forms`,`use_alias_name_in_report`,`use_contacts_details_for_ledger_accounts`,`use_cr_dr_instead_of_to_by`,`use_debit_credit_notes`,`use_name_in_report`,`use_payment_recepit_as_contra`,`use_perfix_symbol_fo_credit_amount`,`use_perfix_symbol_for_debit_amount`,`use_postfix_symbol_for_credit_amount`,`use_postfix_symbol_for_debit_amount`,`use_single_entry_mode_for_contra`,`use_single_entry_mode_for_credit`,`use_single_entry_mode_for_debit`,`use_single_entry_mode_for_payment`,`use_single_entry_mode_for_purchase`,`use_single_entry_mode_for_recepit`,`use_single_entry_mode_for_sale`,`used_advance_parameters`,`warn_on_negative_cash_balance`) values (1,0,'\0','\0','\0','\0','\0','\0',1,'\0','\0','\0','\0','\0','\0','\0','\0','\0',1,'\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0'),(2,0,'\0','\0','\0','\0','\0','\0',2,'\0','\0','\0','\0','\0','\0','\0','\0','\0',2,'\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0'),(3,0,'\0','\0','\0','\0','\0','\0',3,'\0','\0','\0','\0','\0','\0','\0','\0','\0',3,'\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0'),(4,0,'\0','\0','\0','\0','\0','\0',4,'\0','\0','\0','\0','\0','\0','\0','\0','\0',4,'\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0'),(5,0,'\0','\0','\0','\0','\0','\0',5,'\0','\0','\0','\0','\0','\0','\0','\0','\0',5,'\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0'),(6,0,'\0','\0','\0','\0','\0','\0',6,'\0','\0','\0','\0','\0','\0','\0','\0','\0',6,'\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0');

/*Table structure for table `account_flag` */

DROP TABLE IF EXISTS `account_flag`;

CREATE TABLE `account_flag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `unique_key` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_q64l9m7tt3vuxwviu8cmr1upm` (`parent_id`),
  CONSTRAINT `FK_q64l9m7tt3vuxwviu8cmr1upm` FOREIGN KEY (`parent_id`) REFERENCES `account_flag` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=255 DEFAULT CHARSET=latin1;

/*Data for the table `account_flag` */

insert  into `account_flag`(`id`,`version`,`description`,`name`,`parent_id`,`remark`,`unique_key`) values (1,0,NULL,'Advance',NULL,'billRef',1),(2,0,NULL,'Agst Ref.',NULL,'billRef',2),(3,0,NULL,'New Ref.',NULL,'billRef',3),(4,0,NULL,'On Account',NULL,'billRef',4),(5,0,NULL,'Excise',NULL,'TAX',5),(6,0,NULL,'Others',NULL,'TAX',6),(7,0,NULL,'VAT',NULL,'TAX',7),(8,0,NULL,'CST',NULL,'TAX',8),(9,0,NULL,'TCS',NULL,'TAX',9),(10,0,NULL,'ServiceTax',NULL,'TAX',10),(11,0,NULL,'TDS',NULL,'TAX',11),(12,0,NULL,'Not Applicable',NULL,'ROUND',12),(13,0,NULL,'Downward Rounding',NULL,'ROUND',13),(14,0,NULL,'Normal Rounding',NULL,'ROUND',14),(15,0,NULL,'Upward Rounding',NULL,'ROUND',15),(16,0,'Type of Organization','Co-Op Society',NULL,'TOO',16),(17,0,'Type of Organization','Individual/Proprietary',NULL,'TOO',17),(18,0,'Type of Organization','Others',NULL,'TOO',18),(19,0,'Type of Organization','partnership',NULL,'TOO',19),(20,0,'Type of Organization','Registered Private Ltd Company',NULL,'TOO',20),(21,0,'Type of Organization','Registered Public Ltd Company',NULL,'TOO',21),(22,0,'Type of Organization','Register Trust',NULL,'TOO',22),(23,0,'Type of Organization','Society',NULL,'TOO',23),(24,0,'Type of Dealer','Composition',NULL,'TOD',24),(25,0,'Type of Dealer','Composition to Regular',NULL,'TOD',25),(26,0,'Type of Dealer','Regular',NULL,'TOD',26),(27,0,'List of Company Type','Government',NULL,'LOC',27),(28,0,'List of Company Type','Others',NULL,'LOC',28),(29,0,'DH (Duty Head)','Additional Duty',5,'DH',29),(30,0,'DH (Duty Head)','Cess on Custom Duty',5,'DH',30),(31,0,'DH (Duty Head)','Cess on Duty',5,'DH',31),(32,0,'DH (Duty Head)','Customs Duty',5,'DH',32),(33,0,'DH (Duty Head)','Excise Duty',5,'DH',33),(34,0,'DH (Duty Head)','Special Duty',5,'DH',34),(35,0,NULL,'Additional Duty',29,NULL,35),(36,0,NULL,'Additional Duty',30,NULL,36),(37,0,NULL,'Surcharge on Tax',30,NULL,37),(38,0,NULL,'Additional Duty',31,NULL,38),(39,0,NULL,'Surcharge on Tax',31,NULL,39),(40,0,NULL,'On Total Sales',32,NULL,40),(41,0,NULL,'Tax based on Item rate',32,NULL,41),(42,0,NULL,'On Total Sales',33,NULL,42),(43,0,NULL,'Tax based on Item rate',33,NULL,43),(44,0,NULL,'On Total Sales',34,NULL,44),(45,0,NULL,'Tax based on Item rate',34,NULL,45),(46,0,NULL,'Additional Duty',8,NULL,46),(47,0,NULL,'On Total Sales',8,NULL,47),(48,0,NULL,'Surcharge on Tax',8,NULL,48),(49,0,NULL,'Tax based on Item rate',8,NULL,49),(50,0,NULL,'Additional Duty',6,NULL,50),(51,0,NULL,'On Total Sales',6,NULL,51),(52,0,NULL,'Surcharge on Tax',6,NULL,52),(53,0,NULL,'Tax based on Item rate',6,NULL,53),(54,0,'SC ( Service Category )','Advertising Agency',10,'SC',54),(55,0,'SC ( Service Category )','Advertising Space Or Time',10,'SC',55),(56,0,'SC ( Service Category )','Airport Services',10,'SC',56),(57,0,'SC ( Service Category )','Air Transport of Passenger',10,'SC',57),(58,0,'SC ( Service Category )','Air Travel Agency',10,'SC',58),(59,0,'SC ( Service Category )','Architects Services',10,'SC',59),(60,0,'SC ( Service Category )','Asset Management',10,'SC',60),(61,0,'SC ( Service Category )','ATM Operations',10,'SC',61),(62,0,'SC ( Service Category )','Auctioneer\'s Services',10,'SC',62),(63,0,'SC ( Service Category )','Banking and Financial',10,'SC',63),(64,0,'SC ( Service Category )','Beauty Parlours',10,'SC',64),(65,0,'SC ( Service Category )','Broadcasting Services',10,'SC',65),(66,0,'SC ( Service Category )','Business and Exhibition Services',10,'SC',66),(67,0,'SC ( Service Category )','Business Auxiliary Services',10,'SC',67),(68,0,'SC ( Service Category )','Business Support Services',10,'SC',68),(69,0,'SC ( Service Category )','Cable Operators',10,'SC',69),(70,0,'SC ( Service Category )','Cargo Handling Services',10,'SC',70),(71,0,'SC ( Service Category )','Chartered Accountants',10,'SC',71),(72,0,'SC ( Service Category )','Cleaning Services',10,'SC',72),(73,0,'SC ( Service Category )','Clearing and Forwarding Agency',10,'SC',73),(74,0,'SC ( Service Category )','Commercial Training & Coaching',10,'SC',74),(75,0,'SC ( Service Category )','Company Secretaries',10,'SC',75),(76,0,'SC ( Service Category )','Construction of Res.Complex',10,'SC',76),(77,0,'SC ( Service Category )','Construction Services in Commercial/Industrial/Civil',10,'SC',77),(78,0,'SC ( Service Category )','Consulting Engineering',10,'SC',78),(79,0,'SC ( Service Category )','Container By Rail',10,'SC',79),(80,0,'SC ( Service Category )','Convention Service',10,'SC',80),(81,0,'SC ( Service Category )','Cost Accountant',10,'SC',81),(82,0,'SC ( Service Category )','Courier Agency',10,'SC',82),(83,0,'SC ( Service Category )','Credit Card Related Services',10,'SC',83),(84,0,'SC ( Service Category )','Credit Rating Agencies',10,'SC',84),(85,0,'SC ( Service Category )','Custom House Agent',10,'SC',85),(86,0,'SC ( Service Category )','Design Services',10,'SC',86),(87,0,'SC ( Service Category )','Development and Supply of Content',10,'SC',87),(88,0,'SC ( Service Category )','Dredging Services',10,'SC',88),(89,0,'SC ( Service Category )','Dry Cleaning Services',10,'SC',89),(90,0,'SC ( Service Category )','Erection,Commissioning and Installation',10,'SC',90),(91,0,'SC ( Service Category )','Event Management Services',10,'SC',91),(92,0,'SC ( Service Category )','Facsimile Services',10,'SC',92),(93,0,'SC ( Service Category )','Fashion Designer Services',10,'SC',93),(94,0,'SC ( Service Category )','Forward Contract Services',10,'SC',94),(95,0,'SC ( Service Category )','Franchise Services',10,'SC',95),(96,0,'SC ( Service Category )','General Insurance Business',10,'SC',96),(97,0,'SC ( Service Category )','Goods Transport Operator',10,'SC',97),(98,0,'SC ( Service Category )','Health Club and Fitness Centre',10,'SC',98),(99,0,'SC ( Service Category )','Information Technology Software Centre',10,'SC',99),(100,0,'SC ( Service Category )','Insurance Auxiliary',10,'SC',100),(101,0,'SC ( Service Category )','Intellectual Property Services Other than Copyright',10,'SC',101),(102,0,'SC ( Service Category )','Interior Decorators',10,'SC',102),(103,0,'SC ( Service Category )','Internet Cafe',10,'SC',103),(104,0,'SC ( Service Category )','Internet Telecommunication Services',10,'SC',104),(105,0,'SC ( Service Category )','Leased Circuits',10,'SC',105),(106,0,'SC ( Service Category )','Life Insurance Services',10,'SC',106),(107,0,'SC ( Service Category )','Mailing Lite Compilation',10,'SC',107),(108,0,'SC ( Service Category )','Maintenance and Repair Service',10,'SC',108),(109,0,'SC ( Service Category )','Managements Consultants',10,'SC',109),(110,0,'SC ( Service Category )','Management Of Investment in ULIP',10,'SC',110),(111,0,'SC ( Service Category )','Mandap Keeper',10,'SC',111),(112,0,'SC ( Service Category )','Manpower Recruiter Agency',10,'SC',112),(113,0,'SC ( Service Category )','Market Research Agency',10,'SC',113),(114,0,'SC ( Service Category )','Mechanized Slaughter House',10,'SC',114),(115,0,'SC ( Service Category )','MemberShip Of Clubs',10,'SC',115),(116,0,'SC ( Service Category )','Mining of Mineral,oil and Gas',10,'SC',116),(117,0,'SC ( Service Category )','Online Information and Data',10,'SC',117),(118,0,'SC ( Service Category )','Opinion Poll Services',10,'SC',118),(119,0,'SC ( Service Category )','Outdoor Catering',10,'SC',119),(120,0,'SC ( Service Category )','Packaging Services',10,'SC',120),(121,0,'SC ( Service Category )','Pager Services',10,'SC',121),(122,0,'SC ( Service Category )','Pandal Or Shamiana Services',10,'SC',122),(123,0,'SC ( Service Category )','Photography Service',10,'SC',123),(124,0,'SC ( Service Category )','Port Services',10,'SC',124),(125,0,'SC ( Service Category )','Processing and Clearing House Agent Service',10,'SC',125),(126,0,'SC ( Service Category )','Public Relations Service',10,'SC',126),(127,0,'SC ( Service Category )','Rail Travel Agent',10,'SC',127),(128,0,'SC ( Service Category )','Real Estate Agent',10,'SC',128),(129,0,'SC ( Service Category )','Recognized Or Registered Association Service',10,'SC',129),(130,0,'SC ( Service Category )','Recognized Stock Exchange Service',10,'SC',130),(131,0,'SC ( Service Category )','Recovery Agent',10,'SC',131),(132,0,'SC ( Service Category )','Registrar to an Issue',10,'SC',132),(133,0,'SC ( Service Category )','Rent-A-Cab Service',10,'SC',133),(134,0,'SC ( Service Category )','Renting of Immovable Property',10,'SC',134),(135,0,'SC ( Service Category )','Scientific and Technical Consultancy',10,'SC',135),(136,0,'SC ( Service Category )','Security Agency',10,'SC',136),(137,0,'SC ( Service Category )','Servicing of Motor Vehicle',10,'SC',137),(138,0,'SC ( Service Category )','Share Transfer Agent',10,'SC',138),(139,0,'SC ( Service Category )','Ship Management Service',10,'SC',139),(140,0,'SC ( Service Category )','Site Preparation And Clearance',10,'SC',140),(141,0,'SC ( Service Category )','Sound Recording And Service',10,'SC',141),(142,0,'SC ( Service Category )','Sponsorship Service',10,'SC',142),(143,0,'SC ( Service Category )','Steamer Agent',10,'SC',143),(144,0,'SC ( Service Category )','Stock Broker',10,'SC',144),(145,0,'SC ( Service Category )','Storage And Warehouse Service',10,'SC',145),(146,0,'SC ( Service Category )','Supply of Tangible Goods Service',10,'SC',146),(147,0,'SC ( Service Category )','Survey and Map Making',10,'SC',147),(148,0,'SC ( Service Category )','Telecommunication Service',10,'SC',148),(149,0,'SC ( Service Category )','Telegraph Service',10,'SC',149),(150,0,'SC ( Service Category )','Telephone Services',10,'SC',150),(151,0,'SC ( Service Category )','Telex Services',10,'SC',151),(152,0,'SC ( Service Category )','Test,Inspection,Certification',10,'SC',152),(153,0,'SC ( Service Category )','Tour Operator',10,'SC',153),(154,0,'SC ( Service Category )','Transport By Cruise Ships',10,'SC',154),(155,0,'SC ( Service Category )','Transport of Goods By Air',10,'SC',155),(156,0,'SC ( Service Category )','Transport of Goods By Pipeline',10,'SC',156),(157,0,'SC ( Service Category )','Transport of Goods By Road',10,'SC',157),(158,0,'SC ( Service Category )','Travel Agents(other then Air/rail Agent)',10,'SC',158),(159,0,'SC ( Service Category )','TV or Radio Programme Production',10,'SC',159),(160,0,'SC ( Service Category )','Under Writers',10,'SC',160),(161,0,'SC ( Service Category )','Video Tap Production',10,'SC',161),(162,0,'SC ( Service Category )','Works Contract Services',10,'SC',162),(163,0,'NOP (Nature Of Payment)','Any Other Income',11,'NOP',163),(164,0,'NOP (Nature Of Payment)','Any Other Interest on Securities as per Section++uniqueCounter93',11,'NOP',164),(165,0,'NOP (Nature Of Payment)','Commission on Sale of Lottery Tickets',11,'NOP',165),(166,0,'NOP (Nature Of Payment)','Commission Or Brokerage',11,'NOP',166),(167,0,'NOP (Nature Of Payment)','Deemed Dividend U/s 2(22)E',11,'NOP',167),(168,0,'NOP (Nature Of Payment)','Fees for Professional And Technical Services',11,'NOP',168),(169,0,'NOP (Nature Of Payment)','Fees for Tech. Services Agreement is Made After Feb 29,1964 before April++uniqueCounter,1976',11,'NOP',169),(170,0,'NOP (Nature Of Payment)','Fees for Tech. Services Agreement is Made After March 31,1976 before June++uniqueCounter,1997',11,'NOP',170),(171,0,'NOP (Nature Of Payment)','Fees for Tech. Services Agreement is Made After May 31,1997 before June++uniqueCounter,2005',11,'NOP',171),(172,0,'NOP (Nature Of Payment)','Fees for Tech. Services Agreement is Made on or After June++uniqueCounter,2005',11,'NOP',172),(173,0,'NOP (Nature Of Payment)','Income By Way  of Long-Term Gains Referred in Section in++uniqueCounter15E',11,'NOP',173),(174,0,'NOP (Nature Of Payment)','Income From Foreign Currency Bonds Or Shares of',11,'NOP',174),(175,0,'NOP (Nature Of Payment)','Income From Foreign Exchange Assets payable to an indian Citizen',11,'NOP',175),(176,0,'NOP (Nature Of Payment)','Income in Respect In Units of Non-Residents',11,'NOP',176),(177,0,'NOP (Nature Of Payment)','Income of Foreign Institutional Investor\'s From',11,'NOP',177),(178,0,'NOP (Nature Of Payment)','Insurance Commission',11,'NOP',178),(179,0,'NOP (Nature Of Payment)','Interest on 8% Savings Taxable (Bond),2003',11,'NOP',179),(180,0,'NOP (Nature Of Payment)','Interest on nSecurities',11,'NOP',180),(181,0,'NOP (Nature Of Payment)','Interest Other Then interest On Securities',11,'NOP',181),(182,0,'NOP (Nature Of Payment)','Interest Payable By Government and Indian Concern In foreign Currency',11,'NOP',182),(183,0,'NOP (Nature Of Payment)','Long Term Capital Gains[Not Being Covered in Sec++uniqueCounter0(33)(36)(38)]',11,'NOP',183),(184,0,'NOP (Nature Of Payment)','Other Sums Parable\'s To A Non-Resident',11,'NOP',184),(185,0,'NOP (Nature Of Payment)','Payment Of Compensation on Acquisition Of Immovable Property',11,'NOP',185),(186,0,'NOP (Nature Of Payment)','Payments In Respect  of Deposit Under NSS',11,'NOP',186),(187,0,'NOP (Nature Of Payment)','Payments In respect of Units to an offShore Find',11,'NOP',187),(188,0,'NOP (Nature Of Payment)','Payments On AccountsOf Re-purchase of Units By..',11,'NOP',188),(189,0,'NOP (Nature Of Payment)','Payments To Contractors (Other Then Advertisement)',11,'NOP',189),(190,0,'NOP (Nature Of Payment)','Payments  to Non-Resident Sportsmen/Sports Association',11,'NOP',190),(191,0,'NOP (Nature Of Payment)','Payments to Contractors(Advertisement Contractors)',11,'NOP',191),(192,0,'NOP (Nature Of Payment)','Payments to Sub-Contractors',11,'NOP',192),(193,0,'NOP (Nature Of Payment)','Rent on Land ,Building or Furniture',11,'NOP',193),(194,0,'NOP (Nature Of Payment)','Rent on Plant,Machinery or Equipment',11,'NOP',194),(195,0,'NOP (Nature Of Payment)','Royalty(F) Agreement Is Made After May 31,1997 Before June++uniqueCounter,2005',11,'NOP',195),(196,0,'NOP (Nature Of Payment)','Royalty(F) Agreement Is Made  Before June++uniqueCounter,1997',11,'NOP',196),(197,0,'NOP (Nature Of Payment)','Royalty(F) Agreement Is Made on or After  June++uniqueCounter,2005',11,'NOP',197),(198,0,'NOP (Nature Of Payment)','Royalty(G) Agreement Is Made After March 31,1961 Before April++uniqueCounter,1976',11,'NOP',198),(199,0,'NOP (Nature Of Payment)','Royalty(G) Agreement Is Made After March 31,1976 Before June++uniqueCounter,1997',11,'NOP',199),(200,0,'NOP (Nature Of Payment)','Royalty(G) Agreement Is Made After March 31,1997 Before June++uniqueCounter,2005',11,'NOP',200),(201,0,'NOP (Nature Of Payment)','Royalty(G) Agreement Is Made on Or  After June++uniqueCounter,2005',11,'NOP',201),(202,0,'NOP (Nature Of Payment)','Short-Term Capital Gains U/s++uniqueCounter11A',11,'NOP',202),(203,0,'NOP (Nature Of Payment)','Winning From Horse Race',11,'NOP',203),(204,0,'NOP (Nature Of Payment)','Winning From Horse Lotteries and CrossWord Puzzles',11,'NOP',204),(205,0,'LTT (List of TCS Types)','Alcoholic Liquor For Human Consumption',9,'LTT',205),(206,0,'LTT (List of TCS Types)','Any Other Forest Produce (Not Being Tendu Leaves)',9,'LTT',206),(207,0,'LTT (List of TCS Types)','Contractors/Licensee/Leave Relating to Mine/Quarry',9,'LTT',207),(208,0,'LTT (List of TCS Types)','Contractors/Licensee/Leave Relating to Parking Lots',9,'LTT',208),(209,0,'LTT (List of TCS Types)','Contractors/Licensee/Leave Relating to Toll Plaza',9,'LTT',209),(210,0,'LTT (List of TCS Types)','Scrap',9,'LTT',210),(211,0,'LTT (List of TCS Types)','Tendu Leaves',9,'LTT',211),(212,0,'LTT (List of TCS Types)','Timber Obtained  by Any Mode Other Than Forest Lease',9,'LTT',212),(213,0,'LTT (List of TCS Types)','Timber Obtained  Under Forest Lease',9,'LTT',213),(214,0,'DT (Deductee Type)','Artificial Juridical Person',11,'DT',214),(215,0,'DT (Deductee Type)','Association Of Persons',11,'DT',215),(216,0,'DT (Deductee Type)','Body Of Individuals',11,'DT',216),(217,0,'DT (Deductee Type)','Company – Non Resident',11,'DT',217),(218,0,'DT (Deductee Type)','Company –  Resident',11,'DT',218),(219,0,'DT (Deductee Type)','Co- Operative Society',11,'DT',219),(220,0,'DT (Deductee Type)','Individual/HUF – Non Resident',11,'DT',220),(221,0,'DT (Deductee Type)','Individual/HUF – Resident',11,'DT',221),(222,0,'DT (Deductee Type)','Local Authority',11,'DT',222),(223,0,'DT (Deductee Type)','Partnership Firm',11,'DT',223),(224,0,'Classification','Exempt',10,'Classification',224),(225,0,'Classification','Export',10,'Classification',225),(226,0,'CT (Collectee Type)','Artificial Juridical Person',9,'CT',226),(227,0,'CT (Collectee Type)','Association Of Persons',9,'CT',227),(228,0,'CT (Collectee Type)','Body Of Individuals',9,'CT',228),(229,0,'CT (Collectee Type)','Company – Non Resident',9,'CT',229),(230,0,'CT (Collectee Type)','Company –  Resident',9,'CT',230),(231,0,'CT (Collectee Type)','Co- Operative Society',9,'CT',231),(232,0,'CT (Collectee Type)','Individual/HUF – Non Resident',9,'CT',232),(233,0,'CT (Collectee Type)','Individual/HUF – Resident',9,'CT',233),(234,0,'CT (Collectee Type)','Local Authority',9,'CT',234),(235,0,'CT (Collectee Type)','Partnership Firm',9,'CT',235),(236,0,'Vat/Tax class','Inter-State Sales',8,'VTC',236),(237,0,'Vat/Tax class','Inter State Sales Against Form-E1',8,'VTC',237),(238,0,'Vat/Tax class','Inter State Sales Against Form-E2',8,'VTC',238),(239,0,'Vat/Tax class','Inter State Sales at Lower Rate',8,'VTC',239),(240,0,'Vat/Tax class','Inter State Sales – Exempted',8,'VTC',240),(241,0,'Vat/Tax class','Inter State Sales – Tax Free',8,'VTC',241),(242,0,'Vat/Tax class','Input VAT @ 1%',7,'VTC',242),(243,0,'Vat/Tax class','Input VAT @ 12.5%',7,'VTC',243),(244,0,'Vat/Tax class','Input VAT @ 20%',7,'VTC',244),(245,0,'Vat/Tax class','Input VAT @ 4%',7,'VTC',245),(246,0,'Vat/Tax class','Input VAT @ 8% on Works Contract',7,'VTC',246),(247,0,'Vat/Tax class','Output VAT @ 1%',7,'VTC',247),(248,0,'Vat/Tax class','Output VAT @ 12.5%',7,'VTC',248),(249,0,'Vat/Tax class','Output VAT @ 20%',7,'VTC',249),(250,0,'Vat/Tax class','Output VAT @ 4%',7,'VTC',250),(251,0,'Vat/Tax class','Output VAT @ 8% on Works Contract',7,'VTC',251),(252,0,'Vat/Tax class','Purchase – Capital Goods @ 12.5%',7,'VTC',252),(253,0,'Vat/Tax class','Purchase – Capital Goods @ 4%',7,'VTC',253),(254,0,NULL,'On VAT Rate',7,NULL,254);

/*Table structure for table `account_group` */

DROP TABLE IF EXISTS `account_group`;

CREATE TABLE `account_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `alias` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) NOT NULL,
  `date_created` datetime NOT NULL,
  `does_is_affect_on_gross_profit` bit(1) NOT NULL,
  `is_display` bit(1) NOT NULL,
  `is_group_under_primary` bit(1) NOT NULL,
  `is_like_subgroup` bit(1) NOT NULL,
  `is_net_dr_cr_bal_for_report` bit(1) NOT NULL,
  `is_used_for_calculations` bit(1) NOT NULL,
  `last_updated` datetime NOT NULL,
  `last_updated_by_id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `property` varchar(255) DEFAULT NULL,
  `under_group_id` bigint(20) DEFAULT NULL,
  `unique_key` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_comiyshrwxm25hbhjejcso88y` (`company_id`),
  KEY `FK_s2mxvptr9efbaxw4ox4ffxvka` (`last_updated_by_id`),
  KEY `FK_ag725nyhu92s2009fwhrmcx3w` (`under_group_id`),
  CONSTRAINT `FK_ag725nyhu92s2009fwhrmcx3w` FOREIGN KEY (`under_group_id`) REFERENCES `account_group` (`id`),
  CONSTRAINT `FK_comiyshrwxm25hbhjejcso88y` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_s2mxvptr9efbaxw4ox4ffxvka` FOREIGN KEY (`last_updated_by_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=latin1;

/*Data for the table `account_group` */

insert  into `account_group`(`id`,`version`,`alias`,`company_id`,`date_created`,`does_is_affect_on_gross_profit`,`is_display`,`is_group_under_primary`,`is_like_subgroup`,`is_net_dr_cr_bal_for_report`,`is_used_for_calculations`,`last_updated`,`last_updated_by_id`,`name`,`property`,`under_group_id`,`unique_key`) values (1,0,NULL,1,'2015-05-15 19:13:16','\0','\0','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Primary','Primary',NULL,1),(2,0,NULL,1,'2015-05-15 19:13:16','\0','\0','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Assets','Assets',1,2),(3,0,NULL,1,'2015-05-15 19:13:16','\0','\0','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Liabilities','Liabilities',1,3),(4,0,NULL,1,'2015-05-15 19:13:16','\0','\0','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Expenses','Expenses',1,4),(5,0,NULL,1,'2015-05-15 19:13:16','\0','\0','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Income','Income',1,5),(6,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Current Assets','Current Assets',2,6),(7,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Fixed Assets','Fixed Assets',2,7),(8,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Investments','Investments',2,8),(9,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Misc. Expenses (Assets)','Misc. Expenses (Assets)',2,9),(10,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Loans (liability)','Loans (liability)',3,10),(11,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Branch/Divisions','Branch/Divisions',3,11),(12,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Capital Account','Capital Account',3,12),(13,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Current Liabilities','Current Liabilities',3,13),(14,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Suspense A/c','Suspense A/c',3,14),(15,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Direct Expenses','Direct Expenses',4,15),(16,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Indirect Expenses','Indirect Expenses',4,16),(17,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Purchase Accounts','Purchase Accounts',4,17),(18,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Direct Income','Direct Income',5,18),(19,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Indirect Income','Indirect Income',5,19),(20,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Sales Accounts','Sales Accounts',5,20),(21,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Bank Accounts','Bank Accounts',6,21),(22,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Cash-in-Hand','Cash-in-Hand',6,22),(23,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Deposits (Assets)','Deposits (Assets)',6,23),(24,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Stock-in-Hand','Stock-in-Hand',6,24),(25,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Loan and Advance (Assets)','Loan and Advance (Assets)',6,25),(26,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Sundry Debtors','Sundry Debtors',6,26),(27,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Unsecured Loans','Unsecured Loans',10,27),(28,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Bank OD A/c','Bank OD A/c',10,28),(29,0,'Retained Earnings',1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Reserves & Surplus','Reserves & Surplus',12,29),(30,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Secured Loans','Secured Loans',12,30),(31,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Provisions','Provisions',13,31),(32,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Sundry Creditors','Sundry Creditors',13,32),(33,0,NULL,1,'2015-05-15 19:13:16','\0','','\0','\0','\0','\0','2015-05-15 19:13:16',1,'Duties & Taxes','Duties & Taxes',13,33),(34,0,NULL,2,'2015-05-15 19:49:08','\0','\0','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Primary','Primary',NULL,34),(35,0,NULL,2,'2015-05-15 19:49:08','\0','\0','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Assets','Assets',34,35),(36,0,NULL,2,'2015-05-15 19:49:08','\0','\0','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Liabilities','Liabilities',34,36),(37,0,NULL,2,'2015-05-15 19:49:08','\0','\0','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Expenses','Expenses',34,37),(38,0,NULL,2,'2015-05-15 19:49:08','\0','\0','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Income','Income',34,38),(39,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Current Assets','Current Assets',35,39),(40,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Fixed Assets','Fixed Assets',35,40),(41,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Investments','Investments',35,41),(42,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Misc. Expenses (Assets)','Misc. Expenses (Assets)',35,42),(43,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Loans (liability)','Loans (liability)',36,43),(44,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Branch/Divisions','Branch/Divisions',36,44),(45,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Capital Account','Capital Account',36,45),(46,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Current Liabilities','Current Liabilities',36,46),(47,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Suspense A/c','Suspense A/c',36,47),(48,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Direct Expenses','Direct Expenses',37,48),(49,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Indirect Expenses','Indirect Expenses',37,49),(50,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Purchase Accounts','Purchase Accounts',37,50),(51,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Direct Income','Direct Income',38,51),(52,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Indirect Income','Indirect Income',38,52),(53,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Sales Accounts','Sales Accounts',38,53),(54,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Bank Accounts','Bank Accounts',39,54),(55,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Cash-in-Hand','Cash-in-Hand',39,55),(56,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Deposits (Assets)','Deposits (Assets)',39,56),(57,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Stock-in-Hand','Stock-in-Hand',39,57),(58,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Loan and Advance (Assets)','Loan and Advance (Assets)',39,58),(59,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Sundry Debtors','Sundry Debtors',39,59),(60,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Unsecured Loans','Unsecured Loans',43,60),(61,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Bank OD A/c','Bank OD A/c',43,61),(62,0,'Retained Earnings',2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Reserves & Surplus','Reserves & Surplus',45,62),(63,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Secured Loans','Secured Loans',45,63),(64,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Provisions','Provisions',46,64),(65,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Sundry Creditors','Sundry Creditors',46,65),(66,0,NULL,2,'2015-05-15 19:49:08','\0','','\0','\0','\0','\0','2015-05-15 19:49:08',1,'Duties & Taxes','Duties & Taxes',46,66),(67,0,NULL,3,'2015-05-31 12:47:26','\0','\0','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Primary','Primary',NULL,67),(68,0,NULL,3,'2015-05-31 12:47:26','\0','\0','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Assets','Assets',67,68),(69,0,NULL,3,'2015-05-31 12:47:26','\0','\0','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Liabilities','Liabilities',67,69),(70,0,NULL,3,'2015-05-31 12:47:26','\0','\0','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Expenses','Expenses',67,70),(71,0,NULL,3,'2015-05-31 12:47:26','\0','\0','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Income','Income',67,71),(72,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Current Assets','Current Assets',68,72),(73,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Fixed Assets','Fixed Assets',68,73),(74,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Investments','Investments',68,74),(75,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Misc. Expenses (Assets)','Misc. Expenses (Assets)',68,75),(76,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Loans (liability)','Loans (liability)',69,76),(77,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Branch/Divisions','Branch/Divisions',69,77),(78,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Capital Account','Capital Account',69,78),(79,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Current Liabilities','Current Liabilities',69,79),(80,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Suspense A/c','Suspense A/c',69,80),(81,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Direct Expenses','Direct Expenses',70,81),(82,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Indirect Expenses','Indirect Expenses',70,82),(83,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Purchase Accounts','Purchase Accounts',70,83),(84,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Direct Income','Direct Income',71,84),(85,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Indirect Income','Indirect Income',71,85),(86,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Sales Accounts','Sales Accounts',71,86),(87,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Bank Accounts','Bank Accounts',72,87),(88,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Cash-in-Hand','Cash-in-Hand',72,88),(89,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Deposits (Assets)','Deposits (Assets)',72,89),(90,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Stock-in-Hand','Stock-in-Hand',72,90),(91,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Loan and Advance (Assets)','Loan and Advance (Assets)',72,91),(92,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Sundry Debtors','Sundry Debtors',72,92),(93,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Unsecured Loans','Unsecured Loans',76,93),(94,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Bank OD A/c','Bank OD A/c',76,94),(95,0,'Retained Earnings',3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Reserves & Surplus','Reserves & Surplus',78,95),(96,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Secured Loans','Secured Loans',78,96),(97,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Provisions','Provisions',79,97),(98,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Sundry Creditors','Sundry Creditors',79,98),(99,0,NULL,3,'2015-05-31 12:47:26','\0','','\0','\0','\0','\0','2015-05-31 12:47:26',2,'Duties & Taxes','Duties & Taxes',79,99),(100,0,NULL,4,'2015-05-31 13:04:45','\0','\0','\0','\0','\0','\0','2015-05-31 13:04:45',1,'Primary','Primary',NULL,100),(101,0,NULL,4,'2015-05-31 13:04:45','\0','\0','\0','\0','\0','\0','2015-05-31 13:04:45',1,'Assets','Assets',100,101),(102,0,NULL,4,'2015-05-31 13:04:45','\0','\0','\0','\0','\0','\0','2015-05-31 13:04:45',1,'Liabilities','Liabilities',100,102),(103,0,NULL,4,'2015-05-31 13:04:45','\0','\0','\0','\0','\0','\0','2015-05-31 13:04:45',1,'Expenses','Expenses',100,103),(104,0,NULL,4,'2015-05-31 13:04:45','\0','\0','\0','\0','\0','\0','2015-05-31 13:04:45',1,'Income','Income',100,104),(105,0,NULL,4,'2015-05-31 13:04:45','\0','','\0','\0','\0','\0','2015-05-31 13:04:45',1,'Current Assets','Current Assets',101,105),(106,0,NULL,4,'2015-05-31 13:04:45','\0','','\0','\0','\0','\0','2015-05-31 13:04:45',1,'Fixed Assets','Fixed Assets',101,106),(107,0,NULL,4,'2015-05-31 13:04:45','\0','','\0','\0','\0','\0','2015-05-31 13:04:45',1,'Investments','Investments',101,107),(108,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Misc. Expenses (Assets)','Misc. Expenses (Assets)',101,108),(109,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Loans (liability)','Loans (liability)',102,109),(110,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Branch/Divisions','Branch/Divisions',102,110),(111,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Capital Account','Capital Account',102,111),(112,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Current Liabilities','Current Liabilities',102,112),(113,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Suspense A/c','Suspense A/c',102,113),(114,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Direct Expenses','Direct Expenses',103,114),(115,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Indirect Expenses','Indirect Expenses',103,115),(116,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Purchase Accounts','Purchase Accounts',103,116),(117,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Direct Income','Direct Income',104,117),(118,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Indirect Income','Indirect Income',104,118),(119,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Sales Accounts','Sales Accounts',104,119),(120,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Bank Accounts','Bank Accounts',105,120),(121,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Cash-in-Hand','Cash-in-Hand',105,121),(122,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Deposits (Assets)','Deposits (Assets)',105,122),(123,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Stock-in-Hand','Stock-in-Hand',105,123),(124,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Loan and Advance (Assets)','Loan and Advance (Assets)',105,124),(125,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Sundry Debtors','Sundry Debtors',105,125),(126,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Unsecured Loans','Unsecured Loans',109,126),(127,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Bank OD A/c','Bank OD A/c',109,127),(128,0,'Retained Earnings',4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Reserves & Surplus','Reserves & Surplus',111,128),(129,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Secured Loans','Secured Loans',111,129),(130,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Provisions','Provisions',112,130),(131,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Sundry Creditors','Sundry Creditors',112,131),(132,0,NULL,4,'2015-05-31 13:04:46','\0','','\0','\0','\0','\0','2015-05-31 13:04:46',1,'Duties & Taxes','Duties & Taxes',112,132),(133,0,NULL,3,'2015-05-31 13:18:48','\0','','\0','\0','\0','\0','2015-05-31 13:18:48',3,'VAT','Duties & Taxes',99,133),(134,0,NULL,3,'2015-05-31 13:19:30','\0','','\0','\0','\0','\0','2015-05-31 13:19:30',3,'EXCISE','Duties & Taxes',99,134),(135,0,NULL,5,'2015-06-13 15:27:44','\0','\0','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Primary','Primary',NULL,135),(136,0,NULL,5,'2015-06-13 15:27:44','\0','\0','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Assets','Assets',135,136),(137,0,NULL,5,'2015-06-13 15:27:44','\0','\0','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Liabilities','Liabilities',135,137),(138,0,NULL,5,'2015-06-13 15:27:44','\0','\0','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Expenses','Expenses',135,138),(139,0,NULL,5,'2015-06-13 15:27:44','\0','\0','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Income','Income',135,139),(140,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Current Assets','Current Assets',136,140),(141,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Fixed Assets','Fixed Assets',136,141),(142,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Investments','Investments',136,142),(143,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Misc. Expenses (Assets)','Misc. Expenses (Assets)',136,143),(144,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Loans (liability)','Loans (liability)',137,144),(145,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Branch/Divisions','Branch/Divisions',137,145),(146,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Capital Account','Capital Account',137,146),(147,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Current Liabilities','Current Liabilities',137,147),(148,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Suspense A/c','Suspense A/c',137,148),(149,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Direct Expenses','Direct Expenses',138,149),(150,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Indirect Expenses','Indirect Expenses',138,150),(151,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Purchase Accounts','Purchase Accounts',138,151),(152,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Direct Income','Direct Income',139,152),(153,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Indirect Income','Indirect Income',139,153),(154,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Sales Accounts','Sales Accounts',139,154),(155,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Bank Accounts','Bank Accounts',140,155),(156,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Cash-in-Hand','Cash-in-Hand',140,156),(157,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Deposits (Assets)','Deposits (Assets)',140,157),(158,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Stock-in-Hand','Stock-in-Hand',140,158),(159,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Loan and Advance (Assets)','Loan and Advance (Assets)',140,159),(160,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Sundry Debtors','Sundry Debtors',140,160),(161,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Unsecured Loans','Unsecured Loans',144,161),(162,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Bank OD A/c','Bank OD A/c',144,162),(163,0,'Retained Earnings',5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Reserves & Surplus','Reserves & Surplus',146,163),(164,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Secured Loans','Secured Loans',146,164),(165,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Provisions','Provisions',147,165),(166,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Sundry Creditors','Sundry Creditors',147,166),(167,0,NULL,5,'2015-06-13 15:27:44','\0','','\0','\0','\0','\0','2015-06-13 15:27:44',1,'Duties & Taxes','Duties & Taxes',147,167),(168,0,NULL,5,'2015-06-13 17:31:16','\0','','\0','\0','\0','\0','2015-06-13 17:31:16',4,'vat','Duties & Taxes',167,168),(169,0,NULL,6,'2015-06-13 17:52:39','\0','\0','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Primary','Primary',NULL,169),(170,0,NULL,6,'2015-06-13 17:52:39','\0','\0','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Assets','Assets',169,170),(171,0,NULL,6,'2015-06-13 17:52:39','\0','\0','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Liabilities','Liabilities',169,171),(172,0,NULL,6,'2015-06-13 17:52:39','\0','\0','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Expenses','Expenses',169,172),(173,0,NULL,6,'2015-06-13 17:52:39','\0','\0','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Income','Income',169,173),(174,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Current Assets','Current Assets',170,174),(175,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Fixed Assets','Fixed Assets',170,175),(176,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Investments','Investments',170,176),(177,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Misc. Expenses (Assets)','Misc. Expenses (Assets)',170,177),(178,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Loans (liability)','Loans (liability)',171,178),(179,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Branch/Divisions','Branch/Divisions',171,179),(180,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Capital Account','Capital Account',171,180),(181,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Current Liabilities','Current Liabilities',171,181),(182,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Suspense A/c','Suspense A/c',171,182),(183,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Direct Expenses','Direct Expenses',172,183),(184,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Indirect Expenses','Indirect Expenses',172,184),(185,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Purchase Accounts','Purchase Accounts',172,185),(186,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Direct Income','Direct Income',173,186),(187,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Indirect Income','Indirect Income',173,187),(188,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Sales Accounts','Sales Accounts',173,188),(189,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Bank Accounts','Bank Accounts',174,189),(190,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Cash-in-Hand','Cash-in-Hand',174,190),(191,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Deposits (Assets)','Deposits (Assets)',174,191),(192,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Stock-in-Hand','Stock-in-Hand',174,192),(193,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Loan and Advance (Assets)','Loan and Advance (Assets)',174,193),(194,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Sundry Debtors','Sundry Debtors',174,194),(195,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Unsecured Loans','Unsecured Loans',178,195),(196,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Bank OD A/c','Bank OD A/c',178,196),(197,0,'Retained Earnings',6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Reserves & Surplus','Reserves & Surplus',180,197),(198,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Secured Loans','Secured Loans',180,198),(199,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Provisions','Provisions',181,199),(200,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Sundry Creditors','Sundry Creditors',181,200),(201,0,NULL,6,'2015-06-13 17:52:39','\0','','\0','\0','\0','\0','2015-06-13 17:52:39',4,'Duties & Taxes','Duties & Taxes',181,201),(202,0,NULL,6,'2015-06-13 17:55:09','\0','','\0','\0','\0','\0','2015-06-13 17:55:09',5,'vat','Duties & Taxes',201,202);

/*Table structure for table `account_ledger` */

DROP TABLE IF EXISTS `account_ledger`;

CREATE TABLE `account_ledger` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `account_name` varchar(255) DEFAULT NULL,
  `account_no` varchar(255) DEFAULT NULL,
  `active_interest_calculation` bit(1) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `alias` varchar(255) DEFAULT NULL,
  `branch_name` varchar(255) DEFAULT NULL,
  `bsr_code` varchar(255) DEFAULT NULL,
  `buyer_lessee_id` bigint(20) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `company_id` bigint(20) NOT NULL,
  `contact_person` varchar(255) DEFAULT NULL,
  `credit_days` int(11) NOT NULL,
  `cst_no` varchar(255) DEFAULT NULL,
  `date_created` datetime NOT NULL,
  `deductee_type_id` bigint(20) DEFAULT NULL,
  `duty_head_id` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fax_no` varchar(255) DEFAULT NULL,
  `ifsc_code` varchar(255) DEFAULT NULL,
  `ignore_surcharge_exception_limit` bit(1) NOT NULL,
  `is_abatement` bit(1) NOT NULL,
  `is_service_tax` bit(1) NOT NULL,
  `is_tcs_applicable` bit(1) NOT NULL,
  `is_tds_deductee` bit(1) NOT NULL,
  `is_vat` bit(1) NOT NULL,
  `last_updated` datetime NOT NULL,
  `last_updated_by_id` bigint(20) NOT NULL,
  `mailing_name` varchar(255) DEFAULT NULL,
  `maintain_bill` bit(1) NOT NULL,
  `method_of_calculation_id` bigint(20) DEFAULT NULL,
  `mobile_no` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `no_collection_applicable` bit(1) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `notification_no` varchar(255) DEFAULT NULL,
  `opening_balance` decimal(19,2) NOT NULL,
  `pan_no` varchar(255) DEFAULT NULL,
  `percentage` int(11) NOT NULL,
  `percentage_of_calculation` int(11) NOT NULL,
  `pin_code` varchar(255) DEFAULT NULL,
  `provide_bank_details` bit(1) NOT NULL,
  `reconciliation_date` date DEFAULT NULL,
  `round_method_id` bigint(20) DEFAULT NULL,
  `sales_tax_no` varchar(255) DEFAULT NULL,
  `section_number` varchar(255) DEFAULT NULL,
  `state_id` bigint(20) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `tax_class_id` bigint(20) DEFAULT NULL,
  `tcs_lower_rate` int(11) NOT NULL,
  `telephone_no` varchar(255) DEFAULT NULL,
  `type_of_duty_id` bigint(20) DEFAULT NULL,
  `under_group_id` bigint(20) NOT NULL,
  `unique_key` int(11) NOT NULL,
  `use_primary_group` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_62qjx5y362qywwtrarmttn8ji` (`buyer_lessee_id`),
  KEY `FK_9wcwgvvqbrk4nclqthfe1ooh8` (`category_id`),
  KEY `FK_pxx437f0ikda0tvkcrdxusiia` (`company_id`),
  KEY `FK_nso6oq158npsn9lbjg2wi6ecc` (`deductee_type_id`),
  KEY `FK_509633nkv43349t2uh17e0rdi` (`duty_head_id`),
  KEY `FK_e5muc3sxoiaipg1mtuybx2pus` (`last_updated_by_id`),
  KEY `FK_jordsj8utp1f22k2gpbh10d50` (`method_of_calculation_id`),
  KEY `FK_dx7q797n2od14kncsrtf5bbcn` (`round_method_id`),
  KEY `FK_964ffr3rookkasxfe3ig3dv5o` (`state_id`),
  KEY `FK_ct1m7qa7urrfoy5ojyeid3plr` (`tax_class_id`),
  KEY `FK_ehi8kavtjp7suc15bircefs7l` (`type_of_duty_id`),
  KEY `FK_gkha0pegm3mcchx4wctpx6qnk` (`under_group_id`),
  CONSTRAINT `FK_509633nkv43349t2uh17e0rdi` FOREIGN KEY (`duty_head_id`) REFERENCES `account_flag` (`id`),
  CONSTRAINT `FK_62qjx5y362qywwtrarmttn8ji` FOREIGN KEY (`buyer_lessee_id`) REFERENCES `account_flag` (`id`),
  CONSTRAINT `FK_964ffr3rookkasxfe3ig3dv5o` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`),
  CONSTRAINT `FK_9wcwgvvqbrk4nclqthfe1ooh8` FOREIGN KEY (`category_id`) REFERENCES `account_flag` (`id`),
  CONSTRAINT `FK_ct1m7qa7urrfoy5ojyeid3plr` FOREIGN KEY (`tax_class_id`) REFERENCES `account_flag` (`id`),
  CONSTRAINT `FK_dx7q797n2od14kncsrtf5bbcn` FOREIGN KEY (`round_method_id`) REFERENCES `account_flag` (`id`),
  CONSTRAINT `FK_e5muc3sxoiaipg1mtuybx2pus` FOREIGN KEY (`last_updated_by_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_ehi8kavtjp7suc15bircefs7l` FOREIGN KEY (`type_of_duty_id`) REFERENCES `account_flag` (`id`),
  CONSTRAINT `FK_gkha0pegm3mcchx4wctpx6qnk` FOREIGN KEY (`under_group_id`) REFERENCES `account_group` (`id`),
  CONSTRAINT `FK_jordsj8utp1f22k2gpbh10d50` FOREIGN KEY (`method_of_calculation_id`) REFERENCES `account_flag` (`id`),
  CONSTRAINT `FK_nso6oq158npsn9lbjg2wi6ecc` FOREIGN KEY (`deductee_type_id`) REFERENCES `account_flag` (`id`),
  CONSTRAINT `FK_pxx437f0ikda0tvkcrdxusiia` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=latin1;

/*Data for the table `account_ledger` */

insert  into `account_ledger`(`id`,`version`,`account_name`,`account_no`,`active_interest_calculation`,`address`,`alias`,`branch_name`,`bsr_code`,`buyer_lessee_id`,`category_id`,`company_id`,`contact_person`,`credit_days`,`cst_no`,`date_created`,`deductee_type_id`,`duty_head_id`,`email`,`fax_no`,`ifsc_code`,`ignore_surcharge_exception_limit`,`is_abatement`,`is_service_tax`,`is_tcs_applicable`,`is_tds_deductee`,`is_vat`,`last_updated`,`last_updated_by_id`,`mailing_name`,`maintain_bill`,`method_of_calculation_id`,`mobile_no`,`name`,`no_collection_applicable`,`note`,`notification_no`,`opening_balance`,`pan_no`,`percentage`,`percentage_of_calculation`,`pin_code`,`provide_bank_details`,`reconciliation_date`,`round_method_id`,`sales_tax_no`,`section_number`,`state_id`,`status`,`tax_class_id`,`tcs_lower_rate`,`telephone_no`,`type_of_duty_id`,`under_group_id`,`unique_key`,`use_primary_group`) values (1,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,0,NULL,'2015-05-15 19:13:16',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-15 19:13:16',1,NULL,'\0',NULL,NULL,'Cash','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,NULL,NULL,'Dr',NULL,0,NULL,NULL,22,1,'\0'),(2,1,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,0,NULL,'2015-05-15 19:13:16',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-15 19:41:39',1,NULL,'\0',NULL,NULL,'Profit & Loss A/c','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,NULL,NULL,'Dr',NULL,0,NULL,NULL,1,2,''),(3,1,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,0,NULL,'2015-05-15 19:49:08',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-16 18:25:28',1,NULL,'\0',NULL,NULL,'Cash','\0',NULL,NULL,'25000.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,NULL,NULL,'Dr',NULL,0,NULL,NULL,55,3,'\0'),(4,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,0,NULL,'2015-05-15 19:49:08',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-15 19:49:08',1,NULL,'\0',NULL,NULL,'Profit & Loss A/c','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,NULL,NULL,'Dr',NULL,0,NULL,NULL,1,4,'\0'),(5,0,NULL,NULL,'\0',NULL,'d',NULL,NULL,NULL,NULL,2,NULL,0,NULL,'2015-05-16 18:07:47',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-16 18:07:47',2,NULL,'\0',NULL,NULL,'Dabtors  a/c','\0',NULL,NULL,'10000.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,59,5,'\0'),(6,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,0,NULL,'2015-05-16 18:08:45',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-16 18:08:45',2,NULL,'\0',NULL,NULL,'Creditors a/c','\0',NULL,NULL,'10000.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Cr',NULL,0,NULL,NULL,65,6,'\0'),(7,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,0,NULL,'2015-05-16 18:09:24',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-16 18:09:24',2,NULL,'\0',NULL,NULL,'sales','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,53,7,'\0'),(8,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,0,NULL,'2015-05-16 18:10:01',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-16 18:10:01',2,NULL,'\0',NULL,NULL,'Purchase','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,64,8,'\0'),(9,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,0,NULL,'2015-05-16 18:12:26',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-16 18:12:26',2,NULL,'\0',NULL,NULL,'salary','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,49,9,'\0'),(10,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,0,NULL,'2015-05-16 18:12:47',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-16 18:12:47',2,NULL,'\0',NULL,NULL,'Rent paid','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,49,10,'\0'),(11,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,0,NULL,'2015-05-16 18:13:47',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-16 18:13:47',2,NULL,'\0',NULL,NULL,'sales','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,53,11,'\0'),(12,1,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,0,NULL,'2015-05-16 18:14:11',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-16 18:20:02',2,NULL,'\0',NULL,NULL,'Printing and stationary','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,49,12,'\0'),(13,1,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,0,NULL,'2015-05-16 18:14:47',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-16 18:21:08',2,NULL,'\0',NULL,NULL,'Interest Received','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,52,13,'\0'),(14,3,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,0,NULL,'2015-05-16 18:16:33',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-16 18:24:50',2,NULL,'\0',NULL,NULL,'Union bank of india C/C','\0',NULL,NULL,'2500000.00',NULL,0,0,NULL,'\0','2015-05-16',NULL,NULL,'206C',NULL,'Cr',NULL,0,NULL,NULL,61,14,'\0'),(15,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,0,NULL,'2015-05-16 18:23:16',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-16 18:23:16',2,NULL,'\0',NULL,NULL,'Union Bank Current Account','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0','2015-05-16',NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,54,15,'\0'),(16,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,0,NULL,'2015-05-31 12:40:31',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 12:40:31',2,NULL,'\0',NULL,NULL,'dabtor 2','\0',NULL,NULL,'50000.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,59,16,'\0'),(17,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,0,NULL,'2015-05-31 12:41:38',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 12:41:38',2,NULL,'\0',NULL,NULL,'creditors 2','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,65,17,'\0'),(18,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,0,NULL,'2015-05-31 12:45:02',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 12:45:02',2,NULL,'\0',NULL,NULL,'purchase 2','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,50,18,'\0'),(19,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,0,NULL,'2015-05-31 12:47:27',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 12:47:27',2,NULL,'\0',NULL,NULL,'Cash','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,NULL,NULL,'Dr',NULL,0,NULL,NULL,22,19,'\0'),(20,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,0,NULL,'2015-05-31 12:47:27',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 12:47:27',2,NULL,'\0',NULL,NULL,'Profit & Loss A/c','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,NULL,NULL,'Dr',NULL,0,NULL,NULL,1,20,'\0'),(21,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,4,NULL,0,NULL,'2015-05-31 13:04:46',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 13:04:46',1,NULL,'\0',NULL,NULL,'Cash','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,NULL,NULL,'Dr',NULL,0,NULL,NULL,22,21,'\0'),(22,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,4,NULL,0,NULL,'2015-05-31 13:04:46',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 13:04:46',1,NULL,'\0',NULL,NULL,'Profit & Loss A/c','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,NULL,NULL,'Dr',NULL,0,NULL,NULL,1,22,'\0'),(23,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,0,NULL,'2015-05-31 13:17:32',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 13:17:32',3,NULL,'\0',NULL,NULL,'PURCHASE A/C','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,83,23,'\0'),(24,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,0,NULL,'2015-05-31 13:17:55',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 13:17:55',3,NULL,'\0',NULL,NULL,'SALES','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,86,24,'\0'),(25,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,0,NULL,'2015-05-31 13:20:17',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 13:20:17',3,NULL,'\0',NULL,NULL,'INPUT VAT 5','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,6,133,25,'\0'),(26,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,0,NULL,'2015-05-31 13:20:45',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 13:20:45',3,NULL,'\0',NULL,NULL,'OUTPUT VAT 5','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,6,133,26,'\0'),(27,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,0,NULL,'2015-05-31 13:21:29',NULL,33,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 13:21:29',3,NULL,'\0',NULL,NULL,'BASIC EXCISE DUTY SALES','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,5,134,27,'\0'),(28,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,0,NULL,'2015-05-31 13:22:22',NULL,33,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 13:22:22',3,NULL,'\0',NULL,NULL,'EXCISE DUTY OF PURCHASE','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,5,134,28,'\0'),(29,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,0,NULL,'2015-05-31 13:23:41',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 13:23:41',3,NULL,'\0',NULL,NULL,'X DABTORS ACCOUNT','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,92,29,'\0'),(30,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,0,NULL,'2015-05-31 13:24:33',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 13:24:33',3,NULL,'\0',NULL,NULL,'C  CREDITORS','\0',NULL,NULL,'50000.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Cr',NULL,0,NULL,NULL,98,30,'\0'),(31,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,0,NULL,'2015-05-31 13:32:31',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 13:32:31',3,NULL,'\0',NULL,NULL,'FREIGHT OUTWARD','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,81,31,'\0'),(32,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,0,NULL,'2015-05-31 13:32:56',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 13:32:56',3,NULL,'\0',NULL,NULL,'FREIGHT INWARD','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,83,32,'\0'),(33,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,0,NULL,'2015-05-31 13:32:56',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 13:32:56',3,NULL,'\0',NULL,NULL,'FREIGHT INWARD','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,83,33,'\0'),(34,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,0,NULL,'2015-05-31 13:33:31',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 13:33:31',3,NULL,'\0',NULL,NULL,'RENT PAID','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,82,34,'\0'),(35,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,0,NULL,'2015-05-31 13:33:59',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 13:33:59',3,NULL,'\0',NULL,NULL,'SALARY PAID','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,82,35,'\0'),(36,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,0,NULL,'2015-05-31 13:34:41',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 13:34:41',3,NULL,'\0',NULL,NULL,'INTEREST PAID','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,82,36,'\0'),(37,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,0,NULL,'2015-05-31 13:35:15',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-05-31 13:35:15',3,NULL,'\0',NULL,NULL,'INTEREST RECEIVED','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,85,37,'\0'),(38,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,5,NULL,0,NULL,'2015-06-13 15:27:44',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 15:27:44',1,NULL,'\0',NULL,NULL,'Cash','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,NULL,NULL,'Dr',NULL,0,NULL,NULL,22,38,'\0'),(39,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,5,NULL,0,NULL,'2015-06-13 15:27:44',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 15:27:44',1,NULL,'\0',NULL,NULL,'Profit & Loss A/c','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,NULL,NULL,'Dr',NULL,0,NULL,NULL,1,39,'\0'),(40,2,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,5,NULL,0,NULL,'2015-06-13 15:40:31',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 15:43:15',4,NULL,'\0',NULL,NULL,'machinery','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,141,40,'\0'),(41,1,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,5,NULL,0,NULL,'2015-06-13 15:45:51',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 16:05:26',4,NULL,'\0',NULL,NULL,'purchase 5%','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,151,41,'\0'),(42,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,5,NULL,0,NULL,'2015-06-13 15:46:16',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 15:46:16',4,NULL,'\0',NULL,NULL,'sales 5%','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,154,42,'\0'),(43,1,NULL,NULL,'\0',NULL,'5',NULL,NULL,NULL,NULL,5,NULL,0,NULL,'2015-06-13 15:47:47',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 17:31:56',4,NULL,'\0',51,NULL,'Purchase Vat 5%','\0',NULL,NULL,'0.00',NULL,0,5,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,6,168,43,'\0'),(44,1,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,5,NULL,0,NULL,'2015-06-13 15:48:41',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 17:32:16',4,NULL,'\0',51,NULL,'Sales Vat 5%','\0',NULL,NULL,'0.00',NULL,0,5,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,6,168,44,'\0'),(45,2,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,5,NULL,0,'27770409689V','2015-06-13 15:49:13',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 16:03:06',4,NULL,'\0',NULL,NULL,'rajesh creditors','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,'27770409689V','206C',NULL,'Dr',NULL,0,NULL,NULL,166,45,'\0'),(46,2,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,5,NULL,0,'2789304089V','2015-06-13 15:50:01',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 16:03:27',4,NULL,'\0',NULL,NULL,'Mahesh debtors','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,'2789304089V','206C',NULL,'Dr',NULL,0,NULL,NULL,160,46,'\0'),(47,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,5,NULL,0,NULL,'2015-06-13 15:57:57',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 15:57:57',4,NULL,'\0',NULL,NULL,'SBI','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0','2015-06-01',NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,155,47,'\0'),(48,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,5,NULL,0,NULL,'2015-06-13 15:59:04',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 15:59:04',4,NULL,'\0',NULL,NULL,'Praful','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,166,48,'\0'),(49,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,5,NULL,0,NULL,'2015-06-13 16:00:28',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 16:00:28',4,NULL,'\0',50,NULL,'Tds on Commission','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,6,167,49,'\0'),(50,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,5,NULL,0,NULL,'2015-06-13 16:18:46',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 16:18:46',4,NULL,'\0',NULL,NULL,'commission','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,150,50,'\0'),(51,2,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,5,NULL,0,NULL,'2015-06-13 17:11:55',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 17:13:49',4,NULL,'\0',NULL,NULL,'Vat Payable','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,6,165,51,'\0'),(52,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,0,NULL,'2015-06-13 17:52:39',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 17:52:39',4,NULL,'\0',NULL,NULL,'Cash','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,NULL,NULL,'Dr',NULL,0,NULL,NULL,22,52,'\0'),(53,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,0,NULL,'2015-06-13 17:52:39',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 17:52:39',4,NULL,'\0',NULL,NULL,'Profit & Loss A/c','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,NULL,NULL,'Dr',NULL,0,NULL,NULL,1,53,'\0'),(54,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,0,NULL,'2015-06-13 18:02:27',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 18:02:27',5,NULL,'\0',NULL,NULL,'p','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,180,54,'\0'),(55,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,0,NULL,'2015-06-13 18:02:55',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 18:02:55',5,NULL,'\0',NULL,NULL,'machinery','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,175,55,'\0'),(56,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,0,NULL,'2015-06-13 18:03:50',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 18:03:50',5,NULL,'\0',NULL,NULL,'sbi','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0','2015-04-01',NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,189,56,'\0'),(57,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,0,NULL,'2015-06-13 18:04:31',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 18:04:31',5,NULL,'\0',NULL,NULL,'Purchase 5%','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,185,57,'\0'),(58,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,0,NULL,'2015-06-13 18:05:06',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 18:05:06',5,NULL,'\0',NULL,NULL,'Purchase VAt 5%','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,6,202,58,'\0'),(59,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,0,NULL,'2015-06-13 18:05:26',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 18:05:26',5,NULL,'\0',NULL,NULL,'x','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,200,59,'\0'),(60,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,0,NULL,'2015-06-13 18:05:45',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 18:05:45',5,NULL,'\0',NULL,NULL,'y','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,194,60,'\0'),(61,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,0,NULL,'2015-06-13 18:06:10',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 18:06:10',5,NULL,'\0',NULL,NULL,'Sales 5%','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,188,61,'\0'),(62,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,0,NULL,'2015-06-13 18:06:45',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 18:06:45',5,NULL,'\0',NULL,NULL,'Sales Vat 5%','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,6,202,62,'\0'),(63,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,0,NULL,'2015-06-13 18:07:42',NULL,33,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 18:07:42',5,NULL,'\0',42,NULL,'E.Duty On Purchase','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,5,201,63,'\0'),(64,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,0,NULL,'2015-06-13 18:08:21',NULL,33,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 18:08:21',5,NULL,'\0',NULL,NULL,'E. DUTY ON SALES','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,5,201,64,'\0'),(65,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,0,NULL,'2015-06-13 18:09:02',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 18:09:02',5,NULL,'\0',NULL,NULL,'Commission','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,184,65,'\0'),(66,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,0,NULL,'2015-06-13 18:09:39',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 18:09:39',5,NULL,'\0',NULL,NULL,'z commision','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,200,66,'\0'),(67,0,NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,0,NULL,'2015-06-13 18:10:49',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0','\0','2015-06-13 18:10:49',5,NULL,'\0',NULL,NULL,'Vat Payable','\0',NULL,NULL,'0.00',NULL,0,0,NULL,'\0',NULL,NULL,NULL,'206C',NULL,'Dr',NULL,0,NULL,NULL,199,67,'\0');

/*Table structure for table `company` */

DROP TABLE IF EXISTS `company`;

CREATE TABLE `company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `books_beginig_from` date NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `country_id` bigint(20) DEFAULT NULL,
  `currency_symbol` varchar(255) DEFAULT NULL,
  `date_created` datetime NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `financial_from` date NOT NULL,
  `is_company_split` bit(1) NOT NULL,
  `is_manual` bit(1) NOT NULL,
  `is_symbols_suffix` bit(1) NOT NULL,
  `last_updated` datetime NOT NULL,
  `last_updated_by_id` bigint(20) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `number_for_decimal_place` int(11) NOT NULL,
  `organization_id` bigint(20) NOT NULL,
  `pincode` varchar(255) DEFAULT NULL,
  `registration_no` varchar(255) DEFAULT NULL,
  `state_id` bigint(20) DEFAULT NULL,
  `telephone_no` varchar(255) DEFAULT NULL,
  `unique_key` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_q79ooswnnmx4kafhpltwr6441` (`company_id`),
  KEY `FK_6375rhh2tyjm32i7i1r8lb4ew` (`country_id`),
  KEY `FK_6cija92gkusb5j35xasedkg1a` (`last_updated_by_id`),
  KEY `FK_g408c4qv1jgvqkt97k6cyw16i` (`organization_id`),
  KEY `FK_q4or9131cc2fuul96k1j42kx1` (`state_id`),
  CONSTRAINT `FK_6375rhh2tyjm32i7i1r8lb4ew` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FK_6cija92gkusb5j35xasedkg1a` FOREIGN KEY (`last_updated_by_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_g408c4qv1jgvqkt97k6cyw16i` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`),
  CONSTRAINT `FK_q4or9131cc2fuul96k1j42kx1` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`),
  CONSTRAINT `FK_q79ooswnnmx4kafhpltwr6441` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `company` */

insert  into `company`(`id`,`version`,`address`,`books_beginig_from`,`city`,`company_id`,`country_id`,`currency_symbol`,`date_created`,`email`,`financial_from`,`is_company_split`,`is_manual`,`is_symbols_suffix`,`last_updated`,`last_updated_by_id`,`name`,`number_for_decimal_place`,`organization_id`,`pincode`,`registration_no`,`state_id`,`telephone_no`,`unique_key`) values (1,0,NULL,'2015-04-01',NULL,NULL,NULL,NULL,'2015-05-15 19:13:15',NULL,'2015-04-01','\0','\0','\0','2015-05-15 19:13:15',1,'Yogi Company',0,1,NULL,NULL,NULL,NULL,1),(2,0,'Moshi,Pune','2015-04-01','Pune',NULL,1,NULL,'2015-05-15 19:49:08','sale@yogisoftech.in','2015-04-01','\0','','\0','2015-05-15 19:49:08',1,'Yogi System Pvt. Ltd',0,2,NULL,'U29210PN2009PTC134243',21,'9090909090',2),(3,0,'HOUSE NO 987 GAT NO 61 ,PARDESHI VASTI ,A/P CHIMBLI TAL -KHED ,DIST -PUNE','2015-04-01','PUNE',NULL,1,'RS','2015-05-31 12:47:26',NULL,'2015-04-01','\0','','\0','2015-05-31 12:47:26',2,'YOGI WIRES PVT LTD DEMO',0,2,'412105',NULL,21,'9730027987',3),(4,0,'CHIMBALI','2015-05-31','P[UNE',NULL,1,NULL,'2015-05-31 13:04:45',NULL,'2015-05-31','\0','','\0','2015-05-31 13:04:45',1,'YOGI WIRES PVT LTD',0,2,'412105',NULL,21,NULL,4),(5,0,'asfsasasa','2015-04-01','Pune',NULL,NULL,NULL,'2015-06-13 15:27:44',NULL,'2015-04-01','\0','','\0','2015-06-13 15:27:44',1,'yogi systems',0,3,NULL,'876',NULL,NULL,5),(6,0,NULL,'2015-04-01',NULL,NULL,NULL,NULL,'2015-06-13 17:52:39',NULL,'2015-04-01','\0','','\0','2015-06-13 17:52:39',4,'xyz',0,3,NULL,NULL,NULL,NULL,6);

/*Table structure for table `country` */

DROP TABLE IF EXISTS `country`;

CREATE TABLE `country` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `date_created` datetime NOT NULL,
  `last_updated` datetime NOT NULL,
  `name` varchar(255) NOT NULL,
  `unique_key` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `country` */

insert  into `country`(`id`,`version`,`date_created`,`last_updated`,`name`,`unique_key`) values (1,0,'2015-05-15 19:13:15','2015-05-15 19:13:15','India',1);

/*Table structure for table `interest_parameters` */

DROP TABLE IF EXISTS `interest_parameters`;

CREATE TABLE `interest_parameters` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `account_ledger_id` bigint(20) NOT NULL,
  `applicable_date_from` date DEFAULT NULL,
  `applicable_date_to` date DEFAULT NULL,
  `company_id` bigint(20) NOT NULL,
  `rate` decimal(19,2) NOT NULL,
  `rate_on` varchar(255) DEFAULT NULL,
  `rateper` varchar(255) DEFAULT NULL,
  `rounding_id` bigint(20) DEFAULT NULL,
  `srno` int(11) NOT NULL,
  `unique_key` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_9k1dsrh44io9wmcd6a7ts2u4d` (`account_ledger_id`),
  KEY `FK_jigcqo0r0el9guie77fi8k96g` (`company_id`),
  KEY `FK_a6ey6jqw9fmsidghbx7uac8po` (`rounding_id`),
  CONSTRAINT `FK_9k1dsrh44io9wmcd6a7ts2u4d` FOREIGN KEY (`account_ledger_id`) REFERENCES `account_ledger` (`id`),
  CONSTRAINT `FK_a6ey6jqw9fmsidghbx7uac8po` FOREIGN KEY (`rounding_id`) REFERENCES `account_flag` (`id`),
  CONSTRAINT `FK_jigcqo0r0el9guie77fi8k96g` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `interest_parameters` */

/*Table structure for table `organization` */

DROP TABLE IF EXISTS `organization`;

CREATE TABLE `organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `date_created` datetime NOT NULL,
  `last_updated` datetime NOT NULL,
  `name` varchar(255) NOT NULL,
  `unique_key` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `organization` */

insert  into `organization`(`id`,`version`,`date_created`,`last_updated`,`name`,`unique_key`) values (1,0,'2015-05-15 19:13:15','2015-05-15 19:13:15','Yogi Organization',1),(2,0,'2015-05-15 19:47:47','2015-05-15 19:47:47','Yogi Group',2),(3,0,'2015-06-13 15:26:23','2015-06-13 15:26:23','Yogi Group part1',3);

/*Table structure for table `parameters` */

DROP TABLE IF EXISTS `parameters`;

CREATE TABLE `parameters` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `applicable_from` datetime DEFAULT NULL,
  `applicable_to` datetime DEFAULT NULL,
  `company_id` bigint(20) NOT NULL,
  `latest_number` int(11) NOT NULL,
  `postfix` varchar(255) DEFAULT NULL,
  `prefix` varchar(255) DEFAULT NULL,
  `start_number` int(11) NOT NULL,
  `unique_key` int(11) NOT NULL,
  `voucher_type_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jxjrwgnfy4qm1j5g05v3micok` (`company_id`),
  KEY `FK_1e6cnhlmte7m45dd3oogapgt4` (`voucher_type_id`),
  CONSTRAINT `FK_1e6cnhlmte7m45dd3oogapgt4` FOREIGN KEY (`voucher_type_id`) REFERENCES `voucher_type` (`id`),
  CONSTRAINT `FK_jxjrwgnfy4qm1j5g05v3micok` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `parameters` */

/*Table structure for table `party_account` */

DROP TABLE IF EXISTS `party_account`;

CREATE TABLE `party_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `amount` decimal(19,2) NOT NULL,
  `amount_status` varchar(255) DEFAULT NULL,
  `bill_date` date DEFAULT NULL,
  `bill_no` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) NOT NULL,
  `cr_days` decimal(19,2) NOT NULL,
  `date_created` datetime NOT NULL,
  `last_updated` datetime NOT NULL,
  `last_updated_by_id` bigint(20) NOT NULL,
  `narration` varchar(255) DEFAULT NULL,
  `party_name_id` bigint(20) DEFAULT NULL,
  `remain_amount` decimal(19,2) DEFAULT NULL,
  `type_of_ref_id` bigint(20) DEFAULT NULL,
  `unique_key` int(11) NOT NULL,
  `voucher_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_572gluclbeqfwfj3wnydal8g7` (`company_id`),
  KEY `FK_6mcglrri4ruan0qogn9crieyf` (`last_updated_by_id`),
  KEY `FK_2wyhnnrun7tvgefu7k1xb2nvw` (`party_name_id`),
  KEY `FK_j1gnmjgrhhp3j8qgorhoks601` (`type_of_ref_id`),
  KEY `FK_mwwfjb6qg0qcvw30ow2qvfx1m` (`voucher_id`),
  CONSTRAINT `FK_2wyhnnrun7tvgefu7k1xb2nvw` FOREIGN KEY (`party_name_id`) REFERENCES `account_ledger` (`id`),
  CONSTRAINT `FK_572gluclbeqfwfj3wnydal8g7` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_6mcglrri4ruan0qogn9crieyf` FOREIGN KEY (`last_updated_by_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_j1gnmjgrhhp3j8qgorhoks601` FOREIGN KEY (`type_of_ref_id`) REFERENCES `account_flag` (`id`),
  CONSTRAINT `FK_mwwfjb6qg0qcvw30ow2qvfx1m` FOREIGN KEY (`voucher_id`) REFERENCES `voucher` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=latin1;

/*Data for the table `party_account` */

insert  into `party_account`(`id`,`version`,`amount`,`amount_status`,`bill_date`,`bill_no`,`company_id`,`cr_days`,`date_created`,`last_updated`,`last_updated_by_id`,`narration`,`party_name_id`,`remain_amount`,`type_of_ref_id`,`unique_key`,`voucher_id`) values (1,0,'10000.00','Dr','2015-05-16','On Account',2,'0.00','2015-05-16 18:07:47','2015-05-16 18:07:47',2,NULL,5,'0.00',4,1,NULL),(2,0,'10000.00','Cr','2015-05-16','On Account',2,'0.00','2015-05-16 18:09:01','2015-05-16 18:09:01',2,NULL,6,'0.00',4,2,NULL),(3,0,'0.00','Dr','2015-05-16','On Account',2,'0.00','2015-05-16 18:09:24','2015-05-16 18:09:24',2,NULL,7,'0.00',4,3,NULL),(4,0,'0.00','Dr','2015-05-16','On Account',2,'0.00','2015-05-16 18:10:01','2015-05-16 18:10:01',2,NULL,8,'0.00',4,4,NULL),(5,0,'0.00','Dr','2015-05-16','On Account',2,'0.00','2015-05-16 18:12:26','2015-05-16 18:12:26',2,NULL,9,'0.00',4,5,NULL),(6,0,'0.00','Dr','2015-05-16','On Account',2,'0.00','2015-05-16 18:12:47','2015-05-16 18:12:47',2,NULL,10,'0.00',4,6,NULL),(7,0,'0.00','Dr','2015-05-16','On Account',2,'0.00','2015-05-16 18:13:47','2015-05-16 18:13:47',2,NULL,11,'0.00',4,7,NULL),(11,0,'0.00','Dr','2015-05-16','On Account',2,'0.00','2015-05-16 18:23:16','2015-05-16 18:23:16',2,NULL,15,'0.00',4,8,NULL),(12,0,'50000.00','Dr','2015-05-31','On Account',2,'0.00','2015-05-31 12:40:31','2015-05-31 12:40:31',2,NULL,16,'0.00',4,9,NULL),(13,0,'0.00','Dr','2015-05-31','On Account',2,'0.00','2015-05-31 12:41:38','2015-05-31 12:41:38',2,NULL,17,'0.00',4,10,NULL),(14,0,'0.00','Dr','2015-05-31','On Account',2,'0.00','2015-05-31 12:45:02','2015-05-31 12:45:02',2,NULL,18,'0.00',4,11,NULL),(15,0,'0.00','Dr','2015-05-31','On Account',3,'0.00','2015-05-31 13:17:32','2015-05-31 13:17:32',3,NULL,23,'0.00',4,12,NULL),(16,0,'0.00','Dr','2015-05-31','On Account',3,'0.00','2015-05-31 13:17:55','2015-05-31 13:17:55',3,NULL,24,'0.00',4,13,NULL),(17,0,'0.00','Dr','2015-05-31','On Account',3,'0.00','2015-05-31 13:20:17','2015-05-31 13:20:17',3,NULL,25,'0.00',4,14,NULL),(18,0,'0.00','Dr','2015-05-31','On Account',3,'0.00','2015-05-31 13:20:45','2015-05-31 13:20:45',3,NULL,26,'0.00',4,15,NULL),(19,0,'0.00','Dr','2015-05-31','On Account',3,'0.00','2015-05-31 13:21:29','2015-05-31 13:21:29',3,NULL,27,'0.00',4,16,NULL),(20,0,'0.00','Dr','2015-05-31','On Account',3,'0.00','2015-05-31 13:22:22','2015-05-31 13:22:22',3,NULL,28,'0.00',4,17,NULL),(21,0,'0.00','Dr','2015-05-31','On Account',3,'0.00','2015-05-31 13:23:41','2015-05-31 13:23:41',3,NULL,29,'0.00',4,18,NULL),(22,0,'50000.00','Cr','2015-05-31','On Account',3,'0.00','2015-05-31 13:24:33','2015-05-31 13:24:33',3,NULL,30,'0.00',4,19,NULL),(23,0,'0.00','Dr','2015-05-31','On Account',3,'0.00','2015-05-31 13:32:31','2015-05-31 13:32:31',3,NULL,31,'0.00',4,20,NULL),(24,0,'0.00','Dr','2015-05-31','On Account',3,'0.00','2015-05-31 13:32:56','2015-05-31 13:32:56',3,NULL,32,'0.00',4,21,NULL),(25,0,'0.00','Dr','2015-05-31','On Account',3,'0.00','2015-05-31 13:32:56','2015-05-31 13:32:56',3,NULL,33,'0.00',4,22,NULL),(26,0,'0.00','Dr','2015-05-31','On Account',3,'0.00','2015-05-31 13:33:31','2015-05-31 13:33:31',3,NULL,34,'0.00',4,23,NULL),(27,0,'0.00','Dr','2015-05-31','On Account',3,'0.00','2015-05-31 13:33:59','2015-05-31 13:33:59',3,NULL,35,'0.00',4,24,NULL),(28,0,'0.00','Dr','2015-05-31','On Account',3,'0.00','2015-05-31 13:34:41','2015-05-31 13:34:41',3,NULL,36,'0.00',4,25,NULL),(29,0,'0.00','Dr','2015-05-31','On Account',3,'0.00','2015-05-31 13:35:15','2015-05-31 13:35:15',3,NULL,37,'0.00',4,26,NULL),(32,0,'0.00','Dr','2015-06-13','On Account',5,'0.00','2015-06-13 15:46:16','2015-06-13 15:46:16',4,NULL,42,'0.00',4,28,NULL),(37,0,'0.00','Dr','2015-06-13','On Account',5,'0.00','2015-06-13 15:57:57','2015-06-13 15:57:57',4,NULL,47,'0.00',4,31,NULL),(38,0,'0.00','Dr','2015-06-13','On Account',5,'0.00','2015-06-13 15:59:04','2015-06-13 15:59:04',4,NULL,48,'0.00',4,32,NULL),(39,0,'0.00','Dr','2015-06-13','On Account',5,'0.00','2015-06-13 16:00:28','2015-06-13 16:00:28',4,NULL,49,'0.00',4,33,NULL),(40,0,'0.00','Dr','2015-06-13','On Account',5,'0.00','2015-06-13 16:18:46','2015-06-13 16:18:46',4,NULL,50,'0.00',4,34,NULL),(42,0,'0.00','Dr','2015-06-13','On Account',6,'0.00','2015-06-13 18:02:27','2015-06-13 18:02:27',5,NULL,54,'0.00',4,35,NULL),(43,0,'0.00','Dr','2015-06-13','On Account',6,'0.00','2015-06-13 18:02:55','2015-06-13 18:02:55',5,NULL,55,'0.00',4,36,NULL),(44,0,'0.00','Dr','2015-06-13','On Account',6,'0.00','2015-06-13 18:03:50','2015-06-13 18:03:50',5,NULL,56,'0.00',4,37,NULL),(45,0,'0.00','Dr','2015-06-13','On Account',6,'0.00','2015-06-13 18:04:31','2015-06-13 18:04:31',5,NULL,57,'0.00',4,38,NULL),(46,0,'0.00','Dr','2015-06-13','On Account',6,'0.00','2015-06-13 18:05:06','2015-06-13 18:05:06',5,NULL,58,'0.00',4,39,NULL),(47,0,'0.00','Dr','2015-06-13','On Account',6,'0.00','2015-06-13 18:05:27','2015-06-13 18:05:27',5,NULL,59,'0.00',4,40,NULL),(48,0,'0.00','Dr','2015-06-13','On Account',6,'0.00','2015-06-13 18:05:45','2015-06-13 18:05:45',5,NULL,60,'0.00',4,41,NULL),(49,0,'0.00','Dr','2015-06-13','On Account',6,'0.00','2015-06-13 18:06:10','2015-06-13 18:06:10',5,NULL,61,'0.00',4,42,NULL),(50,0,'0.00','Dr','2015-06-13','On Account',6,'0.00','2015-06-13 18:06:45','2015-06-13 18:06:45',5,NULL,62,'0.00',4,43,NULL),(51,0,'0.00','Dr','2015-06-13','On Account',6,'0.00','2015-06-13 18:07:42','2015-06-13 18:07:42',5,NULL,63,'0.00',4,44,NULL),(52,0,'0.00','Dr','2015-06-13','On Account',6,'0.00','2015-06-13 18:08:21','2015-06-13 18:08:21',5,NULL,64,'0.00',4,45,NULL),(53,0,'0.00','Dr','2015-06-13','On Account',6,'0.00','2015-06-13 18:09:02','2015-06-13 18:09:02',5,NULL,65,'0.00',4,46,NULL),(54,0,'0.00','Dr','2015-06-13','On Account',6,'0.00','2015-06-13 18:09:39','2015-06-13 18:09:39',5,NULL,66,'0.00',4,47,NULL),(55,0,'0.00','Dr','2015-06-13','On Account',6,'0.00','2015-06-13 18:10:49','2015-06-13 18:10:49',5,NULL,67,'0.00',4,48,NULL);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `company_id` bigint(20) NOT NULL,
  `date_created` datetime NOT NULL,
  `last_updated` datetime NOT NULL,
  `last_updated_by_id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `unique_key` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1r0c73gb0d2vwt6he3fy7dwc` (`company_id`),
  KEY `FK_him0cy5xim2y9qc7abr5efauv` (`last_updated_by_id`),
  CONSTRAINT `FK_1r0c73gb0d2vwt6he3fy7dwc` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_him0cy5xim2y9qc7abr5efauv` FOREIGN KEY (`last_updated_by_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `role` */

insert  into `role`(`id`,`version`,`company_id`,`date_created`,`last_updated`,`last_updated_by_id`,`name`,`unique_key`) values (1,0,1,'2015-05-15 19:13:16','2015-05-15 19:13:16',1,'Admin_Authority',1),(2,0,1,'2015-05-15 19:50:25','2015-05-15 19:50:25',1,'yogi_system_authority',2),(3,0,1,'2015-06-13 15:29:39','2015-06-13 15:29:39',1,'Yogi Group part1',3);

/*Table structure for table `role_child` */

DROP TABLE IF EXISTS `role_child`;

CREATE TABLE `role_child` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `can_add` bit(1) NOT NULL,
  `can_delete` bit(1) NOT NULL,
  `can_print` bit(1) NOT NULL,
  `can_update` bit(1) NOT NULL,
  `can_view` bit(1) NOT NULL,
  `company_id` bigint(20) NOT NULL,
  `parent_screen_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `screen_id` bigint(20) NOT NULL,
  `status` bit(1) NOT NULL,
  `unique_key` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_n9iu1i0hscbvnr72j15onmh0x` (`company_id`),
  KEY `FK_lwpuxtehnhffxc0orabc7567m` (`parent_screen_id`),
  KEY `FK_p9ndbwkq150e9749ehgir56yv` (`role_id`),
  KEY `FK_8gpjjvoph5cqf5cavh7riaho5` (`screen_id`),
  CONSTRAINT `FK_8gpjjvoph5cqf5cavh7riaho5` FOREIGN KEY (`screen_id`) REFERENCES `screen` (`id`),
  CONSTRAINT `FK_lwpuxtehnhffxc0orabc7567m` FOREIGN KEY (`parent_screen_id`) REFERENCES `screen` (`id`),
  CONSTRAINT `FK_n9iu1i0hscbvnr72j15onmh0x` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_p9ndbwkq150e9749ehgir56yv` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=latin1;

/*Data for the table `role_child` */

insert  into `role_child`(`id`,`version`,`can_add`,`can_delete`,`can_print`,`can_update`,`can_view`,`company_id`,`parent_screen_id`,`role_id`,`screen_id`,`status`,`unique_key`) values (1,0,'','','','','',1,3,1,16,'\0',1),(2,0,'','','','','',1,6,1,37,'\0',2),(3,0,'','','','','',1,3,1,20,'\0',3),(4,0,'','','','','',1,2,1,24,'\0',4),(5,0,'','','','','',1,5,1,28,'\0',5),(6,0,'','','','','',1,3,1,17,'\0',6),(7,0,'','','','','',1,2,1,27,'\0',7),(8,0,'','','','','',1,5,1,35,'\0',8),(9,0,'','','','','',1,5,1,33,'\0',9),(10,0,'','','','','',1,3,1,13,'\0',10),(11,0,'','','','','',1,2,1,14,'\0',11),(12,0,'','','','','',1,3,1,23,'\0',12),(13,0,'','','','','',1,1,1,8,'\0',13),(14,0,'','','','','',1,2,1,21,'\0',14),(15,0,'','','','','',1,4,1,26,'\0',15),(16,0,'','','','','',1,5,1,30,'\0',16),(17,0,'','','','','',1,2,1,15,'\0',17),(18,0,'','','','','',1,5,1,36,'\0',18),(19,0,'','','','','',1,1,1,7,'\0',19),(20,0,'','','','','',1,2,1,12,'\0',20),(21,0,'','','','','',1,3,1,18,'\0',21),(22,0,'','','','','',1,3,1,25,'\0',22),(23,0,'','','','','',1,2,1,22,'\0',23),(24,0,'','','','','',1,5,1,31,'\0',24),(25,0,'','','','','',1,2,1,19,'\0',25),(26,0,'','','','','',1,1,1,11,'\0',26),(27,0,'\0','\0','\0','\0','\0',1,2,2,19,'',27),(28,0,'\0','\0','\0','\0','\0',1,3,2,20,'',28),(29,0,'\0','\0','\0','\0','\0',1,2,2,27,'',29),(30,0,'\0','\0','\0','\0','\0',1,2,2,14,'',30),(31,0,'\0','\0','\0','\0','\0',1,3,2,13,'',31),(32,0,'\0','\0','\0','\0','\0',1,4,2,26,'',32),(33,0,'\0','\0','\0','\0','\0',1,1,2,8,'',33),(34,0,'\0','\0','\0','\0','\0',1,3,2,17,'',34),(35,0,'\0','\0','\0','\0','\0',1,2,2,22,'',35),(36,0,'\0','\0','\0','\0','\0',1,3,2,25,'',36),(37,0,'\0','\0','\0','\0','\0',1,2,2,15,'',37),(38,0,'\0','\0','\0','\0','\0',1,6,2,37,'',38),(39,0,'\0','\0','\0','\0','\0',1,1,2,11,'',39),(40,0,'\0','\0','\0','\0','\0',1,2,2,12,'',40),(41,0,'\0','\0','\0','\0','\0',1,3,2,18,'',41),(42,0,'\0','\0','\0','\0','\0',1,3,2,16,'',42),(43,0,'\0','\0','\0','\0','\0',1,3,2,23,'',43),(44,0,'\0','\0','\0','\0','\0',1,1,2,7,'',44),(45,0,'\0','\0','\0','\0','\0',1,2,2,21,'',45),(46,0,'\0','\0','\0','\0','\0',1,2,2,24,'',46),(47,0,'\0','\0','\0','\0','\0',1,5,2,28,'',47),(48,0,'\0','\0','\0','\0','\0',1,3,3,16,'',48),(49,0,'\0','\0','\0','\0','\0',1,3,3,13,'',49),(50,0,'\0','\0','\0','\0','\0',1,1,3,7,'',50),(51,0,'\0','\0','\0','\0','\0',1,5,3,30,'',51),(52,0,'\0','\0','\0','\0','\0',1,3,3,18,'',52),(53,0,'\0','\0','\0','\0','\0',1,2,3,12,'',53),(54,0,'\0','\0','\0','\0','\0',1,2,3,15,'',54),(55,0,'\0','\0','\0','\0','\0',1,5,3,35,'',55),(56,0,'\0','\0','\0','\0','\0',1,5,3,31,'',56),(57,0,'\0','\0','\0','\0','\0',1,1,3,11,'',57),(58,0,'\0','\0','\0','\0','\0',1,2,3,19,'',58),(59,0,'\0','\0','\0','\0','\0',1,2,3,14,'',59),(60,0,'\0','\0','\0','\0','\0',1,2,3,22,'',60),(61,0,'\0','\0','\0','\0','\0',1,1,3,8,'',61),(62,0,'\0','\0','\0','\0','\0',1,3,3,25,'',62),(63,0,'\0','\0','\0','\0','\0',1,3,3,23,'',63),(64,0,'\0','\0','\0','\0','\0',1,2,3,27,'',64),(65,0,'\0','\0','\0','\0','\0',1,5,3,33,'',65),(66,0,'\0','\0','\0','\0','\0',1,2,3,24,'',66),(67,0,'\0','\0','\0','\0','\0',1,2,3,21,'',67),(68,0,'\0','\0','\0','\0','\0',1,3,3,20,'',68),(69,0,'\0','\0','\0','\0','\0',1,4,3,26,'',69),(70,0,'\0','\0','\0','\0','\0',1,5,3,36,'',70),(71,0,'\0','\0','\0','\0','\0',1,5,3,28,'',71),(72,0,'\0','\0','\0','\0','\0',1,6,3,37,'',72),(73,0,'\0','\0','\0','\0','\0',1,3,3,17,'',73);

/*Table structure for table `screen` */

DROP TABLE IF EXISTS `screen`;

CREATE TABLE `screen` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `controller` varchar(255) DEFAULT NULL,
  `domain_name` varchar(255) DEFAULT NULL,
  `filter` varchar(255) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_screen_id` bigint(20) DEFAULT NULL,
  `sort_list` int(11) NOT NULL,
  `unique_key` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7sd9xrvnfry6m6o9kujfi2hmv` (`parent_screen_id`),
  CONSTRAINT `FK_7sd9xrvnfry6m6o9kujfi2hmv` FOREIGN KEY (`parent_screen_id`) REFERENCES `screen` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;

/*Data for the table `screen` */

insert  into `screen`(`id`,`version`,`controller`,`domain_name`,`filter`,`link`,`name`,`parent_screen_id`,`sort_list`,`unique_key`) values (1,0,NULL,NULL,NULL,NULL,'Master',NULL,1,1),(2,0,NULL,NULL,NULL,NULL,'Outstanding',NULL,5,5),(3,0,NULL,NULL,NULL,NULL,'Account Books',NULL,4,4),(4,0,NULL,NULL,NULL,NULL,'Statistics',NULL,6,6),(5,0,NULL,NULL,NULL,NULL,'Utilities',NULL,3,3),(6,0,NULL,NULL,NULL,NULL,'Transaction',NULL,2,2),(7,0,'accountGroup','Group',NULL,'/group/create','com.master.AccountGroupController',1,1,7),(8,0,'accountLedger','Ledger',NULL,'/ledger/create','com.master.AccountLedgerController',1,1,8),(9,0,'interestParameters','Ledger',NULL,'/ledger/create','com.master.InterestParametersController',NULL,1,9),(10,0,'parameters','Ledger',NULL,'/ledger/create','com.master.ParametersController',NULL,1,10),(11,0,'voucherType','VoucherType',NULL,'/voucherType/create','com.master.VoucherTypeController',1,1,11),(12,0,'balanceSheetReport','Balance Sheet',NULL,'/reports/balanceSheet','com.reports.BalanceSheetReportController',2,1,12),(13,0,'cashBankSummaryReport','Cash/Bank Summary',NULL,'/reports/cashBankSummary','com.reports.CashBankSummaryReportController',3,1,13),(14,0,'dayBookReport','Day Book',NULL,'/reports/dayBook','com.reports.DayBookReportController',2,1,14),(15,0,'groupOutstandingReport','Group Outstanding',NULL,'/reports/groupOutstanding','com.reports.GroupOutstandingReportController',2,1,15),(16,0,'groupSummaryReport','Group Summary',NULL,'/reports/groupSummary','com.reports.GroupSummaryReportController',3,1,16),(17,0,'groupVouchersReport','Group Vouchers',NULL,'/reports/groupVouchers','com.reports.GroupVouchersReportController',3,1,17),(18,0,'journalRegisterReport','Journal Register',NULL,'/reports/journalRegister','com.reports.JournalRegisterReportController',3,1,18),(19,0,'ledgerOutstandingReports','Ledger Outstanding',NULL,'/reports/ledgerOutstanding','com.reports.LedgerOutstandingReportsController',2,1,19),(20,0,'ledgerReport','Ledger Report',NULL,'/reports/ledgerReport','com.reports.LedgerReportController',3,1,20),(21,0,'payablesOutstandingReport','Payables Outstanding',NULL,'/reports/payablesOutstanding','com.reports.PayablesOutstandingReportController',2,1,21),(22,0,'profitAndLossAcReport','Profit And Loss Ac',NULL,'/reports/profitAndLossAc','com.reports.ProfitAndLossAcReportController',2,1,22),(23,0,'purchaseRegisterReport','Purchase Register',NULL,'/reports/purchaseRegister','com.reports.PurchaseRegisterReportController',3,1,23),(24,0,'receivablesOutstandingReport','Receivables Outstanding',NULL,'/reports/receivablesOutstanding','com.reports.ReceivablesOutstandingReportController',2,1,24),(25,0,'salesRegisterReport','Sales Register',NULL,'/reports/salesRegister','com.reports.SalesRegisterReportController',3,1,25),(26,0,'statisticsReport','Statistics Report',NULL,'/reports/statisticsReport','com.reports.StatisticsReportController',4,1,26),(27,0,'trailBalanceReport','Trail Balance',NULL,'/reports/trailBalance','com.reports.TrailBalanceReportController',2,1,27),(28,0,'company','Company',NULL,'/company/create','com.system.CompanyController',5,1,28),(29,0,'country','Company',NULL,'/company/create','com.system.CountryController',NULL,1,29),(30,0,'organization','Organization',NULL,'/organization/create','com.system.OrganizationController',5,1,30),(31,0,'role','Role',NULL,'/role/create','com.system.RoleController',5,1,31),(32,0,'screen','Role',NULL,'/role/create','com.system.ScreenController',NULL,1,32),(33,0,'split','Split Company',NULL,'/split/page','com.system.SplitController',5,1,33),(34,0,'state','Split Company',NULL,'/split/page','com.system.StateController',NULL,1,34),(35,0,'user','User',NULL,'/user/create','com.system.UserController',5,1,35),(36,0,'userRole','User Role',NULL,'/userRole/create','com.system.UserRoleController',5,1,36),(37,0,'voucher','Voucher',NULL,'/voucher/create','com.transaction.VoucherController',6,1,37),(38,0,'dbdoc','Voucher',NULL,'/voucher/create','grails.plugin.databasemigration.DbdocController',NULL,1,38),(39,0,'restfulApi','Voucher',NULL,'/voucher/create','net.hedtech.restfulapi.RestfulApiController',NULL,1,39),(40,0,'jasper','Voucher',NULL,'/voucher/create','org.codehaus.groovy.grails.plugins.jasper.JasperController',NULL,1,40),(41,0,'jasperDemo','Voucher',NULL,'/voucher/create','org.codehaus.groovy.grails.plugins.jasper.JasperDemoController',NULL,1,41),(42,0,'login','Voucher',NULL,'/voucher/create','yogiaccount.LoginController',NULL,1,42);

/*Table structure for table `state` */

DROP TABLE IF EXISTS `state`;

CREATE TABLE `state` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `country_id` bigint(20) NOT NULL,
  `date_created` datetime NOT NULL,
  `last_updated` datetime NOT NULL,
  `name` varchar(255) NOT NULL,
  `unique_key` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lxoqjm8644epv72af3k3jpalx` (`country_id`),
  CONSTRAINT `FK_lxoqjm8644epv72af3k3jpalx` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;

/*Data for the table `state` */

insert  into `state`(`id`,`version`,`country_id`,`date_created`,`last_updated`,`name`,`unique_key`) values (1,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Andaman & Nicobar Island',1),(2,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Andhra Pardesh',2),(3,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Arunachal Pardesh',3),(4,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Assam',4),(5,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Bihar',5),(6,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Chandigarh',6),(7,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Chhattisgarh',7),(8,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Dadra & Nagar Haveli',8),(9,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Daman & Diu',9),(10,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Delhi',10),(11,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Goa',11),(12,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Gujrat',12),(13,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Haryana',13),(14,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Himachal Pardesh',14),(15,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Jammu & Kashmir',15),(16,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Jharkhand',16),(17,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Karanataka',17),(18,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Kerla',18),(19,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Lakshadweep',19),(20,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Madhya Pardesh',20),(21,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Maharashtra',21),(22,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Manipur',22),(23,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Meghalaya',23),(24,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Mizoram',24),(25,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Nagaland',25),(26,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Orissa',26),(27,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Paducherry',27),(28,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Punjab',28),(29,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Rajasthan',29),(30,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Sikkim',30),(31,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Tamilnadu',31),(32,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Tripura',32),(33,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Uttrakhand',33),(34,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','Uttar Pardesh',34),(35,0,1,'2015-05-15 19:13:15','2015-05-15 19:13:15','West Bengal',35);

/*Table structure for table `statutory_info` */

DROP TABLE IF EXISTS `statutory_info`;

CREATE TABLE `statutory_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `activatee1ore2transaction_vat` bit(1) NOT NULL,
  `allow_alteraion_of_tds_nature_of_payment_in_expense` bit(1) NOT NULL,
  `allow_alteraion_of_tds_rate_for_lower_deductions` bit(1) NOT NULL,
  `allow_select_of_vat_tax_during_entry` bit(1) NOT NULL,
  `assessee_code` varchar(255) DEFAULT NULL,
  `commisionerate_code` varchar(255) DEFAULT NULL,
  `commisionerate_name` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) NOT NULL,
  `date_of_reg` date DEFAULT NULL,
  `division_code` varchar(255) DEFAULT NULL,
  `division_name` varchar(255) DEFAULT NULL,
  `enable_service_tax` bit(1) NOT NULL,
  `enable_value_added_tax` bit(1) NOT NULL,
  `inter_state_sales_tax_number` varchar(255) DEFAULT NULL,
  `is_large_tax_payer` bit(1) NOT NULL,
  `large_tax_payer_unit` varchar(255) DEFAULT NULL,
  `local_sales_tax_number` varchar(255) DEFAULT NULL,
  `pan_tax_no` varchar(255) DEFAULT NULL,
  `premises_code_no` varchar(255) DEFAULT NULL,
  `range_code` varchar(255) DEFAULT NULL,
  `range_name` varchar(255) DEFAULT NULL,
  `regular_vat_applicable_from` date DEFAULT NULL,
  `service_tax_alter_details` bit(1) NOT NULL,
  `service_tax_reg_no` varchar(255) DEFAULT NULL,
  `state_id` bigint(20) DEFAULT NULL,
  `tcs_alter_details` bit(1) NOT NULL,
  `tcs_deductor_collector_type_id` bigint(20) DEFAULT NULL,
  `tcs_designation` varchar(255) DEFAULT NULL,
  `tcs_enable_tax_collected_at_source` bit(1) NOT NULL,
  `tcs_income_tax_circleward` varchar(255) DEFAULT NULL,
  `tcs_responsible_person` varchar(255) DEFAULT NULL,
  `tcs_tax_assessment_no` varchar(255) DEFAULT NULL,
  `tds_alter_details` bit(1) NOT NULL,
  `tds_deductor_collector_type_id` bigint(20) DEFAULT NULL,
  `tds_designation` varchar(255) DEFAULT NULL,
  `tds_enable_tax_deducted_at_source` bit(1) NOT NULL,
  `tds_income_tax_circleward` varchar(255) DEFAULT NULL,
  `tds_responsible_person` varchar(255) DEFAULT NULL,
  `tds_tax_assessment_no` varchar(255) DEFAULT NULL,
  `type_of_dealer_id` bigint(20) DEFAULT NULL,
  `type_of_organization_id` bigint(20) DEFAULT NULL,
  `unique_key` int(11) NOT NULL,
  `vat_alter_details` bit(1) NOT NULL,
  `vat_tin_composition` varchar(255) DEFAULT NULL,
  `vat_tin_regular` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_5sa9is52ivyso3p0j9agruqy` (`company_id`),
  KEY `FK_gk4d7j5momhdyv2mj7qkhxr6v` (`state_id`),
  KEY `FK_rtcidh8gphfeycf6og74v0en2` (`tcs_deductor_collector_type_id`),
  KEY `FK_imcdkr9ybqwjuy5qsj1u5iwlu` (`tds_deductor_collector_type_id`),
  KEY `FK_s0kf18my4crk72388hp1y7n9m` (`type_of_dealer_id`),
  KEY `FK_79g9k9epdkln7cic44n22gcs0` (`type_of_organization_id`),
  CONSTRAINT `FK_5sa9is52ivyso3p0j9agruqy` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_79g9k9epdkln7cic44n22gcs0` FOREIGN KEY (`type_of_organization_id`) REFERENCES `account_flag` (`id`),
  CONSTRAINT `FK_gk4d7j5momhdyv2mj7qkhxr6v` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`),
  CONSTRAINT `FK_imcdkr9ybqwjuy5qsj1u5iwlu` FOREIGN KEY (`tds_deductor_collector_type_id`) REFERENCES `account_flag` (`id`),
  CONSTRAINT `FK_rtcidh8gphfeycf6og74v0en2` FOREIGN KEY (`tcs_deductor_collector_type_id`) REFERENCES `account_flag` (`id`),
  CONSTRAINT `FK_s0kf18my4crk72388hp1y7n9m` FOREIGN KEY (`type_of_dealer_id`) REFERENCES `account_flag` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `statutory_info` */

insert  into `statutory_info`(`id`,`version`,`activatee1ore2transaction_vat`,`allow_alteraion_of_tds_nature_of_payment_in_expense`,`allow_alteraion_of_tds_rate_for_lower_deductions`,`allow_select_of_vat_tax_during_entry`,`assessee_code`,`commisionerate_code`,`commisionerate_name`,`company_id`,`date_of_reg`,`division_code`,`division_name`,`enable_service_tax`,`enable_value_added_tax`,`inter_state_sales_tax_number`,`is_large_tax_payer`,`large_tax_payer_unit`,`local_sales_tax_number`,`pan_tax_no`,`premises_code_no`,`range_code`,`range_name`,`regular_vat_applicable_from`,`service_tax_alter_details`,`service_tax_reg_no`,`state_id`,`tcs_alter_details`,`tcs_deductor_collector_type_id`,`tcs_designation`,`tcs_enable_tax_collected_at_source`,`tcs_income_tax_circleward`,`tcs_responsible_person`,`tcs_tax_assessment_no`,`tds_alter_details`,`tds_deductor_collector_type_id`,`tds_designation`,`tds_enable_tax_deducted_at_source`,`tds_income_tax_circleward`,`tds_responsible_person`,`tds_tax_assessment_no`,`type_of_dealer_id`,`type_of_organization_id`,`unique_key`,`vat_alter_details`,`vat_tin_composition`,`vat_tin_regular`) values (1,0,'\0','\0','\0','\0',NULL,NULL,NULL,1,NULL,NULL,NULL,'\0','\0',NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'\0',NULL,NULL,'\0',NULL,NULL,'\0',NULL,NULL,NULL,'\0',NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,1,'\0',NULL,NULL),(2,0,'\0','\0','\0','\0',NULL,NULL,NULL,2,NULL,NULL,NULL,'\0','\0',NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'\0',NULL,NULL,'\0',NULL,NULL,'\0',NULL,NULL,NULL,'\0',NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,2,'\0',NULL,NULL),(3,0,'\0','\0','\0','\0',NULL,NULL,NULL,3,NULL,NULL,NULL,'\0','\0',NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'\0',NULL,NULL,'\0',NULL,NULL,'\0',NULL,NULL,NULL,'\0',NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,3,'\0',NULL,NULL),(4,0,'\0','\0','\0','\0',NULL,NULL,NULL,4,NULL,NULL,NULL,'\0','\0',NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'\0',NULL,NULL,'\0',NULL,NULL,'\0',NULL,NULL,NULL,'\0',NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,4,'\0',NULL,NULL),(5,0,'\0','\0','\0','\0',NULL,NULL,NULL,5,NULL,NULL,NULL,'\0','\0',NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'\0',NULL,NULL,'\0',NULL,NULL,'\0',NULL,NULL,NULL,'\0',NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,5,'\0',NULL,NULL),(6,0,'\0','\0','\0','\0',NULL,NULL,NULL,6,NULL,NULL,NULL,'\0','\0',NULL,'\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'\0',NULL,NULL,'\0',NULL,NULL,'\0',NULL,NULL,NULL,'\0',NULL,NULL,'\0',NULL,NULL,NULL,NULL,NULL,6,'\0',NULL,NULL);

/*Table structure for table `tax_setting` */

DROP TABLE IF EXISTS `tax_setting`;

CREATE TABLE `tax_setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `company_id` bigint(20) NOT NULL,
  `date_created` datetime NOT NULL,
  `is_display` bit(1) NOT NULL,
  `last_updated` datetime NOT NULL,
  `tax_id` bigint(20) NOT NULL,
  `unique_key` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gge4ng9o1svyuhr1sxftv272u` (`company_id`),
  KEY `FK_j2hclh9wtwhq5d6h97fk4al7o` (`tax_id`),
  CONSTRAINT `FK_gge4ng9o1svyuhr1sxftv272u` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_j2hclh9wtwhq5d6h97fk4al7o` FOREIGN KEY (`tax_id`) REFERENCES `account_flag` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;

/*Data for the table `tax_setting` */

insert  into `tax_setting`(`id`,`version`,`company_id`,`date_created`,`is_display`,`last_updated`,`tax_id`,`unique_key`) values (1,0,2,'2015-05-15 19:49:08','','2015-05-15 19:49:08',5,1),(2,0,2,'2015-05-15 19:49:08','','2015-05-15 19:49:08',6,2),(3,0,2,'2015-05-15 19:49:08','\0','2015-05-15 19:49:08',7,3),(4,0,2,'2015-05-15 19:49:08','\0','2015-05-15 19:49:08',8,4),(5,0,2,'2015-05-15 19:49:08','\0','2015-05-15 19:49:08',9,5),(6,0,2,'2015-05-15 19:49:08','\0','2015-05-15 19:49:08',10,6),(7,0,2,'2015-05-15 19:49:08','\0','2015-05-15 19:49:08',11,7),(8,0,3,'2015-05-31 12:47:41','','2015-05-31 12:47:41',5,8),(9,0,3,'2015-05-31 12:47:41','','2015-05-31 12:47:41',6,9),(10,0,3,'2015-05-31 12:47:41','\0','2015-05-31 12:47:41',7,10),(11,0,3,'2015-05-31 12:47:41','\0','2015-05-31 12:47:41',8,11),(12,0,3,'2015-05-31 12:47:41','\0','2015-05-31 12:47:41',9,12),(13,0,3,'2015-05-31 12:47:41','\0','2015-05-31 12:47:41',10,13),(14,0,3,'2015-05-31 12:47:41','\0','2015-05-31 12:47:41',11,14),(15,0,4,'2015-05-31 13:04:46','','2015-05-31 13:04:46',5,15),(16,0,4,'2015-05-31 13:04:46','','2015-05-31 13:04:46',6,16),(17,0,4,'2015-05-31 13:04:46','\0','2015-05-31 13:04:46',7,17),(18,0,4,'2015-05-31 13:04:46','\0','2015-05-31 13:04:46',8,18),(19,0,4,'2015-05-31 13:04:46','\0','2015-05-31 13:04:46',9,19),(20,0,4,'2015-05-31 13:04:46','\0','2015-05-31 13:04:46',10,20),(21,0,4,'2015-05-31 13:04:47','\0','2015-05-31 13:04:47',11,21),(22,0,5,'2015-06-13 15:27:44','','2015-06-13 15:27:44',5,22),(23,0,5,'2015-06-13 15:27:44','','2015-06-13 15:27:44',6,23),(24,0,5,'2015-06-13 15:27:44','\0','2015-06-13 15:27:44',7,24),(25,0,5,'2015-06-13 15:27:44','\0','2015-06-13 15:27:44',8,25),(26,0,5,'2015-06-13 15:27:44','\0','2015-06-13 15:27:44',9,26),(27,0,5,'2015-06-13 15:27:44','\0','2015-06-13 15:27:44',10,27),(28,0,5,'2015-06-13 15:27:44','\0','2015-06-13 15:27:44',11,28),(29,0,6,'2015-06-13 17:52:39','','2015-06-13 17:52:39',5,29),(30,0,6,'2015-06-13 17:52:39','','2015-06-13 17:52:39',6,30),(31,0,6,'2015-06-13 17:52:39','\0','2015-06-13 17:52:39',7,31),(32,0,6,'2015-06-13 17:52:39','\0','2015-06-13 17:52:39',8,32),(33,0,6,'2015-06-13 17:52:39','\0','2015-06-13 17:52:39',9,33),(34,0,6,'2015-06-13 17:52:39','\0','2015-06-13 17:52:39',10,34),(35,0,6,'2015-06-13 17:52:39','\0','2015-06-13 17:52:39',11,35);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `date_created` datetime NOT NULL,
  `enable` bit(1) NOT NULL,
  `is_admin` bit(1) NOT NULL,
  `is_client` bit(1) NOT NULL,
  `last_updated` datetime NOT NULL,
  `organization_id` bigint(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `unique_key` int(11) NOT NULL,
  `use_multiple_company` bit(1) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bteyn2vjkuydkfqefgaje2rhr` (`company_id`),
  KEY `FK_n3q5mbs97yqmvsah7yyji1i78` (`organization_id`),
  CONSTRAINT `FK_bteyn2vjkuydkfqefgaje2rhr` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_n3q5mbs97yqmvsah7yyji1i78` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`id`,`version`,`company_id`,`date_created`,`enable`,`is_admin`,`is_client`,`last_updated`,`organization_id`,`password`,`unique_key`,`use_multiple_company`,`username`) values (1,1,1,'2015-05-15 19:13:15','','','\0','2015-05-15 19:13:15',1,'a',1,'\0','a@gmail.com'),(2,0,2,'2015-05-15 19:49:38','','\0','','2015-05-15 19:49:38',2,'b',2,'\0','b@gmail.com'),(3,0,3,'2015-05-31 13:06:45','','\0','','2015-05-31 13:06:45',2,'arun',3,'','arun@gmail.com'),(4,0,5,'2015-06-13 15:29:06','','\0','','2015-06-13 15:29:06',3,'yg',4,'','yg@gmail.com'),(5,0,6,'2015-06-13 17:53:42','','\0','','2015-06-13 17:53:42',3,'r',5,'','r@gmail.com');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `unique_key` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_it77eq964jhfqtu54081ebtio` (`role_id`),
  KEY `FK_apcc8lxk2xnug8377fatvbn04` (`user_id`),
  CONSTRAINT `FK_apcc8lxk2xnug8377fatvbn04` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_it77eq964jhfqtu54081ebtio` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `user_role` */

insert  into `user_role`(`id`,`version`,`role_id`,`unique_key`,`user_id`) values (1,0,1,1,1),(2,0,2,2,2),(3,0,1,3,3),(4,0,3,4,4),(5,0,3,5,5);

/*Table structure for table `voucher` */

DROP TABLE IF EXISTS `voucher`;

CREATE TABLE `voucher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `amount` decimal(19,2) NOT NULL,
  `amount_status` varchar(255) DEFAULT NULL,
  `buyer_address` varchar(255) DEFAULT NULL,
  `buyers` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) NOT NULL,
  `cosignee_address` varchar(255) DEFAULT NULL,
  `cosignee_name` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `date_created` datetime NOT NULL,
  `delivery_note_no` varchar(255) DEFAULT NULL,
  `despatch_doc_no` varchar(255) DEFAULT NULL,
  `despatch_through` varchar(255) DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `is_post_dated` bit(1) NOT NULL,
  `last_updated` datetime NOT NULL,
  `last_updated_by_id` bigint(20) NOT NULL,
  `mode_trems_of_payment` varchar(255) DEFAULT NULL,
  `narration` varchar(2048) DEFAULT NULL,
  `oredr_no` varchar(255) DEFAULT NULL,
  `other_reference` varchar(255) DEFAULT NULL,
  `party_name_id` bigint(20) DEFAULT NULL,
  `rate` decimal(19,2) NOT NULL,
  `reference_no` varchar(255) DEFAULT NULL,
  `row_status` varchar(255) DEFAULT NULL,
  `sales_ledger_id` bigint(20) DEFAULT NULL,
  `t_in_sales_tax_no` varchar(255) DEFAULT NULL,
  `terms_of_delivery` varchar(255) DEFAULT NULL,
  `unique_key` int(11) NOT NULL,
  `voucher_id` bigint(20) DEFAULT NULL,
  `voucher_no` varchar(255) DEFAULT NULL,
  `voucher_type_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_61db5q9i22s9f79fojjml8x3e` (`company_id`),
  KEY `FK_j8uxncnicvlb1sdy4jsqnbfk7` (`last_updated_by_id`),
  KEY `FK_d8tegno7x8vka2lgltf3um48g` (`party_name_id`),
  KEY `FK_hql2nip1g7tn2r4ouobc9klld` (`sales_ledger_id`),
  KEY `FK_9pc9r97hw8ubc9fij01eqbnem` (`voucher_id`),
  KEY `FK_ougwo5b25nkcmpx2qfyax3v8b` (`voucher_type_id`),
  CONSTRAINT `FK_61db5q9i22s9f79fojjml8x3e` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_9pc9r97hw8ubc9fij01eqbnem` FOREIGN KEY (`voucher_id`) REFERENCES `voucher` (`id`),
  CONSTRAINT `FK_d8tegno7x8vka2lgltf3um48g` FOREIGN KEY (`party_name_id`) REFERENCES `account_ledger` (`id`),
  CONSTRAINT `FK_hql2nip1g7tn2r4ouobc9klld` FOREIGN KEY (`sales_ledger_id`) REFERENCES `account_ledger` (`id`),
  CONSTRAINT `FK_j8uxncnicvlb1sdy4jsqnbfk7` FOREIGN KEY (`last_updated_by_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_ougwo5b25nkcmpx2qfyax3v8b` FOREIGN KEY (`voucher_type_id`) REFERENCES `voucher_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;

/*Data for the table `voucher` */

insert  into `voucher`(`id`,`version`,`amount`,`amount_status`,`buyer_address`,`buyers`,`company_id`,`cosignee_address`,`cosignee_name`,`date`,`date_created`,`delivery_note_no`,`despatch_doc_no`,`despatch_through`,`destination`,`is_post_dated`,`last_updated`,`last_updated_by_id`,`mode_trems_of_payment`,`narration`,`oredr_no`,`other_reference`,`party_name_id`,`rate`,`reference_no`,`row_status`,`sales_ledger_id`,`t_in_sales_tax_no`,`terms_of_delivery`,`unique_key`,`voucher_id`,`voucher_no`,`voucher_type_id`) values (1,0,'1050.00','Cr',NULL,NULL,3,NULL,NULL,'2015-04-01','2015-06-13 12:54:14',NULL,NULL,NULL,NULL,'\0','2015-06-13 12:54:14',3,NULL,NULL,NULL,NULL,30,'0.00',NULL,NULL,NULL,NULL,NULL,1,NULL,'0',17),(2,0,'900.00','Dr',NULL,NULL,3,NULL,NULL,'2015-04-01','2015-06-13 12:54:14',NULL,NULL,NULL,NULL,'\0','2015-06-13 12:54:14',3,NULL,NULL,NULL,NULL,23,'0.00',NULL,NULL,NULL,NULL,NULL,2,1,NULL,NULL),(3,0,'80.00','Dr',NULL,NULL,3,NULL,NULL,'2015-04-01','2015-06-13 12:54:14',NULL,NULL,NULL,NULL,'\0','2015-06-13 12:54:14',3,NULL,NULL,NULL,NULL,28,'0.00',NULL,NULL,NULL,NULL,NULL,3,1,NULL,NULL),(4,0,'70.00','Dr',NULL,NULL,3,NULL,NULL,'2015-04-01','2015-06-13 12:54:15',NULL,NULL,NULL,NULL,'\0','2015-06-13 12:54:15',3,NULL,NULL,NULL,NULL,25,'0.00',NULL,NULL,NULL,NULL,NULL,4,1,NULL,NULL),(5,0,'1050.00','Cr',NULL,NULL,5,NULL,NULL,'2015-06-04','2015-06-13 16:10:21',NULL,NULL,NULL,NULL,'\0','2015-06-13 16:10:21',4,NULL,NULL,NULL,NULL,45,'0.00',NULL,NULL,NULL,NULL,NULL,5,NULL,'123',33),(6,0,'1000.00','Dr',NULL,NULL,5,NULL,NULL,'2015-06-04','2015-06-13 16:10:21',NULL,NULL,NULL,NULL,'\0','2015-06-13 16:10:21',4,NULL,NULL,NULL,NULL,41,'0.00',NULL,NULL,NULL,NULL,NULL,6,5,NULL,NULL),(7,0,'50.00','Dr',NULL,NULL,5,NULL,NULL,'2015-06-04','2015-06-13 16:10:21',NULL,NULL,NULL,NULL,'\0','2015-06-13 16:10:21',4,NULL,NULL,NULL,NULL,43,'0.00',NULL,NULL,NULL,NULL,NULL,7,5,NULL,NULL),(8,0,'5000.00','Dr',NULL,NULL,5,NULL,NULL,'2015-06-10','2015-06-13 16:11:44',NULL,NULL,NULL,NULL,'\0','2015-06-13 16:11:44',4,NULL,NULL,NULL,NULL,46,'0.00',NULL,NULL,NULL,NULL,NULL,8,NULL,'0',38),(9,0,'4500.00','Cr',NULL,NULL,5,NULL,NULL,'2015-06-10','2015-06-13 16:11:44',NULL,NULL,NULL,NULL,'\0','2015-06-13 16:11:44',4,NULL,NULL,NULL,NULL,42,'0.00',NULL,NULL,NULL,NULL,NULL,9,8,NULL,NULL),(10,0,'500.00','Cr',NULL,NULL,5,NULL,NULL,'2015-06-10','2015-06-13 16:11:44',NULL,NULL,NULL,NULL,'\0','2015-06-13 16:11:44',4,NULL,NULL,NULL,NULL,44,'0.00',NULL,NULL,NULL,NULL,NULL,10,8,NULL,NULL),(11,0,'1000.00','Dr',NULL,NULL,5,NULL,NULL,'2015-05-10','2015-06-13 16:13:17',NULL,NULL,NULL,NULL,'\0','2015-06-13 16:13:17',4,NULL,'PAYMENT MADE',NULL,NULL,45,'0.00',NULL,NULL,NULL,NULL,NULL,11,NULL,'0',36),(12,0,'1000.00','Cr',NULL,NULL,5,NULL,NULL,'2015-05-10','2015-06-13 16:13:17',NULL,NULL,NULL,NULL,'\0','2015-06-13 16:13:17',4,NULL,NULL,NULL,NULL,47,'0.00',NULL,NULL,NULL,NULL,NULL,12,11,NULL,NULL),(13,0,'5000.00','Cr',NULL,NULL,5,NULL,NULL,'2015-06-09','2015-06-13 16:14:03',NULL,NULL,NULL,NULL,'\0','2015-06-13 16:14:03',4,NULL,NULL,NULL,NULL,46,'0.00',NULL,NULL,NULL,NULL,NULL,13,NULL,'0',37),(14,0,'5000.00','Dr',NULL,NULL,5,NULL,NULL,'2015-06-09','2015-06-13 16:14:03',NULL,NULL,NULL,NULL,'\0','2015-06-13 16:14:03',4,NULL,NULL,NULL,NULL,47,'0.00',NULL,NULL,NULL,NULL,NULL,14,13,NULL,NULL),(15,0,'1000.00','Dr',NULL,NULL,5,NULL,NULL,'2015-06-05','2015-06-13 16:19:37',NULL,NULL,NULL,NULL,'\0','2015-06-13 16:19:37',4,NULL,'commission paid',NULL,NULL,48,'0.00',NULL,NULL,NULL,NULL,NULL,15,NULL,'0',35),(17,0,'1000.00','Dr',NULL,NULL,5,NULL,NULL,'2015-06-10','2015-06-13 16:20:29',NULL,NULL,NULL,NULL,'\0','2015-06-13 16:20:29',4,NULL,NULL,NULL,NULL,48,'0.00',NULL,NULL,NULL,NULL,NULL,17,NULL,'2',36),(18,0,'1000.00','Cr',NULL,NULL,5,NULL,NULL,'2015-06-10','2015-06-13 16:20:29',NULL,NULL,NULL,NULL,'\0','2015-06-13 16:20:29',4,NULL,NULL,NULL,NULL,47,'0.00',NULL,NULL,NULL,NULL,NULL,18,17,NULL,NULL),(19,0,'500.00','Dr',NULL,NULL,5,NULL,NULL,'2015-06-12','2015-06-13 17:14:50',NULL,NULL,NULL,NULL,'\0','2015-06-13 17:14:50',4,NULL,NULL,NULL,NULL,44,'0.00',NULL,NULL,NULL,NULL,NULL,19,NULL,'2',35),(21,0,'50.00','Dr',NULL,NULL,5,NULL,NULL,'2015-06-12','2015-06-13 17:15:56',NULL,NULL,NULL,NULL,'\0','2015-06-13 17:15:56',4,NULL,NULL,NULL,NULL,43,'0.00',NULL,NULL,NULL,NULL,NULL,21,NULL,'3',35),(27,0,'500.00','Cr',NULL,NULL,5,NULL,NULL,'2015-06-12','2015-06-13 17:22:35',NULL,NULL,NULL,NULL,'\0','2015-06-13 17:22:35',4,NULL,NULL,NULL,NULL,51,'0.00',NULL,NULL,NULL,NULL,NULL,23,19,NULL,NULL),(29,0,'1000.00','Dr',NULL,NULL,5,NULL,NULL,'2015-05-12','2015-06-13 17:39:20',NULL,NULL,NULL,NULL,'\0','2015-06-13 17:39:20',4,NULL,NULL,NULL,NULL,48,'0.00',NULL,NULL,NULL,NULL,NULL,25,NULL,'3',36),(30,0,'1000.00','Cr',NULL,NULL,5,NULL,NULL,'2015-05-12','2015-06-13 17:39:20',NULL,NULL,NULL,NULL,'\0','2015-06-13 17:39:20',4,NULL,NULL,NULL,NULL,47,'0.00',NULL,NULL,NULL,NULL,NULL,26,29,NULL,NULL),(32,0,'1000.00','Cr',NULL,NULL,5,NULL,NULL,'2015-06-05','2015-06-13 17:45:51',NULL,NULL,NULL,NULL,'\0','2015-06-13 17:45:51',4,NULL,NULL,NULL,NULL,48,'0.00',NULL,NULL,NULL,NULL,NULL,27,15,NULL,NULL),(33,0,'50.00','Cr',NULL,NULL,5,NULL,NULL,'2015-06-12','2015-06-13 17:46:52',NULL,NULL,NULL,NULL,'\0','2015-06-13 17:46:52',4,NULL,NULL,NULL,NULL,43,'0.00',NULL,NULL,NULL,NULL,NULL,28,21,NULL,NULL);

/*Table structure for table `voucher_bill_details` */

DROP TABLE IF EXISTS `voucher_bill_details`;

CREATE TABLE `voucher_bill_details` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `amount` decimal(19,2) NOT NULL,
  `amount_status` varchar(255) DEFAULT NULL,
  `bill_date` date DEFAULT NULL,
  `bill_no` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) NOT NULL,
  `cr_days` decimal(19,2) NOT NULL,
  `date_created` datetime NOT NULL,
  `last_updated` datetime NOT NULL,
  `last_updated_by_id` bigint(20) NOT NULL,
  `narration` varchar(255) DEFAULT NULL,
  `party_name_id` bigint(20) DEFAULT NULL,
  `type_of_ref_id` bigint(20) DEFAULT NULL,
  `unique_key` int(11) NOT NULL,
  `voucher_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ib64o8jmp1nhytil4t5wniax3` (`company_id`),
  KEY `FK_l6f0ld8dk0j9t3fcqph25gbsr` (`last_updated_by_id`),
  KEY `FK_ahlu6fe6gmfnhbouo21vc74he` (`party_name_id`),
  KEY `FK_aeew1w7uu5945sninxkk22498` (`type_of_ref_id`),
  KEY `FK_lfppav4f7buftcl700vjdrww0` (`voucher_id`),
  CONSTRAINT `FK_aeew1w7uu5945sninxkk22498` FOREIGN KEY (`type_of_ref_id`) REFERENCES `account_flag` (`id`),
  CONSTRAINT `FK_ahlu6fe6gmfnhbouo21vc74he` FOREIGN KEY (`party_name_id`) REFERENCES `account_ledger` (`id`),
  CONSTRAINT `FK_ib64o8jmp1nhytil4t5wniax3` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_l6f0ld8dk0j9t3fcqph25gbsr` FOREIGN KEY (`last_updated_by_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_lfppav4f7buftcl700vjdrww0` FOREIGN KEY (`voucher_id`) REFERENCES `voucher` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `voucher_bill_details` */

/*Table structure for table `voucher_details` */

DROP TABLE IF EXISTS `voucher_details`;

CREATE TABLE `voucher_details` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `amount` decimal(19,2) NOT NULL,
  `amount_status` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) NOT NULL,
  `narration` varchar(2048) DEFAULT NULL,
  `particulars_id` bigint(20) DEFAULT NULL,
  `rate` decimal(19,2) NOT NULL,
  `unique_key` int(11) NOT NULL,
  `voucher_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_b15t5n3ytr8rsx8jfrknpdu6n` (`company_id`),
  KEY `FK_gvdvipuxfs5ox4b46iww8e16s` (`particulars_id`),
  KEY `FK_2vcxkvnfh3c32ca7cx35frbkv` (`voucher_id`),
  CONSTRAINT `FK_2vcxkvnfh3c32ca7cx35frbkv` FOREIGN KEY (`voucher_id`) REFERENCES `voucher` (`id`),
  CONSTRAINT `FK_b15t5n3ytr8rsx8jfrknpdu6n` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_gvdvipuxfs5ox4b46iww8e16s` FOREIGN KEY (`particulars_id`) REFERENCES `account_ledger` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `voucher_details` */

/*Table structure for table `voucher_status` */

DROP TABLE IF EXISTS `voucher_status`;

CREATE TABLE `voucher_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `child_label` varchar(255) DEFAULT NULL,
  `child_status` varchar(255) DEFAULT NULL,
  `parent_label` varchar(255) DEFAULT NULL,
  `parent_status` varchar(255) DEFAULT NULL,
  `voucher_property` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `voucher_status` */

insert  into `voucher_status`(`id`,`version`,`child_label`,`child_status`,`parent_label`,`parent_status`,`voucher_property`) values (1,0,'Credit','Cr','Amount(Dr)','Dr','Sale'),(2,0,'Debit','Dr','Amount(Cr)','Cr','Contra'),(3,0,'Credit','Cr','Amount(Dr)','Dr','Journal'),(4,0,'Credit','Cr','Amount(Dr)','Dr','Payment'),(5,0,'Debit','Dr','Amount(Cr)','Cr','Receipt'),(6,0,'Debit','Dr','Amount(Cr)','Cr','Purchase'),(7,0,'Debit','Dr','Amount(Cr)','Cr','Credit Note'),(8,0,'Credit','cr','Amount(Dr)','Dr','Debit Note');

/*Table structure for table `voucher_type` */

DROP TABLE IF EXISTS `voucher_type`;

CREATE TABLE `voucher_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `alias` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) NOT NULL,
  `date_created` datetime NOT NULL,
  `default_print_title` varchar(255) DEFAULT NULL,
  `is_prevent_duplicates` bit(1) NOT NULL,
  `is_type_update` bit(1) NOT NULL,
  `last_number` int(11) NOT NULL,
  `last_updated` datetime NOT NULL,
  `last_updated_by_id` bigint(20) NOT NULL,
  `method_of_voucher_numbering` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `narration_for_each_entry` bit(1) NOT NULL,
  `prefix_with_zero` int(11) NOT NULL,
  `print_after_saving_voucher` bit(1) NOT NULL,
  `property` varchar(255) DEFAULT NULL,
  `start_number` int(11) NOT NULL,
  `type_of_voucher_id` bigint(20) DEFAULT NULL,
  `unique_key` int(11) NOT NULL,
  `use_adavance_configuration` bit(1) NOT NULL,
  `use_common_narration` bit(1) NOT NULL,
  `use_effective_dates_for_vouchers` bit(1) NOT NULL,
  `with_of_numerical_part` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_qqfeupqj1ijae9j4ibx830ho1` (`company_id`),
  KEY `FK_64awv726kcvo6blbjqc4dqapx` (`last_updated_by_id`),
  KEY `FK_9kfiff7pb87v29q85jhef9jjf` (`type_of_voucher_id`),
  CONSTRAINT `FK_64awv726kcvo6blbjqc4dqapx` FOREIGN KEY (`last_updated_by_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_9kfiff7pb87v29q85jhef9jjf` FOREIGN KEY (`type_of_voucher_id`) REFERENCES `voucher_type` (`id`),
  CONSTRAINT `FK_qqfeupqj1ijae9j4ibx830ho1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=latin1;

/*Data for the table `voucher_type` */

insert  into `voucher_type`(`id`,`version`,`alias`,`company_id`,`date_created`,`default_print_title`,`is_prevent_duplicates`,`is_type_update`,`last_number`,`last_updated`,`last_updated_by_id`,`method_of_voucher_numbering`,`name`,`narration_for_each_entry`,`prefix_with_zero`,`print_after_saving_voucher`,`property`,`start_number`,`type_of_voucher_id`,`unique_key`,`use_adavance_configuration`,`use_common_narration`,`use_effective_dates_for_vouchers`,`with_of_numerical_part`) values (1,0,NULL,1,'2015-05-15 19:13:16','Purchase Invoice','\0','\0',0,'2015-05-15 19:13:16',1,'1','Purchase','\0',0,'\0','Purchase',0,NULL,1,'\0','','\0',0),(2,0,NULL,1,'2015-05-15 19:13:16','Contra Invoice','\0','\0',0,'2015-05-15 19:13:16',1,'1','Contra','\0',0,'\0','Contra',0,NULL,2,'\0','','\0',0),(3,0,NULL,1,'2015-05-15 19:13:16','Journal Invoice','\0','\0',0,'2015-05-15 19:13:16',1,'1','Journal','\0',0,'\0','Journal',0,NULL,3,'\0','','\0',0),(4,0,NULL,1,'2015-05-15 19:13:16','Payment Voucher','\0','\0',0,'2015-05-15 19:13:16',1,'1','Payment','\0',0,'\0','Payment',0,NULL,4,'\0','','\0',0),(5,0,NULL,1,'2015-05-15 19:13:16','Receipt Voucher','\0','\0',0,'2015-05-15 19:13:16',1,'1','Receipt','\0',0,'\0','Receipt',0,NULL,5,'\0','','\0',0),(6,0,NULL,1,'2015-05-15 19:13:16','Sale Invoice','\0','\0',0,'2015-05-15 19:13:16',1,'1','Sale','',0,'\0','Sale',0,NULL,6,'\0','\0','\0',0),(7,0,NULL,1,'2015-05-15 19:13:16','Credit Note','\0','\0',0,'2015-05-15 19:13:16',1,'1','Credit Note','',0,'\0','Credit Note',0,NULL,7,'\0','\0','\0',0),(8,0,NULL,1,'2015-05-15 19:13:16','Debit Note','\0','\0',0,'2015-05-15 19:13:16',1,'1','Debit Note','',0,'\0','Debit Note',0,NULL,8,'\0','\0','\0',0),(9,0,NULL,2,'2015-05-15 19:49:08','Purchase Invoice','\0','\0',0,'2015-05-15 19:49:08',1,'1','Purchase','\0',0,'\0','Purchase',0,NULL,9,'\0','','\0',0),(10,0,NULL,2,'2015-05-15 19:49:08','Contra Invoice','\0','\0',0,'2015-05-15 19:49:08',1,'1','Contra','\0',0,'\0','Contra',0,NULL,10,'\0','','\0',0),(11,0,NULL,2,'2015-05-15 19:49:08','Journal Invoice','\0','\0',0,'2015-05-15 19:49:08',1,'1','Journal','\0',0,'\0','Journal',0,NULL,11,'\0','','\0',0),(12,0,NULL,2,'2015-05-15 19:49:08','Payment Voucher','\0','\0',0,'2015-05-15 19:49:08',1,'1','Payment','\0',0,'\0','Payment',0,NULL,12,'\0','','\0',0),(13,0,NULL,2,'2015-05-15 19:49:08','Receipt Voucher','\0','\0',0,'2015-05-15 19:49:08',1,'1','Receipt','\0',0,'\0','Receipt',0,NULL,13,'\0','','\0',0),(14,0,NULL,2,'2015-05-15 19:49:08','Sale Invoice','\0','\0',0,'2015-05-15 19:49:08',1,'1','Sale','',0,'\0','Sale',0,NULL,14,'\0','\0','\0',0),(15,0,NULL,2,'2015-05-15 19:49:08','Credit Note','\0','\0',0,'2015-05-15 19:49:08',1,'1','Credit Note','',0,'\0','Credit Note',0,NULL,15,'\0','\0','\0',0),(16,0,NULL,2,'2015-05-15 19:49:08','Debit Note','\0','\0',0,'2015-05-15 19:49:08',1,'1','Debit Note','',0,'\0','Debit Note',0,NULL,16,'\0','\0','\0',0),(17,1,NULL,3,'2015-05-31 12:47:26','Purchase Invoice','\0','\0',1,'2015-06-13 12:54:15',2,'1','Purchase','\0',0,'\0','Purchase',0,NULL,17,'\0','','\0',0),(18,0,NULL,3,'2015-05-31 12:47:26','Contra Invoice','\0','\0',0,'2015-05-31 12:47:26',2,'1','Contra','\0',0,'\0','Contra',0,NULL,18,'\0','','\0',0),(19,0,NULL,3,'2015-05-31 12:47:26','Journal Invoice','\0','\0',0,'2015-05-31 12:47:26',2,'1','Journal','\0',0,'\0','Journal',0,NULL,19,'\0','','\0',0),(20,0,NULL,3,'2015-05-31 12:47:26','Payment Voucher','\0','\0',0,'2015-05-31 12:47:26',2,'1','Payment','\0',0,'\0','Payment',0,NULL,20,'\0','','\0',0),(21,0,NULL,3,'2015-05-31 12:47:26','Receipt Voucher','\0','\0',0,'2015-05-31 12:47:26',2,'1','Receipt','\0',0,'\0','Receipt',0,NULL,21,'\0','','\0',0),(22,0,NULL,3,'2015-05-31 12:47:26','Sale Invoice','\0','\0',0,'2015-05-31 12:47:26',2,'1','Sale','',0,'\0','Sale',0,NULL,22,'\0','\0','\0',0),(23,0,NULL,3,'2015-05-31 12:47:26','Credit Note','\0','\0',0,'2015-05-31 12:47:26',2,'1','Credit Note','',0,'\0','Credit Note',0,NULL,23,'\0','\0','\0',0),(24,0,NULL,3,'2015-05-31 12:47:26','Debit Note','\0','\0',0,'2015-05-31 12:47:26',2,'1','Debit Note','',0,'\0','Debit Note',0,NULL,24,'\0','\0','\0',0),(25,0,NULL,4,'2015-05-31 13:04:46','Purchase Invoice','\0','\0',0,'2015-05-31 13:04:46',1,'1','Purchase','\0',0,'\0','Purchase',0,NULL,25,'\0','','\0',0),(26,0,NULL,4,'2015-05-31 13:04:46','Contra Invoice','\0','\0',0,'2015-05-31 13:04:46',1,'1','Contra','\0',0,'\0','Contra',0,NULL,26,'\0','','\0',0),(27,0,NULL,4,'2015-05-31 13:04:46','Journal Invoice','\0','\0',0,'2015-05-31 13:04:46',1,'1','Journal','\0',0,'\0','Journal',0,NULL,27,'\0','','\0',0),(28,0,NULL,4,'2015-05-31 13:04:46','Payment Voucher','\0','\0',0,'2015-05-31 13:04:46',1,'1','Payment','\0',0,'\0','Payment',0,NULL,28,'\0','','\0',0),(29,0,NULL,4,'2015-05-31 13:04:46','Receipt Voucher','\0','\0',0,'2015-05-31 13:04:46',1,'1','Receipt','\0',0,'\0','Receipt',0,NULL,29,'\0','','\0',0),(30,0,NULL,4,'2015-05-31 13:04:46','Sale Invoice','\0','\0',0,'2015-05-31 13:04:46',1,'1','Sale','',0,'\0','Sale',0,NULL,30,'\0','\0','\0',0),(31,0,NULL,4,'2015-05-31 13:04:46','Credit Note','\0','\0',0,'2015-05-31 13:04:46',1,'1','Credit Note','',0,'\0','Credit Note',0,NULL,31,'\0','\0','\0',0),(32,0,NULL,4,'2015-05-31 13:04:46','Debit Note','\0','\0',0,'2015-05-31 13:04:46',1,'1','Debit Note','',0,'\0','Debit Note',0,NULL,32,'\0','\0','\0',0),(33,1,NULL,5,'2015-06-13 15:27:44','Purchase Invoice','\0','\0',0,'2015-06-13 16:01:17',1,'2','Purchase','\0',0,'\0','Purchase',1,NULL,33,'\0','','\0',0),(34,0,NULL,5,'2015-06-13 15:27:44','Contra Invoice','\0','\0',0,'2015-06-13 15:27:44',1,'1','Contra','\0',0,'\0','Contra',0,NULL,34,'\0','','\0',0),(35,3,NULL,5,'2015-06-13 15:27:44','Journal Invoice','\0','\0',3,'2015-06-13 17:15:56',1,'1','Journal','\0',0,'\0','Journal',0,NULL,35,'\0','','\0',0),(36,3,NULL,5,'2015-06-13 15:27:44','Payment Voucher','\0','\0',3,'2015-06-13 17:39:20',1,'1','Payment','\0',0,'\0','Payment',0,NULL,36,'\0','','\0',0),(37,1,NULL,5,'2015-06-13 15:27:44','Receipt Voucher','\0','\0',1,'2015-06-13 16:14:03',1,'1','Receipt','\0',0,'\0','Receipt',0,NULL,37,'\0','','\0',0),(38,1,NULL,5,'2015-06-13 15:27:44','Sale Invoice','\0','\0',1,'2015-06-13 16:11:44',1,'1','Sale','',0,'\0','Sale',0,NULL,38,'\0','\0','\0',0),(39,0,NULL,5,'2015-06-13 15:27:44','Credit Note','\0','\0',0,'2015-06-13 15:27:44',1,'1','Credit Note','',0,'\0','Credit Note',0,NULL,39,'\0','\0','\0',0),(40,0,NULL,5,'2015-06-13 15:27:44','Debit Note','\0','\0',0,'2015-06-13 15:27:44',1,'1','Debit Note','',0,'\0','Debit Note',0,NULL,40,'\0','\0','\0',0),(41,0,NULL,6,'2015-06-13 17:52:39','Purchase Invoice','\0','\0',0,'2015-06-13 17:52:39',4,'1','Purchase','\0',0,'\0','Purchase',0,NULL,41,'\0','','\0',0),(42,0,NULL,6,'2015-06-13 17:52:39','Contra Invoice','\0','\0',0,'2015-06-13 17:52:39',4,'1','Contra','\0',0,'\0','Contra',0,NULL,42,'\0','','\0',0),(43,0,NULL,6,'2015-06-13 17:52:39','Journal Invoice','\0','\0',0,'2015-06-13 17:52:39',4,'1','Journal','\0',0,'\0','Journal',0,NULL,43,'\0','','\0',0),(44,0,NULL,6,'2015-06-13 17:52:39','Payment Voucher','\0','\0',0,'2015-06-13 17:52:39',4,'1','Payment','\0',0,'\0','Payment',0,NULL,44,'\0','','\0',0),(45,0,NULL,6,'2015-06-13 17:52:39','Receipt Voucher','\0','\0',0,'2015-06-13 17:52:39',4,'1','Receipt','\0',0,'\0','Receipt',0,NULL,45,'\0','','\0',0),(46,0,NULL,6,'2015-06-13 17:52:39','Sale Invoice','\0','\0',0,'2015-06-13 17:52:39',4,'1','Sale','',0,'\0','Sale',0,NULL,46,'\0','\0','\0',0),(47,0,NULL,6,'2015-06-13 17:52:39','Credit Note','\0','\0',0,'2015-06-13 17:52:39',4,'1','Credit Note','',0,'\0','Credit Note',0,NULL,47,'\0','\0','\0',0),(48,0,NULL,6,'2015-06-13 17:52:39','Debit Note','\0','\0',0,'2015-06-13 17:52:39',4,'1','Debit Note','',0,'\0','Debit Note',0,NULL,48,'\0','\0','\0',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

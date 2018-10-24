/*
SQLyog Enterprise - MySQL GUI v7.02 
MySQL - 5.0.22-community-nt : Database - cosmeticdb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`cosmeticdb` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `cosmeticdb`;

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `CustomerID` int(8) NOT NULL auto_increment,
  `CustomerName` varchar(20) NOT NULL,
  `Telephone` varchar(20) default NULL,
  `CutomerAddress` varchar(100) default NULL,
  PRIMARY KEY  (`CustomerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `customer` */

insert  into `customer`(`CustomerID`,`CustomerName`,`Telephone`,`CutomerAddress`) values (1,'普通客户','',''),(3,'ana','13922893019','深圳'),(4,'anny','13933199092','香港');

/*Table structure for table `deliverydetail` */

DROP TABLE IF EXISTS `deliverydetail`;

CREATE TABLE `deliverydetail` (
  `DeliveryID` varchar(8) NOT NULL,
  `ProductID` varchar(8) NOT NULL,
  `SalesQuantity` int(11) NOT NULL default '1',
  `SalesPrice` double NOT NULL,
  KEY `FK_deliveryID` (`DeliveryID`),
  KEY `FK_deliverydetail_productID` (`ProductID`),
  CONSTRAINT `FK_deliverydetail_productID` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`),
  CONSTRAINT `FK_deliveryID` FOREIGN KEY (`DeliveryID`) REFERENCES `deliverymaster` (`DeliveryID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `deliverydetail` */

/*Table structure for table `deliverymaster` */

DROP TABLE IF EXISTS `deliverymaster`;

CREATE TABLE `deliverymaster` (
  `DeliveryID` varchar(8) NOT NULL,
  `DeliveryProperty` int(11) NOT NULL default '1',
  `DeliveryDate` varchar(20) NOT NULL,
  `SalesManID` varchar(8) NOT NULL,
  `CustomerID` int(8) NOT NULL,
  `SubTotal` decimal(18,3) NOT NULL default '0.000',
  PRIMARY KEY  (`DeliveryID`),
  KEY `FK_userID` (`SalesManID`),
  KEY `FK_deliverymaster` (`CustomerID`),
  CONSTRAINT `FK_deliverymaster` FOREIGN KEY (`CustomerID`) REFERENCES `customer` (`CustomerID`),
  CONSTRAINT `FK_userID` FOREIGN KEY (`SalesManID`) REFERENCES `users` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `deliverymaster` */

/*Table structure for table `losespill` */

DROP TABLE IF EXISTS `losespill`;

CREATE TABLE `losespill` (
  `LoseSpillID` varchar(8) NOT NULL,
  `ProductID` varchar(8) NOT NULL default '',
  `CheckDate` varchar(20) NOT NULL,
  `Counts` int(11) NOT NULL,
  `Flags` int(11) NOT NULL,
  PRIMARY KEY  (`LoseSpillID`),
  KEY `FK_losespill_productID` (`ProductID`),
  CONSTRAINT `FK_losespill_productID` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `losespill` */

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `ProductID` varchar(8) NOT NULL,
  `ProductName` varchar(30) NOT NULL,
  `SupplierID` varchar(8) NOT NULL,
  `ProductPrice` decimal(18,3) default NULL,
  `Quantity` int(11) default NULL,
  `MaxSafeStock` int(11) NOT NULL default '0',
  `MinSafeStock` int(11) NOT NULL default '0',
  PRIMARY KEY  (`ProductID`),
  KEY `FK_product` (`SupplierID`),
  CONSTRAINT `FK_product` FOREIGN KEY (`SupplierID`) REFERENCES `supplier` (`SupplierID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `product` */

insert  into `product`(`ProductID`,`ProductName`,`SupplierID`,`ProductPrice`,`Quantity`,`MaxSafeStock`,`MinSafeStock`) values ('pro0001','洗面奶','1','0.000',0,50,20),('pro0002','乳液','1','0.000',20,50,10);

/*Table structure for table `purchasedetail` */

DROP TABLE IF EXISTS `purchasedetail`;

CREATE TABLE `purchasedetail` (
  `PurchaseID` varchar(8) NOT NULL,
  `ProductID` varchar(8) NOT NULL,
  `PurchaseQuantity` int(11) NOT NULL,
  `PurchaseUnitPrice` decimal(18,3) NOT NULL,
  KEY `FK_purchaseID` (`PurchaseID`),
  KEY `FK_productID` (`ProductID`),
  CONSTRAINT `FK_productID` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`),
  CONSTRAINT `FK_purchaseID` FOREIGN KEY (`PurchaseID`) REFERENCES `purchasemaster` (`PurchaseID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `purchasedetail` */

/*Table structure for table `purchasemaster` */

DROP TABLE IF EXISTS `purchasemaster`;

CREATE TABLE `purchasemaster` (
  `PurchaseID` varchar(8) NOT NULL,
  `PurchaseDate` varchar(12) NOT NULL,
  `SupplierID` varchar(8) NOT NULL,
  `PurchaseProperty` int(11) NOT NULL default '1',
  `SubTotal` decimal(18,3) NOT NULL default '0.000',
  PRIMARY KEY  (`PurchaseID`),
  KEY `FK_supplierID` (`SupplierID`),
  CONSTRAINT `FK_supplierID` FOREIGN KEY (`SupplierID`) REFERENCES `supplier` (`SupplierID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `purchasemaster` */

/*Table structure for table `supplier` */

DROP TABLE IF EXISTS `supplier`;

CREATE TABLE `supplier` (
  `SupplierID` varchar(8) NOT NULL,
  `SupplierName` varchar(60) NOT NULL,
  `Telephone` varchar(20) NOT NULL,
  `CompanyAddress` varchar(100) NOT NULL,
  PRIMARY KEY  (`SupplierID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `supplier` */

insert  into `supplier`(`SupplierID`,`SupplierName`,`Telephone`,`CompanyAddress`) values ('1','百雀灵','13833181993','石家庄'),('2','自然堂','15088569874','华安东路18号');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `UserID` varchar(8) NOT NULL,
  `UserName` varchar(128) NOT NULL,
  `PasswordCode` char(32) NOT NULL,
  `UserAuthority` int(11) NOT NULL,
  PRIMARY KEY  (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `users` */

insert  into `users`(`UserID`,`UserName`,`PasswordCode`,`UserAuthority`) values ('0001','admin','admin',0),('0002','sales','sales',1),('0003','purchase','purchase',2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;

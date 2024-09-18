-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: inventorya
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `barcode` varchar(1000) DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `categoryId` int NOT NULL,
  `supplierId` int DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `price` double NOT NULL,
  `quantity` double NOT NULL,
  `description` text NOT NULL,
  `entrydate` datetime DEFAULT CURRENT_TIMESTAMP,
  `variantProd_gms` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `categoryId` (`categoryId`),
  KEY `supplierId` (`supplierId`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`categoryId`) REFERENCES `categories` (`id`),
  CONSTRAINT `products_ibfk_2` FOREIGN KEY (`supplierId`) REFERENCES `suppliers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES ('8900500000017',1,1,1,'Clinical Solutions',165,9,'Anti-dandruff shampoo','2023-12-13 11:47:17',0),('8900500000024',2,1,1,'Classic Clean',120,11,'General shampoo',NULL,0),('8900500000031',3,4,2,'HiColor',190,22,'Red Color 50g box','2024-03-19 00:00:00',0),('8900500000048',4,2,2,'Wax',65,28,'Hair wax','2024-03-18 00:00:00',0),('8900500000055',5,3,3,'Power Light',70,22,'Freshness Cream','2024-03-15 00:00:00',0),('8900500000062',6,3,3,'Oil Clear',160,9,'Face Wash','2024-03-19 00:00:00',0),('8900500000079',7,3,6,'Brylcreem (Red)',300,15,'Light glossy hold product','2024-03-19 00:00:00',0),('8900500000086',8,3,1,'Brylcreem (Green)',105,8,'Anti-dandruff','2024-03-18 00:00:00',0),('8900500000093',9,1,8,'Sunsilk',5,38,'SunSilk shampoo 8gm','2023-12-12 18:03:34',0),('8900500000109',10,5,10,'wired Headset',140,10,'wired headset','2023-12-12 18:05:48',0),('8900500000116',11,5,9,'mobile',7500,3,'mobile phone ','2023-12-12 18:12:17',0),('8900500000123',12,5,9,'laptop',100000,5,'laptop','2024-03-19 00:00:00',0),('8900500000130',13,5,16,'demo',100,11,'laptops windoers','2023-12-15 18:27:05',0),('8900500000147',14,5,16,'testing',1000,7,'keypad mobile','2023-12-15 18:39:02',0),('8900500000161',16,8,25,'salt',18,8,'Ashirwad Salt ','2023-12-17 13:35:25',0),('8900500000178',17,5,16,'testing_import_file',17249,3,'Oneplus Nord CE 3 lite ','2023-12-17 20:19:21',0),('8900500000239',23,5,12,'watch',2500,8,'smartwatch','2023-12-18 11:11:47',0),('8900500000246',24,5,22,'Second workspace Testing 2nd time',17249,4,'Oneplus Nord CE 3 lite Black color','2023-12-18 12:03:06',0),('8900500000253',25,5,22,'mahesh_testing_import',18000,26,'Oneplus Nord CE 3 lite Black color','2023-12-18 14:01:31',0),('8900500000260',26,5,16,'checking_import_btn',36000,12,'Oneplus Nord CE 3 lite Ocean Blue color','2023-12-18 14:29:17',0),('8900500000277',27,5,16,'import_btn',36000,18,'Oneplus Nord CE 3 lite Ocean Blue color','2023-12-19 11:16:50',0),('8900500000352',35,5,16,'goodmorning',36000,17,'Oneplus Nord CE 3 lite Ocean Blue color','2023-12-24 00:17:57',0),('8900500000482',48,1,1,'product1',5,5,'3wt4eyu','2024-01-26 00:00:00',0),('8900500000499',49,1,1,'republic_day_product_adding',5,10,'awsdrtyfugihojpk[\'xdcfvbnj;','2024-01-26 00:00:00',0),('8900500000512',51,1,1,'prod1',1,2,'wqteyrt','2024-01-26 00:00:00',0),('8900500000536',53,1,1,'prod2',5,10,'product edit testing','2024-01-31 00:00:00',0),('8900500000550',55,5,8,'laptops_adding',50000,12,'laptops adding','2024-01-31 00:00:00',0),('8900500000581',58,8,8,'Pen',10,10,'Product adding','2024-01-31 00:00:00',0),('8900500000604',60,7,8,'add_product',10,11,'apparel products','2024-03-19 00:00:00',0),('8900500000628',62,5,12,'test_product',10,10,'testing product','2024-02-15 00:00:00',0),('8900500000642',64,5,8,'testing_barcode',150,5,'testing_barcode','2024-03-01 00:00:00',0),('8900500000659',65,5,8,'123',150,10,'aegsrh','2024-03-01 00:00:00',0),('8900500000680',68,5,8,'testing_5',65443,23,'dsgfhgjhgjk','2024-03-01 00:00:00',0),('8900500000697',69,5,8,'prod3',565,4,'sedfhgvhbj,','2024-03-01 00:00:00',0),('8900500000703',70,5,8,'prod4',4546,65,'srghfjghj','2024-03-01 00:00:00',0),('8900500000727',72,5,8,'testing_barcode_EAN13',150,10,'EAN13 testing','2024-03-20 00:00:00',0),('8900500000741',74,5,8,'prod5',366,5,'EAn_barcodes','2024-03-04 00:00:00',0),('8900500000758',75,5,8,'prod6',345,42,'retrytu','2024-03-04 00:00:00',0),('8900500000789',78,5,8,'admin',223,45,'admin\n','2024-03-04 00:00:00',0),('8900500000796',79,5,8,'product2',23456,10,'product2','2024-03-19 00:00:00',0),('8900500000826',82,5,8,'prod7',3434,2,'awesrdthfjygukg','2024-03-04 00:00:00',0),('8900500000833',83,5,8,'prod8',4500,1,'etfyjghkujklhgf','2024-03-04 00:00:00',0),('8900500000840',84,7,8,'Maggie ',14,1,'maggie instant 2 mins cook','2024-03-04 00:00:00',0),('8900500000857',85,5,8,'prod9',45,1,'ergthgg','2024-03-04 00:00:00',0),('8900500000864',86,5,8,'prod10',450,1,'aetrytkhluyt','2024-03-04 00:00:00',0),('8900500000871',87,5,8,'prod11',354345,2,'waretydtfyg','2024-03-04 00:00:00',0),('8900500000901',90,5,8,'prod12',344,2,'adsfgfhmh','2024-03-04 00:00:00',0),('8900500000918',91,5,8,'prod13',45,2,'dasdhfghjk','2024-03-04 00:00:00',0),('8900500001083',108,1,1,'p7',345,5,'dfbhh','2024-03-05 00:00:00',0),('8900500001106',110,1,1,'p5',35,35,'fdfdghgjkhghghf','2024-03-18 00:00:00',0),('8900500001120',112,1,1,'p6',25,2,'adfbnasdfagfh','2024-03-05 00:00:00',0),('8900500001137',113,5,8,'p4',30,3,'mhgndfbdsdasADDGFHJK','2024-03-05 00:00:00',0),('8900500001168',116,5,8,'last_time_prod_add_barcode_checking',250,1,'last_time_prod_add_barcode_checking','2024-03-05 00:00:00',0),('8900500001182',118,1,1,'p2',53246,2,'rwetrytuyiui','2024-03-05 00:00:00',0),('8900500001205',120,1,1,'p3',35,1,'w4e5ru6t','2024-03-05 00:00:00',0),('8900500001229',122,1,1,'p1',2,3,'fghfds','2024-03-19 00:00:00',0),('8900500001243',124,5,8,'testing_barcode_add',15,2,'testing_barcode_add','2024-03-07 00:00:00',0),('8900500001274',127,1,1,'new_product',15,5,'new prod description','2024-03-08 00:00:00',0),('8900500001281',128,8,25,'sugar',40,249,'sugar of 25500 gms ','2024-03-13 00:00:00',1000),('8900500001298',129,7,8,'Moong Dal',60,10,'Moong dal polished ','2024-03-14 00:00:00',1000),('8900500001304',130,8,16,'organic Tattva Kala Chana',102,3500,'organic Tattva Kala Chana 250gms','2024-03-14 00:00:00',250),('8900500001311',131,8,16,'Toor Dal',130,25000,'Toor dal Unpolished (Split)','2024-03-14 00:00:00',1000),('8900500001328',132,8,16,'Town Grocer White Urad dal',164,25250,'Town Grocer White Urda Dal (Split)','2024-03-14 00:00:00',1000),('8900500001335',133,8,16,'Safe Harvest Toor Dal',138,15000,'Safe Harvest Toor Dal 500gm','2024-03-14 00:00:00',500),('8900500001342',134,8,16,'Moong Dal popular Essentials 1kg',179,45000,'Moong Dal popular Essentials 1kg unpolished','2024-03-14 00:00:00',1000),('8900500001359',135,8,16,'Popular Essentials Kabuli Cana Premium Dal',140,55000,'Popular Essentials Kabuli Cana Premium','2024-03-14 00:00:00',1000),('8900500001366',136,8,16,'24 Mantra organic Roasted Bengal Gram Dal ',121,10500,' 24 Mantra organic Roasted Bengal Gram  500gm','2024-03-14 00:00:00',500),('8900500001373',137,8,16,'Popular Essentials chana Dal',129,55249,'Popular Essentials chana dal 1kg','2024-03-14 00:00:00',1000),('8900500001380',138,8,16,'24 Mantra organic Tur Dal',315,9498.75,'24 Mantra organic Tur Dal 1kg','2024-03-14 00:00:00',1000),('8900500001397',139,8,16,'Ashirwad Red Chilli Powder (250gm)',80,4,'Ashirwad Red Chilli Powder (250gm)- 80/- kg','2024-03-15 00:00:00',250),('8900500001663',166,5,8,'Product checking',125,2,'Product checking','2024-03-21 00:00:00',0),('8900500001724',172,5,8,'qwefew',25,5,'qewdfesdew','2024-03-21 00:00:00',0),('8900500001731',173,5,8,'qewertry',24,4,'qewre','2024-03-21 00:00:00',0),('8900500001748',174,5,8,'sdsfdgfgfhj',25,7,'zsdfg','2024-03-21 00:00:00',0),('8900500001755',175,5,8,'wqerwteryt',25,1,'waeg','2024-03-21 15:01:49',0),('8900500001762',176,5,8,'ytuyiluo;it',5,10,'ruyklgf','2024-03-21 15:03:43',0),('8900500001779',177,3,8,'1',3,3,'23rtw4eytrrt','2024-03-21 15:05:09',0),('8900500001786',178,5,8,'prod checking',100,1,'prod checking','2024-03-21 15:28:11',0);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-22 11:29:45

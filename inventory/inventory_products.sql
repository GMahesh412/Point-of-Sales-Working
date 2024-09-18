CREATE DATABASE  IF NOT EXISTS `inventory` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `inventory`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: inventory
-- ------------------------------------------------------
-- Server version	8.0.34

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
  `id` int NOT NULL AUTO_INCREMENT,
  `categoryId` int NOT NULL,
  `supplierId` int NOT NULL,
  `name` varchar(100) NOT NULL,
  `price` double NOT NULL,
  `quantity` double NOT NULL,
  `description` text NOT NULL,
  `entrydate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `categoryId` (`categoryId`),
  KEY `supplierId` (`supplierId`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`categoryId`) REFERENCES `categories` (`id`),
  CONSTRAINT `products_ibfk_2` FOREIGN KEY (`supplierId`) REFERENCES `suppliers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,1,1,'Clinical Solutions',165,3,'Anti-dandruff shampoo','2023-12-13 11:47:17'),(2,1,1,'Classic Clean',120,144,'General shampoo',NULL),(3,4,2,'HiColor',190,33,'Red Color 50g box',NULL),(4,2,2,'Wax',65,38,'Hair wax',NULL),(5,3,3,'Power Light',70,93,'Freshness Cream',NULL),(6,3,3,'Oil Clear',160,294,'Face Wash',NULL),(7,3,6,'Brylcreem (Red)',300,128,'Light glossy hold',NULL),(8,3,1,'Brylcreem (Green)',105,5,'Anti-dandruff',NULL),(9,1,8,'Sunsilk',5,51,'SunSilk shampoo 8gm','2023-12-12 18:03:34'),(10,5,10,'wired Headset',140,5,'wired headset','2023-12-12 18:05:48'),(11,5,9,'mobile',7500,5,'mobile phone ','2023-12-12 18:12:17'),(12,5,9,'laptop',100000,2,'laptop','2023-12-13 11:47:17'),(13,5,16,'demo',100,10,'laptops windoers','2023-12-15 18:27:05'),(14,5,16,'testing',1000,10,'keypad mobile','2023-12-15 18:39:02'),(16,8,25,'salt',18,10,'Ashirwad Salt ','2023-12-17 13:35:25'),(17,5,16,'testing_import_file',17249,0,'Oneplus Nord CE 3 lite ','2023-12-17 20:19:21'),(23,5,12,'watch',2500,1,'smartwatch','2023-12-18 11:11:47'),(24,5,22,'Second workspace Testing 2nd time',17249,15,'Oneplus Nord CE 3 lite Black color','2023-12-18 12:03:06'),(25,5,22,'mahesh_testing_import',18000,23,'Oneplus Nord CE 3 lite Black color','2023-12-18 14:01:31'),(26,5,16,'checking_import_btn',36000,2,'Oneplus Nord CE 3 lite Ocean Blue color','2023-12-18 14:29:17'),(27,5,16,'import_btn',36000,2,'Oneplus Nord CE 3 lite Ocean Blue color','2023-12-19 11:16:50'),(35,5,16,'goodmorning',36000,1,'Oneplus Nord CE 3 lite Ocean Blue color','2023-12-24 00:17:57'),(36,5,16,'Dell Mouse',750,1,'Dell Mouse Wired ','2023-12-24 09:41:58'),(48,1,1,'qrwaestdrfjy',5,5,'3wt4eyu','2024-01-26 00:00:00'),(49,1,1,'republic_day_product_adding',5,10,'awsdrtyfugihojpk[\'xdcfvbnj;','2024-01-26 00:00:00'),(51,1,1,'srtdhfyugkihojpk',1,3,'wqteyrt','2024-01-26 00:00:00'),(53,1,1,'testing_product',5,10,'product edit testing','2024-01-31 00:00:00'),(54,5,16,'HP Mouse',600,10,'HP Mouse Wired ','2024-01-31 00:00:00'),(55,5,8,'laptops_adding',50000,12,'laptops adding','2024-01-31 00:00:00'),(58,8,8,'Pen',10,10,'Product adding','2024-01-31 00:00:00'),(60,7,8,'add_product',10,2,'hiii','2024-02-01 00:00:00');
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

-- Dump completed on 2024-02-03 20:56:57

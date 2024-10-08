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
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoices` (
  `id` varchar(13) NOT NULL,
  `employeeId` int NOT NULL,
  `total` double NOT NULL,
  `vat` double NOT NULL,
  `discount` double NOT NULL,
  `payable` double NOT NULL,
  `paid` double NOT NULL,
  `returned` double NOT NULL,
  `datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
INSERT INTO `invoices` VALUES ('1491729973342',2,760,19,5,774,800,26,'2017-01-09 15:26:13'),('1491730560516',2,370,9.25,5,374.25,375,0.75,'2017-01-09 15:36:00'),('1492165305284',2,270,6.75,5,271.75,280,8.25,'2017-01-14 16:21:45'),('1492189349464',2,490,12.25,5,497.25,500,2.75,'2017-02-14 23:02:29'),('1492189946488',2,190,4.75,5,189.75,200,10.25,'2017-02-14 23:12:26'),('1492190099626',2,120,3,5,118,120,2,'2017-04-14 23:14:59'),('1492190341116',2,65,1.625,5,61.625,62,0.375,'2017-04-14 23:19:01'),('1492191099328',2,190,4.75,5,189.75,190,0.25,'2017-04-14 23:31:39'),('1492192975710',2,770,19.25,5,784.25,1000,215.75,'2017-04-15 00:02:55'),('1492193361407',2,865,21.625,5,881.625,900,18.375,'2017-03-15 00:09:21'),('1492313070538',2,275,6.875,5,276.875,300,23.125,'2017-03-16 09:24:30'),('1493699328760',2,70,1.75,5,66.75,70,3.25,'2017-05-02 10:28:48'),('1493699482352',2,190,4.75,5,189.75,190,0.25,'2017-05-02 10:31:22'),('1702974874540',2,7500,187.5,5,7682.5,7682.5,0,'2023-12-19 14:04:34'),('1702974928909',2,54,1.35,5,50.35,50.35,0,'2023-12-19 14:05:28'),('1702975010997',2,36,0.9,5,31.9,40,8.100000000000001,'2023-12-19 14:06:51'),('1702975253908',2,135,3.375,5,133.375,150,16.625,'2023-12-19 14:10:53'),('1702975998724',2,17249,431.225,5,17675.225,18000,324.77500000000146,'2023-12-19 14:23:18'),('1703245293860',2,100000,2500,5,102495,102500,5,'2023-12-22 17:11:33');
/*!40000 ALTER TABLE `invoices` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-03 21:01:39

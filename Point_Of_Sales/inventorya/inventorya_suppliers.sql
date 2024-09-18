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
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suppliers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `address` text NOT NULL,
  `createddate` datetime DEFAULT NULL,
  `state` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES (1,'Head & Shoulder','7418529630','USA','2024-03-18 00:00:00','HYD'),(2,'Loreal','8236547890','France','2024-03-18 00:00:00','UP'),(3,'Garnier','8520147963','France','2024-03-18 00:00:00','KA'),(4,'Set Wet','444444','India',NULL,NULL),(5,'Layer','555555','India',NULL,NULL),(6,'Brylcreem','777777','UK',NULL,NULL),(7,'Gatsby','8888888','Canada',NULL,NULL),(8,'Mahesh','3456786789','253465786990','2024-03-18 00:00:00','GJ'),(9,'Mahesh','1234567890','adding supllier',NULL,NULL),(10,'bhanu','1243568790-','asdfghdfhjk',NULL,NULL),(11,'Testing_Supplier','2345687980','hyd,India','2023-12-12 18:02:39','AP'),(12,'Mahesh_Yadav','123456879809','fsdghjdfhgkjl','2023-12-12 18:02:37',NULL),(13,'Bhanu','1235467897','fsdghkjl;\'l;kjhjghfdgsdg','2023-12-12 18:02:51',NULL),(14,'apparelo_retail','7898512312.','dfdsfsdf','2023-12-13 11:50:00',NULL),(15,'testing','12434576980','hyd','2023-12-13 19:10:37','dfgdsg'),(16,'Mahesh','2345867903r435','Yusufguda','2023-12-13 19:17:32','dfgfdg'),(17,'test_user','user_phone_no','user_adrs','2023-12-13 19:33:04','fdghdfg'),(18,'Techdenali','1236985740','BanjaraHills','2023-12-13 19:37:58','Telangana'),(19,'Amazon','123645768790','hyd-8','2023-12-13 19:44:36','Telangana'),(20,'flipkart','79000000000','Chikkamanagaluru','2023-12-13 19:45:43','Karnataka'),(21,'Mahesh Yadav','23456789','hyd','2023-12-14 11:41:56','MP'),(22,'Bhanu','2434567890','Hyd','2023-12-14 11:42:28','KA'),(23,'Testing Phone Filed','45435','phoneField testing','2023-12-15 12:52:32','telangana'),(24,'supplier edit checking','9638527410','bengaluru','2023-12-15 12:57:17','KA'),(25,'testing_supplier','123456789','hyd,testing_addrs','2023-12-17 13:27:38','TS'),(26,'Vijay','wfsedrhg','GachiBowli,Hyd','2023-12-18 16:02:17','Telangana'),(29,'supplier_testing','123654789','supplier addrs','2023-12-18 18:27:50','telangana'),(30,'supplier_checking_phone_No','8179410068','hyd','2023-12-18 18:40:36','Telangana'),(31,'phone_No_checking','1236567890','phone_number_checking','2023-12-18 18:42:54','Telangana'),(32,'erytujgio','we','ewrytui','2023-12-18 18:44:44','wety'),(33,'werhty','try','trgdyugkh','2023-12-18 18:46:38','erty'),(34,'erytu','adfs','rdfghjyert','2023-12-18 18:49:37','dfgh'),(35,'sdrtfy','tweyru','etrytuy','2023-12-18 18:59:52','eryuti'),(36,'y5r6tui','ye5rt6u','wqtery','2023-12-18 19:01:34','triyuo'),(37,'gfdsdf','dewrftgfrd','refwrgfdsdf','2023-12-18 19:05:06','tgrfedrfgtb'),(38,'testing','1234567890','fdsfsfs1','2023-12-18 19:27:45','dfbhg'),(39,'wetryti','qwertyuiol','rewtyui','2023-12-18 19:28:22','fdg'),(41,'cloth','9874563210','hyd','2023-12-24 10:50:30','TS'),(42,'wednesday','8179410069','wednesday','2024-01-24 00:00:00','sdtjkfy'),(48,'kio-l=p[\\\']','7894563210','ihbojpk[op','2024-01-24 00:00:00','hgigohjk'),(54,'Reddys_Lab','8179410069\'','telangana.','2024-01-24 00:00:00','Somajiguda,Hyd.'),(55,'RedTape Limited','9874123698\'','Bengaluru.','2024-01-24 00:00:00','Hyderabad'),(56,'RK World Infocom Pvt ltd','9636985214\'','Noida,UP.','2024-01-24 00:00:00','Noida'),(57,'RetailEZ Pvt ltd','7894563210\'','Maharastra.','2024-01-24 00:00:00','Lathore'),(58,'DesignHub1','7418529630\'','telangana.','2024-01-24 00:00:00','kphb,Hyd'),(59,'Reddys_Lab','8179410062','telangana.','2024-01-25 00:00:00','Somajiguda,Hyd.'),(60,'RedTape Limited','9874123698','Bengaluru','2024-01-25 00:00:00','Hyderabad'),(61,'RK World Infocom Pvt ltd','\"9636985214\"','Noida,UP.','2024-01-25 00:00:00','Noida'),(62,'RetailEZ Pvt ltd','\"7894563210\"','Maharastra.','2024-01-25 00:00:00','Lathore'),(63,'DesignHub1','\"7418529630\"','telangana.','2024-01-25 00:00:00','kphb,Hyd'),(64,'abc','9638527410','awsdfh','2024-01-25 00:00:00','dadshdj'),(65,'Reddys_Lab','\"8179410062\"','telangana.','2024-01-25 00:00:00','Somajiguda,Hyd.'),(66,'RedTape Limited','\"9874123698\"','Bengaluru.','2024-01-25 00:00:00','Hyderabad'),(67,'RK World Infocom Pvt ltd','\"9636985214\"','Noida,UP.','2024-01-25 00:00:00','Noida'),(68,'RetailEZ Pvt ltd','\"7894563210\"','Maharastra.','2024-01-25 00:00:00','Lathore'),(69,'DesignHub1','\"7418529630\"','telangana.','2024-01-25 00:00:00','kphb,Hyd'),(71,'republic_day','1236547890','hyd		','2024-01-26 00:00:00','wqteyrtu'),(82,'new supplier','8527419630','supplier adress','2024-01-31 00:00:00','HYD'),(83,'Reddys_Lab','8179410069\'','telangana.','2024-01-31 00:00:00','Somajiguda,Hyd.'),(84,'RedTape Limited','9874123698\'','Bengaluru.','2024-01-31 00:00:00','Hyderabad'),(85,'RK World Infocom Pvt ltd','9636985214\'','Noida,UP.','2024-01-31 00:00:00','Noida'),(86,'RetailEZ Pvt ltd','7894563210\'','Maharastra.','2024-01-31 00:00:00','Lathore'),(88,'supplier add testing','9638527410','supplier add testing \nedit is also working\nimport and add is also working','2024-01-31 00:00:00','supplier add testing'),(89,'supplier_checking','8527419630','hyd','2024-02-14 00:00:00','AP'),(90,'Golden','8527419630','hyd','2024-02-15 00:00:00','Bihar'),(91,'Reddys_Lab','\"8179410062\"','telangana.','2024-02-15 00:00:00','Somajiguda,Hyd.'),(92,'RedTape Limited','\"9874123698\"','Bengaluru.','2024-02-15 00:00:00','Hyderabad'),(93,'RK World Infocom Pvt ltd','\"9636985214\"','Noida,UP.','2024-02-15 00:00:00','Noida'),(94,'RetailEZ Pvt ltd','\"7894563210\"','Maharastra.','2024-02-15 00:00:00','Lathore'),(117,'new supplier adding today','8527419630','wdfdhgfrewa			','2024-03-19 00:00:00','APTS'),(118,'53245','1234567890','3545e4','2024-03-21 00:00:00','456'),(119,'Garden Harvest Hub','8527419630','Medchal, Hyderbad.','2024-03-21 16:35:09','TS'),(120,'Puma Sports India','7410258369','Benagluru, Karnataka.','2024-03-21 16:37:36','Karnataka'),(121,'Cococblu Retail','9876543203','The FairWay business Park, Challaghatta vill, Domlur, benagaluru, Karnataka.','2024-03-21 16:39:38','Karnataka'),(122,'MORE','7410963258',' kukatpally, Hyderbad','2024-03-21 16:43:16','TS'),(123,'M S R Products','7894563210','M S R Products, Bowrampet,Rangareddy\n','2024-03-21 16:46:23','TS'),(124,'Teja Organics','7412589603','Sainikpuri, Hyderabad','2024-03-21 16:47:08','TS'),(125,'Unicorn Ingredients Pvt Ltd','8521470369','Akshar Chowk,Vadodara\n','2024-03-21 16:48:07','Gujarath'),(126,'MILLET BEATS','9632580147','Patamata, Vijayawada','2024-03-21 16:48:47','AP'),(127,'Bikana Sweets','7801452369','Shivaji Chowk, Jalna, KPHB','2024-03-21 16:49:31','TS'),(128,'Chamunda Enterprises','9895674123','Gulmandi,Aurangabad-Maharashtra\n','2024-03-21 16:50:13','MAHARASTRA'),(129,'Nanjunda Dry Fruits','7896654123','Nanjunda Dry Fruits,\nBanashankari Road, Tumkur','2024-03-21 16:51:10','TN'),(130,'Organic Valley','7458963214','Kammanahalli, Bangalore','2024-03-21 16:52:08','KARNATAKA'),(131,'Vellore Frozen Foods','7412589636','Gandhi Nagar West, Vellore','2024-03-21 16:52:38','GJ'),(132,'Padma Food Product','9874563210','Indiranagar, Bangalore','2024-03-21 16:53:05','KA'),(133,'Siddarth Marketing','8885214794','New Tharagupet,Bangalore\n','2024-03-21 16:53:43','KA'),(134,'Riviera Foods','9874563214','Viman Nagar, Pune','2024-03-21 16:55:19','MAHARASTRA'),(135,'Kk Foods','7845121236','Redhills,Chennai\n','2024-03-21 16:56:07','TN');
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
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

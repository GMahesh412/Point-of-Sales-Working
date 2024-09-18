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
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(100) NOT NULL,
  `description` text NOT NULL,
  `createddate` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Shampoo','Hair Product','2023-12-12 18:13:48'),(2,'Gel','Hair styling gels','2024-03-18 00:00:00'),(3,'Cosmetics','Grooming products',NULL),(4,'Color','Hair colour and dyes category','2024-03-18 00:00:00'),(5,'electronics','Electronics category contain related products','2023-12-12 18:04:47'),(7,'apparel','apparel products','2023-12-13 11:49:12'),(8,'grocery','grocery category adding for testing purpose\n','2023-12-17 13:26:25'),(9,'category edit checking','category edit checking','2024-01-24 00:00:00'),(11,'beauty','this category related to beauty  products','2024-02-01 00:00:00'),(12,'fashion','this category related to fashion  products like men,women,kids clothes etc','2024-02-01 00:00:00'),(13,'home','this category related to household home need products','2024-02-01 00:00:00'),(14,'furniture','this category related to furniture products','2024-02-01 00:00:00'),(15,'pharmacy','this category related to pharmacy products like suppliments,medicines,drug related.','2024-02-01 00:00:00'),(16,'testing_category','testing_category','2024-02-15 00:00:00'),(18,'size','size category ','2024-03-07 00:00:00'),(19,'color','color category like it contains blue,black,red etc.','2024-03-07 00:00:00'),(20,'model','model category can include variations in features, specifications, colors, sizes, or any other attributes','2024-03-07 00:00:00'),(27,'Apollo pharmacy','this category related to pharmacy products like suppliments,medicines & drug related.','2024-03-19 00:00:00'),(38,'Skincare','Skincare category includes Moisturisers, Sunscreens, Face Cleaners, Sheet Masks, Tols, Serum, Toner , EyeCream. ','2024-03-21 15:41:25'),(39,'Fresh Vegetables','This category includes Leafy, Exotic vegetableas &  Summer Fruits, Fresh Cuts & Salads, Organics fruits and Vegatables, Festive Essentials , Frozen Vegatables etc.','2024-03-21 15:45:27'),(40,'Cool Drinks and Juices','Thus Category contains  Soft Drinks, Energy Drinks, Soda & Mixes, Water &  Ice Cubes, Milk Based Mixes, Ice tea & Cold teas, Non alcoholic drinks.','2024-03-21 15:47:30'),(41,'Bath , Body and Care','Bath , Body and Care this includes Shampoo & Conditioners, Shower Gel, Face wash & Scrub , Perfume Deo & Talc, Face Cream, hair Oil & Serum , Body Lotions.','2024-03-21 15:49:23'),(42,'Party Store','This category contains Snacks & Munchies, party Essentials, Sweet Tooth(Ice Creams), beverages, Instant Fixes, After party Cleaning.','2024-03-21 15:50:57'),(43,'Paan Corner','This Category consists of Smoking & Accessories, Hookah Accessories, paan & Mouth Freshners, Mint & Gums, Nicotine Alternatives, lighter, MatchBoxs.','2024-03-21 15:52:41'),(44,'Fitness Store','Includes Power Packed Nutrition, Nourishing Diet Choices , Vitamins & Suppliments, Protein Bars,Pre-Workout Nourishment, ','2024-03-21 15:54:44'),(45,'Baby Store','Includes Diapers  & Wipes , bath & Hair Care, Skin Care, Oral Care, Pharma.','2024-03-21 15:55:41'),(46,'Electronic Store','Smart watches , Mobile Accessories, Audio Appliances,  Earphones, Lighting & Electricals, Home Appliances, Sound System related , and all electronics ..','2024-03-21 15:57:41'),(47,'Pet Supplies','Cat Food, pet Grooming, pet Accessories, Pet Suppliments, Dog Food, etc.','2024-03-21 15:58:54'),(48,'Edible Oils & Ghee','SunFlower Oil & others,  Mustard oils, Ghee, Blended Oils , Rice Bran Oils, Olive Oils, Soyabean Oils, Cold-pressed Oils','2024-03-21 16:00:43'),(49,'Meat and Sea Food','Chicken, Fish and Crabs, prawns, eggs, mutton, Marinated, Cold cuts, frozen, masalas, Paste & Spreads(chciken piclkes like)..','2024-03-21 16:02:58'),(50,'Dairy Bread and Eggs ','Bread & Buns, Milk , Curd & Yogurts, milkshakes and More , Lassi & ButterMilk, paneer & Cream, eggs, Cheese, butter, Dairy Alternatives, Gourmet Bakes, Indian Breads, Milk Based Drinks...','2024-03-21 16:04:52'),(51,'Beauty & Grooming','Face & Lip care, Shaving Needs, hair Colour, Beauty Accesssories, makeUp, Gift Packs, shoe Care, Men\'s grooming, hair removals etc.','2024-03-21 16:06:28'),(52,'Office & Electricals ','Stationery, Audio Store, Home Appliances, Smart watches, batteries, Bulbs & Electricals, Basic electricals, Toys & Games, Party Needs','2024-03-21 16:07:48'),(53,'Books & Education ','Books, new releases, bestSellers, Gifting guide, Magic practice books, Comics, etc.','2024-03-21 16:10:15'),(54,'Fashion','men, Women, Kids, Footwear, accessories, beauty, Sport & Fitness, Luxury brans, Stores.','2024-03-21 16:12:08'),(55,'Men','T-Shirts, Shirts, Formal Wear, Indian Waer, Inner Wear, Jeans,Trousers, Sportwear, all clothing, Casual& Formal shoes, sport shoes, bags& luggages, wallets, Sun Glasses & Frames.','2024-03-21 16:13:42'),(56,'Kids','baby Clothing, Girl\'s Clothing, Boy\'s Clothing, teens, winterwear, T-Shirts & pools, dresses * jumpsuits, footwear, watches, jewellery, Backpacks, etc.','2024-03-21 16:15:01'),(57,'Fresh or Grocery','Fruits, vegetables, Fresh vegetables, party Store, Breakfast food, Cleaning & household, Hygiene & personal care, bath& Body Care, Paan Corner, bay Store, tea & coffee, pet Supplies, Kitvhen & home, Oils, Masalas, Sauces,, Eggs, Meat & fish, Snacks, & Munchies.. ','2024-03-21 16:18:37');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
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

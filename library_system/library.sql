/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19  Distrib 10.11.14-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: librarydb
-- ------------------------------------------------------
-- Server version	11.8.6-MariaDB-ubu2404

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) DEFAULT NULL,
  `available` bit(1) DEFAULT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES
(1,'Alex Michaelides','\0','Celadon Books','The Silent Patient'),
(2,'Khaled Hosseini','\0','Riverhead Books','The Kite Runner'),
(3,'Markus Zusak','\0','Picador','The Book Thief'),
(4,'Yuval Noah Harari','\0','Harper','Sapiens'),
(5,'James Clear','','Avery','Atomic Habits'),
(6,'Paulo Coelho','','HarperCollins','The Alchemist'),
(7,'Harper Lee','','J.B. Lippincott & Co.','To Kill a Mockingbird'),
(8,'George Orwell','\0','Secker & Warburg','1984'),
(9,'Jane Austen','','T. Egerton','Pride and Prejudice'),
(10,'J.R.R. Tolkien','','George Allen & Unwin','The Hobbit'),
(11,'F. Scott Fitzgerald','','Scribner','The Great Gatsby'),
(12,'Herman Melville','','Harper & Brothers','Moby Dick'),
(13,'Leo Tolstoy','','The Russian Messenger','War and Peace'),
(14,'J.D. Salinger','','Little, Brown and Company','The Catcher in the Rye'),
(15,'Fyodor Dostoevsky','','The Russian Messenger','Crime and Punishment'),
(16,'Homer','','Ancient Greece Press','The Odyssey'),
(17,'Aldous Huxley','','Chatto & Windus','Brave New World'),
(18,'Stephen King','','Doubleday','The Shining');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `member_id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES
(1,'arjun@example.com','Arjun Mehta',NULL),
(2,'priya@example.com','Priya Sharma',NULL),
(3,'rohan@example.com','Rohan Verma',NULL),
(4,'sneha@example.com','Sneha Kapoor',NULL);
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `trans_id` int(11) NOT NULL AUTO_INCREMENT,
  `issue_date` date DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  `book_id` int(11) DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`trans_id`),
  KEY `FKhwis5rd79vrejvuuuc513px7a` (`book_id`),
  KEY `FKsm5fuqx3871l7xsmsytjfpj2h` (`member_id`),
  CONSTRAINT `FKhwis5rd79vrejvuuuc513px7a` FOREIGN KEY (`book_id`) REFERENCES `books` (`book_id`),
  CONSTRAINT `FKsm5fuqx3871l7xsmsytjfpj2h` FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `role` enum('ROLE_ADMIN','ROLE_LIBRARIAN','ROLE_MEMBER') NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES
(1,'$2a$10$Xk3gcRMbDGQgKjB07UXIVe48DoGZall4fGVduUoOiQAEAuBjjf442','ROLE_ADMIN','admin'),
(2,'$2a$10$1D6CvQeyGN1J93ElrL0hD.zUyDNFlX8FFzDvLbxx9cMKoOiaY/a4u','ROLE_LIBRARIAN','librarian'),
(3,'$2a$10$Smk4/iwXL50IxeIqqaMdjeW2ywJHCzYMMP6N5OBLX3HvBnUVtOEdq','ROLE_MEMBER','member');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-04 21:23:11

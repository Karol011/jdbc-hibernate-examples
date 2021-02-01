-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: cinema
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `countries`
--

DROP TABLE IF EXISTS `countries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `countries` (
  `country_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `version` int(11) DEFAULT '0',
  PRIMARY KEY (`country_id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `countries`
--

LOCK TABLES `countries` WRITE;
/*!40000 ALTER TABLE `countries` DISABLE KEYS */;
INSERT INTO `countries` VALUES (1,'Poland',0),(2,'USA',0),(3,'Spain',0);
/*!40000 ALTER TABLE `countries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `countries_aud`
--

DROP TABLE IF EXISTS `countries_aud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `countries_aud` (
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `country_id` int(11) NOT NULL,
  PRIMARY KEY (`country_id`,`REV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `countries_aud`
--

LOCK TABLES `countries_aud` WRITE;
/*!40000 ALTER TABLE `countries_aud` DISABLE KEYS */;
INSERT INTO `countries_aud` VALUES (24,1,'Poland 2',1),(25,1,'Poland 3',1),(28,1,'Poland 2',1),(29,1,'Poland 3',1),(32,1,'Poland 2',1),(33,1,'Poland 3',1),(35,1,'Poland',1),(36,1,'Poland',1),(37,1,'Poland',1),(38,1,'Poland',1),(40,1,'Poland 2',1),(41,1,'Poland 3',1),(43,1,'Poland',1),(4,0,'France',7),(5,0,'Norway',8),(6,1,'Norway 2',8),(7,1,'Norway 3',8),(8,1,'Norway 4',8),(9,1,'Norway 5',8),(10,1,'Norway 4',8),(11,1,'Norway 5',8),(12,1,'Norway 6',8),(13,1,'Norway 6',8),(14,1,'Norway 7',8),(15,1,'Norway 8',8),(16,1,'Norway 9',8),(17,1,'Norway 1',8),(18,0,'France',12),(19,2,NULL,12),(20,0,'France',13),(21,0,'Poland 2',16),(22,0,'Poland 3',17),(23,0,'France',18),(26,2,NULL,18),(27,0,'France',19),(30,2,NULL,19),(31,0,'France',20),(34,2,NULL,20),(39,0,'France',21),(42,2,NULL,21);
/*!40000 ALTER TABLE `countries_aud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genres`
--

DROP TABLE IF EXISTS `genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `genres` (
  `genre_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  PRIMARY KEY (`genre_id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genres`
--

LOCK TABLES `genres` WRITE;
/*!40000 ALTER TABLE `genres` DISABLE KEYS */;
INSERT INTO `genres` VALUES (2,'comedy'),(3,'drama'),(1,'thriller');
/*!40000 ALTER TABLE `genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movies`
--

DROP TABLE IF EXISTS `movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `movies` (
  `movie_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(256) NOT NULL,
  `genre_id` int(11) DEFAULT NULL,
  `country_id` int(11) DEFAULT NULL,
  `production_year` int(11) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  PRIMARY KEY (`movie_id`),
  KEY `fk_movies_countries_idx` (`country_id`),
  KEY `fk_movies_genres_idx` (`genre_id`),
  CONSTRAINT `fk_movies_countries` FOREIGN KEY (`country_id`) REFERENCES `countries` (`country_id`),
  CONSTRAINT `fk_movies_genres` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`genre_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies`
--

LOCK TABLES `movies` WRITE;
/*!40000 ALTER TABLE `movies` DISABLE KEYS */;
INSERT INTO `movies` VALUES (1,'Szklana pułapka',1,1,NULL,135),(2,'Kevin sam w domu',2,1,NULL,135);
/*!40000 ALTER TABLE `movies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_types`
--

DROP TABLE IF EXISTS `person_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `person_types` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  PRIMARY KEY (`type_id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_types`
--

LOCK TABLES `person_types` WRITE;
/*!40000 ALTER TABLE `person_types` DISABLE KEYS */;
INSERT INTO `person_types` VALUES (1,'actor'),(2,'producer'),(3,'scenarist');
/*!40000 ALTER TABLE `person_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persons`
--

DROP TABLE IF EXISTS `persons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `persons` (
  `person_id` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `country_id` int(11) DEFAULT NULL,
  `date_of_brith` date DEFAULT NULL,
  PRIMARY KEY (`person_id`),
  KEY `fk_persons_countries_idx` (`country_id`),
  CONSTRAINT `fk_persons_countries` FOREIGN KEY (`country_id`) REFERENCES `countries` (`country_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persons`
--

LOCK TABLES `persons` WRITE;
/*!40000 ALTER TABLE `persons` DISABLE KEYS */;
INSERT INTO `persons` VALUES (1,'Leo','Messi',1,NULL),(2,'Robert','Lewandowski',2,NULL),(3,'Cristiano','Ronaldo',3,NULL);
/*!40000 ALTER TABLE `persons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persons_movies`
--

DROP TABLE IF EXISTS `persons_movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `persons_movies` (
  `movie_id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `person_id` int(11) NOT NULL,
  PRIMARY KEY (`movie_id`,`type_id`,`person_id`),
  KEY `fk_pm_person_types_idx` (`type_id`),
  KEY `fk_pm_persons_idx` (`person_id`),
  CONSTRAINT `fk_pm_movies` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`movie_id`),
  CONSTRAINT `fk_pm_person_types` FOREIGN KEY (`type_id`) REFERENCES `person_types` (`type_id`),
  CONSTRAINT `fk_pm_persons` FOREIGN KEY (`person_id`) REFERENCES `persons` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persons_movies`
--

LOCK TABLES `persons_movies` WRITE;
/*!40000 ALTER TABLE `persons_movies` DISABLE KEYS */;
INSERT INTO `persons_movies` VALUES (1,1,1),(1,1,3),(2,1,1),(2,1,2),(2,1,3),(1,2,1),(1,2,3);
/*!40000 ALTER TABLE `persons_movies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `revinfo`
--

DROP TABLE IF EXISTS `revinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `revinfo` (
  `rev` int(11) NOT NULL AUTO_INCREMENT,
  `revtstmp` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`rev`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `revinfo`
--

LOCK TABLES `revinfo` WRITE;
/*!40000 ALTER TABLE `revinfo` DISABLE KEYS */;
INSERT INTO `revinfo` VALUES (4,1549492702266),(5,1549638604818),(6,1549638931500),(7,1549638938287),(8,1549639116634),(9,1549639116675),(10,1549639286091),(11,1549639326855),(12,1549639810947),(13,1549639879997),(14,1549639880056),(15,1549640096511),(16,1549640104819),(17,1549640128450),(18,1549655001054),(19,1549655001372),(20,1549655763016),(21,1549656090051),(22,1549656090094),(23,1549656174611),(24,1549656174934),(25,1549656174982),(26,1549656175016),(27,1549656265570),(28,1549656265990),(29,1549656266015),(30,1549656266057),(31,1549656452602),(32,1549656452907),(33,1549656452942),(34,1549656452980),(35,1549658709335),(36,1549658876691),(37,1549658972753),(38,1549659542298),(39,1549659763369),(40,1549659763723),(41,1549659763763),(42,1549659763812),(43,1549659764350);
/*!40000 ALTER TABLE `revinfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-08 22:17:17

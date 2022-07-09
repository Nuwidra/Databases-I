-- MySQL dump 10.13  Distrib 8.0.25, for Linux (x86_64)
--
-- Host: localhost    Database: universidad
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `curso`
--

DROP TABLE IF EXISTS `curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `curso` (
  `id` int NOT NULL,
  `nombre` varchar(60) DEFAULT NULL,
  `creditos` int DEFAULT NULL,
  `departamento` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curso`
--

LOCK TABLES `curso` WRITE;
/*!40000 ALTER TABLE `curso` DISABLE KEYS */;
INSERT INTO `curso` VALUES (1,'Programacion I',4,'Ingenieria en Computacion'),(2,'Programacion II',4,'Ingenieria en Computacion'),(3,'Programacion III',1,'Ingenieria en Computacion'),(4,'Programacion IV',4,'Ingenieria en Computacion'),(5,'Programacion V',5,'Ingenieria en Computacion');
/*!40000 ALTER TABLE `curso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `departamento`
--

DROP TABLE IF EXISTS `departamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `departamento` (
  `nombre` varchar(20) NOT NULL,
  `edificio` varchar(20) DEFAULT NULL,
  `presupuesto` int DEFAULT NULL,
  PRIMARY KEY (`nombre`),
  CONSTRAINT `departamento_chk_1` CHECK ((`presupuesto` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departamento`
--

LOCK TABLES `departamento` WRITE;
/*!40000 ALTER TABLE `departamento` DISABLE KEYS */;
INSERT INTO `departamento` VALUES ('Biologia','Ciencias',100000),('Computacion','Exactas',120000),('Filosofia','Sociales',1000),('Fisica','Exactas',10000),('Ing Civil','Ingenieria',120000),('Quimica','Exactas',110000);
/*!40000 ALTER TABLE `departamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estudiante`
--

DROP TABLE IF EXISTS `estudiante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estudiante` (
  `id` int NOT NULL,
  `nombre` varchar(30) DEFAULT NULL,
  `apellido` varchar(30) DEFAULT NULL,
  `total_creditos` int DEFAULT NULL,
  `fecha_nacimiento` timestamp NOT NULL,
  `departamento` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `departamento` (`departamento`),
  CONSTRAINT `estudiante_ibfk_1` FOREIGN KEY (`departamento`) REFERENCES `departamento` (`nombre`),
  CONSTRAINT `estudiante_chk_1` CHECK ((`total_creditos` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudiante`
--

LOCK TABLES `estudiante` WRITE;
/*!40000 ALTER TABLE `estudiante` DISABLE KEYS */;
INSERT INTO `estudiante` VALUES (1,'Steven','Alvarado',8,'2000-08-08 06:00:00','Computacion'),(2,'Lermith','Biarreta',8,'2000-08-08 06:00:00','Biologia'),(3,'Maria','Biarreta',8,'2000-08-08 06:00:00','Biologia'),(4,'Valeria','Calderon',12,'2000-08-08 06:00:00','Biologia'),(5,'Sebastian','Campos',4,'2000-08-08 06:00:00','Quimica'),(6,'Josue','Castro',11,'2000-08-08 06:00:00','Ing Civil'),(7,'Susana','Cen',16,'2000-08-08 06:00:00','Filosofia'),(8,'Johan','Echeverria',8,'2000-08-08 06:00:00','Fisica'),(9,'Junior','Herrera',8,'2000-08-08 06:00:00','Computacion'),(10,'Alejandro','Loaiza',4,'2000-08-08 06:00:00','Fisica');
/*!40000 ALTER TABLE `estudiante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profesor`
--

DROP TABLE IF EXISTS `profesor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profesor` (
  `id` int NOT NULL,
  `nombre` varchar(30) DEFAULT NULL,
  `apellido` varchar(30) DEFAULT NULL,
  `ciudad` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profesor`
--

LOCK TABLES `profesor` WRITE;
/*!40000 ALTER TABLE `profesor` DISABLE KEYS */;
INSERT INTO `profesor` VALUES (1,'Martin','Flores','San Ramon'),(2,'Allan','Cascante','Palmares'),(3,'Albert','Einstein','San Ramon'),(4,'Marco','Calvo','Palmares'),(5,'Jose','Herrera','San Ramon'),(6,'Carolina','Lizano','Palmares'),(7,'Raquel','Rodriguez','San Ramon');
/*!40000 ALTER TABLE `profesor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutor`
--

DROP TABLE IF EXISTS `tutor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tutor` (
  `estudiante_id` int DEFAULT NULL,
  `profesor_id` int DEFAULT NULL,
  KEY `estudiante_id` (`estudiante_id`),
  KEY `profesor_id` (`profesor_id`),
  CONSTRAINT `tutor_ibfk_1` FOREIGN KEY (`estudiante_id`) REFERENCES `estudiante` (`id`),
  CONSTRAINT `tutor_ibfk_2` FOREIGN KEY (`profesor_id`) REFERENCES `profesor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutor`
--

LOCK TABLES `tutor` WRITE;
/*!40000 ALTER TABLE `tutor` DISABLE KEYS */;
/*!40000 ALTER TABLE `tutor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


-- =======================================
-- PASO 9 DEL PROYECTO
-- =======================================

use universidad;
drop user if exists 'universidad_user';
create user 'universidad_user' identified by 'universidad_pass';
grant insert, delete, update on universidad.* to 'universidad_user';
grant execute on universidad.* to 'universidad_user';
flush privileges;

-- =======================================
-- PASO 10 DEL PROYECTO
-- =======================================
SET autocommit = OFF;

-- Dump completed on 2021-06-05 13:57:29

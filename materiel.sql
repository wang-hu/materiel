-- MySQL dump 10.13  Distrib 5.7.21, for Win64 (x86_64)
--
-- Host: localhost    Database: materiel
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_income_materiel`
--

DROP TABLE IF EXISTS `t_income_materiel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_income_materiel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `materiel_no` varchar(100) DEFAULT NULL COMMENT '来料号',
  `income_num` bigint(20) DEFAULT '0' COMMENT '来料数量',
  `income_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='来源物料写入';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_income_materiel`
--

LOCK TABLES `t_income_materiel` WRITE;
/*!40000 ALTER TABLE `t_income_materiel` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_income_materiel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_receive_materiel`
--

DROP TABLE IF EXISTS `t_receive_materiel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_receive_materiel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `receiver` varchar(100) DEFAULT NULL COMMENT '领料人',
  `materiel_no` varchar(100) DEFAULT NULL COMMENT '物料号',
  `receive_num` bigint(20) DEFAULT '0' COMMENT '领取数量',
  `receive_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='领料记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_receive_materiel`
--

LOCK TABLES `t_receive_materiel` WRITE;
/*!40000 ALTER TABLE `t_receive_materiel` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_receive_materiel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_stock`
--

DROP TABLE IF EXISTS `t_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_stock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `materiel_no` varchar(100) DEFAULT NULL COMMENT '物料号',
  `materiel_name` varchar(100) DEFAULT '' COMMENT '物料名称',
  `stock_num` bigint(20) DEFAULT '0' COMMENT '安全库存量',
  `total` bigint(20) DEFAULT '0' COMMENT '库存总量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_stock_materiel_no_uindex` (`materiel_no`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='库存量';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_stock`
--

LOCK TABLES `t_stock` WRITE;
/*!40000 ALTER TABLE `t_stock` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_stock` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-10 12:47:05

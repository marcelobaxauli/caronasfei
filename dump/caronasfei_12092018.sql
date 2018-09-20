-- MySQL dump 10.13  Distrib 5.7.23, for Linux (x86_64)
--
-- Host: localhost    Database: caronasfei
-- ------------------------------------------------------
-- Server version	5.7.23-0ubuntu0.16.04.1

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
-- Current Database: `caronasfei`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `caronasfei` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `caronasfei`;

--
-- Table structure for table `cadastro`
--

DROP TABLE IF EXISTS `cadastro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cadastro` (
  `id_cadastro` int(11) NOT NULL AUTO_INCREMENT,
  `id_cadastro_etapa` int(11) NOT NULL,
  PRIMARY KEY (`id_cadastro`),
  KEY `id_cadastro_etapa` (`id_cadastro_etapa`),
  CONSTRAINT `cadastro_ibfk_1` FOREIGN KEY (`id_cadastro_etapa`) REFERENCES `cadastro_etapa` (`id_cadastro_etapa`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cadastro`
--

LOCK TABLES `cadastro` WRITE;
/*!40000 ALTER TABLE `cadastro` DISABLE KEYS */;
INSERT INTO `cadastro` VALUES (1,3),(2,4),(5,4);
/*!40000 ALTER TABLE `cadastro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cadastro_etapa`
--

DROP TABLE IF EXISTS `cadastro_etapa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cadastro_etapa` (
  `id_cadastro_etapa` int(11) NOT NULL AUTO_INCREMENT,
  `ds_cadastro_etapa` varchar(50) NOT NULL,
  `cd_cadastro_etapa` varchar(10) NOT NULL,
  PRIMARY KEY (`id_cadastro_etapa`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cadastro_etapa`
--

LOCK TABLES `cadastro_etapa` WRITE;
/*!40000 ALTER TABLE `cadastro_etapa` DISABLE KEYS */;
INSERT INTO `cadastro_etapa` VALUES (1,'DADOS PESSOAIS','DAPE'),(2,'AUTENTICACAO FEI','AUTFEI'),(3,'PERFIL USUARIO','PRFUSU'),(4,'COMPLETO','COMPL');
/*!40000 ALTER TABLE `cadastro_etapa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curso`
--

DROP TABLE IF EXISTS `curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curso` (
  `id_curso` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) DEFAULT NULL,
  `codigo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_curso`),
  UNIQUE KEY `codigo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curso`
--

LOCK TABLES `curso` WRITE;
/*!40000 ALTER TABLE `curso` DISABLE KEYS */;
INSERT INTO `curso` VALUES (1,'Administração','ADM'),(2,'Ciência da Computação','CC'),(3,'Engenharia de Automação e Controle','EAC'),(4,'Engenharia Civil','EC'),(5,'Engenharia Elétrica','EL'),(6,'Engenharia de Materiais','EM'),(7,'Engenharia de Produção','EP'),(8,'Engenharia Química','EQ'),(9,'Engenharia Têxtil','ET'),(10,'Pós Graduação','POS');
/*!40000 ALTER TABLE `curso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curso_periodo`
--

DROP TABLE IF EXISTS `curso_periodo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curso_periodo` (
  `id_curso_periodo` int(11) NOT NULL AUTO_INCREMENT,
  `id_curso` int(11) NOT NULL,
  `id_periodo` int(11) NOT NULL,
  PRIMARY KEY (`id_curso_periodo`),
  KEY `id_curso` (`id_curso`),
  KEY `id_periodo` (`id_periodo`),
  CONSTRAINT `curso_periodo_ibfk_1` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id_curso`),
  CONSTRAINT `curso_periodo_ibfk_2` FOREIGN KEY (`id_periodo`) REFERENCES `periodo` (`id_periodo`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curso_periodo`
--

LOCK TABLES `curso_periodo` WRITE;
/*!40000 ALTER TABLE `curso_periodo` DISABLE KEYS */;
INSERT INTO `curso_periodo` VALUES (1,1,1),(2,2,1),(3,3,1),(4,4,1),(5,5,1),(6,6,1),(7,7,1),(8,8,1),(9,9,1),(10,10,1),(11,1,2),(12,2,2),(13,3,2),(14,4,2),(15,5,2),(16,6,2),(17,7,2),(18,8,2),(19,9,2),(20,10,2);
/*!40000 ALTER TABLE `curso_periodo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endereco`
--

DROP TABLE IF EXISTS `endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `endereco` (
  `id_endereco` int(11) NOT NULL AUTO_INCREMENT,
  `rua` varchar(500) NOT NULL,
  `numero` int(11) NOT NULL,
  `cidade` varchar(100) NOT NULL,
  `cep` varchar(30) NOT NULL,
  `estado` varchar(30) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `opcao_endereco` varchar(10) NOT NULL,
  `bairro` varchar(100) NOT NULL,
  PRIMARY KEY (`id_endereco`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endereco`
--

LOCK TABLES `endereco` WRITE;
/*!40000 ALTER TABLE `endereco` DISABLE KEYS */;
INSERT INTO `endereco` VALUES (15,'rua conselheiro furtado',1114,'sp','01511-001','SP',-23.562837869553913,-46.63263288732753,'OUTRO','Liberdade'),(16,'Rua Orfanato',667,'SP','03131-010','SP',-23.57876157340302,-46.57922205259966,'OUTRO','Vila Prudente');
/*!40000 ALTER TABLE `endereco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `horario`
--

DROP TABLE IF EXISTS `horario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `horario` (
  `id_horario` int(11) NOT NULL AUTO_INCREMENT,
  `id_horario_tipo` int(11) NOT NULL,
  `horario` datetime DEFAULT NULL,
  PRIMARY KEY (`id_horario`),
  KEY `id_horario_tipo` (`id_horario_tipo`),
  CONSTRAINT `horario_ibfk_1` FOREIGN KEY (`id_horario_tipo`) REFERENCES `horario_tipo` (`id_horario_tipo`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horario`
--

LOCK TABLES `horario` WRITE;
/*!40000 ALTER TABLE `horario` DISABLE KEYS */;
INSERT INTO `horario` VALUES (21,8,'1970-01-01 19:08:00'),(22,5,NULL),(23,8,'1970-01-01 19:17:00'),(24,8,'1970-01-01 17:40:00');
/*!40000 ALTER TABLE `horario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `horario_tipo`
--

DROP TABLE IF EXISTS `horario_tipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `horario_tipo` (
  `id_horario_tipo` int(11) NOT NULL AUTO_INCREMENT,
  `cd_horario_tipo` varchar(30) NOT NULL,
  `ds_horario_tipo` varchar(60) NOT NULL,
  PRIMARY KEY (`id_horario_tipo`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horario_tipo`
--

LOCK TABLES `horario_tipo` WRITE;
/*!40000 ALTER TABLE `horario_tipo` DISABLE KEYS */;
INSERT INTO `horario_tipo` VALUES (5,'qualquer','Qualquer Horário'),(6,'horario_fixo','Horário Fixo'),(7,'de','De'),(8,'ate','Até');
/*!40000 ALTER TABLE `horario_tipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `intencao_carona`
--

DROP TABLE IF EXISTS `intencao_carona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `intencao_carona` (
  `id_intencao_carona` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) DEFAULT NULL,
  `data_criacao` datetime NOT NULL,
  `acao_carona` varchar(60) NOT NULL,
  `id_local_partida` int(11) DEFAULT NULL,
  `id_local_destino` int(11) DEFAULT NULL,
  `id_horario_partida` int(11) DEFAULT NULL,
  `id_horario_chegada` int(11) DEFAULT NULL,
  `estado` varchar(30) NOT NULL,
  `data_cancelamento` datetime DEFAULT NULL,
  PRIMARY KEY (`id_intencao_carona`),
  KEY `id_usuario` (`id_usuario`),
  KEY `id_local_partida` (`id_local_partida`),
  KEY `id_local_destino` (`id_local_destino`),
  KEY `id_horario_partida` (`id_horario_partida`),
  KEY `id_horario_chegada` (`id_horario_chegada`),
  CONSTRAINT `intencao_carona_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `intencao_carona_ibfk_2` FOREIGN KEY (`id_local_partida`) REFERENCES `endereco` (`id_endereco`),
  CONSTRAINT `intencao_carona_ibfk_3` FOREIGN KEY (`id_local_destino`) REFERENCES `endereco` (`id_endereco`),
  CONSTRAINT `intencao_carona_ibfk_4` FOREIGN KEY (`id_horario_partida`) REFERENCES `horario` (`id_horario`),
  CONSTRAINT `intencao_carona_ibfk_5` FOREIGN KEY (`id_horario_chegada`) REFERENCES `horario` (`id_horario`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `intencao_carona`
--

LOCK TABLES `intencao_carona` WRITE;
/*!40000 ALTER TABLE `intencao_carona` DISABLE KEYS */;
INSERT INTO `intencao_carona` VALUES (16,2,'2018-05-15 18:32:48','PEDIR_CARONA',15,NULL,22,21,'ATIVA',NULL),(17,3,'2018-05-19 16:43:47','OFERECER_CARONA',16,NULL,24,23,'ATIVA',NULL);
/*!40000 ALTER TABLE `intencao_carona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfil`
--

DROP TABLE IF EXISTS `perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perfil` (
  `id_perfil` int(11) NOT NULL AUTO_INCREMENT,
  `id_perfil_passageiro` int(11) DEFAULT NULL,
  `id_perfil_motorista` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_perfil`),
  KEY `id_perfil_passageiro` (`id_perfil_passageiro`),
  KEY `id_perfil_motorista` (`id_perfil_motorista`),
  CONSTRAINT `perfil_ibfk_1` FOREIGN KEY (`id_perfil_passageiro`) REFERENCES `perfil_passageiro` (`id_perfil_passageiro`),
  CONSTRAINT `perfil_ibfk_2` FOREIGN KEY (`id_perfil_motorista`) REFERENCES `perfil_motorista` (`id_perfil_motorista`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil`
--

LOCK TABLES `perfil` WRITE;
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` VALUES (1,1,NULL),(4,4,NULL),(7,7,NULL);
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfil_motorista`
--

DROP TABLE IF EXISTS `perfil_motorista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perfil_motorista` (
  `id_perfil_motorista` int(11) NOT NULL AUTO_INCREMENT,
  `modelo_carro` varchar(150) DEFAULT NULL,
  `ano` int(11) DEFAULT NULL,
  `cor` varchar(20) DEFAULT NULL,
  `placa` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id_perfil_motorista`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil_motorista`
--

LOCK TABLES `perfil_motorista` WRITE;
/*!40000 ALTER TABLE `perfil_motorista` DISABLE KEYS */;
/*!40000 ALTER TABLE `perfil_motorista` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfil_passageiro`
--

DROP TABLE IF EXISTS `perfil_passageiro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perfil_passageiro` (
  `id_perfil_passageiro` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id_perfil_passageiro`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil_passageiro`
--

LOCK TABLES `perfil_passageiro` WRITE;
/*!40000 ALTER TABLE `perfil_passageiro` DISABLE KEYS */;
INSERT INTO `perfil_passageiro` VALUES (1),(4),(7);
/*!40000 ALTER TABLE `perfil_passageiro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `periodo`
--

DROP TABLE IF EXISTS `periodo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `periodo` (
  `id_periodo` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) DEFAULT NULL,
  `codigo` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id_periodo`),
  UNIQUE KEY `codigo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `periodo`
--

LOCK TABLES `periodo` WRITE;
/*!40000 ALTER TABLE `periodo` DISABLE KEYS */;
INSERT INTO `periodo` VALUES (1,'Noturno','N'),(2,'Diurno','D');
/*!40000 ALTER TABLE `periodo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sugestao_trajeto`
--

DROP TABLE IF EXISTS `sugestao_trajeto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sugestao_trajeto` (
  `id_sugestao_trajeto` int(11) NOT NULL AUTO_INCREMENT,
  `id_sugestao_trajeto_motorista` int(11) NOT NULL,
  PRIMARY KEY (`id_sugestao_trajeto`),
  KEY `id_sugestao_trajeto_motorista` (`id_sugestao_trajeto_motorista`),
  CONSTRAINT `sugestao_trajeto_ibfk_1` FOREIGN KEY (`id_sugestao_trajeto_motorista`) REFERENCES `sugestao_trajeto_usuario` (`id_sugestao_trajeto_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sugestao_trajeto`
--

LOCK TABLES `sugestao_trajeto` WRITE;
/*!40000 ALTER TABLE `sugestao_trajeto` DISABLE KEYS */;
INSERT INTO `sugestao_trajeto` VALUES (6,6);
/*!40000 ALTER TABLE `sugestao_trajeto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sugestao_trajeto_passageiro`
--

DROP TABLE IF EXISTS `sugestao_trajeto_passageiro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sugestao_trajeto_passageiro` (
  `id_sugestao_trajeto_passageiro` int(11) NOT NULL AUTO_INCREMENT,
  `id_intencao_carona` int(11) NOT NULL,
  `estado` varchar(20) NOT NULL,
  `distancia` decimal(10,0) NOT NULL,
  `id_sugestao_trajeto` int(11) NOT NULL,
  PRIMARY KEY (`id_sugestao_trajeto_passageiro`),
  KEY `id_intencao_carona` (`id_intencao_carona`),
  KEY `id_sugestao_trajeto` (`id_sugestao_trajeto`),
  CONSTRAINT `sugestao_trajeto_passageiro_ibfk_1` FOREIGN KEY (`id_intencao_carona`) REFERENCES `intencao_carona` (`id_intencao_carona`),
  CONSTRAINT `sugestao_trajeto_passageiro_ibfk_2` FOREIGN KEY (`id_sugestao_trajeto`) REFERENCES `sugestao_trajeto` (`id_sugestao_trajeto`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sugestao_trajeto_passageiro`
--

LOCK TABLES `sugestao_trajeto_passageiro` WRITE;
/*!40000 ALTER TABLE `sugestao_trajeto_passageiro` DISABLE KEYS */;
INSERT INTO `sugestao_trajeto_passageiro` VALUES (2,16,'CONFIRMADO_MOTORISTA',6,6);
/*!40000 ALTER TABLE `sugestao_trajeto_passageiro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sugestao_trajeto_usuario`
--

DROP TABLE IF EXISTS `sugestao_trajeto_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sugestao_trajeto_usuario` (
  `id_sugestao_trajeto_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `id_intencao_carona` int(11) NOT NULL,
  `estado` varchar(20) NOT NULL,
  PRIMARY KEY (`id_sugestao_trajeto_usuario`),
  KEY `id_intencao_carona` (`id_intencao_carona`),
  CONSTRAINT `sugestao_trajeto_usuario_ibfk_1` FOREIGN KEY (`id_intencao_carona`) REFERENCES `intencao_carona` (`id_intencao_carona`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sugestao_trajeto_usuario`
--

LOCK TABLES `sugestao_trajeto_usuario` WRITE;
/*!40000 ALTER TABLE `sugestao_trajeto_usuario` DISABLE KEYS */;
INSERT INTO `sugestao_trajeto_usuario` VALUES (6,17,'NAO_CONFIRMADO');
/*!40000 ALTER TABLE `sugestao_trajeto_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(300) NOT NULL,
  `foto` blob,
  `email` varchar(300) NOT NULL,
  `senha` varchar(300) NOT NULL,
  `numero_celular` varchar(30) NOT NULL,
  `cidade` varchar(100) DEFAULT NULL,
  `autenticado_fei` tinyint(4) NOT NULL,
  `id_curso_periodo` int(11) NOT NULL,
  `id_cadastro` int(11) NOT NULL,
  `id_perfil` int(11) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  KEY `id_curso_periodo` (`id_curso_periodo`),
  KEY `id_cadastro` (`id_cadastro`),
  KEY `id_perfil` (`id_perfil`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`id_curso_periodo`) REFERENCES `curso_periodo` (`id_curso_periodo`),
  CONSTRAINT `usuario_ibfk_2` FOREIGN KEY (`id_cadastro`) REFERENCES `cadastro` (`id_cadastro`),
  CONSTRAINT `usuario_ibfk_3` FOREIGN KEY (`id_perfil`) REFERENCES `perfil` (`id_perfil`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'lkçwjerewlkçjr',NULL,'dsafasdf@sadfasd.com','teste','34 24324-2343','',1,1,1,1),(2,'Marcelo Lima Baxauli',NULL,'mlb122@hotmail.com','teste','11 95904-2935','',1,2,2,4),(3,'Jõao Firmino',NULL,'joao@hotmail.com','teste','(33) 33333-3333',NULL,1,1,5,7);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-12 23:33:37

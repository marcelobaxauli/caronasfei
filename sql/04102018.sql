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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cadastro`
--

LOCK TABLES `cadastro` WRITE;
/*!40000 ALTER TABLE `cadastro` DISABLE KEYS */;
INSERT INTO `cadastro` VALUES (1,4),(2,4),(3,4),(4,4),(5,4),(6,4),(7,4),(8,4),(9,4),(10,4);
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
  `opcao_endereco` varchar(10) NOT NULL,
  `rua` varchar(500) NOT NULL,
  `numero` int(11) NOT NULL,
  `bairro` varchar(100) NOT NULL,
  `cidade` varchar(100) NOT NULL,
  `cep` varchar(30) NOT NULL,
  `estado` varchar(30) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  PRIMARY KEY (`id_endereco`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endereco`
--

LOCK TABLES `endereco` WRITE;
/*!40000 ALTER TABLE `endereco` DISABLE KEYS */;
INSERT INTO `endereco` VALUES (1,'FEI_SBC','Av. Humberto de Alencar Castelo Branco',3972,'Assunção','São Bernardo do Campo','09850-901','SP',-23.72633935,-46.580927320178915),(2,'OUTRO','rua conselheiro furtado',1114,'Liberdade','Sao Paulo','01511-001','SP',-23.562837869553913,-46.63263288732753),(3,'OUTRO','rua luis antônio padrão',155,'Cipava','Osasco','São Paulo','SP',-23.544386165321114,-46.78661786591838),(4,'OUTRO','rua sobradinho',342,'Cachoeirinha','Sao Paulo','02674-070','SP',-23.4611314679439,-46.66925968734492),(5,'OUTRO','R. Octacílio Malheiros',252,'Gopouva','Guarulhos','São Paulo','SP',-23.461326422011396,-46.54159579810259),(6,'OUTRO','rua frança carvalho',108,'Taipas','Sao Paulo','São Paulo','SP',-22.888332308274276,-45.36822471566084),(7,'OUTRO','rua rodesia',278,'Pinheiros','Sao Paulo','05435-020','SP',-23.551728688439432,-46.69155109754851),(8,'OUTRO','Rua Carlos Petit',341,'Vila Mariana','Sao Paulo','04110-000','SP',-23.583537326190957,-46.63410448362489),(9,'OUTRO','Rua Jurupari',811,'Jabaquara','Sao Paulo','04348-070','SP',-23.64150955121468,-46.65360074508045),(10,'OUTRO','rua dráusio',632,'Paulicéia','Sao Bernardo Do Campo','São Paulo','SP',-23.667821822090154,-46.59494682307111),(11,'OUTRO','rua conselheiro furtado',1114,'Liberdade','Sao Paulo','01511-001','SP',-23.562837869553913,-46.63263288732753);
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horario`
--

LOCK TABLES `horario` WRITE;
/*!40000 ALTER TABLE `horario` DISABLE KEYS */;
INSERT INTO `horario` VALUES (1,2,'1970-01-01 20:40:00'),(2,2,'1970-01-01 20:40:00'),(3,2,'1970-01-01 19:20:00'),(4,2,'1970-01-01 17:20:00'),(5,2,'1970-01-01 18:32:00'),(6,2,'1970-01-01 15:32:00'),(7,2,'1970-01-01 19:34:00'),(8,2,'1970-01-01 18:34:00'),(9,2,'1970-01-01 18:35:00'),(10,2,'1970-01-01 16:35:00'),(11,2,'1970-01-01 18:47:00'),(12,2,'1970-01-01 17:47:00'),(13,2,'1970-01-01 19:48:00'),(14,2,'1970-01-01 16:48:00'),(15,2,'1970-01-01 19:50:00'),(16,2,'1970-01-01 17:50:00'),(17,2,'1970-01-01 20:57:00'),(18,2,'1970-01-01 15:57:00'),(19,2,'1970-01-01 19:06:00'),(20,2,'1970-01-01 17:20:00');
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horario_tipo`
--

LOCK TABLES `horario_tipo` WRITE;
/*!40000 ALTER TABLE `horario_tipo` DISABLE KEYS */;
INSERT INTO `horario_tipo` VALUES (1,'qualquer','Qualquer Horário'),(2,'horario_fixo','Horário Fixo'),(3,'de','De'),(4,'ate','Até');
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
  `id_usuario` int(11) NOT NULL,
  `data_criacao` datetime NOT NULL,
  `data_cancelamento` datetime DEFAULT NULL,
  `acao_carona` varchar(60) NOT NULL,
  `direcao_carona` varchar(60) NOT NULL,
  `estado` varchar(30) NOT NULL,
  `numero_assentos` int(11) DEFAULT NULL,
  `detour` int(11) DEFAULT NULL,
  `id_local_partida` int(11) NOT NULL,
  `id_local_destino` int(11) NOT NULL,
  `id_horario_partida` int(11) DEFAULT NULL,
  `id_horario_chegada` int(11) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `intencao_carona`
--

LOCK TABLES `intencao_carona` WRITE;
/*!40000 ALTER TABLE `intencao_carona` DISABLE KEYS */;
INSERT INTO `intencao_carona` VALUES (2,2,'2018-10-04 20:21:30',NULL,'PEDIR_CARONA','IDA_FEI','ATIVA',NULL,NULL,3,1,4,3),(3,3,'2018-10-04 20:33:30',NULL,'PEDIR_CARONA','IDA_FEI','ATIVA',NULL,NULL,4,1,6,5),(4,4,'2018-10-04 20:35:04',NULL,'PEDIR_CARONA','IDA_FEI','ATIVA',NULL,NULL,5,1,8,7),(5,5,'2018-10-04 20:36:11',NULL,'PEDIR_CARONA','IDA_FEI','ATIVA',NULL,NULL,6,1,10,9),(6,6,'2018-10-04 20:48:05',NULL,'PEDIR_CARONA','IDA_FEI','ATIVA',NULL,NULL,7,1,12,11),(7,7,'2018-10-04 20:49:45',NULL,'PEDIR_CARONA','IDA_FEI','ATIVA',NULL,NULL,8,1,14,13),(8,8,'2018-10-04 20:51:00',NULL,'PEDIR_CARONA','IDA_FEI','ATIVA',NULL,NULL,9,1,16,15),(9,9,'2018-10-04 20:58:23',NULL,'PEDIR_CARONA','IDA_FEI','ATIVA',NULL,NULL,10,1,18,17),(10,1,'2018-10-04 21:07:33',NULL,'OFERECER_CARONA','IDA_FEI','ATIVA',3,40,11,1,20,19);
/*!40000 ALTER TABLE `intencao_carona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `intencao_carona_motorista`
--

DROP TABLE IF EXISTS `intencao_carona_motorista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `intencao_carona_motorista` (
  `id_intencao_carona_motorista` int(11) NOT NULL AUTO_INCREMENT,
  `id_intencao_carona` int(11) NOT NULL,
  PRIMARY KEY (`id_intencao_carona_motorista`),
  KEY `id_intencao_carona` (`id_intencao_carona`),
  CONSTRAINT `intencao_carona_motorista_ibfk_1` FOREIGN KEY (`id_intencao_carona`) REFERENCES `intencao_carona` (`id_intencao_carona`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `intencao_carona_motorista`
--

LOCK TABLES `intencao_carona_motorista` WRITE;
/*!40000 ALTER TABLE `intencao_carona_motorista` DISABLE KEYS */;
/*!40000 ALTER TABLE `intencao_carona_motorista` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil`
--

LOCK TABLES `perfil` WRITE;
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` VALUES (1,1,NULL),(2,2,NULL),(3,3,NULL),(4,4,NULL),(5,5,NULL),(6,6,NULL),(7,7,NULL),(8,8,NULL),(9,9,NULL),(10,10,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil_passageiro`
--

LOCK TABLES `perfil_passageiro` WRITE;
/*!40000 ALTER TABLE `perfil_passageiro` DISABLE KEYS */;
INSERT INTO `perfil_passageiro` VALUES (1),(2),(3),(4),(5),(6),(7),(8),(9),(10);
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
-- Table structure for table `rejeicao_carona`
--

DROP TABLE IF EXISTS `rejeicao_carona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rejeicao_carona` (
  `id_rejeicao_carona` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario_motorista` int(11) NOT NULL,
  `id_usuario_passageiro` int(11) NOT NULL,
  `sentido` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id_rejeicao_carona`),
  KEY `id_usuario_motorista` (`id_usuario_motorista`),
  KEY `id_usuario_passageiro` (`id_usuario_passageiro`),
  CONSTRAINT `rejeicao_carona_ibfk_1` FOREIGN KEY (`id_usuario_motorista`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `rejeicao_carona_ibfk_2` FOREIGN KEY (`id_usuario_passageiro`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rejeicao_carona`
--

LOCK TABLES `rejeicao_carona` WRITE;
/*!40000 ALTER TABLE `rejeicao_carona` DISABLE KEYS */;
/*!40000 ALTER TABLE `rejeicao_carona` ENABLE KEYS */;
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
  `visualizada` tinyint(4) NOT NULL,
  `score` int(11) NOT NULL,
  `estado` varchar(60) NOT NULL,
  PRIMARY KEY (`id_sugestao_trajeto`),
  KEY `id_sugestao_trajeto_motorista` (`id_sugestao_trajeto_motorista`),
  CONSTRAINT `sugestao_trajeto_ibfk_1` FOREIGN KEY (`id_sugestao_trajeto_motorista`) REFERENCES `sugestao_trajeto_motorista` (`id_sugestao_trajeto_motorista`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sugestao_trajeto`
--

LOCK TABLES `sugestao_trajeto` WRITE;
/*!40000 ALTER TABLE `sugestao_trajeto` DISABLE KEYS */;
/*!40000 ALTER TABLE `sugestao_trajeto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sugestao_trajeto_motorista`
--

DROP TABLE IF EXISTS `sugestao_trajeto_motorista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sugestao_trajeto_motorista` (
  `id_sugestao_trajeto_motorista` int(11) NOT NULL AUTO_INCREMENT,
  `id_intencao_carona` int(11) NOT NULL,
  `estado` varchar(20) NOT NULL,
  PRIMARY KEY (`id_sugestao_trajeto_motorista`),
  KEY `id_intencao_carona` (`id_intencao_carona`),
  CONSTRAINT `sugestao_trajeto_motorista_ibfk_1` FOREIGN KEY (`id_intencao_carona`) REFERENCES `intencao_carona` (`id_intencao_carona`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sugestao_trajeto_motorista`
--

LOCK TABLES `sugestao_trajeto_motorista` WRITE;
/*!40000 ALTER TABLE `sugestao_trajeto_motorista` DISABLE KEYS */;
/*!40000 ALTER TABLE `sugestao_trajeto_motorista` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sugestao_trajeto_passageiro`
--

LOCK TABLES `sugestao_trajeto_passageiro` WRITE;
/*!40000 ALTER TABLE `sugestao_trajeto_passageiro` DISABLE KEYS */;
/*!40000 ALTER TABLE `sugestao_trajeto_passageiro` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'MARCELO LIMA BAXAULI',NULL,'mlb122@hotmail.com','teste','(11) 11111-1111',NULL,1,2,1,1),(2,'Deberson Moreira',NULL,'deb@teste.com','teste','(22) 22222-2222',NULL,1,1,2,2),(3,'Ferdson Freitas',NULL,'fred@hubs.com','teste','(22) 22222-2222',NULL,1,6,3,3),(4,'Felipson Freitas',NULL,'asdf@asdfasdf.com','teste','(33) 33333-3333',NULL,1,14,4,4),(5,'Erickson Felipo',NULL,'eirf@asdfasdf.com','teste','(34) 44444-4444',NULL,1,8,5,5),(6,'Gilberson Almeida',NULL,'asdfas@asdfasdfa.com','teste','(11) 11111-1111',NULL,1,3,6,6),(7,'Edson Aranha',NULL,'ed@asdfasd.com','teste','(33) 33333-3333',NULL,1,4,7,7),(8,'Gilberto Freire',NULL,'gibdd@asdfasdfa.com','teste','(33) 33333-3333',NULL,1,4,8,8),(9,'Cleber Machado',NULL,'cleb@sadfasdfas.com','teste','(33) 33333-3333',NULL,1,5,9,9),(10,'Felipe Anderson',NULL,'felp@asdfas.com','teste','(55) 55555-5555',NULL,1,7,10,10);
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

-- Dump completed on 2018-10-04 23:19:37

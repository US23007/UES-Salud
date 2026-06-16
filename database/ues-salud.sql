CREATE DATABASE  IF NOT EXISTS `ues_salud` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ues_salud`;
-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: ues_salud
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Table structure for table `detalles_recetas`
--

DROP TABLE IF EXISTS `detalles_recetas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalles_recetas` (
  `id_detalle` int NOT NULL AUTO_INCREMENT,
  `id_receta` int DEFAULT NULL,
  `nombre_medicamento` varchar(150) DEFAULT NULL,
  `dosis` varchar(100) DEFAULT NULL,
  `indicaciones` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_detalle`),
  KEY `id_receta` (`id_receta`),
  CONSTRAINT `detalles_recetas_ibfk_1` FOREIGN KEY (`id_receta`) REFERENCES `recetas` (`id_receta`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalles_recetas`
--

LOCK TABLES `detalles_recetas` WRITE;
/*!40000 ALTER TABLE `detalles_recetas` DISABLE KEYS */;
INSERT INTO `detalles_recetas` VALUES (1,2,'Tabcin','1','Despues de comer');
/*!40000 ALTER TABLE `detalles_recetas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctores`
--

DROP TABLE IF EXISTS `doctores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctores` (
  `id_doctor` int NOT NULL AUTO_INCREMENT,
  `id_especialidad` int DEFAULT NULL,
  `nombre_doctor` varchar(100) NOT NULL,
  `apellido_doctor` varchar(100) NOT NULL,
  `junta_vigilancia` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_doctor`),
  UNIQUE KEY `junta_vigilancia` (`junta_vigilancia`),
  KEY `id_especialidad` (`id_especialidad`),
  CONSTRAINT `doctores_ibfk_1` FOREIGN KEY (`id_especialidad`) REFERENCES `especialidades` (`id_especialidad`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctores`
--

LOCK TABLES `doctores` WRITE;
/*!40000 ALTER TABLE `doctores` DISABLE KEYS */;
INSERT INTO `doctores` VALUES (1,1,'Juan Carlos','Pérez Mendoza','JVPM-10452'),(2,2,'María Luisa','Benítez Sorto','JVPM-8943'),(3,3,'Carlos Ernesto','Alvarenga Huezo','JVPM-12301'),(4,1,'Ana Beatriz','Rivas Castaneda','JVPM-11567');
/*!40000 ALTER TABLE `doctores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `especialidades`
--

DROP TABLE IF EXISTS `especialidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `especialidades` (
  `id_especialidad` int NOT NULL AUTO_INCREMENT,
  `nombre_especialidad` varchar(100) NOT NULL,
  PRIMARY KEY (`id_especialidad`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `especialidades`
--

LOCK TABLES `especialidades` WRITE;
/*!40000 ALTER TABLE `especialidades` DISABLE KEYS */;
INSERT INTO `especialidades` VALUES (1,'Medicina General'),(2,'Psicología'),(3,'Odontología'),(4,'Nutrición'),(5,'Emergencias');
/*!40000 ALTER TABLE `especialidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pacientes`
--

DROP TABLE IF EXISTS `pacientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pacientes` (
  `id_paciente` int NOT NULL AUTO_INCREMENT,
  `carnet` varchar(15) NOT NULL,
  `nombres` varchar(100) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `sexo` varchar(10) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `direccion` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id_paciente`),
  UNIQUE KEY `carnet` (`carnet`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pacientes`
--

LOCK TABLES `pacientes` WRITE;
/*!40000 ALTER TABLE `pacientes` DISABLE KEYS */;
INSERT INTO `pacientes` VALUES (1,'US23007','Samuel De Jesus','Umaña Sorto','2004-03-21','Hombre','76438438','San Salvador');
/*!40000 ALTER TABLE `pacientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recetas`
--

DROP TABLE IF EXISTS `recetas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recetas` (
  `id_receta` int NOT NULL AUTO_INCREMENT,
  `id_triaje` int DEFAULT NULL,
  `id_doctor` int DEFAULT NULL,
  `diagnostico` text,
  `fecha_emision` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_receta`),
  UNIQUE KEY `id_triaje` (`id_triaje`),
  KEY `id_doctor` (`id_doctor`),
  CONSTRAINT `recetas_ibfk_1` FOREIGN KEY (`id_triaje`) REFERENCES `triaje` (`id_triaje`),
  CONSTRAINT `recetas_ibfk_2` FOREIGN KEY (`id_doctor`) REFERENCES `doctores` (`id_doctor`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recetas`
--

LOCK TABLES `recetas` WRITE;
/*!40000 ALTER TABLE `recetas` DISABLE KEYS */;
INSERT INTO `recetas` VALUES (1,2,1,'Diarrea','2026-06-16 07:30:34'),(2,4,1,'Diarrea','2026-06-16 07:35:15');
/*!40000 ALTER TABLE `recetas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `triaje`
--

DROP TABLE IF EXISTS `triaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `triaje` (
  `id_triaje` int NOT NULL AUTO_INCREMENT,
  `id_paciente` int NOT NULL,
  `id_especialidad` int NOT NULL,
  `sintomas` text,
  `temperatura` decimal(4,2) DEFAULT NULL,
  `presion_arterial` varchar(20) DEFAULT NULL,
  `nivel_urgencia` varchar(20) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_triaje`),
  KEY `id_paciente` (`id_paciente`),
  KEY `id_especialidad` (`id_especialidad`),
  CONSTRAINT `triaje_ibfk_1` FOREIGN KEY (`id_paciente`) REFERENCES `pacientes` (`id_paciente`),
  CONSTRAINT `triaje_ibfk_2` FOREIGN KEY (`id_especialidad`) REFERENCES `especialidades` (`id_especialidad`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `triaje`
--

LOCK TABLES `triaje` WRITE;
/*!40000 ALTER TABLE `triaje` DISABLE KEYS */;
INSERT INTO `triaje` VALUES (1,1,1,'Diarrea',35.00,'120/70','BAJA','2026-06-16 01:30:34'),(2,1,1,'Diarrea',35.00,'120/70','BAJA','2026-06-16 01:30:34'),(3,1,1,'Diarrea',35.00,'120/70','BAJA','2026-06-16 01:35:15'),(4,1,1,'Diarrea',35.00,'120/70','BAJA','2026-06-16 01:35:15');
/*!40000 ALTER TABLE `triaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'ues_salud'
--

--
-- Dumping routines for database 'ues_salud'
--
/*!50003 DROP PROCEDURE IF EXISTS `sp_insertar_receta` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertar_receta`(
IN p_idTriaje INT,
IN p_nombreDoctor VARCHAR(100),
IN p_diagnostico TEXT
)
BEGIN
	DECLARE v_idDoctor INT;
	SELECT id_doctor INTO v_idDoctor FROM doctores WHERE concat(nombre_doctor,' ',apellido_doctor) = p_nombreDoctor;
    IF v_idDoctor IS NOT NULL THEN 
		INSERT INTO recetas(id_triaje,id_doctor,diagnostico) VALUES (p_idTriaje,v_idDoctor,p_diagnostico);
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insertar_triaje` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertar_triaje`(
    IN p_carnet VARCHAR(20),
    IN p_especialidad VARCHAR(45), -- Ojo con la longitud, asegúrate que calce con tu tabla
    IN p_sintomas TEXT,
    IN p_temperatura DECIMAL(4,2),
    IN p_presion VARCHAR(20),
    IN p_nivel_urgencia VARCHAR(20)
)
BEGIN
    DECLARE v_idPaciente INT DEFAULT NULL;
    DECLARE v_idEspecialidad INT DEFAULT NULL;
    
    -- 1. Buscar los IDs correspondientes en una sola línea por cada uno
    SELECT id_paciente INTO v_idPaciente FROM pacientes WHERE carnet = p_carnet;
    SELECT id_especialidad INTO v_idEspecialidad FROM especialidades WHERE nombre_especialidad = p_especialidad;
    
    -- 2. VALIDACIÓN CORRECTA: Verificar que ambos registros existan en la BD
    IF v_idPaciente IS NOT NULL AND v_idEspecialidad IS NOT NULL THEN
        
        INSERT INTO triaje(
            id_paciente, 
            id_especialidad, 
            sintomas, 
            temperatura, 
            presion_arterial, 
            nivel_urgencia,
            fecha_registro)
        VALUES (
            v_idPaciente, 
            v_idEspecialidad, 
            p_sintomas, 
            p_temperatura, 
            p_presion, 
            p_nivel_urgencia,
            NOW()
        );
        
        SELECT LAST_INSERT_ID() AS id_triaje;
        
    ELSE
    
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error: El carnet del estudiante o la especialidad médica no existen en el sistema.';
    END IF;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listar_pacientes` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listar_pacientes`()
BEGIN
	SELECT 
		p.carnet,
		CONCAT(p.nombres, ' ', p.apellidos) AS Nombre_Completo,
    TIMESTAMPDIFF(YEAR, p.fecha_nacimiento, CURDATE()) AS Edad,
		p.sexo AS Género,
		COUNT(t.id_triaje) AS Consultas
	FROM pacientes p
		INNER JOIN triaje t ON p.id_paciente = t.id_paciente
	GROUP BY p.id_paciente, p.carnet, p.nombres, p.apellidos, p.fecha_nacimiento, p.sexo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-16  1:37:17

-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               Microsoft SQL Server 2022 (RTM) - 16.0.1000.6
-- Server OS:                    Linux (Ubuntu 20.04.5 LTS) <X64>
-- HeidiSQL Version:             12.0.0.6468
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES  */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for mymsdb
CREATE DATABASE IF NOT EXISTS "mymsdb";
USE "mymsdb";

-- Dumping structure for table mymsdb.category
CREATE TABLE IF NOT EXISTS "category" (
	"category_id" BIGINT NOT NULL,
	"name" VARCHAR(25) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"last_update" DATETIME NOT NULL,
	PRIMARY KEY ("category_id")
);

-- Dumping data for table mymsdb.category: -1 rows
/*!40000 ALTER TABLE "category" DISABLE KEYS */;
INSERT INTO "category" ("category_id", "name", "last_update") VALUES
	(52, 'Category', '2023-02-03 17:02:39.827'),
	(53, 'Category', '2023-02-03 17:02:59.460'),
	(54, 'Category', '2023-02-03 17:03:10.557'),
	(55, 'Category', '2023-02-03 17:03:34.893');
/*!40000 ALTER TABLE "category" ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

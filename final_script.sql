CREATE DATABASE  IF NOT EXISTS `forum`;
USE `forum`;

SET FOREIGN_KEY_CHECKS = 0;
--
-- Table structure for table `uzivatel`
--

DROP TABLE IF EXISTS `uzivatel`;
CREATE TABLE `uzivatel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `uzivatelske_meno` varchar(50) NOT NULL,
  `heslo` varchar(60) NOT NULL,
  `is_email_verifed` tinyint NOT NULL DEFAULT '0',
  `datum_registarcie` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `posledny_datum_prihlasenia` date DEFAULT NULL,
  `rola` varchar(50) DEFAULT 'USER',
  `prava` varchar(10000) DEFAULT '',
  `uzivatelske_info_id` int DEFAULT NULL,
  `activation_code_id` varchar(100) DEFAULT NULL,
  `je_verejny` tinyint DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `iduser_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `uzivatelske_meno_UNIQUE` (`uzivatelske_meno`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `skupina`
--
DROP TABLE IF EXISTS `skupina`;
CREATE TABLE `skupina` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_uzivatela` int NOT NULL,
  `nazov` varchar(50) NOT NULL,
  `popis` text,
  `datum_zalozenia` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `je_verejna` tinyint DEFAULT '1',
  `list_moderatorov` VARCHAR(5000) DEFAULT '',
  `list_register_user` VARCHAR(5000) DEFAULT '',
  `list_register_moderator` VARCHAR(5000) DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idskupina_UNIQUE` (`id`),
  UNIQUE KEY `nazov_UNIQUE` (`nazov`),
  CONSTRAINT `fk_skupiny_iduzivatela` FOREIGN KEY (`id_uzivatela`) REFERENCES `uzivatel` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `moderatori_skupiny`
--
DROP TABLE IF EXISTS `moderatori_skupiny`;
 CREATE TABLE `moderatori_skupiny` (
  `id_uzivatela` int NOT NULL ,
  `id_skupiny` int NOT NULL,
  PRIMARY KEY (`id_uzivatela`, `id_skupiny`),
  CONSTRAINT `fk_moderator_iduzivatela` FOREIGN KEY (`id_uzivatela`) REFERENCES `uzivatel` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_moderator_idskupiny` FOREIGN KEY (`id_skupiny`) REFERENCES `skupina` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `clenovia_skupiny`
--
DROP TABLE IF EXISTS `clenovia_skupiny`;
 CREATE TABLE `clenovia_skupiny` (
  `id_uzivatela` int NOT NULL ,
  `id_skupiny` int NOT NULL,
  PRIMARY KEY (`id_uzivatela`, `id_skupiny`),
  CONSTRAINT `fk_clen_iduzivatela` FOREIGN KEY (`id_uzivatela`) REFERENCES `uzivatel` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_clen_idskupiny` FOREIGN KEY (`id_skupiny`) REFERENCES `skupina` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `vlakno`
--
DROP TABLE IF EXISTS `vlakno`;
CREATE TABLE `vlakno` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_uzivatela` int NOT NULL,
  `id_skupiny` int NOT NULL,
  `predmet` varchar(50) NOT NULL,
  `kontent` text DEFAULT NULL,
  `pohlady` int NOT NULL DEFAULT '0',
  `datum_zalozenia` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `posledny_datum_upravy` timestamp NULL DEFAULT NULL,
  `je_zavrete` tinyint DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idvlakno_UNIQUE` (`id`),
  KEY `fk_idsection_index` (`id_skupiny`),
  KEY `fk_iduser_index` (`id_uzivatela`),
  CONSTRAINT `fk_vlakna_idskupiny` FOREIGN KEY (`id_skupiny`) REFERENCES `skupina` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_vlakna_iduzivatela` FOREIGN KEY (`id_uzivatela`) REFERENCES `uzivatel` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `prispevok`
--
DROP TABLE IF EXISTS `prispevok`;
CREATE TABLE `prispevok` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_vlakna` int NOT NULL,
  `id_uzivatela` int NOT NULL,
  `obsah` text NOT NULL,
  `ranking` int DEFAULT 0,
  `datum_zalozenia` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `posledny_datum_upravy` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_iduzivatela_index` (`id_uzivatela`),
  KEY `fk_idvlakna_index` (`id_vlakna`),
  CONSTRAINT `fk_prispevku_idvlakna` FOREIGN KEY (`id_vlakna`) REFERENCES `vlakno` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_prispevku_iduzivatela` FOREIGN KEY (`id_uzivatela`) REFERENCES `uzivatel` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=300 DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `uzivatelske_info`
--
DROP TABLE IF EXISTS `uzivatelske_info`;
CREATE TABLE `uzivatelske_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_uzivatela` int DEFAULT NULL,
  `mobil` varchar(20) DEFAULT NULL,
  `meno` varchar(50) DEFAULT NULL,
  `priezvisko` varchar(50) DEFAULT NULL,
  -- `birthday` datetime DEFAULT NULL,
  `mesto` varchar(50) DEFAULT NULL,
  `bibliografia` text,
  `footer` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_uzivatelske_info_id_uzivatela` FOREIGN KEY (`id_uzivatela`) REFERENCES `uzivatel` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `activation_code`
--
DROP TABLE IF EXISTS `activation_code`;
CREATE TABLE `activation_code` (
  `id` varchar(300) NOT NULL,
  `id_uzivatela` int DEFAULT NULL,
  `date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_activation_code_id_uzivatela` FOREIGN KEY (`id_uzivatela`) REFERENCES `uzivatel` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ALTER TABLE uzivatel
-- DROP FOREIGN KEY `uzivatel_ibfk_2`;

ALTER TABLE uzivatel
ADD FOREIGN KEY (`activation_code_id`) REFERENCES `activation_code`(`id`);

-- ALTER TABLE uzivatel
-- DROP FOREIGN KEY `uzivatel_ibfk_1`;

ALTER TABLE uzivatel
ADD FOREIGN KEY (`uzivatelske_info_id`) REFERENCES `uzivatelske_info`(`id`);

--
-- Table structure for table `ranking`
--


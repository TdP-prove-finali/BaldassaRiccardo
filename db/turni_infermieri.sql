-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versione server:              10.3.14-MariaDB - mariadb.org binary distribution
-- S.O. server:                  Win64
-- HeidiSQL Versione:            10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dump della struttura del database turni_infermieri
CREATE DATABASE IF NOT EXISTS `turni_infermieri` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `turni_infermieri`;

-- Dump della struttura di tabella turni_infermieri.ferie
CREATE TABLE IF NOT EXISTS `ferie` (
  `id_ferie` int(11) NOT NULL,
  `id_infermiere` int(11) NOT NULL,
  `data` date NOT NULL,
  PRIMARY KEY (`id_ferie`),
  KEY `id_infermiere` (`id_infermiere`),
  CONSTRAINT `FK_ferie_infermiere` FOREIGN KEY (`id_infermiere`) REFERENCES `infermiere` (`id_infermiere`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='vengono riportate tutte le preferenze di giorni di ferie di tutti gli infermieri';

-- Dump dei dati della tabella turni_infermieri.ferie: ~54 rows (circa)
DELETE FROM `ferie`;
/*!40000 ALTER TABLE `ferie` DISABLE KEYS */;
INSERT INTO `ferie` (`id_ferie`, `id_infermiere`, `data`) VALUES
	(1, 1, '2019-09-03'),
	(2, 1, '2019-09-15'),
	(3, 1, '2019-10-10'),
	(4, 1, '2019-10-19'),
	(5, 1, '2019-11-01'),
	(6, 1, '2019-11-11'),
	(7, 2, '2019-09-12'),
	(8, 2, '2019-09-17'),
	(9, 2, '2019-10-02'),
	(10, 2, '2019-10-29'),
	(11, 2, '2019-11-14'),
	(12, 2, '2019-11-24'),
	(13, 3, '2019-09-08'),
	(14, 3, '2019-09-25'),
	(15, 3, '2019-10-12'),
	(16, 3, '2019-10-18'),
	(17, 3, '2019-11-04'),
	(18, 3, '2019-11-10'),
	(19, 4, '2019-09-05'),
	(20, 4, '2019-09-11'),
	(21, 4, '2019-10-20'),
	(22, 4, '2019-10-24'),
	(23, 4, '2019-11-02'),
	(24, 4, '2019-11-13'),
	(25, 5, '2019-09-11'),
	(26, 5, '2019-09-16'),
	(27, 5, '2019-10-14'),
	(28, 5, '2019-10-29'),
	(29, 5, '2019-11-07'),
	(30, 5, '2019-11-30'),
	(31, 6, '2019-09-18'),
	(32, 6, '2019-09-23'),
	(33, 6, '2019-10-30'),
	(34, 6, '2019-10-31'),
	(35, 6, '2019-11-04'),
	(36, 6, '2019-11-10'),
	(37, 7, '2019-09-14'),
	(38, 7, '2019-09-30'),
	(39, 7, '2019-10-16'),
	(40, 7, '2019-10-27'),
	(41, 7, '2019-11-05'),
	(42, 7, '2019-11-26'),
	(43, 8, '2019-09-07'),
	(44, 8, '2019-09-18'),
	(45, 8, '2019-10-14'),
	(46, 8, '2019-10-28'),
	(47, 8, '2019-11-01'),
	(48, 8, '2019-11-08'),
	(49, 9, '2019-09-09'),
	(50, 9, '2019-09-22'),
	(51, 9, '2019-10-04'),
	(52, 9, '2019-10-16'),
	(53, 9, '2019-11-07'),
	(54, 9, '2019-11-21');
/*!40000 ALTER TABLE `ferie` ENABLE KEYS */;

-- Dump della struttura di tabella turni_infermieri.infermiere
CREATE TABLE IF NOT EXISTS `infermiere` (
  `id_infermiere` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) COLLATE utf8_bin NOT NULL,
  `cognome` varchar(50) COLLATE utf8_bin NOT NULL,
  `data_nascita` date NOT NULL,
  `trimestre_ferie_lunghe` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_infermiere`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='anagrafica degli infermieri';

-- Dump dei dati della tabella turni_infermieri.infermiere: ~11 rows (circa)
DELETE FROM `infermiere`;
/*!40000 ALTER TABLE `infermiere` DISABLE KEYS */;
INSERT INTO `infermiere` (`id_infermiere`, `nome`, `cognome`, `data_nascita`, `trimestre_ferie_lunghe`) VALUES
	(1, 'Mario', 'Rossi', '1990-07-24', 1),
	(2, 'Martina', 'Saro', '1975-02-19', 1),
	(3, 'Giovanni', 'Allegro', '1982-11-05', 1),
	(4, 'Luca', 'Marrone', '1992-11-25', 2),
	(5, 'Paola', 'Montalba', '1994-02-04', 2),
	(6, 'Cristina', 'Pace', '1989-03-06', 3),
	(7, 'Sebastiano', 'Salienti', '1995-12-12', 3),
	(8, 'Laura', 'Bartoli', '1983-01-30', 3),
	(9, 'Benedetta', 'Sacco', '1971-05-15', 4),
	(10, 'Daniele', 'Giacorra', '1966-08-20', 4),
	(11, 'Stefania', 'Allecco', '1988-10-01', 4);
/*!40000 ALTER TABLE `infermiere` ENABLE KEYS */;

-- Dump della struttura di tabella turni_infermieri.orario
CREATE TABLE IF NOT EXISTS `orario` (
  `id_orario` int(11) NOT NULL AUTO_INCREMENT,
  `id_infermiere` int(11) NOT NULL,
  `data` date NOT NULL,
  `turno` char(1) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id_orario`),
  KEY `id_infermiere` (`id_infermiere`),
  CONSTRAINT `FK_orario_infermiere` FOREIGN KEY (`id_infermiere`) REFERENCES `infermiere` (`id_infermiere`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='viene indicato il turno che ogni infermiere dovrà fare nei diversi giorni';

-- Dump dei dati della tabella turni_infermieri.orario: ~0 rows (circa)
DELETE FROM `orario`;
/*!40000 ALTER TABLE `orario` DISABLE KEYS */;
/*!40000 ALTER TABLE `orario` ENABLE KEYS */;

-- Dump della struttura di tabella turni_infermieri.statistiche
CREATE TABLE IF NOT EXISTS `statistiche` (
  `id_statistiche` int(11) NOT NULL,
  `id_infermiere` int(11) NOT NULL,
  `turno_mattino` int(11) NOT NULL,
  `turno_pomeriggio` int(11) NOT NULL,
  `turno_notte` int(11) NOT NULL,
  `riposi` int(11) NOT NULL,
  `ore_lavorative_totali` int(11) NOT NULL,
  `diff_teorico_effettivo` int(11) NOT NULL,
  `giorni_ferie` int(11) NOT NULL,
  `giorni_ferie_richiesti` int(11) NOT NULL,
  PRIMARY KEY (`id_statistiche`),
  KEY `id_infermiere` (`id_infermiere`),
  CONSTRAINT `FK_statistiche_infermiere` FOREIGN KEY (`id_infermiere`) REFERENCES `infermiere` (`id_infermiere`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='per ogni infermiere vengono riportate delle statistiche riguardo i turni lavorativi ';

-- Dump dei dati della tabella turni_infermieri.statistiche: ~0 rows (circa)
DELETE FROM `statistiche`;
/*!40000 ALTER TABLE `statistiche` DISABLE KEYS */;
/*!40000 ALTER TABLE `statistiche` ENABLE KEYS */;

-- Dump della struttura di tabella turni_infermieri.vincoli
CREATE TABLE IF NOT EXISTS `vincoli` (
  `id_vincolo` int(11) NOT NULL,
  `nome` varchar(50) COLLATE utf8_bin NOT NULL,
  `valore` int(11) NOT NULL,
  `unita` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id_vincolo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='insieme di tutti i vincoli necessari per verificare che l''orario generato sia accettabile';

-- Dump dei dati della tabella turni_infermieri.vincoli: ~8 rows (circa)
DELETE FROM `vincoli`;
/*!40000 ALTER TABLE `vincoli` DISABLE KEYS */;
INSERT INTO `vincoli` (`id_vincolo`, `nome`, `valore`, `unita`) VALUES
	(1, 'ferie_annue', 32, 'giorni'),
	(2, 'ore_lavorative_settimanali_contrattuali', 36, 'ore'),
	(3, 'max_ore_lavorative_settimanali', 48, 'ore'),
	(4, 'max_giorni_lavorativi_consecutivi', 6, 'giorni'),
	(5, 'min_ore_riposo_consecutive', 11, 'ore'),
	(6, 'min_giorni_riposo_settimanali', 1, 'giorni'),
	(7, 'debito_orario_teorico_annuale', 1872, 'ore'),
	(8, 'durata_turno_lavorativo', 8, 'ore');
/*!40000 ALTER TABLE `vincoli` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

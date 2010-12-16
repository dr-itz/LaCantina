
SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `lacantina`
--

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `login` varchar(20) NOT NULL,
  `password_hash` varchar(41) NOT NULL,
  `email` varchar(255) NOT NULL,
  `is_admin` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_uni` (`login`),
  UNIQUE KEY `email_uni` (`email`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--


-- --------------------------------------------------------

--
-- Table structure for table `winecellars`
--

DROP TABLE IF EXISTS `winecellars`;
CREATE TABLE IF NOT EXISTS `winecellars` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `capacity` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `winecellars`
--


-- --------------------------------------------------------

--
-- Table structure for table `wines`
--

DROP TABLE IF EXISTS `wines`;
CREATE TABLE IF NOT EXISTS `wines` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `producer` varchar(50) NOT NULL,
  `country` varchar(50) NOT NULL,
  `region` varchar(50) NOT NULL,
  `description` text,
  `bottle_size` smallint(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

--
-- Dumping data for table `wines`
--


-- --------------------------------------------------------

--
-- Table structure for table `wine_years`
--

DROP TABLE IF EXISTS `wine_years`;
CREATE TABLE IF NOT EXISTS `wine_years` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `winecellar_id` int(11) NOT NULL,
  `wine_id` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `rating_points` int(11) DEFAULT NULL,
  `rating_text` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `wine_uni` (`winecellar_id`,`wine_id`,`year`),
  KEY `wine_id` (`wine_id`),
  KEY `winecellar_id` (`winecellar_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `wine_years`
--


--
-- Constraints for dumped tables
--

--
-- Constraints for table `winecellars`
--
ALTER TABLE `winecellars`
  ADD CONSTRAINT `winecellars_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `wines`
--
ALTER TABLE `wines`
  ADD CONSTRAINT `wines_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `wine_years`
--
ALTER TABLE `wine_years`
  ADD CONSTRAINT `wine_years_ibfk_1` FOREIGN KEY (`winecellar_id`) REFERENCES `winecellars` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `wine_years_ibfk_2` FOREIGN KEY (`wine_id`) REFERENCES `wines` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

SET FOREIGN_KEY_CHECKS=1;

--
-- Default data to fill the database with dummy data
--
INSERT INTO `users` (`id`, `first_name`, `last_name`, `login`, `password_hash`, `email`, `is_admin`)
  VALUES (1, 'mister', 'admin', 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', 'admin@blub.com', 1);

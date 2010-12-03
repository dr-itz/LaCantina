
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `users`
--


-- --------------------------------------------------------

--
-- Table structure for table `winecellars`
--

CREATE TABLE IF NOT EXISTS `winecellars` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `winecellars`
--


-- --------------------------------------------------------

--
-- Table structure for table `wines`
--

CREATE TABLE IF NOT EXISTS `wines` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `producer` varchar(50) NOT NULL,
  `country` varchar(50) NOT NULL,
  `region` varchar(50) NOT NULL,
  `description` text,
  `bottle_size` smallint(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `wines`
--


-- --------------------------------------------------------

--
-- Table structure for table `wine_years`
--

CREATE TABLE IF NOT EXISTS `wine_years` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `winecellar_id` int(11) NOT NULL,
  `wine_id` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `rating_points` int(11) DEFAULT NULL,
  `rating_text` text,
  PRIMARY KEY (`id`),
  KEY `wine_id` (`wine_id`),
  KEY `winecellar_id` (`winecellar_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

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
-- Constraints for table `wine_years`
--
ALTER TABLE `wine_years`
  ADD CONSTRAINT `wine_years_ibfk_1` FOREIGN KEY (`winecellar_id`) REFERENCES `winecellars` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `wine_years_ibfk_2` FOREIGN KEY (`wine_id`) REFERENCES `wines` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;
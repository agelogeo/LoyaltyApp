-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 03, 2017 at 11:18 AM
-- Server version: 10.1.20-MariaDB
-- PHP Version: 7.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id755156_loyalty_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `coupons`
--

CREATE TABLE `coupons` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `required_stamps` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `coupons`
--

INSERT INTO `coupons` (`id`, `name`, `required_stamps`) VALUES
(23, 'Volemataki', 5),
(26, 'Extreme', 15),
(29, 'Free Drink', 15),
(31, 'Snack', 8),
(32, 'Coupon #55', 5),
(35, 'Coffee', 1);

-- --------------------------------------------------------

--
-- Table structure for table `coupons_track`
--

CREATE TABLE `coupons_track` (
  `id` int(10) UNSIGNED NOT NULL,
  `coupons_id` int(11) NOT NULL,
  `datetime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `coupons_track`
--

INSERT INTO `coupons_track` (`id`, `coupons_id`, `datetime`) VALUES
(2, 23, '2017-11-02 21:00:00'),
(4, 29, '2017-11-02 18:53:35'),
(5, 35, '2017-11-02 18:53:35'),
(6, 35, '2017-11-02 18:53:35'),
(7, 35, '2017-11-02 18:53:35'),
(8, 35, '2017-11-02 18:53:35'),
(9, 35, '2017-10-25 18:53:35'),
(10, 35, '2017-11-02 18:53:35'),
(11, 35, '2017-11-02 18:53:35'),
(12, 35, '2017-11-02 18:53:35'),
(13, 35, '2017-11-02 18:53:35'),
(14, 31, '2017-11-02 18:53:35'),
(15, 31, '2017-11-02 18:53:35'),
(16, 31, '2017-11-02 18:53:35'),
(17, 31, '2017-11-02 18:53:35'),
(18, 31, '2017-11-02 18:53:35'),
(19, 31, '2017-10-02 18:53:35'),
(20, 31, '2017-11-02 18:53:35'),
(21, 31, '2017-11-02 18:53:35'),
(22, 31, '2017-11-02 18:53:35'),
(23, 31, '2017-11-02 18:53:35'),
(24, 31, '2017-11-02 18:53:35'),
(25, 31, '2017-11-02 18:53:35'),
(26, 31, '2017-11-02 18:53:35'),
(27, 31, '2017-11-01 18:53:35'),
(28, 31, '2017-11-02 18:53:35'),
(29, 31, '2017-11-02 18:53:35'),
(30, 31, '2017-11-02 18:53:35'),
(31, 31, '2017-11-02 18:53:35'),
(33, 29, '2017-11-02 18:53:35'),
(34, 29, '2017-11-02 18:53:35'),
(35, 29, '2017-11-02 18:53:35'),
(36, 29, '2017-11-02 18:53:35'),
(37, 29, '2017-11-02 18:53:35'),
(38, 29, '2017-11-02 18:53:35'),
(39, 29, '2017-11-02 18:53:35'),
(40, 29, '2017-11-02 18:53:35'),
(41, 29, '2017-11-02 18:53:35'),
(42, 29, '2017-11-02 18:53:35'),
(43, 29, '2017-11-02 18:53:35'),
(44, 29, '2017-11-02 18:53:35'),
(45, 26, '2017-11-02 18:53:35'),
(46, 26, '2017-11-02 18:53:35'),
(47, 26, '2017-11-02 18:53:35'),
(48, 26, '2017-11-02 18:53:35'),
(49, 26, '2017-11-02 18:53:35'),
(50, 23, '2017-11-02 18:53:35'),
(51, 23, '2017-11-02 18:53:35'),
(52, 23, '2017-11-02 18:53:35'),
(53, 23, '2017-11-02 18:53:35'),
(54, 23, '2017-11-02 18:53:35'),
(55, 23, '2017-11-02 18:53:35'),
(56, 23, '2017-11-02 18:53:35'),
(57, 23, '2017-11-02 18:53:35'),
(58, 23, '2017-11-02 18:53:35'),
(59, 23, '2017-11-02 18:53:35'),
(60, 23, '2017-11-02 18:53:35'),
(61, 23, '2017-11-02 18:53:35'),
(62, 23, '2017-11-02 18:53:35'),
(63, 23, '2017-11-02 18:53:35'),
(64, 23, '2017-11-02 18:53:35'),
(65, 23, '2017-11-02 18:53:35'),
(66, 23, '2017-11-02 18:53:35'),
(67, 23, '2017-11-02 18:53:35'),
(68, 23, '2017-11-02 18:53:35'),
(69, 32, '2017-08-02 18:53:35'),
(70, 35, '2017-11-02 15:00:00'),
(71, 35, '2017-11-02 15:00:00'),
(72, 35, '2017-11-02 15:00:00'),
(73, 35, '2017-11-02 15:00:00'),
(74, 35, '2017-11-02 15:00:00'),
(75, 35, '2017-11-02 15:00:00'),
(76, 35, '2017-11-02 21:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `surname` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `barcode` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `stamps` int(11) UNSIGNED NOT NULL DEFAULT '0',
  `coupons_used` int(11) UNSIGNED NOT NULL DEFAULT '0',
  `visits` int(11) UNSIGNED NOT NULL DEFAULT '0',
  `last_visit` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`, `name`, `surname`, `phone`, `barcode`, `stamps`, `coupons_used`, `visits`, `last_visit`) VALUES
(185, 'Pasxalis', 'Angelopoulos', '2310209135', '45556', 24, 205, 1544, '2017-10-30'),
(186, 'Pappous', 'Angelopoulos', '6944332158', '74323', 12, 0, 3, '2017-02-03'),
(193, 'Giannis', 'Salpi', '44444', '59077', 8, 0, 8, '2017-10-05'),
(195, 'sissi', 'karioti', '56556', '58185', 0, 0, 0, '0000-00-00'),
(199, 'georgios', 'liak', '6983867324', '62998', 10, 0, 1, '2017-02-03'),
(201, 'Joey', 'Tribbiani', '012345', '12345', 3, 10, 101, '0000-00-00'),
(202, 'Rachel', 'Green', '012346', '12346', 8, 11, 108, '2017-02-08'),
(204, 'Monica', 'Geller', '012348', '12348', 2, 5, 10, '0000-00-00'),
(205, 'Chandler', 'Bing', '252885', '25285', 7, 8, 87, '0000-00-00'),
(206, 'Phoebe', 'Buffay', '012543', '12333', 1, 10, 12, '2017-02-04'),
(209, 'Zoe', 'Makri', '01', '56538', 0, 0, 0, '0000-00-00'),
(262, 'kostas', 'kostas', '9878', '17687', 5, 0, 5, '2017-02-10'),
(319, 'Ionic', 'Angular', '3', '13170', 0, 0, 0, '0000-00-00'),
(424, 'Joel', 'Ellie', '2013', '77208', 0, 0, 0, '0000-00-00'),
(583, 'Ionic', 'Animations', '001122', '22884', 0, 0, 0, '0000-00-00');

-- --------------------------------------------------------

--
-- Table structure for table `operators`
--

CREATE TABLE `operators` (
  `id` int(255) NOT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `access_level` varchar(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT '2',
  `first_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(10) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `operators`
--

INSERT INTO `operators` (`id`, `username`, `password`, `access_level`, `first_name`, `last_name`, `phone`) VALUES
(35, 'admin', 'ney-8392', '1', 'Giorgos', 'Angelopoulos', '6976942477'),
(44, 'sofia278', 'ney-8392', '2', 'Sofia', 'Karioti', '6976942476'),
(47, 'dominic', 'admin', '1', 'Dom', 'Toreto', '2001'),
(48, 'nikos', 'nikos', '2', 'nikos', 'nikos', '00000'),
(49, 'alex123', '123', '1', 'Alexandros Junior', 'Ntouskas', '112233'),
(50, 'AlexNt', 'alexnt', '1', 'Alexandros', 'Malakas', '6945807789');

-- --------------------------------------------------------

--
-- Table structure for table `settings`
--

CREATE TABLE `settings` (
  `id` int(3) NOT NULL,
  `same_day_twice` tinyint(1) NOT NULL DEFAULT '1',
  `double_wildcard_search` tinyint(1) NOT NULL DEFAULT '0',
  `stamp_value` int(5) NOT NULL DEFAULT '1',
  `language` varchar(3) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'en'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `settings`
--

INSERT INTO `settings` (`id`, `same_day_twice`, `double_wildcard_search`, `stamp_value`, `language`) VALUES
(1, 1, 0, 1, 'en');

-- --------------------------------------------------------

--
-- Table structure for table `track_visits`
--

CREATE TABLE `track_visits` (
  `id` int(11) NOT NULL,
  `datetime` datetime NOT NULL,
  `visits` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `track_visits`
--

INSERT INTO `track_visits` (`id`, `datetime`, `visits`) VALUES
(1, '2017-11-03 13:00:00', 2),
(2, '2017-11-03 12:00:00', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `coupons`
--
ALTER TABLE `coupons`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `coupons_track`
--
ALTER TABLE `coupons_track`
  ADD PRIMARY KEY (`id`),
  ADD KEY `coupons` (`coupons_id`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `barcode` (`barcode`),
  ADD UNIQUE KEY `phone` (`phone`);

--
-- Indexes for table `operators`
--
ALTER TABLE `operators`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `phone` (`phone`);

--
-- Indexes for table `settings`
--
ALTER TABLE `settings`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `track_visits`
--
ALTER TABLE `track_visits`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `coupons`
--
ALTER TABLE `coupons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;
--
-- AUTO_INCREMENT for table `coupons_track`
--
ALTER TABLE `coupons_track`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;
--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=585;
--
-- AUTO_INCREMENT for table `operators`
--
ALTER TABLE `operators`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;
--
-- AUTO_INCREMENT for table `settings`
--
ALTER TABLE `settings`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `track_visits`
--
ALTER TABLE `track_visits`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `coupons_track`
--
ALTER TABLE `coupons_track`
  ADD CONSTRAINT `coupons_track_ibfk_1` FOREIGN KEY (`coupons_id`) REFERENCES `coupons` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

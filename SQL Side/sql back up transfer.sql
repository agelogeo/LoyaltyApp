-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Feb 08, 2017 at 11:06 PM
-- Server version: 10.1.20-MariaDB
-- PHP Version: 7.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id99137_aggeloandroid`
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
(23, 'Volemataki', 1),
(24, 'maraki', 7),
(26, 'Extreme', 10),
(29, 'Coupon #1', 15),
(31, 'Coupon #8', 8),
(32, 'Coupon #55', 5),
(35, 'Kelly', 1);

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
(185, 'Pasxalis', 'Angelopoulos', '2310209135', '45556', 14, 0, 60, '2017-02-08'),
(186, 'g', 'g', '55', '74323', 12, 0, 3, '2017-02-03'),
(187, 'Alex', 'Greek', '123', '79471', 0, 0, 0, '0000-00-00'),
(189, 'foivos', 'trela', '120', '76371', 0, 0, 0, '0000-00-00'),
(190, 'γοβργος', 'αγελ', '0000', '95961', 0, 0, 0, '0000-00-00'),
(192, 'qaz', 'qwer', '1122', '78652', 0, 0, 0, '0000-00-00'),
(193, 'salpi', 'salpi', '44444', '59077', 1, 0, 1, '2017-02-04'),
(194, 't', 'tt', '0967', '53304', 0, 0, 0, '0000-00-00'),
(195, 'sissi', 'karioti', '56556', '58185', 0, 0, 0, '0000-00-00'),
(196, 'stavros', 'anton', '007', '95165', 0, 0, 0, '0000-00-00'),
(197, 'mama', 'mama', '565', '90601', 1, 0, 1, '2017-02-07'),
(198, 'drift', 'tokyo', '2004', '44647', 0, 0, 0, '0000-00-00'),
(199, 'georgios', 'liak', '6983867324', '62998', 10, 0, 1, '2017-02-03'),
(201, 'Joey', 'Tribbiani', '012345', '12345', 3, 10, 101, '0000-00-00'),
(202, 'Rachel', 'Green', '012346', '12346', 8, 11, 108, '2017-02-08'),
(203, 'Dr. Ross', 'Geller', '012347', '12347', 7, 19, 151, '0000-00-00'),
(204, 'Monica', 'Geller', '012348', '12348', 2, 5, 10, '0000-00-00'),
(205, 'Chandler', 'Bing', '252885', '25285', 7, 8, 87, '0000-00-00'),
(206, 'Phoebe', 'Buffay', '012543', '12333', 1, 10, 12, '2017-02-04'),
(209, 'Zoe', 'Makri', '01', '56538', 0, 0, 0, '0000-00-00'),
(262, 'kostas', 'kostas', '9878', '17687', 1, 0, 1, '2017-02-08');

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
(48, 'nikos', 'nikos', '2', 'nikos', 'nikos', '00000');

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
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `coupons`
--
ALTER TABLE `coupons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;
--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=263;
--
-- AUTO_INCREMENT for table `operators`
--
ALTER TABLE `operators`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;
--
-- AUTO_INCREMENT for table `settings`
--
ALTER TABLE `settings`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

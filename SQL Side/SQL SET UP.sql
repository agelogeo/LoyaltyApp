-- phpMyAdmin SQL Dump
-- version 4.6.5.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jan 21, 2017 at 12:59 AM
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
CREATE DATABASE IF NOT EXISTS `id99137_aggeloandroid` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `id99137_aggeloandroid`;

-- --------------------------------------------------------

--
-- Table structure for table `coupons`
--

CREATE TABLE `coupons` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `required_stamps` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `coupons`
--

INSERT INTO `coupons` (`id`, `name`, `required_stamps`) VALUES
(3, 'CafeOino', 4),
(13, 'Medium', 3),
(15, 'Testing', 1);

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
  `stamps` int(11) NOT NULL DEFAULT '0',
  `coupons_used` int(11) NOT NULL DEFAULT '0',
  `visits` int(11) NOT NULL DEFAULT '0',
  `last_visit` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`, `name`, `surname`, `phone`, `barcode`, `stamps`, `coupons_used`, `visits`, `last_visit`) VALUES
(185, 'Pasxalis', 'Angelopoulos', '2310209135', '45556', 3, 0, 0, ''),
(186, 'g', 'g', '55', '74323', 0, 0, 0, ''),
(187, 'Alex', 'Greek', '123', '79471', 0, 0, 0, ''),
(188, 'σουκης', 'σουλης', '666', '95227', 0, 0, 0, ''),
(189, 'foivos', 'trela', '120', '76371', 0, 0, 0, ''),
(190, 'γοβργος', 'αγελ', '0000', '95961', 0, 0, 0, ''),
(191, 'Andrew', 'Drazdou', '1212184', '58431', 0, 0, 0, ''),
(192, 'qaz', 'qwer', '1122', '78652', 0, 0, 0, ''),
(193, 'salpi', 'salpi', '44444', '59077', 0, 0, 0, '');

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
(35, 'admin', 'ney-8392', '1', 'Giorgos', 'Angelopoulos', '6976942475'),
(44, 'sofia278', 'ney-8392', '2', 'Sofia', 'Karioti', '6976942476'),
(45, 'z', 'z', '2', 'g', 'g', '55');

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
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `coupons`
--
ALTER TABLE `coupons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=194;
--
-- AUTO_INCREMENT for table `operators`
--
ALTER TABLE `operators`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;--
-- Database: `id99137_aggeloandroid`
--
CREATE DATABASE IF NOT EXISTS `id99137_aggeloandroid` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `id99137_aggeloandroid`;

-- --------------------------------------------------------

--
-- Table structure for table `coupons`
--

CREATE TABLE `coupons` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `required_stamps` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `coupons`
--

INSERT INTO `coupons` (`id`, `name`, `required_stamps`) VALUES
(3, 'CafeOino', 4),
(13, 'Medium', 3),
(15, 'Testing', 1);

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
  `stamps` int(11) NOT NULL DEFAULT '0',
  `coupons_used` int(11) NOT NULL DEFAULT '0',
  `visits` int(11) NOT NULL DEFAULT '0',
  `last_visit` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`, `name`, `surname`, `phone`, `barcode`, `stamps`, `coupons_used`, `visits`, `last_visit`) VALUES
(185, 'Pasxalis', 'Angelopoulos', '2310209135', '45556', 3, 0, 0, ''),
(186, 'g', 'g', '55', '74323', 0, 0, 0, ''),
(187, 'Alex', 'Greek', '123', '79471', 0, 0, 0, ''),
(188, 'σουκης', 'σουλης', '666', '95227', 0, 0, 0, ''),
(189, 'foivos', 'trela', '120', '76371', 0, 0, 0, ''),
(190, 'γοβργος', 'αγελ', '0000', '95961', 0, 0, 0, ''),
(191, 'Andrew', 'Drazdou', '1212184', '58431', 0, 0, 0, ''),
(192, 'qaz', 'qwer', '1122', '78652', 0, 0, 0, ''),
(193, 'salpi', 'salpi', '44444', '59077', 0, 0, 0, '');

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
(35, 'admin', 'ney-8392', '1', 'Giorgos', 'Angelopoulos', '6976942475'),
(44, 'sofia278', 'ney-8392', '2', 'Sofia', 'Karioti', '6976942476'),
(45, 'z', 'z', '2', 'g', 'g', '55');

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
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `coupons`
--
ALTER TABLE `coupons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=194;
--
-- AUTO_INCREMENT for table `operators`
--
ALTER TABLE `operators`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;--
-- Database: `id99137_aggeloandroid`
--
CREATE DATABASE IF NOT EXISTS `id99137_aggeloandroid` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `id99137_aggeloandroid`;

-- --------------------------------------------------------

--
-- Table structure for table `coupons`
--

CREATE TABLE `coupons` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `required_stamps` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `coupons`
--

INSERT INTO `coupons` (`id`, `name`, `required_stamps`) VALUES
(3, 'CafeOino', 4),
(13, 'Medium', 3),
(15, 'Testing', 1);

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
  `stamps` int(11) NOT NULL DEFAULT '0',
  `coupons_used` int(11) NOT NULL DEFAULT '0',
  `visits` int(11) NOT NULL DEFAULT '0',
  `last_visit` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`, `name`, `surname`, `phone`, `barcode`, `stamps`, `coupons_used`, `visits`, `last_visit`) VALUES
(185, 'Pasxalis', 'Angelopoulos', '2310209135', '45556', 3, 0, 0, ''),
(186, 'g', 'g', '55', '74323', 0, 0, 0, ''),
(187, 'Alex', 'Greek', '123', '79471', 0, 0, 0, ''),
(188, 'σουκης', 'σουλης', '666', '95227', 0, 0, 0, ''),
(189, 'foivos', 'trela', '120', '76371', 0, 0, 0, ''),
(190, 'γοβργος', 'αγελ', '0000', '95961', 0, 0, 0, ''),
(191, 'Andrew', 'Drazdou', '1212184', '58431', 0, 0, 0, ''),
(192, 'qaz', 'qwer', '1122', '78652', 0, 0, 0, ''),
(193, 'salpi', 'salpi', '44444', '59077', 0, 0, 0, '');

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
(35, 'admin', 'ney-8392', '1', 'Giorgos', 'Angelopoulos', '6976942475'),
(44, 'sofia278', 'ney-8392', '2', 'Sofia', 'Karioti', '6976942476'),
(45, 'z', 'z', '2', 'g', 'g', '55');

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
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `coupons`
--
ALTER TABLE `coupons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=194;
--
-- AUTO_INCREMENT for table `operators`
--
ALTER TABLE `operators`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;--
-- Database: `id99137_aggeloandroid`
--
CREATE DATABASE IF NOT EXISTS `id99137_aggeloandroid` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `id99137_aggeloandroid`;

-- --------------------------------------------------------

--
-- Table structure for table `coupons`
--

CREATE TABLE `coupons` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `required_stamps` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `coupons`
--

INSERT INTO `coupons` (`id`, `name`, `required_stamps`) VALUES
(3, 'CafeOino', 4),
(13, 'Medium', 3),
(15, 'Testing', 1);

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
  `stamps` int(11) NOT NULL DEFAULT '0',
  `coupons_used` int(11) NOT NULL DEFAULT '0',
  `visits` int(11) NOT NULL DEFAULT '0',
  `last_visit` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`, `name`, `surname`, `phone`, `barcode`, `stamps`, `coupons_used`, `visits`, `last_visit`) VALUES
(185, 'Pasxalis', 'Angelopoulos', '2310209135', '45556', 3, 0, 0, ''),
(186, 'g', 'g', '55', '74323', 0, 0, 0, ''),
(187, 'Alex', 'Greek', '123', '79471', 0, 0, 0, ''),
(188, 'σουκης', 'σουλης', '666', '95227', 0, 0, 0, ''),
(189, 'foivos', 'trela', '120', '76371', 0, 0, 0, ''),
(190, 'γοβργος', 'αγελ', '0000', '95961', 0, 0, 0, ''),
(191, 'Andrew', 'Drazdou', '1212184', '58431', 0, 0, 0, ''),
(192, 'qaz', 'qwer', '1122', '78652', 0, 0, 0, ''),
(193, 'salpi', 'salpi', '44444', '59077', 0, 0, 0, '');

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
(35, 'admin', 'ney-8392', '1', 'Giorgos', 'Angelopoulos', '6976942475'),
(44, 'sofia278', 'ney-8392', '2', 'Sofia', 'Karioti', '6976942476'),
(45, 'z', 'z', '2', 'g', 'g', '55');

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
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `coupons`
--
ALTER TABLE `coupons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=194;
--
-- AUTO_INCREMENT for table `operators`
--
ALTER TABLE `operators`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;--
-- Database: `id99137_aggeloandroid`
--
CREATE DATABASE IF NOT EXISTS `id99137_aggeloandroid` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `id99137_aggeloandroid`;

-- --------------------------------------------------------

--
-- Table structure for table `coupons`
--

CREATE TABLE `coupons` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `required_stamps` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `coupons`
--

INSERT INTO `coupons` (`id`, `name`, `required_stamps`) VALUES
(3, 'CafeOino', 4),
(13, 'Medium', 3),
(15, 'Testing', 1);

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
  `stamps` int(11) NOT NULL DEFAULT '0',
  `coupons_used` int(11) NOT NULL DEFAULT '0',
  `visits` int(11) NOT NULL DEFAULT '0',
  `last_visit` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`, `name`, `surname`, `phone`, `barcode`, `stamps`, `coupons_used`, `visits`, `last_visit`) VALUES
(185, 'Pasxalis', 'Angelopoulos', '2310209135', '45556', 3, 0, 0, ''),
(186, 'g', 'g', '55', '74323', 0, 0, 0, ''),
(187, 'Alex', 'Greek', '123', '79471', 0, 0, 0, ''),
(188, 'σουκης', 'σουλης', '666', '95227', 0, 0, 0, ''),
(189, 'foivos', 'trela', '120', '76371', 0, 0, 0, ''),
(190, 'γοβργος', 'αγελ', '0000', '95961', 0, 0, 0, ''),
(191, 'Andrew', 'Drazdou', '1212184', '58431', 0, 0, 0, ''),
(192, 'qaz', 'qwer', '1122', '78652', 0, 0, 0, ''),
(193, 'salpi', 'salpi', '44444', '59077', 0, 0, 0, '');

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
(35, 'admin', 'ney-8392', '1', 'Giorgos', 'Angelopoulos', '6976942475'),
(44, 'sofia278', 'ney-8392', '2', 'Sofia', 'Karioti', '6976942476'),
(45, 'z', 'z', '2', 'g', 'g', '55');

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
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `coupons`
--
ALTER TABLE `coupons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=194;
--
-- AUTO_INCREMENT for table `operators`
--
ALTER TABLE `operators`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;--
-- Database: `id99137_aggeloandroid`
--
CREATE DATABASE IF NOT EXISTS `id99137_aggeloandroid` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `id99137_aggeloandroid`;

-- --------------------------------------------------------

--
-- Table structure for table `coupons`
--

CREATE TABLE `coupons` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `required_stamps` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `coupons`
--

INSERT INTO `coupons` (`id`, `name`, `required_stamps`) VALUES
(3, 'CafeOino', 4),
(13, 'Medium', 3),
(15, 'Testing', 1);

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
  `stamps` int(11) NOT NULL DEFAULT '0',
  `coupons_used` int(11) NOT NULL DEFAULT '0',
  `visits` int(11) NOT NULL DEFAULT '0',
  `last_visit` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`, `name`, `surname`, `phone`, `barcode`, `stamps`, `coupons_used`, `visits`, `last_visit`) VALUES
(185, 'Pasxalis', 'Angelopoulos', '2310209135', '45556', 3, 0, 0, ''),
(186, 'g', 'g', '55', '74323', 0, 0, 0, ''),
(187, 'Alex', 'Greek', '123', '79471', 0, 0, 0, ''),
(188, 'σουκης', 'σουλης', '666', '95227', 0, 0, 0, ''),
(189, 'foivos', 'trela', '120', '76371', 0, 0, 0, ''),
(190, 'γοβργος', 'αγελ', '0000', '95961', 0, 0, 0, ''),
(191, 'Andrew', 'Drazdou', '1212184', '58431', 0, 0, 0, ''),
(192, 'qaz', 'qwer', '1122', '78652', 0, 0, 0, ''),
(193, 'salpi', 'salpi', '44444', '59077', 0, 0, 0, '');

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
(35, 'admin', 'ney-8392', '1', 'Giorgos', 'Angelopoulos', '6976942475'),
(44, 'sofia278', 'ney-8392', '2', 'Sofia', 'Karioti', '6976942476'),
(45, 'z', 'z', '2', 'g', 'g', '55');

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
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `coupons`
--
ALTER TABLE `coupons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=194;
--
-- AUTO_INCREMENT for table `operators`
--
ALTER TABLE `operators`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

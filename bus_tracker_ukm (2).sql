-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 01, 2016 at 01:48 PM
-- Server version: 5.7.16-0ubuntu0.16.04.1
-- PHP Version: 7.0.8-0ubuntu0.16.04.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bus_tracker_ukm`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `AD_Id` bigint(20) NOT NULL,
  `AD_Name` varchar(50) NOT NULL,
  `AD_Description` varchar(255) NOT NULL,
  `AD_Username` varchar(50) NOT NULL,
  `AD_Password` varchar(50) NOT NULL,
  `AD_Type` varchar(50) NOT NULL,
  `AD_Created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `AD_Active` tinyint(1) NOT NULL,
  `AD_Last_Password` varchar(50) NOT NULL,
  `AD_Last_Login` datetime NOT NULL,
  `AD_Email` varchar(50) NOT NULL,
  `AD_Phone` varchar(50) NOT NULL,
  `AD_Position` varchar(50) NOT NULL,
  `AD_Updated` datetime NOT NULL,
  `AD_Updated_By` bigint(20) NOT NULL,
  `AD_Avatar` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Admin';

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`AD_Id`, `AD_Name`, `AD_Description`, `AD_Username`, `AD_Password`, `AD_Type`, `AD_Created`, `AD_Active`, `AD_Last_Password`, `AD_Last_Login`, `AD_Email`, `AD_Phone`, `AD_Position`, `AD_Updated`, `AD_Updated_By`, `AD_Avatar`) VALUES
(2, 'Haekal Putra', '', 'haekal', 'e10adc3949ba59abbe56e057f20f883e', 'admin', '2016-10-18 09:32:57', 1, '', '0000-00-00 00:00:00', 'haekal@gmail.com', '', '', '0000-00-00 00:00:00', 0, '28086.jpg'),
(4, 'waqas khalid', '', 'wqs', 'e10adc3949ba59abbe56e057f20f883e', '', '2016-10-14 07:34:04', 0, '', '2016-10-14 15:34:04', 'waqas@gmail.com', '', '', '0000-00-00 00:00:00', 0, '842727.jpg'),
(7, 'amin', '', 'amin', 'e10adc3949ba59abbe56e057f20f883e', '', '2016-10-14 08:28:58', 0, '', '0000-00-00 00:00:00', 'amin@gmail.com', '', '', '0000-00-00 00:00:00', 0, ''),
(8, 'adrian ss', '', 'adriansss', '', 'Officer Level 1', '2016-11-02 16:47:12', 0, '', '0000-00-00 00:00:00', 'adriansss', 'adriansss', 'adrianssss', '0000-00-00 00:00:00', 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `admin_level`
--

CREATE TABLE `admin_level` (
  `AL_Id` bigint(20) NOT NULL,
  `AL_Description` varchar(255) NOT NULL,
  `AL_AD_Type` varchar(50) NOT NULL,
  `AL_Level` varchar(50) NOT NULL,
  `AL_Created` datetime NOT NULL,
  `AL_Created_By` bigint(20) NOT NULL,
  `AL_Updated` datetime NOT NULL,
  `AL_Updated_By` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin_level`
--

INSERT INTO `admin_level` (`AL_Id`, `AL_Description`, `AL_AD_Type`, `AL_Level`, `AL_Created`, `AL_Created_By`, `AL_Updated`, `AL_Updated_By`) VALUES
(1, 'level of officer', 'officer', 'O1', '2016-10-05 00:00:00', 3, '2016-10-05 00:00:00', 4);

-- --------------------------------------------------------

--
-- Table structure for table `admin_level_permission`
--

CREATE TABLE `admin_level_permission` (
  `ALP_Id` bigint(20) NOT NULL,
  `ALP_AL_Level` varchar(50) NOT NULL,
  `ALP_Permission` varchar(50) NOT NULL,
  `ALP_Created` datetime NOT NULL,
  `ALP_Created_By` bigint(20) NOT NULL,
  `ALP_Updated` datetime NOT NULL,
  `ALP_Updated_By` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `bus_location`
--

CREATE TABLE `bus_location` (
  `BL_Id` int(11) NOT NULL,
  `BL_Bus_Id` varchar(60) NOT NULL,
  `BL_Latitud` float(10,6) NOT NULL,
  `BL_Longitud` float(10,6) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bus_location`
--

INSERT INTO `bus_location` (`BL_Id`, `BL_Bus_Id`, `BL_Latitud`, `BL_Longitud`) VALUES
(4, 'kkm', 2.928608, 101.787888),
(5, 'ibu zain', 2.930205, 101.783356),
(6, 'FEP', 2.924483, 101.782364),
(8, '', 2.935755, 101.791245),
(9, 'new', 2.930071, 101.777817),
(10, '', 2.929797, 101.788307),
(13, '1', 21.000000, 34.000000),
(14, '1', 21.000000, 34.000000),
(15, '1', 21.000000, 34.000000),
(16, '1', 21.000000, 34.000000),
(17, '1', 21.000000, 34.000000);

-- --------------------------------------------------------

--
-- Table structure for table `bus_locationss`
--

CREATE TABLE `bus_locationss` (
  `BL_Id` bigint(20) NOT NULL,
  `BL_Bus_Id` bigint(20) NOT NULL,
  `BL_Latitud` varchar(50) NOT NULL,
  `BL_Longitud` varchar(50) NOT NULL,
  `BL_Type` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bus_locationss`
--

INSERT INTO `bus_locationss` (`BL_Id`, `BL_Bus_Id`, `BL_Latitud`, `BL_Longitud`, `BL_Type`) VALUES
(12345, 23546, '62.145264', '54.1345464', '');

-- --------------------------------------------------------

--
-- Table structure for table `bus_location_log`
--

CREATE TABLE `bus_location_log` (
  `BLL_Id` bigint(20) NOT NULL,
  `BLL_Latitud` varchar(50) NOT NULL,
  `BLL_Longitud` varchar(50) NOT NULL,
  `BLL_Bus_Id` bigint(20) NOT NULL,
  `BLL_Created` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `bus_schedule`
--

CREATE TABLE `bus_schedule` (
  `BS_Id` bigint(20) NOT NULL,
  `BS_Start_Time` time NOT NULL,
  `BS_Route` varchar(50) NOT NULL,
  `BS_Day` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bus_schedule`
--

INSERT INTO `bus_schedule` (`BS_Id`, `BS_Start_Time`, `BS_Route`, `BS_Day`) VALUES
(234, '05:00:00', 'sds', 'sdssds'),
(235, '05:00:00', 'sdsdsds', 'sd');

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `C_Id` bigint(20) NOT NULL,
  `C_Reporter_Id` bigint(20) NOT NULL,
  `C_Created` datetime NOT NULL,
  `C_Created_By` bigint(20) NOT NULL,
  `C_Message` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `driver_info`
--

CREATE TABLE `driver_info` (
  `DI_Id` bigint(20) NOT NULL,
  `DI_Name` varchar(50) NOT NULL,
  `DI_Gender` varchar(6) NOT NULL,
  `DI_Phone` varchar(50) NOT NULL,
  `DI_Active` tinyint(1) NOT NULL,
  `DI_Email` varchar(50) NOT NULL,
  `DI_Address` varchar(50) NOT NULL,
  `DI_Driver_Rate` int(11) DEFAULT NULL,
  `DI_Avatar` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `driver_info`
--

INSERT INTO `driver_info` (`DI_Id`, `DI_Name`, `DI_Gender`, `DI_Phone`, `DI_Active`, `DI_Email`, `DI_Address`, `DI_Driver_Rate`, `DI_Avatar`) VALUES
(1, 'ahmadaaa', 'male', '092083924', 1, 'ahmad@gmail.com', 'Bangi', NULL, NULL),
(2, 'farida', 'males', '0920839243', 0, 'farid@gmail.coma', 'Kajanga', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `driver_log`
--

CREATE TABLE `driver_log` (
  `DL_Id` bigint(20) NOT NULL,
  `DL_Driver_Id` bigint(20) NOT NULL,
  `DL_Device_Id` bigint(20) NOT NULL,
  `DL_RB_Id` bigint(20) NOT NULL,
  `DL_Created` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `marker`
--

CREATE TABLE `marker` (
  `M_Id` int(11) NOT NULL,
  `M_Name` varchar(60) NOT NULL,
  `M_Address` varchar(80) NOT NULL,
  `M_Lat` float(10,6) NOT NULL,
  `M_Long` float(10,6) NOT NULL,
  `M_Type` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `marker`
--

INSERT INTO `marker` (`M_Id`, `M_Name`, `M_Address`, `M_Lat`, `M_Long`, `M_Type`) VALUES
(4, 'zon 2', 'kkm', 2.928608, 101.787888, 'busStop'),
(5, 'zon 3', 'kolej ibu zain', 2.930205, 101.783356, 'college'),
(6, 'zon 6', 'fakulti ekonomi', 2.924483, 101.782364, 'faculty'),
(10, 'zon3', 'kajaian', 2.930071, 101.777817, 'college');

-- --------------------------------------------------------

--
-- Table structure for table `markers`
--

CREATE TABLE `markers` (
  `id` int(11) NOT NULL,
  `name` varchar(60) NOT NULL,
  `address` varchar(80) NOT NULL,
  `lat` float(10,6) NOT NULL,
  `lng` float(10,6) NOT NULL,
  `type` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `markers`
--

INSERT INTO `markers` (`id`, `name`, `address`, `lat`, `lng`, `type`) VALUES
(4, 'kkm', 'Bus Stop', 2.928608, 101.787888, 'busStop'),
(5, 'ibu zain', 'kolej ibu zain', 2.930205, 101.783356, 'college'),
(6, 'FEP new', 'fakulti ekonomi', 2.930071, 101.777817, 'faculty'),
(8, 'ntah', 'ga tau', 2.929990, 101.787262, 'busStop');

-- --------------------------------------------------------

--
-- Table structure for table `point_of_interest`
--

CREATE TABLE `point_of_interest` (
  `POI_Id` bigint(20) NOT NULL,
  `POI_Station_Id` bigint(20) DEFAULT NULL,
  `POI_Name` varchar(50) NOT NULL,
  `POI_Address` varchar(50) NOT NULL,
  `POI_Created` datetime DEFAULT NULL,
  `POI_Created_By` bigint(20) DEFAULT NULL,
  `POI_Latitud` varchar(50) NOT NULL,
  `POI_Longitud` varchar(50) NOT NULL,
  `POI_Description` varchar(50) DEFAULT NULL,
  `POI_Type` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `point_of_interest`
--

INSERT INTO `point_of_interest` (`POI_Id`, `POI_Station_Id`, `POI_Name`, `POI_Address`, `POI_Created`, `POI_Created_By`, `POI_Latitud`, `POI_Longitud`, `POI_Description`, `POI_Type`) VALUES
(12, 14, 'ftsm', 'ftsm', '2016-11-18 00:00:00', 12, '2.918302', '101.771843', 'faculty', 'bus stop'),
(16, NULL, 'faiz', 'kajai', NULL, NULL, '2.926926', '101.782172', NULL, 'college');

-- --------------------------------------------------------

--
-- Table structure for table `registered_bus`
--

CREATE TABLE `registered_bus` (
  `RB_Id` bigint(20) NOT NULL,
  `RB_Driver_Id` varchar(20) NOT NULL,
  `RB_Name` varchar(50) NOT NULL,
  `RB_Plate_Number` varchar(50) NOT NULL,
  `RB_Device_Id` varchar(20) NOT NULL,
  `RB_Created` datetime DEFAULT NULL,
  `RB_Created_By` varchar(20) DEFAULT NULL,
  `RB_Updated` datetime DEFAULT NULL,
  `RB_Updated_By` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `registered_bus`
--

INSERT INTO `registered_bus` (`RB_Id`, `RB_Driver_Id`, `RB_Name`, `RB_Plate_Number`, `RB_Device_Id`, `RB_Created`, `RB_Created_By`, `RB_Updated`, `RB_Updated_By`) VALUES
(2, 'szx', 'kajai', 'szx', 'xz', '2016-11-17 00:00:00', 'sd', '2016-11-17 00:00:00', '');

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

CREATE TABLE `report` (
  `R_Id` bigint(20) NOT NULL,
  `R_Reporter_Id` bigint(20) NOT NULL,
  `R_Subject` varchar(50) NOT NULL,
  `R_Message` text NOT NULL,
  `R_Report_Type` varchar(50) NOT NULL,
  `R_Created` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `report`
--

INSERT INTO `report` (`R_Id`, `R_Reporter_Id`, `R_Subject`, `R_Message`, `R_Report_Type`, `R_Created`) VALUES
(3544, 232233, 'Bus Zon 2', 'Pliss', 'Engine Problem', '2016-11-17 00:00:00'),
(3545, 23, 'engine', 'engine problem', 'engine issue', '2016-11-18 00:00:00'),
(3546, 23, 'tire', 'punctures', 'tire issue', '2016-11-28 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `routes`
--

CREATE TABLE `routes` (
  `Ro_Id` bigint(20) NOT NULL,
  `Ro_Route` varchar(50) NOT NULL,
  `Ro_Name` varchar(50) NOT NULL,
  `Ro_Route_Busses` bigint(20) NOT NULL,
  `Ro_Created` datetime NOT NULL,
  `Ro_Created_By` bigint(20) NOT NULL,
  `Ro_Active` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `routes`
--

INSERT INTO `routes` (`Ro_Id`, `Ro_Route`, `Ro_Name`, `Ro_Route_Busses`, `Ro_Created`, `Ro_Created_By`, `Ro_Active`) VALUES
(1, 'test', 'test', 1, '2016-11-22 00:00:00', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `station`
--

CREATE TABLE `station` (
  `S_Id` bigint(20) NOT NULL,
  `S_Name` varchar(50) NOT NULL,
  `S_Code` varchar(50) NOT NULL,
  `S_Created` datetime DEFAULT NULL,
  `S_Created_By` varchar(20) DEFAULT NULL,
  `S_Description` varchar(50) NOT NULL,
  `S_Loadlevel` int(11) DEFAULT NULL,
  `S_Active` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `station`
--

INSERT INTO `station` (`S_Id`, `S_Name`, `S_Code`, `S_Created`, `S_Created_By`, `S_Description`, `S_Loadlevel`, `S_Active`) VALUES
(1, 'kajai', 'S002', '2016-11-09 00:00:00', 'admin', 'staion kajai', 90, 1),
(2, 'as', 'as', NULL, NULL, 'as', 23, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users`
--

CREATE TABLE `tbl_users` (
  `userID` int(11) NOT NULL,
  `userName` varchar(20) NOT NULL,
  `userProfession` varchar(50) NOT NULL,
  `userPic` varchar(200) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_users`
--

INSERT INTO `tbl_users` (`userID`, `userName`, `userProfession`, `userPic`) VALUES
(56, 'hahsh', 'asjanksasa', '695357.jpg'),
(57, 'hjg', 'kjk', '844608.jpg'),
(58, 'jhj', 'klkjk', '701566.jpg'),
(55, 'hmp', 'staff', '391850.png'),
(59, 'waqas', 'superadmin', '954148.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `U_Id` bigint(20) NOT NULL,
  `U_Name` varchar(50) NOT NULL,
  `U_Username` varchar(50) NOT NULL,
  `U_Password` varchar(70) NOT NULL,
  `U_Email` varchar(50) NOT NULL,
  `U_Created` datetime NOT NULL,
  `U_Active` tinyint(1) NOT NULL,
  `U_Last_Login` datetime NOT NULL,
  `U_Last_Password` varchar(50) NOT NULL,
  `U_Address` varchar(50) NOT NULL,
  `U_Phone` varchar(50) NOT NULL,
  `U_Occup` varchar(50) NOT NULL,
  `U_Device_Id` varchar(50) NOT NULL,
  `U_Last_Latitud` varchar(50) NOT NULL,
  `U_Last_Longitud` varchar(50) NOT NULL,
  `U_Last_Origin` varchar(50) NOT NULL,
  `U_Last_Destination` varchar(50) NOT NULL,
  `U_Update` datetime NOT NULL,
  `U_Updated_by` bigint(20) NOT NULL,
  `U_Point` int(11) NOT NULL,
  `U_Refer_Id` bigint(20) NOT NULL,
  `U_In_Journey` tinyint(1) NOT NULL,
  `U_Fav_Origin` varchar(50) NOT NULL,
  `U_Fav_Destination` varchar(50) NOT NULL,
  `U_Gender` tinyint(1) NOT NULL,
  `U_Nationality` varchar(50) NOT NULL,
  `U_Avatar` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`U_Id`, `U_Name`, `U_Username`, `U_Password`, `U_Email`, `U_Created`, `U_Active`, `U_Last_Login`, `U_Last_Password`, `U_Address`, `U_Phone`, `U_Occup`, `U_Device_Id`, `U_Last_Latitud`, `U_Last_Longitud`, `U_Last_Origin`, `U_Last_Destination`, `U_Update`, `U_Updated_by`, `U_Point`, `U_Refer_Id`, `U_In_Journey`, `U_Fav_Origin`, `U_Fav_Destination`, `U_Gender`, `U_Nationality`, `U_Avatar`) VALUES
(1, 'aminuddin', 'amin', '$2y$10$8i2vzZSa/YtAc6Ez/3AndeFTxVuTO2Uls/JmcYlKdDZgI1rzPT8rK', 'as', '2016-11-09 00:00:00', 1, '2016-11-15 00:00:00', 'asfaga', 'dasd', '2341', 'dfg', '1', '1', '1', '1', '1', '2016-11-17 00:00:00', 1, 1, 1, 1, '1', '1', 1, '1', '1');

-- --------------------------------------------------------

--
-- Table structure for table `user_origin_destination`
--

CREATE TABLE `user_origin_destination` (
  `UOD_Id` bigint(20) NOT NULL,
  `UOD_User_Id` bigint(20) NOT NULL,
  `UOD_Origin` varchar(50) NOT NULL,
  `UOD_Destination` varchar(50) NOT NULL,
  `UOD_Date` datetime NOT NULL,
  `UOD_Wait_Time` int(11) NOT NULL,
  `UOD_Selected_Bus` bigint(20) NOT NULL,
  `UOD_ETA` int(11) NOT NULL,
  `UOD_Active` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`AD_Id`);

--
-- Indexes for table `admin_level`
--
ALTER TABLE `admin_level`
  ADD PRIMARY KEY (`AL_Id`);

--
-- Indexes for table `admin_level_permission`
--
ALTER TABLE `admin_level_permission`
  ADD PRIMARY KEY (`ALP_Id`);

--
-- Indexes for table `bus_location`
--
ALTER TABLE `bus_location`
  ADD PRIMARY KEY (`BL_Id`);

--
-- Indexes for table `bus_locationss`
--
ALTER TABLE `bus_locationss`
  ADD PRIMARY KEY (`BL_Id`);

--
-- Indexes for table `bus_location_log`
--
ALTER TABLE `bus_location_log`
  ADD PRIMARY KEY (`BLL_Id`);

--
-- Indexes for table `bus_schedule`
--
ALTER TABLE `bus_schedule`
  ADD PRIMARY KEY (`BS_Id`);

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`C_Id`);

--
-- Indexes for table `driver_info`
--
ALTER TABLE `driver_info`
  ADD PRIMARY KEY (`DI_Id`);

--
-- Indexes for table `driver_log`
--
ALTER TABLE `driver_log`
  ADD PRIMARY KEY (`DL_Id`);

--
-- Indexes for table `marker`
--
ALTER TABLE `marker`
  ADD PRIMARY KEY (`M_Id`);

--
-- Indexes for table `markers`
--
ALTER TABLE `markers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `point_of_interest`
--
ALTER TABLE `point_of_interest`
  ADD PRIMARY KEY (`POI_Id`);

--
-- Indexes for table `registered_bus`
--
ALTER TABLE `registered_bus`
  ADD PRIMARY KEY (`RB_Id`);

--
-- Indexes for table `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`R_Id`);

--
-- Indexes for table `routes`
--
ALTER TABLE `routes`
  ADD PRIMARY KEY (`Ro_Id`);

--
-- Indexes for table `station`
--
ALTER TABLE `station`
  ADD PRIMARY KEY (`S_Id`);

--
-- Indexes for table `tbl_users`
--
ALTER TABLE `tbl_users`
  ADD PRIMARY KEY (`userID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`U_Id`);

--
-- Indexes for table `user_origin_destination`
--
ALTER TABLE `user_origin_destination`
  ADD PRIMARY KEY (`UOD_Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `AD_Id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `admin_level`
--
ALTER TABLE `admin_level`
  MODIFY `AL_Id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `admin_level_permission`
--
ALTER TABLE `admin_level_permission`
  MODIFY `ALP_Id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `bus_location`
--
ALTER TABLE `bus_location`
  MODIFY `BL_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `bus_locationss`
--
ALTER TABLE `bus_locationss`
  MODIFY `BL_Id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12346;
--
-- AUTO_INCREMENT for table `bus_location_log`
--
ALTER TABLE `bus_location_log`
  MODIFY `BLL_Id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `bus_schedule`
--
ALTER TABLE `bus_schedule`
  MODIFY `BS_Id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=236;
--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `C_Id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `driver_info`
--
ALTER TABLE `driver_info`
  MODIFY `DI_Id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `driver_log`
--
ALTER TABLE `driver_log`
  MODIFY `DL_Id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `marker`
--
ALTER TABLE `marker`
  MODIFY `M_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `markers`
--
ALTER TABLE `markers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `point_of_interest`
--
ALTER TABLE `point_of_interest`
  MODIFY `POI_Id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT for table `registered_bus`
--
ALTER TABLE `registered_bus`
  MODIFY `RB_Id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `report`
--
ALTER TABLE `report`
  MODIFY `R_Id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3547;
--
-- AUTO_INCREMENT for table `routes`
--
ALTER TABLE `routes`
  MODIFY `Ro_Id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `station`
--
ALTER TABLE `station`
  MODIFY `S_Id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `tbl_users`
--
ALTER TABLE `tbl_users`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `U_Id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `user_origin_destination`
--
ALTER TABLE `user_origin_destination`
  MODIFY `UOD_Id` bigint(20) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

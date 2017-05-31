-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2017-05-31 10:40:26
-- 服务器版本： 10.1.13-MariaDB
-- PHP Version: 5.6.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `iot_management`
--

-- --------------------------------------------------------

--
-- 表的结构 `alarmlist`
--

CREATE TABLE `alarmlist` (
  `AlarmList` int(11) NOT NULL,
  `AlarmRuleID` int(11) DEFAULT NULL,
  `AlarmRuleContent` text,
  `ActualValue` float DEFAULT NULL,
  `isRead` tinyint(1) DEFAULT NULL,
  `SaveTime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `alarmrule`
--

CREATE TABLE `alarmrule` (
  `AlarmRuleID` int(11) NOT NULL,
  `SensingDeviceID` int(11) DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL,
  `DataTypeID` int(11) DEFAULT NULL,
  `Rule` text,
  `Threshold` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `alarmrule`
--

INSERT INTO `alarmrule` (`AlarmRuleID`, `SensingDeviceID`, `UserID`, `DataTypeID`, `Rule`, `Threshold`) VALUES
(1, 4, 1, 2, '>', 25);

-- --------------------------------------------------------

--
-- 表的结构 `app`
--

CREATE TABLE `app` (
  `AppID` int(11) NOT NULL,
  `UserID` int(11) DEFAULT NULL,
  `AppName` varchar(50) DEFAULT NULL,
  `Info` text,
  `IsShowIndex` text,
  `AppKey` text,
  `CreateTime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `app_datatype`
--

CREATE TABLE `app_datatype` (
  `AppID` int(11) NOT NULL,
  `DataTypeID` int(11) NOT NULL,
  `ShowStyle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `configlog`
--

CREATE TABLE `configlog` (
  `ConfigLogID` int(11) NOT NULL,
  `ConfigTypeID` int(11) DEFAULT NULL,
  `ConfigContent` text,
  `Savetime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `configlog`
--

INSERT INTO `configlog` (`ConfigLogID`, `ConfigTypeID`, `ConfigContent`, `Savetime`) VALUES
(1, 4, '44444', '2017-05-09 00:00:00'),
(2, 4, '1111', '2017-05-02 00:00:00'),
(3, 2, 'df', '2017-05-22 00:00:00'),
(4, 2, 'ggg', '2017-05-22 16:28:12');

-- --------------------------------------------------------

--
-- 表的结构 `configtype`
--

CREATE TABLE `configtype` (
  `ConfigTypeID` int(11) NOT NULL,
  `ControllingDeviceID` int(11) DEFAULT NULL,
  `ConfigTypeName` text,
  `Mark` text,
  `CreateTime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `configtype`
--

INSERT INTO `configtype` (`ConfigTypeID`, `ControllingDeviceID`, `ConfigTypeName`, `Mark`, `CreateTime`) VALUES
(2, 2, '关闭', 'shutdown', '2017-05-10 09:45:03'),
(4, 2, '命令行', 'cmd', '2017-05-10 14:16:36'),
(5, 2, '测试控制项目', 'test', '2017-05-10 14:36:29'),
(6, 2, '开始', 'start', '2017-05-10 14:38:47');

-- --------------------------------------------------------

--
-- 表的结构 `controllingdevice`
--

CREATE TABLE `controllingdevice` (
  `ControllingDeviceID` int(11) NOT NULL,
  `ProjectID` int(11) DEFAULT NULL,
  `DeviceName` varchar(50) DEFAULT NULL,
  `Mac` text,
  `Protocol` varchar(50) DEFAULT NULL,
  `Description` text,
  `Localtion` text,
  `DeviceKey` text,
  `CreateTime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `controllingdevice`
--

INSERT INTO `controllingdevice` (`ControllingDeviceID`, `ProjectID`, `DeviceName`, `Mac`, `Protocol`, `Description`, `Localtion`, `DeviceKey`, `CreateTime`) VALUES
(2, 3, '控制器2222', 'aa-bb-cc-dd-ee-ff-gg-hh', 'HTTP', '描述描述描述描述描述', '仙林', 'fdfdsfsdhthjkiuyewiyruhf', '2017-05-06 16:52:18'),
(6, 3, '一个设备', 'df-dfd-hj-bg-bh', 'HTTP', '描述', '学五', 'dfdfdsfs', '2017-05-09 16:43:15'),
(8, 3, '一个控制器hhh', 'dfd-sdf-dfdsf-dfd', 'HTTP', '描述', '南瑞路', 'dfsdffsdf', '2017-05-09 16:47:18'),
(9, 3, '又一个控制设备', 'dfdsf-dfsdf-dfds-df', 'HTTP', '描述', '东门', 'dfddsgfgfdgdg', '2017-05-09 16:48:25'),
(10, 5, 'ggg', 'ggg', 'HTTP', 'gg', 'gg', 'gg', '2017-05-09 16:48:42');

-- --------------------------------------------------------

--
-- 表的结构 `datatype`
--

CREATE TABLE `datatype` (
  `DataTypeID` int(11) NOT NULL,
  `SensingDeviceID` int(11) DEFAULT NULL,
  `Type` varchar(20) DEFAULT NULL,
  `Mark` varchar(20) DEFAULT NULL,
  `Symbol` varchar(20) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `datatype`
--

INSERT INTO `datatype` (`DataTypeID`, `SensingDeviceID`, `Type`, `Mark`, `Symbol`, `CreateTime`) VALUES
(2, 4, '气温', 'temperature', '华氏度', '2017-05-10 09:45:37'),
(4, 4, '湿度', 'humidity', '百分比', '2017-05-10 14:09:34'),
(5, 4, '测试类型', 'test', '千万', '2017-05-10 15:22:47');

-- --------------------------------------------------------

--
-- 表的结构 `devicedata`
--

CREATE TABLE `devicedata` (
  `DeviceDataID` int(11) NOT NULL,
  `DataTypeID` int(11) DEFAULT NULL,
  `Value` float DEFAULT NULL,
  `Savetime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `devicedata`
--

INSERT INTO `devicedata` (`DeviceDataID`, `DataTypeID`, `Value`, `Savetime`) VALUES
(1, 2, 22.3, '2017-05-11 07:30:35'),
(2, 2, 25.5, '2017-05-11 23:24:22'),
(3, 2, 10.5, '2017-05-03 02:08:10'),
(4, 2, 30, '2017-05-23 13:23:24'),
(5, 2, 10.8, '2017-05-09 00:00:00'),
(6, 2, 12.5, '2017-05-04 00:00:00'),
(7, 2, 13.6, '2017-05-08 00:00:00'),
(8, 2, 14.8, '2017-05-10 00:00:00'),
(9, 2, 20.5, '2017-05-13 00:00:00'),
(10, 2, 25.6, '2017-05-15 00:00:00'),
(11, 2, 22.6, '2017-05-17 00:00:00'),
(12, 2, 21, '2017-05-20 00:00:00'),
(13, 2, 19.5, '2017-05-22 00:00:00'),
(14, 2, 19.8, '2017-05-24 00:00:00');

-- --------------------------------------------------------

--
-- 表的结构 `project`
--

CREATE TABLE `project` (
  `ProjectID` int(11) NOT NULL,
  `UserID` int(11) DEFAULT NULL,
  `ProjectName` varchar(50) DEFAULT NULL,
  `isPublic` text,
  `ProjectKey` text,
  `CreateTime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `project`
--

INSERT INTO `project` (`ProjectID`, `UserID`, `ProjectName`, `isPublic`, `ProjectKey`, `CreateTime`) VALUES
(3, 1, '南邮测试', '1', 'dsfsdafsdafdsafasdfgdgfg', '2017-05-06 16:29:41'),
(5, 1, '南邮2', '0', 'dsfsdafsdafdsafasdfgdgfg', '2017-05-06 16:37:35'),
(7, 1, '南邮气象管理系统', '0', 'ca15f048cd3b4753bb5e3077ab9eaff2', '2017-05-07 17:37:42');

-- --------------------------------------------------------

--
-- 表的结构 `sensingdevice`
--

CREATE TABLE `sensingdevice` (
  `SensingDeviceID` int(11) NOT NULL,
  `ProjectID` int(11) DEFAULT NULL,
  `DeviceName` varchar(50) DEFAULT NULL,
  `Mac` text,
  `Protocol` text,
  `Description` text,
  `Localtion` text,
  `DeviceKey` text,
  `CreateTime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `sensingdevice`
--

INSERT INTO `sensingdevice` (`SensingDeviceID`, `ProjectID`, `DeviceName`, `Mac`, `Protocol`, `Description`, `Localtion`, `DeviceKey`, `CreateTime`) VALUES
(3, 3, '传感器222', 'aa-bb-cc-dd-ee-ff-gg', 'HTTP', '描述描述描述描述描述', '仙林校区', 'fdfdsfsdhthjkiuyewiyruhfnjk', '2017-05-06 16:52:20'),
(4, 3, '测试传感器33', 'df-sd-sd-f-df-df-sd', 'HTTP', '一段文字描述，另外一段文字描述', '三牌楼', 'dfdsfsafdsafdsagfgdgfd', '2017-05-08 23:12:13'),
(5, 3, '一个传感器', 'dfdf-dfdsf-sdfdsf-gf', 'HTTP', '方垣闰的传感器', '楼顶', 'dffgffhghfghgfh', '2017-05-09 16:51:29'),
(6, 3, '非官方的个', '非官方的', 'HTTP', 'df', 'dfdf', 'fgfgfdgdfgdfg', '2017-05-10 16:29:51');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE `user` (
  `UserID` int(11) NOT NULL,
  `UserName` varchar(50) DEFAULT NULL,
  `Email` text,
  `Password` text,
  `RegisterTime` datetime DEFAULT NULL,
  `isAdmin` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`UserID`, `UserName`, `Email`, `Password`, `RegisterTime`, `isAdmin`) VALUES
(1, '方垣闰', '547805712@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '2017-05-06 16:26:58', 0),
(2, 'final_lap', '547805712@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '2017-05-06 16:27:47', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `alarmlist`
--
ALTER TABLE `alarmlist`
  ADD PRIMARY KEY (`AlarmList`),
  ADD KEY `FK_Alarm` (`AlarmRuleID`);

--
-- Indexes for table `alarmrule`
--
ALTER TABLE `alarmrule`
  ADD PRIMARY KEY (`AlarmRuleID`),
  ADD KEY `FK_ManageRule` (`UserID`),
  ADD KEY `FK_NeedRule` (`SensingDeviceID`),
  ADD KEY `FK_Rule` (`DataTypeID`);

--
-- Indexes for table `app`
--
ALTER TABLE `app`
  ADD PRIMARY KEY (`AppID`),
  ADD KEY `FK_ManageAPP` (`UserID`);

--
-- Indexes for table `app_datatype`
--
ALTER TABLE `app_datatype`
  ADD PRIMARY KEY (`AppID`,`DataTypeID`),
  ADD KEY `FK_APP_DataType2` (`DataTypeID`);

--
-- Indexes for table `configlog`
--
ALTER TABLE `configlog`
  ADD PRIMARY KEY (`ConfigLogID`),
  ADD KEY `FK_Log` (`ConfigTypeID`);

--
-- Indexes for table `configtype`
--
ALTER TABLE `configtype`
  ADD PRIMARY KEY (`ConfigTypeID`),
  ADD KEY `FK_Container` (`ControllingDeviceID`);

--
-- Indexes for table `controllingdevice`
--
ALTER TABLE `controllingdevice`
  ADD PRIMARY KEY (`ControllingDeviceID`),
  ADD KEY `FK_Have2` (`ProjectID`);

--
-- Indexes for table `datatype`
--
ALTER TABLE `datatype`
  ADD PRIMARY KEY (`DataTypeID`),
  ADD KEY `FK_Containe` (`SensingDeviceID`);

--
-- Indexes for table `devicedata`
--
ALTER TABLE `devicedata`
  ADD PRIMARY KEY (`DeviceDataID`),
  ADD KEY `FK_Data` (`DataTypeID`);

--
-- Indexes for table `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`ProjectID`),
  ADD KEY `FK_ManageProject` (`UserID`);

--
-- Indexes for table `sensingdevice`
--
ALTER TABLE `sensingdevice`
  ADD PRIMARY KEY (`SensingDeviceID`),
  ADD KEY `FK_Have` (`ProjectID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UserID`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `alarmlist`
--
ALTER TABLE `alarmlist`
  MODIFY `AlarmList` int(11) NOT NULL AUTO_INCREMENT;
--
-- 使用表AUTO_INCREMENT `alarmrule`
--
ALTER TABLE `alarmrule`
  MODIFY `AlarmRuleID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- 使用表AUTO_INCREMENT `app`
--
ALTER TABLE `app`
  MODIFY `AppID` int(11) NOT NULL AUTO_INCREMENT;
--
-- 使用表AUTO_INCREMENT `configlog`
--
ALTER TABLE `configlog`
  MODIFY `ConfigLogID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- 使用表AUTO_INCREMENT `configtype`
--
ALTER TABLE `configtype`
  MODIFY `ConfigTypeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- 使用表AUTO_INCREMENT `controllingdevice`
--
ALTER TABLE `controllingdevice`
  MODIFY `ControllingDeviceID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- 使用表AUTO_INCREMENT `datatype`
--
ALTER TABLE `datatype`
  MODIFY `DataTypeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- 使用表AUTO_INCREMENT `devicedata`
--
ALTER TABLE `devicedata`
  MODIFY `DeviceDataID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- 使用表AUTO_INCREMENT `project`
--
ALTER TABLE `project`
  MODIFY `ProjectID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- 使用表AUTO_INCREMENT `sensingdevice`
--
ALTER TABLE `sensingdevice`
  MODIFY `SensingDeviceID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- 使用表AUTO_INCREMENT `user`
--
ALTER TABLE `user`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- 限制导出的表
--

--
-- 限制表 `alarmlist`
--
ALTER TABLE `alarmlist`
  ADD CONSTRAINT `FK_Alarm` FOREIGN KEY (`AlarmRuleID`) REFERENCES `alarmrule` (`AlarmRuleID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `alarmrule`
--
ALTER TABLE `alarmrule`
  ADD CONSTRAINT `FK_ManageRule` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_NeedRule` FOREIGN KEY (`SensingDeviceID`) REFERENCES `sensingdevice` (`SensingDeviceID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_Rule` FOREIGN KEY (`DataTypeID`) REFERENCES `datatype` (`DataTypeID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `app`
--
ALTER TABLE `app`
  ADD CONSTRAINT `FK_ManageAPP` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `app_datatype`
--
ALTER TABLE `app_datatype`
  ADD CONSTRAINT `FK_APP_DataType` FOREIGN KEY (`AppID`) REFERENCES `app` (`AppID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_APP_DataType2` FOREIGN KEY (`DataTypeID`) REFERENCES `datatype` (`DataTypeID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `configlog`
--
ALTER TABLE `configlog`
  ADD CONSTRAINT `FK_Log` FOREIGN KEY (`ConfigTypeID`) REFERENCES `configtype` (`ConfigTypeID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `configtype`
--
ALTER TABLE `configtype`
  ADD CONSTRAINT `FK_Container` FOREIGN KEY (`ControllingDeviceID`) REFERENCES `controllingdevice` (`ControllingDeviceID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `controllingdevice`
--
ALTER TABLE `controllingdevice`
  ADD CONSTRAINT `FK_Have2` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ProjectID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `datatype`
--
ALTER TABLE `datatype`
  ADD CONSTRAINT `FK_Containe` FOREIGN KEY (`SensingDeviceID`) REFERENCES `sensingdevice` (`SensingDeviceID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `devicedata`
--
ALTER TABLE `devicedata`
  ADD CONSTRAINT `FK_Data` FOREIGN KEY (`DataTypeID`) REFERENCES `datatype` (`DataTypeID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `project`
--
ALTER TABLE `project`
  ADD CONSTRAINT `FK_ManageProject` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `sensingdevice`
--
ALTER TABLE `sensingdevice`
  ADD CONSTRAINT `FK_Have` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ProjectID`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: demo
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `job_applications`
--

DROP TABLE IF EXISTS `job_applications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_applications` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `application_status` varchar(50) DEFAULT 'pending',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `status` varchar(255) DEFAULT NULL,
  `resume_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `job_id` (`job_id`),
  KEY `user_id` (`user_id`),
  KEY `job_applications_ibfk_3` (`resume_id`),
  CONSTRAINT `job_applications_ibfk_1` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`) ON DELETE CASCADE,
  CONSTRAINT `job_applications_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `job_applications_ibfk_3` FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_applications`
--

LOCK TABLES `job_applications` WRITE;
/*!40000 ALTER TABLE `job_applications` DISABLE KEYS */;
INSERT INTO `job_applications` VALUES (1,4,17,'pending','2024-11-10 02:46:10','2024-11-10 02:46:10','APPROVED',NULL),(2,4,17,'pending','2024-11-10 02:46:12','2024-11-10 02:46:12','PENDING',NULL),(3,4,17,'pending','2024-11-10 02:46:15','2024-11-10 02:46:15','PENDING',NULL),(4,5,17,'pending','2024-11-10 02:56:07','2024-11-10 02:56:07','PENDING',NULL),(5,6,17,'pending','2024-11-10 03:47:08','2024-11-10 03:47:08','PENDING',NULL),(6,4,18,'pending','2024-11-17 18:09:49','2024-11-17 18:09:49','PENDING',NULL);
/*!40000 ALTER TABLE `job_applications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jobs`
--

DROP TABLE IF EXISTS `jobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jobs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `company` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `salary_range` varchar(50) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `requirements` text DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobs`
--

LOCK TABLES `jobs` WRITE;
/*!40000 ALTER TABLE `jobs` DISABLE KEYS */;
INSERT INTO `jobs` VALUES (4,'软件工程师','Tencent 腾讯','深圳市南山区高新区科技中一路腾讯大厦35层','10000+','负责软件开发、测试和维护工作\r\n参与需求分析和系统设计\r\n编写高质量的代码和技术文档\r\n','计算机科学或相关专业本科及以上学历\r\n\r\n熟悉至少一种编程语言，如Java、C++、Python等\r\n具备良好的问题解决能力和团队合作精神\r\n有相关工作经验者优先','/uploads/1731865734704_ten.jpg','2024-11-09 12:06:47','2024-11-17 17:48:54','pending'),(5,'运维工程师','微信','中国广东省深圳市南山区粤海街道麻岭社区科技中一路腾讯大厦35层','7000-10000','负责系统和应用的日常维护和监控，确保系统的稳定运行\r\n\r\n处理系统故障和性能问题，提供技术支持\r\n\r\n参与系统架构设计和优化，提升系统性能和可靠性\r\n\r\n编写和维护运维相关的技术文档和操作手册\r\n\r\n进行自动化运维工具的开发和优化','计算机科学或相关专业本科及以上学历\r\n\r\n熟悉Linux/Unix操作系统，具备系统管理和故障排除能力\r\n\r\n熟悉常见的运维工具和技术，如Nagios、Zabbix、Ansible等\r\n\r\n具备良好的编程能力，熟悉至少一种脚本语言，如Python、Shell等\r\n\r\n具备良好的问题解决能力和团队合作精神\r\n\r\n有相关工作经验者优先','/uploads/1731865982889_R.jpg','2024-11-09 12:12:16','2024-11-17 17:53:02','pending'),(6,'产品经理','微软（中国）有限公司','北京市海淀区丹棱街5号微软大厦','10000+','负责产品的全生命周期管理，包括需求分析、产品设计、开发、测试、上线和运营\r\n\r\n与跨职能团队合作，确保产品按时上线并达到预期目标\r\n\r\n进行市场调研，分析用户需求和竞争对手，制定产品策略\r\n\r\n跟踪产品性能，收集用户反馈，持续优化产品','计算机科学、信息技术或相关专业本科及以上学历\r\n\r\n具备3年以上产品管理经验，熟悉产品开发流程\r\n\r\n优秀的沟通和团队合作能力，能够与技术、设计、市场等团队紧密合作\r\n\r\n具备良好的市场分析和用户研究能力\r\n\r\n有互联网或软件行业背景者优先','/uploads/1731866096333_micro.webp','2024-11-09 13:43:21','2024-11-17 17:54:56','pending'),(7,'抖音游戏平台产品运营经理','字节跳动（ByteDance）','北京市海淀区北三环西路43号中航广场A2楼','7000-10000','1、负责运营抖音游戏合作客户的资产管理、资金入账与结算等业务，关注客户的全流程体验，基于业务变化和客户组织变化持续迭代各类服务SOP，为内外部用户提供高效优质的解决方案；\r\n2、持续优化Oncall&反馈机制，与上下游团队协同，发现、分析、改善各链路下各角色的痛点，沉淀通用方法论，为产品迭代提供决策和业务支持，从而提升平台用户的运营满意度；\r\n3、通过多渠道多形式完成平台产品能力的宣传，不断提升目标用户的使用率与使用深度。','1、本科及以上学历，具备互联网产品运营经验优先；\r\n2、优秀的跨部门沟通协作能力，项目管理能力，风险预判能力，抗压能力强，思考全面，具备解决复杂问题的能力，能推动复杂流程、多业务角色的项目顺利落地；\r\n3、逻辑清晰，具有较强的数据敏感度和数据分析能力，主人翁意识强，以结果为导向。','/uploads/1731866300686_OIP (1).jpg','2024-11-09 14:18:35','2024-11-17 17:58:20','pending'),(8,'边缘计算网络工程师','抖音（Douyin）','北京市海淀区北三环西路43号中航广场A2楼','10000+','负责边缘计算网络的设计、部署和维护\r\n\r\n参与边缘计算平台的架构设计和优化\r\n\r\n解决网络性能问题，确保系统的高可用性和稳定性\r\n\r\n编写和维护技术文档，提供技术支持','计算机科学或相关专业本科及以上学历\r\n\r\n熟悉网络协议和网络设备，具备网络故障排除能力\r\n\r\n熟悉边缘计算技术和平台，如Edge Computing、Fog Computing等\r\n\r\n具备良好的编程能力，熟悉至少一种编程语言，如Python、C++等\r\n\r\n具备良好的问题解决能力和团队合作精神\r\n\r\n有相关工作经验者优先','/uploads/1731866379524_OIP.jpg','2024-11-09 14:31:45','2024-11-17 17:59:39','pending'),(9,'云计算工程师','阿里云','杭州市西湖区阿里巴巴西溪园区','10000+','负责云计算平台的设计、部署和维护\r\n\r\n参与云计算架构的设计和优化\r\n\r\n解决云计算平台的性能问题，确保系统的高可用性和稳定性\r\n\r\n编写和维护技术文档，提供技术支持','计算机科学或相关专业本科及以上学历\r\n\r\n熟悉云计算技术和平台，如阿里云、AWS、Azure等\r\n\r\n具备良好的编程能力，熟悉至少一种编程语言，如Python、Java等\r\n\r\n具备良好的问题解决能力和团队合作精神\r\n\r\n有相关工作经验者优先','/uploads/1731866531537_ali.png','2024-11-09 15:49:52','2024-11-17 18:02:11','pending');
/*!40000 ALTER TABLE `jobs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resume`
--

DROP TABLE IF EXISTS `resume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resume` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `photo_path` varchar(255) DEFAULT NULL,
  `resume_file_path` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `gender` enum('male','female') NOT NULL,
  `age` int(3) NOT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `marital_status` enum('single','married','divorced','widowed') DEFAULT 'single',
  `expected_salary` varchar(50) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `resume_ibfk_1` (`user_id`),
  CONSTRAINT `resume_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resume`
--

LOCK TABLES `resume` WRITE;
/*!40000 ALTER TABLE `resume` DISABLE KEYS */;
INSERT INTO `resume` VALUES (1,17,'/uploads/photo/57341856-b887-477a-ac41-ca385932ff08_Image_20241105224940.jpg','/uploads/resume/0ad6f3af-81b8-45e4-9757-d866dd3e804d_草稿.docx','LIM JIA JUN','jiajunlim0701@gmail.com','male',12,'01128797556','married','123','2024-11-10 05:44:09','2024-11-14 14:58:56'),(2,18,'05ddf5e0-a651-45e6-a45c-14eca6a87f5c_qq.jpg','aa13381d-fcf7-4ffa-967b-c1b6b9e55845_草稿.docx','神盾局','user123@gmail.com','male',10,'123456789','single','20000','2024-11-17 18:22:30','2024-11-17 18:22:30');
/*!40000 ALTER TABLE `resume` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (2,'ROLE_ADMIN'),(17,'ROLE_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'kelvin@gmail.com','卡瑞','黄','$2a$10$s/14cexLmdFzd25f5SusUuv7jJJW/SXkxsyApG1CzKC97xX5Db6wW'),(2,'admin@gmail.com','admin','admin','$2a$10$rCszc8Z8D2sA7vUedLFPf.Ida3L428uFhBy3mb1tjThg5CIrmGjYi'),(17,'user@gmail.com','user','user','$2a$10$BaomD8AeEtRO2BivNF6iG.BHAqCjSETCqgdmyeS3mYWf..YMQS/wq'),(18,'user123@gmail.com','用户','一','$2a$10$gbqf96q.R7HPKtiHM1b6beELN7ApxeQ5PePAniAAUcuiYytOXsZ5q'),(19,'user11@gmail.com','十一','陈','$2a$10$vjEP24v1ImumGIjtOIjtdO4BXTWnI9G8UvpeMPuojBHWNz2aQ.zB2'),(20,'sdad@gmail.com','苏打','倪','$2a$10$vOrxz7wW6qqETCr4EkBIcOooQFmvz9LCLmHdo4zqbIGJ6.NXZpmWS'),(21,'userdasdasd@gmail.com','的撒大苏打','的撒大苏打','$2a$10$d994m.9EweUqYIwfC44cjejUHDrj1EYYmHD6V1Ju1Ncu1ezeR5DG2'),(22,'udsadad@gmail.com','的撒大苏打的撒大','的撒大苏打','$2a$10$auOXPyU3i0upXdYlGdGsaum6LeIYkZ0Bi7UVtYOaqpS1h/Llux5MC'),(23,'qq@gmail.com','大厦哒哒哒萨德','魏汝稳','$2a$10$LVd/Lm/cHcJtv9QtG31eZe6x9pOBnIvRScLLrJb5Bf6Zhzi6tQ7im'),(24,'dsar@gmail.com','S最喜欢认为人的法国工人','大师赛反对犯得上方法','$2a$10$dqhaQbWfMoFusTo1QVxaxOn6sLLrx6rDK9WU3ZV5YXw49wuDOlQxW'),(25,'usdsr@gmail.com','阿斯蒂芬','大苏打的','$2a$10$FrcidzKIa0CX.qapWFF0FeVNCBfZIR.ceCheqhyyR5R7pxXdmcD6K'),(30,'usersad@gmail.com','LIM','JIA JUN123','$2a$10$6ezcc98AblxcONgtHkSGQO8Xem/rHCpI0kXISPipdIE3mbu1LYsqO'),(31,'usedasr@gmail.com','LIMsdv','JIA JUN','$2a$10$pSUXyYe00CCbmfgApDbcf.cV9YMbe4HDPiIiJfL4MNsHAitlxevkS'),(32,'cxzczxc@gmail.com','cxzczxc','cxzczxc','$2a$10$BHmtNd3RCVc3OWGbibz0HOBKOEJQ.logvFc8sBVQLcnHC3FXPsFsO'),(33,'dsad@gmail.com','aSd','dasda','$2a$10$IQlc3Ssa1tZtPNEJ/FTdl.aWXQWH7KcY1ky.glVzfYZ05TyTNLLtO'),(34,'dasdada@gmail.com','DS在东方大国和第三方v','爱上大师傅大师傅v吧','$2a$10$hAMObKKx1dGV03gEn6wTBOSdMpFVvqztSL1j/RYy5PDax43yWjW86'),(35,'dasda@gmail.com','S主程序v','撒大大','$2a$10$UASK6kqu1UgG4uoRY02GZuV/KAc4NZ09zWSbiDLG6gwJGmBUQ75fu'),(37,'userddd@gmail.com','LIM爱上犯得上反对大师傅','JIA JUN','$2a$10$JRokG1IjlPTBIdItIZa9LOPBbSmQpruJc2G.VblZc7SWfmoyMj/ky'),(39,'dasd12er@gmail.com','LIMzxczc','JIA JUN撒大大','$2a$10$qiMHPqFjvdMqZTR5qq7IkO0UnKnkXpM6aPLFEyG3RjU7vybyWSqHq'),(40,'3wqer@gmail.com','LIMdasasd','dasdasdwere','$2a$10$xqFX0o4IRwhmFivRVCH8XOqLxJj6.IM.T0RS87Nt00Y7fM7xXwK3K'),(42,'dasdasdar@gmail.com','dasdasda','dasdada','$2a$10$R3DxPB3HlOS/d3URFMaZl.NHzvsobROsNN/BcayCi473tXVIVgxi2'),(44,'dsadad23r@gmail.com','LIMasasdfggf','JIA JUNdasvasdfv','$2a$10$kyz9Mlf8bTMYTDfgTB/JuOnVfEM3h5Jc1fdvDQqzcX8eirZRPNldS'),(46,'fff@gmail.com','LIM啊实打实打算发顺丰','JIA JUN的撒大大','$2a$10$gOvKVr10JkZ/S3SoT5HYaugqCY/mEjhtsJ8SdnQ2DuPZL.pH2xVsm');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `users_roles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `users_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (2,2),(18,17),(19,17),(20,17),(21,17),(22,17),(23,17),(24,17),(25,17),(30,17),(31,17),(32,17),(33,17),(34,17),(35,17),(37,17),(39,17),(40,17),(42,17),(44,17),(46,17);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-18  3:01:05

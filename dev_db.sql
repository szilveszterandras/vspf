-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: vspf
-- ------------------------------------------------------
-- Server version	5.7.14-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `photos`
--

DROP TABLE IF EXISTS `photos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `photos` (
  `id` bigint(20) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `hash` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `uploadedAt` datetime DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `photos`
--

LOCK TABLES `photos` WRITE;
/*!40000 ALTER TABLE `photos` DISABLE KEYS */;
INSERT INTO `photos` VALUES (8,'An old woman in a remote village in Himachal Pradesh, India, carries a big log back home to warm up her house.','ec5f0520-1edd-4e56-ac20-5745cbf83972','natgeo/ec5f0520-1edd-4e56-ac20-5745cbf83972.jpg','Remote Life','2016-08-06 16:15:09',16),(9,'It was when I drove back home feeling disappointed with the fact that I had finished the day in vain without any anticipated subject that I heard the joyful voice from the car window like “quack, quack!” There they were: red foxes. Around the end of the winter, they meet the season of love; they care for and love each other enough to make us jealous.','696c64ad-3d1b-42b5-839a-f60c76d9287d','natgeo/696c64ad-3d1b-42b5-839a-f60c76d9287d.jpg','Wherever you go, I\'ll follow','2016-08-06 16:19:35',16),(10,'From a doorless helicopter looking south on Central Park West, dividing the architecture and Central Park, on November 5, 2014, a day before my 27th birthday. The flight was my birthday gift.','c114174b-8b1b-4ed8-b0e6-2e0d7c1b878e','natgeo/c114174b-8b1b-4ed8-b0e6-2e0d7c1b878e.jpg','Divide','2016-08-06 16:20:52',16),(11,'Easter Island is famous for its 887 extant monumental statues, called moai, created by the early Rapa Nui people.','4fe320b2-6c44-4af2-bb34-eedd0107bf27','natgeo/4fe320b2-6c44-4af2-bb34-eedd0107bf27.jpg','Easter Island','2016-08-06 16:22:01',16),(12,'The winter in Inner Mongolia is very unforgiving. At a freezing temperature of minus 20 and lower, with a constant breeze of snow from all directions, it was pretty hard to convince myself to get out of the car and take photos. I saw horsemen showing off their skills and commanding the steed from a distance. I quickly grabbed my telephoto lens and captured the moment when one of the horsemen charged out from the morning mist.','c4c85986-fd58-45ee-badf-3950447455d0','natgeo/c4c85986-fd58-45ee-badf-3950447455d0.jpg','Winter Horseman','2016-08-06 16:24:08',16),(13,'I was in the Brazilian Pantanal along the Rio Negrinho. I realized that the river, at certain points of the loops, created places where there were many yacare caimans. I saw a yacare sink suddenly, and I immediately looked for the best location to photograph when it resurfaced. The whole thing lasted only a fraction of a moment.','827a59bb-b9bc-4c9c-8f59-9de53c225f2c','natgeo/827a59bb-b9bc-4c9c-8f59-9de53c225f2c.jpg','Double Trapping','2016-08-06 16:25:06',16),(14,'I made this photo during my recent photographic expedition in Atacama Desert, in April 2016. I embarked alone on this adventure to find images not yet published of the most arid desert in the world and its contrasts. Despite the Atacama Desert being one of the best places on the planet to do night photography, in my prior research I discovered that there were not many night photos in the main tourist destinations there.','4a4ee8bb-7bf8-4c94-add0-c02cfeb7bb8c','natgeo/4a4ee8bb-7bf8-4c94-add0-c02cfeb7bb8c.jpg','Lagunas Baltinache (Atacama Desert)','2016-08-06 16:25:46',16),(15,'The location is on the sea ice in Davis Straight, off the coast of Baffin Island in the Canadian Arctic, on April 2, 2016. This mother polar bear and her yearling are perched atop a huge snow-covered iceberg that got socked in when the ocean froze over for the winter. To me, the relative smallness of these large creatures when compared to the immensity of the iceberg in the photo represents the precariousness of the polar bear\'s reliance on the sea and sea ice for its existence.','8156a0b5-f10c-4e65-87fb-896009b1e672','natgeo/8156a0b5-f10c-4e65-87fb-896009b1e672.jpg','Bears on a Berg','2016-08-06 16:26:34',16),(16,'Marrakesh, Morocco, is an exciting city for any traveler, but I was tired of walking on the crowded street and being asked for money from local people, so I was looking for a place to settle down. Even though there were a lot of people in Ben Youssef Madrasa, it was still a more quiet and relaxing place than outside. Suddenly a beautiful reflection appeared on the shallow pool when I was taking a rest.','477feb6c-29c7-4996-bd03-bf81d0aaa4c4','natgeo/477feb6c-29c7-4996-bd03-bf81d0aaa4c4.jpg','Ben Youssef','2016-08-06 16:27:32',16),(17,'This photo was taken on my last trip to Guangzhou, China. This place is the school dormitories of South China Normal University. When I was hanging around, most of them were taking a break. After lunchtime, they needed to go back to study.','dee8e5e4-7c42-45f4-beab-34fc93532408','natgeo/dee8e5e4-7c42-45f4-beab-34fc93532408.jpg','Silenced','2016-08-06 16:28:32',16),(18,'Lightning seemingly strikes Komtar Tower, the most iconic landmark of George Town, capital of Penang state in Malaysia, during a thunderstorm. It is symbolic of the rejuvenation that the city, famous for a unique blend of centuries-old buildings and modern structures, has enjoyed in recent years. While many of its old neighborhoods fell into neglect in the 1990s and early 2000s, a UNESCO World Heritage listing in 2008 sparked a transformation.','da32060a-6439-4540-a8fa-9a198e679e2b','natgeo/da32060a-6439-4540-a8fa-9a198e679e2b.jpg','Celestial Reverie','2016-08-06 16:29:02',16),(19,'It\'s like watching the earth being born. A four hour bumpy jeep ride in the middle of the night got me to the freezing cold view point of Mt. Bromo, Indonesia. After a couple more hours wait in the dark I was greeted with not only a rare opportunity to see this special place erupting, but also a wonderful sunrise casting it\'s orange glow on the ash. Note the small square temple between the cones. This image was made from a seven shot bracket, taken immediately one after the other.','45e11470-e90a-4cc2-90eb-28a77e9f2c57','natgeo/45e11470-e90a-4cc2-90eb-28a77e9f2c57.jpg','Mt. Bromo Sunrise Eruption','2016-08-06 16:29:59',16),(20,'Witnessing sunrise at Canyon de Chelly is absolutely breathtaking!','8810b2de-2eb4-4408-a0fb-e99113a8c80d','natgeo/8810b2de-2eb4-4408-a0fb-e99113a8c80d.jpg','Sunrise at Canyon de Chelly','2016-08-06 16:30:40',16),(21,'A male lion is resting and bathing in the setting sun\'s light.','748593b3-e88a-43b6-b619-26e50a508b6f','natgeo/748593b3-e88a-43b6-b619-26e50a508b6f.jpg','Lion in sunset','2016-08-06 16:32:46',16),(22,'A striking northern lights show in Karjakla Harjumaa, Estonia.','32b59a8b-9e4c-48e0-a49b-85ddff3ec354','natgeo/32b59a8b-9e4c-48e0-a49b-85ddff3ec354.jpg','Northern Lights','2016-08-06 16:33:58',16),(23,'Frog sits on plant','49c03aa9-c2cc-450d-a26e-e4e811d96ce9','natgeo/49c03aa9-c2cc-450d-a26e-e4e811d96ce9.jpg','Frog on a plant','2016-08-06 16:34:27',16),(24,'Climber in a cave','affab0db-0c6a-43d4-96de-316f91f46d99','natgeo/affab0db-0c6a-43d4-96de-316f91f46d99.jpg','Lost World','2016-08-07 19:25:44',16),(25,'Zebras','c952c948-7153-4177-9932-2edf98013c2e','animals/c952c948-7153-4177-9932-2edf98013c2e.jpg','Two zebras having a good time','2016-08-10 21:03:00',17),(26,'Snow leopard sits in the snow','636925a9-8c54-4f3b-9728-7d724082d431','animals/636925a9-8c54-4f3b-9728-7d724082d431.jpg','White cat','2016-08-10 21:05:21',17),(27,'','1891890a-4b30-4ecb-b7be-c5d85a6fdb19','animals/1891890a-4b30-4ecb-b7be-c5d85a6fdb19.jpg','Hummingbird','2016-08-10 21:05:39',17),(28,'','0e006654-8cc5-412e-a63c-350d16a48396','animals/0e006654-8cc5-412e-a63c-350d16a48396.jpg','Giraffes','2016-08-10 21:06:01',17),(29,'','26971dee-64f3-40e5-9c43-2e8057cc5fa5','animals/26971dee-64f3-40e5-9c43-2e8057cc5fa5.jpg','Tiger','2016-08-10 21:06:23',17),(30,'','24345e72-ece6-4e5b-b9b6-b5aedb9a4da1','animals/24345e72-ece6-4e5b-b9b6-b5aedb9a4da1.jpg','Green snake','2016-08-10 21:06:54',17),(31,'','5c3a244d-c4bf-4ccb-8cc0-2950a8d633b7','animals/5c3a244d-c4bf-4ccb-8cc0-2950a8d633b7.jpg','Eagle','2016-08-10 21:20:03',17),(32,'Frogs on a branch','955483d8-a12b-4ac6-808c-1bc9543b9a07','animals/955483d8-a12b-4ac6-808c-1bc9543b9a07.jpg','Frogs','2016-08-10 21:20:33',17),(33,'','a6488afb-e3ed-42c3-a4a1-d5ead0cf60b6','animals/a6488afb-e3ed-42c3-a4a1-d5ead0cf60b6.jpg','Natural Landscape','2016-08-10 21:21:01',17),(34,'Izvorul Bigăr (English: Bigar Spring, but more commonly known as Bigar Waterfall) is a protected area (nature reserve IUCN category IV) situated in the administrative territory of Bozovici, in Caraş-Severin County in southwestern Romania.','6291b552-cb9c-4b69-af57-4ca007a4fcc4','animals/6291b552-cb9c-4b69-af57-4ca007a4fcc4.jpg','Romanian Waterfall','2016-08-10 21:21:21',17),(35,'','f47a35db-703e-4059-bb9f-b7808494f3ea','city_person/f47a35db-703e-4059-bb9f-b7808494f3ea.jpg','Del Fiore','2016-08-10 21:23:26',18),(36,'','56554847-76f5-439c-95c4-182e6932ac64','city_person/56554847-76f5-439c-95c4-182e6932ac64.jpg','City Life','2016-08-10 21:23:46',18),(37,'','3cf25916-8e76-47d1-936f-23a5264f841e','city_person/3cf25916-8e76-47d1-936f-23a5264f841e.jpg','New York','2016-08-10 21:24:12',18),(38,'','b18bb43b-2fae-4b42-b1fb-4c788be6b75e','city_person/b18bb43b-2fae-4b42-b1fb-4c788be6b75e.jpg','Brooklyn Bridge','2016-08-10 21:24:38',18),(39,'','d8f0767f-afc2-41c2-81ab-356d0a9e9233','animals/d8f0767f-afc2-41c2-81ab-356d0a9e9233.jpg','Florence','2016-09-04 14:07:50',17),(40,'The Golden Gate Bridge is a suspension bridge spanning the Golden Gate strait, the one-mile-wide (1.6 km), three-mile-long (4.8 km) channel between San Francisco Bay and the Pacific Ocean. The structure links the American city of San Francisco, California – the northern tip of the San Francisco Peninsula – to Marin County, carrying both U.S. Route 101 and California State Route 1 across the strait. The bridge is one of the most internationally recognized symbols of San Francisco, California, and the United States. It has been declared one of the Wonders of the Modern World by the American Society of Civil Engineers.','678542a0-23f1-42b7-ad53-899ee7b80306','natgeo/678542a0-23f1-42b7-ad53-899ee7b80306.jpg','Golden Gate Bridge','2016-09-04 14:09:54',16),(41,'','6e450633-f856-4230-89e3-735f1f90ff32','city_person/6e450633-f856-4230-89e3-735f1f90ff32.jpg','Fantasy city','2016-09-04 14:17:38',18),(42,'','e82470e9-fd5b-4ee8-9c67-77d4701abf26','city_person/e82470e9-fd5b-4ee8-9c67-77d4701abf26.jpg','Houses on the beach','2016-09-04 14:18:44',18),(43,'','02c84c70-332e-4ea6-ac22-8479a0772195','city_person/02c84c70-332e-4ea6-ac22-8479a0772195.jpg','Pidgeons','2016-09-04 14:21:33',18),(44,'','79399196-7fdb-4f45-b5e9-e51290d84442','city_person/79399196-7fdb-4f45-b5e9-e51290d84442.jpg','Street View','2016-09-04 14:23:34',18);
/*!40000 ALTER TABLE `photos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reviews` (
  `id` bigint(20) NOT NULL,
  `addedAt` datetime DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `photoId` bigint(20) DEFAULT NULL,
  `rating` bigint(20) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,'2016-09-01 23:22:11','Photographing the aurora tests the limits of our equipment, so this is a case where the best equipment possible really makes a difference in the quality of your images. Cameras with full frame sensors are ideal as are fast, ultra wide angle lenses. Cameras such as the Nikon D800 and Canon 5D Mark III are particularly well-suited to this work. If you are shooting with an APSc sensor camera, newer camera models that perform better at high ISOs are the next best option.',22,90,'Nice photo',17),(2,'2016-09-03 22:25:53','When photographers are asked how they managed to achieve a certain result, they will usually point to their own artistic proficiency, not the capability of their tools. \'It’s the photographer, not the camera,\' is the common refrain. There are, of course, elements of artistry in aurora photography as well.',22,50,'Have seen better',17),(3,'2016-09-04 14:16:05','Terracotta roof tops, hidden courtyards and streets alive with historical features. Florence is a city to get lost in with your camera. Roam under the Tuscan sun and soak up the Italian culture in the city where bicycles and motorbikes are more popular than cars.',39,82,'Must\'ve been hard to get up there',16);
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sessions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `token` varchar(45) DEFAULT NULL,
  `lastUpdated` datetime DEFAULT NULL,
  `expiresAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_userId_idx` (`userId`),
  CONSTRAINT `fk_userId` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessions`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
INSERT INTO `sessions` VALUES (44,16,'7aa8ffc3-af37-4d68-a0c9-86c86b6f3823','2016-08-10 20:53:04','2016-08-10 21:53:04'),(45,17,'ebd99f45-a9ee-4e80-ac55-b11d8cca7c05','2016-08-10 21:21:24','2016-08-10 22:21:24'),(46,18,'8c73060b-b697-40ad-b9b2-7df7482e4997','2016-08-10 21:25:39','2016-08-10 22:25:39'),(47,16,'a0215982-ef99-49fd-aba5-476d7cf3e273','2016-09-01 23:15:27','2016-09-02 00:15:27'),(48,16,'8017af12-5571-4f4b-a280-23c5721d3fa0','2016-09-03 16:35:44','2016-09-03 17:35:44'),(49,17,'a9a283a8-6906-4cff-ac38-0cb267ef12a0','2016-09-04 14:08:00','2016-09-04 15:08:00'),(50,16,'e7d9b006-bcd0-4550-9ac4-695d4e619d74','2016-09-04 14:13:53','2016-09-04 15:13:53'),(51,18,'d03f6ca7-e642-413f-9654-63127a0c4970','2016-09-04 14:24:09','2016-09-04 15:24:09');
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stars`
--

DROP TABLE IF EXISTS `stars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stars` (
  `id` bigint(20) NOT NULL,
  `photoId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stars`
--

LOCK TABLES `stars` WRITE;
/*!40000 ALTER TABLE `stars` DISABLE KEYS */;
INSERT INTO `stars` VALUES (1,33,18),(2,31,18),(15,25,16),(18,9,17),(19,10,17),(20,11,17),(21,12,17),(23,8,17),(24,39,16),(25,36,16),(26,37,16),(27,38,16),(28,40,18);
/*!40000 ALTER TABLE `stars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `photoId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (1,'natgeo',8),(2,'natgeo',9),(3,'natgeo',10),(4,'natgeo',11),(6,'natgeo',23),(7,'nature',23),(8,'natgeo',24),(9,'nature',24),(10,'nature',25),(11,'animal',25),(12,'animal',26),(13,'nature',27),(14,'animal',28),(15,'giraffe',28),(16,'snake',30),(17,'eagle',31),(18,'nature',32),(19,'nature',33),(20,'architecture',35),(21,'city',35),(22,'city',36),(23,'brooklyn',38),(24,'nyc',38),(25,'waterfall',34),(26,'romania',34),(27,'florence',39),(28,'bridge',40),(29,'goldengate',40),(30,'fantasy',41),(31,'beach',42),(32,'house',42),(33,'cute',42),(34,'pidgeons',43),(35,'animals',43),(36,'city',43),(37,'city',44);
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (16,'natgeo','National Geographic','givinginfo@ngs.org','password','natgeo/avatar_377c2a96-d53e-45a2-bee8-19c515401ba2.jpg'),(17,'animals','Animal Lover Person','animal@lover.com','password','animals/avatar_9b6f3dab-c02f-41fb-b231-24e25e8e71d7.jpg'),(18,'city_person','John Doe','jd@nyc.com','password','city_person/avatar_0213bd77-1804-4839-859f-e874e6c3890e.jpg');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-04 14:26:38

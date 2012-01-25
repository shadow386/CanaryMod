INSERT INTO `items` VALUES
('mycelium',110),('lilypad',111),('netherbrick',112),('netherbrickfence',113),
('netherbrickstairs',114),('netherwartblock',115),('enchantmenttable',116),
('brewingstandblock',117),('cauldron',118),('endportal',119),('endportalframe',120),
('endstone',121),('dragonegg',122),('blazerod',369),('ghasttear',370),
('goltnugget',371),('netherwart',372),('potion',373),('glassbottle',374),
('spidereye',375),('fermentedspidereye',376),('blazepowder',377),('magmacream',378),
('brewingstand',379),('eyeofender',381),('glisteringmelon',382),('blocksrecord',2258),
('chirprecord',2259),('farrecord',2260),('mallrecord',2261),('mellohirecord',2262),
('stalrecord',2263),('stradrecord',2264),('wardrecord',2265),('11record',2266);

--
-- Table structure for table `antixrayblocks`
--

DROP TABLE IF EXISTS `antixrayblocks`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `antixrayblocks` (
  `antixrayblocks` int(10) unsigned NOT NULL,
  PRIMARY KEY (`antixrayblocks`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `antixrayblocks`
--

LOCK TABLES `antixrayblocks` WRITE;
/*!40000 ALTER TABLE `antixrayblocks` DISABLE KEYS */;
INSERT INTO `antixrayblocks` VALUES 
(14),(15),(16),(21),(56),(73);
/*!40000 ALTER TABLE `antixrayblocks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enderblocks`
--

DROP TABLE IF EXISTS `enderblocks`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `enderblocks` (
  `enderblocks` int(10) unsigned NOT NULL,
  PRIMARY KEY (`enderblocks`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `enderblocks`
--

LOCK TABLES `enderblocks` WRITE;
/*!40000 ALTER TABLE `enderblocks` DISABLE KEYS */;
INSERT INTO `enderblocks` VALUES 
(1),(2),(3),(4),(5),(12),(13),(14),(15),(16),(17),
(18),(19),(20),(21),(22),(24),(35),(37),(38),(39),
(40),(41),(42),(45),(46),(47),(48),(56),(57),(58),
(73),(74),(79),(81),(82),(86),(87),(88),(89),(91),
(98),(99),(100),(103);
/*!40000 ALTER TABLE `enderblocks` ENABLE KEYS */;
UNLOCK TABLES;

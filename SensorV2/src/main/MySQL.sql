
CREATE SCHEMA `weather`;

CREATE TABLE `sensor` (
                          `id_sensor` int NOT NULL AUTO_INCREMENT,
                          `name_sensor` varchar(45) NOT NULL,
                          PRIMARY KEY (`id_sensor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `measurement` (
                               `id_measurement` int NOT NULL AUTO_INCREMENT,
                               `rain` tinyint NOT NULL,
                               `temperature` float NOT NULL,
                               `create_date` datetime(6) DEFAULT NULL,
                               `unit_id` int DEFAULT NULL,
                               PRIMARY KEY (`id_measurement`),
                               KEY `sensor_measurement_idx` (`unit_id`),
                               CONSTRAINT `sensor_measurement` FOREIGN KEY (`unit_id`) REFERENCES `sensor` (`id_sensor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.7.24 : Database - jobpe_business
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE = ''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS */`jobpe_business` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `jobpe_business`;

/*Data for the table `business` */

insert into `business`(`id`, `name`, `user_id`)
values (1, 'Peluquería Loreta Valdéz', 1),
       (2, 'Marco Aldany Fuencarral', 2);

/*Data for the table `contract_type` */

insert into `contract_type`(`id`, `type`)
values (1, 'COMPLETA'),
       (2, 'AUTÓNOMO'),
       (3, 'FORMATIVO'),
       (4, 'PARCIAL'),
       (5, 'RELEVO'),
       (6, 'FIJO DISCONTINUO'),
       (7, 'OTROS');

/*Data for the table `requested_professional_experience` */

insert into `requested_professional_experience`(`id`, `occupation`)
values (1, 'OFICIAL DE PELUQUERÍA'),
       (2, 'ESTUDIANTE DE PELUQUERÍA'),
       (3, 'AYUDANTE DE PELUQUERÍA'),
       (4, 'FORMADOR TÉCNICO DE PELUQUERÍA'),
       (5, 'BARBERO'),
       (6, 'COMERCIAL VENTA PELUQUERÍA'),
       (7, 'ESTETICISTA'),
       (8, 'MANICURISTA'),
       (9, 'ESTUDIANTE DE ESTÉTICA'),
       (10, 'FORMADOR/TÉCNICO DE ESTÉTICA'),
       (11, 'TÉCNICO FX'),
       (12, 'FISIOTERAPEUTA'),
       (13, 'OSTEÓPATA'),
       (14, 'MASAJISTA'),
       (15, 'QUIROMASAJE'),
       (16, 'PELUQUERO CANINO');

/*Data for the table `workday` */

insert into `workday`(`id`, `hours`, `name`)
values (1, 8, 'COMPLETA'),
       (2, 4, 'MEDIA JORNADA MAÑANA'),
       (3, 4, 'MEDIA JORNADA TARDE'),
       (4, 0, 'FLEXIBLE'),
       (5, 8, 'FINES DE SEMANA');

/*Data for the table `years_of_experience` */

insert into `years_of_experience`(`id`, `description`)
values (1, '1 AÑO'),
       (2, '2 AÑOS'),
       (3, 'ENTRE 3 Y 5 AÑOS'),
       (4, 'ENTRE 5 Y 10 AÑOS'),
       (5, 'MÁS DE 10 AÑOS'),
       (6, 'ESTUDIANTE EN PRÁCTICAS'),
       (7, 'SIN EXPERIENCIA');

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

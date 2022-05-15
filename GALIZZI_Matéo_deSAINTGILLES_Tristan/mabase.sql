-- phpMyAdmin SQL Dump
-- version 4.7.1
-- https://www.phpmyadmin.net/
--
-- Hôte : sql11.freemysqlhosting.net
-- Généré le :  Dim 15 mai 2022 à 18:18
-- Version du serveur :  5.5.62-0ubuntu0.14.04.1
-- Version de PHP :  7.0.33-0ubuntu0.16.04.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `sql11491980`
--

-- --------------------------------------------------------

--
-- Structure de la table `CHAMBRE`
--

CREATE TABLE `CHAMBRE` (
  `IDHotel` int(11) NOT NULL DEFAULT '0',
  `NumeroChambre` int(11) NOT NULL DEFAULT '1',
  `Prix` float NOT NULL,
  `NbPlaces` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `CHAMBRE`
--

INSERT INTO `CHAMBRE` (`IDHotel`, `NumeroChambre`, `Prix`, `NbPlaces`) VALUES
(1, 18, 39.99, 1),
(1, 23, 69.99, 2),
(1, 42, 124.99, 2),
(2, 101, 99.99, 1),
(2, 222, 222.22, 2),
(2, 432, 349.99, 4),
(3, 1, 999.99, 1),
(3, 2, 10, 2),
(3, 3, 87379, 10),
(4, 1, 79.99, 2);

-- --------------------------------------------------------

--
-- Structure de la table `CLIENT`
--

CREATE TABLE `CLIENT` (
  `IDClient` int(11) NOT NULL,
  `NomClient` char(50) NOT NULL,
  `PrenomClient` char(50) DEFAULT NULL,
  `VilleCl` char(100) NOT NULL,
  `CPCl` int(5) NOT NULL,
  `NumCl` char(10) NOT NULL,
  `RueCl` char(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `CLIENT`
--

INSERT INTO `CLIENT` (`IDClient`, `NomClient`, `PrenomClient`, `VilleCl`, `CPCl`, `NumCl`, `RueCl`) VALUES
(1, 'de SAINT GILLES', 'Tristan', 'Saint Maurice en Riviere', 71620, '59', 'Grande Rue'),
(2, 'Lutte', 'Arthur', 'Besancon', 25000, '2', 'Luc dUr'),
(3, 'Lebbe', 'Michel', 'Nice', 6100, '99', 'Rue Incroyable'),
(4, 'Lutte', 'Arthur', 'Dijon', 21000, '45', 'Folle Rue'),
(5, 'Pierre', 'Jean', 'Paris', 75000, '2', 'de Gaulle'),
(6, 'Basset', 'Jordan', 'Chalon sur sa“ne', 71100, '5', 'rue du basset'),
(8, 'galizzi', 'mateo', 'besancon', 25000, '1', 'megevand'),
(9, 'galizzi', 'mateo', 'besancon', 25000, '36', 'rue');

-- --------------------------------------------------------

--
-- Structure de la table `HOTEL`
--

CREATE TABLE `HOTEL` (
  `IDHotel` int(11) NOT NULL DEFAULT '0',
  `NomHotel` char(30) NOT NULL,
  `VilleH` char(100) NOT NULL,
  `CPH` int(5) NOT NULL,
  `NumRueH` char(10) NOT NULL,
  `RueH` char(100) NOT NULL,
  `categorie` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `HOTEL`
--

INSERT INTO `HOTEL` (`IDHotel`, `NomHotel`, `VilleH`, `CPH`, `NumRueH`, `RueH`, `categorie`) VALUES
(1, 'California Hotel', 'Besancon', 25000, '36', 'rue des Potiers', 3),
(2, 'Antenne Hotel', 'Besancon', 25260, '2', 'rue des Amandes', 2),
(3, 'Maitre Kebabiste Hotel', 'Saint Maurice en Riviere', 71620, '10', 'rue Fugace', 1),
(4, 'California Hotel', 'Besancon', 25000, '12', 'rue du Chien', 4);

-- --------------------------------------------------------

--
-- Structure de la table `RESERVATION`
--

CREATE TABLE `RESERVATION` (
  `IDClient` int(11) NOT NULL DEFAULT '0',
  `IDHotel` int(11) NOT NULL DEFAULT '0',
  `NumeroChambre` int(11) NOT NULL DEFAULT '1',
  `DateReservation` date NOT NULL,
  `Paye` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `RESERVATION`
--

INSERT INTO `RESERVATION` (`IDClient`, `IDHotel`, `NumeroChambre`, `DateReservation`, `Paye`) VALUES
(3, 2, 222, '2022-05-14', 1),
(4, 4, 1, '2022-05-14', 1),
(5, 2, 101, '0001-01-01', 0),
(5, 2, 101, '0004-04-04', 0),
(5, 2, 101, '2022-05-15', 1),
(9, 2, 101, '2022-06-15', 0);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `CHAMBRE`
--
ALTER TABLE `CHAMBRE`
  ADD PRIMARY KEY (`IDHotel`,`NumeroChambre`);

--
-- Index pour la table `CLIENT`
--
ALTER TABLE `CLIENT`
  ADD PRIMARY KEY (`IDClient`),
  ADD UNIQUE KEY `IDClient` (`IDClient`);

--
-- Index pour la table `HOTEL`
--
ALTER TABLE `HOTEL`
  ADD PRIMARY KEY (`IDHotel`),
  ADD UNIQUE KEY `IDHotel` (`IDHotel`);

--
-- Index pour la table `RESERVATION`
--
ALTER TABLE `RESERVATION`
  ADD PRIMARY KEY (`IDClient`,`IDHotel`,`NumeroChambre`,`DateReservation`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `CLIENT`
--
ALTER TABLE `CLIENT`
  MODIFY `IDClient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

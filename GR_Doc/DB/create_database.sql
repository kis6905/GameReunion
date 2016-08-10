
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `{#db_database_name#}` ;
CREATE SCHEMA IF NOT EXISTS `{#db_database_name#}` DEFAULT CHARACTER SET utf8 ;
USE `{#db_database_name#}` ;


-- -----------------------------------------------------
-- Table `gr_com_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gr_com_code` (
  `codeGroup` INT NOT NULL,
  `code` INT NOT NULL,
  `codeTitle` VARCHAR(255) NOT NULL,
  `codeValue` VARCHAR(255) NULL,
  PRIMARY KEY (`codeGroup`, `code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gr_member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gr_member` (
  `memberId` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `passwordFailCnt` INT NOT NULL DEFAULT 0,
  `email` VARCHAR(255) DEFAULT NULL,
  `nickname` VARCHAR(255) NOT NULL,
  `gradeCodeGroup` INT NOT NULL,
  `gradeCode` INT NOT NULL,
  `registeredDate` DATETIME NOT NULL,
  `modifiedDate` DATETIME NOT NULL,
  `lastLoginDate` DATETIME DEFAULT NULL,
  PRIMARY KEY (`memberId`),
  INDEX `fk_gr_member_gr_com_code1_idx` (`gradeCodeGroup` ASC, `gradeCode` ASC),
  CONSTRAINT `fk_gr_member_gr_com_code1`
    FOREIGN KEY (`gradeCodeGroup` , `gradeCode`)
    REFERENCES `gr_com_code` (`codeGroup` , `code`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gr_persistent_login`
-- -----------------------------------------------------
CREATE TABLE `gr_persistent_login` (
	`memberId` VARCHAR(64) NOT NULL,
	`series` VARCHAR(64) NOT NULL,
	`token` VARCHAR(64) NOT NULL,
	`lastUsed` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`series`)
);


-- -----------------------------------------------------
-- Table `gr_friends`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gr_friends` (
  `fromMemberId` VARCHAR(255) NOT NULL,
  `toMemberId` VARCHAR(255) NOT NULL,
  `status` CHAR(1) NOT NULL,
  `requestedDate` DATETIME NOT NULL,
  INDEX `fk_gr_friends_gr_member_idx` (`fromMemberId` ASC),
  INDEX `fk_gr_friends_gr_member1_idx` (`toMemberId` ASC),
  PRIMARY KEY (`fromMemberId`, `toMemberId`),
  CONSTRAINT `fk_gr_friends_gr_member`
    FOREIGN KEY (`fromMemberId`)
    REFERENCES `gr_member` (`memberId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_gr_friends_gr_member1`
    FOREIGN KEY (`toMemberId`)
    REFERENCES `gr_member` (`memberId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gr_recevied_message`
-- -----------------------------------------------------
CREATE TABLE `gr_recevied_message` (
  `seq` int(11) NOT NULL,
  `fromMemberId` varchar(255) NOT NULL,
  `toMemberId` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `contents` varchar(255) NOT NULL,
  `confirmStatus` char(1) NOT NULL DEFAULT 'N',
  `registeredDate` datetime NOT NULL,
  PRIMARY KEY (`seq`),
  KEY `fk_gr_recevied_message_gr_member2_idx` (`toMemberId`),
  KEY `fk_gr_recevied_message_gr_member1` (`fromMemberId`),
  CONSTRAINT `fk_gr_recevied_message_gr_member1` 
  	FOREIGN KEY (`fromMemberId`)
  	REFERENCES `gr_member` (`memberId`) 
  	ON DELETE CASCADE 
  	ON UPDATE CASCADE,
  CONSTRAINT `fk_gr_recevied_message_gr_member2` 
  	FOREIGN KEY (`toMemberId`) 
  	REFERENCES `gr_member` (`memberId`) 
  	ON DELETE CASCADE 
  	ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- -----------------------------------------------------
-- Table `gr_sent_message`
-- -----------------------------------------------------
CREATE TABLE `gr_sent_message` (
  `seq` int(11) NOT NULL,
  `fromMemberId` varchar(255) NOT NULL,
  `toMemberId` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `contents` varchar(255) NOT NULL,
  `confirmStatus` char(1) NOT NULL DEFAULT 'N',
  `registeredDate` datetime NOT NULL,
  PRIMARY KEY (`seq`),
  KEY `fk_gr_sent_message_gr_member2_idx` (`toMemberId`),
  KEY `fk_gr_sent_message_gr_member1_idx` (`fromMemberId`),
  CONSTRAINT `fk_gr_sent_message_gr_member1` 
  	FOREIGN KEY (`fromMemberId`) 
  	REFERENCES `gr_member` (`memberId`) 
  	ON DELETE CASCADE 
  	ON UPDATE CASCADE,
  CONSTRAINT `fk_gr_sent_message_gr_member2` 
  	FOREIGN KEY (`toMemberId`) 
  	REFERENCES `gr_member` (`memberId`) 
  	ON DELETE CASCADE 
  	ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- -----------------------------------------------------
-- Table `gr_game`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gr_game` (
  `gameCode` VARCHAR(10) NOT NULL,
  `gameName` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `registeredDate` DATETIME NOT NULL,
  `modifiedDate` DATETIME NOT NULL,
  PRIMARY KEY (`gameCode`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gr_genre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gr_genre` (
  `genreCode` VARCHAR(10) NOT NULL,
  `genreName` VARCHAR(255) NOT NULL,
  `registeredDate` DATETIME NOT NULL,
  `modifiedDate` DATETIME NOT NULL,
  PRIMARY KEY (`genreCode`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gr_map_game_genre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gr_map_game_genre` (
  `genreCode` VARCHAR(10) NOT NULL,
  `gameCode` VARCHAR(10) NOT NULL,
  INDEX `fk_gr_map_game_genre_gr_genre1_idx` (`genreCode` ASC),
  INDEX `fk_gr_map_game_genre_gr_game1_idx` (`gameCode` ASC),
  CONSTRAINT `fk_gr_map_game_genre_gr_genre1`
    FOREIGN KEY (`genreCode`)
    REFERENCES `gr_genre` (`genreCode`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_gr_map_game_genre_gr_game1`
    FOREIGN KEY (`gameCode`)
    REFERENCES `gr_game` (`gameCode`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gr_bookmark`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gr_bookmark` (
  `memberId` VARCHAR(255) NOT NULL,
  `gameCode` VARCHAR(10) NOT NULL,
  INDEX `fk_gr_bookmark_gr_member1_idx` (`memberId` ASC),
  INDEX `fk_gr_bookmark_gr_game1_idx` (`gameCode` ASC),
  CONSTRAINT `fk_gr_bookmark_gr_member1`
    FOREIGN KEY (`memberId`)
    REFERENCES `gr_member` (`memberId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_gr_bookmark_gr_game1`
    FOREIGN KEY (`gameCode`)
    REFERENCES `gr_game` (`gameCode`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gr_game_server`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gr_game_server` (
  `serverName` VARCHAR(255) NOT NULL,
  `gameCode` VARCHAR(10) NOT NULL,
  `registeredDate` DATETIME NOT NULL,
  `modifiedDate` DATETIME NOT NULL,
  PRIMARY KEY (`serverName`),
  CONSTRAINT `fk_gr_game_server_gr_game1`
    FOREIGN KEY (`gameCode`)
    REFERENCES `gr_game` (`gameCode`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gr_find_board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gr_find_board` (
  `no` INT NOT NULL,
  `gameCode` VARCHAR(10) NOT NULL,
  `serverName` VARCHAR(255) NULL,
  `memberId` VARCHAR(255) NOT NULL,
  `title` VARCHAR(255),
  `contents` VARCHAR(255) NOT NULL,
  `registeredDate` DATETIME NOT NULL,
  `modifiedDate` DATETIME NOT NULL,
  PRIMARY KEY (`no`, `gameCode`),
  INDEX `fk_gr_find_board_gr_game1_idx` (`gameCode` ASC),
  INDEX `fk_gr_find_board_gr_game_server1_idx` (`serverName` ASC),
  INDEX `fk_gr_find_board_gr_member1_idx` (`memberId` ASC),
  CONSTRAINT `fk_gr_find_board_gr_game1`
    FOREIGN KEY (`gameCode`)
    REFERENCES `gr_game` (`gameCode`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_gr_find_board_gr_game_server1`
    FOREIGN KEY (`serverName`)
    REFERENCES `gr_game_server` (`serverName`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_gr_find_board_gr_member1`
    FOREIGN KEY (`memberId`)
    REFERENCES `gr_member` (`memberId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gr_free_board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gr_free_board` (
  `no` INT NOT NULL,
  `gameCode` VARCHAR(10) NULL,
  `memberId` VARCHAR(255) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `contents` VARCHAR(255) NOT NULL,
  `registeredDate` DATETIME NOT NULL,
  `modifiedDate` DATETIME NOT NULL,
  PRIMARY KEY (`no`),
  INDEX `fk_gr_free_board_gr_game1_idx` (`gameCode` ASC),
  INDEX `fk_gr_free_board_gr_member1_idx` (`memberId` ASC),
  CONSTRAINT `fk_gr_free_board_gr_game1`
    FOREIGN KEY (`gameCode`)
    REFERENCES `gr_game` (`gameCode`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_gr_free_board_gr_member1`
    FOREIGN KEY (`memberId`)
    REFERENCES `gr_member` (`memberId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gr_free_board_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gr_free_board_comment` (
  `commentNo` INT NOT NULL,
  `boardNo` INT NOT NULL,
  `memberId` VARCHAR(255) NOT NULL,
  `contents` VARCHAR(255) NOT NULL,
  `registeredDate` DATETIME NOT NULL,
  `modifiedDate` DATETIME NOT NULL,
  INDEX `fk_table1_gr_free_board1_idx` (`boardNo` ASC),
  INDEX `fk_table1_gr_member1_idx` (`memberId` ASC),
  PRIMARY KEY (`commentNo`),
  CONSTRAINT `fk_table1_gr_free_board1`
    FOREIGN KEY (`boardNo`)
    REFERENCES `gr_free_board` (`no`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_table1_gr_member1`
    FOREIGN KEY (`memberId`)
    REFERENCES `gr_member` (`memberId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gr_notice_board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gr_notice_board` (
  `no` INT NOT NULL,
  `memberId` VARCHAR(255) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `contents` VARCHAR(255) NOT NULL,
  `registeredDate` DATETIME NOT NULL,
  `modifiedDate` DATETIME NOT NULL,
  PRIMARY KEY (`no`),
  INDEX `fk_gr_notice_board_gr_member1_idx` (`memberId` ASC),
  CONSTRAINT `fk_gr_notice_board_gr_member1`
    FOREIGN KEY (`memberId`)
    REFERENCES `gr_member` (`memberId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

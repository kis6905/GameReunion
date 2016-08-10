
-- -----------------------------------------------------
-- Table `gr_com_code`
-- -----------------------------------------------------
INSERT INTO `gr_com_code` (`codeGroup`, `code`, `codeTitle`, `codeValue`) VALUES (0, 1001, 'Admin', '1001');
INSERT INTO `gr_com_code` (`codeGroup`, `code`, `codeTitle`, `codeValue`) VALUES (0, 1002, 'User', '1002');

INSERT INTO `gr_com_code` (`codeGroup`, `code`, `codeTitle`, `codeValue`) VALUES (1, 1101, 'Valid Password Fail Count', '10');


-- -----------------------------------------------------
-- Table `gr_genre`
-- -----------------------------------------------------
INSERT INTO `gr_genre` (`genreCode`, `genreName`, `registeredDate`, `modifiedDate`) VALUES ('GE001', 'MMORPG', SYSDATE(), SYSDATE());
INSERT INTO `gr_genre` (`genreCode`, `genreName`, `registeredDate`, `modifiedDate`) VALUES ('GE002', '슈팅', SYSDATE(), SYSDATE());
INSERT INTO `gr_genre` (`genreCode`, `genreName`, `registeredDate`, `modifiedDate`) VALUES ('GE003', '아케이드', SYSDATE(), SYSDATE());
INSERT INTO `gr_genre` (`genreCode`, `genreName`, `registeredDate`, `modifiedDate`) VALUES ('GE004', 'FPS', SYSDATE(), SYSDATE());
INSERT INTO `gr_genre` (`genreCode`, `genreName`, `registeredDate`, `modifiedDate`) VALUES ('GE005', '레이싱', SYSDATE(), SYSDATE());


-- -----------------------------------------------------
-- Table `gr_game`
-- -----------------------------------------------------
INSERT INTO `gr_game` (`gameCode`, `gameName`, `description`, `registeredDate`, `modifiedDate`) VALUES ('GA001', '리니지', 'RPG의 레전드! 수많은 폐인을 만든 그 게임!', SYSDATE(), SYSDATE());
INSERT INTO `gr_game` (`gameCode`, `gameName`, `description`, `registeredDate`, `modifiedDate`) VALUES ('GA002', '바람의 나라', 'RPG의 레전드! 바람의 나라를 모르는 사람이 있을까!?', SYSDATE(), SYSDATE());
INSERT INTO `gr_game` (`gameCode`, `gameName`, `description`, `registeredDate`, `modifiedDate`) VALUES ('GA003', '크레이지 아케이드', '크아! 말이 필요 없다', SYSDATE(), SYSDATE());
INSERT INTO `gr_game` (`gameCode`, `gameName`, `description`, `registeredDate`, `modifiedDate`) VALUES ('GA004', '포트리스', '포트리스... 안해본 사람 있나요?', SYSDATE(), SYSDATE());


-- -----------------------------------------------------
-- Table `gr_game_server`
-- -----------------------------------------------------
INSERT INTO `gr_game_server` (`serverName`, `gameCode`, `registeredDate`, `modifiedDate`) VALUES ('이실로테', 'GA001', SYSDATE(), SYSDATE());
INSERT INTO `gr_game_server` (`serverName`, `gameCode`, `registeredDate`, `modifiedDate`) VALUES ('아데나', 'GA001', SYSDATE(), SYSDATE());
INSERT INTO `gr_game_server` (`serverName`, `gameCode`, `registeredDate`, `modifiedDate`) VALUES ('발록', 'GA001', SYSDATE(), SYSDATE());
INSERT INTO `gr_game_server` (`serverName`, `gameCode`, `registeredDate`, `modifiedDate`) VALUES ('연', 'GA002', SYSDATE(), SYSDATE());
INSERT INTO `gr_game_server` (`serverName`, `gameCode`, `registeredDate`, `modifiedDate`) VALUES ('봉황', 'GA002', SYSDATE(), SYSDATE());
INSERT INTO `gr_game_server` (`serverName`, `gameCode`, `registeredDate`, `modifiedDate`) VALUES ('유리', 'GA002', SYSDATE(), SYSDATE());
INSERT INTO `gr_game_server` (`serverName`, `gameCode`, `registeredDate`, `modifiedDate`) VALUES ('해피', 'GA003', SYSDATE(), SYSDATE());
INSERT INTO `gr_game_server` (`serverName`, `gameCode`, `registeredDate`, `modifiedDate`) VALUES ('드림', 'GA003', SYSDATE(), SYSDATE());
INSERT INTO `gr_game_server` (`serverName`, `gameCode`, `registeredDate`, `modifiedDate`) VALUES ('알파', 'GA004', SYSDATE(), SYSDATE());
INSERT INTO `gr_game_server` (`serverName`, `gameCode`, `registeredDate`, `modifiedDate`) VALUES ('베타', 'GA004', SYSDATE(), SYSDATE());
INSERT INTO `gr_game_server` (`serverName`, `gameCode`, `registeredDate`, `modifiedDate`) VALUES ('감마', 'GA004', SYSDATE(), SYSDATE());


-- -----------------------------------------------------
-- Table `gr_map_game_genre`
-- -----------------------------------------------------
INSERT INTO `gr_map_game_genre` (`genreCode`, `gameCode`) VALUES ('GE001', 'GA001');
INSERT INTO `gr_map_game_genre` (`genreCode`, `gameCode`) VALUES ('GE001', 'GA002');
INSERT INTO `gr_map_game_genre` (`genreCode`, `gameCode`) VALUES ('GE003', 'GA003');
INSERT INTO `gr_map_game_genre` (`genreCode`, `gameCode`) VALUES ('GE002', 'GA004');

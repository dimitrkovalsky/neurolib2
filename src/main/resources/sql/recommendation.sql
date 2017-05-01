CREATE TABLE `recommendation` (
  `BookId`           INT(11) NOT NULL,
  `RecommendationId` INT(11) NOT NULL,
  PRIMARY KEY (`BookId`, `RecommendationId`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

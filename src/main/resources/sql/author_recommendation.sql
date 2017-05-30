CREATE TABLE `neurolib`.`author_recommendation` (
  `author_id` INT NOT NULL,
  `similar_id` INT NOT NULL,
  PRIMARY KEY (`author_id`, `similar_id`));

ALTER TABLE `neurolib`.`author_recommendation`
  ADD INDEX `authorIndex` (`author_id` ASC);
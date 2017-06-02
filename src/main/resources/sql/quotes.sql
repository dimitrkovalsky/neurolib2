CREATE TABLE `quote_author` (
  `id`          INT(11)      NOT NULL,
  `name`        VARCHAR(255) NOT NULL,
  `bio`         LONGTEXT,
  `flibusta_id` INT(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `quote` (
  `id`                 INT(11)      NOT NULL,
  `text`               LONGTEXT     NOT NULL,
  `quote_author_id`    INT(11)      NOT NULL,
  `flibusta_author_id` INT(11) DEFAULT NULL,
  `quote_author_name`  VARCHAR(255) NOT NULL,
  `tags`               JSON    DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
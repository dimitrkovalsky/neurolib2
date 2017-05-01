CREATE
  ALGORITHM = UNDEFINED
  DEFINER = `root`@`localhost`
  SQL SECURITY DEFINER
VIEW `full_book` AS
  SELECT
    `b`.`BookId`  AS `BookId`,
    `b`.`Title`   AS `Title`,
    `b`.`Lang`    AS `Lang`,
    `b`.`Deleted` AS `Deleted`,
    `b`.`Pages`   AS `Pages`,
    `b`.`Chars`   AS `Chars`,
    `g`.`GenreId` AS `GenreId`,
    `a`.`AvtorId` AS `AuthorId`,
    `s`.`SeqId`   AS `TagId`,
    `r`.`Rate`    AS `Rate`
  FROM
    ((((`libbook` `b`
      JOIN `libgenre` `g` ON ((`b`.`BookId` = `g`.`BookId`)))
      JOIN `libavtor` `a` ON ((`b`.`BookId` = `a`.`BookId`)))
      JOIN `libseq` `s` ON ((`b`.`BookId` = `s`.`BookId`)))
      JOIN `rate_view` `r` ON ((`b`.`BookId` = `r`.`BookId`)))
  WHERE
    (`b`.`Deleted` = FALSE)
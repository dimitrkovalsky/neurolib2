CREATE
  ALGORITHM = UNDEFINED
  DEFINER = `root`@`localhost`
  SQL SECURITY DEFINER
VIEW `book_card` AS
  SELECT
    `b`.`BookId` AS `BookId`,
    `b`.`Title` AS `Title`,
    `b`.`Lang` AS `Lang`,
    `g`.`GenreId` AS `GenreId`,
    `a`.`AvtorId` AS `AuthorId`,
    `an`.`FirstName` AS `AuthorFirstName`,
    `an`.`LastName` AS `AuthorLastName`,
    `jl`.`GenreDesc` AS `GenreDesc`,
    `jl`.`GenreMeta` AS `GenreMeta`,
    `r`.`Rate` AS `Rate`
  FROM
    ((((((`libbook` `b`
      JOIN `libgenre` `g` ON ((`b`.`BookId` = `g`.`BookId`)))
      JOIN `libavtor` `a` ON ((`b`.`BookId` = `a`.`BookId`)))
      JOIN `libseq` `s` ON ((`b`.`BookId` = `s`.`BookId`)))
      JOIN `rate_view` `r` ON ((`b`.`BookId` = `r`.`BookId`)))
      JOIN `libavtorname` `an` ON ((`a`.`AvtorId` = `an`.`AvtorId`)))
      JOIN `libgenrelist` `jl` ON ((`g`.`GenreId` = `jl`.`GenreId`)))
  WHERE
    (`b`.`Deleted` = FALSE)
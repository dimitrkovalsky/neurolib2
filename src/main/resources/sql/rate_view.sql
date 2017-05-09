CREATE
    ALGORITHM = UNDEFINED
    DEFINER = `root`@`localhost`
    SQL SECURITY DEFINER
VIEW `rate_view` AS
    SELECT
        `librate`.`BookId` AS `BookId`,
        AVG(`librate`.`Rate`) AS `Rate`
    FROM
        `librate`
    GROUP BY `librate`.`BookId`
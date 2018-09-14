-- -----------------------------------------------------
-- Table `currency_fair`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `currency_fair`.`message` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL,
  `originating_country` CHAR(2) NOT NULL,
  `currency_from` CHAR(3) NOT NULL,
  `currency_to` CHAR(3) NOT NULL,
  `amount_sell` DOUBLE(10,2) NOT NULL,
  `amount_buy` DOUBLE(10,2) NOT NULL,
  `rate` DOUBLE(10,5) NOT NULL,
  `time_placed` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`));

-- -----------------------------------------------------
-- Table `currency_fair`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `currency_fair`.`message` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL,
  `originating_country` VARCHAR(3) NOT NULL,
  `currency_from` VARCHAR(3) NOT NULL,
  `currency_to` VARCHAR(3) NOT NULL,
  `amount_sell` DOUBLE(10,5) NOT NULL,
  `amount_buy` DOUBLE(10,5) NOT NULL,
  `rate` DOUBLE(10,5) NOT NULL,
  `time_placed` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`));

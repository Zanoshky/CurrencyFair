-- -----------------------------------------------------
-- Table `currency_fair`.`currency_pair`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `currency_fair`.`currency_pair` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `currency_from` CHAR(3) NOT NULL,
  `currency_to` CHAR(3) NOT NULL,
  PRIMARY KEY (`id`));

-- -----------------------------------------------------
-- Table `currency_fair`.`currency_pair_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `currency_fair`.`currency_pair_detail` (
  `time_id` DATETIME NOT NULL,
  `currency_pair_id` INT UNSIGNED NOT NULL,
  `count` INT NOT NULL,
  PRIMARY KEY (`currency_pair_id`, `time_id`),
  CONSTRAINT `fk_currency_pair_detail_currency_pair`
    FOREIGN KEY (`currency_pair_id`)
    REFERENCES `currency_fair`.`currency_pair` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
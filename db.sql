CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `full_name` VARCHAR(50) NOT NULL,
  `username` VARCHAR(20) NOT NULL UNIQUE,
  `email` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(50) NOT NULL,
  `image_url` VARCHAR(255),
  `is_active` TINYINT(1) DEFAULT 1,
  `account_non_locked` TINYINT(1) DEFAULT 1,
  `failed_attempt` INT DEFAULT 0,
  `lock_time` DATETIME DEFAULT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

CREATE TABLE `category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL, 
  `name` VARCHAR(50) NOT NULL UNIQUE,
  `color_code` VARCHAR(10),
  `type` ENUM('Income', 'Expense') NOT NULL, 
  `description` TEXT,
  `is_active` TINYINT(1) DEFAULT 1,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
);

CREATE TABLE `account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(50),
  `name` VARCHAR(100) NOT NULL,
  `type` VARCHAR(100) NOT NULL,
  `is_active` TINYINT(1) DEFAULT 1,
  `remarks` TEXT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

CREATE TABLE `transaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` VARCHAR(255),
  `system_entry_no` VARCHAR(25) UNIQUE NOT NULL,
  `amount` DOUBLE DEFAULT 0 NOT NULL,
  `remarks` TEXT,
  `category_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `account_id` INT NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`category_id`) REFERENCES `category`(`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
  FOREIGN KEY (`account_id`) REFERENCES `account`(`id`)
);

CREATE TABLE `budget` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `amount` DOUBLE DEFAULT 0 NOT NULL,
  `remarks` TEXT,
  `category_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `period` ENUM('Daily', 'Monthly', 'Yearly') NOT NULL, 
  `notification_threshold` INT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`category_id`) REFERENCES `category`(`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
);








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
  `name` VARCHAR(50) NOT NULL UNIQUE,
  `color_code` VARCHAR(10),
  `type` ENUM('Income', 'Expense') NOT NULL, 
  `description` TEXT,
  `is_active` TINYINT(1) DEFAULT 1,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
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
  `period` ENUM('Daily', 'Monthly', 'Yearly') NOT NULL, 
  `notification_threshold` INT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`category_id`) REFERENCES `category`(`id`)
);


-- ========================
-- USERS
-- ========================
INSERT INTO `user` (id, full_name, username, email, password, image_url, is_active, account_non_locked)
VALUES
(101, 'Alice', 'alice', 'alice@example.com', 'password', NULL, 1, 1),
(102, 'Bob', 'bob', 'bob@example.com', 'password', NULL, 1, 1),
(103, 'Charlie', 'charlie', 'charlie@example.com', 'password', NULL, 1, 1),
(104, 'David', 'david', 'david@example.com', 'password', NULL, 1, 1),
(105, 'Eve', 'eve', 'eve@example.com', 'password', NULL, 1, 1);

-- ========================
-- ACCOUNTS
-- ========================
INSERT INTO `account` (id, code, name, type, is_active, remarks)
VALUES
(201, 'ACC201', 'Cash', 'Asset', 1, 'Cash in hand'),
(202, 'ACC202', 'Bank', 'Asset', 1, 'Bank account'),
(203, 'ACC203', 'Credit Card', 'Liability', 1, 'Credit card account'),
(204, 'ACC204', 'Cash', 'Asset', 1, 'Cash wallet'),
(205, 'ACC205', 'Bank', 'Asset', 1, 'Bank account');

-- ========================
-- CATEGORIES
-- ========================
INSERT INTO `category` (id, name, color_code, type, description, is_active)
VALUES
(1, 'Groceries', '#FF5733', 'Expense', 'Daily groceries and food items', 1),
(2, 'Utilities', '#33C1FF', 'Expense', 'Electricity, water, internet etc.', 1),
(3, 'Miscellaneous', '#DA33FF', 'Expense', 'Other expenses', 1),
(4, 'Transport', '#33FF57', 'Expense', 'Transport expenses like taxi, bus, fuel', 1),
(5, 'Dining Out', '#FFC300', 'Expense', 'Restaurants, coffee, snacks', 1),
(6, 'Entertainment', '#FF5733', 'Expense', 'Movies, subscriptions, fun', 1),
(7, 'Education', '#33FFF6', 'Expense', 'Books, stationery, courses', 1),
(8, 'Health', '#FF33A6', 'Expense', 'Medical, gym, health related', 1),
(9, 'Personal Care', '#C70039', 'Expense', 'Haircut, personal care', 1);

-- ========================
-- TRANSACTIONS
-- ========================
INSERT INTO `transaction` (id, date, system_entry_no, amount, remarks, category_id, user_id, account_id)
VALUES
(1, '2082-03-01', 'SE001', 300, 'Bought groceries', 1, 101, 201),
(2, '2082-03-01', 'SE002', 200, 'Electricity bill', 2, 102, 202),
(3, '2082-03-01', 'SE003', 250, 'Other expenses', 3, 103, 203),
(4, '2082-03-02', 'SE004', 150, 'Bus fare', 4, 104, 204),
(5, '2082-03-02', 'SE005', 400, 'Dinner out', 5, 105, 205),
(6, '2082-03-03', 'SE006', 500, 'Monthly internet', 2, 101, 201),
(7, '2082-03-03', 'SE007', 350, 'Weekly shopping', 1, 102, 202),
(8, '2082-03-03', 'SE008', 600, 'Electricity bill', 2, 103, 203),
(9, '2082-03-04', 'SE009', 250, 'Cinema ticket', 6, 104, 204),
(10, '2082-03-04', 'SE010', 300, 'Taxi ride', 4, 105, 205),
(11, '2082-03-05', 'SE011', 200, 'Office lunch', 5, 101, 201),
(12, '2082-03-05', 'SE012', 100, 'Evening snacks', 5, 102, 202),
(13, '2082-03-05', 'SE013', 450, 'Petrol refill', 4, 103, 203),
(14, '2082-03-06', 'SE014', 600, 'Stationery', 7, 104, 204),
(15, '2082-03-06', 'SE015', 80, 'Coffee break', 5, 105, 205),
(16, '2082-03-07', 'SE016', 1200, 'Streaming service', 6, 101, 201),
(17, '2082-03-07', 'SE017', 700, 'Birthday gift', 3, 102, 202),
(18, '2082-03-07', 'SE018', 1000, 'Monthly fee', 8, 103, 203),
(19, '2082-03-08', 'SE019', 150, 'Fruit market', 1, 104, 204),
(20, '2082-03-08', 'SE020', 350, 'Salon visit', 9, 105, 205),
(21, '2082-03-09', 'SE021', 500, 'Pharmacy', 8, 101, 201),
(22, '2082-03-09', 'SE022', 100, 'Parking fee', 4, 102, 202),
(23, '2082-03-09', 'SE023', 150, 'Drinking water', 2, 103, 203),
(24, '2082-03-10', 'SE024', 50, 'Daily news', 6, 104, 204),
(25, '2082-03-10', 'SE025', 250, 'Office taxi', 4, 105, 205),
(26, '2082-03-10', 'SE026', 500, 'Weekly groceries', 1, 101, 201),
(27, '2082-03-11', 'SE027', 500, 'Monthly internet', 2, 102, 202),
(28, '2082-03-11', 'SE028', 80, 'Coffee shop', 5, 103, 203),
(29, '2082-03-11', 'SE029', 200, 'Fruit shopping', 1, 104, 204),
(30, '2082-03-12', 'SE030', 600, 'Full tank', 4, 105, 205),
(31, '2082-03-12', 'SE031', 120, 'Evening snacks', 5, 101, 201),
(32, '2082-03-12', 'SE032', 250, 'Office lunch', 5, 102, 202),
(33, '2082-03-12', 'SE033', 450, 'Course material', 7, 103, 203),
(34, '2082-03-13', 'SE034', 300, 'Meeting taxi', 4, 104, 204),
(35, '2082-03-13', 'SE035', 800, 'Friend birthday', 3, 105, 205),
(36, '2082-03-13', 'SE036', 1200, 'Music streaming', 6, 101, 201),
(37, '2082-03-14', 'SE037', 1000, 'Fitness fee', 8, 102, 202),
(38, '2082-03-14', 'SE038', 180, 'Fruit stall', 1, 103, 203),
(39, '2082-03-14', 'SE039', 400, 'Hair salon', 9, 104, 204),
(40, '2082-03-15', 'SE040', 550, 'Pharmacy', 8, 105, 205),
(41, '2082-03-15', 'SE041', 220, 'Quick lunch', 5, 101, 201),
(42, '2082-03-15', 'SE042', 90, 'Morning coffee', 5, 102, 202),
(43, '2082-03-15', 'SE043', 260, 'Weekend movie', 6, 103, 203),
(44, '2082-03-16', 'SE044', 550, 'Partial refill', 4, 104, 204),
(45, '2082-03-16', 'SE045', 500, 'Reference book', 7, 105, 205),
(46, '2082-03-16', 'SE046', 900, 'Special gift', 3, 101, 201),
(47, '2082-03-17', 'SE047', 1100, 'Annual subscription', 6, 102, 202),
(48, '2082-03-17', 'SE048', 950, 'Monthly gym', 8, 103, 203),
(49, '2082-03-17', 'SE049', 130, 'Evening snack', 5, 104, 204),
(50, '2082-03-18', 'SE050', 370, 'Trim and style', 9, 105, 205);

-- ========================
-- BUDGETS
-- ========================
INSERT INTO `budget` (id, amount, remarks, category_id, period, notification_threshold)
VALUES
(1, 5000, 'Daily groceries budget', 1, 'Daily', 80),
(2, 20000, 'Monthly utilities budget', 2, 'Monthly', 75),
(3, 10000, 'Miscellaneous expenses budget', 3, 'Monthly', 70),
(4, 3000, 'Daily transport budget', 4, 'Daily', 80),
(5, 8000, 'Monthly dining out budget', 5, 'Monthly', 70),
(6, 5000, 'Monthly entertainment budget', 6, 'Monthly', 75),
(7, 4000, 'Monthly education budget', 7, 'Monthly', 70),
(8, 6000, 'Monthly health budget', 8, 'Monthly', 75),
(9, 3000, 'Monthly personal care budget', 9, 'Monthly', 70),
(10, 100000, 'Yearly groceries budget', 1, 'Yearly', 80),
(11, 240000, 'Yearly utilities budget', 2, 'Yearly', 75),
(12, 120000, 'Yearly miscellaneous budget', 3, 'Yearly', 70),
(13, 36000, 'Yearly transport budget', 4, 'Yearly', 80),
(14, 96000, 'Yearly dining out budget', 5, 'Yearly', 70),
(15, 60000, 'Yearly entertainment budget', 6, 'Yearly', 75),
(16, 48000, 'Yearly education budget', 7, 'Yearly', 70),
(17, 72000, 'Yearly health budget', 8, 'Yearly', 75),
(18, 36000, 'Yearly personal care budget', 9, 'Yearly', 70);






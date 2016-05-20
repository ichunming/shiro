use test;
DROP TABLE IF EXISTS `roles_permissions`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `permissions`;
DROP TABLE IF EXISTS `roles`;

#------------------------
# table roles
#------------------------
CREATE TABLE `roles` (  
  `role_id` int(10) unsigned NOT NULL AUTO_INCREMENT,  
  `role_name` varchar(64) COLLATE utf8_bin DEFAULT NULL,  
  `created_time` datetime DEFAULT NULL,  
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,  
  PRIMARY KEY(`role_id`)  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

#------------------------
# table permissions
#------------------------
CREATE TABLE `permissions` (  
  `permission_id` int(10) unsigned NOT NULL AUTO_INCREMENT,  
  `permission_name` varchar(64) COLLATE utf8_bin DEFAULT NULL,  
  `created_time` datetime DEFAULT NULL,  
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,  
  PRIMARY KEY(`permission_id`)  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

#------------------------
# table users
#------------------------
CREATE TABLE `users` (  
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,  
  `user_name` varchar(64) COLLATE utf8_bin DEFAULT NULL,  
  `password` varchar(128) COLLATE utf8_bin DEFAULT NULL,  
  `role_id` int(10),
  `created_time` datetime DEFAULT NULL,  
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,  
  PRIMARY KEY(`user_id`),  
  FOREIGN KEY(`user_id`) REFERENCES roles(`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

#------------------------
# table roles_permissions
#------------------------
CREATE TABLE `roles_permissions` (  
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,  
  `role_id` int(10) unsigned NOT NULL,  
  `permission_id` int(10) unsigned NOT NULL,  
  `created_time` datetime DEFAULT NULL,  
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,  
  PRIMARY KEY(`id`),
  FOREIGN KEY(`role_id`) REFERENCES roles(`role_id`),
  FOREIGN KEY(`permission_id`) REFERENCES permissions(`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

#------------------------
# roles
#------------------------
INSERT INTO roles(`role_name`) VALUES('admin');
INSERT INTO roles(`role_name`) VALUES('user');
INSERT INTO roles(`role_name`) VALUES('guest');

#------------------------
# permissions
#------------------------
INSERT INTO permissions(`permission_name`) VALUES('create');
INSERT INTO permissions(`permission_name`) VALUES('retrieve');
INSERT INTO permissions(`permission_name`) VALUES('update');
INSERT INTO permissions(`permission_name`) VALUES('delete');

#------------------------
# roles_permissions
#------------------------
INSERT INTO roles_permissions(`role_id`, `permission_id`) VALUES(1, 1);
INSERT INTO roles_permissions(`role_id`, `permission_id`) VALUES(1, 2);
INSERT INTO roles_permissions(`role_id`, `permission_id`) VALUES(1, 3);
INSERT INTO roles_permissions(`role_id`, `permission_id`) VALUES(1, 4);
INSERT INTO roles_permissions(`role_id`, `permission_id`) VALUES(2, 2);
INSERT INTO roles_permissions(`role_id`, `permission_id`) VALUES(2, 3);
INSERT INTO roles_permissions(`role_id`, `permission_id`) VALUES(3, 2);

#------------------------
# users
#------------------------
INSERT INTO users(`user_name`, `password`, `role_id`) VALUES('aTom', 'aTom', 1);
INSERT INTO users(`user_name`, `password`, `role_id`) VALUES('uPitter', 'uPitter', 2);
INSERT INTO users(`user_name`, `password`, `role_id`) VALUES('gLily', 'gLily', 3);





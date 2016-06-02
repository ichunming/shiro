use test;
DROP TABLE IF EXISTS `roles_permissions`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `permissions`;
DROP TABLE IF EXISTS `roles`;

#------------------------
# table users
#------------------------
CREATE TABLE `users` (  
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,  
  `user_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,  
  `password` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `password_salt` varchar(32),
  `roles` varchar(128),
  `created_time` datetime DEFAULT NULL,  
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP,  
  PRIMARY KEY(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

#------------------------
# table roles_permissions
#------------------------
CREATE TABLE `roles_permissions` (  
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,  
  `role` varchar(20) NOT NULL,  
  `permission` varchar(50) NOT NULL,  
  `created_time` datetime DEFAULT NULL,  
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP,  
  PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

#------------------------
# roles_permissions
#------------------------
INSERT INTO roles_permissions(`role`, `permission`) VALUES('admin', 'admin:create');
INSERT INTO roles_permissions(`role`, `permission`) VALUES('admin', 'admin:retrieve');
INSERT INTO roles_permissions(`role`, `permission`) VALUES('admin', 'admin:update');
INSERT INTO roles_permissions(`role`, `permission`) VALUES('admin', 'admin:delete');
INSERT INTO roles_permissions(`role`, `permission`) VALUES('user', 'user:retrieve');
INSERT INTO roles_permissions(`role`, `permission`) VALUES('user', 'user:update');
INSERT INTO roles_permissions(`role`, `permission`) VALUES('user', 'user:delete');
INSERT INTO roles_permissions(`role`, `permission`) VALUES('guest', 'guest:retrieve');

#------------------------
# users
#------------------------
INSERT INTO users(`user_name`, `password`, `password_salt`, `roles`) VALUES('aTom', 'aTom', '123', 'admin,user,guest');
INSERT INTO users(`user_name`, `password`, `password_salt`, `roles`) VALUES('uPitter', 'uPitter', '123', 'user');
INSERT INTO users(`user_name`, `password`, `password_salt`, `roles`) VALUES('gLily', 'gLily', '123', 'guest');





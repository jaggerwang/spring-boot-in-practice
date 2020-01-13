CREATE TABLE `file`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint(20) NOT NULL COMMENT '上传者 ID',
    `region` varchar(20) NOT NULL COMMENT '区域',
    `bucket` varchar(20) NOT NULL COMMENT '桶',
    `path` varchar(100) NOT NULL COMMENT '路径',
    `meta` json NOT NULL COMMENT '元信息',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
);
COMMENT ON TABLE `file` IS '文件';

CREATE TABLE `post`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint(20) NOT NULL COMMENT '发布者 ID',
    `type` varchar(20) NOT NULL COMMENT '类型',
    `text` varchar(100) NOT NULL DEFAULT '' COMMENT '文本内容',
    `image_ids` json DEFAULT NULL COMMENT '图片文件 ID 列表',
    `video_id` bigint(20) DEFAULT NULL COMMENT '视频文件 ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
);
COMMENT ON TABLE `post` IS '动态';

CREATE TABLE `post_like`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `post_id` bigint(20) NOT NULL COMMENT '动态 ID',
    `user_id` bigint(20) NOT NULL COMMENT '点赞用户 ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
);
COMMENT ON TABLE `post_like` IS '动态点赞';

CREATE TABLE `post_stat`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `post_id` bigint(20) NOT NULL COMMENT '动态 ID',
    `like_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '被喜欢次数',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
);
COMMENT ON TABLE `post_stat` IS '动态统计';

CREATE TABLE `role`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name` varchar(20) NOT NULL COMMENT '角色名',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
);
COMMENT ON TABLE `role` IS '角色';

CREATE TABLE `user`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username` varchar(20) NOT NULL COMMENT '用户名',
    `password` varchar(64) NOT NULL COMMENT '已加密的密码',
    `mobile` char(11) DEFAULT NULL COMMENT '手机号',
    `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
    `avatar_id` bigint(20) DEFAULT NULL COMMENT '头像文件 ID',
    `intro` varchar(100) NOT NULL DEFAULT '' COMMENT '自我介绍',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
);
COMMENT ON TABLE `user` IS '用户';

CREATE TABLE `user_follow`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `following_id` bigint(20) NOT NULL COMMENT '被关注用户 ID',
    `follower_id` bigint(20) NOT NULL COMMENT '粉丝用户 ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
);
COMMENT ON TABLE `user_follow` IS '用户关注';

CREATE TABLE `user_role`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint(20) NOT NULL COMMENT '用户 ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色 ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
);
COMMENT ON TABLE `user_role` IS '用户角色';

CREATE TABLE `user_stat`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint(20) NOT NULL COMMENT '用户 ID',
    `post_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '发布动态数',
    `like_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '喜欢动态数',
    `following_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '关注人数',
    `follower_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '粉丝人数',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
);
COMMENT ON TABLE `user_stat` IS '用户统计';


CREATE INDEX `file_idx_user_id` ON `file` (`user_id`);

CREATE INDEX `post_idx_video_id` ON `post` (`video_id`);
CREATE INDEX `post_idx_user_id` ON `post` (`user_id`);

CREATE UNIQUE INDEX `post_like_idx_user_id_post_id` ON `post_like` (`user_id`,`post_id`);
CREATE INDEX `post_like_idx_user_id_created_at` ON `post_like` (`user_id`,`created_at`);
CREATE INDEX `post_like_idx_post_id_created_at` ON `post_like` (`post_id`,`created_at`);

CREATE UNIQUE INDEX `post_stat_idx_post_id` ON `post_stat` (`post_id`);

CREATE UNIQUE INDEX `name_idx_name` ON `role` (`name`);

CREATE UNIQUE INDEX `user_idx_username` ON `user` (`username`);
CREATE UNIQUE INDEX `user_idx_mobile` ON `user` (`mobile`);
CREATE UNIQUE INDEX `user_idx_email` ON `user` (`email`);

CREATE UNIQUE INDEX `user_follow_idx_follower_id_following_id` ON `user_follow` (`follower_id`,`following_id`);
CREATE INDEX `user_follow_idx_following_id_created_at` ON `user_follow` (`following_id`,`created_at`);
CREATE INDEX `user_follow_idx_follower_id_created_at` ON `user_follow` (`follower_id`,`created_at`);

CREATE UNIQUE INDEX `user_role_idx_user_id_role_id` ON `user_role` (`user_id`,`role_id`);
CREATE INDEX `user_role_idx_role_id_created_at` ON `user_role` (`role_id`,`created_at`);
CREATE INDEX `user_role_idx_user_id_created_at` ON `user_role` (`user_id`,`created_at`);

CREATE UNIQUE INDEX `user_stat_idx_user_id` ON `user_stat` (`user_id`);


ALTER TABLE `file` ADD CONSTRAINT `file_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `post` ADD  CONSTRAINT `post_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `post` ADD  CONSTRAINT `post_fk_video_id` FOREIGN KEY (`video_id`) REFERENCES `file` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `post_like` ADD  CONSTRAINT `post_like_fk_post_id` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `post_like` ADD  CONSTRAINT `post_like_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `post_stat` ADD  CONSTRAINT `post_stat_fk_post_id` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `user_follow` ADD  CONSTRAINT `user_follow_fk_following_id` FOREIGN KEY (`following_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `user_follow` ADD  CONSTRAINT `user_follow_fk_follower_id` FOREIGN KEY (`follower_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `user_role` ADD  CONSTRAINT `user_role_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `user_role` ADD  CONSTRAINT `user_role_fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `user_stat` ADD  CONSTRAINT `user_stat_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
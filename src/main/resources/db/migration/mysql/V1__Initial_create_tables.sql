SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint(20) NOT NULL COMMENT '上传者 ID',
    `region` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '区域',
    `bucket` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '桶',
    `path` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '路径',
    `meta` json NOT NULL COMMENT '元信息',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    CONSTRAINT `file_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='文件';

DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint(20) NOT NULL COMMENT '发布者 ID',
    `type` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型',
    `text` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文本内容',
    `image_ids` json DEFAULT NULL COMMENT '图片文件 ID 列表',
    `video_id` bigint(20) DEFAULT NULL COMMENT '视频文件 ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_video_id` (`video_id`),
    KEY `idx_user_id` (`user_id`),
    CONSTRAINT `post_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `post_fk_video_id` FOREIGN KEY (`video_id`) REFERENCES `file` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='动态';

DROP TABLE IF EXISTS `post_like`;
CREATE TABLE `post_like`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `post_id` bigint(20) NOT NULL COMMENT '动态 ID',
    `user_id` bigint(20) NOT NULL COMMENT '点赞用户 ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_user_id_post_id` (`user_id`,`post_id`),
    KEY `idx_user_id_created_at` (`user_id`,`created_at`),
    KEY `idx_post_id_created_at` (`post_id`,`created_at`),
    CONSTRAINT `post_like_fk_post_id` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `post_like_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='动态点赞';

DROP TABLE IF EXISTS `post_stat`;
CREATE TABLE `post_stat`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `post_id` bigint(20) NOT NULL COMMENT '动态 ID',
    `like_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '被喜欢次数',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_post_id` (`post_id`),
    CONSTRAINT `post_stat_fk_post_id` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='动态统计';

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色';

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
    `password` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '已加密的密码',
    `mobile` char(11) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
    `email` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
    `avatar_id` bigint(20) DEFAULT NULL COMMENT '头像文件 ID',
    `intro` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '自我介绍',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_username` (`username`),
    UNIQUE KEY `idx_mobile` (`mobile`),
    UNIQUE KEY `idx_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户';

DROP TABLE IF EXISTS `user_follow`;
CREATE TABLE `user_follow`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `following_id` bigint(20) NOT NULL COMMENT '被关注用户 ID',
    `follower_id` bigint(20) NOT NULL COMMENT '粉丝用户 ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_follower_id_following_id` (`follower_id`,`following_id`),
    KEY `idx_following_id_created_at` (`following_id`,`created_at`),
    KEY `idx_follower_id_created_at` (`follower_id`,`created_at`),
    CONSTRAINT `user_follow_fk_follower_id` FOREIGN KEY (`follower_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `user_follow_fk_following_id` FOREIGN KEY (`following_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户关注';

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint(20) NOT NULL COMMENT '用户 ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色 ID',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_user_id_role_id` (`user_id`,`role_id`) USING BTREE,
    KEY `idx_role_id_created_at` (`role_id`,`created_at`) USING BTREE,
    KEY `idx_user_id_created_at` (`user_id`,`created_at`) USING BTREE,
    CONSTRAINT `user_role_fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `user_role_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户角色';

DROP TABLE IF EXISTS `user_stat`;
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
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_user_id` (`user_id`),
    CONSTRAINT `user_stat_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户统计';

SET FOREIGN_KEY_CHECKS = 1;
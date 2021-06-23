package net.jaggerwang.sbip.adapter.dao.mybatis.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.adapter.dao.mybatis.type.LongListJsonTypeHandler;
import net.jaggerwang.sbip.entity.PostBO;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author Jagger Wang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(autoResultMap = true)
public class Post {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private PostBO.Type type;

    private String text;

    @TableField(typeHandler = LongListJsonTypeHandler.class)
    private List<Long> imageIds;

    private Long videoId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static Post fromBO(PostBO postBO) {
        var text = postBO.getText();
        if (text == null) {
            text = "";
        }

        return Post.builder()
                .id(postBO.getId())
                .userId(postBO.getUserId())
                .type(postBO.getType())
                .text(text)
                .imageIds(postBO.getImageIds())
                .videoId(postBO.getVideoId())
                .createdAt(postBO.getCreatedAt())
                .updatedAt(postBO.getUpdatedAt())
                .build();
    }

    public PostBO toBO() {
        return PostBO.builder()
                .id(id)
                .userId(userId)
                .type(type)
                .text(text)
                .imageIds(imageIds != null ? imageIds : Collections.emptyList())
                .videoId(videoId)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}

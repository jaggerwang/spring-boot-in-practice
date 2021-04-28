package net.jaggerwang.sbip.adapter.api.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.PostBO;

/**
 * @author Jagger Wang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;

    private Long userId;

    private PostBO.Type type;

    private String text;

    @Builder.Default
    private List<Long> imageIds = List.of();

    private Long videoId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UserDTO user;

    @Builder.Default
    private List<FileDTO> images = List.of();

    private FileDTO video;

    private PostStatDTO stat;

    private Boolean liked;

    public static PostDTO fromBO(PostBO postBO) {
        return PostDTO.builder().id(postBO.getId()).userId(postBO.getUserId())
                .type(postBO.getType()).text(postBO.getText())
                .imageIds(postBO.getImageIds()).videoId(postBO.getVideoId())
                .createdAt(postBO.getCreatedAt()).updatedAt(postBO.getUpdatedAt()).build();
    }

    public PostBO toBO() {
        return PostBO.builder().id(id).userId(userId).type(type).text(text).imageIds(imageIds)
                .videoId(videoId).createdAt(createdAt).updatedAt(updatedAt).build();
    }
}

package net.jaggerwang.sbip.adapter.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.PostEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;

    private Long userId;

    private PostEntity.Type type;

    private String text;

    private List<Long> imageIds;

    private Long videoId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UserDTO user;

    private List<FileDTO> images;

    private FileDTO video;

    private PostStatDTO stat;

    private boolean liked;

    public static PostDTO fromEntity(PostEntity postEntity) {
        return PostDTO.builder().id(postEntity.getId()).userId(postEntity.getUserId())
                .type(postEntity.getType()).text(postEntity.getText())
                .imageIds(postEntity.getImageIds()).videoId(postEntity.getVideoId())
                .createdAt(postEntity.getCreatedAt()).updatedAt(postEntity.getUpdatedAt()).build();
    }

    public PostEntity toEntity() {
        return PostEntity.builder().id(id).userId(userId).type(type).text(text).imageIds(imageIds)
                .videoId(videoId).createdAt(createdAt).updatedAt(updatedAt).build();
    }
}

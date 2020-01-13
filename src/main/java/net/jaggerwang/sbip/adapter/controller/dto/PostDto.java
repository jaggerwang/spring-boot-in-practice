package net.jaggerwang.sbip.adapter.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.PostEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;

    private Long userId;

    private PostEntity.Type type;

    private String text;

    @Builder.Default
    private List<Long> imageIds = List.of();

    private Long videoId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UserDto user;

    @Builder.Default
    private List<FileDto> images = List.of();

    private FileDto video;

    private PostStatDto stat;

    private Boolean liked;

    public static PostDto fromEntity(PostEntity postEntity) {
        return PostDto.builder().id(postEntity.getId()).userId(postEntity.getUserId())
                .type(postEntity.getType()).text(postEntity.getText())
                .imageIds(postEntity.getImageIds()).videoId(postEntity.getVideoId())
                .createdAt(postEntity.getCreatedAt()).updatedAt(postEntity.getUpdatedAt()).build();
    }

    public PostEntity toEntity() {
        return PostEntity.builder().id(id).userId(userId).type(type).text(text).imageIds(imageIds)
                .videoId(videoId).createdAt(createdAt).updatedAt(updatedAt).build();
    }
}

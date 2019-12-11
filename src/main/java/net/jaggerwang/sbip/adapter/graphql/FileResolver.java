package net.jaggerwang.sbip.adapter.graphql;

import java.nio.file.Paths;
import java.util.HashMap;
import com.coxautodev.graphql.tools.GraphQLResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.entity.FileEntity;
import net.jaggerwang.sbip.entity.UserEntity;

@Component
public class FileResolver extends BaseResolver implements GraphQLResolver<FileEntity> {
    @Value("${storage.local.url-base}")
    private String urlBase;

    public UserEntity user(FileEntity fileEntity) {
        return userUsecases.info(fileEntity.getUserId());
    }

    public String url(FileEntity fileEntity) {
        if (fileEntity.getRegion() == FileEntity.Region.LOCAL) {
            return urlBase
                    + Paths.get("/", fileEntity.getBucket(), fileEntity.getPath()).toString();
        } else {
            return "";
        }
    }

    public String thumbs(FileEntity fileEntity) {
        if (fileEntity.getMeta().getType().startsWith("image/")) {
            var thumbs = new HashMap<FileEntity.ThumbType, String>();
            var u = url(fileEntity);
            thumbs.put(FileEntity.ThumbType.SMALL,
                    String.format("%s?process=%s", u, "thumb-small"));
            thumbs.put(FileEntity.ThumbType.MIDDLE,
                    String.format("%s?process=%s", u, "thumb-middle"));
            thumbs.put(FileEntity.ThumbType.LARGE,
                    String.format("%s?process=%s", u, "thumb-large"));
            thumbs.put(FileEntity.ThumbType.HUGE, String.format("%s?process=%s", u, "thumb-huge"));

            try {
                return objectMapper.writeValueAsString(thumbs);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }
}

package net.jaggerwang.sbip.adapter.graphql;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import com.coxautodev.graphql.tools.GraphQLResolver;
import net.jaggerwang.sbip.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.entity.FileEntity;

@Component
public class FileResolver extends AbstractResolver implements GraphQLResolver<FileEntity> {
    @Value("${file.base-url}")
    private String urlBase;

    public UserEntity user(FileEntity fileEntity) {
        return userUsecase.info(fileEntity.getUserId()).get();
    }

    public String url(FileEntity fileEntity) {
        if (fileEntity.getRegion() == FileEntity.Region.LOCAL) {
            return urlBase
                    + Paths.get("/", fileEntity.getBucket(), fileEntity.getPath()).toString();
        } else {
            return "";
        }
    }

    public Map<FileEntity.ThumbType, String> thumbs(FileEntity fileEntity) {
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
            return thumbs;
        } else {
            return null;
        }
    }
}

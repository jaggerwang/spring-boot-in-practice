package net.jaggerwang.sbip.adapter.graphql;

import java.nio.file.Paths;
import java.util.HashMap;

import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.entity.FileEntity;

@Component
public class FileDataFetcher extends AbstractDataFetcher {
    @Value("${file.base-url}")
    private String urlBase;

    public DataFetcher user() {
        return env -> {
            FileEntity fileEntity = env.getSource();
            return userUsecase.info(fileEntity.getUserId()).get();
        };
    }

    private String generateUrl(FileEntity fileEntity) {
        if (fileEntity.getRegion() == FileEntity.Region.LOCAL) {
            return urlBase
                    + Paths.get("/", fileEntity.getBucket(), fileEntity.getPath()).toString();
        } else {
            return "";
        }
    }

    public DataFetcher url() {
        return env -> {
            FileEntity fileEntity = env.getSource();
            return generateUrl(fileEntity);
        };
    }

    public DataFetcher thumbs() {
        return env -> {
            FileEntity fileEntity = env.getSource();
            if (fileEntity.getMeta().getType().startsWith("image/")) {
                var thumbs = new HashMap<FileEntity.ThumbType, String>();
                var u = generateUrl(fileEntity);
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
        };
    }
}

package net.jaggerwang.sbip.adapter.graphql.datafetcher.file;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.entity.FileEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class FileThumbsDataFetcher extends AbstractFileDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment env) {
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
    }
}

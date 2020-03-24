package net.jaggerwang.sbip.adapter.graphql.datafetcher.file;

import net.jaggerwang.sbip.adapter.graphql.datafetcher.AbstractDataFetcher;
import net.jaggerwang.sbip.entity.FileEntity;
import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Paths;

abstract  public class AbstractFileDataFetcher extends AbstractDataFetcher {
    @Value("${file.base-url}")
    private String baseUrl;

    protected String generateUrl(FileEntity fileEntity) {
        if (fileEntity.getRegion() == FileEntity.Region.LOCAL) {
            return baseUrl
                    + Paths.get("/", fileEntity.getBucket(), fileEntity.getPath()).toString();
        } else {
            return "";
        }
    }
}

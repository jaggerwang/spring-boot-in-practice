package net.jaggerwang.sbip.adapter.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.jaggerwang.sbip.util.generator.IdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.entity.FileBO.Meta;
import net.jaggerwang.sbip.usecase.port.service.StorageService;

/**
 * @author Jagger Wang
 */
@Component
public class LocalStorageServiceImpl implements StorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String store(String path, byte[] content, Meta meta) throws IOException {
        var saveDir = Paths.get(uploadDir, path);
        if (Files.notExists(saveDir)) {
            Files.createDirectories(saveDir);
        }

        var filename =
                new IdGenerator().objectId() + meta.getName().substring(
                        meta.getName().lastIndexOf('.'));
        Files.write(saveDir.resolve(filename), content);

        return Paths.get(path, filename).toString();
    }
}

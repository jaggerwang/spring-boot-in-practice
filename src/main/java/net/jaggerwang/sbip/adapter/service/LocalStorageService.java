package net.jaggerwang.sbip.adapter.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.entity.FileEntity.Meta;
import net.jaggerwang.sbip.usecase.port.generator.IdGenerator;
import net.jaggerwang.sbip.usecase.port.service.StorageService;

@Component
public class LocalStorageService implements StorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private IdGenerator idGenerator;

    @Override
    public String store(String path, byte[] content, Meta meta) throws IOException {
        var saveDir = Paths.get(uploadDir, path);
        if (Files.notExists(saveDir)) {
            Files.createDirectories(saveDir);
        }

        var filename =
                idGenerator.objectId() + meta.getName().substring(meta.getName().lastIndexOf('.'));
        Files.write(saveDir.resolve(filename), content);

        return Paths.get(path, filename).toString();
    }
}

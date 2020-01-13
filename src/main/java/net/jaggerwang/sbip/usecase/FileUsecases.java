package net.jaggerwang.sbip.usecase;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import net.jaggerwang.sbip.entity.FileEntity;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;
import net.jaggerwang.sbip.usecase.port.repository.FileRepository;
import net.jaggerwang.sbip.usecase.port.service.StorageService;

public class FileUsecases {
    private final FileRepository fileRepository;
    private final StorageService storageService;

    public FileUsecases(FileRepository fileRepository, StorageService storageService) {
        this.fileRepository = fileRepository;
        this.storageService = storageService;
    }

    public FileEntity upload(String path, byte[] content, FileEntity fileEntity) {
        String savedPath;
        try {
            savedPath = storageService.store(path, content, fileEntity.getMeta());
        } catch (IOException e) {
            throw new UsecaseException("存储文件出错");
        }

        var file = FileEntity.builder().userId(fileEntity.getUserId())
                .region(fileEntity.getRegion()).bucket(fileEntity.getBucket()).path(savedPath)
                .meta(fileEntity.getMeta()).build();
        return fileRepository.save(file);
    }

    public Optional<FileEntity> info(Long id) {
        return fileRepository.findById(id);
    }

    public List<FileEntity> infos(List<Long> ids, Boolean keepNull) {
        var fileEntities = fileRepository.findAllById(ids);

        if (!keepNull) {
            fileEntities.removeIf(Objects::isNull);
        }

        return fileEntities;
    }
}

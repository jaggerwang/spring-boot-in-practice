package net.jaggerwang.sbip.usecase.port.repository;

import java.util.List;
import java.util.Optional;

import net.jaggerwang.sbip.entity.FileEntity;

public interface FileRepository {
    FileEntity save(FileEntity fileEntity);

    Optional<FileEntity> findById(Long id);

    List<FileEntity> findAllById(List<Long> ids);
}

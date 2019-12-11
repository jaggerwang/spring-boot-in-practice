package net.jaggerwang.sbip.usecase.port.service;

import java.io.IOException;

import net.jaggerwang.sbip.entity.FileEntity;

public interface StorageService {
    String store(String path, byte[] content, FileEntity.Meta meta) throws IOException;
}

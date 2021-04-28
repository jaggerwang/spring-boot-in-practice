package net.jaggerwang.sbip.usecase.port.service;

import java.io.IOException;

import net.jaggerwang.sbip.entity.FileBO;

/**
 * @author Jagger Wang
 */
public interface StorageService {
    /**
     * 保存内容到文件
     * @param path 存放目录
     * @param content 存储内容
     * @param meta 元信息
     * @return 文件路径
     * @throws IOException
     */
    String store(String path, byte[] content, FileBO.Meta meta) throws IOException;
}

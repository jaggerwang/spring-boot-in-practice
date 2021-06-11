package net.jaggerwang.sbip.usecase.port.dao;

import java.util.List;
import java.util.Optional;

import net.jaggerwang.sbip.entity.FileBO;

/**
 * @author Jagger Wang
 */
public interface FileDao {
    /**
     * 保存文件
     * @param fileBO 要保存的文件
     * @return 已保存的文件
     */
    FileBO save(FileBO fileBO);

    /**
     * 查找指定 ID 的文件
     * @param id 文件 ID
     * @return 文件
     */
    Optional<FileBO> findById(Long id);

    /**
     * 批量查找指定 ID 列表里的文件
     * @param ids 文件 ID 列表
     * @return 文件列表
     */
    List<FileBO> findAllById(List<Long> ids);
}

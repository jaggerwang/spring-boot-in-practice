package net.jaggerwang.sbip.adapter.dao.mybatis.mapper;

import net.jaggerwang.sbip.adapter.dao.mybatis.model.File;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jagger Wang
 */
@Mapper
public interface FileMapper {
    /**
     * 插入文件
     * @param file
     */
    void insert(File file);

    /**
     * 更新文件
     * @param file
     */
    void update(File file);

    /**
     * 删除文件
     * @param id
     */
    void delete(Long id);

    /**
     * 查询文件
     * @param id
     * @return
     */
    File select(Long id);

    /**
     * 根据多个 ID 查询文件
     * @param ids
     * @return
     */
    List<File> selectByIds(List<Long> ids);
}

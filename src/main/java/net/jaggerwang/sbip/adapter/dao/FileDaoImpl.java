package net.jaggerwang.sbip.adapter.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import net.jaggerwang.sbip.adapter.dao.mybatis.mapper.FileMapper;
import net.jaggerwang.sbip.adapter.dao.mybatis.model.File;
import net.jaggerwang.sbip.entity.FileBO;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.usecase.port.dao.FileDao;

/**
 * @author Jagger Wang
 */
@Component
public class FileDaoImpl extends AbstractDao implements FileDao {
    private final FileMapper fileMapper;

    public FileDaoImpl(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Override
    public FileBO save(FileBO fileBO) {
        var file = fileMapper.select(fileBO.getId());
        if (file == null) {
            file  = File.fromBO(fileBO);
            fileMapper.insert(file);
        } else {
            fileMapper.update(file);
        }
        return file.toBO();
    }

    @Override
    public Optional<FileBO> findById(Long id) {
        return Optional.ofNullable(fileMapper.select(id)).map(File::toBO);
    }

    @Override
    public List<FileBO> findAllById(List<Long> ids) {
        var files = fileMapper.selectByIds(ids).stream()
                .collect(Collectors.toMap(File::getId, File::toBO));

        var fileBOs = new FileBO[ids.size()];
        IntStream.range(0, ids.size()).forEach(i -> {
            var id = ids.get(i);
            if (files.containsKey(id)) {
                fileBOs[i] = files.get(id);
            }
        });

        return Arrays.asList(fileBOs);
    }
}

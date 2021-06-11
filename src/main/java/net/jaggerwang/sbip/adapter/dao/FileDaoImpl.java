package net.jaggerwang.sbip.adapter.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import net.jaggerwang.sbip.adapter.dao.jpa.entity.File;
import net.jaggerwang.sbip.entity.FileBO;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.adapter.dao.jpa.repository.FileRepository;
import net.jaggerwang.sbip.usecase.port.dao.FileDao;

/**
 * @author Jagger Wang
 */
@Component
public class FileDaoImpl implements FileDao {
    private FileRepository fileRepository;

    public FileDaoImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public FileBO save(FileBO fileBO) {
        return fileRepository.save(File.fromBO(fileBO)).toBO();
    }

    @Override
    public Optional<FileBO> findById(Long id) {
        return fileRepository.findById(id).map(file -> file.toBO());
    }

    @Override
    public List<FileBO> findAllById(List<Long> ids) {
        var files = fileRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(file -> file.getId(), file -> file.toBO()));

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

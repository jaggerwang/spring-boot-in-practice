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
import net.jaggerwang.sbip.usecase.port.dao.FileDAO;

/**
 * @author Jagger Wang
 */
@Component
public class FileDAOImpl implements FileDAO {
    private FileRepository fileRepository;

    public FileDAOImpl(FileRepository fileRepository) {
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
        var fileDos = fileRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(file -> file.getId(), file -> file.toBO()));

        var fileEntities = new FileBO[ids.size()];
        IntStream.range(0, ids.size()).forEach(i -> {
            var id = ids.get(i);
            if (fileDos.containsKey(id)) {
                fileEntities[i] = fileDos.get(id);
            }
        });

        return Arrays.asList(fileEntities);
    }
}

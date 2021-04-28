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
    private FileRepository fileJpaRepo;

    public FileDAOImpl(FileRepository fileJpaRepo) {
        this.fileJpaRepo = fileJpaRepo;
    }

    @Override
    public FileBO save(FileBO fileBO) {
        return fileJpaRepo.save(File.fromEntity(fileBO)).toEntity();
    }

    @Override
    public Optional<FileBO> findById(Long id) {
        return fileJpaRepo.findById(id).map(file -> file.toEntity());
    }

    @Override
    public List<FileBO> findAllById(List<Long> ids) {
        var fileDos = fileJpaRepo.findAllById(ids).stream()
                .collect(Collectors.toMap(file -> file.getId(), file -> file.toEntity()));

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

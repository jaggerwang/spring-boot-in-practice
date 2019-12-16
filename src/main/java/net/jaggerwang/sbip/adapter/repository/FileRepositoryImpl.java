package net.jaggerwang.sbip.adapter.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.adapter.repository.jpa.FileJpaRepository;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.FileDo;
import net.jaggerwang.sbip.entity.FileEntity;
import net.jaggerwang.sbip.usecase.port.repository.FileRepository;

@Component
public class FileRepositoryImpl extends BaseRepositoryImpl implements FileRepository {
    @Autowired
    private FileJpaRepository fileJpaRepo;

    @Override
    public FileEntity save(FileEntity fileEntity) {
        return fileJpaRepo.save(FileDo.fromEntity(fileEntity)).toEntity();
    }

    @Override
    public Optional<FileEntity> findById(Long id) {
        return fileJpaRepo.findById(id).map(fileDo -> fileDo.toEntity());
    }

    @Override
    public List<FileEntity> findAllById(List<Long> ids) {
        var fileDos = fileJpaRepo.findAllById(ids).stream()
                .collect(Collectors.toMap(fileDo -> fileDo.getId(), fileDo -> fileDo.toEntity()));

        var fileEntities = new FileEntity[ids.size()];
        IntStream.range(0, ids.size()).forEach(i -> {
            var id = ids.get(i);
            if (fileDos.containsKey(id)) {
                fileEntities[i] = fileDos.get(id);
            }
        });

        return Arrays.asList(fileEntities);
    }
}

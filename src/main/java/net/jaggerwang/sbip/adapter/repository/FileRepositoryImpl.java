package net.jaggerwang.sbip.adapter.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.adapter.repository.jpa.FileRepo;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.FileDO;
import net.jaggerwang.sbip.entity.FileEntity;
import net.jaggerwang.sbip.usecase.port.repository.FileRepository;

@Component
public class FileRepositoryImpl implements FileRepository {
    @Autowired
    private FileRepo fileRepo;

    @Override
    public FileEntity save(FileEntity fileEntity) {
        return fileRepo.save(FileDO.fromEntity(fileEntity)).toEntity();
    }

    @Override
    public Optional<FileEntity> findById(Long id) {
        return fileRepo.findById(id).map(fileDO -> fileDO.toEntity());
    }

    @Override
    public List<FileEntity> findAllById(List<Long> ids) {
        var fileDOs = fileRepo.findAllById(ids).stream()
                .collect(Collectors.toMap(fileDO -> fileDO.getId(), fileDO -> fileDO.toEntity()));

        var fileEntities = new FileEntity[ids.size()];
        IntStream.range(0, ids.size()).forEach(i -> {
            var id = ids.get(i);
            if (fileDOs.containsKey(id)) {
                fileEntities[i] = fileDOs.get(id);
            }
        });

        return Arrays.asList(fileEntities);
    }
}

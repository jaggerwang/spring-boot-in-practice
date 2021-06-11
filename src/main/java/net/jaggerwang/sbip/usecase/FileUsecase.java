package net.jaggerwang.sbip.usecase;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import net.jaggerwang.sbip.entity.FileBO;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;
import net.jaggerwang.sbip.usecase.port.dao.FileDao;
import net.jaggerwang.sbip.usecase.port.service.StorageService;
import org.springframework.stereotype.Component;

/**
 * @author Jagger Wang
 */
@Component
public class FileUsecase {
    private final FileDao fileDAO;
    private final StorageService storageService;

    public FileUsecase(FileDao fileDAO, StorageService storageService) {
        this.fileDAO = fileDAO;
        this.storageService = storageService;
    }

    public FileBO upload(String path, byte[] content, FileBO fileBO) {
        String savedPath;
        try {
            savedPath = storageService.store(path, content, fileBO.getMeta());
        } catch (IOException e) {
            throw new UsecaseException("存储文件出错");
        }

        var file = FileBO.builder().userId(fileBO.getUserId())
                .region(fileBO.getRegion()).bucket(fileBO.getBucket()).path(savedPath)
                .meta(fileBO.getMeta()).build();
        return fileDAO.save(file);
    }

    public Optional<FileBO> info(Long id) {
        return fileDAO.findById(id);
    }

    public List<FileBO> infos(List<Long> ids, Boolean keepNull) {
        var fileBOs = fileDAO.findAllById(ids);

        if (!keepNull) {
            fileBOs.removeIf(Objects::isNull);
        }

        return fileBOs;
    }
}

package net.jaggerwang.sbip.adapter.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.jaggerwang.sbip.entity.FileBO;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.jaggerwang.sbip.adapter.api.controller.dto.RootDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jagger Wang
 */
@RestController
@RequestMapping("/file")
@Api(tags = "File Apis")
public class FileController extends AbstractController {
    @PostMapping("/upload")
    @ApiOperation("Upload file")
    public RootDTO upload(@RequestParam(defaultValue = "LOCAL") FileBO.Region region,
                          @RequestParam(defaultValue = "") String bucket,
                          @RequestParam(defaultValue = "") String path,
                          @RequestParam("file") List<MultipartFile> files) throws IOException {
        var fileBOs = new ArrayList<FileBO>();
        for (var file : files) {
            var content = file.getBytes();

            var meta = FileBO.Meta.builder()
                    .name(file.getOriginalFilename())
                    .size(file.getSize())
                    .type(file.getContentType())
                    .build();
            var fileBO = FileBO.builder()
                    .userId(loggedUserId())
                    .region(region)
                    .bucket(bucket)
                    .meta(meta)
                    .build();

            fileBO = fileUsecase.upload(path, content, fileBO);

            fileBOs.add(fileBO);
        }

        return new RootDTO().addDataEntry("files",
                fileBOs.stream().map(this::fullFileDto).collect(Collectors.toList()));
    }

    @GetMapping("/info")
    @ApiOperation("Get file info")
    public RootDTO info(@RequestParam Long id) {
        var fileBO = fileUsecase.info(id);
        if (fileBO.isEmpty()) {
            throw new NotFoundException("文件未找到");
        }

        return new RootDTO().addDataEntry("file", fullFileDto(fileBO.get()));
    }
}

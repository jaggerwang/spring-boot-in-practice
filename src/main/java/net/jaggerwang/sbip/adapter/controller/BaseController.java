package net.jaggerwang.sbip.adapter.controller;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import net.jaggerwang.sbip.adapter.controller.dto.FileDto;
import net.jaggerwang.sbip.adapter.controller.dto.PostDto;
import net.jaggerwang.sbip.adapter.controller.dto.PostStatDto;
import net.jaggerwang.sbip.adapter.controller.dto.UserDto;
import net.jaggerwang.sbip.adapter.controller.dto.UserStatDto;
import net.jaggerwang.sbip.api.security.LoggedUser;
import net.jaggerwang.sbip.usecase.AuthorityUsecases;
import net.jaggerwang.sbip.usecase.FileUsecases;
import net.jaggerwang.sbip.usecase.MetricUsecases;
import net.jaggerwang.sbip.usecase.PostUsecases;
import net.jaggerwang.sbip.usecase.StatUsecases;
import net.jaggerwang.sbip.usecase.UserUsecases;
import net.jaggerwang.sbip.entity.FileEntity;
import net.jaggerwang.sbip.entity.PostEntity;
import net.jaggerwang.sbip.entity.PostStatEntity;
import net.jaggerwang.sbip.entity.UserEntity;
import net.jaggerwang.sbip.entity.UserStatEntity;

abstract public class BaseController {
    @Value("${storage.local.url-base}")
    protected String urlBase;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected AuthenticationManager authManager;

    @Autowired
    protected AuthorityUsecases authorityUsecases;

    @Autowired
    protected FileUsecases fileUsecases;

    @Autowired
    protected MetricUsecases metricUsecases;

    @Autowired
    protected PostUsecases postUsecases;

    @Autowired
    protected StatUsecases statUsecases;

    @Autowired
    protected UserUsecases userUsecases;

    protected void loginUser(String username, String password) {
        var auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        var sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
    }

    protected void logoutUser() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    protected Long loggedUserId() {
        var loggedFile = (LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loggedFile.getId();
    }

    protected UserDto fullUserDto(UserEntity userEntity) {
        var userDto = UserDto.fromEntity(userEntity);

        if (userDto.getAvatarId() != null) {
            userDto.setAvatar(fullFileDto(fileUsecases.info(userDto.getAvatarId())));
        }

        userDto.setStat(UserStatDto.fromEntity(statUsecases.userStatInfoByUserId(userDto.getId())));

        if (loggedUserId() != null) {
            userDto.setFollowing(userUsecases.isFollowing(loggedUserId(), userDto.getId()));
        }

        return userDto;
    }

    protected PostDto fullPostDto(PostEntity postEntity) {
        var postDto = PostDto.fromEntity(postEntity);

        postDto.setUser(fullUserDto(userUsecases.info(postDto.getUserId())));

        if (postDto.getImageIds() != null && postDto.getImageIds().size() > 0) {
            postDto.setImages(fileUsecases.infos(postDto.getImageIds(), false).stream()
                    .map(fileEntity -> fullFileDto(fileEntity)).collect(Collectors.toList()));
        }

        if (postDto.getVideoId() != null) {
            postDto.setVideo(fullFileDto(fileUsecases.info(postDto.getVideoId())));
        }

        postDto.setStat(PostStatDto.fromEntity(statUsecases.postStatInfoByPostId(postDto.getId())));

        if (loggedUserId() != null) {
            postDto.setLiked(postUsecases.isLiked(loggedUserId(), postDto.getId()));
        }

        return postDto;
    }

    protected FileDto fullFileDto(FileEntity fileEntity) {
        var fileDto = FileDto.fromEntity(fileEntity);

        var url = "";
        if (fileDto.getRegion() == FileEntity.Region.LOCAL) {
            url = urlBase + Paths.get("/", fileDto.getBucket(), fileDto.getPath()).toString();
        }
        fileDto.setUrl(url);

        if (fileDto.getMeta().getType().startsWith("image/")) {
            var thumbs = new HashMap<FileEntity.ThumbType, String>();
            thumbs.put(FileEntity.ThumbType.SMALL, String.format("%s?process=%s", url, "thumb-small"));
            thumbs.put(FileEntity.ThumbType.MIDDLE, String.format("%s?process=%s", url, "thumb-middle"));
            thumbs.put(FileEntity.ThumbType.LARGE, String.format("%s?process=%s", url, "thumb-large"));
            thumbs.put(FileEntity.ThumbType.HUGE, String.format("%s?process=%s", url, "thumb-huge"));
            fileDto.setThumbs(thumbs);
        }

        return fileDto;
    }

    protected UserStatDto fullUserStatDto(UserStatEntity userStatEntity) {
        var userStatDto = UserStatDto.fromEntity(userStatEntity);

        userStatDto.setUser(fullUserDto(userUsecases.info(userStatDto.getUserId())));

        return userStatDto;
    }

    protected PostStatDto fullPostStatDto(PostStatEntity postStatEntity) {
        var postStatDto = PostStatDto.fromEntity(postStatEntity);

        postStatDto.setPost(PostDto.fromEntity(postUsecases.info(postStatDto.getPostId())));

        return postStatDto;
    }
}

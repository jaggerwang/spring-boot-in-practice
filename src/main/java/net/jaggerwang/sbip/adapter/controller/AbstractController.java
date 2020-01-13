package net.jaggerwang.sbip.adapter.controller;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.jaggerwang.sbip.adapter.controller.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import net.jaggerwang.sbip.api.security.LoggedUser;
import net.jaggerwang.sbip.usecase.AuthorityUsecases;
import net.jaggerwang.sbip.usecase.FileUsecases;
import net.jaggerwang.sbip.usecase.MetricUsecases;
import net.jaggerwang.sbip.usecase.PostUsecases;
import net.jaggerwang.sbip.usecase.StatUsecases;
import net.jaggerwang.sbip.usecase.UserUsecases;
import net.jaggerwang.sbip.entity.FileEntity;
import net.jaggerwang.sbip.entity.PostEntity;
import net.jaggerwang.sbip.entity.UserEntity;

abstract public class AbstractController {
    @Value("${file.base-url}")
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
        var auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        var sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
    }

    protected void logoutUser() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    protected Long loggedUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken || !auth.isAuthenticated()) {
            return null;
        }

        var loggedUser = (LoggedUser) auth.getPrincipal();
        return loggedUser.getId();
    }

    protected UserDto fullUserDto(UserEntity userEntity) {
        var userDto = UserDto.fromEntity(userEntity);

        if (userDto.getAvatarId() != null) {
            var avatar = fileUsecases.info(userDto.getAvatarId());
            avatar.ifPresent(fileEntity -> userDto.setAvatar(fullFileDto(fileEntity)));
        }

        userDto.setStat(UserStatDto.fromEntity(statUsecases.userStatInfoByUserId(userDto.getId())));

        if (loggedUserId() != null) {
            userDto.setFollowing(userUsecases.isFollowing(loggedUserId(), userDto.getId()));
        }

        return userDto;
    }

    protected PostDto fullPostDto(PostEntity postEntity) {
        var postDto = PostDto.fromEntity(postEntity);

        var user = userUsecases.info(postDto.getUserId());
        user.ifPresent(userEntity -> postDto.setUser(fullUserDto(userEntity)));

        if (postDto.getImageIds().size() > 0) {
            postDto.setImages(fileUsecases.infos(postDto.getImageIds(), false).stream()
                    .map(this::fullFileDto).collect(Collectors.toList()));
        }

        if (postDto.getVideoId() != null) {
            var video = fileUsecases.info(postDto.getVideoId());
            video.ifPresent(fileEntity -> postDto.setVideo(fullFileDto(fileEntity)));
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
            thumbs.put(FileEntity.ThumbType.SMALL,
                    String.format("%s?process=%s", url, "thumb-small"));
            thumbs.put(FileEntity.ThumbType.MIDDLE,
                    String.format("%s?process=%s", url, "thumb-middle"));
            thumbs.put(FileEntity.ThumbType.LARGE,
                    String.format("%s?process=%s", url, "thumb-large"));
            thumbs.put(FileEntity.ThumbType.HUGE,
                    String.format("%s?process=%s", url, "thumb-huge"));
            fileDto.setThumbs(thumbs);
        }

        return fileDto;
    }
}

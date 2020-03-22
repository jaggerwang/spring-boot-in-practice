package net.jaggerwang.sbip.adapter.controller;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;
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
import net.jaggerwang.sbip.usecase.FileUsecase;
import net.jaggerwang.sbip.usecase.MetricUsecase;
import net.jaggerwang.sbip.usecase.PostUsecase;
import net.jaggerwang.sbip.usecase.StatUsecase;
import net.jaggerwang.sbip.usecase.UserUsecase;
import net.jaggerwang.sbip.entity.FileEntity;
import net.jaggerwang.sbip.entity.PostEntity;
import net.jaggerwang.sbip.entity.UserEntity;

abstract public class AbstractController {
    @Value("${file.base-url}")
    protected String urlBase;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    protected FileUsecase fileUsecase;

    @Autowired
    protected MetricUsecase metricUsecase;

    @Autowired
    protected PostUsecase postUsecase;

    @Autowired
    protected StatUsecase statUsecase;

    @Autowired
    protected UserUsecase userUsecase;

    protected LoggedUser loginUser(String username, String password) {
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username, password));
        var securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);
        return (LoggedUser) auth.getPrincipal();
    }

    protected Optional<LoggedUser> logoutUser() {
        var loggedUser = loggedUser();
        SecurityContextHolder.getContext().setAuthentication(null);
        return loggedUser;
    }

    protected Optional<LoggedUser> loggedUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken || !auth.isAuthenticated()) {
            return Optional.empty();
        }
        return Optional.of((LoggedUser) auth.getPrincipal());
    }

    protected Long loggedUserId() {
        var loggedUser = loggedUser();
        return loggedUser.isPresent() ? loggedUser.get().getId() : null;
    }

    protected UserDto fullUserDto(UserEntity userEntity) {
        var userDto = UserDto.fromEntity(userEntity);

        if (userDto.getAvatarId() != null) {
            var avatar = fileUsecase.info(userDto.getAvatarId());
            avatar.ifPresent(fileEntity -> userDto.setAvatar(fullFileDto(fileEntity)));
        }

        userDto.setStat(UserStatDto.fromEntity(statUsecase.userStatInfoByUserId(userDto.getId())));

        if (loggedUserId() != null) {
            userDto.setFollowing(userUsecase.isFollowing(loggedUserId(), userDto.getId()));
        }

        return userDto;
    }

    protected PostDto fullPostDto(PostEntity postEntity) {
        var postDto = PostDto.fromEntity(postEntity);

        var user = userUsecase.info(postDto.getUserId());
        user.ifPresent(userEntity -> postDto.setUser(fullUserDto(userEntity)));

        if (postDto.getImageIds().size() > 0) {
            postDto.setImages(fileUsecase.infos(postDto.getImageIds(), false).stream()
                    .map(this::fullFileDto).collect(Collectors.toList()));
        }

        if (postDto.getVideoId() != null) {
            var video = fileUsecase.info(postDto.getVideoId());
            video.ifPresent(fileEntity -> postDto.setVideo(fullFileDto(fileEntity)));
        }

        postDto.setStat(PostStatDto.fromEntity(statUsecase.postStatInfoByPostId(postDto.getId())));

        if (loggedUserId() != null) {
            postDto.setLiked(postUsecase.isLiked(loggedUserId(), postDto.getId()));
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

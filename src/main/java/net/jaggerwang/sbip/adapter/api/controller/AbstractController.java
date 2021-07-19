package net.jaggerwang.sbip.adapter.api.controller;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.jaggerwang.sbip.adapter.api.controller.dto.*;
import net.jaggerwang.sbip.entity.FileBO;
import net.jaggerwang.sbip.entity.PostBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import net.jaggerwang.sbip.adapter.api.security.LoggedUser;
import net.jaggerwang.sbip.usecase.FileUsecase;
import net.jaggerwang.sbip.usecase.MetricUsecase;
import net.jaggerwang.sbip.usecase.PostUsecase;
import net.jaggerwang.sbip.usecase.StatUsecase;
import net.jaggerwang.sbip.usecase.UserUsecase;
import net.jaggerwang.sbip.entity.UserBO;

/**
 * @author Jagger Wang
 */
abstract public class AbstractController {
    @Value("${file.base-url}")
    protected String baseUrl;

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

    protected LoggedUser logoutUser() {
        var loggedUser = loggedUser();
        SecurityContextHolder.getContext().setAuthentication(null);
        return loggedUser;
    }

    protected LoggedUser loggedUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken ||
                !auth.isAuthenticated()) {
            return null;
        }
        return (LoggedUser) auth.getPrincipal();
    }

    protected Long loggedUserId() {
        var loggedUser = loggedUser();
        return loggedUser != null ? loggedUser.getId() : null;
    }

    protected UserDTO fullUserDto(UserBO userBO) {
        var userDto = UserDTO.fromBO(userBO);

        if (userDto.getAvatarId() != null) {
            var avatar = fileUsecase.info(userDto.getAvatarId());
            avatar.ifPresent(file -> userDto.setAvatar(fullFileDto(file)));
        }

        userDto.setStat(UserStatDTO.fromBO(statUsecase.userStatInfoByUserId(userDto.getId())));

        if (loggedUserId() != null) {
            userDto.setFollowing(userUsecase.isFollowing(loggedUserId(), userDto.getId()));
        }

        return userDto;
    }

    protected PostDTO fullPostDto(PostBO postBO) {
        var postDto = PostDTO.fromBO(postBO);

        var userBO = userUsecase.info(postDto.getUserId());
        userBO.ifPresent(user -> postDto.setUser(fullUserDto(user)));

        if (postDto.getImageIds().size() > 0) {
            postDto.setImages(fileUsecase.infos(postDto.getImageIds(), false).stream()
                    .map(this::fullFileDto).collect(Collectors.toList()));
        }

        if (postDto.getVideoId() != null) {
            var video = fileUsecase.info(postDto.getVideoId());
            video.ifPresent(file -> postDto.setVideo(fullFileDto(file)));
        }

        postDto.setStat(PostStatDTO.fromBO(statUsecase.postStatInfoByPostId(postDto.getId())));

        if (loggedUserId() != null) {
            postDto.setLiked(postUsecase.isLiked(loggedUserId(), postDto.getId()));
        }

        return postDto;
    }

    protected FileDTO fullFileDto(FileBO fileBO) {
        var fileDto = FileDTO.fromBO(fileBO);

        var url = "";
        if (fileDto.getRegion() == FileBO.Region.LOCAL) {
            url = baseUrl + Paths.get("/", fileDto.getBucket(), fileDto.getPath()).toString();
        }
        fileDto.setUrl(url);

        if (fileDto.getMeta().getType().startsWith("image/")) {
            var thumbs = new HashMap<FileBO.ThumbType, String>(8);
            thumbs.put(FileBO.ThumbType.SMALL,
                    String.format("%s?process=%s", url, "thumb-small"));
            thumbs.put(FileBO.ThumbType.MIDDLE,
                    String.format("%s?process=%s", url, "thumb-middle"));
            thumbs.put(FileBO.ThumbType.LARGE,
                    String.format("%s?process=%s", url, "thumb-large"));
            thumbs.put(FileBO.ThumbType.HUGE,
                    String.format("%s?process=%s", url, "thumb-huge"));
            fileDto.setThumbs(thumbs);
        }

        return fileDto;
    }
}

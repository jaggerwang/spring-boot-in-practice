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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
import net.jaggerwang.sbip.adapter.controller.dto.FileDTO;
import net.jaggerwang.sbip.adapter.controller.dto.PostDTO;
import net.jaggerwang.sbip.adapter.controller.dto.PostStatDTO;
import net.jaggerwang.sbip.adapter.controller.dto.UserDTO;
import net.jaggerwang.sbip.adapter.controller.dto.UserStatDTO;
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

    protected void login(String username, String password) {
        var auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        var sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        var requestAttrs =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        requestAttrs.getRequest().getSession().setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
    }

    protected Long loggedUserId() {
        var loggedFile =
                (LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loggedFile.getId();
    }

    protected UserDTO fullUserDTO(UserEntity userEntity) {
        var userDTO = UserDTO.fromEntity(userEntity);

        if (userDTO.getAvatarId() != null) {
            userDTO.setAvatar(fullFileDTO(fileUsecases.info(userDTO.getAvatarId())));
        }

        userDTO.setStat(UserStatDTO.fromEntity(statUsecases.userStatInfoByUserId(userDTO.getId())));

        if (loggedUserId() != null) {
            userDTO.setFollowing(userUsecases.isFollowing(loggedUserId(), userDTO.getId()));
        }

        return userDTO;
    }

    protected PostDTO fullPostDTO(PostEntity postEntity) {
        var postDTO = PostDTO.fromEntity(postEntity);

        postDTO.setUser(fullUserDTO(userUsecases.info(postDTO.getUserId())));

        if (postDTO.getImageIds() != null && postDTO.getImageIds().size() > 0) {
            postDTO.setImages(fileUsecases.infos(postDTO.getImageIds(), false).stream()
                    .map(fileEntity -> fullFileDTO(fileEntity)).collect(Collectors.toList()));
        }

        if (postDTO.getVideoId() != null) {
            postDTO.setVideo(fullFileDTO(fileUsecases.info(postDTO.getVideoId())));
        }

        postDTO.setStat(PostStatDTO.fromEntity(statUsecases.postStatInfoByPostId(postDTO.getId())));

        if (loggedUserId() != null) {
            postDTO.setLiked(postUsecases.isLiked(loggedUserId(), postDTO.getId()));
        }

        return postDTO;
    }

    protected FileDTO fullFileDTO(FileEntity fileEntity) {
        var fileDTO = FileDTO.fromEntity(fileEntity);

        var url = "";
        if (fileDTO.getRegion() == FileEntity.Region.LOCAL) {
            url = urlBase + Paths.get("/", fileDTO.getBucket(), fileDTO.getPath()).toString();
        }
        fileDTO.setUrl(url);

        if (fileDTO.getMeta().getType().startsWith("image/")) {
            var thumbs = new HashMap<FileEntity.ThumbType, String>();
            thumbs.put(FileEntity.ThumbType.SMALL,
                    String.format("%s?process=%s", url, "thumb-small"));
            thumbs.put(FileEntity.ThumbType.MIDDLE,
                    String.format("%s?process=%s", url, "thumb-middle"));
            thumbs.put(FileEntity.ThumbType.LARGE,
                    String.format("%s?process=%s", url, "thumb-large"));
            thumbs.put(FileEntity.ThumbType.HUGE,
                    String.format("%s?process=%s", url, "thumb-huge"));
            fileDTO.setThumbs(thumbs);
        }

        return fileDTO;
    }

    protected UserStatDTO fullUserStatDTO(UserStatEntity userStatEntity) {
        var userStatDTO = UserStatDTO.fromEntity(userStatEntity);

        userStatDTO.setUser(fullUserDTO(userUsecases.info(userStatDTO.getUserId())));

        return userStatDTO;
    }

    protected PostStatDTO fullPostStatDTO(PostStatEntity postStatEntity) {
        var postStatDTO = PostStatDTO.fromEntity(postStatEntity);

        postStatDTO.setPost(PostDTO.fromEntity(postUsecases.info(postStatDTO.getPostId())));

        return postStatDTO;
    }
}

package net.jaggerwang.sbip.adapter.api.controller;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.jaggerwang.sbip.adapter.api.controller.dto.RootDTO;
import net.jaggerwang.sbip.adapter.api.controller.dto.PostDTO;
import net.jaggerwang.sbip.entity.PostBO;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;

/**
 * @author Jagger Wang
 */
@RestController
@RequestMapping("/post")
@Api(tags = "Post Apis")
public class PostController extends AbstractController {
    @PostMapping("/publish")
    @ApiOperation("Publish post")
    public RootDTO publish(@RequestBody PostBO postInput) {
        postInput.setUserId(loggedUserId());
        var postBO = postUsecase.publish(postInput);

        return new RootDTO().addDataEntry("post", PostDTO.fromBO(postBO));
    }

    @PostMapping("/delete")
    @ApiOperation("Delete post")
    public RootDTO delete(@RequestBody Map<String, Object> input) {
        var id = objectMapper.convertValue(input.get("id"), Long.class);

        var postBO = postUsecase.info(id);
        if (postBO.isEmpty()) {
            throw new NotFoundException("动态未找到");
        }
        if (!Objects.equals(postBO.get().getUserId(), loggedUserId())) {
            throw new UsecaseException("无权删除");
        }

        postUsecase.delete(id);

        return new RootDTO();
    }

    @GetMapping("/info")
    @ApiOperation("Get post info")
    public RootDTO info(@RequestParam Long id) {
        var postBO = postUsecase.info(id);
        if (postBO.isEmpty()) {
            throw new NotFoundException("动态未找到");
        }

        return new RootDTO().addDataEntry("post", fullPostDto(postBO.get()));
    }

    @GetMapping("/published")
    @ApiOperation("Posts published by some user")
    public RootDTO published(@RequestParam(required = false) Long userId,
                             @RequestParam(defaultValue = "10") Long limit,
                             @RequestParam(defaultValue = "0") Long offset) {
        var postBOs = postUsecase.published(userId, limit, offset);

        return new RootDTO().addDataEntry("posts",
                postBOs.stream().map(this::fullPostDto).collect(Collectors.toList()));
    }

    @PostMapping("/like")
    @ApiOperation("Like post")
    public RootDTO like(@RequestBody Map<String, Object> input) {
        var postId = objectMapper.convertValue(input.get("postId"), Long.class);

        postUsecase.like(loggedUserId(), postId);

        return new RootDTO();
    }

    @PostMapping("/unlike")
    @ApiOperation("Unlike post")
    public RootDTO unlike(@RequestBody Map<String, Object> input) {
        var postId = objectMapper.convertValue(input.get("postId"), Long.class);

        postUsecase.unlike(loggedUserId(), postId);

        return new RootDTO();
    }

    @GetMapping("/liked")
    @ApiOperation("Posts liked by some user")
    public RootDTO liked(@RequestParam(required = false) Long userId,
                         @RequestParam(defaultValue = "10") Long limit,
                         @RequestParam(defaultValue = "0") Long offset) {
        var postBOs = postUsecase.liked(userId, limit, offset);

        return new RootDTO().addDataEntry("posts",
                postBOs.stream().map(this::fullPostDto).collect(Collectors.toList()));
    }

    @GetMapping("/following")
    @ApiOperation("Posts published by users which are followed by current user")
    public RootDTO following(@RequestParam(defaultValue = "10") Long limit,
                             @RequestParam(required = false) Long beforeId,
                             @RequestParam(required = false) Long afterId) {
        var postBOs = postUsecase.following(loggedUserId(), limit, beforeId, afterId);

        return new RootDTO().addDataEntry("posts",
                postBOs.stream().map(this::fullPostDto).collect(Collectors.toList()));
    }
}

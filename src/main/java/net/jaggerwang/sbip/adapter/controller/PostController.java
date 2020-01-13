package net.jaggerwang.sbip.adapter.controller;

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
import net.jaggerwang.sbip.adapter.controller.dto.RootDto;
import net.jaggerwang.sbip.adapter.controller.dto.PostDto;
import net.jaggerwang.sbip.entity.PostEntity;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;

@RestController
@RequestMapping("/post")
@Api(tags = "Post Apis")
public class PostController extends AbstractController {
    @PostMapping("/publish")
    @ApiOperation("Publish post")
    public RootDto publish(@RequestBody PostEntity postInput) {
        postInput.setUserId(loggedUserId());
        var postEntity = postUsecases.publish(postInput);

        return new RootDto().addDataEntry("post", PostDto.fromEntity(postEntity));
    }

    @PostMapping("/delete")
    @ApiOperation("Delete post")
    public RootDto delete(@RequestBody Map<String, Object> input) {
        var id = objectMapper.convertValue(input.get("id"), Long.class);

        var postEntity = postUsecases.info(id);
        if (postEntity.isEmpty()) {
            throw new NotFoundException("动态未找到");
        }
        if (!Objects.equals(postEntity.get().getUserId(), loggedUserId())) {
            throw new UsecaseException("无权删除");
        }

        postUsecases.delete(id);

        return new RootDto();
    }

    @GetMapping("/info")
    @ApiOperation("Get post info")
    public RootDto info(@RequestParam Long id) {
        var postEntity = postUsecases.info(id);
        if (postEntity.isEmpty()) {
            throw new NotFoundException("动态未找到");
        }

        return new RootDto().addDataEntry("post", fullPostDto(postEntity.get()));
    }

    @GetMapping("/published")
    @ApiOperation("Posts published by some user")
    public RootDto published(@RequestParam(required = false) Long userId,
                             @RequestParam(defaultValue = "10") Long limit,
                             @RequestParam(defaultValue = "0") Long offset) {
        var postEntities = postUsecases.published(userId, limit, offset);

        return new RootDto().addDataEntry("posts",
                postEntities.stream().map(this::fullPostDto).collect(Collectors.toList()));
    }

    @PostMapping("/like")
    @ApiOperation("Like post")
    public RootDto like(@RequestBody Map<String, Object> input) {
        var postId = objectMapper.convertValue(input.get("postId"), Long.class);

        postUsecases.like(loggedUserId(), postId);

        return new RootDto();
    }

    @PostMapping("/unlike")
    @ApiOperation("Unlike post")
    public RootDto unlike(@RequestBody Map<String, Object> input) {
        var postId = objectMapper.convertValue(input.get("postId"), Long.class);

        postUsecases.unlike(loggedUserId(), postId);

        return new RootDto();
    }

    @GetMapping("/liked")
    @ApiOperation("Posts liked by some user")
    public RootDto liked(@RequestParam(required = false) Long userId,
                         @RequestParam(defaultValue = "10") Long limit,
                         @RequestParam(defaultValue = "0") Long offset) {
        var postEntities = postUsecases.liked(userId, limit, offset);

        return new RootDto().addDataEntry("posts",
                postEntities.stream().map(this::fullPostDto).collect(Collectors.toList()));
    }

    @GetMapping("/following")
    @ApiOperation("Posts published by users which are followed by current user")
    public RootDto following(@RequestParam(defaultValue = "10") Long limit,
                             @RequestParam(required = false) Long beforeId,
                             @RequestParam(required = false) Long afterId) {
        var postEntities = postUsecases.following(loggedUserId(), limit, beforeId, afterId);

        return new RootDto().addDataEntry("posts",
                postEntities.stream().map(this::fullPostDto).collect(Collectors.toList()));
    }
}

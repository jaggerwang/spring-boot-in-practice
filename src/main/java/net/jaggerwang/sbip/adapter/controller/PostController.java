package net.jaggerwang.sbip.adapter.controller;

import java.util.Map;
import java.util.stream.Collectors;
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
public class PostController extends AbstractController {
    @PostMapping("/publish")
    public RootDto publish(@RequestBody PostEntity postInput) {
        postInput.setUserId(loggedUserId());
        var postEntity = postUsecases.publish(postInput);

        return new RootDto().addDataEntry("post", PostDto.fromEntity(postEntity));
    }

    @PostMapping("/delete")
    public RootDto delete(@RequestBody Map<String, ?> input) {
        var id = objectMapper.convertValue(input.get("id"), Long.class);

        var postEntity = postUsecases.info(id);
        if (!loggedUserId().equals(postEntity.getUserId())) {
            throw new UsecaseException("无权删除");
        }

        postUsecases.delete(id);

        return new RootDto();
    }

    @GetMapping("/info")
    public RootDto info(@RequestParam Long id) {
        var postEntity = postUsecases.info(id);

        return new RootDto().addDataEntry("post", fullPostDto(postEntity));
    }

    @GetMapping("/published")
    public RootDto published(@RequestParam(required = false) Long userId,
            @RequestParam(required = false, defaultValue = "10") Long limit,
            @RequestParam(required = false) Long offset) {
        var postEntities = postUsecases.published(userId, limit, offset);

        return new RootDto().addDataEntry("posts",
                postEntities.stream().map(postEntity -> fullPostDto(postEntity)).collect(Collectors.toList()));
    }

    @PostMapping("/like")
    public RootDto like(@RequestBody Map<String, ?> input) {
        var postId = objectMapper.convertValue(input.get("postId"), Long.class);

        postUsecases.like(loggedUserId(), postId);

        return new RootDto();
    }

    @PostMapping("/unlike")
    public RootDto unlike(@RequestBody Map<String, ?> input) {
        var postId = objectMapper.convertValue(input.get("postId"), Long.class);

        postUsecases.unlike(loggedUserId(), postId);

        return new RootDto();
    }

    @GetMapping("/liked")
    public RootDto liked(@RequestParam(required = false) Long userId,
            @RequestParam(required = false, defaultValue = "10") Long limit,
            @RequestParam(required = false) Long offset) {
        var postEntities = postUsecases.liked(userId, limit, offset);

        return new RootDto().addDataEntry("posts",
                postEntities.stream().map(postEntity -> fullPostDto(postEntity)).collect(Collectors.toList()));
    }

    @GetMapping("/following")
    public RootDto following(@RequestParam(required = false) Long limit,
            @RequestParam(required = false, defaultValue = "10") Long beforeId,
            @RequestParam(required = false) Long afterId) {
        var postEntities = postUsecases.following(loggedUserId(), limit, beforeId, afterId);

        return new RootDto().addDataEntry("posts",
                postEntities.stream().map(postEntity -> fullPostDto(postEntity)).collect(Collectors.toList()));
    }
}

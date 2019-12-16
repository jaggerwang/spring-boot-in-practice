package net.jaggerwang.sbip.adapter.controller;

import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.jaggerwang.sbip.adapter.controller.dto.JsonDto;
import net.jaggerwang.sbip.adapter.controller.dto.UserDto;
import net.jaggerwang.sbip.entity.UserEntity;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @PostMapping("/register")
    public JsonDto register(@RequestBody UserDto userDto) {
        var userEntity = userUsecases.register(userDto.toEntity());

        loginUser(userDto.getUsername(), userDto.getPassword());

        metricUsecases.increment("registerCount", 1L);

        return new JsonDto().addDataEntry("user", UserDto.fromEntity(userEntity));
    }

    @PostMapping("/login")
    public JsonDto login(@RequestBody UserDto userDto) {
        String username = null;
        UserEntity userEntity = null;
        if (userDto.getUsername() != null) {
            username = userDto.getUsername();
            userEntity = userUsecases.infoByUsername(username);
        } else if (userDto.getMobile() != null) {
            username = userDto.getMobile();
            userEntity = userUsecases.infoByMobile(username);
        } else if (userDto.getEmail() != null) {
            username = userDto.getEmail();
            userEntity = userUsecases.infoByEmail(username);
        } else {
            username = null;
            userEntity = null;
        }
        var password = userDto.getPassword();
        if (username == null || password == null) {
            throw new UsecaseException("用户名或密码不能为空");
        }

        loginUser(username, password);

        return new JsonDto().addDataEntry("user", UserDto.fromEntity(userEntity));
    }

    @GetMapping("/logged")
    public JsonDto logged() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken || !auth.isAuthenticated()) {
            return new JsonDto().addDataEntry("user", null);
        }

        return new JsonDto().addDataEntry("user", fullUserDto(userUsecases.info(loggedUserId())));
    }

    @GetMapping("/logout")
    public JsonDto logout() {
        var userEntity = userUsecases.info(loggedUserId());

        logoutUser();

        return new JsonDto().addDataEntry("user", UserDto.fromEntity(userEntity));
    }

    @PostMapping("/modify")
    public JsonDto modify(@RequestBody Map<String, ?> input) {
        var userDto = objectMapper.convertValue(input.get("user"), UserDto.class);
        var code = objectMapper.convertValue(input.get("code"), String.class);

        if ((userDto.getMobile() != null && !userUsecases.checkEmailVerifyCode("modify", userDto.getMobile(), code))
                || userDto.getEmail() != null
                        && !userUsecases.checkEmailVerifyCode("modify", userDto.getEmail(), code)) {
            throw new UsecaseException("验证码错误");
        }

        var userEntity = userUsecases.modify(loggedUserId(), userDto.toEntity());

        return new JsonDto().addDataEntry("user", UserDto.fromEntity(userEntity));
    }

    @GetMapping("/info")
    public JsonDto info(@RequestParam Long id) {
        var userEntity = userUsecases.info(id);

        return new JsonDto().addDataEntry("user", fullUserDto(userEntity));
    }

    @PostMapping("/follow")
    public JsonDto follow(@RequestBody Map<String, ?> input) {
        var userId = objectMapper.convertValue(input.get("userId"), Long.class);

        userUsecases.follow(loggedUserId(), userId);

        return new JsonDto();
    }

    @PostMapping("/unfollow")
    public JsonDto unfollow(@RequestBody Map<String, ?> input) {
        var userId = objectMapper.convertValue(input.get("userId"), Long.class);

        userUsecases.unfollow(loggedUserId(), userId);

        return new JsonDto();
    }

    @GetMapping("/following")
    public JsonDto following(@RequestParam(required = false) Long userId,
            @RequestParam(required = false, defaultValue = "20") Long limit,
            @RequestParam(required = false) Long offset) {
        var userEntities = userUsecases.following(userId, limit, offset);

        return new JsonDto().addDataEntry("users",
                userEntities.stream().map(userEntity -> fullUserDto(userEntity)).collect(Collectors.toList()));
    }

    @GetMapping("/follower")
    public JsonDto follower(@RequestParam(required = false) Long userId,
            @RequestParam(required = false, defaultValue = "20") Long limit,
            @RequestParam(required = false) Long offset) {
        var userEntities = userUsecases.follower(userId, limit, offset);

        return new JsonDto().addDataEntry("users",
                userEntities.stream().map(userEntity -> fullUserDto(userEntity)).collect(Collectors.toList()));
    }
}

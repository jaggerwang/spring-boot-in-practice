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
import net.jaggerwang.sbip.adapter.controller.dto.JsonDTO;
import net.jaggerwang.sbip.adapter.controller.dto.UserDTO;
import net.jaggerwang.sbip.entity.UserEntity;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @PostMapping("/register")
    public JsonDTO register(@RequestBody UserDTO userDTO) {
        var userEntity = userUsecases.register(userDTO.toEntity());

        loginUser(userDTO.getUsername(), userDTO.getPassword());

        metricUsecases.increment("registerCount", 1L);

        return new JsonDTO().addDataEntry("user", UserDTO.fromEntity(userEntity));
    }

    @PostMapping("/login")
    public JsonDTO login(@RequestBody UserDTO userDTO) {
        String username = null;
        UserEntity userEntity = null;
        if (userDTO.getUsername() != null) {
            username = userDTO.getUsername();
            userEntity = userUsecases.infoByUsername(username);
        } else if (userDTO.getMobile() != null) {
            username = userDTO.getMobile();
            userEntity = userUsecases.infoByMobile(username);
        } else if (userDTO.getEmail() != null) {
            username = userDTO.getEmail();
            userEntity = userUsecases.infoByEmail(username);
        } else {
            username = null;
            userEntity = null;
        }
        var password = userDTO.getPassword();
        if (username == null || password == null) {
            throw new UsecaseException("用户名或密码不能为空");
        }

        loginUser(username, password);

        return new JsonDTO().addDataEntry("user", UserDTO.fromEntity(userEntity));
    }

    @GetMapping("/logged")
    public JsonDTO logged() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken || !auth.isAuthenticated()) {
            return new JsonDTO().addDataEntry("user", null);
        }

        return new JsonDTO().addDataEntry("user", fullUserDTO(userUsecases.info(loggedUserId())));
    }

    @GetMapping("/logout")
    public JsonDTO logout() {
        var userEntity = userUsecases.info(loggedUserId());

        logoutUser();

        return new JsonDTO().addDataEntry("user", UserDTO.fromEntity(userEntity));
    }

    @PostMapping("/modify")
    public JsonDTO modify(@RequestBody Map<String, ?> input) {
        var userDTO = objectMapper.convertValue(input.get("user"), UserDTO.class);
        var code = objectMapper.convertValue(input.get("code"), String.class);

        if ((userDTO.getMobile() != null
                && !userUsecases.checkEmailVerifyCode("modify", userDTO.getMobile(), code))
                || userDTO.getEmail() != null
                        && !userUsecases.checkEmailVerifyCode("modify", userDTO.getEmail(), code)) {
            throw new UsecaseException("验证码错误");
        }

        var userEntity = userUsecases.modify(loggedUserId(), userDTO.toEntity());

        return new JsonDTO().addDataEntry("user", UserDTO.fromEntity(userEntity));
    }

    @GetMapping("/info")
    public JsonDTO info(@RequestParam Long id) {
        var userEntity = userUsecases.info(id);

        return new JsonDTO().addDataEntry("user", fullUserDTO(userEntity));
    }

    @PostMapping("/follow")
    public JsonDTO follow(@RequestBody Map<String, ?> input) {
        var userId = objectMapper.convertValue(input.get("userId"), Long.class);

        userUsecases.follow(loggedUserId(), userId);

        return new JsonDTO();
    }

    @PostMapping("/unfollow")
    public JsonDTO unfollow(@RequestBody Map<String, ?> input) {
        var userId = objectMapper.convertValue(input.get("userId"), Long.class);

        userUsecases.unfollow(loggedUserId(), userId);

        return new JsonDTO();
    }

    @GetMapping("/following")
    public JsonDTO following(@RequestParam(required = false) Long userId,
            @RequestParam(required = false, defaultValue = "20") Long limit,
            @RequestParam(required = false) Long offset) {
        var userEntities = userUsecases.following(userId, limit, offset);

        return new JsonDTO().addDataEntry("users", userEntities.stream()
                .map(userEntity -> fullUserDTO(userEntity)).collect(Collectors.toList()));
    }

    @GetMapping("/follower")
    public JsonDTO follower(@RequestParam(required = false) Long userId,
            @RequestParam(required = false, defaultValue = "20") Long limit,
            @RequestParam(required = false) Long offset) {
        var userEntities = userUsecases.follower(userId, limit, offset);

        return new JsonDTO().addDataEntry("users", userEntities.stream()
                .map(userEntity -> fullUserDTO(userEntity)).collect(Collectors.toList()));
    }
}

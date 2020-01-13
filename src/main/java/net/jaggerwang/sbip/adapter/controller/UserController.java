package net.jaggerwang.sbip.adapter.controller;

import java.util.Map;
import java.util.Optional;
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
import net.jaggerwang.sbip.adapter.controller.dto.UserDto;
import net.jaggerwang.sbip.entity.UserEntity;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;

@RestController
@RequestMapping("/user")
@Api(tags = "User Apis")
public class UserController extends AbstractController {
    @PostMapping("/register")
    @ApiOperation("Register user")
    public RootDto register(@RequestBody UserDto userDto) {
        var userEntity = userUsecases.register(userDto.toEntity());

        loginUser(userDto.getUsername(), userDto.getPassword());

        metricUsecases.increment("registerCount", 1L);

        return new RootDto().addDataEntry("user", UserDto.fromEntity(userEntity));
    }

    @PostMapping("/login")
    @ApiOperation("Login")
    public RootDto login(@RequestBody UserDto userDto) {
        Optional<UserEntity> userEntity;
        if (userDto.getUsername() != null) {
            userEntity = userUsecases.infoByUsername(userDto.getUsername());
        } else if (userDto.getMobile() != null) {
            userEntity = userUsecases.infoByMobile(userDto.getMobile());
        } else if (userDto.getEmail() != null) {
            userEntity = userUsecases.infoByEmail(userDto.getEmail());
        } else {
            throw new UsecaseException("用户名、手机或邮箱不能都为空");
        }
        if (userEntity.isEmpty()) {
            throw new UsecaseException("用户名或密码错误");
        }

        var password = userDto.getPassword();
        if (password == null) {
            throw new UsecaseException("密码不能为空");
        }

        loginUser(userEntity.get().getUsername(), password);

        return new RootDto().addDataEntry("user", UserDto.fromEntity(userEntity.get()));
    }

    @GetMapping("/logged")
    @ApiOperation("Current user")
    public RootDto logged() {
        if (loggedUserId() == null) {
            return new RootDto().addDataEntry("user", null);
        }

        var userEntity = userUsecases.info(loggedUserId());
        return new RootDto().addDataEntry("user", userEntity.map(this::fullUserDto).get());
    }

    @GetMapping("/logout")
    @ApiOperation("Logout")
    public RootDto logout() {
        var userEntity = userUsecases.info(loggedUserId());
        if (userEntity.isEmpty()) {
            throw new NotFoundException("用户未找到");
        }

        logoutUser();

        return new RootDto().addDataEntry("user", UserDto.fromEntity(userEntity.get()));
    }

    @PostMapping("/modify")
    @ApiOperation("Modify info of current user")
    public RootDto modify(@RequestBody Map<String, Object> input) {
        var userDto = objectMapper.convertValue(input.get("user"), UserDto.class);
        var code = objectMapper.convertValue(input.get("code"), String.class);

        if ((userDto.getMobile() != null && !userUsecases.checkMobileVerifyCode("modify", userDto.getMobile(), code))
                || userDto.getEmail() != null
                        && !userUsecases.checkEmailVerifyCode("modify", userDto.getEmail(), code)) {
            throw new UsecaseException("验证码错误");
        }

        var userEntity = userUsecases.modify(loggedUserId(), userDto.toEntity());

        return new RootDto().addDataEntry("user", UserDto.fromEntity(userEntity));
    }

    @GetMapping("/info")
    @ApiOperation("Get user info")
    public RootDto info(@RequestParam Long id) {
        var userEntity = userUsecases.info(id);
        if (userEntity.isEmpty()) {
            throw new NotFoundException("用户未找到");
        }

        return new RootDto().addDataEntry("user", fullUserDto(userEntity.get()));
    }

    @PostMapping("/follow")
    @ApiOperation("Follow user")
    public RootDto follow(@RequestBody Map<String, Object> input) {
        var userId = objectMapper.convertValue(input.get("userId"), Long.class);

        userUsecases.follow(loggedUserId(), userId);

        return new RootDto();
    }

    @PostMapping("/unfollow")
    @ApiOperation("Unfollow user")
    public RootDto unfollow(@RequestBody Map<String, Object> input) {
        var userId = objectMapper.convertValue(input.get("userId"), Long.class);

        userUsecases.unfollow(loggedUserId(), userId);

        return new RootDto();
    }

    @GetMapping("/following")
    @ApiOperation("Users followed by some user")
    public RootDto following(@RequestParam(required = false) Long userId,
                             @RequestParam(defaultValue = "20") Long limit,
                             @RequestParam(defaultValue = "0") Long offset) {
        var userEntities = userUsecases.following(userId, limit, offset);

        return new RootDto().addDataEntry("users",
                userEntities.stream().map(this::fullUserDto).collect(Collectors.toList()));
    }

    @GetMapping("/follower")
    @ApiOperation("Users following some user")
    public RootDto follower(@RequestParam(required = false) Long userId,
                            @RequestParam(defaultValue = "20") Long limit,
                            @RequestParam(defaultValue = "0") Long offset) {
        var userEntities = userUsecases.follower(userId, limit, offset);

        return new RootDto().addDataEntry("users",
                userEntities.stream().map(this::fullUserDto).collect(Collectors.toList()));
    }

    @PostMapping("/sendMobileVerifyCode")
    @ApiOperation("Send verify code by mobile")
    public RootDto sendMobileVerifyCode(@RequestBody Map<String, Object> input) {
        var type = objectMapper.convertValue(input.get("type"), String.class);
        var mobile = objectMapper.convertValue(input.get("mobile"), String.class);

        var verifyCode = userUsecases.sendMobileVerifyCode(type, mobile);

        return new RootDto().addDataEntry("verifyCode", verifyCode);
    }

    @PostMapping("/sendEmailVerifyCode")
    @ApiOperation("Send verify code by email")
    public RootDto sendEmailVerifyCode(@RequestBody Map<String, Object> input) {
        var type = objectMapper.convertValue(input.get("type"), String.class);
        var email = objectMapper.convertValue(input.get("email"), String.class);

        var verifyCode = userUsecases.sendEmailVerifyCode(type, email);

        return new RootDto().addDataEntry("verifyCode", verifyCode);
    }
}

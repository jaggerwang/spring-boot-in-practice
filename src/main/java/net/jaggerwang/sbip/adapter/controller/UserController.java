package net.jaggerwang.sbip.adapter.controller;

import java.util.Map;
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
import net.jaggerwang.sbip.usecase.exception.UsecaseException;

@RestController
@RequestMapping("/user")
@Api(tags = "User Apis")
public class UserController extends AbstractController {
    @PostMapping("/register")
    @ApiOperation("Register user")
    public RootDto register(@RequestBody UserDto userDto) {
        var userEntity = userUsecase.register(userDto.toEntity());

        loginUser(userDto.getUsername(), userDto.getPassword());

        metricUsecase.increment("registerCount", 1L);

        return new RootDto().addDataEntry("user", UserDto.fromEntity(userEntity));
    }

    @PostMapping("/modify")
    @ApiOperation("Modify info of current user")
    public RootDto modify(@RequestBody Map<String, Object> input) {
        var userDto = objectMapper.convertValue(input.get("user"), UserDto.class);
        var code = objectMapper.convertValue(input.get("code"), String.class);

        if ((userDto.getMobile() != null && !userUsecase.checkMobileVerifyCode("modify", userDto.getMobile(), code))
                || userDto.getEmail() != null
                        && !userUsecase.checkEmailVerifyCode("modify", userDto.getEmail(), code)) {
            throw new UsecaseException("验证码错误");
        }

        var userEntity = userUsecase.modify(loggedUserId(), userDto.toEntity());

        return new RootDto().addDataEntry("user", UserDto.fromEntity(userEntity));
    }

    @GetMapping("/info")
    @ApiOperation("Get user info")
    public RootDto info(@RequestParam Long id) {
        var userEntity = userUsecase.info(id);
        if (userEntity.isEmpty()) {
            throw new NotFoundException("用户未找到");
        }

        return new RootDto().addDataEntry("user", fullUserDto(userEntity.get()));
    }

    @PostMapping("/follow")
    @ApiOperation("Follow user")
    public RootDto follow(@RequestBody Map<String, Object> input) {
        var userId = objectMapper.convertValue(input.get("userId"), Long.class);

        userUsecase.follow(loggedUserId(), userId);

        return new RootDto();
    }

    @PostMapping("/unfollow")
    @ApiOperation("Unfollow user")
    public RootDto unfollow(@RequestBody Map<String, Object> input) {
        var userId = objectMapper.convertValue(input.get("userId"), Long.class);

        userUsecase.unfollow(loggedUserId(), userId);

        return new RootDto();
    }

    @GetMapping("/following")
    @ApiOperation("Users followed by some user")
    public RootDto following(@RequestParam(required = false) Long userId,
                             @RequestParam(defaultValue = "20") Long limit,
                             @RequestParam(defaultValue = "0") Long offset) {
        var userEntities = userUsecase.following(userId, limit, offset);

        return new RootDto().addDataEntry("users",
                userEntities.stream().map(this::fullUserDto).collect(Collectors.toList()));
    }

    @GetMapping("/follower")
    @ApiOperation("Users following some user")
    public RootDto follower(@RequestParam(required = false) Long userId,
                            @RequestParam(defaultValue = "20") Long limit,
                            @RequestParam(defaultValue = "0") Long offset) {
        var userEntities = userUsecase.follower(userId, limit, offset);

        return new RootDto().addDataEntry("users",
                userEntities.stream().map(this::fullUserDto).collect(Collectors.toList()));
    }

    @PostMapping("/sendMobileVerifyCode")
    @ApiOperation("Send verify code by mobile")
    public RootDto sendMobileVerifyCode(@RequestBody Map<String, Object> input) {
        var type = objectMapper.convertValue(input.get("type"), String.class);
        var mobile = objectMapper.convertValue(input.get("mobile"), String.class);

        var verifyCode = userUsecase.sendMobileVerifyCode(type, mobile);

        return new RootDto().addDataEntry("verifyCode", verifyCode);
    }

    @PostMapping("/sendEmailVerifyCode")
    @ApiOperation("Send verify code by email")
    public RootDto sendEmailVerifyCode(@RequestBody Map<String, Object> input) {
        var type = objectMapper.convertValue(input.get("type"), String.class);
        var email = objectMapper.convertValue(input.get("email"), String.class);

        var verifyCode = userUsecase.sendEmailVerifyCode(type, email);

        return new RootDto().addDataEntry("verifyCode", verifyCode);
    }
}

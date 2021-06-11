package net.jaggerwang.sbip.adapter.api.controller;

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
import net.jaggerwang.sbip.adapter.api.controller.dto.RootDTO;
import net.jaggerwang.sbip.adapter.api.controller.dto.UserDTO;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;

/**
 * @author Jagger Wang
 */
@RestController
@RequestMapping("/user")
@Api(tags = "User Apis")
public class UserController extends AbstractController {
    @PostMapping("/register")
    @ApiOperation("Register user")
    public RootDTO register(@RequestBody UserDTO userDto) {
        var userBO = userUsecase.register(userDto.toBO());

        loginUser(userDto.getUsername(), userDto.getPassword());

        metricUsecase.increment("registerCount", 1L);

        return new RootDTO().addDataEntry("user", UserDTO.fromBO(userBO));
    }

    @PostMapping("/modify")
    @ApiOperation("Modify info of current user")
    public RootDTO modify(@RequestBody Map<String, Object> input) {
        var userDto = objectMapper.convertValue(input.get("user"), UserDTO.class);
        var code = objectMapper.convertValue(input.get("code"), String.class);

        if ((userDto.getMobile() != null
                && !userUsecase.checkMobileVerifyCode("modify", userDto.getMobile(), code))
                || userDto.getEmail() != null
                        && !userUsecase.checkEmailVerifyCode("modify", userDto.getEmail(), code)) {
            throw new UsecaseException("验证码错误");
        }

        var userBO = userUsecase.modify(loggedUserId(), userDto.toBO());

        return new RootDTO().addDataEntry("user", UserDTO.fromBO(userBO));
    }

    @GetMapping("/info")
    @ApiOperation("Get user info")
    public RootDTO info(@RequestParam Long id) {
        var userBO = userUsecase.info(id);
        if (userBO.isEmpty()) {
            throw new NotFoundException("用户未找到");
        }

        return new RootDTO().addDataEntry("user", fullUserDto(userBO.get()));
    }

    @PostMapping("/follow")
    @ApiOperation("Follow user")
    public RootDTO follow(@RequestBody Map<String, Object> input) {
        var userId = objectMapper.convertValue(input.get("userId"), Long.class);

        userUsecase.follow(loggedUserId(), userId);

        return new RootDTO();
    }

    @PostMapping("/unfollow")
    @ApiOperation("Unfollow user")
    public RootDTO unfollow(@RequestBody Map<String, Object> input) {
        var userId = objectMapper.convertValue(input.get("userId"), Long.class);

        userUsecase.unfollow(loggedUserId(), userId);

        return new RootDTO();
    }

    @GetMapping("/following")
    @ApiOperation("Users followed by some user")
    public RootDTO following(@RequestParam(required = false) Long userId,
                             @RequestParam(defaultValue = "20") Long limit,
                             @RequestParam(defaultValue = "0") Long offset) {
        var userBOs = userUsecase.following(userId, limit, offset);

        return new RootDTO().addDataEntry("users",
                userBOs.stream().map(this::fullUserDto).collect(Collectors.toList()));
    }

    @GetMapping("/follower")
    @ApiOperation("Users following some user")
    public RootDTO follower(@RequestParam(required = false) Long userId,
                            @RequestParam(defaultValue = "20") Long limit,
                            @RequestParam(defaultValue = "0") Long offset) {
        var userBOs = userUsecase.follower(userId, limit, offset);

        return new RootDTO().addDataEntry("users",
                userBOs.stream().map(this::fullUserDto).collect(Collectors.toList()));
    }

    @PostMapping("/sendMobileVerifyCode")
    @ApiOperation("Send verify code by mobile")
    public RootDTO sendMobileVerifyCode(@RequestBody Map<String, Object> input) {
        var type = objectMapper.convertValue(input.get("type"), String.class);
        var mobile = objectMapper.convertValue(input.get("mobile"), String.class);

        var verifyCode = userUsecase.sendMobileVerifyCode(type, mobile);

        return new RootDTO().addDataEntry("verifyCode", verifyCode);
    }

    @PostMapping("/sendEmailVerifyCode")
    @ApiOperation("Send verify code by email")
    public RootDTO sendEmailVerifyCode(@RequestBody Map<String, Object> input) {
        var type = objectMapper.convertValue(input.get("type"), String.class);
        var email = objectMapper.convertValue(input.get("email"), String.class);

        var verifyCode = userUsecase.sendEmailVerifyCode(type, email);

        return new RootDTO().addDataEntry("verifyCode", verifyCode);
    }
}

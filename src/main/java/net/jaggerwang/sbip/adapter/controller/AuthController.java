package net.jaggerwang.sbip.adapter.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.jaggerwang.sbip.adapter.controller.dto.RootDto;
import net.jaggerwang.sbip.adapter.controller.dto.UserDto;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Api(tags = "User Apis")
public class AuthController extends AbstractController {
    @PostMapping("/login")
    @ApiOperation("Login")
    public RootDto login(@RequestBody UserDto userDto) {
        String username = null;
        if (userDto.getUsername() != null)  {
            username = userDto.getUsername();
        } else if (userDto.getMobile() != null) {
            username = userDto.getMobile();
        } else if (userDto.getEmail() != null) {
            username = userDto.getEmail();
        }
        if (StringUtils.isEmpty(username)) {
            throw new UsecaseException("用户名、手机或邮箱不能都为空");
        }
        var password = userDto.getPassword();
        if (StringUtils.isEmpty(password)) {
            throw new UsecaseException("密码不能为空");
        }

        var loggedUser = loginUser(username, password);

        var userEntity = userUsecase.info(loggedUser.getId());
        return new RootDto().addDataEntry("user", UserDto.fromEntity(userEntity.get()));
    }

    @GetMapping("/logout")
    @ApiOperation("Logout")
    public RootDto logout() {
        var loggedUser = logoutUser();
        if (loggedUser.isEmpty()) {
            return new RootDto().addDataEntry("user", null);
        }

        var userEntity = userUsecase.info(loggedUser.get().getId());
        return new RootDto().addDataEntry("user", UserDto.fromEntity(userEntity.get()));
    }

    @GetMapping("/logged")
    @ApiOperation("Current user")
    public RootDto logged() {
        if (loggedUserId() == null) {
            return new RootDto().addDataEntry("user", null);
        }

        var userEntity = userUsecase.info(loggedUserId());
        return new RootDto().addDataEntry("user", userEntity.map(this::fullUserDto).get());
    }
}

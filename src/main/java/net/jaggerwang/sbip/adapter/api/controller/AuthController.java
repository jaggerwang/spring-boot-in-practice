package net.jaggerwang.sbip.adapter.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.jaggerwang.sbip.adapter.api.controller.dto.RootDTO;
import net.jaggerwang.sbip.adapter.api.controller.dto.UserDTO;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jagger Wang
 */
@RestController
@RequestMapping("/auth")
@Api(tags = "User Apis")
public class AuthController extends AbstractController {
    @PostMapping("/login")
    @ApiOperation("Login")
    public RootDTO login(@RequestBody UserDTO userDto) {
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

        var userBO = userUsecase.info(loggedUser.getId());
        return new RootDTO().addDataEntry("user", UserDTO.fromBO(userBO.get()));
    }

    @GetMapping("/logout")
    @ApiOperation("Logout")
    public RootDTO logout() {
        var loggedUser = logoutUser();
        if (loggedUser == null) {
            return new RootDTO().addDataEntry("user", null);
        }

        var userBO = userUsecase.info(loggedUser.getId());
        return new RootDTO().addDataEntry("user", UserDTO.fromBO(userBO.get()));
    }

    @GetMapping("/logged")
    @ApiOperation("Current user")
    public RootDTO logged() {
        if (loggedUserId() == null) {
            return new RootDTO().addDataEntry("user", null);
        }

        var userBO = userUsecase.info(loggedUserId());
        return new RootDTO().addDataEntry("user", userBO.map(this::fullUserDto).get());
    }
}

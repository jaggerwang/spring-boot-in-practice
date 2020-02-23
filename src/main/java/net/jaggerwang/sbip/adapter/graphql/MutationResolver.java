package net.jaggerwang.sbip.adapter.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.api.security.annotation.PermitAll;
import net.jaggerwang.sbip.entity.PostEntity;
import net.jaggerwang.sbip.entity.UserEntity;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;

import java.util.Optional;

@Component
public class MutationResolver extends AbstractResolver implements GraphQLMutationResolver {
    @PermitAll
    public UserEntity userRegister(UserEntity userInput) {
        var userEntity = userUsecase.register(userInput);

        loginUser(userInput.getUsername(), userInput.getPassword());

        return userEntity;
    }

    @PermitAll
    public UserEntity userLogin(UserEntity userInput) {
        Optional<UserEntity> userEntity;
        if (userInput.getUsername() != null) {
            userEntity = userUsecase.infoByUsername(userInput.getUsername());
        } else if (userInput.getMobile() != null) {
            userEntity = userUsecase.infoByMobile(userInput.getMobile());
        } else if (userInput.getEmail() != null) {
            userEntity = userUsecase.infoByEmail(userInput.getEmail());
        } else {
            throw new UsecaseException("用户名、手机或邮箱不能都为空");
        }
        if (userEntity.isEmpty()) {
            throw new UsecaseException("用户名或密码错误");
        }

        var password = userInput.getPassword();
        if (password == null) {
            throw new UsecaseException("密码不能为空");
        }

        loginUser(userEntity.get().getUsername(), password);

        return userEntity.get();
    }

    public UserEntity userModify(UserEntity userInput, String code) {
        if ((userInput.getMobile() != null
                && !userUsecase.checkMobileVerifyCode("modify", userInput.getMobile(), code))
                || userInput.getEmail() != null && !userUsecase.checkEmailVerifyCode("modify",
                        userInput.getEmail(), code)) {
            throw new UsecaseException("验证码错误");
        }

        return userUsecase.modify(loggedUserId(), userInput);
    }

    public String userSendMobileVerifyCode(String type, String mobile) {
        return userUsecase.sendMobileVerifyCode(type, mobile);
    }

    public Boolean userFollow(Long userId) {
        userUsecase.follow(loggedUserId(), userId);
        return true;
    }

    public Boolean userUnfollow(Long userId) {
        userUsecase.unfollow(loggedUserId(), userId);
        return true;
    }

    public PostEntity postPublish(PostEntity postInput) {
        postInput.setUserId(loggedUserId());
        return postUsecase.publish(postInput);
    }

    public Boolean postDelete(Long id) {
        var postEntity = postUsecase.info(id);
        if (postEntity.isEmpty()) {
            throw new NotFoundException("动态未找到");
        }
        if (!loggedUserId().equals(postEntity.get().getUserId())) {
            throw new UsecaseException("无权删除");
        }

        postUsecase.delete(id);
        return true;
    }

    public Boolean postLike(Long postId) {
        postUsecase.like(loggedUserId(), postId);
        return true;
    }

    public Boolean postUnlike(Long postId) {
        postUsecase.unlike(loggedUserId(), postId);
        return true;
    }
}

package net.jaggerwang.sbip.adapter.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.api.security.annotation.PermitALL;
import net.jaggerwang.sbip.entity.PostEntity;
import net.jaggerwang.sbip.entity.UserEntity;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;

@Component
public class MutationResolver extends AbstractResolver implements GraphQLMutationResolver {
    @PermitALL
    public UserEntity userRegister(UserEntity userInput) {
        var userEntity = userUsecases.register(userInput);

        loginUser(userInput.getUsername(), userInput.getPassword());

        return userEntity;
    }

    @PermitALL
    public UserEntity userLogin(UserEntity userInput) {
        String username = null;
        UserEntity userEntity = null;
        if (userInput.getUsername() != null) {
            username = userInput.getUsername();
            userEntity = userUsecases.infoByUsername(username);
        } else if (userInput.getMobile() != null) {
            username = userInput.getMobile();
            userEntity = userUsecases.infoByMobile(username);
        } else if (userInput.getEmail() != null) {
            username = userInput.getEmail();
            userEntity = userUsecases.infoByEmail(username);
        }
        var password = userInput.getPassword();
        if (username == null || password == null) {
            throw new UsecaseException("用户名或密码不能为空");
        }

        loginUser(username, password);

        return userEntity;
    }

    public UserEntity userModify(UserEntity userInput, String code) {
        if ((userInput.getMobile() != null
                && !userUsecases.checkMobileVerifyCode("modify", userInput.getMobile(), code))
                || userInput.getEmail() != null && !userUsecases.checkEmailVerifyCode("modify",
                        userInput.getEmail(), code)) {
            throw new UsecaseException("验证码错误");
        }

        return userUsecases.modify(loggedUserId(), userInput);
    }

    public String userSendMobileVerifyCode(String type, String mobile) {
        return userUsecases.sendMobileVerifyCode(type, mobile);
    }

    public Boolean userFollow(Long userId) {
        userUsecases.follow(loggedUserId(), userId);
        return true;
    }

    public Boolean userUnfollow(Long userId) {
        userUsecases.unfollow(loggedUserId(), userId);
        return true;
    }

    public PostEntity postPublish(PostEntity postInput) {
        postInput.setUserId(loggedUserId());
        return postUsecases.publish(postInput);
    }

    public Boolean postDelete(Long id) {
        var postEntity = postUsecases.info(id);
        if (!loggedUserId().equals(postEntity.getUserId())) {
            throw new UsecaseException("无权删除");
        }

        postUsecases.delete(id);
        return true;
    }

    public Boolean postLike(Long postId) {
        postUsecases.like(loggedUserId(), postId);
        return true;
    }

    public Boolean postUnlike(Long postId) {
        postUsecases.unlike(loggedUserId(), postId);
        return true;
    }
}

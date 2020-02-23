package net.jaggerwang.sbip.adapter.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.api.security.annotation.PermitAll;
import net.jaggerwang.sbip.entity.PostEntity;
import net.jaggerwang.sbip.entity.UserEntity;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;

import java.util.Optional;

@Component
public class MutationDataFetcher extends AbstractDataFetcher {
    public DataFetcher userRegister() {
        return new DataFetcher() {
            @PermitAll
            @Override
            public Object get(DataFetchingEnvironment env) {
                var userInput = objectMapper.convertValue(env.getArgument("user"), UserEntity.class);
                var userEntity = userUsecase.register(userInput);
                loginUser(userInput.getUsername(), userInput.getPassword());
                return userEntity;
            }
        };
    }

    public DataFetcher userLogin() {
        return new DataFetcher() {
            @PermitAll
            @Override
            public Object get(DataFetchingEnvironment env) {
                var userInput = objectMapper.convertValue(env.getArgument("user"), UserEntity.class);
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
        };
    }

    public DataFetcher userModify() {
        return env -> {
            var userInput = objectMapper.convertValue(env.getArgument("user"), UserEntity.class);
            String code = env.getArgument("code");
            if ((userInput.getMobile() != null
                    && !userUsecase.checkMobileVerifyCode("modify", userInput.getMobile(), code))
                    || userInput.getEmail() != null && !userUsecase.checkEmailVerifyCode("modify",
                    userInput.getEmail(), code)) {
                throw new UsecaseException("验证码错误");
            }

            return userUsecase.modify(loggedUserId(), userInput);
        };
    }

    public DataFetcher userSendMobileVerifyCode() {
        return env -> {
            String type = env.getArgument("type");
            String mobile = env.getArgument("mobile");
            return userUsecase.sendMobileVerifyCode(type, mobile);
        };
    }

    public DataFetcher userFollow() {
        return env -> {
            var userId = Long.valueOf((Integer) env.getArgument("userId"));
            userUsecase.follow(loggedUserId(), userId);
            return true;
        };
    }

    public DataFetcher userUnfollow() {
        return env -> {
            var userId = Long.valueOf((Integer) env.getArgument("userId"));
            userUsecase.unfollow(loggedUserId(), userId);
            return true;
        };
    }

    public DataFetcher postPublish() {
        return env -> {
            var postInput = objectMapper.convertValue(env.getArgument("post"), PostEntity.class);
            postInput.setUserId(loggedUserId());
            return postUsecase.publish(postInput);
        };
    }

    public DataFetcher postDelete() {
        return env -> {
            var id = Long.valueOf((Integer) env.getArgument("id"));
            var postEntity = postUsecase.info(id);
            if (postEntity.isEmpty()) {
                throw new NotFoundException("动态未找到");
            }
            if (!loggedUserId().equals(postEntity.get().getUserId())) {
                throw new UsecaseException("无权删除");
            }

            postUsecase.delete(id);
            return true;
        };
    }

    public DataFetcher postLike() {
        return env -> {
            var postId = Long.valueOf((Integer) env.getArgument("postId"));
            postUsecase.like(loggedUserId(), postId);
            return true;
        };
    }

    public DataFetcher postUnlike() {
        return env -> {
            var postId = Long.valueOf((Integer) env.getArgument("postId"));
            postUsecase.unlike(loggedUserId(), postId);
            return true;
        };
    }
}

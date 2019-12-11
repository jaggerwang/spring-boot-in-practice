package net.jaggerwang.sbip.adapter.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.entity.UserStatEntity;
import net.jaggerwang.sbip.entity.UserEntity;

@Component
public class UserStatResolver extends BaseResolver implements GraphQLResolver<UserStatEntity> {
    public UserEntity user(UserStatEntity userStatEntity) {
        return userUsecases.info(userStatEntity.getUserId());
    }
}

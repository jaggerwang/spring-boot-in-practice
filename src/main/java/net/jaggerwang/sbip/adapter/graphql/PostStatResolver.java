package net.jaggerwang.sbip.adapter.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.entity.PostStatEntity;
import net.jaggerwang.sbip.entity.PostEntity;

@Component
public class PostStatResolver extends BaseResolver implements GraphQLResolver<PostStatEntity> {
    public PostEntity post(PostStatEntity postStatEntity) {
        return postUsecases.info(postStatEntity.getPostId());
    }
}

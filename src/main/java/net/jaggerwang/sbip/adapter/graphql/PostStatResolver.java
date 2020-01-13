package net.jaggerwang.sbip.adapter.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import net.jaggerwang.sbip.entity.PostEntity;
import net.jaggerwang.sbip.entity.PostStatEntity;
import org.springframework.stereotype.Component;

@Component
public class PostStatResolver extends AbstractResolver implements GraphQLResolver<PostStatEntity> {
    public PostEntity post(PostStatEntity postStatEntity) {
        return postUsecases.info(postStatEntity.getPostId()).get();
    }
}

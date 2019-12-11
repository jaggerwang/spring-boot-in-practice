package net.jaggerwang.sbip.adapter.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.PostStatDO;

@Repository
public interface PostStatRepo extends JpaRepository<PostStatDO, Long> {
    Optional<PostStatDO> findByPostId(Long postId);
}

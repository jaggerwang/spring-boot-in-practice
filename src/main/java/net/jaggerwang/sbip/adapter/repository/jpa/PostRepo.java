package net.jaggerwang.sbip.adapter.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.PostDO;

@Repository
public interface PostRepo extends JpaRepository<PostDO, Long>, PostRepoCustom {
}

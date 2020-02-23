package net.jaggerwang.sbip.adapter.repository;

import static org.assertj.core.api.Assertions.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import net.jaggerwang.sbip.adapter.repository.jpa.UserFollowJpaRepository;
import net.jaggerwang.sbip.adapter.repository.jpa.UserJpaRepository;
import net.jaggerwang.sbip.api.config.CommonConfig;
import net.jaggerwang.sbip.api.config.JpaConfig;
import net.jaggerwang.sbip.entity.UserEntity;
import net.jaggerwang.sbip.usecase.port.repository.UserRepository;

@DataJpaTest
@ContextConfiguration(classes = {CommonConfig.class, JpaConfig.class})
@EnabledIfSystemProperty(named = "test.repository.enabled", matches = "true")
public class UserRepositoryTests {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UserFollowJpaRepository userFollowJpaRepository;

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository =
                new UserRepositoryImpl(jpaQueryFactory, userJpaRepository, userFollowJpaRepository);
    }

    @Test
    void save() {
        var userEntity = UserEntity.builder().username("jaggerwang").password("123456").build();
        var savedUser = userRepository.save(userEntity);
        assertThat(savedUser).hasFieldOrPropertyWithValue("username", userEntity.getUsername())
                .hasFieldOrPropertyWithValue("password", userEntity.getPassword());
    }
}

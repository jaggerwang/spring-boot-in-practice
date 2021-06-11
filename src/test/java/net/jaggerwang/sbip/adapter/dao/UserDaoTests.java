package net.jaggerwang.sbip.adapter.dao;

import static org.assertj.core.api.Assertions.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import net.jaggerwang.sbip.adapter.dao.jpa.repository.UserFollowRepository;
import net.jaggerwang.sbip.adapter.dao.jpa.repository.UserRepository;
import net.jaggerwang.sbip.adapter.api.config.CommonConfig;
import net.jaggerwang.sbip.adapter.api.config.JpaConfig;
import net.jaggerwang.sbip.entity.UserBO;
import net.jaggerwang.sbip.usecase.port.dao.UserDao;

@DataJpaTest
@ContextConfiguration(classes = {CommonConfig.class, JpaConfig.class})
@EnabledIfSystemProperty(named = "test.dao.enabled", matches = "true")
public class UserDaoTests {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFollowRepository userFollowRepository;

    private UserDao userDAO;

    @BeforeEach
    void setUp() {
        userDAO =
                new UserDaoImpl(jpaQueryFactory, userRepository, userFollowRepository);
    }

    @Test
    void save() {
        var userBO = UserBO.builder().username("jaggerwang").password("123456").build();
        var savedUser = userDAO.save(userBO);
        assertThat(savedUser).hasFieldOrPropertyWithValue("username", userBO.getUsername())
                .hasFieldOrPropertyWithValue("password", userBO.getPassword());
    }
}

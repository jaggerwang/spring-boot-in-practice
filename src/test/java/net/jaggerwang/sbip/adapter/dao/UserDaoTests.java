package net.jaggerwang.sbip.adapter.dao;

import static org.assertj.core.api.Assertions.*;

import net.jaggerwang.sbip.adapter.api.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import net.jaggerwang.sbip.entity.UserBO;
import net.jaggerwang.sbip.usecase.port.dao.UserDao;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = Application.class)
@MybatisTest
@Import(UserDaoImpl.class)
@EnabledIfSystemProperty(named = "test.dao.enabled", matches = "true")
public class UserDaoTests {
    @Autowired
    private UserDao userDAO;

    @Test
    void save() {
        var userBO = UserBO.builder()
                .username("jaggerwang")
                .password("123456")
                .build();
        var savedUser = userDAO.save(userBO);
        assertThat(savedUser).hasFieldOrPropertyWithValue("username", userBO.getUsername())
                .hasFieldOrPropertyWithValue("password", userBO.getPassword());
    }
}

package net.jaggerwang.sbip.usecase;

import net.jaggerwang.sbip.usecase.port.dao.RoleDAO;
import net.jaggerwang.sbip.util.encoder.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import java.util.Optional;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import net.jaggerwang.sbip.entity.UserBO;
import net.jaggerwang.sbip.usecase.port.dao.UserDAO;

@ExtendWith(SpringExtension.class)
@EnabledIfSystemProperty(named = "test.usecase.enabled", matches = "true")
public class UserUsecaseTests {
    private UserUsecase userUsecase;

    @MockBean
    private UserDAO userDAO;

    @MockBean
    private RoleDAO roleDAO;

    @BeforeEach
    void setUp() {
        userUsecase = new UserUsecase(userDAO, roleDAO);
    }

    @Test
    void register() {
        var passwordEncoder = new PasswordEncoder();

        var userEntity = UserBO.builder().username("jaggerwang").password("123456").build();
        given(userDAO.findByUsername(userEntity.getUsername())).willReturn(Optional.empty());

        var savedUser = UserBO.builder().username(userEntity.getUsername())
                .password(passwordEncoder.encode(userEntity.getPassword())).build();
        given(userDAO.save(any(UserBO.class))).willReturn(savedUser);

        var registeredUser = userUsecase.register(userEntity);
        assertThat(registeredUser).hasFieldOrPropertyWithValue("username", userEntity.getUsername())
                .hasFieldOrProperty("password");
        assertThat(registeredUser.getPassword()).isNotEqualTo(userEntity.getPassword());

    }
}

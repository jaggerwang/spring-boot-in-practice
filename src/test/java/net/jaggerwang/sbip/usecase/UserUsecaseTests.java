package net.jaggerwang.sbip.usecase;

import net.jaggerwang.sbip.usecase.port.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import java.util.Optional;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import net.jaggerwang.sbip.entity.UserEntity;
import net.jaggerwang.sbip.usecase.port.encoder.PasswordEncoder;
import net.jaggerwang.sbip.usecase.port.generator.RandomGenerator;
import net.jaggerwang.sbip.usecase.port.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@EnabledIfSystemProperty(named = "test.usecase.enabled", matches = "true")
public class UserUsecaseTests {
    private UserUsecase userUsecase;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private RandomGenerator randomGenerator;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userUsecase = new UserUsecase(userRepository, roleRepository, randomGenerator,
                passwordEncoder);
    }

    @Test
    void register() {
        given(passwordEncoder.encode(anyString())).will((invocation) -> invocation.getArgument(0));

        var userEntity = UserEntity.builder().username("jaggerwang").password("123456").build();
        given(userRepository.findByUsername(userEntity.getUsername())).willReturn(Optional.empty());

        var savedUser = UserEntity.builder().username(userEntity.getUsername())
                .password(passwordEncoder.encode(userEntity.getPassword())).build();
        given(userRepository.save(any(UserEntity.class))).willReturn(savedUser);

        var registeredUser = userUsecase.register(userEntity);
        assertThat(registeredUser).hasFieldOrPropertyWithValue("username", userEntity.getUsername())
                .hasFieldOrPropertyWithValue("password",
                        passwordEncoder.encode(userEntity.getPassword()));
    }
}

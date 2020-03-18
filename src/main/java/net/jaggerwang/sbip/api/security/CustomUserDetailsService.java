package net.jaggerwang.sbip.api.security;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import net.jaggerwang.sbip.entity.UserEntity;
import net.jaggerwang.sbip.usecase.UserUsecase;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserUsecase userUsecase;

    public CustomUserDetailsService(UserUsecase userUsecase) {
        this.userUsecase = userUsecase;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity;
        if (username.matches("[0-9]+")) {
            userEntity = userUsecase.infoByMobile(username);
        } else if (username.matches("[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+")) {
            userEntity = userUsecase.infoByEmail(username);
        } else {
            userEntity = userUsecase.infoByUsername(username);
        }
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("用户未找到");
        }

        List<GrantedAuthority> authorities = userUsecase.roles(username).stream()
                .map(v -> new SimpleGrantedAuthority("ROLE_" + v.getName()))
                .collect(Collectors.toList());

        return new LoggedUser(userEntity.get().getId(), userEntity.get().getUsername(),
                userEntity.get().getPassword(), authorities);
    }
}

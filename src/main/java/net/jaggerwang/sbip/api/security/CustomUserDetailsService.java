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
import net.jaggerwang.sbip.usecase.AuthorityUsecases;
import net.jaggerwang.sbip.usecase.UserUsecases;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserUsecases userUsecases;
    private AuthorityUsecases authorityUsecases;

    public CustomUserDetailsService(UserUsecases userUsecases, AuthorityUsecases authorityUsecases) {
        this.userUsecases = userUsecases;
        this.authorityUsecases = authorityUsecases;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity;
        if (username.matches("[0-9]+")) {
            userEntity = userUsecases.infoByMobile(username);
        } else if (username.matches("[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+")) {
            userEntity = userUsecases.infoByEmail(username);
        } else {
            userEntity = userUsecases.infoByUsername(username);
        }
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("用户未找到");
        }

        List<GrantedAuthority> authorities = authorityUsecases.rolesOfUser(username).stream()
                .map(v -> new SimpleGrantedAuthority("ROLE_" + v.getName()))
                .collect(Collectors.toList());

        return new LoggedUser(userEntity.get().getId(), userEntity.get().getUsername(),
                userEntity.get().getPassword(), authorities);
    }
}

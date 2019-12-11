package net.jaggerwang.sbip.api.security;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import net.jaggerwang.sbip.entity.UserEntity;
import net.jaggerwang.sbip.usecase.AuthorityUsecases;
import net.jaggerwang.sbip.usecase.UserUsecases;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserUsecases userUsecases;

    @Autowired
    private AuthorityUsecases authorityUsecases;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity;
        try {
            if (username.matches("[0-9]+")) {
                userEntity = userUsecases.infoByMobile(username);
            } else if (username.matches("[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+")) {
                userEntity = userUsecases.infoByEmail(username);
            } else {
                userEntity = userUsecases.infoByUsername(username);
            }
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

        List<GrantedAuthority> authorities = authorityUsecases.rolesOfUser(username).stream()
                .map(v -> new SimpleGrantedAuthority("ROLE_" + v.getName()))
                .collect(Collectors.toList());

        return new LoggedUser(userEntity.getId(), userEntity.getUsername(),
                userEntity.getPassword(), authorities);
    }
}

package net.jaggerwang.sbip.adapter.api.security;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import net.jaggerwang.sbip.entity.UserBO;
import net.jaggerwang.sbip.usecase.UserUsecase;

/**
 * @author Jagger Wang
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserUsecase userUsecase;

    public UserDetailsServiceImpl(UserUsecase userUsecase) {
        this.userUsecase = userUsecase;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserBO> userBO;
        if (username.matches("[0-9]+")) {
            userBO = userUsecase.infoByMobile(username);
        } else if (username.matches("[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+")) {
            userBO = userUsecase.infoByEmail(username);
        } else {
            userBO = userUsecase.infoByUsername(username);
        }
        if (userBO.isEmpty()) {
            throw new UsernameNotFoundException("用户未找到");
        }

        List<GrantedAuthority> authorities = userUsecase.roles(username).stream()
                .map(v -> new SimpleGrantedAuthority("ROLE_" + v.getName()))
                .collect(Collectors.toList());

        return new LoggedUser(userBO.get().getId(), userBO.get().getUsername(),
                userBO.get().getPassword(), authorities);
    }
}

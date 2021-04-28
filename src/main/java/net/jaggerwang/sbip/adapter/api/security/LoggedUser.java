package net.jaggerwang.sbip.adapter.api.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


/**
 * @author Jagger Wang
 */
public class LoggedUser extends User {
    private static final long serialVersionUID = 1L;

    private final Long id;

    public LoggedUser(Long id, String username, String password,
            Collection<GrantedAuthority> authorities) {
        super(username, password, authorities);

        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

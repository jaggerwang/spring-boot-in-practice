package net.jaggerwang.sbip.adapter.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.jaggerwang.sbip.api.security.LoggedUser;
import net.jaggerwang.sbip.usecase.AuthorityUsecases;
import net.jaggerwang.sbip.usecase.FileUsecases;
import net.jaggerwang.sbip.usecase.MetricUsecases;
import net.jaggerwang.sbip.usecase.PostUsecases;
import net.jaggerwang.sbip.usecase.StatUsecases;
import net.jaggerwang.sbip.usecase.UserUsecases;


abstract public class AbstractResolver {
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected AuthenticationManager authManager;

    @Autowired
    protected AuthorityUsecases authorityUsecases;

    @Autowired
    protected FileUsecases fileUsecases;

    @Autowired
    protected MetricUsecases metricUsecases;

    @Autowired
    protected PostUsecases postUsecases;

    @Autowired
    protected StatUsecases statUsecases;

    @Autowired
    protected UserUsecases userUsecases;

    protected void loginUser(String username, String password) {
        var auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        var sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
    }

    protected void logoutUser() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    protected Long loggedUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken || !auth.isAuthenticated()) {
            return null;
        }

        var loggedUser = (LoggedUser) auth.getPrincipal();
        return loggedUser.getId();
    }
}

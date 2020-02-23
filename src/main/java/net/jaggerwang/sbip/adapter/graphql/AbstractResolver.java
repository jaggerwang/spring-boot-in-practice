package net.jaggerwang.sbip.adapter.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.jaggerwang.sbip.api.security.LoggedUser;
import net.jaggerwang.sbip.usecase.AuthorityUsecase;
import net.jaggerwang.sbip.usecase.FileUsecase;
import net.jaggerwang.sbip.usecase.MetricUsecase;
import net.jaggerwang.sbip.usecase.PostUsecase;
import net.jaggerwang.sbip.usecase.StatUsecase;
import net.jaggerwang.sbip.usecase.UserUsecase;


abstract public class AbstractResolver {
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected AuthenticationManager authManager;

    @Autowired
    protected AuthorityUsecase authorityUsecase;

    @Autowired
    protected FileUsecase fileUsecase;

    @Autowired
    protected MetricUsecase metricUsecase;

    @Autowired
    protected PostUsecase postUsecase;

    @Autowired
    protected StatUsecase statUsecase;

    @Autowired
    protected UserUsecase userUsecase;

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

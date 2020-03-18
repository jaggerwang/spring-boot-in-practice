package net.jaggerwang.sbip.adapter.graphql;

import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.jaggerwang.sbip.api.security.LoggedUser;
import net.jaggerwang.sbip.usecase.FileUsecase;
import net.jaggerwang.sbip.usecase.MetricUsecase;
import net.jaggerwang.sbip.usecase.PostUsecase;
import net.jaggerwang.sbip.usecase.StatUsecase;
import net.jaggerwang.sbip.usecase.UserUsecase;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


abstract public class AbstractDataFetcher {
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected AuthenticationManager authManager;

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

    protected LoggedUser loginUser(String username, String password) {
        var auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(
                username, password));
        var sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        return (LoggedUser) auth.getPrincipal();
    }

    protected Optional<LoggedUser> logoutUser() {
        var loggedUser = loggedUser();
        SecurityContextHolder.getContext().setAuthentication(null);
        return loggedUser;
    }

    protected Optional<LoggedUser> loggedUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken || !auth.isAuthenticated()) {
            return Optional.empty();
        }
        return Optional.of((LoggedUser) auth.getPrincipal());
    }

    protected Long loggedUserId() {
        var loggedUser = loggedUser();
        return loggedUser.isPresent() ? loggedUser.get().getId() : null;
    }

    public Map<String, DataFetcher> toMap() {
        var dataFetchers = new HashMap<String, DataFetcher>();
        var methods = this.getClass().getDeclaredMethods();
        for (var method: methods) {
            if (Modifier.isPublic(method.getModifiers()) &&
                    method.getReturnType().equals(DataFetcher.class)) {
                try {
                    dataFetchers.put(method.getName(), (DataFetcher) method.invoke(this));
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataFetchers;
    }
}

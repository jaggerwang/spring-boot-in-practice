package net.jaggerwang.sbip.adapter.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.jaggerwang.sbip.api.security.LoggedUser;
import net.jaggerwang.sbip.usecase.AuthorityUsecases;
import net.jaggerwang.sbip.usecase.FileUsecases;
import net.jaggerwang.sbip.usecase.MetricUsecases;
import net.jaggerwang.sbip.usecase.PostUsecases;
import net.jaggerwang.sbip.usecase.StatUsecases;
import net.jaggerwang.sbip.usecase.UserUsecases;


public abstract class BaseResolver {
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

    protected void login(String username, String password) {
        var auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        var sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        var requestAttrs =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        requestAttrs.getRequest().getSession().setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
    }

    protected Long loggedUserId() {
        var loggedFile =
                (LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loggedFile.getId();
    }
}

package net.jaggerwang.sbip.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jaggerwang.sbip.entity.UserEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql({"/db/init-db-test.sql"})
@Sql(scripts = {"/db/clean-db-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Disabled("The /graphql endpoint not exists in spring boot test. It should be a bug, see more on https://github.com/graphql-java-kickstart/graphql-spring-boot/issues/82")
public class UserGraphQLApiTests {
    @Autowired
    private MockMvc mvc;

    @Value("${graphql.servlet.mapping}")
    private String graphqlServletMapping;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register() throws Exception {
        var userEntity = UserEntity.builder().username("jagger001").password("123456").build();
        var content = new ObjectMapper().createObjectNode();
        content.put("query", "mutation($user: UserInput!) { userRegister(user: $user) { id username } }");
        content.putObject("variables").putObject("user").put("username", userEntity.getUsername()).put("password", userEntity.getPassword());
        mvc.perform(post(graphqlServletMapping).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(content))).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data.userRegister.username").value(userEntity.getUsername()));
    }

    @Test
    void login() throws Exception {
        var userEntity = UserEntity.builder().username("jaggerwang").password("123456").build();
        var content = new ObjectMapper().createObjectNode();
        content.put("query", "mutation($user: UserInput!) { authLogin(user: $user) { id username } }");
        content.putObject("variables").putObject("user").put("username", userEntity.getUsername()).put("password", userEntity.getPassword());
        mvc.perform(post(graphqlServletMapping).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(content))).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data.authLogin.username").value(userEntity.getUsername()));
    }

    @WithUserDetails("jaggerwang")
    @Test
    void logout() throws Exception {
        var content = new ObjectMapper().createObjectNode();
        content.put("query", "query { authLogout { id username } }");
        mvc.perform(post(graphqlServletMapping).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(content))).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data.authLogout.username").value("jaggerwang"));
    }

    @WithUserDetails("jaggerwang")
    @Test
    void logged() throws Exception {
        var content = new ObjectMapper().createObjectNode();
        content.put("query", "query { authLogged { id username } }");
        mvc.perform(post(graphqlServletMapping).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(content))).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data.authLogged.username").value("jaggerwang"));
    }

    @WithUserDetails("jaggerwang")
    @Test
    void info() throws Exception {
        var content = new ObjectMapper().createObjectNode();
        content.put("query", "query(id: Int!) { userInfo(id: $id) { id username } }");
        content.putObject("variables").put("id", 1);
        mvc.perform(post(graphqlServletMapping).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(content))).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data.userInfo.id").value(1));
    }
}

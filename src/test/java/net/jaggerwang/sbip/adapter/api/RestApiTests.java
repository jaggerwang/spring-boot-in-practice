package net.jaggerwang.sbip.adapter.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import net.jaggerwang.sbip.entity.UserBO;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql({"/db/init-db-test.sql"})
@Sql(scripts = {"/db/clean-db-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@EnabledIfSystemProperty(named = "test.api.enabled", matches = "true")
public class RestApiTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login() throws Exception {
        var userBO = UserBO.builder().username("jaggerwang").password("123456").build();
        mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userBO))).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("ok"))
                .andExpect(jsonPath("$.data.user.username").value(userBO.getUsername()));
    }

    @WithUserDetails("jaggerwang")
    @Test
    void logout() throws Exception {
        mvc.perform(get("/auth/logout")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("ok"))
                .andExpect(jsonPath("$.data.user.username").value("jaggerwang"));
    }

    @WithUserDetails("jaggerwang")
    @Test
    void logged() throws Exception {
        mvc.perform(get("/auth/logged")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("ok"))
                .andExpect(jsonPath("$.data.user.username").value("jaggerwang"));
    }

    @Test
    void register() throws Exception {
        var userBO = UserBO.builder().username("jagger001").password("123456").build();
        mvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userBO))).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("ok"))
                .andExpect(jsonPath("$.data.user.username").value(userBO.getUsername()));
    }

    @WithUserDetails("jaggerwang")
    @Test
    void info() throws Exception {
        mvc.perform(get("/user/info").param("id", "1")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("ok"))
                .andExpect(jsonPath("$.data.user.id").value(1));
    }
}

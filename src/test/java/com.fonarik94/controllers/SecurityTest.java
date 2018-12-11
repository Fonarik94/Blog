package com.fonarik94.controllers;

import com.fonarik94.domain.Post;
import com.fonarik94.domain.Roles;
import com.fonarik94.domain.User;
import com.fonarik94.repo.PostRepository;
import com.fonarik94.repo.UserRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class SecurityTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostRepository postRepository;

    private static String encryptedPassword = new BCryptPasswordEncoder().encode("admin");
    private static User admin = User
            .builder()
            .username("admin")
            .password(encryptedPassword)
            .email("test@example.com")
            .authority(Roles.ADMIN)
            .accountNonExpired(true)
            .accountNonLocked(true)
            .credentialsNonExpired(true)
            .enabled(true)
            .build();
    private static boolean initialized = false;

    @Before
    public void setUp() {
        if (!initialized) {
            initialized = true;
            userRepo.save(admin);
            postRepository.save(new Post("Header", "Text"));
        }
    }

    @Test
    public void noAuthorizeEndpointAdminTest() throws Exception {
        this.mockMvc
                .perform(get("/administration"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void noAuthorizeEndpointWriterTest() throws Exception {
        this.mockMvc
                .perform(get("/administration/postwriter"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void noAuthorizeEndpointAddNewPostTest() throws Exception {
        this.mockMvc
                .perform(get("/administration/postwriter/addpost"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void noAuthorizeEndpointEditTest() throws Exception {
        this.mockMvc
                .perform(get("/administration/postwriter/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    /*Authorized tests*/
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void AuthorizeEndpointWriterTest() throws Exception {
        this.mockMvc
                .perform(get("/administration/postwriter"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Header")))
                .andExpect(content().string(containsString("Удалить")))
                .andExpect(content().string(containsString("Редактировать")));
    }

    @Test
    public void correctLoginFormTest() throws Exception {
        this.mockMvc
                .perform(formLogin()
                        .user("admin")
                        .password("admin"))
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }

    @Test
    public void invalidLoginFormTest() throws Exception {
        this.mockMvc
                .perform(formLogin()
                        .user("user")
                        .password("user"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    public void logoutTest() throws Exception {
        this.mockMvc
                .perform(post("/logout").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}


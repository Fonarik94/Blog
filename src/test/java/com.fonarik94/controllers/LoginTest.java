package com.fonarik94.controllers;

import com.fonarik94.domain.Roles;
import com.fonarik94.domain.User;
import com.fonarik94.repo.UserRepo;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class LoginTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;

    private static User admin = User
            .builder()
            .username("admin")
            .password(new BCryptPasswordEncoder().encode("admin"))
            .email("test@example.com")
            .authority(Roles.ADMIN)
            .accountNonExpired(true)
            .accountNonLocked(true)
            .credentialsNonExpired(true)
            .enabled(true)
            .build();

    private static boolean initialized = false;

    @Before
    public void setUp(){
        if(!initialized){
            initialized = true;
            userRepo.save(admin);
        }
    }

    @Test
    public void correctLoginTest() throws Exception {
        this.mockMvc
                .perform(formLogin()
                        .user("admin")
                        .password("admin"))
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }

    @Test
    public void invalidLoginTest() throws Exception {
        this.mockMvc
                .perform(formLogin()
                            .user("user")
                            .password("user"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }
}

package com.fonarik94.controllers;

import com.fonarik94.domain.Post;
import com.fonarik94.repo.CommentRepository;
import com.fonarik94.repo.PostRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AdministrationControllerTest {
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    MockMvc mockMvc;
    private String header = "Header";
    private String text = "Text";
    private List<Post> postList = Arrays.asList(
            new Post(header, text),
            new Post(header+"1", text+"1"),
            new Post(header+"2", text+"2"));

    private static boolean initialized = false;

    @Before
    public void setUp() {
        if (!initialized) {
            initialized = true;
            postList.forEach(post -> post.setPublished(true));
            postRepository.saveAll(postList);
        }
    }

    @Test
    public void getAllPosts() throws Exception {
        this.mockMvc
                .perform(get("/administration/postwriter"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(header)))
                .andExpect(content().string(containsString(text)))
                .andExpect(content().string(containsString(header+"1")))
                .andExpect(content().string(containsString(text+"1")))
                .andExpect(content().string(containsString(header+"2")))
                .andExpect(content().string(containsString(text+"2")));
    }
}

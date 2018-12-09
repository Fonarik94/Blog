package com.fonarik94.controllers;

import com.fonarik94.domain.Post;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource({"/application-test.properties"})
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class IndexControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepository postRepository;
    private List<Post> posts = Arrays.asList(new Post("Header", "Text")
            , new Post("Header1", "Text1")
            , new Post("Header2", "Text2"));
    private static boolean initialized = false;

    @Before
    public void setUp() {
        if (!initialized) {
        initialized = true;
        posts.forEach(post -> post.setPublished(true));
        posts.forEach(post -> post.setPublicationDate(LocalDateTime.now()));
        postRepository.saveAll(posts);
        }
    }

/*    @After
    public void tearDown(){
        this.postRepository.deleteAll();
    }*/

    @Test
    public void main() throws Exception {
        this.mockMvc
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Header")))
                .andExpect(content().string(containsString("Text")))
                .andExpect(content().string(containsString("Header1")))
                .andExpect(content().string(containsString("Text1")))
                .andExpect(content().string(containsString("Header2")))
                .andExpect(content().string(containsString("Text2")));
    }


    @Test
    public void about() throws Exception {
        this.mockMvc
                .perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Text")))
        ;
    }

    @Test
    public void getPost() throws Exception {
        this.mockMvc
                .perform(get("/post/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Header")))
                .andExpect(content().string(containsString("Text")));
    }

    @Test
    public void getWrongPost() throws Exception {
        this.mockMvc.perform(get("/post/10"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("404")));
    }

    @Test
    public void getWrongPostId() throws Exception {
        this.mockMvc.perform(get("/post/rand"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("404")));
    }

    @Test
    public void getEmptyPostId() throws Exception {
        this.mockMvc.perform(get("/post/"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("404")));
    }


}
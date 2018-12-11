package com.fonarik94.controllers;

import com.fonarik94.domain.Post;
import com.fonarik94.dto.CaptchaResponseDto;
import com.fonarik94.repo.PostRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource({"/application-test.properties"})
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControllersTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepository postRepository;
    @MockBean
    private RestTemplate restTemplate;
    @Mock
    private ResponseEntity<CaptchaResponseDto> responseEntity;
    @Mock
    private CaptchaResponseDto captchaResponseDto;

    private String header = "Header";
    private String text = "Text";
    private List<Post> posts = Arrays.asList(
            new Post(header, text),
            new Post(header + "1", text + "1"),
            new Post(header + "2", text + "2"),
            new Post(header + "3", text + "3"));

    private static boolean initialized = false;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        if (!initialized) {
            initialized = true;
            posts.forEach(post -> post.setPublished(true));
            posts.forEach(post -> post.setPublicationDate(LocalDateTime.now()));
            posts.forEach(post -> System.out.println(post.getPublicationDateAsString()));
            postRepository.saveAll(posts);
        }
    }

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
                .andExpect(status().isNotFound());
//                .andExpect(content().string(containsString("404")));
    }

    @Test
    public void getWrongPostId() throws Exception {
        this.mockMvc.perform(get("/post/rand"))
                .andExpect(status().isNotFound());
//                .andExpect(content().string(containsString("404")));
    }

    @Test
    public void getEmptyPostId() throws Exception {
        this.mockMvc.perform(get("/post"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getAllPosts() throws Exception {
        this.mockMvc
                .perform(get("/administration/postwriter"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(header)))
                .andExpect(content().string(containsString(header + "1")))
                .andExpect(content().string(containsString(header + "2")));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void addPostTest() throws Exception {
        this.mockMvc
                .perform(post("/administration/postwriter/addpost")
                        .param("header", "Header1")
                        .param("text", "Text1")
                        .param("published", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/administration/postwriter"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void editPostByIdPageTest() throws Exception {
        this.mockMvc
                .perform(get("/administration/postwriter/edit")
                        .param("editbyid", "2"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void editPostByIdTest() throws Exception {
        int id = 2;
        this.mockMvc
                .perform(post("/administration/postwriter/edit?editbyid=" + id)
                        .param("header", "goodHeader")
                        .param("text", "goodText")
                        .param("published", "1")
                        .flashAttr("post", postRepository.findById(id))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/administration/postwriter"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void editWrongPostByIdTest() throws Exception {
        int id = 2999;
        this.mockMvc
                .perform(post("/administration/postwriter/edit?editbyid=" + id)
                        .param("header", "goodHeader")
                        .param("text", "goodText")
                        .param("published", "1")
                        .flashAttr("post", new Post())
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void deletePostByIdTest() throws Exception {
        int id = 4;
        this.mockMvc
                .perform(delete("/administration/postwriter/delete/post/" + id).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("deleted")));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void deleteWrongPostByIdTest() throws Exception {
        int id = 4999;
        this.mockMvc
                .perform(delete("/administration/postwriter/delete/post/" + id).with(csrf()))
                .andExpect(status().isNotFound());
//                .andExpect(content().string(containsString("404")));
    }

    @Test
    public void addCommentTest() throws Exception {
        doReturn(captchaResponseDto)
                .when(restTemplate)
                .postForObject(any(String.class), any(List.class), any());
        when(captchaResponseDto.isSuccess()).thenReturn(true);
        assertTrue(captchaResponseDto.isSuccess());
        this.mockMvc
                .perform(post("/post/2")
                        .param("author", "author")
                        .param("text", "text")
                        .param("g-recaptcha-response", "dfgkjhsgnodfg")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/post/2"));
    }

}
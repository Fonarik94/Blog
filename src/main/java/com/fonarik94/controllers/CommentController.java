package com.fonarik94.controllers;

import com.fonarik94.dto.CaptchaResponseDto;
import com.fonarik94.domain.Comment;
import com.fonarik94.domain.Post;
import com.fonarik94.repo.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@Slf4j
public class CommentController {
    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    @Value("${recaptcha.secret}")
    private String captchaSecret;
    private final PostRepository postRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public CommentController(PostRepository postRepository, RestTemplate restTemplate) {
        this.postRepository = postRepository;
        this.restTemplate = restTemplate;
    }

    @PostMapping(value = "/post/{id:[\\d]+}")
    public String addComment(@Valid @ModelAttribute Comment comment,
                             BindingResult bindingResult,
                             @PathVariable int id,
                             RedirectAttributes redirectAttr,
                             @RequestParam("g-recaptcha-response") String captchaResponse){

        String captchaValidationUrl = String.format(CAPTCHA_URL, captchaSecret, captchaResponse);
        CaptchaResponseDto responseDto = restTemplate.postForObject(captchaValidationUrl, Collections.emptyList(), CaptchaResponseDto.class);
        boolean hasAnyErrors = false;
        if(captchaResponse!=null & !responseDto.isSuccess()) {
            redirectAttr.addFlashAttribute("captcha_error", "Заполните капчу");
            hasAnyErrors = true;

        }
        if(bindingResult.hasErrors() || hasAnyErrors){
            redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult." + bindingResult.getObjectName(), bindingResult);
            redirectAttr.addFlashAttribute("comment", comment);

        } else {
            Post post = postRepository.findById(id).get();
            post.addComment(comment);
            postRepository.save(post);
        }
        return "redirect:/post/{id}";
    }

}

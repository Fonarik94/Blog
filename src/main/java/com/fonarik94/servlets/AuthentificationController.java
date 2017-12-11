package com.fonarik94.servlets;

import com.fonarik94.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthentificationController {
    @PostMapping(value = "/register")
    String register(@RequestParam("email")String email,
                          @RequestParam("username") String name,
                          @RequestParam("password") String password){
        User user = new User(name, email, password);
        return "redirect:/";
    }

    @GetMapping(value = {"/login", "/register"})
    public String login(HttpServletRequest request, Model model){
        model.addAttribute("requestedURI", request.getRequestURI());
        return "login";
    }

}

package com.askrmrboffin.reciterproject.controller;

import com.askrmrboffin.reciterproject.model.User;
import com.askrmrboffin.reciterproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    UserService userService;

    @GetMapping
    public String getSignupPage(Model model){
        model.addAttribute("newUser", new User());
        return "signup";
    }

    @PostMapping
    public String registerNewUser(@ModelAttribute("newUser") User user){
        if(user != null){
            Integer userId = userService.createNewUser(user);
            if(userId != null && userId > 0){
                return "redirect:login";
            }
        }

        return "signup";
    }
}

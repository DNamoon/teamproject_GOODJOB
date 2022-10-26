package com.goodjob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String main(){
        return "mainPage";
    }
    @GetMapping("/search")
    public String searchPage(){
        return "/searchPage";
    }

    @GetMapping("/login")
    public String login() { return "login";}

}


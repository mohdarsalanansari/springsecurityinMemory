package com.sde.spbsecurityinmemory.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Maps to src/main/resources/templates/login.html
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin"; // Maps to src/main/resources/templates/admin.html
    }

    @GetMapping("/user")
    public String user() {
        return "user"; // Maps to src/main/resources/templates/user.html
    }
}
package com.sde.spbsecurityinmemory.Controller;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @GetMapping("/home")
    public HashMap<String, String> home() {
        HashMap<String, String>map=new HashMap<>();
        map.put("only_user","127.0.0.1:8080/onlyUser");
        map.put("only_manager","127.0.0.1:8080/onlyManager");
        map.put("only_admin","127.0.0.1:8080/officeFiles");
        map.put("admin_manager","127.0.0.1:8080/configuration");
        map.put("admin_manager_user","127.0.0.1:8080/service");
        map.put("login","127.0.0.1:8080/login");
        map.put("logout","127.0.0.1:8080/logout");
        return map;
    }

    @GetMapping("/onlyUser")
    public String onlyUser() {
        return "Only User API hit successfully"; // Maps to src/main/resources/templates/admin.html
    }
     @GetMapping("/onlyManager")
    public String onlyManager() {
        return "Only Manager API hit successfully"; // Maps to src/main/resources/templates/admin.html
    }

     @GetMapping("/officeFiles")
    public String onlyAdmin() {
        return "Only Admin API hit successfully"; // Maps to src/main/resources/templates/admin.html
    }

     @GetMapping("/configuration")
    public String adminManager() {
        return "Admin &  Manager API hit successfully"; // Maps to src/main/resources/templates/admin.html
    }

     @GetMapping("/service")
    public String adminManagerUser() {
        return "Admin, Manager & User API hit successfully"; // Maps to src/main/resources/templates/admin.html
    }
}
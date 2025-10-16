package co.edu.javeriana.regata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Raíz -> login.html (mockup)
    @GetMapping("/")
    public String home() {
        return "forward:/login.html";
    }
}

package io.github.mat3e.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SSOController {
    @GetMapping("/logout")
    String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "index";  //TODO powinno przekierowywać na stronę startową a ponownie prosi o zalogowanie
//    return "redirect:/";
    }
}

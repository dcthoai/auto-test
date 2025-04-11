package vn.softdream.autotest.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeResource {

    @GetMapping("/")
    public String welcome() {
        return "<h1 style=\"width: 400px; margin: 0 auto; margin-top: 45vh;\">Welcome to DCT Auto Tests</h1>";
    }
}

package codesync.ticketsystem.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/")
public class DemoController {
    @PostMapping("demo")
    public String welcome(){
        return "Welcome from secure endpoint";
    }
}
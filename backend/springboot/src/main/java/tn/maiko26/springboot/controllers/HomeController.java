package tn.maiko26.springboot.controllers;

import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> greeting() {
        return Collections.singletonMap("message", "Hello, World !!");
    }
    @GetMapping("/hello")
    public Map<String,Object> hello(){
        return Collections.singletonMap("message","Hello, World AGAIN yoo xqo xq  x dq !!");
    }
}

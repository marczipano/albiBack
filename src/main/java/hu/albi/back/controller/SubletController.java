package hu.albi.back.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubletController {

    @RequestMapping("/hello")
    public String sayHello(){
        return "Hello World";
    }
}

package Triping.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin
@RequestMapping(path="/")
public class IndexController {
    @GetMapping
    public @ResponseBody String index(HttpSession session){
        return "Home";
    }
}

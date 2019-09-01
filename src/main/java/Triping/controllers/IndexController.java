package Triping.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping(path="/index")
public class IndexController {
    @GetMapping
    public @ResponseBody String index(){
        return "hola amigops";
    }
}

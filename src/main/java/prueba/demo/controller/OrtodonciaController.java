package prueba.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping ("/servicios")
@Controller
public class OrtodonciaController {
    @GetMapping("/ortodoncia")
    public String ortodoncia(){
        return "/servicios/ortodoncia";
    }
}

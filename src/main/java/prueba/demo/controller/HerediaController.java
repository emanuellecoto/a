
package prueba.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/consultorio")
@Controller
public class HerediaController {
    @GetMapping("/ProvinciaHeredia")
    public String consultorio(){
        return "/consultorio/ProvinciaHeredia";
    }
    
}

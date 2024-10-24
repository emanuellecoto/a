
package prueba.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/consultorio")
@Controller
public class LindoraController {
    @GetMapping("/ProvinciaLindora")
    public String consultorio(){
        return "/consultorio/ProvinciaLindora";
    }
    
}


package prueba.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


    @RequestMapping("/inicioSesion")
@Controller
public class inicioSesionContoroller { 
        @GetMapping("/inicioSesion")

    public String inicioSesion(){
        return "/inicioSesion/inicioSesion";
    }
}


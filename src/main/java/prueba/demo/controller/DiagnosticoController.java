
package prueba.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/cita")
@Controller
public class DiagnosticoController {
    
    @GetMapping("/diagnostico")
    public String cita(){
        return "/cita/diagnostico";
    }
    
}

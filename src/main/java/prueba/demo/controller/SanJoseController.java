
package prueba.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/consultorios")
@Controller
public class SanJoseController {
    @GetMapping("/ProvinciaSanJose")
    
    public String consultorio(){
        return "/consultorios/ProvinciaSanJose";
    }
    
}


package prueba.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/proveedores")
@Controller
public class ProveedoresController {
    @GetMapping("/proveedores")
    public String consultorio(){
        return "/proveedores/proveedores";
    }
    
}

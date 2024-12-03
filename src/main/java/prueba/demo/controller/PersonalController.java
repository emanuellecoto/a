/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import prueba.demo.service.UsuarioEmpleadoService;

@RequestMapping("/empleados")
@Controller
public class PersonalController {
    
    @Autowired UsuarioEmpleadoService usuarioEmpleado;
    
    @GetMapping("/personal")
    public String empleados(Model model) {

        var lista = usuarioEmpleado.getUser();  


        model.addAttribute("listar", lista);


        return "/empleados/personal";
    }
    
    
    
    
}

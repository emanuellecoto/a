/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author baflo
 */
@RequestMapping("/adminUsuarios")
@Controller
public class adminUsuariosController {
    @GetMapping("/usuarios")
   public String usuarios(){
       return "/adminUsuarios/usuarios";
   } 
}

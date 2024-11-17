/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import prueba.demo.dao.UsuarioDao;
import prueba.demo.domain.Usuario;
import prueba.demo.service.UsuarioService;

/**
 *
 * @author baflo
 */
@RequestMapping("/adminUsuarios")
@Controller
public class adminUsuariosController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public String usuarios() {
        return "/adminUsuarios/usuarios";
    }

    @GetMapping("/lista")
    public String lista(Model model) {
        var lista = usuarioService.getUser();

        model.addAttribute("listado", lista);

        return "/adminUsuarios/lista";
    }







}

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
@RequestMapping ("/contactenos")
@Controller
public class ContactenosController {
    @GetMapping ("/contactenos")
    public String contactenos(){
        return "/contactenos/contactenos";
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.demo.controller;

import java.sql.CallableStatement;
import java.sql.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import prueba.demo.service.UsuarioEmpleadoService;

@RequestMapping("/empleados")
@Controller
public class PersonalController {
    
    @Autowired 
    UsuarioEmpleadoService usuarioEmpleado;
    
   @Autowired
   JdbcTemplate jdbctemplate;
    
    
    @GetMapping("/personal")
    public String empleados(Model model) {

        var lista = usuarioEmpleado.getUser();  


        model.addAttribute("listar", lista);


        return "/empleados/personal";
    }
    
    @PostMapping("/agregar")
    public String AgregarSalario(@RequestParam("idUser") Long idUser, @RequestParam("Salario") int Salario, Model model){
        try {
            
            String functionAgregarSalario = "{? = call FIDE_CLINICA_DENTAL_FUNCTIONS_PKG.SALARIO_AGREGAR_SALARIO_FN(?, ?)}";
                  String querySalarioExistente = "SELECT FIDE_CLINICA_DENTAL_FUNCTIONS_PKG.SALARIO_VALIDAR_SALARIO_EXISTENTE_FN(?) FROM DUAL";
                Long salarioExiste = jdbctemplate.queryForObject(querySalarioExistente, Long.class, idUser);
            
   jdbctemplate.execute(functionAgregarSalario,  (CallableStatement cs) -> {
               cs.registerOutParameter(1, Types.INTEGER);
                cs.setLong(2, idUser);
                 cs.setInt(3, Salario);
                cs.execute();
                return cs.getInt(1);
            });
     
    if (salarioExiste == 0) {
                var lista = usuarioEmpleado.getUser();  
        model.addAttribute("listar", lista);
    model.addAttribute("cambio", "se actualizo el salario correctamente");
} else {
                var lista = usuarioEmpleado.getUser();  
        model.addAttribute("listar", lista);
      model.addAttribute("cambio", "no se encontro usuario");
}
            
        } catch (Exception e) {
            model.addAttribute("cambio", e);
        }
        
        
        return"/empleados/personal";
    }
    
    
    
    
}

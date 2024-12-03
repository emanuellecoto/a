/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.demo.controller;

import jakarta.servlet.http.HttpSession;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import prueba.demo.dao.UsuarioDao;
import prueba.demo.domain.Usuario;
import prueba.demo.service.UsuarioService;

/**
 *
 * @author baflo
 */
@Controller
public class adminUsuariosController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    JdbcTemplate jdbctemplate;

    @GetMapping("/adminUsuarios/usuarios")
    public String usuarios() {
        return "/adminUsuarios/usuarios";
    }

    @GetMapping("/adminUsuarios/lista")
    public String lista(Model model) {
        var lista = usuarioService.getUser();

        model.addAttribute("listado", lista);

        return "/adminUsuarios/lista";
    }

    @PostMapping("/adminUsuarios/lista")
    public String cambiarEstado(@RequestParam("idUser") Long idUser, Model model) {

        try {
            String functionEstado = "{? = call FIDE_CLINICA_DENTAL_FUNCTIONS_PKG.ESTADO_RETORNAR_ESTADO_FN(?)}";

            jdbctemplate.execute(functionEstado, (CallableStatement cs) -> {
                cs.registerOutParameter(1, Types.VARCHAR);
                cs.setLong(2, idUser);
                cs.execute();
                return cs.getString(1);
            });

            var lista = usuarioService.getUser();

            model.addAttribute("listado", lista);

        } catch (Exception e) {
            model.addAttribute("estado", "Error al cambiar el estado del usuario: " + e.getMessage());
        }

        return "/adminUsuarios/lista";
    }

    @PostMapping("/adminUsuario/cambioRol")
    public String cambioRol(@RequestParam("idUsuario") Long IdUsuario, @RequestParam("rolId") Long rolId,  Model model) {

        try {
            String ProcedureRole = "{call FIDE_CLINICA_DENTAL_PROCEDURES_PKG.USUARIO_ASIGNAR_OTRO_ROLE_SP(?, ?)}";
            jdbctemplate.update(ProcedureRole, IdUsuario, rolId);
                             String ProcedureUpdate = "{call FIDE_CLINICA_DENTAL_PROCEDURES_PKG.USUARIO_OBTENER_UPDATE_ID_USUARIO_SP(?, ?)}";
                  jdbctemplate.update(ProcedureUpdate, IdUsuario, rolId);
            model.addAttribute("cambio", "Cambio  realizado con exito");
            if (rolId == 2) {
                   String ProcedureAdmin = "{call FIDE_CLINICA_DENTAL_PROCEDURES_PKG.USUARIO_OBTENER_ID_USUARIO_SP(?)}";
                   jdbctemplate.update(ProcedureAdmin, IdUsuario);
                   
            }

                  
      
            

        } catch (Exception e) {
            model.addAttribute("cambio", "ocurrio un error");
        }
        
        return "/adminUsuarios/usuarios";

    }
    
@PostMapping("/adminUsuario/cambioUsuario")
public String cambioUsuario(
        @RequestParam("idUsuario") Long idUsuario,
        @RequestParam Map<String, String> allParams,
        Model model) {

    try {
        // Filtrar solo los parámetros relevantes (excluir idUsuario)
        Map<String, String> camposAActualizar = allParams.entrySet().stream()
                .filter(entry -> !"idUsuario".equals(entry.getKey()) && entry.getValue() != null && !entry.getValue().trim().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Verificar si hay campos para actualizar
        if (camposAActualizar.isEmpty()) {
            model.addAttribute("cambio", "No se proporcionó información válida para actualizar.");
            return "/adminUsuarios/usuarios";
        }

        // Llamar al procedimiento adecuado según los campos presentes
        if (camposAActualizar.size() == 3) {
            // Actualizar todos los campos
            String procedureTodo = "{call FIDE_CLINICA_DENTAL_PROCEDURES_PKG.USUARIO_EDITAR_TODO_SP(?,?,?,?)}";
            jdbctemplate.update(procedureTodo, idUsuario, 
                                camposAActualizar.get("nombre"), 
                                camposAActualizar.get("apellido"), 
                                camposAActualizar.get("sapellido"));

        } else {
            // Actualizar campos individuales
            camposAActualizar.forEach((campo, valor) -> {
                String procedure = obtenerProcedureParaCampo(campo);
                jdbctemplate.update(procedure, idUsuario, valor);
            });
        }

        model.addAttribute("cambio", "Usuario actualizado correctamente.");

    } catch (Exception e) {
        model.addAttribute("cambio", "Error al actualizar el usuario: " + e.getMessage());
    }

    return "/adminUsuarios/usuarios";
}

private String obtenerProcedureParaCampo(String campo) {
    switch (campo) {
        case "nombre":
            return "{call FIDE_CLINICA_DENTAL_PROCEDURES_PKG.USUARIO_EDITAR_NOMBRE_SP(?,?)}";
        case "apellido":
            return "{call FIDE_CLINICA_DENTAL_PROCEDURES_PKG.USUARIO_EDITAR_PRIMER_APELLIDO_SP(?,?)}";
        case "sapellido":
            return "{call FIDE_CLINICA_DENTAL_PROCEDURES_PKG.USUARIO_EDITAR_SEGUNDO_APELLIDO_SP(?,?)}";
        default:
            throw new IllegalArgumentException("Campo no soportado: " + campo);
    }
}


    
    
}

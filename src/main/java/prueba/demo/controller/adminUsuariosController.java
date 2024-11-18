/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.demo.controller;

import jakarta.servlet.http.HttpSession;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
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
    public String cambioRol(@RequestParam("idUsuario") Long IdUsuario, @RequestParam("rolId") Long rolId, Model model) {

        try {
            String ProcedureRole = "{call FIDE_CLINICA_DENTAL_PROCEDURES_PKG.USUARIO_ASIGNAR_OTRO_ROLE_SP(?, ?)}";
            jdbctemplate.update(ProcedureRole, IdUsuario, rolId);
            model.addAttribute("cambio", "Cambio de rol realizado con exito");
        } catch (Exception e) {
            model.addAttribute("cambio", "ocurrio un error");
        }
        
        return "/adminUsuarios/usuarios";

    }
}

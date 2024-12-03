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
import org.springframework.web.bind.annotation.PathVariable;
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
public String cambioRol(@RequestParam("idUsuario") Long idUsuario, @RequestParam("rolId") Long rolId, Model model) {

    try {
        // Validar si el usuario existe
        String queryUsuarioExistente = "SELECT FIDE_CLINICA_DENTAL_FUNCTIONS_PKG.USUARIO_VALIDAR_USUARIO_EXISTENTE_FN(?) FROM DUAL";
        Long usuarioExiste = jdbctemplate.queryForObject(queryUsuarioExistente, Long.class, idUsuario);

        if (usuarioExiste == 1) {

            String procedureAsignarRol = "{call FIDE_CLINICA_DENTAL_PROCEDURES_PKG.USUARIO_ASIGNAR_OTRO_ROLE_SP(?, ?)}";
            jdbctemplate.update(procedureAsignarRol, idUsuario, rolId);

            if (rolId == 2 || rolId == 3 || rolId == 4) { 
      
                String querySalarioExistente = "SELECT FIDE_CLINICA_DENTAL_FUNCTIONS_PKG.SALARIO_VALIDAR_SALARIO_EXISTENTE_FN(?) FROM DUAL";
                Long salarioExiste = jdbctemplate.queryForObject(querySalarioExistente, Long.class, idUsuario);

                if (salarioExiste == 1) {
   
                    String procedureInsertarSalario = "{call FIDE_CLINICA_DENTAL_PROCEDURES_PKG.USUARIO_OBTENER_ID_USUARIO_SP(?)}";
                    jdbctemplate.update(procedureInsertarSalario, idUsuario);
                    model.addAttribute("cambio", "Registro creado exitosamente.");
                } else {
          
                    String procedureActualizarSalario = "{call FIDE_CLINICA_DENTAL_PROCEDURES_PKG.USUARIO_OBTENER_UPDATE_ID_USUARIO_SP(?, ?)}";
                    jdbctemplate.update(procedureActualizarSalario, idUsuario, rolId);
                    model.addAttribute("cambio", "Estado actualizado a 'Activo'.");
                }
            } else {
    
                String procedureActualizarEstado = "{call FIDE_CLINICA_DENTAL_PROCEDURES_PKG.USUARIO_OBTENER_UPDATE_ID_USUARIO_SP(?, ?)}";
                jdbctemplate.update(procedureActualizarEstado, idUsuario, rolId);
                model.addAttribute("cambio", "Estado actualizado a 'Inactivo'.");
            }

        } else {
            // El usuario no existe
            model.addAttribute("cambio", "El usuario no existe.");
        }

    } catch (Exception e) {
        // Manejo de errores
        model.addAttribute("cambio", "Error: " + e.getMessage());
    }

    return "/adminUsuarios/usuarios";
}


    @PostMapping("/adminUsuario/cambioUsuario")
    public String cambioUsuario(
            @RequestParam("idUsuario") Long idUsuario,
            @RequestParam Map<String, String> allParams,
            Model model) {

        try {
            String query = "SELECT FIDE_CLINICA_DENTAL_FUNCTIONS_PKG.USUARIO_VALIDAR_USUARIO_EXISTENTE_FN(?) FROM DUAL";
            Map<String, String> camposAActualizar = allParams.entrySet().stream()
                    .filter(entry -> !"idUsuario".equals(entry.getKey()) && entry.getValue() != null && !entry.getValue().trim().isEmpty())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            if (camposAActualizar.isEmpty()) {
                model.addAttribute("cambio", "No se proporcionó información válida para actualizar.");
                return "/adminUsuarios/usuarios";
            }
            Long existe = jdbctemplate.queryForObject(query, Long.class, idUsuario);

            if (existe == 1) {
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
            } else {
                model.addAttribute("cambio", "El Usuario no existe.");
            }

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

    public String obtenerUsuarioPorId(int idUsuario) {
        String sql = "BEGIN ? := FIDE_CLINICA_DENTAL_FUNCTIONS_PKG.OBTENER_USUARIO_FN(?); END;";
        return jdbctemplate.execute((Connection connection) -> {
            try (CallableStatement callableStatement = connection.prepareCall(sql)) {
                callableStatement.registerOutParameter(1, Types.VARCHAR);
                callableStatement.setInt(2, idUsuario);
                callableStatement.execute();
                return callableStatement.getString(1);
            }
        });
    }

    @GetMapping("/adminUsuarios/id")
    public String obtenerUsuario(@RequestParam(value = "idUser", required = true) Integer idUsuario, Model model) {
        if (idUsuario == null) {
            model.addAttribute("error", "Debe ingresar un ID de usuario válido.");
            return "/adminUsuarios/lista";
        }

        var usuarioInfo = obtenerUsuarioPorId(idUsuario);
        if (usuarioInfo == null || usuarioInfo.equals("Usuario no encontrado.")) {
            var lista = usuarioService.getUser();

            model.addAttribute("listado", lista);
            model.addAttribute("error", "Usuario no encontrado.");
        } else {

            model.addAttribute("usuarioInfo", usuarioInfo);
        }

        return "/adminUsuarios/lista";
    }
}

package prueba.demo.controller;

import jakarta.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Types;
import java.util.Map;
import static org.apache.tomcat.util.security.ConcurrentMessageDigest.digest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import java.sql.CallableStatement;
import java.util.HashMap;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class inicioSesionController {

    @Autowired
    JdbcTemplate jdbctemplate;

    @GetMapping("/login")
    public String inicioSesion() {
        return "inicioSesion";
    }

    @PostMapping("/loginUser")
    public String iniciarSesion(@RequestParam String correo,
            @RequestParam String contra,
            Model model,
            HttpSession session) {
        try {
            // Generar el hash de la contraseña en formato RAW (SHA-256)
            byte[] contraHash = MessageDigest.getInstance("SHA-256").digest(contra.getBytes(StandardCharsets.UTF_8));

            // Crear el SimpleJdbcCall para el procedimiento almacenado
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbctemplate)
                    .withCatalogName("FIDE_CLINICA_DENTAL_PROCEDURES_PKG") // Nombre del paquete
                    .withProcedureName("USUARIO_VALIDAR_USUARIO_SP") // Nombre del procedimiento
                    .declareParameters(
                            new SqlParameter("V_EMAIL", Types.VARCHAR),
                            new SqlParameter("V_CONTRASENA", Types.BINARY), // RAW es representado por BINARY en JDBC
                            new SqlOutParameter("V_RESULTADO", Types.INTEGER)
                    );

            // Parámetros para la llamada al procedimiento
            Map<String, Object> inParams = new HashMap<>();
            inParams.put("V_EMAIL", correo);
            inParams.put("V_CONTRASENA", contraHash);

            // Ejecutar la llamada al procedimiento y obtener el resultado
            Map<String, Object> outParams = jdbcCall.execute(inParams);

            // Obtener el parámetro de salida V_RESULTADO
            Integer resultado = (Integer) outParams.get("V_RESULTADO");

            String obtenerNombre = "SELECT NOMBRE FROM FIDE_USUARIO_TB WHERE EMAIL = ?";
            String obtenerEstado = "SELECT ID_ESTADO FROM FIDE_USUARIO_TB WHERE EMAIL = ?";
            String obtenerRole = "SELECT ID_ROLE FROM FIDE_USUARIO_TB WHERE EMAIL = ?";

            String nombreAlmacenado = jdbctemplate.queryForObject(obtenerNombre, String.class, correo);
            String estadoAlmacenado = jdbctemplate.queryForObject(obtenerEstado, String.class, correo);
            Long obtenerRoleAlmacenado = jdbctemplate.queryForObject(obtenerRole, Long.class, correo);

            if (estadoAlmacenado.equalsIgnoreCase("I")) {
                model.addAttribute("message", "Cuenta inactiva, comuníquese con un asesor.");

                return "inicioSesion";
            }

            // Verificar el resultado de la validación
            if (resultado != null && resultado == 1) {
                model.addAttribute("message", "Inicio de sesión exitoso");
                session.setAttribute("usuarioLogueado", nombreAlmacenado);
                session.setAttribute("rol", obtenerRoleAlmacenado);
                return "/index"; // Página de inicio
            } else {
                model.addAttribute("message", "Correo o contraseña incorrectos");
                return "/inicioSesion"; // Página de inicio de sesión
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error al iniciar sesión");
            return "/inicioSesion";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "inicioSesion";
    }
}

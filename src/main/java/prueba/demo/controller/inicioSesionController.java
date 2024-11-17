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


    private String generarHash(String contrasena) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(contrasena.getBytes(StandardCharsets.UTF_8));
            // Convertir el byte[] a una cadena hexadecima
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;  // En caso de error, devolver null
        }
    }

@PostMapping("/loginUser")
public String loginUsuario(@RequestParam String correo,
                           @RequestParam String contra,
                           Model model,
                           HttpSession session) {
    try {
        // Generar el hash de la contraseña ingresada
        String hashedPassword = generarHash(contra);

        // Verificar si el correo existe
        String verificarEmail = "SELECT COUNT(*) FROM FIDE_USUARIO_TB WHERE EMAIL = ?";
        int contador = jdbctemplate.queryForObject(verificarEmail, Integer.class, correo);

        if (contador == 0) {
            model.addAttribute("message", "Usuario no encontrado.");
            return "inicioSesion";
        }

        // Recuperar el hash de la contraseña, nombre y estado del usuario
        String obtenerContrasenaHash = "SELECT CONTRASEÑA FROM FIDE_USUARIO_TB WHERE EMAIL = ?";
        String obtenerNombre = "SELECT NOMBRE FROM FIDE_USUARIO_TB WHERE EMAIL = ?";
        String obtenerEstado = "SELECT ID_ESTADO FROM FIDE_USUARIO_TB WHERE EMAIL = ?";
        String obtenerRole  = "SELECT ID_ROLE FROM FIDE_USUARIO_TB WHERE EMAIL = ?";
                
        String contrasenaAlmacenada = jdbctemplate.queryForObject(obtenerContrasenaHash, String.class, correo);
        String nombreAlmacenado = jdbctemplate.queryForObject(obtenerNombre, String.class, correo);
        String estadoAlmacenado = jdbctemplate.queryForObject(obtenerEstado, String.class, correo);
        Long obtenerRoleAlmacenado = jdbctemplate.queryForObject(obtenerRole, Long.class, correo);


        if (estadoAlmacenado.equalsIgnoreCase("I")) {
            model.addAttribute("message", "Cuenta inactiva, comuníquese con un asesor.");
            return "inicioSesion";
        }

        // Comparar los hashes de la contraseña
        if (hashedPassword.equals(contrasenaAlmacenada)) {
           
            session.setAttribute("usuarioLogueado", nombreAlmacenado);
            session.setAttribute("rol", obtenerRoleAlmacenado);
          
            return "index"; // Redirigir al inicio si el login es exitoso
        } else {
            model.addAttribute("message", "Contraseña incorrecta.");
            return "inicioSesion";
        }

    } catch (Exception e) {
        e.printStackTrace();
        model.addAttribute("message", "Error al procesar login.");
        return "inicioSesion";
    }
}


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida la sesión actual
        return "inicioSesion"; // Redirige a la página de inicio de sesión
    }

}

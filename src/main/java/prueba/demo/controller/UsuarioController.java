/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.demo.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import prueba.demo.domain.Usuario;

@Controller
public class UsuarioController {

    @Autowired
    private JdbcTemplate jdbctemplate;


 private String generarHash(String contrasena) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(contrasena.getBytes(StandardCharsets.UTF_8));
            // Convertir el byte[] a una cadena hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;  
        }
    }

    @PostMapping("/regUser")
    public String registrarUsuario(@RequestParam String nombre,
                                   @RequestParam String Papellido,
                                   @RequestParam String Sapellido,
                                   @RequestParam String correo,
                                   @RequestParam Long telefono,
                                   @RequestParam String contra,
                                   Model model) {
        try {
            // Verificar si el correo ya existe en la base de datos
            String verificarEmail = "SELECT COUNT(*) FROM FIDE_USUARIO_TB WHERE EMAIL = ?";
            int contador = jdbctemplate.queryForObject(verificarEmail, Integer.class, correo);

            if (contador > 0) {
                model.addAttribute("correo", "El correo ya existe. Intentelo de nuevo");
                return "/registro";
            } else {
                // Generar el hash de la contraseña
                String contrasenaHash = generarHash(contra);

                // Si el hash es null, no proceder
                if (contrasenaHash == null) {
                    model.addAttribute("message", "Error al generar el hash de la contraseña");
                    return "/registro";
                }
                
                
                

                // Llamar al procedimiento almacenado para registrar al usuario
                String procedureReg = "{call FIDE_CLINICA_DENTAL_PROCEDURES_PKG.USUARIO_OBTENER_REGISTRO_SP(?, ?, ?, ?, ?, ?)}";
                jdbctemplate.update(procedureReg, nombre, Papellido, Sapellido, correo, telefono, contrasenaHash);


                model.addAttribute("message", "Usuario registrado exitosamente. Procede a loguearte");
                return "/inicioSesion";
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error al registrar usuario");
            System.out.println(e.getMessage());
            return "/inicioSesion";
        }
    }
    

    
   
}


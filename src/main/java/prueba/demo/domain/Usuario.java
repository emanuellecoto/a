/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "FIDE_USUARIO_TB")
@SequenceGenerator(name = "FIDE_USUARIO_SQ", sequenceName = "FIDE_USUARIO_SQ", allocationSize = 1)
public class Usuario {

    @Id
    @Column(name = "ID_USUARIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FIDE_USUARIO_SQ")
    private Long IdUsuario;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "PRIMER_APELLIDO")
    private String primerApellido;
    @Column(name = "SEGUNDO_APELLIDO")
    private String segundoApellido;
    @Column(name = "EMAIL", unique = true)
    private String email;
    @Column(name = "TELEFONO")
    private Long telefono;
    @Column(name = "ID_ROLE")
    private Long idRole;
    @Column(name = "ID_ESTADO")
    private String idEstado;
    @Column(name = "CONTRASEÑA")
    private byte[] contraseña;


    
    
}  

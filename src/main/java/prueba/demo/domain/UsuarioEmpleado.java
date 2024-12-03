/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.demo.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;



@Data
@Entity
@Table(name = "FIDE_USUARIOS_EMPLEADOS_VM")
public class UsuarioEmpleado {

    @Id
    @Column(name = "ID") 
    private Long id;

    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "P_APELLIDO")
    private String primerApellido;

    @Column(name = "SE_APELLIDO")
    private String segundoApellido;

    @Column(name = "ROL")
    private String rol;
    
    @Column(name = "SALARIO")
    private String salario;

    @Column(name = "ESTADO")
    private String estado;
}


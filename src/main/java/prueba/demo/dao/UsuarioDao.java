/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package prueba.demo.dao;


import org.springframework.data.jpa.repository.JpaRepository;


import prueba.demo.domain.Usuario;

/**
 *
 * @author emanu
 */
public interface UsuarioDao extends JpaRepository<Usuario, Long>{
    
   
    
}

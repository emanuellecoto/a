/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.demo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prueba.demo.dao.UsuarioDao;
import prueba.demo.domain.Usuario;
import prueba.demo.service.UsuarioService;

/**
 *
 * @author emanu
 */

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    UsuarioDao usuarioDao;
    
    @Override
    public List<Usuario> getUser() {
        return usuarioDao.findAll();
    }
    
   
    
}

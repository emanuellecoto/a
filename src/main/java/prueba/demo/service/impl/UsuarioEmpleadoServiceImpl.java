/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.demo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prueba.demo.dao.UsuarioEmpleadoDao;
import prueba.demo.domain.UsuarioEmpleado;

import prueba.demo.service.UsuarioEmpleadoService;

@Service
public class UsuarioEmpleadoServiceImpl implements UsuarioEmpleadoService{
    
    
    @Autowired UsuarioEmpleadoDao usuarioEmpleadoDao;

    @Override
    public List<UsuarioEmpleado> getUser() {
        return usuarioEmpleadoDao.findAll();
    }
    
    
    
}

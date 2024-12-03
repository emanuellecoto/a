/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba.demo.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
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
    
         @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<Usuario> getUser() {
        return usuarioDao.findAll();
    }
    
    

@Override
public List<Object[]> buscarUsuarioPorId(Long idUsuario) {
    // Crear una consulta para el procedimiento almacenado
    StoredProcedureQuery query = entityManager
        .createStoredProcedureQuery("USUARIOS_BUSCAR_USUARIO_SP")
        .registerStoredProcedureParameter("V_ID_USUARIO", Long.class, ParameterMode.IN)
        .registerStoredProcedureParameter("V_CURSOR", void.class, ParameterMode.REF_CURSOR);

    // Establece el valor del parámetro de entrada
    query.setParameter("V_ID_USUARIO", idUsuario);

    // Ejecuta la consulta
    query.execute();

    // Obtiene los resultados del cursor (retorna una lista de Object[])
    List<Object[]> result = query.getResultList();

    return result;
}

    
   
    
}

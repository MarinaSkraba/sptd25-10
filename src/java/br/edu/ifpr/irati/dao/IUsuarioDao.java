package br.edu.ifpr.irati.dao;

import br.edu.ifpr.irati.modelo.Usuario;
import java.util.List;

public interface IUsuarioDao {

    public Usuario verificarUsuario(String user, String senha);
    
    public List<Usuario> buscarUsuariosASeremHabilitados();

}

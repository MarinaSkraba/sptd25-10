package br.edu.ifpr.irati.dao;

import br.edu.ifpr.irati.modelo.Usuario;

public interface IUsuarioDao {

    public Usuario verificarUsuario(String user, String senha);

}

package br.edu.ifpr.irati.dao;

import br.edu.ifpr.irati.modelo.ProjetoPesquisaExtensao;
import java.io.Serializable;
import java.util.List;

public interface IProjetoPesquisaExtensaoDao {

    public List<ProjetoPesquisaExtensao> buscarProjetosExtensaoAtivos();

    public List<ProjetoPesquisaExtensao> buscarProjetosExtensaoPorProfessor(Serializable idUsuario);

}

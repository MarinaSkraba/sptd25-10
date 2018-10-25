package br.edu.ifpr.irati.dao;

import br.edu.ifpr.irati.modelo.Participacao;
import br.edu.ifpr.irati.modelo.Professor;
import br.edu.ifpr.irati.modelo.ProjetoPesquisaExtensao;
import br.edu.ifpr.irati.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class ProjetoPesquisaExtensaoDAO implements IProjetoPesquisaExtensaoDao {

    @Override
    public List<ProjetoPesquisaExtensao> buscarProjetosExtensaoAtivos() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from projetopesquisaextensao";
        Query query = session.createQuery(hql);
        List<ProjetoPesquisaExtensao> results = query.list();
        List<ProjetoPesquisaExtensao> filtrados = new ArrayList<>();
        for (ProjetoPesquisaExtensao pesquisaExtensao : results) {
            if(pesquisaExtensao.getEstadoProjetoPesquisaExtensao().equalsIgnoreCase("Ativo")){
                filtrados.add(pesquisaExtensao);
            }
        }
        session.clear();
        session.close();
        return filtrados;
        
    }

    @Override
    public List<ProjetoPesquisaExtensao> buscarProjetosExtensaoPorProfessor(Serializable idUsuario) {
        int id = (int) idUsuario;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from participacao";
        Query query = session.createQuery(hql);
        List<Participacao> results = query.list();
        List<ProjetoPesquisaExtensao> filtrados = new ArrayList<>();
        for (Participacao participacao : results) {
            if (participacao.getProfessor().getIdUsuario() == id) {
                if(participacao.getRotulo().equalsIgnoreCase("Autor")){
                    filtrados.add(participacao.getProjetoPesquisaExtensao());
                }
            }
        }
        session.clear();
        session.close();
        return filtrados;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpr.irati.dao;

import br.edu.ifpr.irati.modelo.Administracao;
import br.edu.ifpr.irati.modelo.Apoio;
import br.edu.ifpr.irati.modelo.AtividadeASerProposta;
import br.edu.ifpr.irati.modelo.Aula;
import br.edu.ifpr.irati.modelo.Horario;
import br.edu.ifpr.irati.modelo.ManutencaoEnsino;
import br.edu.ifpr.irati.modelo.OutroTipoAtividade;
import br.edu.ifpr.irati.modelo.PTD;
import br.edu.ifpr.irati.modelo.Participacao;
import br.edu.ifpr.irati.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class PTDDAO implements IPTDDAO {

    @Override
    public List<PTD> buscarPTDsConcluidosPorProfessor(Serializable idUsuario) {
        int id = (int) idUsuario;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String estado = "CONCLUÍDO";
        String hql = "from ptd p where p.estadoPTD like '" + estado + "' ";
        Query query = session.createQuery(hql);
        List<PTD> results = query.list();
        List<PTD> filtrados = new ArrayList<>();
        for (PTD ptd : results) {
            if (ptd.getProfessor().getIdUsuario() == id) {
                filtrados.add(ptd);
            }
        }
        session.clear();
        session.close();
        return filtrados;
    }

    @Override
    public List<PTD> buscarPTDsConcluidos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String estado = "CONCLUÍDO";
        String hql = "from ptd p where p.estadoPTD like '" + estado + "' ";
        Query query = session.createQuery(hql);
        List<PTD> results = query.list();
        session.clear();
        session.close();
        return results;
    }

    @Override
    public List<PTD> buscarPTDsAprovadosPorProfessor(Serializable idUsuario) {
        int id = (int) idUsuario;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String estado = "APROVADO";
        String hql = "from ptd p where p.estadoPTD like '" + estado + "' ";
        Query query = session.createQuery(hql);
        List<PTD> results = query.list();
        List<PTD> filtrados = new ArrayList<>();
        for (PTD ptd : results) {
            if (ptd.getProfessor().getIdUsuario() == id) {
                filtrados.add(ptd);
            }
        }
        session.clear();
        session.close();
        return filtrados;
    }

    @Override
    public List<PTD> buscarPTDsEmEdicaoPorProfessor(Serializable idUsuario) {
        int id = (int) idUsuario;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String estado = "EDICAO";
        String hql = "from ptd p where p.estadoPTD like '" + estado + "' ";
        Query query = session.createQuery(hql);
        List<PTD> results = query.list();
        List<PTD> filtrados = new ArrayList<>();
        for (PTD ptd : results) {
            if (ptd.getProfessor().getIdUsuario() == id) {
                filtrados.add(ptd);
            }
        }
        session.clear();
        session.close();
        return filtrados;
    }

    @Override
    public List<PTD> buscarPTDEmAvaliacao() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String estado = "AVALIACAO";
        String hql = "from ptd p where p.estadoPTD like '" + estado + "' ";
        Query query = session.createQuery(hql);
        List<PTD> results = query.list();
        session.clear();
        session.close();
        return results;
    }

    @Override
    public List<PTD> buscarPTDsReprovadosPorProfessor(Serializable idUsuario) {
        int id = (int) idUsuario;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String estado = "REPROVADO";
        String hql = "from ptd p where p.estadoPTD like '" + estado + "' ";
        Query query = session.createQuery(hql);
        List<PTD> results = query.list();
        List<PTD> filtrados = new ArrayList<>();
        for (PTD ptd : results) {
            if (ptd.getProfessor().getIdUsuario() == id) {
                filtrados.add(ptd);
            }
        }
        session.clear();
        session.close();
        return filtrados;
    }

    @Override
    public void excluirPTDEOQueTemDentro(PTD ptd) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.delete(ptd);

        session.getTransaction().commit();
        session.clear();
        session.close();
    }

    @Override
    public List<PTD> buscarPTDsPorNomeDocente(String nomeDocente) {
        String estado = "CONCLUÍDO";
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from ptd p where p.estadoPTD like '" + estado + "' ";
        Query query = session.createQuery(hql);
        List<PTD> results = query.list();
        List<PTD> filtrados = new ArrayList<>();
        for (PTD ptd : results) {
            if (ptd.getProfessor().getNomeCompleto().contains(nomeDocente) == true) {

                filtrados.add(ptd);

            }
        }
        session.clear();
        session.close();
        return filtrados;
    }

    @Override
    public List<PTD> buscarPTDsPorAtividade(String rotuloAtividade) {

        String estado = "CONCLUÍDO";
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from ptd p where p.estadoPTD like '" + estado + "' ";
        Query query = session.createQuery(hql);
        List<PTD> results = query.list();
        List<PTD> filtrados = new ArrayList<>();
        for (PTD ptd : results) {
            for (Administracao a : ptd.getAdministrativas()) {
                if (a.getTipoAdministracao().getRotulo().contains(rotuloAtividade)) {
                    filtrados.add(ptd);
                }
            }
            for (Apoio ap : ptd.getApoios()) {
                if (ap.getTipoApoio().getRotulo().contains(rotuloAtividade)) {
                    filtrados.add(ptd);
                }
            }
            for (AtividadeASerProposta asp : ptd.getAtividadesASeremPropostas()) {
                if (asp.getRotulo().contains(rotuloAtividade)) {
                    if (filtrados.contains(ptd) == false) {
                        filtrados.add(ptd);
                    }
                }
            }
            for (Aula aula : ptd.getAulas()) {
                if (aula.getComponenteCurricular().contains(rotuloAtividade)) {
                    if (filtrados.contains(ptd) == false) {
                        filtrados.add(ptd);
                    }
                }
            }
            for (ManutencaoEnsino mE : ptd.getManutencoesEnsino()) {
                if (mE.getTipoManutencao().getRotulo().contains(rotuloAtividade)) {
                    if (filtrados.contains(ptd) == false) {
                        filtrados.add(ptd);
                    }
                }
            }
            for (OutroTipoAtividade oTa : ptd.getOutrosTiposAtividades()) {
                if (oTa.getRotulo().contains(rotuloAtividade)) {
                    if (filtrados.contains(ptd) == false) {
                        filtrados.add(ptd);
                    }
                }

            }
            for (Participacao p : ptd.getParticipacoes()) {
                if (p.getProjetoPesquisaExtensao().getTituloProcesso().contains(rotuloAtividade)) {
                    if (filtrados.contains(ptd) == false) {
                        filtrados.add(ptd);
                    }
                }

            }
        }

        session.clear();
        session.close();
        return filtrados;

    }
}

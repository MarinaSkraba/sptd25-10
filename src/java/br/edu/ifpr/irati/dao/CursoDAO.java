package br.edu.ifpr.irati.dao;

import br.edu.ifpr.irati.modelo.Curso;
import br.edu.ifpr.irati.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class CursoDAO implements ICursoDAO {

    @Override
    public List<Curso> buscarCursosAtivos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String estado = "Ativo";
        String hql = "from curso c where c.estadoCurso like '"+ estado +"' ";       
        Query query = session.createQuery(hql);
        List<Curso> results = query.list();
        session.clear();
        session.close();
        return results;
    }
    
}

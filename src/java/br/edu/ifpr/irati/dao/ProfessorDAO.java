package br.edu.ifpr.irati.dao;

import br.edu.ifpr.irati.modelo.PTD;
import br.edu.ifpr.irati.modelo.Professor;
import br.edu.ifpr.irati.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class ProfessorDAO implements IProfessorDAO{

    @Override
    public List<Professor> buscarProfessoresASeremHabilitados() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String estado = "AHabilitar";
        String hql = "from usuario u where u.estadoUsuario like '" + estado + "' ";
        Query query = session.createQuery(hql);
        List<Professor> results = query.list();
        session.clear();
        session.close();
        return results;
    }
    
}

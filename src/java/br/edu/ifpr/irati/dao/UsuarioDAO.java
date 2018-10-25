package br.edu.ifpr.irati.dao;

import br.edu.ifpr.irati.modelo.DiretorEnsino;
import br.edu.ifpr.irati.modelo.Professor;
import br.edu.ifpr.irati.modelo.Usuario;
import br.edu.ifpr.irati.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class UsuarioDAO implements IUsuarioDao {

    @Override
    public Usuario verificarUsuario(String email, String senhaAlfanumerica) {
        Usuario u = new Usuario();
        Professor p = new Professor();
        DiretorEnsino de = new DiretorEnsino();
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql01 = "from usuario where email = ? and senhaAlfanumérica = ?";
        Query query = session.createQuery(hql01);
        query.setString(0, email);
        query.setString(1, senhaAlfanumerica);
        List resultados = query.list();
        if (resultados.isEmpty() != true) {
            u = (Usuario) resultados.get(0);
            if(u instanceof Professor){
                p = (Professor) session.load(Professor.class, u.getIdUsuario());
                u = p;
            } else {
                de = (DiretorEnsino) session.load(DiretorEnsino.class, u.getIdUsuario());
                u = de;
            }
        }
        session.clear();
        session.close();
        return u;
    }

}

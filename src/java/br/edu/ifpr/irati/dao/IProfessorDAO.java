package br.edu.ifpr.irati.dao;

import br.edu.ifpr.irati.modelo.Professor;
import java.util.List;

public interface IProfessorDAO {
    
    public List<Professor> buscarProfessoresASeremHabilitados();
    
}

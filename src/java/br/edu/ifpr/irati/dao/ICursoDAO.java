package br.edu.ifpr.irati.dao;

import br.edu.ifpr.irati.modelo.Curso;
import java.util.List;

public interface ICursoDAO {
    
    public List<Curso> buscarCursosAtivos();
    
}

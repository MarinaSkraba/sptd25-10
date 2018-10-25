package br.edu.ifpr.irati.dao;

import br.edu.ifpr.irati.modelo.PTD;
import java.io.Serializable;
import java.util.List;

public interface IPTDDAO {
    
    public List<PTD> buscarPTDsConcluidosPorProfessor(Serializable idUsuario);
    
    public List<PTD> buscarPTDsConcluidos();
    
    public List<PTD> buscarPTDsAprovadosPorProfessor(Serializable idUsuario);
    
    public List<PTD> buscarPTDsEmEdicaoPorProfessor(Serializable idUsuario);
    
    public List<PTD> buscarPTDEmAvaliacao();
    
    public List<PTD> buscarPTDsReprovadosPorProfessor(Serializable idUsuario);
    
    public List<PTD> buscarPTDsPorNomeDocente(String nomeDocente);
    
    public List<PTD> buscarPTDsPorAtividade(String rotuloAtividade);
    
    public void excluirPTDEOQueTemDentro(PTD ptd); 
    
    
}

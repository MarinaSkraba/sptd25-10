package br.edu.ifpr.irati.mb;

import br.edu.ifpr.irati.dao.Dao;
import br.edu.ifpr.irati.dao.GenericDAO;
import br.edu.ifpr.irati.modelo.DiretorEnsino;
import br.edu.ifpr.irati.modelo.PTD;
import br.edu.ifpr.irati.modelo.Usuario;
import br.edu.ifpr.irati.util.Digest;
import br.edu.ifpr.irati.util.HashGenerationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class DiretorEnsinoMB {

    private DiretorEnsino diretorEnsino;
    private DiretorEnsino diretorEnsinoSelecionado;
    private PTD ptd;
    private List<PTD> ptds;
    private List<String> errosEdicaoDiretorEnsino;
    private String confirmacaoSenhaSelecionado;

    public DiretorEnsinoMB() {

        ptd = new PTD();
        Dao<PTD> ptdDAO = new GenericDAO<>(PTD.class);
        ptds = ptdDAO.buscarTodos(PTD.class);
        errosEdicaoDiretorEnsino = new ArrayList<>();
        diretorEnsino = new DiretorEnsino();
        diretorEnsinoSelecionado = new DiretorEnsino();
        confirmacaoSenhaSelecionado = "";

    }

    public void salvarDiretorEnsino() {

        Dao<DiretorEnsino> diretorEnsinoDAO = new GenericDAO<>(DiretorEnsino.class);
        if (getDiretorEnsino().getEmail().contains("@ifpr.edu.br") == true) {
            diretorEnsinoDAO.salvar(getDiretorEnsino());
            setDiretorEnsino(new DiretorEnsino());
        }

    }

    public String verificarPossibilidadeAtualizacaoDiretorEnsino() {

        String nomeCaixaRetorno = "";

        if (getErrosEdicaoDiretorEnsino().isEmpty() == false) {

            nomeCaixaRetorno = "erroEdicaoDiretorEnsinoDialog";
            return nomeCaixaRetorno;

        } else {

            nomeCaixaRetorno = "confirmarEdicaoDiretorEnsinoDialog";
            return nomeCaixaRetorno;
            
        }

    }

    public void alterarDiretorEnsino() throws HashGenerationException {

        String senhaSHA512 = "";
        senhaSHA512 = Digest.hashString(diretorEnsinoSelecionado.getSenhaAlfanumerica(), "SHA-512");
        Dao<Usuario> usuarioDAO = new GenericDAO<>(Usuario.class);
        Usuario u = new Usuario(diretorEnsinoSelecionado.getIdUsuario(), diretorEnsinoSelecionado.getNomeCompleto(), diretorEnsinoSelecionado.getEmail(), diretorEnsinoSelecionado.getImagemPerfil(), senhaSHA512, "Habilitado");
        Dao<DiretorEnsino> diretorEnsinoDAO = new GenericDAO<>(DiretorEnsino.class);
        usuarioDAO.alterar(u);
        diretorEnsinoDAO.alterar(diretorEnsinoSelecionado);
        setDiretorEnsino(new DiretorEnsino());

    }

    public void desabilitarDiretorEnsino() {

        Dao<DiretorEnsino> diretorEnsinoDAO = new GenericDAO<>(DiretorEnsino.class);
        getDiretorEnsino().setEstadoUsuario("Desabilitado");
        diretorEnsinoDAO.alterar(getDiretorEnsino());

    }

    public void aprovarPTD(PTD ptd) {

        Dao<PTD> ptdSubmetidoDAO = new GenericDAO<>(PTD.class);
        ptd.setEstadoPTD("Aprovado");
        ptdSubmetidoDAO.alterar(ptd);

    }

    public void reprovarPTD(PTD ptd) {

        Dao<PTD> ptdSubmetidoDAO = new GenericDAO<>(PTD.class);
        ptd.setEstadoPTD("Reprovado");
        ptdSubmetidoDAO.alterar(ptd);

    }

    /**
     * @return the diretorEnsino
     */
    public DiretorEnsino getDiretorEnsino() {
        return diretorEnsino;
    }

    /**
     * @param diretorEnsino the diretorEnsino to set
     */
    public void setDiretorEnsino(DiretorEnsino diretorEnsino) {
        this.diretorEnsino = diretorEnsino;
    }

    public PTD getPtd() {
        return ptd;
    }

    public void setPtd(PTD ptd) {
        this.ptd = ptd;
    }

    public List<PTD> getPtds() {
        return ptds;
    }

    public void setPtds(List<PTD> ptds) {
        this.ptds = ptds;
    }

    /**
     * @return the errosEdicaoDiretorEnsino
     */
    public List<String> getErrosEdicaoDiretorEnsino() {
        errosEdicaoDiretorEnsino = new ArrayList<>();
        
        if (getDiretorEnsinoSelecionado().getSenhaAlfanumerica().length() < 8 | getDiretorEnsino().getSenhaAlfanumerica().length() > 16) {

            errosEdicaoDiretorEnsino.add("Sua senha deve conter entre  8 a 16 caracteres");

        } else if (getDiretorEnsinoSelecionado().getSenhaAlfanumerica().equals(getConfirmacaoSenhaSelecionado()) == false) {

            errosEdicaoDiretorEnsino.add("As senhas informadas não coincidem");

        }
        Date dataAtual = new Date();
        if (getDiretorEnsinoSelecionado().getDataContratacao().after(dataAtual)) {

            errosEdicaoDiretorEnsino.add("A data que você inseriu como sua data de contratação "
                    + "é posterior a data atual");

        } else if (getDiretorEnsinoSelecionado().getEmail().contains("@ifpr.edu.br") == false) {

            errosEdicaoDiretorEnsino.add("O email deve ser institucional(@ifpr.edu.br)");

        } else if (diretorEnsinoSelecionado.getEmail().equalsIgnoreCase("")) {

            errosEdicaoDiretorEnsino.add("O campo 'Email' deve ser obrigatoriamente preenchido");

        } else if (diretorEnsinoSelecionado.getNomeCompleto().equalsIgnoreCase("")) {

            errosEdicaoDiretorEnsino.add("O campo 'Nome completo' deve ser obrigatoriamente preenchido");

        } else if (diretorEnsinoSelecionado.getSenhaAlfanumerica().equalsIgnoreCase("")) {

            errosEdicaoDiretorEnsino.add("O campo 'Senha' deve ser obrigatoriamente preenchido");

        } else if (confirmacaoSenhaSelecionado.equalsIgnoreCase("")) {

            errosEdicaoDiretorEnsino.add("O campo 'Confirmação senha' deve ser obrigatoriamente preenchido");

        }
        return errosEdicaoDiretorEnsino;
    }

    /**
     * @param errosEdicaoDiretorEnsino the errosEdicaoDiretorEnsino to set
     */
    public void setErrosEdicaoDiretorEnsino(List<String> errosEdicaoDiretorEnsino) {
        this.errosEdicaoDiretorEnsino = errosEdicaoDiretorEnsino;
    }

    /**
     * @return the confirmacaoSenha
     */

    /**
     * @return the diretorEnsinoSelecionado
     */
    public DiretorEnsino getDiretorEnsinoSelecionado() {
        return diretorEnsinoSelecionado;
    }

    /**
     * @param diretorEnsinoSelecionado the diretorEnsinoSelecionado to set
     */
    public void setDiretorEnsinoSelecionado(DiretorEnsino diretorEnsinoSelecionado) {
        this.diretorEnsinoSelecionado = diretorEnsinoSelecionado;
    }

    /**
     * @return the confirmacaoSenhaSelecionado
     */
    public String getConfirmacaoSenhaSelecionado() {
        return confirmacaoSenhaSelecionado;
    }

    /**
     * @param confirmacaoSenhaSelecionado the confirmacaoSenhaSelecionado to set
     */
    public void setConfirmacaoSenhaSelecionado(String confirmacaoSenhaSelecionado) {
        this.confirmacaoSenhaSelecionado = confirmacaoSenhaSelecionado;
    }
}

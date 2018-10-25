/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpr.irati.mb;

import br.edu.ifpr.irati.dao.Dao;
import br.edu.ifpr.irati.dao.GenericDAO;
import br.edu.ifpr.irati.modelo.Administracao;
import br.edu.ifpr.irati.modelo.Apoio;
import br.edu.ifpr.irati.modelo.AtividadeASerProposta;
import br.edu.ifpr.irati.modelo.Aula;
import br.edu.ifpr.irati.modelo.Horario;
import br.edu.ifpr.irati.modelo.ManutencaoEnsino;
import br.edu.ifpr.irati.modelo.OutroTipoAtividade;
import br.edu.ifpr.irati.modelo.PTD;
import br.edu.ifpr.irati.modelo.Participacao;
import br.edu.ifpr.irati.modelo.Professor;
import br.edu.ifpr.irati.modelo.ProjetoPesquisaExtensao;
import br.edu.ifpr.irati.modelo.Usuario;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.print.attribute.standard.DateTimeAtCompleted;

@ManagedBean
@SessionScoped
public class HorarioMB {

    private Horario horarioAula;
    private Horario horarioManuEnsino;
    private Horario horarioApoioEnsino;
    private Horario horarioPesquisaExtensaoAutor;
    private Horario horarioPesquisaExtensaoColab;
    private Horario horarioAdministracao;
    private Horario horarioOutroTipoAtividade;
    private Horario horarioAtividadeASerProposta;
    private List<Horario> horarios;
    private List<String> diasSemana;

    public HorarioMB() {

        horarioAula = new Horario();
        horarioManuEnsino = new Horario();
        horarioApoioEnsino = new Horario();
        horarioPesquisaExtensaoAutor = new Horario();
        horarioPesquisaExtensaoColab = new Horario();
        horarioAdministracao = new Horario();
        horarioOutroTipoAtividade = new Horario();
        horarioAtividadeASerProposta = new Horario();
        horarios = new ArrayList<>();
        diasSemana = new ArrayList<>();
        diasSemana.add("Segunda");
        diasSemana.add("Terça");
        diasSemana.add("Quarta");
        diasSemana.add("Quinta");
        diasSemana.add("Sexta");
        diasSemana.add("Sábado");

    }

    public String salvarHorarioAula(Aula aula, Usuario usuario) {
        Dao<Horario> horarioDAO = new GenericDAO<>(Horario.class);
        Dao<Aula> aulaDAO = new GenericDAO<>(Aula.class);
        Dao<Professor> professorDAO = new GenericDAO<>(Professor.class);
        Professor p = professorDAO.buscarPorId(usuario.getIdUsuario());
        horarioAula.setEstadoHorario("Ativo");
        horarioAula.setProfessor(p);
        aula.getHorariosAula().add(horarioAula);
        horarioDAO.salvar(aula.getHorariosAula().get(aula.getHorariosAula().size() - 1));
        aulaDAO.alterar(aula);
        horarioAula = new Horario();
        return "CriarCorrigirPTD?faces-redirect=true";
    }

    public String salvarHorarioAtividade(Object object, Usuario usuario) {

        Dao<Horario> horarioDAO = new GenericDAO<>(Horario.class);
        Dao<Professor> professorDAO = new GenericDAO<>(Professor.class);
        Professor p = professorDAO.buscarPorId(usuario.getIdUsuario());
        Dao<ManutencaoEnsino> manutencaoEnsinoDAO = new GenericDAO<>(ManutencaoEnsino.class);
        Dao<Apoio> apoioEnsinoDAO = new GenericDAO<>(Apoio.class);
        Dao<Participacao> participacaoDAO = new GenericDAO<>(Participacao.class);
        Dao<Administracao> administracaoDAO = new GenericDAO<>(Administracao.class);
        Dao<OutroTipoAtividade> outroTipoAtividadeDAO = new GenericDAO<>(OutroTipoAtividade.class);
        Dao<AtividadeASerProposta> atividadeASerPropostaDAO = new GenericDAO<>(AtividadeASerProposta.class);
        List<Horario> horariosObjetoSelecionado = new ArrayList();

        double cargaHoraNovoHorario = 0;
        double cargaHorariosTotal = 0;
        double minTotal = 0;
        Horario horarioAtividade = new Horario();

        if (object instanceof ManutencaoEnsino) {
            horarioAtividade = horarioManuEnsino;
            horarioManuEnsino = new Horario();
            horariosObjetoSelecionado = manutencaoEnsinoDAO.buscarPorId(((ManutencaoEnsino) object).getIdManutencao()).getHorariosManutecao();
        }
        if (object instanceof Apoio) {
            horarioAtividade = horarioApoioEnsino;
            horarioApoioEnsino = new Horario();
            horariosObjetoSelecionado = apoioEnsinoDAO.buscarPorId(((Apoio) object).getIdApoio()).getHorariosApoio();
        }
        if (object instanceof Participacao) {
            if (((Participacao) object).getRotulo().equals("Colaborador")) {
                horarioAtividade = horarioPesquisaExtensaoColab;
            } else {
                horarioAtividade = horarioPesquisaExtensaoAutor;
            }
            horarioPesquisaExtensaoAutor = new Horario();
            horarioPesquisaExtensaoColab = new Horario();
            horariosObjetoSelecionado = participacaoDAO.buscarPorId(((Participacao) object).getIdParticipacao()).getHorariosParticipacao();
        }
        if (object instanceof Administracao) {
            horarioAtividade = horarioAdministracao;
            horarioAdministracao = new Horario();
            horariosObjetoSelecionado = administracaoDAO.buscarPorId(((Administracao) object).getIdAdministracao()).getHorariosAdministracao();
        }
        if (object instanceof OutroTipoAtividade) {
            horarioAtividade = horarioOutroTipoAtividade;
            horarioOutroTipoAtividade = new Horario();
            horariosObjetoSelecionado = outroTipoAtividadeDAO.buscarPorId(((OutroTipoAtividade) object).getIdOutroTipoAtividade()).getHorariosOutroTipoAtividade();
        }
        if (object instanceof AtividadeASerProposta) {
            horarioAtividade = horarioAtividadeASerProposta;
            horarioAtividadeASerProposta = new Horario();
            horariosObjetoSelecionado = atividadeASerPropostaDAO.buscarPorId(((AtividadeASerProposta) object).getIdAtividadeASerProposta()).getHorariosAtividadesASerProposta();
        }

        horarioAtividade.setProfessor(p);
        horarioAtividade.setEstadoHorario("Ativo");

        double minInicio = horarioAtividade.getHoraInicio().getMinutes();
        double minTermino = horarioAtividade.getHoraTermino().getMinutes();
        double horaInicio = horarioAtividade.getHoraInicio().getHours();
        double horaTermino = horarioAtividade.getHoraTermino().getHours();

        cargaHoraNovoHorario = horaTermino - horaInicio;
        if (minTermino > minInicio) {
            minTotal = minTermino - minInicio;
            cargaHoraNovoHorario = cargaHoraNovoHorario + (minTotal / 60);
        }
        if (minTermino < minInicio) {
            minTotal = (60 - minInicio) + minTermino;
            cargaHoraNovoHorario = (cargaHoraNovoHorario + (minTotal / 60)) - 1;
        }
        
        for (Horario h : horariosObjetoSelecionado) {

            double minutoInicio = h.getHoraInicio().getMinutes();
            double minutoTermino = h.getHoraTermino().getMinutes();
            double horasInicio = h.getHoraInicio().getHours();
            double horasTermino = h.getHoraTermino().getHours();

            cargaHorariosTotal = cargaHorariosTotal + (horasTermino - horasInicio);
            if (minutoTermino > minutoInicio) {
                minTotal = minutoTermino - minutoInicio;
                cargaHoraNovoHorario = cargaHoraNovoHorario + (minTotal / 60);
            }
            if (minutoTermino < minutoInicio) {
                minTotal = (60 - minutoInicio) + minutoTermino;
                cargaHorariosTotal = (cargaHoraNovoHorario + (minTotal / 60)) - 1;
            }
        }
        

        if (object instanceof ManutencaoEnsino) {
            ((ManutencaoEnsino) object).setCargaHorariaSemanalManutencaoEnsino(cargaHorariosTotal + cargaHoraNovoHorario);
            ((ManutencaoEnsino) object).getHorariosManutecao().add(horarioAtividade);
            horarioDAO.salvar(((ManutencaoEnsino) object).getHorariosManutecao().get(((ManutencaoEnsino) object).getHorariosManutecao().size() - 1));
            manutencaoEnsinoDAO.alterar(((ManutencaoEnsino) object));
        }
        if (object instanceof Apoio) {
            ((Apoio) object).setCargaHorariaSemanalApoio(cargaHorariosTotal + cargaHoraNovoHorario);
            ((Apoio) object).getHorariosApoio().add(horarioAtividade);
            horarioDAO.salvar(((Apoio) object).getHorariosApoio().get(((Apoio) object).getHorariosApoio().size() - 1));
            apoioEnsinoDAO.alterar(((Apoio) object));
        }
        if (object instanceof Participacao) {
            ((Participacao) object).setCargaHorariaSemanalParticipacao(cargaHorariosTotal + cargaHoraNovoHorario);
            ((Participacao) object).getHorariosParticipacao().add(horarioAtividade);
            horarioDAO.salvar(((Participacao) object).getHorariosParticipacao().get(((Participacao) object).getHorariosParticipacao().size() - 1));
            participacaoDAO.alterar(((Participacao) object));
        }
        if (object instanceof Administracao) {
            ((Administracao) object).setCargaHorariaSemanalAdministracao(cargaHorariosTotal + cargaHoraNovoHorario);
            ((Administracao) object).getHorariosAdministracao().add(horarioAtividade);
            horarioDAO.salvar(((Administracao) object).getHorariosAdministracao().get(((Administracao) object).getHorariosAdministracao().size() - 1));
            administracaoDAO.alterar(((Administracao) object));
        }
        if (object instanceof OutroTipoAtividade) {
            ((OutroTipoAtividade) object).setCargaHorariaSemanalOutroTipoAtividade(cargaHorariosTotal + cargaHoraNovoHorario);
            ((OutroTipoAtividade) object).getHorariosOutroTipoAtividade().add(horarioAtividade);
            horarioDAO.salvar(((OutroTipoAtividade) object).getHorariosOutroTipoAtividade().get(((OutroTipoAtividade) object).getHorariosOutroTipoAtividade().size() - 1));
            outroTipoAtividadeDAO.alterar(((OutroTipoAtividade) object));
        }
        if (object instanceof AtividadeASerProposta) {
            ((AtividadeASerProposta) object).setCargaHorariaSemanalAtividadeASerProposta(cargaHorariosTotal + cargaHoraNovoHorario);
            ((AtividadeASerProposta) object).getHorariosAtividadesASerProposta().add(horarioAtividade);
            horarioDAO.salvar(((AtividadeASerProposta) object).getHorariosAtividadesASerProposta().get(((AtividadeASerProposta) object).getHorariosAtividadesASerProposta().size() - 1));
            atividadeASerPropostaDAO.alterar(((AtividadeASerProposta) object));
        }

        return "CriarCorrigirPTD?faces-redirect=true";

    }

    public String alterarHorarioAula(List<Horario> horariosSelecionada) {
        Dao<Horario> horarioDAO = new GenericDAO<>(Horario.class);
        for (Horario h : horariosSelecionada) {
            horarioDAO.alterar(h);
        }
        return "CriarCorrigirPTD?faces-redirect=true";
    }

    public String alterarHorarioAtividade(List<Horario> horariosSelecionada, Object object) {
        Dao<Horario> horarioDAO = new GenericDAO<>(Horario.class);
        double cargaHoraNovoHorario = 0;
        double minTotal = 0;

        for (Horario h : horariosSelecionada) {

            double minInicio = h.getHoraInicio().getMinutes();
            double minTermino = h.getHoraTermino().getMinutes();
            double horaInicio = h.getHoraInicio().getHours();
            double horaTermino = h.getHoraTermino().getHours();

            cargaHoraNovoHorario = cargaHoraNovoHorario + (horaTermino - horaInicio);
            if (minTermino > minInicio) {
                minTotal = minTermino - minInicio;
                cargaHoraNovoHorario = cargaHoraNovoHorario + (minTotal / 60);
            }
            if (minTermino < minInicio) {
                minTotal = (60 - minInicio) + minTermino;
                cargaHoraNovoHorario = (cargaHoraNovoHorario + (minTotal / 60)) - 1;
            }
            horarioDAO.alterar(h);
        }

        if (object instanceof ManutencaoEnsino) {
            Dao<ManutencaoEnsino> manutencaoEnsinoDAO = new GenericDAO<>(ManutencaoEnsino.class);
            ((ManutencaoEnsino) object).setCargaHorariaSemanalManutencaoEnsino(cargaHoraNovoHorario);
            manutencaoEnsinoDAO.alterar((ManutencaoEnsino) object);
        }
        if (object instanceof Apoio) {
            Dao<Apoio> apoioEnsinoDAO = new GenericDAO<>(Apoio.class);
            ((Apoio) object).setCargaHorariaSemanalApoio(cargaHoraNovoHorario);
            apoioEnsinoDAO.alterar((Apoio) object);
        }
        if (object instanceof Participacao) {
            Dao<Participacao> participacaoDAO = new GenericDAO<>(Participacao.class);
            ((Participacao) object).setCargaHorariaSemanalParticipacao(cargaHoraNovoHorario);
            participacaoDAO.alterar(((Participacao) object));
        }
        if (object instanceof Administracao) {
            Dao<Administracao> administracaoDAO = new GenericDAO<>(Administracao.class);
            ((Administracao) object).setCargaHorariaSemanalAdministracao(cargaHoraNovoHorario);
            administracaoDAO.alterar((Administracao) object);
        }
        if (object instanceof OutroTipoAtividade) {
            Dao<OutroTipoAtividade> outroTipoAtividadeDAO = new GenericDAO<>(OutroTipoAtividade.class);
            ((OutroTipoAtividade) object).setCargaHorariaSemanalOutroTipoAtividade(cargaHoraNovoHorario);
            outroTipoAtividadeDAO.alterar((OutroTipoAtividade) object);
        }
        if (object instanceof AtividadeASerProposta) {
            Dao<AtividadeASerProposta> atividadeASerPropostaDAO = new GenericDAO<>(AtividadeASerProposta.class);
            ((AtividadeASerProposta) object).setCargaHorariaSemanalAtividadeASerProposta(cargaHoraNovoHorario);
            atividadeASerPropostaDAO.alterar((AtividadeASerProposta) object);
        }

        return "CriarCorrigirPTD?faces-redirect=true";
    }

    public String excluirHorarioAula(Horario horario, Aula aula) {
        Dao<Horario> horarioDAO = new GenericDAO<>(Horario.class);
        Dao<Aula> aulaDAO = new GenericDAO<>(Aula.class);
        aula.getHorariosAula().remove(horario);
        aulaDAO.alterar(aula);
        horarioDAO.excluir(horario);
        return "CriarCorrigirPTD?faces-redirect=true";
    }

    public String excluirHorarioAtividade(Horario horario, Object object) {
        Dao<Horario> horarioDAO = new GenericDAO<>(Horario.class);

        double cargaHoraNovoHorario = 0;
        double minTotal = 0;
        double minInicio = horario.getHoraInicio().getMinutes();
        double minTermino = horario.getHoraTermino().getMinutes();
        double horaInicio = horario.getHoraInicio().getHours();
        double horaTermino = horario.getHoraTermino().getHours();

        cargaHoraNovoHorario = cargaHoraNovoHorario + (horaTermino - horaInicio);
        if (minTermino > minInicio) {
            minTotal = minTermino - minInicio;
            cargaHoraNovoHorario = cargaHoraNovoHorario + (minTotal / 60);
        }
        if (minTermino < minTermino) {
            minTotal = (60 - minInicio) + minTermino;
            cargaHoraNovoHorario = (cargaHoraNovoHorario + (minTotal / 60)) - 1;
        }

        if (object instanceof ManutencaoEnsino) {
            Dao<ManutencaoEnsino> manutencaoEnsinoDAO = new GenericDAO<>(ManutencaoEnsino.class);
            ((ManutencaoEnsino) object).setCargaHorariaSemanalManutencaoEnsino(((ManutencaoEnsino) object).getCargaHorariaSemanalManutencaoEnsino() - cargaHoraNovoHorario);
            manutencaoEnsinoDAO.alterar((ManutencaoEnsino) object);
            ((ManutencaoEnsino) object).getHorariosManutecao().remove(horario);
            manutencaoEnsinoDAO.alterar(((ManutencaoEnsino) object));
        }
        if (object instanceof Apoio) {
            Dao<Apoio> apoioEnsinoDAO = new GenericDAO<>(Apoio.class);
            ((Apoio) object).setCargaHorariaSemanalApoio(((Apoio) object).getCargaHorariaSemanalApoio() - cargaHoraNovoHorario);
            apoioEnsinoDAO.alterar((Apoio) object);
            ((Apoio) object).getHorariosApoio().remove(horario);
            apoioEnsinoDAO.alterar(((Apoio) object));
        }
        if (object instanceof Participacao) {
            Dao<Participacao> participacaoDAO = new GenericDAO<>(Participacao.class);
            ((Participacao) object).setCargaHorariaSemanalParticipacao(((Participacao) object).getCargaHorariaSemanalParticipacao() - cargaHoraNovoHorario);
            participacaoDAO.alterar(((Participacao) object));
            ((Participacao) object).getHorariosParticipacao().remove(horario);
            participacaoDAO.alterar(((Participacao) object));
        }
        if (object instanceof Administracao) {
            Dao<Administracao> administracaoDAO = new GenericDAO<>(Administracao.class);
            ((Administracao) object).setCargaHorariaSemanalAdministracao(((Administracao) object).getCargaHorariaSemanalAdministracao() - cargaHoraNovoHorario);
            administracaoDAO.alterar((Administracao) object);
            ((Administracao) object).getHorariosAdministracao().remove(horario);
            administracaoDAO.alterar(((Administracao) object));
        }
        if (object instanceof OutroTipoAtividade) {
            Dao<OutroTipoAtividade> outroTipoAtividadeDAO = new GenericDAO<>(OutroTipoAtividade.class);
            ((OutroTipoAtividade) object).setCargaHorariaSemanalOutroTipoAtividade(((OutroTipoAtividade) object).getCargaHorariaSemanalOutroTipoAtividade() - cargaHoraNovoHorario);
            outroTipoAtividadeDAO.alterar((OutroTipoAtividade) object);
            ((OutroTipoAtividade) object).getHorariosOutroTipoAtividade().remove(horario);
            outroTipoAtividadeDAO.alterar(((OutroTipoAtividade) object));
        }
        if (object instanceof AtividadeASerProposta) {
            Dao<AtividadeASerProposta> atividadeASerPropostaDAO = new GenericDAO<>(AtividadeASerProposta.class);
            ((AtividadeASerProposta) object).setCargaHorariaSemanalAtividadeASerProposta(((AtividadeASerProposta) object).getCargaHorariaSemanalAtividadeASerProposta() - cargaHoraNovoHorario);
            atividadeASerPropostaDAO.alterar((AtividadeASerProposta) object);
            ((AtividadeASerProposta) object).getHorariosAtividadesASerProposta().remove(horario);
            atividadeASerPropostaDAO.alterar(((AtividadeASerProposta) object));
        }

        horarioDAO.excluir(horario);
        return "CriarCorrigirPTD?faces-redirect=true";
    }

    /**
     * @return the horarios
     */
    public List<Horario> getHorarios() {
        return horarios;
    }

    /**
     * @param horarios the horarios to set
     */
    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    /**
     * @return the diasSemana
     */
    public List<String> getDiasSemana() {
        return diasSemana;
    }

    /**
     * @param diasSemana the diasSemana to set
     */
    public void setDiasSemana(List<String> diasSemana) {
        this.diasSemana = diasSemana;
    }

    /**
     * @return the horarioAula
     */
    public Horario getHorarioAula() {
        return horarioAula;
    }

    /**
     * @param horarioAula the horarioAula to set
     */
    public void setHorarioAula(Horario horarioAula) {
        this.horarioAula = horarioAula;
    }

    /**
     * @return the horarioManuEnsino
     */
    public Horario getHorarioManuEnsino() {
        return horarioManuEnsino;
    }

    /**
     * @param horarioManuEnsino the horarioManuEnsino to set
     */
    public void setHorarioManuEnsino(Horario horarioManuEnsino) {
        this.horarioManuEnsino = horarioManuEnsino;
    }

    /**
     * @return the horarioApoioEnsino
     */
    public Horario getHorarioApoioEnsino() {
        return horarioApoioEnsino;
    }

    /**
     * @param horarioApoioEnsino the horarioApoioEnsino to set
     */
    public void setHorarioApoioEnsino(Horario horarioApoioEnsino) {
        this.horarioApoioEnsino = horarioApoioEnsino;
    }

    /**
     * @return the horarioAdministracao
     */
    public Horario getHorarioAdministracao() {
        return horarioAdministracao;
    }

    /**
     * @param horarioAdministracao the horarioAdministracao to set
     */
    public void setHorarioAdministracao(Horario horarioAdministracao) {
        this.horarioAdministracao = horarioAdministracao;
    }

    /**
     * @return the horarioOutroTipoAtividade
     */
    public Horario getHorarioOutroTipoAtividade() {
        return horarioOutroTipoAtividade;
    }

    /**
     * @param horarioOutroTipoAtividade the horarioOutroTipoAtividade to set
     */
    public void setHorarioOutroTipoAtividade(Horario horarioOutroTipoAtividade) {
        this.horarioOutroTipoAtividade = horarioOutroTipoAtividade;
    }

    /**
     * @return the horarioAtividadeASerProposta
     */
    public Horario getHorarioAtividadeASerProposta() {
        return horarioAtividadeASerProposta;
    }

    /**
     * @param horarioAtividadeASerProposta the horarioAtividadeASerProposta to
     * set
     */
    public void setHorarioAtividadeASerProposta(Horario horarioAtividadeASerProposta) {
        this.horarioAtividadeASerProposta = horarioAtividadeASerProposta;
    }

    /**
     * @return the horarioPesquisaExtensao
     */
    public Horario getHorarioPesquisaExtensaoAutor() {
        return horarioPesquisaExtensaoAutor;
    }

    /**
     * @param horarioPesquisaExtensaoAutor the horarioPesquisaExtensao to set
     */
    public void setHorarioPesquisaExtensaoAutor(Horario horarioPesquisaExtensaoAutor) {
        this.horarioPesquisaExtensaoAutor = horarioPesquisaExtensaoAutor;
    }

    /**
     * @return the horarioPesquisaExtensaoParticipacao
     */
    public Horario getHorarioPesquisaExtensaoParticipacao() {
        return horarioPesquisaExtensaoColab;
    }

    /**
     * @param horarioPesquisaExtensaoColab the
     * horarioPesquisaExtensaoParticipacao to set
     */
    public void setHorarioPesquisaExtensaoColab(Horario horarioPesquisaExtensaoColab) {
        this.horarioPesquisaExtensaoColab = horarioPesquisaExtensaoColab;
    }

}

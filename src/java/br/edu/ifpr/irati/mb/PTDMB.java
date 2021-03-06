package br.edu.ifpr.irati.mb;

import br.edu.ifpr.irati.dao.Dao;
import br.edu.ifpr.irati.dao.GenericDAO;
import br.edu.ifpr.irati.dao.IPTDDAO;
import br.edu.ifpr.irati.dao.IUsuarioDao;
import br.edu.ifpr.irati.dao.PTDDAO;
import br.edu.ifpr.irati.dao.UsuarioDAO;
import br.edu.ifpr.irati.modelo.Administracao;
import br.edu.ifpr.irati.modelo.Apoio;
import br.edu.ifpr.irati.modelo.AtividadeASerProposta;
import br.edu.ifpr.irati.modelo.Aula;
import br.edu.ifpr.irati.modelo.Curso;
import br.edu.ifpr.irati.modelo.DiretorEnsino;
import br.edu.ifpr.irati.modelo.Horario;
import br.edu.ifpr.irati.modelo.ManutencaoEnsino;
import br.edu.ifpr.irati.modelo.OutroTipoAtividade;
import br.edu.ifpr.irati.modelo.PTD;
import br.edu.ifpr.irati.modelo.Participacao;
import br.edu.ifpr.irati.modelo.Professor;
import br.edu.ifpr.irati.modelo.ProjetoPesquisaExtensao;
import br.edu.ifpr.irati.modelo.TipoAdministracao;
import br.edu.ifpr.irati.modelo.TipoApoio;
import br.edu.ifpr.irati.modelo.TipoManutencao;
import br.edu.ifpr.irati.modelo.TipoOferta;
import br.edu.ifpr.irati.modelo.Usuario;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.swing.text.Document;

@ManagedBean
@SessionScoped
public class PTDMB {

    private PTD ptd;
    private PTD ptdConcluido;
    private PTD ptdAprovado;
    private List<PTD> ptdsEmAvaliacao;
    private List<PTD> ptdsReprovados;
    private List<PTD> ptdsEmEdicao;
    private List<PTD> ptdsAprovados;
    private List<PTD> ptdsConcluídos;
    private List<PTD> ptdsArquivados;
    private List<Participacao> participacoesAutorPTDEdicao;
    private List<Participacao> participacoesColabPTDEdicao;
    private List<Participacao> participacoesAutorPTDAprovado;
    private List<Participacao> participacoesColabPTDAprovado;
    private List<Participacao> participacoesAutorPTDConcluido;
    private List<Participacao> participacoesColabPTDConcluido;
    private List<Professor> professoresAHabilitar;
    private List<DiretorEnsino> diretoresAHabilitar;
    private String estadoCargaHorariaPTD;
    private List<String> errosTabelaAula;
    private List<String> errosTabelaManuEnsino;
    private List<String> errosTabelaApoioEnsino;
    private List<String> errosTabelaPesquisaExtensaoAutor;
    private List<String> errosTabelaPesquisaExtensaoColaborador;
    private List<String> errosTabelaAdministrativas;
    private List<String> errosTabelaOutrasAtividades;
    private List<String> errosTabelaAtividadesASeremPropostas;
    private List<String> irregularidadesPTDEdicao;
    private double cargaHorariaTotalPTDPTDEdicao;
    private String saidaTelaMostrarPTDAux;

    public PTDMB() {

        IPTDDAO ptdDAOEspecifico = new PTDDAO();
        ptd = new PTD();
        ptdAprovado = new PTD();
        ptdConcluido = new PTD();
        ptdsEmAvaliacao = new ArrayList<>();
        ptdsReprovados = new ArrayList<>();
        ptdsEmEdicao = new ArrayList();
        ptdsConcluídos = new ArrayList();
        ptdsArquivados = new ArrayList<>();
        ptdsEmAvaliacao = ptdDAOEspecifico.buscarPTDEmAvaliacao();
        participacoesAutorPTDEdicao = new ArrayList<>();
        participacoesColabPTDEdicao = new ArrayList<>();
        participacoesAutorPTDAprovado = new ArrayList<>();
        participacoesColabPTDAprovado = new ArrayList<>();
        participacoesAutorPTDConcluido = new ArrayList<>();
        participacoesColabPTDConcluido = new ArrayList<>();
        professoresAHabilitar = new ArrayList<>();
        diretoresAHabilitar = new ArrayList<>();
        this.estadoCargaHorariaPTD = "";
        errosTabelaAula = new ArrayList<>();
        errosTabelaManuEnsino = new ArrayList<>();
        errosTabelaApoioEnsino = new ArrayList<>();
        errosTabelaPesquisaExtensaoAutor = new ArrayList<>();
        errosTabelaPesquisaExtensaoColaborador = new ArrayList<>();
        errosTabelaAdministrativas = new ArrayList<>();
        errosTabelaOutrasAtividades = new ArrayList<>();
        errosTabelaAtividadesASeremPropostas = new ArrayList<>();
        irregularidadesPTDEdicao = new ArrayList<>();
        saidaTelaMostrarPTDAux = "";

    }

    /*
     Acessa as informações da variável 'ptd' que representa o PTD em edição e separa
     nas listas de participações para serem mostradas nas tabelas de participação da 
     tela de criação e edição de PTDs
     */
    public void atualizarListasParticipacoesPTDEdicao() {
        participacoesAutorPTDEdicao = new ArrayList<>();
        participacoesColabPTDEdicao = new ArrayList<>();
        for (Participacao part : ptd.getParticipacoes()) {
            if (part.getRotulo().equalsIgnoreCase("Autor")) {
                participacoesAutorPTDEdicao.add(part);
            } else {
                participacoesColabPTDEdicao.add(part);
            }
        }
    }

    /*
     Acessa as informações da variável 'ptdAprovado' e separa nas listas de participações 
     para serem mostradas nas tabelas de participação da tela 'MostrarPTDAprovado'
     */
    public void atualizarListasParticipacoesPTDAprovado() {
        setParticipacoesAutorPTDAprovado(new ArrayList<>());
        setParticipacoesColabPTDAprovado(new ArrayList<>());
        for (Participacao part : ptdAprovado.getParticipacoes()) {
            if (part.getRotulo().equalsIgnoreCase("Autor")) {
                participacoesAutorPTDAprovado.add(part);
            } else {
                participacoesColabPTDAprovado.add(part);
            }
        }
    }

    /*
     Acessa as informações da variável 'ptdConcluido' e separa nas listas de participações 
     para serem mostradas nas tabelas de participação da tela 'MostrarPTD'
     */
    public void atualizarListasParticipacoesPTDConcluido() {
        setParticipacoesAutorPTDConcluido(new ArrayList<>());
        setParticipacoesColabPTDConcluido(new ArrayList<>());
        for (Participacao part : ptdConcluido.getParticipacoes()) {
            if (part.getRotulo().equalsIgnoreCase("Autor")) {
                participacoesAutorPTDConcluido.add(part);
            } else {
                participacoesColabPTDConcluido.add(part);
            }
        }
    }

    /*
     Considerando que a tela 'MostrarPTD' pode ser acessada por dois caiminhos diferentes -
     através da tela de busca e da tela de notificações do professor - o método identifica
     o conteúdo de uma variável auxiliar para executar a função 'voltar' daquela tela
     */
    public String sairTelaMostrarPTD(Usuario usuario) {
        if (saidaTelaMostrarPTDAux.equalsIgnoreCase("")) {
            return "/BuscarPTDs?faces-redirect=true";
        } else {
            return abrirNotificacoesDocente(usuario.getIdUsuario());
        }
    }

    /*
     Considerando que a tela 'BuscarPTDs' pode ser acessada por diferentes caminhos -
     através da tela de notificações do professor, do docente e da tela 'index' -  o
     método identica se há um usuário logado no sistema e se, no caso de haver um, que
     tipo de usuário está logado, para, dessa forma, encaminhar para as respectivas 
     telas anteriores como ação do botão 'voltar'
     */
    public String sairTelaBuscarPTDs(Usuario usuario) {
        if (usuario.getIdUsuario() != 0) {
            if (usuario instanceof Professor) {
                return abrirNotificacoesDocente(usuario.getIdUsuario());
            } else {
                return abrirNotificacoesDiretorEnsino(usuario.getIdUsuario());
            }
        } else {
            return "/index?faces-redirect=true";
        }
    }

    /*
     No cenário em que os métodos de recuperação do último PTD em edição, de continuação
     de edição do último PTD em estado de edição e de recuperação de um PTD reprovado
     funcionam, o método prepararia a tela de criação e edição de PTDs para receber um
     PTD diferente
     */
    public void limparVariáveis() {
        errosTabelaAdministrativas = new ArrayList<>();
        errosTabelaApoioEnsino = new ArrayList<>();
        errosTabelaAtividadesASeremPropostas = new ArrayList<>();
        errosTabelaAula = new ArrayList<>();
        errosTabelaManuEnsino = new ArrayList<>();
        errosTabelaOutrasAtividades = new ArrayList<>();
        errosTabelaPesquisaExtensaoAutor = new ArrayList<>();
        errosTabelaPesquisaExtensaoColaborador = new ArrayList<>();
        irregularidadesPTDEdicao = new ArrayList<>();
        cargaHorariaTotalPTDPTDEdicao = 0;
    }

    /*
     Devido ao fato das telas de notificações para docentes e para diretor de ensino são
     telas de apresentação de dados, na tela de login, depois do ManagedBean de usuário
     ter identificado o tipo de usuário, esse método é chamado para reconhecer qual tela
     deve ser carregada, encaminhando os métodos que prepararão os dados que serão 
     apresentados
     */
    public String concluirLogin(String tela, int idUsuario) {
        if (tela.equalsIgnoreCase("/NotificacoesDocente?faces-redirect=true")) {
            return abrirNotificacoesDocente(idUsuario);
        } else if (tela.equalsIgnoreCase("/NotificacoesDiretorEnsino?faces-redirect=true")) {
            return abrirNotificacoesDiretorEnsino(idUsuario);
        } else {
            return tela;
        }

    }

    /*
     Prepara as informações que serão utilizadas na tela 'MostrarPTD' setando o PTD, cuja
     consulta foi solicitada, e uma variável auxiliar utilizada no método 'sairTelaMostrarPTD'
     */
    public void abrirMostrarPTD(PTD ptd, String s) {
        ptdConcluido = ptd;
        saidaTelaMostrarPTDAux = s;
    }

    /*
     Prepara as informações que serão utilizadas na tela 'MostrarPTDAprovado'
     */
    public void abrirMostrarPTDAprovado(PTD ptd) {
        ptdAprovado = ptd;
    }

    /*
     Prepara as informações que serão disponibilizadas na tela de notificações referente
     ao professor
     */
    public String abrirNotificacoesDocente(int idUsuario) {
        IPTDDAO ptdDAOEspecifico = new PTDDAO();
        ptdsReprovados = ptdDAOEspecifico.buscarPTDsReprovadosPorProfessor(idUsuario);
        ptdsAprovados = ptdDAOEspecifico.buscarPTDsAprovadosPorProfessor(idUsuario);
        ptdsConcluídos = ptdDAOEspecifico.buscarPTDsConcluidosPorProfessor(idUsuario);
        ptdsArquivados = ptdDAOEspecifico.buscarPTDsArquivadosPorProfessor(idUsuario);
        return "/NotificacoesDocente?faces-redirect=true";
    }

    /*
     Prepara as informações que serão disponibilizadas na tela de notificações referente
     ao diretor de ensino
     */
    public String abrirNotificacoesDiretorEnsino(int idUsuario) {
        IPTDDAO ptdDAOEspecifico = new PTDDAO();
        Dao<DiretorEnsino> diretorDAO = new GenericDAO(DiretorEnsino.class);
        Dao<Professor> professorDAO = new GenericDAO(Professor.class);
        IUsuarioDao usuarioDAOEspecifico = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAOEspecifico.buscarUsuariosASeremHabilitados();
        ptdsEmAvaliacao = ptdDAOEspecifico.buscarPTDEmAvaliacao();
        professoresAHabilitar = new ArrayList<>();
        diretoresAHabilitar = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u instanceof DiretorEnsino) {
                DiretorEnsino d = diretorDAO.buscarPorId(u.getIdUsuario());
                diretoresAHabilitar.add(d);
            } else {
                Professor p = professorDAO.buscarPorId(u.getIdUsuario());
                professoresAHabilitar.add(p);
            }
        }
        return "/NotificacoesDiretorEnsino?faces-redirect=true";
    }

    /*
     Concentra a chamada dos métodos de conferência/verificação para a tela de criação
     e edição de PTDs
     */
    public void realizarConferencias() {
        verificarErros();
        verificarCargaHorariaPTDEdicao();
    }

    /*
     Realiza a chamada da tela de criação e edição de PTDs criando um PTD em branco, com
     status em 'EDICAO'. Considerando o caso em que os métodos de recuperação/recarregamento
     de PTD funcionam, o método busca PTDs que estejam em estado de edição e os coloca em 
     estado 'CANCELADO'
     */
    public String abrirCriarCorrigirPTDEmBranco(Usuario usuario) {
        Dao<PTD> ptdDAOGenerico = new GenericDAO<>(PTD.class);
        Dao<Professor> professorDAOGenerico = new GenericDAO<>(Professor.class);
        IPTDDAO ptdDAOEspecifico = new PTDDAO();
        Professor p = professorDAOGenerico.buscarPorId(usuario.getIdUsuario());

        getPtd().setProfessor(p);
        setPtdsEmEdicao(ptdDAOEspecifico.buscarPTDsEmEdicaoPorProfessor(p.getIdUsuario()));
        for (PTD ptdE : getPtdsEmEdicao()) {
//            ptdDAOEspecifico.excluirPTDEOQueTemDentro(ptdE);
            ptdE.setEstadoPTD("CANCELADO");
            ptdDAOGenerico.alterar(ptdE);
        }
        setPtd(new PTD());

        getPtd().setProfessor(p);

        getPtd().setDiretorEnsino(null);
        getPtd().setEstadoPTD("EDICAO");
        ptdDAOGenerico.salvar(getPtd());

        limparVariáveis();

        if (!ptdDAOEspecifico.buscarPTDsEmEdicaoPorProfessor(p.getIdUsuario()).isEmpty()) {
            setPtd(ptdDAOEspecifico.buscarPTDsEmEdicaoPorProfessor(p.getIdUsuario()).get(0));
        }

        return "/CriarCorrigirPTD?faces-redirect=true";
    }

    /*
     Verifica a existência de PTDs no estado de edição e coloca o resultado na variável de 
     ptd que é utilizada na tela de criação e edição de PTDs
     */
    public String abrirCriarCorrigirPTDContinuarEdicao(Usuario usuario) {

        IPTDDAO ptdDAOEspecifico = new PTDDAO();
        List<PTD> ptdsEmEdicao = ptdDAOEspecifico.buscarPTDsEmEdicaoPorProfessor(usuario.getIdUsuario());
        if (!ptdsEmEdicao.isEmpty()) {
            setPtd(ptdsEmEdicao.get(0));
            return "/CriarCorrigirPTD?faces-redirect=true";
        } else {
            return "/NotificacoesDocente?faces-redirect=true";
        }

    }

    /*
     Realiza uma busca dos PTDs concluídos pelo professor para encontrar o PTD vigente, 
     recupera suas informações e as cadastra fazendo parte de um novo PTD, este em 
     edição
     */
    public String abrirCriarCorrigirPTDAPartirDoUltimoArquivado(Usuario usuario) {
        Dao<PTD> ptdDAOGenerico = new GenericDAO<>(PTD.class);
        IPTDDAO ptdDAOEspecifico = new PTDDAO();
        List<PTD> ptdsEmEdicao = ptdDAOEspecifico.buscarPTDsEmEdicaoPorProfessor(usuario.getIdUsuario());
        for (PTD ptdE : ptdsEmEdicao) {
            ptdE.setEstadoPTD("CANCELADO");
            ptdDAOGenerico.alterar(ptdE);
        }
        List<PTD> ptdsAprovados = ptdDAOEspecifico.buscarPTDsConcluidosPorProfessor(usuario.getIdUsuario());

        limparVariáveis();

        if (!ptdsAprovados.isEmpty()) {

            Dao<Administracao> administracaoDAO = new GenericDAO<>(PTD.class);
            Dao<TipoAdministracao> tipoAdministracaoDAO = new GenericDAO<>(TipoAdministracao.class);
            Dao<Apoio> apoioDAO = new GenericDAO<>(Apoio.class);
            Dao<TipoApoio> tipoApoioDAO = new GenericDAO<>(TipoApoio.class);
            Dao<AtividadeASerProposta> aASPropostaDAO = new GenericDAO<>(AtividadeASerProposta.class);
            Dao<Aula> aulaDAO = new GenericDAO<>(Aula.class);
            Dao<Curso> cursoDAO = new GenericDAO<>(Curso.class);
            Dao<DiretorEnsino> diretorEnsinoDAO = new GenericDAO<>(DiretorEnsino.class);
            Dao<Horario> horarioDAO = new GenericDAO<>(Horario.class);
            Dao<ManutencaoEnsino> manutencaoDAO = new GenericDAO<>(ManutencaoEnsino.class);
            Dao<TipoManutencao> tipoManutencaoDAO = new GenericDAO<>(TipoManutencao.class);
            Dao<OutroTipoAtividade> oTAtividadeDAO = new GenericDAO<>(OutroTipoAtividade.class);
            Dao<PTD> ptdDAO = new GenericDAO<>(PTD.class);
            Dao<Participacao> participacaoDAO = new GenericDAO<>(Participacao.class);
            Dao<Professor> professorDAO = new GenericDAO<>(Professor.class);
            Dao<ProjetoPesquisaExtensao> pPesquisaExtensaoDAO = new GenericDAO<>(ProjetoPesquisaExtensao.class);
            Dao<TipoOferta> tipoOfertaDAO = new GenericDAO<>(TipoOferta.class);

            setPtd(ptdsAprovados.get(ptdsAprovados.size() - 1));
            PTD ptdAux = new PTD();
            ptdAux.setIdPTD(0);
            ptdAux.setEstadoPTD("EDICAO");
            ptdAux.setProfessor(ptd.getProfessor());
            ptdAux.setDiretorEnsino(null);

            for (Administracao adm : ptd.getAdministrativas()) {
                adm.setIdAdministracao(0);
                List<Horario> horariosAux = adm.getHorariosAdministracao();
                adm.setHorariosAdministracao(new ArrayList<>());
                for (Horario h : horariosAux) {
                    h.setIdHorario(0);
                    adm.getHorariosAdministracao().add(h);
                    horarioDAO.salvar(h);
                }
                administracaoDAO.salvar(adm);
                ptdAux.getAdministrativas().add(adm);
            }
            for (Apoio apoio : ptd.getApoios()) {
                apoio.setIdApoio(0);
                List<Horario> horariosAux = apoio.getHorariosApoio();
                apoio.setHorariosApoio(new ArrayList<>());
                for (Horario h : horariosAux) {
                    h.setIdHorario(0);
                    apoio.getHorariosApoio().add(h);
                    horarioDAO.salvar(h);
                }
                apoioDAO.salvar(apoio);
                ptdAux.getApoios().add(apoio);
            }
            for (AtividadeASerProposta aasp : ptd.getAtividadesASeremPropostas()) {
                aasp.setIdAtividadeASerProposta(0);
                List<Horario> horariosAux = aasp.getHorariosAtividadesASerProposta();
                aasp.setHorariosAtividadesASerProposta(new ArrayList<>());
                for (Horario h : horariosAux) {
                    h.setIdHorario(0);
                    aasp.getHorariosAtividadesASerProposta().add(h);
                    horarioDAO.salvar(h);
                }
                aASPropostaDAO.salvar(aasp);
                ptdAux.getAtividadesASeremPropostas().add(aasp);
            }
            for (Aula al : ptd.getAulas()) {
                al.setIdAula(0);
                List<Horario> horariosAux = al.getHorariosAula();
                al.setHorariosAula(new ArrayList<>());
                for (Horario h : horariosAux) {
                    h.setIdHorario(0);
                    al.getHorariosAula().add(h);
                    horarioDAO.salvar(h);
                }
                aulaDAO.salvar(al);
                ptdAux.getAulas().add(al);
            }
            for (ManutencaoEnsino me : ptd.getManutencoesEnsino()) {
                me.setIdManutencao(0);
                List<Horario> horariosAux = me.getHorariosManutecao();
                me.setHorariosManutecao(new ArrayList<>());
                for (Horario h : horariosAux) {
                    h.setIdHorario(0);
                    me.getHorariosManutecao().add(h);
                    horarioDAO.salvar(h);
                }
                manutencaoDAO.salvar(me);
                ptdAux.getManutencoesEnsino().add(me);
            }
            for (OutroTipoAtividade ota : ptd.getOutrosTiposAtividades()) {
                ota.setIdOutroTipoAtividade(0);
                List<Horario> horariosAux = ota.getHorariosOutroTipoAtividade();
                ota.setHorariosOutroTipoAtividade(new ArrayList<>());
                for (Horario h : horariosAux) {
                    h.setIdHorario(0);
                    ota.getHorariosOutroTipoAtividade().add(h);
                    horarioDAO.salvar(h);
                }
                oTAtividadeDAO.salvar(ota);
                ptdAux.getOutrosTiposAtividades().add(ota);
            }
            for (Participacao part : ptd.getParticipacoes()) {
                part.setIdParticipacao(0);
                List<Horario> horariosAux = part.getHorariosParticipacao();
                part.setHorariosParticipacao(new ArrayList<>());
                for (Horario h : horariosAux) {
                    h.setIdHorario(0);
                    part.getHorariosParticipacao().add(h);
                    horarioDAO.salvar(h);
                }
                participacaoDAO.salvar(part);
                ptdAux.getParticipacoes().add(part);
            }

            ptd = new PTD();
            ptdDAOGenerico.salvar(ptdAux);
            List<PTD> ptds = ptdDAOEspecifico.buscarPTDsEmEdicaoPorProfessor(usuario.getIdUsuario());
            ptd = ptds.get(ptds.size() - 1);

            errosTabelaAdministrativas = new ArrayList<>();
            errosTabelaApoioEnsino = new ArrayList<>();
            errosTabelaAtividadesASeremPropostas = new ArrayList<>();
            errosTabelaAula = new ArrayList<>();
            errosTabelaManuEnsino = new ArrayList<>();
            errosTabelaOutrasAtividades = new ArrayList<>();
            errosTabelaPesquisaExtensaoAutor = new ArrayList<>();
            errosTabelaPesquisaExtensaoColaborador = new ArrayList<>();
            irregularidadesPTDEdicao = new ArrayList<>();
            return "/CriarCorrigirPTD?faces-redirect=true";
        } else {

            return "/NotificacoesDocente?faces-redirect=true";
        }
    }

    /*
     Conforme solicitação de um usuário docente, no método é criado um novo PTD em estado 
     de edição recuperando as informações do PTD reprovado selecionado
     */
    public void abrirCriarCorrigirPTDAPartirDeUmReprovado(PTD ptdReprovado) {
        Dao<PTD> ptdDAOGenerico = new GenericDAO<>(PTD.class);
        IPTDDAO ptdDAOEspecifico = new PTDDAO();
        List<PTD> ptdsEmEdicao = ptdDAOEspecifico.buscarPTDsEmEdicaoPorProfessor(ptdReprovado.getProfessor().getIdUsuario());
        for (PTD ptdE : ptdsEmEdicao) {
            ptdE.setEstadoPTD("CANCELADO");
            ptdDAOGenerico.alterar(ptdE);
        }
        PTD ptdAux = ptdReprovado;
        ptdAux.setEstadoPTD("EDICAO");

        ptdReprovado.setEstadoPTD("CANCELADO");
        ptdDAOGenerico.alterar(ptdReprovado);
        ptdDAOGenerico.salvar(ptdAux);

        limparVariáveis();

        ptdDAOGenerico.alterar(ptdReprovado);
        setPtd(ptdDAOEspecifico.buscarPTDsEmEdicaoPorProfessor(ptdReprovado.getProfessor().getIdUsuario()).get(0));
    }

    /*
    
     */
    public String cancelarPTD(PTD ptdACancelar, int idUsuario, String telaFutura) {

        Dao<Administracao> administracaoDAO = new GenericDAO<>(PTD.class);
        Dao<TipoAdministracao> tipoAdministracaoDAO = new GenericDAO<>(TipoAdministracao.class);
        Dao<Apoio> apoioDAO = new GenericDAO<>(Apoio.class);
        Dao<TipoApoio> tipoApoioDAO = new GenericDAO<>(TipoApoio.class);
        Dao<AtividadeASerProposta> aASPropostaDAO = new GenericDAO<>(AtividadeASerProposta.class);
        Dao<Aula> aulaDAO = new GenericDAO<>(Aula.class);
        Dao<Curso> cursoDAO = new GenericDAO<>(Curso.class);
        Dao<DiretorEnsino> diretorEnsinoDAO = new GenericDAO<>(DiretorEnsino.class);
        Dao<Horario> horarioDAO = new GenericDAO<>(Horario.class);
        Dao<ManutencaoEnsino> manutencaoDAO = new GenericDAO<>(ManutencaoEnsino.class);
        Dao<TipoManutencao> tipoManutencaoDAO = new GenericDAO<>(TipoManutencao.class);
        Dao<OutroTipoAtividade> oTAtividadeDAO = new GenericDAO<>(OutroTipoAtividade.class);
        Dao<PTD> ptdDAO = new GenericDAO<>(PTD.class);
        Dao<Participacao> participacaoDAO = new GenericDAO<>(Participacao.class);
        Dao<Professor> professorDAO = new GenericDAO<>(Professor.class);
        Dao<ProjetoPesquisaExtensao> pPesquisaExtensaoDAO = new GenericDAO<>(ProjetoPesquisaExtensao.class);
        Dao<TipoOferta> tipoOfertaDAO = new GenericDAO<>(TipoOferta.class);
        Dao<Usuario> usuarioDAO = new GenericDAO<>(Usuario.class);

        List<Administracao> auxAdm = new ArrayList<>(ptdACancelar.getAdministrativas());
        for (Administracao adm : auxAdm) {

            List<Horario> aux = new ArrayList<>(adm.getHorariosAdministracao());
            for (Horario h : aux) {
                adm.getHorariosAdministracao().remove(h);
                administracaoDAO.alterar(adm);
                horarioDAO.excluir(h);
            }
            ptdACancelar.getAdministrativas().remove(adm);
            ptdDAO.alterar(ptdACancelar);
            administracaoDAO.excluir(adm);
            tipoAdministracaoDAO.excluir(adm.getTipoAdministracao());

        }
        List<Apoio> auxApoio = new ArrayList<>(ptdACancelar.getApoios());
        for (Apoio apoio : auxApoio) {
            List<Horario> aux = new ArrayList<>(apoio.getHorariosApoio());
            for (Horario h : aux) {
                apoio.getHorariosApoio().remove(h);
                apoioDAO.alterar(apoio);
                horarioDAO.excluir(h);
            }
            ptdACancelar.getApoios().remove(apoio);
            ptdDAO.alterar(ptdACancelar);
            apoioDAO.excluir(apoio);
            tipoApoioDAO.excluir(apoio.getTipoApoio());
        }
        List<AtividadeASerProposta> auxAASP = new ArrayList<>(ptdACancelar.getAtividadesASeremPropostas());
        for (AtividadeASerProposta aASP : auxAASP) {
            List<Horario> aux = new ArrayList<>(aASP.getHorariosAtividadesASerProposta());
            for (Horario h : aux) {
                aASP.getHorariosAtividadesASerProposta().remove(h);
                aASPropostaDAO.alterar(aASP);
                horarioDAO.excluir(h);
            }
            ptdACancelar.getAtividadesASeremPropostas().remove(aASP);
            ptdDAO.alterar(ptdACancelar);
            aASPropostaDAO.excluir(aASP);
        }
        List<Aula> auxAula = new ArrayList<>(ptdACancelar.getAulas());
        for (Aula aula : auxAula) {
            List<Horario> aux = new ArrayList<>(aula.getHorariosAula());
            for (Horario h : aux) {
                aula.getHorariosAula().remove(h);
                aulaDAO.alterar(aula);
                horarioDAO.excluir(h);
            }
            ptdACancelar.getAulas().remove(aula);
            ptdDAO.alterar(ptdACancelar);
            aulaDAO.excluir(aula);
        }
        List<ManutencaoEnsino> auxManuEnsino = new ArrayList<>(ptdACancelar.getManutencoesEnsino());
        for (ManutencaoEnsino mEnsino : auxManuEnsino) {
            List<Horario> aux = new ArrayList<>(mEnsino.getHorariosManutecao());
            for (Horario h : aux) {
                mEnsino.getHorariosManutecao().remove(h);
                manutencaoDAO.alterar(mEnsino);
                horarioDAO.excluir(h);
            }
            ptdACancelar.getManutencoesEnsino().remove(mEnsino);
            ptdDAO.alterar(ptdACancelar);
            manutencaoDAO.excluir(mEnsino);
            tipoManutencaoDAO.excluir(mEnsino.getTipoManutencao());
        }
        List<OutroTipoAtividade> auxOTA = new ArrayList<>(ptdACancelar.getOutrosTiposAtividades());
        for (OutroTipoAtividade oTA : auxOTA) {
            List<Horario> aux = new ArrayList<>(oTA.getHorariosOutroTipoAtividade());
            for (Horario h : aux) {
                oTA.getHorariosOutroTipoAtividade().remove(h);
                oTAtividadeDAO.alterar(oTA);
                horarioDAO.excluir(h);
            }

            ptdACancelar.getOutrosTiposAtividades().remove(oTA);
            ptdDAO.alterar(ptdACancelar);
            oTAtividadeDAO.excluir(oTA);
        }
        List<Participacao> auxPart = new ArrayList<>(ptdACancelar.getParticipacoes());
        for (Participacao p : auxPart) {

            ptdACancelar.getParticipacoes().remove(p);
            ptdDAO.alterar(ptdACancelar);
            participacaoDAO.excluir(p);

        }

        ptdDAO.excluir(ptdACancelar);

        if (telaFutura.equalsIgnoreCase("login")) {
            return "/Login?faces-redirect=true";
        } else {
            return abrirNotificacoesDocente(idUsuario);
        }

    }

    /*
     Altera um PTD reprovado mudando seu estado para 'CANCELADO'
     */
    public void descartarPTD(PTD ptdADescartar) {

        Dao<PTD> ptdDAO = new GenericDAO<>(PTD.class);
        ptdADescartar.setEstadoPTD("CANCELADO");
        ptdADescartar.setDiretorEnsino(null);
        ptdDAO.alterar(ptdADescartar);

    }

    /*
     Altera um PTD em edição mudando seu estado para 'AVALIACAO' e alterando sua carga 
     horária total calculada no método de calculo de 'verificarCargaHorariaPTDEdicao'
     */
    public String submeterPTD(int idUsuario) {
        Dao<PTD> ptdDAOGenerico = new GenericDAO<>(PTD.class);
        getPtd().setEstadoPTD("AVALIACAO");
        ptd.setCargaHorariaTotal(cargaHorariaTotalPTDPTDEdicao);
        ptdDAOGenerico.alterar(getPtd());
        return abrirNotificacoesDocente(idUsuario);
    }

    /*
     Todas os erros de dados e irregularidades legais ao serem identificados são adicionados
     em listas específicas, que através desse método apontam para caixas de dialogo na 
     interface. Quando houverem erros não será permitida a submissão do PTD, e no caso de
     irregularidades, a submissão será permitida se houverem justificativas/comentários 
     nas tabelas referêntes ao que foi apontado
     */
    public String verificarPossibilidadeSubmissao() {

        String nomeCaixaDialogo = "";
        atualizarListasParticipacoesPTDEdicao();

        // Conferência da existência de erros
        realizarConferencias();

        if (getCargaHorariaTotalPTDPTDEdicao() == 0) {
            nomeCaixaDialogo = "documentoVazioDialog";
        } else if (errosTabelaAdministrativas.isEmpty() != true) {
            nomeCaixaDialogo = "avisoErrosDialog";
        } else if (errosTabelaApoioEnsino.isEmpty() != true) {
            nomeCaixaDialogo = "avisoErrosDialog";
        } else if (errosTabelaAtividadesASeremPropostas.isEmpty() != true) {
            nomeCaixaDialogo = "avisoErrosDialog";
        } else if (errosTabelaAula.isEmpty() != true) {
            nomeCaixaDialogo = "avisoErrosDialog";
        } else if (errosTabelaManuEnsino.isEmpty() != true) {
            nomeCaixaDialogo = "avisoErrosDialog";
        } else if (errosTabelaOutrasAtividades.isEmpty() != true) {
            nomeCaixaDialogo = "avisoErrosDialog";
        } else if (errosTabelaPesquisaExtensaoAutor.isEmpty() != true) {
            nomeCaixaDialogo = "avisoErrosDialog";
        } else if (errosTabelaPesquisaExtensaoColaborador.isEmpty() != true) {
            nomeCaixaDialogo = "avisoErrosDialog";
        } else if (irregularidadesPTDEdicao.isEmpty() != true) {
            for (String irregularidade : irregularidadesPTDEdicao) {
                if ((irregularidade.equals("A carga horária é superior "
                        + "à 4 horas em Apoio ao Ensino!") | irregularidade.equals("A carga horária é inferior à 4 horas em Apoio ao Ensino!")) && ptd.getCampoJustificativaApoioEnsino().isEmpty() == true) {
                    nomeCaixaDialogo = "avisoIrregularidadeDialog";

                } else if ((irregularidade.equals("A carga horária de projetos de pesquisa "
                        + "e/ou extensão como colaborador "
                        + "e autor é superior à 16 horas!") | irregularidade.equals("A carga horária de projetos"
                                + "de pesquisa e/ou extensão como "
                                + "colaborador e autor é inferior à 16 horas!")) && ptd.getCampoJustificativaProjetoPesquisaExtensao().isEmpty() == true) {

                    nomeCaixaDialogo = "avisoIrregularidadeDialog";

                } else if ((irregularidade.equals("A carga horária é inferior à 4 horas"
                        + " em Manutenção ao Ensino") | irregularidade.equals("A carga horária é superior à 4"
                                + " horas em Manutenção ao Ensino")) && ptd.getCampoJustificativaManutencaoEnsino().isEmpty() == true) {

                    nomeCaixaDialogo = "avisoIrregularidadeDialog";

                } else if ((irregularidade.equals("A carga horária é inferior "
                        + "à 8 horas em Aula!") | irregularidade.equals("A carga horária é superior "
                                + "à 12 horas em Aula!") | irregularidade.equals("A carga horária é inferior "
                                + "à 12 horas em Aula!") | irregularidade.equals("A carga horária é superior"
                                + " à 16 horas em Aula!") | irregularidade.equals("Mesmo descontando a carga horária redistribuída"
                                + "de projeto de pesquisa e/ou extensão para aula"
                                + " e apoio ao ensino, o componente aula apresenta"
                                + " carga horária superior à 16 horas") | irregularidade.equals("Descontando a carga horária redistribuída de projeto de pesquisa"
                                + " e/ou extensão para aula e apoio ao ensino,"
                                + " o componente aula apresenta carga horária inferior à 12 horas")) && ptd.getCampoJustificativaAtividadeEnsino().isEmpty() == true) {

                    nomeCaixaDialogo = "avisoIrregularidadeDialog";

                } else if ((irregularidade.equals("A carga horária dedicada a Atividades de Ensino"
                        + "(apoio,manutenção e aulas) é inferior à 8 horas") | irregularidade.equals("A carga horária dedicada a Atividades de Ensino"
                                + "(apoio,manutenção e aulas) é superior à 12 horas") | irregularidade.equals("A carga horária dedicada a Atividades de Ensino"
                                + "(apoio,manutenção e aulas) é superior à 24 horas")) && ptd.getCampoJustificativaGeral().isEmpty() == true) {

                    nomeCaixaDialogo = "avisoIrregularidadeDialog";

                } else if (irregularidade.equals("A carga horária do PTD diverge com seu regime de trabalho de 20h") | irregularidade.equals("A carga horária do PTD diverge com seu regime de trabalho de 40h")) {

                    nomeCaixaDialogo = "avisoIrregularidadeDialog";

                } else {

                    nomeCaixaDialogo = "confirmacaoIrregularidadeDialog";

                }
            }
        } else {
            nomeCaixaDialogo = "conclusãoDialog";
        }

        return nomeCaixaDialogo;

    }

    /*
     Verifica a ocorrência de erros que caracterizam falhas no momento de inserção de 
     dados inseridos
     */
    public void verificarErros() {
        errosTabelaAdministrativas = new ArrayList();
        errosTabelaApoioEnsino = new ArrayList();
        errosTabelaAtividadesASeremPropostas = new ArrayList();
        errosTabelaAula = new ArrayList();
        errosTabelaManuEnsino = new ArrayList();
        errosTabelaOutrasAtividades = new ArrayList();
        errosTabelaPesquisaExtensaoAutor = new ArrayList();
        errosTabelaPesquisaExtensaoColaborador = new ArrayList();

        for (Administracao adm : getPtd().getAdministrativas()) {

            if (adm.getHorariosAdministracao().isEmpty() == true) {

                errosTabelaAdministrativas.add("Você precisa adicionar um horário à atividade que pertence a seção"
                        + "Atividades Administrativas");
            }

            for (Horario hadm : adm.getHorariosAdministracao()) {

                for (Apoio ap : getPtd().getApoios()) {

                    for (Horario hap : ap.getHorariosApoio()) {
                        if (hadm.getDiaSemana().equals(hap.getDiaSemana())) {
                            if ((hadm.getHoraInicio().getTime() < hap.getHoraTermino().getTime() && hadm.getHoraInicio().getTime() >= hap.getHoraInicio().getTime()) | (hadm.getHoraTermino().getTime() <= hap.getHoraTermino().getTime() && hadm.getHoraTermino().getTime() > hap.getHoraInicio().getTime())) {

                                errosTabelaAdministrativas.add("Há conflitos entre horários de execução com "
                                        + "os horários na seção Apoio ao Ensino");

                            }
                        }

                    }
                }

                for (Aula a : getPtd().getAulas()) {

                    for (Horario ha : a.getHorariosAula()) {
                        if (hadm.getDiaSemana().equals(ha.getDiaSemana())) {
                            if ((hadm.getHoraInicio().getTime() < ha.getHoraTermino().getTime() && hadm.getHoraInicio().getTime() >= ha.getHoraInicio().getTime()) | (hadm.getHoraTermino().getTime() <= ha.getHoraTermino().getTime() && hadm.getHoraTermino().getTime() > ha.getHoraInicio().getTime())) {

                                errosTabelaAdministrativas.add("Há conflitos entre horários de execução com "
                                        + "os horários na seção Aulas");

                            }
                        }

                    }

                    for (ManutencaoEnsino mE : getPtd().getManutencoesEnsino()) {

                        for (Horario hme : mE.getHorariosManutecao()) {
                            if (hadm.getDiaSemana().equals(hme.getDiaSemana())) {
                                if ((hadm.getHoraInicio().getTime() < hme.getHoraTermino().getTime() && hadm.getHoraInicio().getTime() >= hme.getHoraInicio().getTime()) | (hadm.getHoraTermino().getTime() <= hme.getHoraTermino().getTime() && hadm.getHoraTermino().getTime() > hme.getHoraInicio().getTime())) {

                                    errosTabelaAdministrativas.add("Há conflitos entre horários de execução com "
                                            + "os horários na seção Manutenção ao Ensino");
                                }
                            }

                        }
                    }
                    for (OutroTipoAtividade oTA : getPtd().getOutrosTiposAtividades()) {

                        for (Horario hota : oTA.getHorariosOutroTipoAtividade()) {
                            if (hadm.getDiaSemana().equals(hota.getDiaSemana())) {
                                if ((hadm.getHoraInicio().getTime() < hota.getHoraTermino().getTime() && hadm.getHoraInicio().getTime() >= hota.getHoraInicio().getTime()) | (hadm.getHoraTermino().getTime() <= hota.getHoraTermino().getTime() && hadm.getHoraTermino().getTime() > hota.getHoraInicio().getTime())) {

                                    errosTabelaAdministrativas.add("Há conflitos entre horários de execução com "
                                            + "os horários na seção Outras atividades desenvolvidas no campus");

                                }
                            }

                        }
                    }

                    for (Participacao p : getPtd().getParticipacoes()) {

                        for (Horario hp : p.getHorariosParticipacao()) {
                            if (hadm.getDiaSemana().equals(hp.getDiaSemana())) {
                                if ((hadm.getHoraInicio().getTime() < hp.getHoraTermino().getTime() && hadm.getHoraInicio().getTime() >= hp.getHoraInicio().getTime()) | (hadm.getHoraTermino().getTime() <= hp.getHoraTermino().getTime() && hadm.getHoraTermino().getTime() > hp.getHoraInicio().getTime())) {

                                    errosTabelaAdministrativas.add("Há conflitos entre horários de execução com "
                                            + "os horários na seções de participação em Projetos de Pesquisa e/ou Extensão ");

                                }
                            }

                        }
                    }
                    if (hadm.getHoraInicio().getTime() > hadm.getHoraTermino().getTime()) {
                        errosTabelaAdministrativas.add("Você inseriu um horário de início posterior ao de término!");

                    } else if (hadm.getHoraInicio().getTime() == 0) {
                        errosTabelaAdministrativas.add("Insira um Horário de Início!");

                    } else if (hadm.getHoraTermino().getTime() == 0) {
                        errosTabelaAdministrativas.add("Insira um Horário de Término!");

                    } else if (adm.getCargaHorariaSemanalAdministracao() == 0) {
                        errosTabelaAdministrativas.add("Carga Horária Nula!");

                    }
                }
                if (adm.getTipoAdministracao().getRotulo().equalsIgnoreCase("")) {
                    errosTabelaAdministrativas.add("Adicione um tipo à atividade de Administração");

                }
            }
        }

        for (Apoio apoio : getPtd().getApoios()) {

            if (apoio.getHorariosApoio().isEmpty() == true) {

                errosTabelaApoioEnsino.add("Você precisa adicionar um horário à atividade que pertence a seção Apoio ao Ensino");
            }

            for (Horario hapoio : apoio.getHorariosApoio()) {

                for (Administracao adm : getPtd().getAdministrativas()) {

                    for (Horario hadmin : adm.getHorariosAdministracao()) {
                        if (hapoio.getDiaSemana().equals(hadmin.getDiaSemana())) {
                            if ((hapoio.getHoraInicio().getTime() < hadmin.getHoraTermino().getTime() && hapoio.getHoraInicio().getTime() >= hadmin.getHoraInicio().getTime()) | (hapoio.getHoraTermino().getTime() <= hadmin.getHoraTermino().getTime() && hapoio.getHoraTermino().getTime() > hadmin.getHoraInicio().getTime())) {

                                errosTabelaApoioEnsino.add("Há conflitos entre horários de execução com "
                                        + "os horários na seção de Atividades Administrativas");

                            }
                        }

                    }
                }
                for (Aula a : getPtd().getAulas()) {

                    for (Horario ha : a.getHorariosAula()) {
                        if (hapoio.getDiaSemana().equals(ha.getDiaSemana())) {
                            if ((hapoio.getHoraInicio().getTime() < ha.getHoraTermino().getTime() && hapoio.getHoraInicio().getTime() >= ha.getHoraInicio().getTime()) | (hapoio.getHoraTermino().getTime() <= ha.getHoraTermino().getTime() && hapoio.getHoraTermino().getTime() > ha.getHoraInicio().getTime())) {

                                errosTabelaApoioEnsino.add("Há conflitos entre horários de execução com "
                                        + "os horários na seção Aulas");

                            }
                        }

                    }
                }
                for (ManutencaoEnsino mE : getPtd().getManutencoesEnsino()) {

                    for (Horario hme : mE.getHorariosManutecao()) {
                        if (hapoio.getDiaSemana().equals(hme.getDiaSemana())) {
                            if ((hapoio.getHoraInicio().getTime() < hme.getHoraTermino().getTime() && hapoio.getHoraInicio().getTime() >= hme.getHoraInicio().getTime()) | (hapoio.getHoraTermino().getTime() <= hme.getHoraTermino().getTime() && hapoio.getHoraTermino().getTime() > hme.getHoraInicio().getTime())) {

                                errosTabelaApoioEnsino.add("Há conflitos entre horários de execução com "
                                        + "os horários na seção Manutenção ao Ensino");
                            }
                        }

                    }
                }
                for (OutroTipoAtividade oTA : getPtd().getOutrosTiposAtividades()) {

                    for (Horario hota : oTA.getHorariosOutroTipoAtividade()) {
                        if (hapoio.getDiaSemana().equals(hota.getDiaSemana())) {
                            if ((hapoio.getHoraInicio().getTime() < hota.getHoraTermino().getTime() && hapoio.getHoraInicio().getTime() >= hota.getHoraInicio().getTime()) | (hapoio.getHoraTermino().getTime() <= hota.getHoraTermino().getTime() && hapoio.getHoraTermino().getTime() > hota.getHoraInicio().getTime())) {

                                errosTabelaApoioEnsino.add("Há conflitos entre horários de execução com "
                                        + "os horários na seção Outros atividades desenvolvidas no campus");

                            }
                        }

                    }
                }
                for (Participacao p : getPtd().getParticipacoes()) {

                    for (Horario hp : p.getHorariosParticipacao()) {
                        if (hapoio.getDiaSemana().equals(hp.getDiaSemana())) {
                            if ((hapoio.getHoraInicio().getTime() < hp.getHoraTermino().getTime() && hapoio.getHoraInicio().getTime() >= hp.getHoraInicio().getTime()) | (hapoio.getHoraTermino().getTime() <= hp.getHoraTermino().getTime() && hapoio.getHoraTermino().getTime() > hp.getHoraInicio().getTime())) {

                                errosTabelaApoioEnsino.add("Há conflitos entre horários de execução com "
                                        + "os horários na seções de participação em Projetos de Pesquisa e/ou Extensão ");

                            }
                        }

                    }
                }
                if (hapoio.getHoraInicio().getTime() > hapoio.getHoraTermino().getTime()) {
                    errosTabelaApoioEnsino.add("Você inseriu um horário de início posterior ao de término!");

                } else if (hapoio.getHoraInicio().getTime() == 0) {
                    errosTabelaApoioEnsino.add("Insira um Horário de Início!");

                } else if (hapoio.getHoraTermino().getTime() == 0) {
                    errosTabelaApoioEnsino.add("Insira um Horário de Término!");

                } else if (apoio.getCargaHorariaSemanalApoio() == 0) {
                    errosTabelaApoioEnsino.add("Carga Horária Nula!");

                }
            }
            if (apoio.getTipoApoio().getRotulo().equalsIgnoreCase("")) {
                errosTabelaApoioEnsino.add("Adicione um tipo à atividadade de apoio");

            }
        }

        for (AtividadeASerProposta aSP : getPtd().getAtividadesASeremPropostas()) {

            if (aSP.getHorariosAtividadesASerProposta().isEmpty() == true) {

                errosTabelaAtividadesASeremPropostas.add("Você precisa adicionar um horário à atividade que pertence a seção "
                        + "Outras atividades que pretendo propor no semestre");
            }

            for (Horario h : aSP.getHorariosAtividadesASerProposta()) {

                if (h.getHoraInicio().getTime() > h.getHoraTermino().getTime()) {
                    errosTabelaAtividadesASeremPropostas.add("Você inseriu um horário de início posterior ao de término!");

                } else if (h.getHoraInicio().getTime() == 0) {
                    errosTabelaAtividadesASeremPropostas.add("Insira um Horário de Início!");

                } else if (h.getHoraTermino().getTime() == 0) {
                    errosTabelaAtividadesASeremPropostas.add("Insira um Horário de Término!");

                } else if (aSP.getCargaHorariaSemanalAtividadeASerProposta() == 0) {
                    errosTabelaAtividadesASeremPropostas.add("Carga Horária Nula!");

                }
            }
            if (aSP.getRotulo().equalsIgnoreCase("")) {
                errosTabelaAtividadesASeremPropostas.add("Adicione um rótulo à atividade a ser proposta");

            }
        }

        for (Aula aula : getPtd().getAulas()) {

            if (aula.getHorariosAula().isEmpty() == true) {

                errosTabelaAula.add("Você precisa adicionar um horário à atividade que pertence a seção Aulas");
            }

            double cargaHoraHorario = 0;
            double minTotal = 0;
            for (Horario h : aula.getHorariosAula()) {

                double minInicio = h.getHoraInicio().getMinutes();
                double minTermino = h.getHoraTermino().getMinutes();
                double horaInicio = h.getHoraInicio().getHours();
                double horaTermino = h.getHoraTermino().getHours();

                cargaHoraHorario = cargaHoraHorario + (horaTermino - horaInicio);
                if (minTermino > minInicio) {
                    minTotal = minTermino - minInicio;
                    cargaHoraHorario = cargaHoraHorario + (minTotal / 60);
                }
                if (minTermino < minInicio) {
                    minTotal = (60 - minInicio) + minTermino;
                    cargaHoraHorario = (cargaHoraHorario + (minTotal / 60)) - 1;
                }
            }
            if (!aula.getHorariosAula().isEmpty()) {
                if (cargaHoraHorario > (aula.getCargaHorariaTotal() + 0.2) | cargaHoraHorario < (aula.getCargaHorariaTotal() - 0.2)) {
                    errosTabelaAula.add("A carga horária fornecida é diferente da carga resultante dos horários fornecidos em aula! Atualize a atividade ou seus horários.");
                }
            }

            for (Horario ha : aula.getHorariosAula()) {

                for (Administracao adm : getPtd().getAdministrativas()) {

                    for (Horario hadmin : adm.getHorariosAdministracao()) {
                        if (ha.getDiaSemana().equals(hadmin.getDiaSemana())) {
                            if ((ha.getHoraInicio().getTime() < hadmin.getHoraTermino().getTime() && ha.getHoraInicio().getTime() >= hadmin.getHoraInicio().getTime()) | (ha.getHoraTermino().getTime() <= hadmin.getHoraTermino().getTime() && ha.getHoraTermino().getTime() > hadmin.getHoraInicio().getTime())) {

                                errosTabelaAula.add("Há conflitos entre horários de execução com "
                                        + "os horários na seção de Atividades Administrativas");

                            }
                        }

                    }
                }
                for (Apoio ap : getPtd().getApoios()) {

                    for (Horario hap : ap.getHorariosApoio()) {
                        if (ha.getDiaSemana().equals(hap.getDiaSemana())) {
                            if ((ha.getHoraInicio().getTime() < hap.getHoraTermino().getTime() && ha.getHoraInicio().getTime() >= hap.getHoraInicio().getTime()) | (ha.getHoraTermino().getTime() <= hap.getHoraTermino().getTime() && ha.getHoraTermino().getTime() > hap.getHoraInicio().getTime())) {

                                errosTabelaAula.add("Há conflitos entre horários de execução com "
                                        + "os horários na seção Apoio ao Ensino");

                            }
                        }

                    }
                }
                for (ManutencaoEnsino mE : getPtd().getManutencoesEnsino()) {

                    for (Horario hme : mE.getHorariosManutecao()) {
                        if (ha.getDiaSemana().equals(hme.getDiaSemana())) {
                            if ((ha.getHoraInicio().getTime() < hme.getHoraTermino().getTime() && ha.getHoraInicio().getTime() >= hme.getHoraInicio().getTime()) | (ha.getHoraTermino().getTime() <= hme.getHoraTermino().getTime() && ha.getHoraTermino().getTime() > hme.getHoraInicio().getTime())) {

                                errosTabelaAula.add("Há conflitos entre horários de execução com "
                                        + "os horários na seção Manutenção ao Ensino");
                            }
                        }

                    }
                }
                for (OutroTipoAtividade oTA : getPtd().getOutrosTiposAtividades()) {

                    for (Horario hota : oTA.getHorariosOutroTipoAtividade()) {
                        if (ha.getDiaSemana().equals(hota.getDiaSemana())) {
                            if ((ha.getHoraInicio().getTime() < hota.getHoraTermino().getTime() && ha.getHoraInicio().getTime() >= hota.getHoraInicio().getTime()) | (ha.getHoraTermino().getTime() <= hota.getHoraTermino().getTime() && ha.getHoraTermino().getTime() > hota.getHoraInicio().getTime())) {

                                errosTabelaAula.add("Há conflitos entre horários de execução com "
                                        + "os horários na seção Outras atividades desenvolvidas no campus");

                            }
                        }

                    }
                }
                for (Participacao p : getPtd().getParticipacoes()) {

                    for (Horario hp : p.getHorariosParticipacao()) {
                        if (ha.getDiaSemana().equals(hp.getDiaSemana())) {
                            if ((ha.getHoraInicio().getTime() < hp.getHoraTermino().getTime() && ha.getHoraInicio().getTime() >= hp.getHoraInicio().getTime()) | (ha.getHoraTermino().getTime() <= hp.getHoraTermino().getTime() && ha.getHoraTermino().getTime() > hp.getHoraInicio().getTime())) {

                                errosTabelaAula.add("Há conflitos entre horários de execução com "
                                        + "os horários na seções de participação em Projetos de Pesquisa e/ou Extensão ");

                            }
                        }

                    }
                }

                if (ha.getHoraInicio().getTime() > ha.getHoraTermino().getTime()) {
                    errosTabelaAula.add("Você inseriu um horário de início posterior ao de término!");

                } else if (ha.getHoraInicio().getTime() == 0) {
                    errosTabelaAula.add("Insira um Horário de Início!");

                } else if (ha.getHoraTermino().getTime() == 0) {
                    errosTabelaAula.add("Insira um Horário de Término!");

                } else if (aula.getCargaHorariaTotal() == 0) {
                    errosTabelaAula.add("Carga Horária Nula!");

                }
            }
            if (aula.getTipoOferta().getRotulo().equalsIgnoreCase("")) {
                errosTabelaAula.add("Adicione um tipo de oferta à atividade aula!");
            } else if (aula.getCurso() == null) {
                errosTabelaAula.add("Adicione um curso à atividade aula!");
            } else if (aula.getComponenteCurricular().equalsIgnoreCase("")) {
                errosTabelaAula.add("Adicione um título para o componente curricular!");
            }
        }

        for (ManutencaoEnsino mE : getPtd().getManutencoesEnsino()) {

            if (mE.getHorariosManutecao().isEmpty() == true) {

                errosTabelaManuEnsino.add("Você precisa adicionar um horário à atividade que pertence a seção Manutenção ao Ensino");
            }

            for (Horario hME : mE.getHorariosManutecao()) {

                for (Administracao adm : getPtd().getAdministrativas()) {

                    for (Horario hadmin : adm.getHorariosAdministracao()) {
                        if (hME.getDiaSemana().equals(hadmin.getDiaSemana())) {
                            if ((hME.getHoraInicio().getTime() < hadmin.getHoraTermino().getTime() && hME.getHoraInicio().getTime() >= hadmin.getHoraInicio().getTime()) | (hME.getHoraTermino().getTime() <= hadmin.getHoraTermino().getTime() && hME.getHoraTermino().getTime() > hadmin.getHoraInicio().getTime())) {

                                errosTabelaManuEnsino.add("Há conflitos entre horários de execução com "
                                        + "os horários na seção de Atividades Administrativas");

                            }
                        }

                    }
                }
                for (Apoio ap : getPtd().getApoios()) {

                    for (Horario hap : ap.getHorariosApoio()) {
                        if (hME.getDiaSemana().equals(hap.getDiaSemana())) {
                            if ((hME.getHoraInicio().getTime() < hap.getHoraTermino().getTime() && hME.getHoraInicio().getTime() >= hap.getHoraInicio().getTime()) | (hME.getHoraTermino().getTime() <= hap.getHoraTermino().getTime() && hME.getHoraTermino().getTime() > hap.getHoraInicio().getTime())) {

                                errosTabelaManuEnsino.add("Há conflitos entre horários de execução com "
                                        + "os horários na seção Apoio ao Ensino");

                            }
                        }

                    }
                }
                for (Aula aula : getPtd().getAulas()) {

                    for (Horario haula : aula.getHorariosAula()) {
                        if (hME.getDiaSemana().equals(haula.getDiaSemana())) {
                            if ((hME.getHoraInicio().getTime() < haula.getHoraTermino().getTime() && hME.getHoraInicio().getTime() >= haula.getHoraInicio().getTime()) | (hME.getHoraTermino().getTime() <= haula.getHoraTermino().getTime() && hME.getHoraTermino().getTime() > haula.getHoraInicio().getTime())) {

                                errosTabelaManuEnsino.add("Há conflitos entre horários de execução com"
                                        + " os horários na seção Aulas");
                            }
                        }

                    }
                }
                for (OutroTipoAtividade oTa : getPtd().getOutrosTiposAtividades()) {

                    for (Horario hoTa : oTa.getHorariosOutroTipoAtividade()) {
                        if (hME.getDiaSemana().equals(hoTa.getDiaSemana())) {
                            if ((hME.getHoraInicio().getTime() < hoTa.getHoraTermino().getTime() && hME.getHoraInicio().getTime() >= hoTa.getHoraInicio().getTime()) | (hME.getHoraTermino().getTime() <= hoTa.getHoraTermino().getTime() && hME.getHoraTermino().getTime() > hoTa.getHoraInicio().getTime())) {

                                errosTabelaManuEnsino.add("Há conflitos entre horários de execução com "
                                        + "os horários na seção Outros atividades desenvolvidas no campus");
                            }
                        }

                    }
                }
                for (Participacao p : getPtd().getParticipacoes()) {

                    for (Horario hp : p.getHorariosParticipacao()) {
                        if (hME.getDiaSemana().equals(hp.getDiaSemana())) {
                            if ((hME.getHoraInicio().getTime() < hp.getHoraTermino().getTime() && hME.getHoraInicio().getTime() >= hp.getHoraInicio().getTime()) | (hME.getHoraTermino().getTime() <= hp.getHoraTermino().getTime() && hME.getHoraTermino().getTime() > hp.getHoraInicio().getTime())) {

                                errosTabelaManuEnsino.add("Há conflitos entre horários de execução com "
                                        + "os horários na seções de participação em Projetos de Pesquisa e/ou Extensão ");

                            }
                        }

                    }
                }

                if (hME.getHoraInicio().getTime() > hME.getHoraTermino().getTime()) {
                    errosTabelaManuEnsino.add("Você inseriu um horário de início posterior ao de término!");
                } else if (hME.getHoraInicio().getTime() == 0) {
                    errosTabelaManuEnsino.add("Insira um Horário de Início!");
                } else if (hME.getHoraTermino().getTime() == 0) {
                    errosTabelaManuEnsino.add("Insira um Horário de Término!");
                } else if (mE.getCargaHorariaSemanalManutencaoEnsino() == 0) {
                    errosTabelaManuEnsino.add("Carga Horária Nula!");
                }
            }
            if (mE.getTipoManutencao().getRotulo().equalsIgnoreCase("")) {
                errosTabelaManuEnsino.add("Adicione um tipo à atividade de manutenção de ensino");
            }

        }

        for (OutroTipoAtividade oTA : getPtd().getOutrosTiposAtividades()) {

            if (oTA.getHorariosOutroTipoAtividade().isEmpty() == true) {

                errosTabelaOutrasAtividades.add("Você precisa adicionar um horário à atividade que pertence a seção Outras"
                        + "atividades desenvolvidas no campus");
            }

            for (Horario hoTa : oTA.getHorariosOutroTipoAtividade()) {

                for (Administracao adm : getPtd().getAdministrativas()) {

                    for (Horario hadmin : adm.getHorariosAdministracao()) {
                        if (hoTa.getDiaSemana().equals(hadmin.getDiaSemana())) {
                            if ((hoTa.getHoraInicio().getTime() < hadmin.getHoraTermino().getTime() && hoTa.getHoraInicio().getTime() >= hadmin.getHoraInicio().getTime()) | (hoTa.getHoraTermino().getTime() <= hadmin.getHoraTermino().getTime() && hoTa.getHoraTermino().getTime() > hadmin.getHoraInicio().getTime())) {

                                errosTabelaOutrasAtividades.add("Há conflitos entre horários de execução com "
                                        + "os horários na seção de Atividades Administrativas");

                            }
                        }

                    }
                }
                for (Apoio ap : getPtd().getApoios()) {

                    for (Horario hap : ap.getHorariosApoio()) {
                        if (hoTa.getDiaSemana().equals(hap.getDiaSemana())) {
                            if ((hoTa.getHoraInicio().getTime() < hap.getHoraTermino().getTime() && hoTa.getHoraInicio().getTime() >= hap.getHoraInicio().getTime()) | (hoTa.getHoraTermino().getTime() <= hap.getHoraTermino().getTime() && hoTa.getHoraTermino().getTime() > hap.getHoraInicio().getTime())) {

                                errosTabelaOutrasAtividades.add("Há conflitos entre horários de execução com "
                                        + "os horários na seção Apoio ao Ensino");

                            }
                        }

                    }
                }
                for (ManutencaoEnsino mE : getPtd().getManutencoesEnsino()) {

                    for (Horario hme : mE.getHorariosManutecao()) {
                        if (hoTa.getDiaSemana().equals(hme.getDiaSemana())) {
                            if ((hoTa.getHoraInicio().getTime() < hme.getHoraTermino().getTime() && hoTa.getHoraInicio().getTime() >= hme.getHoraInicio().getTime()) | (hoTa.getHoraTermino().getTime() <= hme.getHoraTermino().getTime() && hoTa.getHoraTermino().getTime() > hme.getHoraInicio().getTime())) {

                                errosTabelaOutrasAtividades.add("Há conflitos entre horários de execução com "
                                        + "os horários na seção Manutenção ao Ensino");
                            }
                        }

                    }
                }
                for (Aula aula : getPtd().getAulas()) {

                    for (Horario haulas : aula.getHorariosAula()) {
                        if (hoTa.getDiaSemana().equals(haulas.getDiaSemana())) {
                            if ((hoTa.getHoraInicio().getTime() < haulas.getHoraTermino().getTime() && hoTa.getHoraInicio().getTime() >= haulas.getHoraInicio().getTime()) | (hoTa.getHoraTermino().getTime() <= haulas.getHoraTermino().getTime() && hoTa.getHoraTermino().getTime() > haulas.getHoraInicio().getTime())) {

                                errosTabelaOutrasAtividades.add("Há conflitos entre horários de execução com "
                                        + "os horários na seção Aula");

                            }
                        }

                    }
                }
                for (Participacao p : getPtd().getParticipacoes()) {

                    for (Horario hp : p.getHorariosParticipacao()) {
                        if (hoTa.getDiaSemana().equals(hp.getDiaSemana())) {
                            if ((hoTa.getHoraInicio().getTime() < hp.getHoraTermino().getTime() && hoTa.getHoraInicio().getTime() >= hp.getHoraInicio().getTime()) | (hoTa.getHoraTermino().getTime() <= hp.getHoraTermino().getTime() && hoTa.getHoraTermino().getTime() > hp.getHoraInicio().getTime())) {

                                errosTabelaOutrasAtividades.add("Há conflitos entre horários de execução com "
                                        + "os horários nas seções de participação em Projetos de Pesquisa e/ou Extensão ");

                            }
                        }

                    }
                }

                if (hoTa.getHoraInicio().getTime() > hoTa.getHoraTermino().getTime()) {
                    errosTabelaOutrasAtividades.add("Você inseriu um horário de início posterior ao de término!");
                } else if (hoTa.getHoraInicio().getTime() == 0) {
                    errosTabelaOutrasAtividades.add("Insira um Horário de Início!");
                } else if (hoTa.getHoraTermino().getTime() == 0) {
                    errosTabelaOutrasAtividades.add("Insira um Horário de Término!");
                }
                if (oTA.getCargaHorariaSemanalOutroTipoAtividade() == 0) {
                    errosTabelaOutrasAtividades.add("Carga Horária Nula!");
                }
            }
            if (oTA.getRotulo().equalsIgnoreCase("")) {
                errosTabelaOutrasAtividades.add("Insira um título!");
            }
        }

        for (Participacao p : getPtd().getParticipacoes()) {

            if (p.getRotulo().equalsIgnoreCase("Autor")) {

                if (p.getHorariosParticipacao().isEmpty() == true) {

                    errosTabelaPesquisaExtensaoAutor.add("Você precisa adicionar um horário à atividade que pertence nas seções de participação em Projetos de Pesquisa e/ou Extensão ");
                }
                for (Horario hpartAutor : p.getHorariosParticipacao()) {

                    for (Administracao adm : getPtd().getAdministrativas()) {

                        for (Horario hadmin : adm.getHorariosAdministracao()) {
                            if (hpartAutor.getDiaSemana().equals(hadmin.getDiaSemana())) {
                                if ((hpartAutor.getHoraInicio().getTime() < hadmin.getHoraTermino().getTime() && hpartAutor.getHoraInicio().getTime() >= hadmin.getHoraInicio().getTime()) | (hpartAutor.getHoraTermino().getTime() <= hadmin.getHoraTermino().getTime() && hpartAutor.getHoraTermino().getTime() > hadmin.getHoraInicio().getTime())) {

                                    errosTabelaPesquisaExtensaoAutor.add("Há conflitos entre horários de execução com "
                                            + "os horários na seção de Atividades Administrativas");

                                }
                            }

                        }
                    }
                    for (Apoio ap : getPtd().getApoios()) {

                        for (Horario hap : ap.getHorariosApoio()) {
                            if (hpartAutor.getDiaSemana().equals(hap.getDiaSemana())) {
                                if ((hpartAutor.getHoraInicio().getTime() < hap.getHoraTermino().getTime() && hpartAutor.getHoraInicio().getTime() >= hap.getHoraInicio().getTime()) | (hpartAutor.getHoraTermino().getTime() <= hap.getHoraTermino().getTime() && hpartAutor.getHoraTermino().getTime() > hap.getHoraInicio().getTime())) {

                                    errosTabelaPesquisaExtensaoAutor.add("Há conflitos entre horários de execução com"
                                            + " os horários na seção Apoio ao Ensino");

                                }
                            }

                        }
                    }
                    for (ManutencaoEnsino mE : getPtd().getManutencoesEnsino()) {

                        for (Horario hme : mE.getHorariosManutecao()) {
                            if (hpartAutor.getDiaSemana().equals(hme.getDiaSemana())) {
                                if ((hpartAutor.getHoraInicio().getTime() < hme.getHoraTermino().getTime() && hpartAutor.getHoraInicio().getTime() >= hme.getHoraInicio().getTime()) | (hpartAutor.getHoraTermino().getTime() <= hme.getHoraTermino().getTime() && hpartAutor.getHoraTermino().getTime() > hme.getHoraInicio().getTime())) {

                                    errosTabelaPesquisaExtensaoAutor.add("Há conflitos entre horários de execução com "
                                            + "os horários na seção de Manutenção ao Ensino");
                                }
                            }

                        }
                    }
                    for (Aula aula : getPtd().getAulas()) {

                        for (Horario haulas : aula.getHorariosAula()) {
                            if (hpartAutor.getDiaSemana().equals(haulas.getDiaSemana())) {
                                if ((hpartAutor.getHoraInicio().getTime() < haulas.getHoraTermino().getTime() && hpartAutor.getHoraInicio().getTime() >= haulas.getHoraInicio().getTime()) | (hpartAutor.getHoraTermino().getTime() <= haulas.getHoraTermino().getTime() && hpartAutor.getHoraTermino().getTime() > haulas.getHoraInicio().getTime())) {

                                    errosTabelaPesquisaExtensaoAutor.add("Há conflitos entre horários de execução com "
                                            + "os horários na seção Aulas");

                                }
                            }

                        }
                    }
                    for (OutroTipoAtividade oTA : getPtd().getOutrosTiposAtividades()) {

                        for (Horario hoTa : oTA.getHorariosOutroTipoAtividade()) {
                            if (hpartAutor.getDiaSemana().equals(hoTa.getDiaSemana())) {
                                if ((hpartAutor.getHoraInicio().getTime() < hoTa.getHoraTermino().getTime() && hpartAutor.getHoraInicio().getTime() >= hoTa.getHoraInicio().getTime()) | (hpartAutor.getHoraTermino().getTime() <= hoTa.getHoraTermino().getTime() && hpartAutor.getHoraTermino().getTime() > hoTa.getHoraInicio().getTime())) {

                                    errosTabelaPesquisaExtensaoAutor.add("Há conflitos entre horários de execução com "
                                            + "os horários na seção Outras atividades desenvolvidas no campus");

                                }
                            }

                        }
                    }

                    if (hpartAutor.getHoraInicio().getTime() > hpartAutor.getHoraTermino().getTime() && p.getRotulo().equals("Autor")) {
                        errosTabelaPesquisaExtensaoAutor.add("Você inseriu um horário de início posterior ao de término!");

                    } else if (hpartAutor.getHoraInicio().getTime() == 0) {
                        errosTabelaPesquisaExtensaoAutor.add("Insira um Horário de Início!");

                    } else if (hpartAutor.getHoraTermino().getTime() == 0) {
                        errosTabelaPesquisaExtensaoAutor.add("Insira um Horário de Término!");

                    } else if (p.getCargaHorariaSemanalParticipacao() == 0) {
                        errosTabelaPesquisaExtensaoAutor.add("Carga Horária Nula!");

                    }
                }
                if (p.getProjetoPesquisaExtensao().getTituloProcesso().equals("")) {
                    errosTabelaPesquisaExtensaoAutor.add("Adicione um título ao projeto de pesquisa/extensão");

                }
                for (Participacao p2 : getPtd().getParticipacoes()) {
                    if (p.getProjetoPesquisaExtensao().getTituloProcesso().equals(p2.getProjetoPesquisaExtensao().getTituloProcesso()) && p.getIdParticipacao() != p2.getIdParticipacao()) {
                        errosTabelaPesquisaExtensaoAutor.add("Você tem mais de uma participação no mesmo projeto, caso trabalhe nele em mais de um dia, adicione um novo horário!");

                    } else if (p.getProjetoPesquisaExtensao().getNumeroProcesso().equals(p2.getProjetoPesquisaExtensao().getNumeroProcesso()) && p.getIdParticipacao() != p2.getIdParticipacao()) {
                        errosTabelaPesquisaExtensaoAutor.add("Você tem mais de uma participação no mesmo projeto, caso trabalhe nele em mais de um dia, adicione um novo horário!");
                    }
                }
            } else if (p.getRotulo().equalsIgnoreCase("Colaborador")) {
                if (p.getHorariosParticipacao().isEmpty() == true) {

                    errosTabelaPesquisaExtensaoColaborador.add("Você precisa adicionar um horário à atividade que pertence nas seções de participação em Projetos de Pesquisa e/ou Extensão ");
                }
                for (Horario hpartColab : p.getHorariosParticipacao()) {

                    for (Administracao adm : getPtd().getAdministrativas()) {

                        for (Horario hadmin : adm.getHorariosAdministracao()) {
                            if (hpartColab.getDiaSemana().equals(hadmin.getDiaSemana())) {
                                if ((hpartColab.getHoraInicio().getTime() < hadmin.getHoraTermino().getTime() && hpartColab.getHoraInicio().getTime() >= hadmin.getHoraInicio().getTime()) | (hpartColab.getHoraTermino().getTime() <= hadmin.getHoraTermino().getTime() && hpartColab.getHoraTermino().getTime() > hadmin.getHoraInicio().getTime())) {

                                    errosTabelaPesquisaExtensaoColaborador.add("Há conflitos entre horários de execução com os horários na seção de Atividades Administrativas");

                                }
                            }

                        }
                    }
                    for (Apoio ap : getPtd().getApoios()) {

                        for (Horario hap : ap.getHorariosApoio()) {
                            if (hpartColab.getDiaSemana().equals(hap.getDiaSemana())) {
                                if ((hpartColab.getHoraInicio().getTime() < hap.getHoraTermino().getTime() && hpartColab.getHoraInicio().getTime() >= hap.getHoraInicio().getTime()) | (hpartColab.getHoraTermino().getTime() <= hap.getHoraTermino().getTime() && hpartColab.getHoraTermino().getTime() > hap.getHoraInicio().getTime())) {

                                    errosTabelaPesquisaExtensaoColaborador.add("Há conflitos entre horários de execução com "
                                            + "os horários na seção Apoio ao Ensino");

                                }
                            }

                        }
                    }
                    for (ManutencaoEnsino mE : getPtd().getManutencoesEnsino()) {

                        for (Horario hme : mE.getHorariosManutecao()) {
                            if (hpartColab.getDiaSemana().equals(hme.getDiaSemana())) {
                                if ((hpartColab.getHoraInicio().getTime() < hme.getHoraTermino().getTime() && hpartColab.getHoraInicio().getTime() >= hme.getHoraInicio().getTime()) | (hpartColab.getHoraTermino().getTime() <= hme.getHoraTermino().getTime() && hpartColab.getHoraTermino().getTime() > hme.getHoraInicio().getTime())) {

                                    errosTabelaPesquisaExtensaoColaborador.add("Há conflitos entre horários de execução com "
                                            + "os horários na seção de Manutenção ao Ensino");
                                }
                            }

                        }
                    }
                    for (Aula aula : getPtd().getAulas()) {

                        for (Horario haulas : aula.getHorariosAula()) {
                            if (hpartColab.getDiaSemana().equals(haulas.getDiaSemana())) {
                                if ((hpartColab.getHoraInicio().getTime() < haulas.getHoraTermino().getTime() && hpartColab.getHoraInicio().getTime() >= haulas.getHoraInicio().getTime()) | (hpartColab.getHoraTermino().getTime() <= haulas.getHoraTermino().getTime() && hpartColab.getHoraTermino().getTime() > haulas.getHoraInicio().getTime())) {

                                    errosTabelaPesquisaExtensaoColaborador.add("Há conflitos entre horários de execução com "
                                            + "os horários na seção Aulas");

                                }
                            }

                        }
                    }
                    for (OutroTipoAtividade oTA : getPtd().getOutrosTiposAtividades()) {

                        for (Horario hoTa : oTA.getHorariosOutroTipoAtividade()) {

                            if (hpartColab.getDiaSemana().equals(hoTa.getDiaSemana())) {
                                if ((hpartColab.getHoraInicio().getTime() < hoTa.getHoraTermino().getTime() && hpartColab.getHoraInicio().getTime() >= hoTa.getHoraInicio().getTime()) | (hpartColab.getHoraTermino().getTime() <= hoTa.getHoraTermino().getTime() && hpartColab.getHoraTermino().getTime() > hoTa.getHoraInicio().getTime())) {

                                    errosTabelaPesquisaExtensaoColaborador.add("Há conflitos entre horários de execução com "
                                            + "os horários na seção Outras atividades desenvolvidas no campus");

                                }
                            }

                        }
                    }

                    if (hpartColab.getHoraInicio().getTime() > hpartColab.getHoraTermino().getTime() && p.getRotulo().equals("Autor")) {
                        errosTabelaPesquisaExtensaoColaborador.add("Você inseriu um horário de início posterior ao de término!");

                    } else if (hpartColab.getHoraInicio().getTime() == 0) {
                        errosTabelaPesquisaExtensaoColaborador.add("Insira um Horário de Início!");

                    } else if (hpartColab.getHoraTermino().getTime() == 0) {
                        errosTabelaPesquisaExtensaoColaborador.add("Insira um Horário de Término!");

                    } else if (p.getCargaHorariaSemanalParticipacao() == 0) {
                        errosTabelaPesquisaExtensaoColaborador.add("Carga Horária Nula!");

                    }
                }
                if (p.getProjetoPesquisaExtensao().getTituloProcesso().equals("")) {
                    errosTabelaPesquisaExtensaoColaborador.add("Adicione um título ao projeto de pesquisa/extensão");

                }
                for (Participacao p2 : getPtd().getParticipacoes()) {
                    if (p.getProjetoPesquisaExtensao().getTituloProcesso().equals(p2.getProjetoPesquisaExtensao().getTituloProcesso()) && p.getIdParticipacao() != p2.getIdParticipacao()) {
                        errosTabelaPesquisaExtensaoColaborador.add("Você tem mais de uma participação no mesmo projeto, caso trabalhe nele em mais de um dia, adicione um novo horário!");

                    } else if (p.getProjetoPesquisaExtensao().getNumeroProcesso().equals(p2.getProjetoPesquisaExtensao().getNumeroProcesso()) && p.getIdParticipacao() != p2.getIdParticipacao()) {
                        errosTabelaPesquisaExtensaoColaborador.add("Você tem mais de uma participação no mesmo projeto, caso trabalhe nele em mais de um dia, adicione um novo horário!");
                    }
                }
            }

        }
    }

    /*
     As caixas das listas de erros e irregularidades só são mostradas caso das listas
     não estarem vazias, assim a visibilidade (no caso a opacidade) das caixas é dada
     pelo resultado dessa verificação
     */
    public int verificarConteúdoListaParaOpacidade(List<String> lista) {
        if (lista.isEmpty()) {
            return 0;
        } else {
            return 1;
        }
    }

    /*
     Percorre as listas de horários dentro das atividades das listas de atividades do
     PTD em edição alterando a carga horária total de cada seção identificando irregularidas
     perante a legislação durante a passagem pelas listas
     */
    public void verificarCargaHorariaPTDEdicao() {

        irregularidadesPTDEdicao = new ArrayList<>();
        cargaHorariaTotalPTDPTDEdicao = 0;
        ptd.setCargaHorariaSecaoAdministracao(0);
        ptd.setCargaHorariaSecaoApoioEnsino(0);
        ptd.setCargaHorariaSecaoAtividadesASeremPropostas(0);
        ptd.setCargaHorariaSecaoAulas(0);
        ptd.setCargaHorariaSecaoManutencaoEnsino(0);
        ptd.setCargaHorariaSecaoOutroTipoAtividade(0);
        ptd.setCargaHorariaSecaoProjetoPesquisaExtensaoAutor(0);
        ptd.setCargaHorariaSecaoProjetoPesquisaExtensaoColab(0);
        double cargaHorariaTotalPTDAux = 0;

        PTD ptd = new PTD();
        if (this.ptd.getIdPTD() != 0) {
            ptd = this.ptd;

            ptd.setCargaHorariaSecaoAdministracao(0);
            ptd.setCargaHorariaSecaoApoioEnsino(0);
            ptd.setCargaHorariaSecaoAtividadesASeremPropostas(0);
            ptd.setCargaHorariaSecaoAulas(cargaHorariaTotalPTDAux);
            ptd.setCargaHorariaSecaoManutencaoEnsino(0);
            ptd.setCargaHorariaSecaoOutroTipoAtividade(0);
            ptd.setCargaHorariaSecaoProjetoPesquisaExtensaoAutor(0);
            ptd.setCargaHorariaSecaoProjetoPesquisaExtensaoColab(0);
        }

        for (Administracao adm : getPtd().getAdministrativas()) {
            ptd.setCargaHorariaSecaoAdministracao(ptd.getCargaHorariaSecaoAdministracao()
                    + adm.getCargaHorariaSemanalAdministracao());
        }
        for (AtividadeASerProposta asp : getPtd().getAtividadesASeremPropostas()) {
            ptd.setCargaHorariaSecaoAtividadesASeremPropostas(ptd.getCargaHorariaSecaoAtividadesASeremPropostas()
                    + asp.getCargaHorariaSemanalAtividadeASerProposta());
        }

        for (Participacao part : getPtd().getParticipacoes()) {
            if (part.getRotulo().equals("Autor")) {
                ptd.setCargaHorariaSecaoProjetoPesquisaExtensaoAutor(ptd.getCargaHorariaSecaoProjetoPesquisaExtensaoAutor()
                        + part.getCargaHorariaSemanalParticipacao());
            } else if (part.getRotulo().equals("Colaborador")) {
                ptd.setCargaHorariaSecaoProjetoPesquisaExtensaoColab(ptd.getCargaHorariaSecaoProjetoPesquisaExtensaoColab()
                        + part.getCargaHorariaSemanalParticipacao());
            }
        }
        if (getPtd().getProfessor().getRegimeTrabalho().equalsIgnoreCase("40h") | getPtd().getProfessor().getRegimeTrabalho().equalsIgnoreCase("Dedicação Exclusiva")) {
            if ((ptd.getCargaHorariaSecaoProjetoPesquisaExtensaoAutor()
                    + ptd.getCargaHorariaSecaoProjetoPesquisaExtensaoColab()) != 16) {
                if (ptd.getCargaHorariaSecaoProjetoPesquisaExtensaoAutor()
                        + ptd.getCargaHorariaSecaoProjetoPesquisaExtensaoColab() > 16) {

                    getIrregularidadesPTDEdicao().add("A carga horária de projetos de pesquisa e/ou extensão"
                            + " como colaborador e autor é superior à 16 horas!");

                } else if (ptd.getCargaHorariaSecaoProjetoPesquisaExtensaoAutor() + ptd.getCargaHorariaSecaoProjetoPesquisaExtensaoColab() < 16 && ptd.getCargaHorariaSecaoProjetoPesquisaExtensaoAutor() + ptd.getCargaHorariaSecaoProjetoPesquisaExtensaoColab() > 1) {

                    getIrregularidadesPTDEdicao().add("A carga horária de projetos de pesquisa e/ou extensão"
                            + " como colaborador e autor é inferior à 16 horas!");

                }
            }
        }
        for (Apoio ap : getPtd().getApoios()) {
            ptd.setCargaHorariaSecaoApoioEnsino(ptd.getCargaHorariaSecaoApoioEnsino() + ap.getCargaHorariaSemanalApoio());
        }
        if (ptd.getCargaHorariaSecaoApoioEnsino() != 4) {

            if (ptd.getCargaHorariaSecaoApoioEnsino() > 4 && (ptd.getCargaHorariaSecaoProjetoPesquisaExtensaoAutor() + ptd.getCargaHorariaSecaoProjetoPesquisaExtensaoColab()) != 0) {
                getIrregularidadesPTDEdicao().add("A carga horária é superior à 4 horas em Apoio ao Ensino!");
            } else if (ptd.getCargaHorariaSecaoApoioEnsino() < 4) {
                getIrregularidadesPTDEdicao().add("A carga horária é  inferior à 4 horas em Apoio ao Ensino!");
            }

        }

        for (Aula a : getPtd().getAulas()) {
            ptd.setCargaHorariaSecaoAulas(ptd.getCargaHorariaSecaoAulas() + a.getCargaHorariaTotal());

        }

        if (getPtd().getProfessor().getRegimeTrabalho().equals("20h")) {

            if (ptd.getCargaHorariaSecaoAulas() < 7.5) {

                getIrregularidadesPTDEdicao().add("A carga horária é inferior à 8 horas em Aula!");

            } else if (ptd.getCargaHorariaSecaoAulas() > 12.5) {

                getIrregularidadesPTDEdicao().add("A carga horária é superior à 12 horas em Aula!");

            }

        } else if (getPtd().getProfessor().getRegimeTrabalho().equalsIgnoreCase("40h") | getPtd().getProfessor().getRegimeTrabalho().equalsIgnoreCase("Dedicação Exclusiva")) {
            if (ptd.getCargaHorariaSecaoAulas() < 11.5) {

                getIrregularidadesPTDEdicao().add("A carga horária é inferior à 12 horas em Aula!");

            } else if (ptd.getCargaHorariaSecaoAulas() > 16.5) {

                if (ptd.getCargaHorariaSecaoApoioEnsino() > 4 && (ptd.getCargaHorariaSecaoProjetoPesquisaExtensaoAutor() + ptd.getCargaHorariaSecaoProjetoPesquisaExtensaoColab()) == 0) {

                    double excessoApoio = ptd.getCargaHorariaSecaoApoioEnsino() - 4;
                    double excessoEsperadoAula = 16 - excessoApoio;

                    if ((ptd.getCargaHorariaSecaoAulas() - excessoEsperadoAula) > 16.5) {

                        getIrregularidadesPTDEdicao().add("Mesmo descontando a carga horária redistribuída de projeto de pesquisa"
                                + "e/ou extensão para aula e apoio ao ensino, o componente aula apresenta carga horária"
                                + "superior à 16 horas");

                    }

                } else {
                    getIrregularidadesPTDEdicao().add("A carga horária é superior à 16 horas em Aula!");
                }

                if (ptd.getCargaHorariaSecaoAulas() >= 11.5 && ptd.getCargaHorariaSecaoAulas() <= 16.5) {
                    if ((ptd.getCargaHorariaSecaoProjetoPesquisaExtensaoAutor() + ptd.getCargaHorariaSecaoProjetoPesquisaExtensaoColab()) == 0) {

                        double excessoApoio = ptd.getCargaHorariaSecaoApoioEnsino() - 4;
                        double excessoEsperadoAula = 16 - excessoApoio;

                        if ((ptd.getCargaHorariaSecaoAulas() - excessoEsperadoAula) < 11.5) {

                            getIrregularidadesPTDEdicao().add("Descontando a carga horária redistribuída de projeto de pesquisa"
                                    + "e/ou extensão para aula e apoio ao ensino, o componente aula apresenta carga horária"
                                    + "inferior à 12 horas");

                        }
                    }
                }
            }
        }
        for (ManutencaoEnsino me : getPtd().getManutencoesEnsino()) {
            ptd.setCargaHorariaSecaoManutencaoEnsino(ptd.getCargaHorariaSecaoManutencaoEnsino() + me.getCargaHorariaSemanalManutencaoEnsino());
        }

        if (ptd.getCargaHorariaSecaoManutencaoEnsino() != 4) {
            if (ptd.getCargaHorariaSecaoManutencaoEnsino() < 4) {

                getIrregularidadesPTDEdicao().add("A carga horária é inferior"
                        + " à 4 horas em Manutenção ao Ensino");

            } else if (ptd.getCargaHorariaSecaoManutencaoEnsino() > 4) {

                getIrregularidadesPTDEdicao().add("A carga horária é superior "
                        + "à 4 horas em Manutenção ao Ensino!");

            }
        }

        for (OutroTipoAtividade ota : getPtd().getOutrosTiposAtividades()) {
            ptd.setCargaHorariaSecaoOutroTipoAtividade(ptd.getCargaHorariaSecaoOutroTipoAtividade() + ota.getCargaHorariaSemanalOutroTipoAtividade());
        }
        if (!getPtd().getApoios().isEmpty() && !getPtd().getAulas().isEmpty() && !getPtd().getManutencoesEnsino().isEmpty()) {
            if (getPtd().getProfessor().getRegimeTrabalho().equalsIgnoreCase("20h")) {
                if ((ptd.getCargaHorariaSecaoApoioEnsino() + ptd.getCargaHorariaSecaoAulas() + ptd.getCargaHorariaSecaoManutencaoEnsino()) < 7.5) {

                    irregularidadesPTDEdicao.add("A carga horária dedicada a Atividades de Ensino(apoio,manutenção e aulas) é inferior à 8 horas");

                } else if ((ptd.getCargaHorariaSecaoApoioEnsino() + ptd.getCargaHorariaSecaoAulas() + ptd.getCargaHorariaSecaoManutencaoEnsino()) > 20.5) {

                    irregularidadesPTDEdicao.add("A carga horária dedicada a Atividades de Ensino(apoio,manutenção e aulas) é superior à 20 horas");

                }
            }
            if (getPtd().getProfessor().getRegimeTrabalho().equalsIgnoreCase("40h") | getPtd().getProfessor().getRegimeTrabalho().equalsIgnoreCase("Dedicação Exclusiva")) {

                if ((ptd.getCargaHorariaSecaoApoioEnsino()
                        + ptd.getCargaHorariaSecaoAulas()
                        + ptd.getCargaHorariaSecaoManutencaoEnsino()) < 12) {

                    irregularidadesPTDEdicao.add("A carga horária dedicada a Atividades de Ensino(apoio,manutenção e aulas) é inferior à 12 horas");

                } else if ((ptd.getCargaHorariaSecaoApoioEnsino()
                        + ptd.getCargaHorariaSecaoAulas()
                        + ptd.getCargaHorariaSecaoManutencaoEnsino()) > 24) {

                    irregularidadesPTDEdicao.add("A carga horária dedicada a Atividades de Ensino(apoio,manutenção e aulas) é superior à 24 horas");

                }
            }
        }
        cargaHorariaTotalPTDAux = ptd.getCargaHorariaSecaoAdministracao()
                + ptd.getCargaHorariaSecaoAtividadesASeremPropostas()
                + ptd.getCargaHorariaSecaoApoioEnsino()
                + ptd.getCargaHorariaSecaoAulas()
                + ptd.getCargaHorariaSecaoManutencaoEnsino()
                + ptd.getCargaHorariaSecaoOutroTipoAtividade()
                + ptd.getCargaHorariaSecaoProjetoPesquisaExtensaoAutor()
                + ptd.getCargaHorariaSecaoProjetoPesquisaExtensaoColab();
        double regime = 20;
        if ((getPtd().getProfessor().getRegimeTrabalho().equals("40h") | getPtd().getProfessor().getRegimeTrabalho().equals("Dedicação Exclusiva")) && ((cargaHorariaTotalPTDAux < 39.5 | cargaHorariaTotalPTDAux > 40.5))) {

            irregularidadesPTDEdicao.add("A carga horária do PTD diverge com seu regime de trabalho de 40h");

        } else if (getPtd().getProfessor().getRegimeTrabalho().equals("20h") && ((cargaHorariaTotalPTDAux < 19.5 | cargaHorariaTotalPTDAux > 20.5))) {

            irregularidadesPTDEdicao.add("A carga horária do PTD diverge com seu regime de trabalho de 20h");

        }
        if (getPtd().getProfessor().getRegimeTrabalho().equalsIgnoreCase("40h") | getPtd().getProfessor().getRegimeTrabalho().equalsIgnoreCase("Dedicação Exclusiva")) {
            regime = 40;
        }
        if (regime == cargaHorariaTotalPTDAux) {
            setEstadoCargaHorariaPTD("CORRETO");
        } else {
            setEstadoCargaHorariaPTD("INCORRETO");
        }

        cargaHorariaTotalPTDPTDEdicao = cargaHorariaTotalPTDAux;

        ptd.setCargaHorariaTotal(cargaHorariaTotalPTDAux);
        Dao<PTD> ptdDAO = new GenericDAO<>(PTD.class);
        ptdDAO.alterar(ptd);

    }

    /*
    
     */
    public String salvarJustificativasEComentários() {
        Dao<PTD> ptdDAO = new GenericDAO<>(PTD.class);
        ptdDAO.alterar(ptd);
        return "CriarCorrigirPTD?faces-redirect=true";
    }

    /*
     Altera o estado do atual PTD concluído de um professor e mudao o estado do PTD 
     aprovado selecionado para 'CONCLUÍDO' o que faz com que o PTD seja considerado o 
     atual PTD de um professor (o PTD vigente).
     */
    public void concluirPTD(PTD ptd) {
        Dao<PTD> ptdDAOGenerico = new GenericDAO<>(PTD.class);
        ptd.setEstadoPTD("CONCLUÍDO");
        IPTDDAO ptdDAOEspecifico = new PTDDAO();
        List<PTD> ptdsConcluídosAux = ptdDAOEspecifico.buscarPTDsConcluidosPorProfessor(ptd.getProfessor().getIdUsuario());
        for (PTD p : ptdsConcluídosAux) {
            p.setEstadoPTD("ARQUIVADO");
            ptdDAOGenerico.alterar(p);
        }
        ptdDAOGenerico.alterar(ptd);
    }

    public PTD getPtd() {
        return ptd;
    }

    public void setPtd(PTD ptd) {
        this.ptd = ptd;
    }

    /**
     * @return the ptdsEmAvaliacao
     */
    public List<PTD> getPtdsEmAvaliacao() {
        return ptdsEmAvaliacao;
    }

    /**
     * @param ptdsEmAvaliacao the pdtsEmAvaliacao to set
     */
    public void setPtdsEmAvaliacao(List<PTD> ptdsEmAvaliacao) {
        this.ptdsEmAvaliacao = ptdsEmAvaliacao;
    }

    public List<PTD> getPtdsEmEdicao() {
        return ptdsEmEdicao;
    }

    public void setPtdsEmEdicao(List<PTD> ptdsEmEdicao) {
        this.ptdsEmEdicao = ptdsEmEdicao;
    }

    public String getEstadoCargaHorariaPTD() {
        return estadoCargaHorariaPTD;
    }

    public void setEstadoCargaHorariaPTD(String estadoCargaHorariaPTD) {
        this.estadoCargaHorariaPTD = estadoCargaHorariaPTD;
    }

    /**
     * @return the errosTabelaAula
     */
    public List<String> getErrosTabelaAula() {
        return errosTabelaAula;
    }

    /**
     * @param errosTabelaAula the errosTabelaAula to set
     */
    public void setErrosTabelaAula(List<String> errosTabelaAula) {
        this.errosTabelaAula = errosTabelaAula;
    }

    /**
     * @return the errosTabelaManuEnsino
     */
    public List<String> getErrosTabelaManuEnsino() {
        return errosTabelaManuEnsino;
    }

    /**
     * @param errosTabelaManuEnsino the errosTabelaManuEnsino to set
     */
    public void setErrosTabelaManuEnsino(List<String> errosTabelaManuEnsino) {
        this.errosTabelaManuEnsino = errosTabelaManuEnsino;
    }

    /**
     * @return the errosTabelaApoioEnsino
     */
    public List<String> getErrosTabelaApoioEnsino() {
        return errosTabelaApoioEnsino;
    }

    /**
     * @param errosTabelaApoioEnsino the errosTabelaApoioEnsino to set
     */
    public void setErrosTabelaApoioEnsino(List<String> errosTabelaApoioEnsino) {
        this.errosTabelaApoioEnsino = errosTabelaApoioEnsino;
    }

    /**
     * @return the errosTabelaPesquisaExtensaoAutor
     */
    public List<String> getErrosTabelaPesquisaExtensaoAutor() {
        return errosTabelaPesquisaExtensaoAutor;
    }

    /**
     * @param errosTabelaPesquisaExtensaoAutor the
     * errosTabelaPesquisaExtensaoAutor to set
     */
    public void setErrosTabelaPesquisaExtensaoAutor(List<String> errosTabelaPesquisaExtensaoAutor) {
        this.errosTabelaPesquisaExtensaoAutor = errosTabelaPesquisaExtensaoAutor;
    }

    /**
     * @return the errosTabelaPesquisaExtensaoColaborador
     */
    public List<String> getErrosTabelaPesquisaExtensaoColaborador() {
        return errosTabelaPesquisaExtensaoColaborador;
    }

    /**
     * @param errosTabelaPesquisaExtensaoColaborador the
     * errosTabelaPesquisaExtensaoColaborador to set
     */
    public void setErrosTabelaPesquisaExtensaoColaborador(List<String> errosTabelaPesquisaExtensaoColaborador) {
        this.errosTabelaPesquisaExtensaoColaborador = errosTabelaPesquisaExtensaoColaborador;
    }

    /**
     * @return the errosTabelaAdministrativas
     */
    public List<String> getErrosTabelaAdministrativas() {
        return errosTabelaAdministrativas;
    }

    /**
     * @param errosTabelaAdministrativas the errosTabelaAdministrativas to set
     */
    public void setErrosTabelaAdministrativas(List<String> errosTabelaAdministrativas) {
        this.errosTabelaAdministrativas = errosTabelaAdministrativas;
    }

    /**
     * @return the errosTabelaOutrasAtividades
     */
    public List<String> getErrosTabelaOutrasAtividades() {
        return errosTabelaOutrasAtividades;
    }

    /**
     * @param errosTabelaOutrasAtividades the errosTabelaOutrasAtividades to set
     */
    public void setErrosTabelaOutrasAtividades(List<String> errosTabelaOutrasAtividades) {
        this.errosTabelaOutrasAtividades = errosTabelaOutrasAtividades;
    }

    /**
     * @return the errosTabelaAtividadesASeremPropostas
     */
    public List<String> getErrosTabelaAtividadesASeremPropostas() {
        return errosTabelaAtividadesASeremPropostas;
    }

    /**
     * @param errosTabelaAtividadesASeremPropostas the
     * errosTabelaAtividadesASeremPropostas to set
     */
    public void setErrosTabelaAtividadesASeremPropostas(List<String> errosTabelaAtividadesASeremPropostas) {
        this.errosTabelaAtividadesASeremPropostas = errosTabelaAtividadesASeremPropostas;
    }

    /**
     * @return the participacoesAutorPTDEdicao
     */
    public List<Participacao> getParticipacoesAutorPTDEdicao() {
        atualizarListasParticipacoesPTDEdicao();
        return participacoesAutorPTDEdicao;
    }

    /**
     * @param participacoesAutorPTDEdicao the participacoesAutorPTDEdicao to set
     */
    public void setParticipacoesAutorPTDEdicao(List<Participacao> participacoesAutorPTDEdicao) {
        this.participacoesAutorPTDEdicao = participacoesAutorPTDEdicao;
    }

    /**
     * @return the participacoesColabPTDEdicao
     */
    public List<Participacao> getParticipacoesColabPTDEdicao() {
        atualizarListasParticipacoesPTDEdicao();
        return participacoesColabPTDEdicao;
    }

    /**
     * @param participacoesColabPTDEdicao the participacoesColabPTDEdicao to set
     */
    public void setParticipacoesColabPTDEdicao(List<Participacao> participacoesColabPTDEdicao) {
        this.participacoesColabPTDEdicao = participacoesColabPTDEdicao;
    }

    /**
     * @return the ptdsReprovados
     */
    public List<PTD> getPtdsReprovados() {
        return ptdsReprovados;
    }

    /**
     * @param ptdsReprovados the ptdsReprovados to set
     */
    public void setPtdsReprovados(List<PTD> ptdsReprovados) {
        this.ptdsReprovados = ptdsReprovados;
    }

    /**
     * @return the ptdsAprovados
     */
    public List<PTD> getPtdsAprovados() {
        return ptdsAprovados;
    }

    /**
     * @param ptdsAprovados the ptdsAprovados to set
     */
    public void setPtdsAprovados(List<PTD> ptdsAprovados) {
        this.ptdsAprovados = ptdsAprovados;
    }

    /**
     * @return the irregularidadesPTDEdicao
     */
    public List<String> getIrregularidadesPTDEdicao() {
        return irregularidadesPTDEdicao;
    }

    /**
     * @param irregularidadesPTDEdicao the irregularidadesPTDEdicao to set
     */
    public void setIrregularidadesPTDEdicao(List<String> irregularidadesPTDEdicao) {
        this.irregularidadesPTDEdicao = irregularidadesPTDEdicao;
    }

    /**
     * @return the cargaHorariaTotalPTDPTDEdicao
     */
    public double getCargaHorariaTotalPTDPTDEdicao() {
        return cargaHorariaTotalPTDPTDEdicao;
    }

    /**
     * @param cargaHorariaTotalPTDPTDEdicao the cargaHorariaTotalPTDPTDEdicao to
     * set
     */
    public void setCargaHorariaTotalPTDPTDEdicao(double cargaHorariaTotalPTDPTDEdicao) {
        this.cargaHorariaTotalPTDPTDEdicao = cargaHorariaTotalPTDPTDEdicao;
    }

    /**
     * @return the ptdConcluido
     */
    public PTD getPtdConcluido() {
        atualizarListasParticipacoesPTDConcluido();
        return ptdConcluido;
    }

    /**
     * @param ptdConcluido the ptdConcluido to set
     */
    public void setPtdConcluido(PTD ptdConcluido) {
        this.ptdConcluido = ptdConcluido;
    }

    /**
     * @return the ptdsConcluídos
     */
    public List<PTD> getPtdsConcluídos() {
        return ptdsConcluídos;
    }

    /**
     * @param ptdsConcluídos the ptdsConcluídos to set
     */
    public void setPtdsConcluídos(List<PTD> ptdsConcluídos) {
        this.ptdsConcluídos = ptdsConcluídos;
    }

    /**
     * @return the participacoesAutorPTDAprovado
     */
    public List<Participacao> getParticipacoesAutorPTDAprovado() {
        return participacoesAutorPTDAprovado;
    }

    /**
     * @param participacoesAutorPTDAprovado the participacoesAutorPTDAprovado to
     * set
     */
    public void setParticipacoesAutorPTDAprovado(List<Participacao> participacoesAutorPTDAprovado) {
        this.participacoesAutorPTDAprovado = participacoesAutorPTDAprovado;
    }

    /**
     * @return the participacoesColabPTDAprovado
     */
    public List<Participacao> getParticipacoesColabPTDAprovado() {
        return participacoesColabPTDAprovado;
    }

    /**
     * @param participacoesColabPTDAprovado the participacoesColabPTDAprovado to
     * set
     */
    public void setParticipacoesColabPTDAprovado(List<Participacao> participacoesColabPTDAprovado) {
        this.participacoesColabPTDAprovado = participacoesColabPTDAprovado;
    }

    /**
     * @return the professoresAHabilitar
     */
    public List<Professor> getProfessoresAHabilitar() {
        return professoresAHabilitar;
    }

    /**
     * @param professoresAHabilitar the professoresAHabilitar to set
     */
    public void setProfessoresAHabilitar(List<Professor> professoresAHabilitar) {
        this.professoresAHabilitar = professoresAHabilitar;
    }

    /**
     * @return the diretoresAHabilitar
     */
    public List<DiretorEnsino> getDiretoresAHabilitar() {
        return diretoresAHabilitar;
    }

    /**
     * @param diretoresAHabilitar the diretoresAHabilitar to set
     */
    public void setDiretoresAHabilitar(List<DiretorEnsino> diretoresAHabilitar) {
        this.diretoresAHabilitar = diretoresAHabilitar;
    }

    /**
     * @return the ptdsArquivados
     */
    public List<PTD> getPtdsArquivados() {
        return ptdsArquivados;
    }

    /**
     * @param ptdsArquivados the ptdsArquivados to set
     */
    public void setPtdsArquivados(List<PTD> ptdsArquivados) {
        this.ptdsArquivados = ptdsArquivados;
    }

    /**
     * @return the saidaTelaMostrarPTDAux
     */
    public String getSaidaTelaMostrarPTDAux() {
        return saidaTelaMostrarPTDAux;
    }

    /**
     * @param saidaTelaMostrarPTDAux the saidaTelaMostrarPTDAux to set
     */
    public void setSaidaTelaMostrarPTDAux(String saidaTelaMostrarPTDAux) {
        this.saidaTelaMostrarPTDAux = saidaTelaMostrarPTDAux;
    }

    /**
     * @return the participacoesAutorPTDConcluido
     */
    public List<Participacao> getParticipacoesAutorPTDConcluido() {
        return participacoesAutorPTDConcluido;
    }

    /**
     * @param participacoesAutorPTDConcluido the participacoesAutorPTDConcluido
     * to set
     */
    public void setParticipacoesAutorPTDConcluido(List<Participacao> participacoesAutorPTDConcluido) {
        this.participacoesAutorPTDConcluido = participacoesAutorPTDConcluido;
    }

    /**
     * @return the participacoesColabPTDConcluido
     */
    public List<Participacao> getParticipacoesColabPTDConcluido() {
        return participacoesColabPTDConcluido;
    }

    /**
     * @param participacoesColabPTDConcluido the participacoesColabPTDConcluido
     * to set
     */
    public void setParticipacoesColabPTDConcluido(List<Participacao> participacoesColabPTDConcluido) {
        this.participacoesColabPTDConcluido = participacoesColabPTDConcluido;
    }

    /**
     * @return the ptdAprovado
     */
    public PTD getPtdAprovado() {
        return ptdAprovado;
    }

    /**
     * @param ptdAprovado the ptdAprovado to set
     */
    public void setPtdAprovado(PTD ptdAprovado) {
        this.ptdAprovado = ptdAprovado;
    }

}

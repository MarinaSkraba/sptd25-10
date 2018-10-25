package br.edu.ifpr.irati.mb;

import br.edu.ifpr.irati.dao.Dao;
import br.edu.ifpr.irati.dao.GenericDAO;
import br.edu.ifpr.irati.dao.IPTDDAO;
import br.edu.ifpr.irati.dao.PTDDAO;
import br.edu.ifpr.irati.modelo.PTD;
import br.edu.ifpr.irati.modelo.Participacao;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.component.export.PDFOptions;

@ManagedBean
@SessionScoped
public class PTDASerMostradoMB {

    private PTD ptdConcluido;
    private List<PTD> ptdsResultadoBusca;
    private List<Participacao> participacoesAutorPTDConcluido;
    private List<Participacao> participacoesColabPTDConcluido;
    private String textoBusca;
    private String filtroBusca;
    private PDFOptions pdfOpt;
    private String legislacaoAula;
    private String legislacaoApoio;
    private String legislacaoManutencao;
    private String legislacaoGeral;

    public PTDASerMostradoMB() {
        IPTDDAO ptdDAOEspecifico = new PTDDAO();
        ptdConcluido = new PTD();
        ptdsResultadoBusca = new ArrayList<>();
        ptdsResultadoBusca = ptdDAOEspecifico.buscarPTDsConcluidos();
        participacoesAutorPTDConcluido = new ArrayList<>();
        participacoesColabPTDConcluido = new ArrayList<>();
        textoBusca = "";
        filtroBusca = "";
        legislacaoAula = "Art. 4º - As aulas, além das presenciais, "
                + "poderão ser ministradas na modalidade de Ensino a Distância"
                + " – EAD, desde que previstas no Projeto Pedagógico do Curso, "
                + "aprovado pelo Conselho Superior, nos limites e condições "
                + "estabelecidas pela legislação vigente específica e sem "
                + "remuneração adicional.";
        legislacaoApoio = "Art. 6º - Serão consideradas Atividades de Apoio ao Ensino"
                + " as ações do docente, diretamente vinculadas às matrizes curriculares"
                + " e programas dos cursos regulares do IFPR, e/ou que incidam diretamente "
                + "na melhoria das condições de oferta de ensino, compreendendo:\n"
                + "I.Orientação de Estágio Curricular Supervisionado;"
                + "II.Orientação de Trabalho de Conclusão de Curso;\n"
                + "III.Orientação de Atividades Complementares;";
        legislacaoManutencao = "Art. 5º - Serão consideradas Atividades de Manutenção "
                + "de Ensino as ações didáticas do docente relacionadas ao estudo,"
                + " planejamento, preparação, desenvolvimento e avaliação das aulas"
                + " ministradas nos cursos e programas regulares do IFPR, com base "
                + "no artigo 112 da Lei 11.784, de 22/09/08.";
        legislacaoGeral = "Art. 2º - O Regime de Trabalho dos docentes efetivos do Quadro Permanente "
                + "do IFPR, ou dos docentes com contrato de trabalho na qualidade de substitutos, é"
                + " definido segundo critérios de contratação previstos na legislação Federal"
                + " – Lei nº 7.596/87, de 10/04/87, Decreto Federal 94.664, de 23/07/87, Portaria nº 475, "
                + "de 26/08/87 e Lei 11.784, de 22/09/08, compreendendo os seguintes regimes de trabalho:\n"
                + " \n"
                + "I. Tempo parcial de 20 (vinte) horas semanais de trabalho;\n"
                + "II. Tempo integral de 40 (quarenta) horas semanais de trabalho, em 2 (dois) turnos diários completos;\n"
                + "III. Dedicação exclusiva, 40 horas semanais, em 2 (dois) turnos diários completos e impedimento "
                + "do exercício de outra atividade remunerada, pública ou privada; e\n"
                + "IV.	Contrato administrativo de prestação de serviços, de acordo com a Lei n º 8.745, de 09/12/93 "
                + "e Lei n º9.849, de 26/10/99.\n"
                + "Art. 11 – A carga horária destinada as atividades de ensino terá a seguinte distribuição:\n"
                + "I.  Docentes com regime de tempo parcial de 20 (vinte) horas semanais de trabalho destinarão:"
                + " no mínimo 08 (oito) e no máximo 12 (doze) horas para aula, 04 (quatro) horas para manutenção "
                + "de ensino e 04 (quatro) para apoio ao ensino; e\n"
                + "II. Docentes com regime de tempo integral de 40 (quarenta) horas semanais de trabalho ou Dedicação "
                + "Exclusiva destinarão: no mínimo 12 (doze) e no máximo 16 (dezesseis) horas semanais para aula, 04 (quatro) horas para manutenção de ensino e 04 (quatro) para apoio ao ensino.\n"
                + "Art. 12 – Os docentes em regime de tempo integral de 40 (quarenta) horas e dedicação exclusiva, exceto "
                + "os docentes afastados na forma da lei, estão obrigados ao cumprimento de 16 (dezesseis) horas em atividades "
                + "de pesquisa e/ou extensão.\n"
                + "Parágrafo Único – Os docentes em regime de tempo integral de 40 (quarenta) horas e dedicação exclusiva "
                + "que não desenvolvem atividades de pesquisa e/ou extensão terão a carga horária automaticamente destinada"
                + " às atividades de apoio ao ensino e para aula.\n"
                + "Art. 14 – O docente que exerça função administrativa e de assessoramento, previstas no Regimento do IFPR,"
                + " poderá ter reduzida sua carga horária destinada ás atividades de ensino, pesquisa e extensão, "
                + "mediante autorização das instâncias superiores a que esteja vinculado.";
    }

    public void realizarBusca() {

        setPtdsResultadoBusca((List<PTD>) new ArrayList());
        IPTDDAO ptdDAOEspecifico = new PTDDAO();

        if (getFiltroBusca().equals("buscaPorNome")) {

            setPtdsResultadoBusca(ptdDAOEspecifico.buscarPTDsPorNomeDocente(getTextoBusca()));

        } else if (getFiltroBusca().equals("buscaPorAtividade")) {

            setPtdsResultadoBusca(ptdDAOEspecifico.buscarPTDsPorAtividade(getTextoBusca()));

        }

    }

    public void layoutPDF() {

        setPdfOpt(new PDFOptions());
        getPdfOpt().setFacetBgColor("#F88017");
        getPdfOpt().setFacetFontColor("#0000ff");
        getPdfOpt().setFacetFontStyle("BOLD");
        getPdfOpt().setCellFontSize("12");
    }

    public void gerarPDF(Object document) throws IOException, BadElementException, DocumentException {

        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String logoif = externalContext.getRealPath("") + File.separator + "img" + "logoif.jpg";
        String logoMinisterioEducacao = externalContext.getRealPath("") + File.separator + "img" + "logoMinisterioEducacao.jpg";
        pdf.addAuthor(getPtdConcluido().getProfessor().getNomeCompleto());
        pdf.addHeader(logoif, logoMinisterioEducacao);
        pdf.addTitle("1 IDENTIFICAÇAO DO DOCENTE");
// tabela identificação docente
        pdf.addSubject(legislacaoGeral);
        pdf.addTitle("2 ATIVIDADES DE ENSINO");
        pdf.addTitle("2.1 AULAS");
        pdf.addSubject(legislacaoAula);
// tabela aulas 
        pdf.addSubject(ptdConcluido.getCampoJustificativaAtividadeEnsino());
        pdf.addTitle("2.2 ATIVIDADES MANUTENÇÃO AO ENSINO");
        pdf.addSubject(legislacaoManutencao);
// tabela manutenções ao ensino
        pdf.addSubject(ptdConcluido.getCampoJustificativaManutencaoEnsino());
        pdf.addTitle("2.3 APOIO AO ENSINO");
        pdf.addSubject(legislacaoApoio);
// tabela apoios ao ensino
        pdf.addSubject(ptdConcluido.getCampoJustificativaApoioEnsino());
        pdf.addTitle("3 ATIVIDADES DE PESQUISA E EXTENSÃO");
        pdf.addTitle("3.1 ATIVIDADES DE PESQUISA E/OU EXTENSÃO CADASTRADAS NO COPE(PARTICIPAÇÃO COMO AUTOR)");
// tabela projetos pesquisa e/ou extensão cadastrados como autor 
        pdf.addTitle("3.2 ATIVIDADES DE PESQUISA E/OU EXTENSÃO CADASTRADAS NO COPE(PARTICIPAÇÃO COMO COLABORADOR");
// tabela projetos pesquisa 
        pdf.addSubject(ptdConcluido.getCampoJustificativaProjetoPesquisaExtensao());
        pdf.addTitle("4 ATIVIDADES ADMINISTRATIVAS");
// tabela atividades administrativas
        pdf.addSubject(ptdConcluido.getCampoJustificativaAdministracao());
        pdf.addTitle("5 OUTRAS ATIVIDADES DESENVOLVIDAS NO CAMPUS");
//tabela outras atividades
        pdf.addSubject(ptdConcluido.getCampoJustificativaOutrasAtividades());
        pdf.addTitle("6 OUTRAS ATIVIDADES QUE PRETENDO PROPOR NO SEMESTRE");
// tabela outras atividades que pretende propor

        pdf.addSubject(ptdConcluido.getCampoJustificativaSeremPropostas());
        pdf.addTitle("7 QUADRO GERAL DAS ATIVIDADES- CARGA HORÁRIA SEMANAL");
// tabela contabilizando atividades
        pdf.addSubject(ptdConcluido.getDataAvaliacaoPTD().toString());
        pdf.addSubject("____________________________________________");
        pdf.addSubject("Assinatura Docente");
        pdf.addSubject(ptdConcluido.getProfessor().getNomeCompleto());

    }

    public void abrirTelaBuscarPTDs() {
        IPTDDAO ptdDAOEspecifico = new PTDDAO();
        setPtdsResultadoBusca(ptdDAOEspecifico.buscarPTDsConcluidos());
    }

    public void abrirMostrarPTD(PTD ptd) {
        Dao<PTD> ptdDAOGenerico = new GenericDAO<>(PTD.class);
        setPtdConcluido(ptdDAOGenerico.buscarPorId(ptd.getIdPTD()));
    }

    public void atualizarListasParticipacoesPTD() {
        setParticipacoesAutorPTDConcluido(new ArrayList<>());
        setParticipacoesColabPTDConcluido(new ArrayList<>());
        for (Participacao part : getPtdConcluido().getParticipacoes()) {
            if (part.getRotulo().equalsIgnoreCase("Autor")) {
                getParticipacoesAutorPTDConcluido().add(part);
            } else {
                getParticipacoesColabPTDConcluido().add(part);
            }
        }
    }

    /**
     * @return the ptdsResultadoBusca
     */
    public List<PTD> getPtdsResultadoBusca() {
        return ptdsResultadoBusca;
    }

    /**
     * @param ptdsResultadoBusca the ptdsResultadoBusca to set
     */
    public void setPtdsResultadoBusca(List<PTD> ptdsResultadoBusca) {
        this.ptdsResultadoBusca = ptdsResultadoBusca;
    }

    /**
     * @return the ptdConcluido
     */
    public PTD getPtdConcluido() {
        return ptdConcluido;
    }

    /**
     * @param ptdConcluido the ptdConcluido to set
     */
    public void setPtdConcluido(PTD ptdConcluido) {
        this.ptdConcluido = ptdConcluido;
    }

    /**
     * @return the participacoesAutorPTDConcluido
     */
    public List<Participacao> getParticipacoesAutorPTDConcluido() {
        atualizarListasParticipacoesPTD();
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
        atualizarListasParticipacoesPTD();
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
     * @return the textoBusca
     */
    public String getTextoBusca() {
        return textoBusca;
    }

    /**
     * @param textoBusca the textoBusca to set
     */
    public void setTextoBusca(String textoBusca) {
        this.textoBusca = textoBusca;
    }

    /**
     * @return the filtroBusca
     */
    public String getFiltroBusca() {
        return filtroBusca;
    }

    /**
     * @param filtroBusca the filtroBusca to set
     */
    public void setFiltroBusca(String filtroBusca) {
        this.filtroBusca = filtroBusca;
    }

    /**
     * @return the pdfOpt
     */
    public PDFOptions getPdfOpt() {
        return pdfOpt;
    }

    /**
     * @param pdfOpt the pdfOpt to set
     */
    public void setPdfOpt(PDFOptions pdfOpt) {
        this.pdfOpt = pdfOpt;
    }

    /**
     * @return the legislacaoAula
     */
    public String getLegislacaoAula() {
        return legislacaoAula;
    }

    /**
     * @param legislacaoAula the legislacaoAula to set
     */
    public void setLegislacaoAula(String legislacaoAula) {
        this.legislacaoAula = legislacaoAula;
    }

    /**
     * @return the legislacaoApoio
     */
    public String getLegislacaoApoio() {
        return legislacaoApoio;
    }

    /**
     * @param legislacaoApoio the legislacaoApoio to set
     */
    public void setLegislacaoApoio(String legislacaoApoio) {
        this.legislacaoApoio = legislacaoApoio;
    }

    /**
     * @return the legislacaoManutencao
     */
    public String getLegislacaoManutencao() {
        return legislacaoManutencao;
    }

    /**
     * @param legislacaoManutencao the legislacaoManutencao to set
     */
    public void setLegislacaoManutencao(String legislacaoManutencao) {
        this.legislacaoManutencao = legislacaoManutencao;
    }

    /**
     * @return the legislacaoGeral
     */
    public String getLegislacaoGeral() {
        return legislacaoGeral;
    }

    /**
     * @param legislacaoGeral the legislacaoGeral to set
     */
    public void setLegislacaoGeral(String legislacaoGeral) {
        this.legislacaoGeral = legislacaoGeral;
    }

}

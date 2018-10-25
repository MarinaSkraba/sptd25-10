package br.edu.ifpr.irati.util;

import br.edu.ifpr.irati.dao.Dao;
import br.edu.ifpr.irati.dao.GenericDAO;
import br.edu.ifpr.irati.modelo.ProjetoPesquisaExtensao;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;



@FacesConverter(forClass = ProjetoPesquisaExtensao.class, value = "projetoPesquisaExtensaoConverter")
public class ProjetoPesquisaExtensaoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        if (value == null || value.isEmpty()) {
            return null;
        } else {
            Integer id = Integer.parseInt(value);
            Dao <ProjetoPesquisaExtensao> peDAO = new GenericDAO<>(ProjetoPesquisaExtensao.class);
            ProjetoPesquisaExtensao projetoPesquisaExtensao  = peDAO.buscarPorId(id);
            return projetoPesquisaExtensao;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        ProjetoPesquisaExtensao projetoPesquisaExtensao = (ProjetoPesquisaExtensao) value;
        if (projetoPesquisaExtensao != null) {
            return String.valueOf(projetoPesquisaExtensao.getIdProjetoPesquisaExtensao());
        } else {
            return null;
        }

    }

}

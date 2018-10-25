/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpr.irati.util;

import br.edu.ifpr.irati.dao.Dao;
import br.edu.ifpr.irati.dao.GenericDAO;
import br.edu.ifpr.irati.modelo.TipoOferta;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author willi
 */
@FacesConverter(forClass = TipoOferta.class, value = "tipoOfertaConverter")
public class TipoOfertaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        } else {
            Integer id = Integer.parseInt(value);
            Dao<TipoOferta> toDAO = new GenericDAO<>(TipoOferta.class);
            TipoOferta tipoOferta = toDAO.buscarPorId(id);
            return tipoOferta;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        TipoOferta tipoOferta = (TipoOferta) value;
        if (tipoOferta != null) {
            return String.valueOf(tipoOferta.getIdTipoOferta());
        } else {
            return null;
        }

    }
}

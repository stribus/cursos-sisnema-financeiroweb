package br.com.sisnema.financeiroweb.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.sisnema.financeiroweb.model.Categoria;
import br.com.sisnema.financeiroweb.negocio.CategoriaRN;

class CategoriaConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		try {
			return (new CategoriaRN()).obterPorId(new Categoria(new Integer(arg2)));
		} catch (Exception e) {
			return null;
		}
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if ((arg2 instanceof Categoria)) {
			return ((Categoria) arg2).getCodigo().toString();
		} else {
			return null;
		}

	}

}

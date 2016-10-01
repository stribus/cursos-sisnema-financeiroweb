package br.com.sisnema.financeiroweb.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.sisnema.financeiroweb.model.Categoria;
import br.com.sisnema.financeiroweb.negocio.CategoriaRN;

@FacesConverter(forClass = Categoria.class)
class CategoriaConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent componente, String codCateg) {
		try {
			return (new CategoriaRN()).obterPorId(new Categoria(Integer.valueOf(codCateg)));
		} catch (Exception e) {
			return null;
		}
	}

	public String getAsString(FacesContext contexto, UIComponent componente, Object categoria) {
		if ((categoria instanceof Categoria)) {
			return ((Categoria) categoria).getCodigo().toString();
		} else {
			return null;
		}

	}

}

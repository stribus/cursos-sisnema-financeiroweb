package br.com.sisnema.financeiroweb.negocio;

import br.com.sisnema.financeiroweb.dao.IDAO;
import br.com.sisnema.financeiroweb.exception.RNException;
import br.com.sisnema.financeiroweb.model.Categoria;

public class CategoriaRN extends RN<Categoria> {

	public CategoriaRN(IDAO<Categoria> dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}

	public void salvar(Categoria model) throws RNException {
		// TODO Auto-generated method stub

	}

}

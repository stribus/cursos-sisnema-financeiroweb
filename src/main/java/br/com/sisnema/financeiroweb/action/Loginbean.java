package br.com.sisnema.financeiroweb.action;

import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.negocio.UsuarioRN;

public class Loginbean extends ActionBean<Usuario> {

	public Loginbean() {
		super(new UsuarioRN());
	}

}

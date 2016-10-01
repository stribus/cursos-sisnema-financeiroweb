package br.com.sisnema.financeiroweb.negocio;

import br.com.sisnema.financeiroweb.dao.ContaDAO;
import br.com.sisnema.financeiroweb.exception.DAOException;
import br.com.sisnema.financeiroweb.exception.RNException;
import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Usuario;

public class ContaRN extends RN<Conta> {

	public ContaRN() {
		super(new ContaDAO());
	}
	
	public void salvar(Conta model) throws RNException {
		try {
			dao.salvar(model);
		} catch (DAOException e) {
			throw new RNException("Não foi possível salvar a Conta. Erro: "+e.getMessage(), e);
			
		}
	}
	
	public Conta buscarFavorita(Usuario u){
		return ((ContaDAO) dao).buscarFavorita(u);
	}
	
	public void tornarFavorita(Conta contaDaTela) throws RNException{

		// primeiro verificamos se há uma conta favorita para o usuario da conta....
		Conta contaFavorita = buscarFavorita(contaDaTela.getUsuario());
		
		if(contaFavorita != null){
			// caso encontre, desfavorita a mesma
			contaFavorita.setFavorita(false);
			salvar(contaFavorita);
		}
		
		// por fim, favoritamos a conta que veio da tela
		contaDaTela.setFavorita(true);
		salvar(contaDaTela);
	}
}



















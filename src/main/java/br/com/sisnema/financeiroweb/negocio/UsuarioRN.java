package br.com.sisnema.financeiroweb.negocio;

import br.com.sisnema.financeiroweb.dao.UsuarioDAO;
import br.com.sisnema.financeiroweb.domain.UsuarioPermissao;
import br.com.sisnema.financeiroweb.exception.DAOException;
import br.com.sisnema.financeiroweb.exception.RNException;
import br.com.sisnema.financeiroweb.model.Usuario;

public class UsuarioRN extends RN<Usuario> {

	public UsuarioRN() {
		super(new UsuarioDAO());
	}

	public void salvar(Usuario usuarioTela) throws RNException {

		try {
			if (usuarioTela.getCodigo() != null) {
				// update
				((UsuarioDAO) dao).atualizar(usuarioTela);

			} else {
				// insert
				Usuario usuarioBanco = buscarPorLogin(usuarioTela.getLogin());
				if (usuarioBanco != null) {
					throw new RNException("Já existe um usuário com o login informado.");
				}
				
				usuarioTela.getPermissoes().add(UsuarioPermissao.ROLE_USUARIO);
				dao.salvar(usuarioTela);
				(new CategoriaRN()).salvaEstruturaPadrao(usuarioTela);				
			}
		} catch (DAOException e) {
			throw new RNException(e.getMessage(), e);
		}

	}

	@Override
	public void excluir(Usuario model) throws RNException {		
			(new CategoriaRN()).excluir(model);			
			super.excluir(model);
	}
// movido para classe pai	
//
//	public Usuario obterPorId(Usuario filtro) {
//		return dao.obterPorId(filtro);
//	}
//
//	public List<Usuario> pesquisar(Usuario filtros) {
//		return dao.pesquisar(filtros);
//	}

	public Usuario buscarPorLogin(String login) {
		// váriavel dao é do tipo IDAO ou seja, pois apenas os métodos
		// genéricos, logo
		// para se ter acesso aos métodos específicos de UsuarioDAO temos de
		// torná-la
		// mais específica, realizando uma operação de DOWNCASTING.
		UsuarioDAO userDAO = (UsuarioDAO) dao;
		try {
			return userDAO.buscarPorLogin(login);
		} catch (DAOException e) {
			return null;
		}
	}

	public Usuario buscarPorLoginESenha(String login, String senha) {
		return ((UsuarioDAO) dao).buscarPorLoginESenha(login, senha);
	}

}

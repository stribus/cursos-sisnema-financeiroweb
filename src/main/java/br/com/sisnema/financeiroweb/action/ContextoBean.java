package br.com.sisnema.financeiroweb.action;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.sisnema.financeiroweb.domain.UsuarioPermissao;
import br.com.sisnema.financeiroweb.model.Usuario;

/**
 * Objetivo desta classe é conter os dados do usuario logado armazenando em um
 * único objeto tudo que possa ser necessario para a aplicacao, o qual estará em
 * escopo de sessão.
 */
@ManagedBean
@SessionScoped
public class ContextoBean implements Serializable {

	private static final long serialVersionUID = -8188279286811242712L;

	/** Contém a instância do usuário logado */
	private Usuario usuarioLogado = null;

	public Usuario getUsuarioLogado() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		usuarioLogado = (Usuario) session.getAttribute(LoginBean.USUARIO_LOGADO);
		return usuarioLogado;
	}

	/**
	 * Método que recebe uma determinada permissão e verifica se o usuário logado a
	 * possui
	 * 
	 * @param role
	 *            - permissão a ser verificada
	 * @return valor boleano informando se usuário possui a permissão
	 *         parametrizada
	 */
	public boolean hasRole(String role) {
		Usuario user = getUsuarioLogado();
		return user != null && user.getPermissoes().contains(UsuarioPermissao.valueOf(role));
	}
	/**
	 * Método que recebe uma determinada permissão e verifica se o usuário a
	 * possui
	 * 
	 * @param us 
	 * 			- usuario que sera verificado a permissao
	 * @param role
	 *            - permissão a ser verificada
	 * @return valor boleano informando se usuário possui a permissão
	 *         parametrizada
	 */
	public boolean hasRole(Usuario us, String role) {
		return us.getPermissoes().contains(UsuarioPermissao.valueOf(role));
	}
}

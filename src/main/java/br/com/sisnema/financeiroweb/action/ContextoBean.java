package br.com.sisnema.financeiroweb.action;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.sisnema.financeiroweb.domain.UsuarioPermissao;
import br.com.sisnema.financeiroweb.model.Usuario;

/**
 * Objetivo desta classe � conter os dados do usuario logado armazenando em um
 * �nico objeto tudo que possa ser necessario para a aplicacao, o qual estar� em
 * escopo de sess�o.
 */
@ManagedBean
@SessionScoped
public class ContextoBean implements Serializable {

	private static final long serialVersionUID = -8188279286811242712L;

	/** Cont�m a inst�ncia do usu�rio logado */
	private Usuario usuarioLogado = null;

	public Usuario getUsuarioLogado() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		usuarioLogado = (Usuario) session.getAttribute(LoginBean.USUARIO_LOGADO);
		return usuarioLogado;
	}

	/**
	 * M�todo que recebe uma determinada permiss�o e verifica se o usu�rio logado a
	 * possui
	 * 
	 * @param role
	 *            - permiss�o a ser verificada
	 * @return valor boleano informando se usu�rio possui a permiss�o
	 *         parametrizada
	 */
	public boolean hasRole(String role) {
		Usuario user = getUsuarioLogado();
		return user != null && user.getPermissoes().contains(UsuarioPermissao.valueOf(role));
	}
	/**
	 * M�todo que recebe uma determinada permiss�o e verifica se o usu�rio a
	 * possui
	 * 
	 * @param us 
	 * 			- usuario que sera verificado a permissao
	 * @param role
	 *            - permiss�o a ser verificada
	 * @return valor boleano informando se usu�rio possui a permiss�o
	 *         parametrizada
	 */
	public boolean hasRole(Usuario us, String role) {
		return us.getPermissoes().contains(UsuarioPermissao.valueOf(role));
	}
}

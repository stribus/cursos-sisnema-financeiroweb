package br.com.sisnema.financeiroweb.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import br.com.sisnema.financeiroweb.domain.UsuarioPermissao;
import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.negocio.ContaRN;

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

	private Conta contaAtiva;
	private List<String> landscapes;	
	
	public ContextoBean() {
		landscapes = new ArrayList<String>();
		for(int i =1 ; i<=8; i++){
			landscapes.add("n"+i+".jpg");
		}
	}
	
	
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
	
	public Conta getContaAtiva() {
		if(contaAtiva == null){
			ContaRN rn = new ContaRN();
			contaAtiva = rn.buscarFavorita(getUsuarioLogado());
			
			// Pode ser que o usuário ainda não possua uma conta favorita
			if(contaAtiva == null){
				
				// caso não encontre uma conta favorita, busca todas as contas do usuario
				List<Conta> contas = rn.pesquisar(new Conta(getUsuarioLogado()));
				
				// pega a primeira conta que encontrar...
				contaAtiva = contas.get(0);
//				for (Conta conta : contas) {
//					// pega a primeira conta que encontrar...
//					contaAtiva = conta;
//					break;
//				}
			}
		}
		
		return contaAtiva;
	}
	

	public void setContaAtiva(Conta contaAtiva) {
		this.contaAtiva = contaAtiva;
	}
	

	/**
	 * Sempre que se mudar na combo a conta selecionada, deve-se alterar
	 * a conta que esta ativa e na sessão...
	 * 
	 * metodo a ser chamado a partir de combo presente em menu_sistema.xhtml
	 */
	public void setContaAtiva(ValueChangeEvent event) {
		// obtem o código da conta que se selecionou....
		Integer codigo = (Integer) event.getNewValue();
		
		// busca a conta de acordo com o código
		contaAtiva = new ContaRN().obterPorId(new Conta(codigo));
	}
}

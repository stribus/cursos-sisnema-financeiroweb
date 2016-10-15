package br.com.sisnema.financeiroweb.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import br.com.sisnema.financeiroweb.domain.UsuarioPermissao;
import br.com.sisnema.financeiroweb.exception.RNException;
import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.negocio.ContaRN;
import br.com.sisnema.financeiroweb.negocio.UsuarioRN;

/**
 * Objetivo desta classe é conter os dados do usuario logado
 * armazenando em um único objeto tudo que possa ser necessario para a aplicacao,
 * o qual estará em escopo de sessão.
 */
@ManagedBean
@SessionScoped
public class ContextoBean implements Serializable {

	private static final long serialVersionUID = -8188279286811242712L;

	/** Contém a instância do usuário logado */
	private Usuario usuarioLogado = null;
	
	private Conta contaAtiva;
	
	private List<String> landscapes;
	
	private Locale locale;
	
	private List<Locale> idiomas;
	
	public ContextoBean() {
		landscapes = new ArrayList<String>();
		landscapes.add("n1.jpg");
		landscapes.add("n2.jpg");
		landscapes.add("n3.jpg");
		landscapes.add("n4.jpg");
		landscapes.add("n5.jpg");
		landscapes.add("n6.jpg");
		landscapes.add("n7.jpg");
		landscapes.add("n8.jpg");
	}
	
	public Usuario getUsuarioLogado() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
														.getExternalContext()
														.getSession(false);
		
		usuarioLogado = (Usuario) session.getAttribute(LoginBean.USUARIO_LOGADO);
		return usuarioLogado;
	}
	
	/**
	 * Método que recebe uma determinada permissão e verifica se o usuário a possui
	 * @param role - permissão a ser verificada
	 * @return valor boleano informando se usuário possui a permissão parametrizada
	 */
	public boolean hasRole(String role){
		Usuario user = getUsuarioLogado();
		return user != null && user.getPermissoes().contains(UsuarioPermissao.valueOf(role));
	}
	
	public boolean hasRole(Usuario us, String role){
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

	public List<String> getLandscapes() {
		return landscapes;
	}

	/**
	 * Retorna uma lista de Locale (idiomas suportados pelo sistema, 
	 * definido no arquivo faces-config.xml)
	 */
	public List<Locale> getIdiomas() {
		// Obtemos a instância corrente do JSF
		FacesContext context = FacesContext.getCurrentInstance();

		// Com ela obtemos uma lista dos idiomas suportados (definidos no arquivo faces-config.xml)
		Iterator<Locale> locales = context.getApplication().getSupportedLocales();
		idiomas = new ArrayList<Locale>();
		
		// Iteramos este objeto e retornamos uma lista SIMPLES com os Nomes dos mesmos
		while(locales.hasNext()) {
			idiomas.add(locales.next());
		}
		
		return idiomas;
	}
	
	/**
	 * Altera o idioma default do usuario na base de dados
	 * para que sempre fique nesse idioma (até ser alterado)
	 */
	public void setIdiomaUsuario(String idioma) {
		UsuarioRN usuarioRN = new UsuarioRN();
		try {
			usuarioLogado = usuarioRN.obterPorId(getUsuarioLogado());
			usuarioLogado.setIdioma(idioma);
			usuarioRN.salvar(usuarioLogado);
		} catch (RNException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao setar o idioma: " + e.getMessage(), null));
			return;
		}

		// APÓS ter alterado o idioma do usuario
		// seta o novo idioma no locale da sessao
		String[] info = idioma.split("_");
		locale = new Locale(info[0], info[1]);
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.getViewRoot().setLocale(locale);
		
	}
	
	public Locale getLocale() {
		if (locale == null) {
			// Entra logo após o login, quando o objeto ainda não foi criado
			Usuario usuario = getUsuarioLogado();
			
			// necessário para o cadastro usuario não estando logado
			if(usuario == null){
				locale = new Locale("pt", "BR");
				return locale;
			}
			
			String idioma = usuario.getIdioma();
			String[] info = idioma.split("_");
			locale = new Locale(info[0], info[1]);
		}
		
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}















package br.com.sisnema.financeiroweb.action;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.negocio.UsuarioRN;

@ManagedBean
@RequestScoped
public class LoginBean extends ActionBean<Usuario> {
	public static final String USUARIO_LOGADO = "usuarioLogado";
	private String user;
	private String password;

	public LoginBean() {
		super(new UsuarioRN());
	}

	public String logar(){
		Usuario usuarioLogado = ((UsuarioRN) negocio).buscarPorLoginESenha(user, password);
		
		if(usuarioLogado != null){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
															.getExternalContext()
															.getSession(true);
			
			session.setAttribute(USUARIO_LOGADO, usuarioLogado);
			//rediriciona a pagina pelo JSF alterando a url
			return "/restrito/principal?faces-redirect=true";
			
		} else {
			apresentarMensagemDeErro("Usu�rio/Senha inv�lidos");
		}
		
		return null;
	}
	
	public String logout() {
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext()
				.getSession(false);
	
		session.invalidate();
		
		return "publico/login?faces-redirect=true";
		
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}

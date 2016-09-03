package br.com.sisnema.financeiroweb.action;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

@ManagedBean
public class UsuarioBean {

	private String nome;
	private String email;
	private String senha;
	private String confirmaSenha;

	public String salvar() {
		if (!StringUtils.equals(senha, confirmaSenha)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senhas diferentes", ""));
			return null;
		} else {
			return "usuarioSucesso";
		}
			
	}
	
	public String novo() {
		return "usuario";
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

}

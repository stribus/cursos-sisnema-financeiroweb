package br.com.sisnema.financeiroweb.action;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.lang3.StringUtils;

import br.com.sisnema.financeiroweb.exception.RNException;
import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.negocio.UsuarioRN;

@ManagedBean
@RequestScoped
public class UsuarioBean extends ActionBean<Usuario> {

	private Usuario usuario = new Usuario();
	private String confirmaSenha;
	
	public UsuarioBean() {
		super(new UsuarioRN());
	}

	public String salvar() {
		
		try {
			if (!StringUtils.equals(usuario.getSenha(), confirmaSenha)) {
				apresentarMensagemDeErro("Senhas diferentes");
				return null;
				
			} else {
				
			String msg = "Usuário "
					+ (usuario.getCodigo() != null ? " alterado " : " inserido ") 
					+ "com sucesso";
			
			negocio.salvar(usuario);
			
			apresentarMensagemDeSucesso(msg);
			
			return "usuarioSucesso";

			}
		} catch (RNException e) {
			apresentarMensagemDeErro(e);

		}
		return null;

	}

	public String novo() {
		this.usuario = new Usuario();
		return "usuario";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

}

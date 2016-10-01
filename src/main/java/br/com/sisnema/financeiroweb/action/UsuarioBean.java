package br.com.sisnema.financeiroweb.action;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.lang3.StringUtils;

import br.com.sisnema.financeiroweb.domain.UsuarioPermissao;
import br.com.sisnema.financeiroweb.exception.LockException;
import br.com.sisnema.financeiroweb.exception.RNException;
import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.negocio.ContaRN;
import br.com.sisnema.financeiroweb.negocio.UsuarioRN;

@ManagedBean
@RequestScoped
public class UsuarioBean extends ActionBean<Usuario> {

	private Usuario usuario = new Usuario();
	private Conta conta = new Conta();
	private String confirmaSenha;
	private String destinoSalvar;
	private List<Usuario> lista;
	
	public UsuarioBean() {
		super(new UsuarioRN());
	}
	
	public String novo(){
		usuario = new Usuario();
		usuario.setAtivo(true);
		destinoSalvar = "/publico/usuarioSucesso";
		return "usuario";
	}
	
	public String editar(){
		destinoSalvar = "/admin/usuarioAdm";
		confirmaSenha = usuario.getSenha();
		return "/publico/usuario";
	}
	
	public String excluir(){
		
		try {
			negocio.excluir(usuario);
			lista = null;
			apresentarMensagemDeSucesso("Usu�rio exclu�do com sucesso.");
		} catch (RNException e) {
			apresentarMensagemDeErro(e);
		}
		
		return null;
	}
	
	public String salvar(){
		
		try {
			if(!StringUtils.equals(usuario.getSenha(), confirmaSenha)){
				apresentarMensagemDeErro("Senhas diferentes");
				
				return null;
			}
			
			String msg = "Usu�rio "+ (usuario.getCodigo() != null ? " alterado " : " inserido ") +
						 "com sucesso";
			
			negocio.salvar(usuario);
			
			if(StringUtils.isNotBlank(conta.getDescricao())){
				conta.setFavorita(true);
				conta.setUsuario(usuario);
				new ContaRN().salvar(conta);
			}
			
			apresentarMensagemDeSucesso(msg);
			lista = null;
			
			return destinoSalvar;
			
		} catch (RNException e) {
			apresentarMensagemDeErro(e);
			
			if(e.getCause() instanceof LockException){
				return destinoSalvar;
			}
		}
		return null;
	}
	
	public void validarLogin(){
		Usuario us = ((UsuarioRN) negocio).buscarPorLogin(usuario.getLogin());
		
		if(us != null){
			apresentarMensagemDeErro("J� existe um usu�rio para o login informado.");
		}
	}

	public String ativar(){
		try {
			usuario.setAtivo(!usuario.isAtivo());
			negocio.salvar(usuario);
			apresentarMensagemDeSucesso("Usu�rio "+
										(usuario.isAtivo() ? "ativado" : "inativado")+
										" com sucesso!");
			
		} catch (RNException e) {
			usuario.setAtivo(!usuario.isAtivo());
			apresentarMensagemDeErro(e);
		}
		return null;
	}
	
	public void atribuiPermissao(Usuario usuario, String permissao){
		UsuarioPermissao up = UsuarioPermissao.valueOf(permissao);
		
		if(usuario.getPermissoes().contains(up)){
			usuario.getPermissoes().remove(up);
		} else {
			usuario.getPermissoes().add(up);
		}
	}
	
	
	public List<Usuario> getLista() {
		if(lista == null){
			lista = negocio.pesquisar(new Usuario());
		}
		return lista;
	}
	
	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDestinoSalvar() {
		return destinoSalvar;
	}

	public void setDestinoSalvar(String destinoSalvar) {
		this.destinoSalvar = destinoSalvar;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

}

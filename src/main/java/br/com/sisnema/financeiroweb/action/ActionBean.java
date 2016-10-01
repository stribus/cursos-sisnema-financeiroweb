package br.com.sisnema.financeiroweb.action;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import br.com.sisnema.financeiroweb.exception.RNException;
import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.negocio.IRN;

public class ActionBean<T> {
	
	protected IRN<T> negocio;
	
	@ManagedProperty(value="#{contextoBean}") 
	private ContextoBean contextoBean;

	public ActionBean(IRN<T> negocio) {
		this.negocio = negocio;
	}
	
	protected final Conta obterContaAtiva(){
		return contextoBean.getContaAtiva();
	}
	
	protected final Usuario obterUsuarioLogado(){
		return contextoBean.getUsuarioLogado();
	}
	
	protected final void apresentarMensagemDeSucesso(String msg) {
		apresentarMensagem(msg, FacesMessage.SEVERITY_INFO);
	}
	
	protected final void apresentarMensagemDeErro(String msg) {
		apresentarMensagem(msg, FacesMessage.SEVERITY_ERROR);
	}
	
	protected final void apresentarMensagemDeErro(RNException e) {
		apresentarMensagem(e.getMessage(), FacesMessage.SEVERITY_ERROR);
	}
	
	protected final void apresentarMensagem(String msg, Severity severity) {
		FacesContext.getCurrentInstance() .addMessage(null, new FacesMessage(severity, msg, ""));
	}


	public ContextoBean getContextoBean() {
		return contextoBean;
	}


	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}
}

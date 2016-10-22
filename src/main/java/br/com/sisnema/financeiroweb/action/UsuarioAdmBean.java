package br.com.sisnema.financeiroweb.action;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.sisnema.financeiroweb.domain.UsuarioPermissao;
import br.com.sisnema.financeiroweb.exception.RNException;
import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.negocio.UsuarioRN;
import br.com.sisnema.financeiroweb.util.DynamicDataModel;

@ManagedBean
@ViewScoped
public class UsuarioAdmBean extends ActionBean<Usuario> implements ActionPaginator<Usuario> {

	private static final long serialVersionUID = -3412770224589173691L;
	
	@Inject
	private Usuario selecionado;
	
	@Inject
	private DynamicDataModel<Usuario> dataModel;
	
	public UsuarioAdmBean() {
		super(new UsuarioRN());
	}
	
	@PostConstruct
	public void init(){
		dataModel = new DynamicDataModel<Usuario>();
		dataModel.setPaginator(this);
	}
	
	public String ativar(Usuario usuario){
		try {
			usuario.setAtivo(!usuario.isAtivo());
			negocio.salvar(usuario);
			
			apresentarMensagemDeSucesso("Usuário "+
										(usuario.isAtivo() ? "ativado" : "inativado")+
										" com sucesso!");
			
		} catch (RNException e) {
			apresentarMensagemDeErro(e);
		}
		
		return null;
	} 
	
	public void atribuiPermissao(Usuario usuario, String permissao){
		try {
			UsuarioPermissao up = UsuarioPermissao.valueOf(permissao);
			
			if(usuario.getPermissoes().contains(up)){
				usuario.getPermissoes().remove(up);
			} else {
				usuario.getPermissoes().add(up);
			}
			negocio.salvar(usuario);
			
		} catch (RNException e) {
			apresentarMensagemDeErro(e);
		}
	}

	public List<Usuario> recuperarListaPaginada(Integer firstResult, Integer maxResults, String orderBy, boolean asc, Map<String, Object> filters) {
		return ((UsuarioRN) negocio).pesquisar(new Usuario(), firstResult, maxResults, orderBy, asc, filters);
	}

	public Long recuperarCount(Map<String, Object> filters) {
		return ((UsuarioRN) negocio).pesquisarCount(new Usuario(), filters);
	}

	public Usuario getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Usuario selecionado) {
		this.selecionado = selecionado;
	}

	public DynamicDataModel<Usuario> getDataModel() {
		return dataModel;
	}

	public void setDataModel(DynamicDataModel<Usuario> dataModel) {
		this.dataModel = dataModel;
	}

}

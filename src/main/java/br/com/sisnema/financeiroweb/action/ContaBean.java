package br.com.sisnema.financeiroweb.action;

import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.StreamedContent;

import br.com.sisnema.financeiroweb.exception.RNException;
import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.negocio.ContaRN;
import br.com.sisnema.financeiroweb.util.RelatorioUtil;

@ManagedBean
@RequestScoped
public class ContaBean extends ActionBean<Conta> {

	private Conta selecionada = new Conta();
	private List<Conta> lista;
	private StreamedContent	arquivoRetorno;
	private int				tipoRelatorio;
	private String 			fileName;
	
	public ContaBean() {
		super(new ContaRN());
	}
	
	public void salvar(){
		try {
			selecionada.setUsuario(obterUsuarioLogado());
			negocio.salvar(selecionada);
			selecionada = new Conta();
			lista = null;
			apresentarMensagemDeSucesso("Conta salva com sucesso");
			
		} catch (RNException e) {
			apresentarMensagemDeErro(e);
		}
	}
	
	public void excluir(){
		try {
			negocio.excluir(selecionada);
			selecionada = new Conta();
			lista = null;
			apresentarMensagemDeSucesso("Conta excluida com sucesso");
			
		} catch (RNException e) {
			apresentarMensagemDeErro(e);
		}
	}
	
	public void tornarFavorita(){
		try {
			((ContaRN) negocio).tornarFavorita(selecionada);
			selecionada = new Conta();
			lista = null;
			apresentarMensagemDeSucesso("Conta favoritada com sucesso");
		} catch (RNException e) {
			apresentarMensagemDeErro(e);
		}
	}
	
	public List<Conta> getLista() {
		
		if(lista == null){
			lista = negocio.pesquisar(new Conta(obterUsuarioLogado()));
		}
		
		return lista;
	}
	public String geraPDF(){
		gerarRelatorio(false); 
		return "contaRel";
	}
	
	public StreamedContent getArquivoRetorno() {
		gerarRelatorio(false); 
		return arquivoRetorno;
	}
	
	public StreamedContent getArquivoRetornoLista() {
		gerarRelatorio(true); 
		return arquivoRetorno;
	}

	private void gerarRelatorio(boolean withCollection) {
		Usuario us = obterUsuarioLogado();
		String login = us.getLogin();
		String nomeRelatorioJasper = withCollection ? "contasHbf":"contas";
		String nomeRelatorioSaida = login + "_contas";
		
		List<Conta> contas = withCollection ? getLista() : null;
		
		RelatorioUtil relatorioUtil = new RelatorioUtil();
		HashMap<String,Object> parametrosRelatorio = new HashMap<String,Object>();
		parametrosRelatorio.put("codUsuario", us.getCodigo());
		parametrosRelatorio.put("nmUsuario", us.getNome());
		
		try {
			this.arquivoRetorno = relatorioUtil.geraRelatorio(parametrosRelatorio, 
															  nomeRelatorioJasper, 
															  nomeRelatorioSaida, 
															  tipoRelatorio, 
															  contas);
			
			fileName = arquivoRetorno.getName();
		} catch (Exception e) {
			apresentarMensagemDeErro(e.getMessage());
		}
	}

	public Conta getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(Conta selecionada) {
		this.selecionada = selecionada;
	}

	public int getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(int tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}

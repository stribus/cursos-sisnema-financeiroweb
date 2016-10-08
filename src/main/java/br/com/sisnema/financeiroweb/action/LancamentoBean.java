package br.com.sisnema.financeiroweb.action;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.lang3.time.DateUtils;

import br.com.sisnema.financeiroweb.exception.RNException;
import br.com.sisnema.financeiroweb.model.Lancamento;
import br.com.sisnema.financeiroweb.negocio.LancamentoRN;
import br.com.sisnema.financeiroweb.vo.LancamentoVO;

@ManagedBean
@RequestScoped
public class LancamentoBean extends ActionBean<Lancamento> {

	private List<LancamentoVO> lista;
	private List<LancamentoVO> listaAteHoje;
	private List<LancamentoVO> listaFuturos;
	private Lancamento editado = new Lancamento();

	public LancamentoBean() {
		super(new LancamentoRN());
		novo();
	}
	
	public void novo() {
		editado = new Lancamento();
		editado.setData(new Date());
		lista = null;
	}
	
	public void editar(Integer codigo){
		editado = negocio.obterPorId(new Lancamento(codigo));
	}
	
	public void salvar(){
		try {
			editado.setUsuario(obterUsuarioLogado());
			editado.setConta(obterContaAtiva());
			negocio.salvar(editado);
			novo();
		} catch (RNException e) {
			apresentarMensagemDeErro(e);
		}
	}
	
	public void excluir(Integer codigo){
		try {
			editado = negocio.obterPorId(new Lancamento(codigo));
			negocio.excluir(editado);
			novo();
		} catch (RNException e) {
			apresentarMensagemDeErro(e);
		}
	}

	public List<LancamentoVO> getLista() {
		if(lista == null){
			Date mesPassado = DateUtils.addMonths(new Date(), -1);
			lista = ((LancamentoRN) negocio).pesquisarLancamentosComSaldo( obterContaAtiva(), 
																		   mesPassado, 
																		   null);
		}
		return lista;
	}

	public List<LancamentoVO> getListaAteHoje() {
		if(listaAteHoje == null){
			listaAteHoje = ((LancamentoRN) negocio).pesquisar( obterContaAtiva(), 
															   null,
															   new Date());
			
		}
		return listaAteHoje;
	}

	public List<LancamentoVO> getListaFuturos() {
		if(listaFuturos == null){
			Date amanha = DateUtils.addDays(new Date(), 1);
			listaFuturos = ((LancamentoRN) negocio).pesquisar( obterContaAtiva(), 
															   amanha, 
															   null);
		}
		return listaFuturos;
	}

	public Lancamento getEditado() {
		return editado;
	}

	public void setEditado(Lancamento editado) {
		this.editado = editado;
	}

}

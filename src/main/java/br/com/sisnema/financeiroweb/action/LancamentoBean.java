package br.com.sisnema.financeiroweb.action;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.time.DateUtils;

import br.com.sisnema.financeiroweb.exception.RNException;
import br.com.sisnema.financeiroweb.model.Lancamento;
import br.com.sisnema.financeiroweb.negocio.LancamentoRN;
import br.com.sisnema.financeiroweb.vo.LancamentoVO;

@ManagedBean
@ViewScoped
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

	public void editar(Integer codigo) {
		editado = this.negocio.obterPorId(new Lancamento(codigo));
	}

	public void salvar() {
		editado.setUsuario(obterUsuarioLogado());

		editado.setConta(obterContaAtiva());
		try {
			negocio.salvar(editado);
			novo();
		} catch (RNException e) {
			apresentarMensagemDeErro(e);
		}
	}

	public void excluir(Integer codigo) {
		editado = this.negocio.obterPorId(new Lancamento(codigo));
		try {
			negocio.excluir(editado);
			novo();
		} catch (RNException e) {
			apresentarMensagemDeErro(e);
		}

	}

	public List<LancamentoVO> getLista() {
		if (lista == null) {
			Date dataCriada = DateUtils.addMonths(new Date(), -1);
			lista = ((LancamentoRN)negocio).pesquisarLancamentosComSaldo(obterContaAtiva(), dataCriada, null);
		}
		return lista;
	}

	public List<LancamentoVO> getListaAteHoje() {
		if (listaAteHoje == null) {
			listaAteHoje = ((LancamentoRN)negocio).pesquisarLancamentosComSaldo(obterContaAtiva(), null, new Date());
		}
		return listaAteHoje;
	}

	public List<LancamentoVO> getListaFuturos() {
		if (listaFuturos == null) {
			Date dataAmanha = DateUtils.addDays(new Date(), 1);
			listaFuturos= ((LancamentoRN)negocio).pesquisarLancamentosComSaldo(obterContaAtiva(), dataAmanha,null);
		}
		return listaFuturos;
	}
	/*
	 * getLista() caso lista nula criar uma data removendo um mês da data atual
	 * (DateUtils.a..) chamar o método pesquisarLancamentosComSaldo passando:
	 * contaAtiva, dataCriada, null getListaAteHoje() caso listaAteHoje nula
	 * chamar o método pesquisar passando: contaAtiva, null, new Date()
	 * getListaFuturos() caso listaFuturos nula criar uma data adicionando um
	 * dia da data atual chamar o método pesquisar passando: contaAtiva, amanha,
	 * null
	 */

	public Lancamento getEditado() {
		return editado;
	}

	public void setEditado(Lancamento editado) {
		this.editado = editado;
	}
}

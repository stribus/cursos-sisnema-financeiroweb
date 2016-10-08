package br.com.sisnema.financeiroweb.negocio;

import java.util.Date;
import java.util.List;

import br.com.sisnema.financeiroweb.dao.LancamentoDAO;
import br.com.sisnema.financeiroweb.exception.DAOException;
import br.com.sisnema.financeiroweb.exception.RNException;
import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Lancamento;
import br.com.sisnema.financeiroweb.vo.LancamentoVO;

public class LancamentoRN extends RN<Lancamento> {

	public LancamentoRN() {
		super(new LancamentoDAO());

	}

	public void salvar(Lancamento model) throws RNException {
		try {
			if (model.getCategoria().getPai() == null) {
				throw new RNException("Categoria inválida. Selecione uma Categoria menos genérica.");
			}
			dao.salvar(model);
		} catch (DAOException e) {
			throw new RNException("Não foi possível salvar o lancamento. Erro: " + e.getMessage(), e);
		}
	}

	public List<LancamentoVO> pesquisar(Conta conta, Date dataInicio, Date dataFim) {
		return ((LancamentoDAO) dao).pesquisar(conta, dataInicio, dataFim);
	}

	public float saldo(Conta conta, Date date) {
		return ((LancamentoDAO) dao).saldo(conta, date) + conta.getSaldoInicial();
	}
}

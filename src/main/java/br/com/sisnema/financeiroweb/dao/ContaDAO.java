package br.com.sisnema.financeiroweb.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.sisnema.financeiroweb.model.Conta;

public class ContaDAO extends DAO<Conta> {


	public Conta obterPorId(Conta filtro) {
		return getSession().get(Conta.class,filtro.getCodigo());
	}

	@SuppressWarnings("unchecked")
	public List<Conta> pesquisar(Conta filtros) {
		Criteria criteria = getSession().createCriteria(Conta.class);
		criteria.add(Restrictions.eq("usuario",filtros.getUsuario()));
		return criteria.list();
	}

}

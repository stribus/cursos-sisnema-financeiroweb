package br.com.sisnema.financeiroweb.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;

import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Lancamento;
import br.com.sisnema.financeiroweb.vo.LancamentoVO;

public class LancamentoDAO extends DAO<Lancamento> {

	public Lancamento obterPorId(Lancamento filtro) {
		return getSession().get(Lancamento.class, filtro.getCodigo());
	}

	public List<Lancamento> pesquisar(Lancamento filtros) {
		return null;
	}

	public List<LancamentoVO> pesquisar(Conta conta, Date dataInicio, Date dataFim) {
		Criteria crit = getSession().createCriteria(Lancamento.class,"lanc.");
		
		crit.createAlias( "lanc."+Lancamento.Fields.CATEGORIA.toString(),
				  Lancamento.Fields.CATEGORIA.toString(),
				  JoinType.INNER_JOIN
			    );
		crit.add(Restrictions.eq(Lancamento.Fields.CONTA.toString(), conta));
		
		if ((dataInicio != null) && (dataFim != null)){
			crit.add(Restrictions.between(Lancamento.Fields.DATA.toString(), dataInicio,dataFim));
		} else if (dataInicio != null) {
			crit.add(Restrictions.ge(Lancamento.Fields.DATA.toString(), dataInicio));
		} else if (dataFim != null) {
			crit.add(Restrictions.le(Lancamento.Fields.DATA.toString(), dataFim));
		} 
		
		crit.addOrder(Order.asc(Lancamento.Fields.DATA.toString()));
		
		
		/**projeção -> campos resultantes da consultas que se deseja retornar dentro de um outro objeto*/
		
		crit.setProjection(Projections.projectionList()
				.add(Projections.property(Lancamento.Fields.CODIGO.toString()),LancamentoVO.Fields.CODIGO.toString())
				.add(Projections.property(Lancamento.Fields.DATA.toString()),LancamentoVO.Fields.DATA.toString())
				.add(Projections.property(Lancamento.Fields.VALOR.toString()),LancamentoVO.Fields.VALOR.toString())
				.add(Projections.property(Lancamento.Fields.DESCRICAO.toString()),LancamentoVO.Fields.DESCRICAO.toString())
				.add(Projections.property(Lancamento.Fields.FATOR_CATEGORIA.toString()),LancamentoVO.Fields.FATOR_CATEGORIA.toString())
				);
		//Altera o objeto resultante da pesquisa
		crit.setResultTransformer(Transformers.aliasToBean(LancamentoVO.class));
		
		return crit.list();
	}

	
	public float saldo(Conta conta, Date date) {
		String sql = "select "
				+ "		sum(lanc.valor * cat.fator) as saldo "
				+ " from lancamento lanc "
				+ " 	inner join categoria cat on cat.codigo = lanc.cod_categoria "
				+ " where "
				+ "		lanc.cod_conta = :conta"
				+ "		and lanc.data_2 <= :data";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("conta", conta.getCodigo());
		query.setParameter("data", date);
		
		BigDecimal result = (BigDecimal) query.uniqueResult();
		
		if(result != null){
			return result.floatValue();
		}
		
		return 0f;
	}
}

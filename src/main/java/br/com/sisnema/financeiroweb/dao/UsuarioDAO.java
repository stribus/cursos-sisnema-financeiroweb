package br.com.sisnema.financeiroweb.dao;

import java.util.List;

import javax.persistence.OptimisticLockException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.sisnema.financeiroweb.exception.DAOException;
import br.com.sisnema.financeiroweb.exception.LockException;
import br.com.sisnema.financeiroweb.model.Usuario;

public class UsuarioDAO extends DAO<Usuario> {

	public void salvar(Usuario model) throws DAOException {
		try {
			// abertura e fechamento de transacao eh feita pelo filtro.
			getSession().save(model);
		} catch (Exception e) {
			throw new DAOException("Não foi possivel inserir usuario. Erro: \n" + e.getMessage(), e);
		}

	}

	public void atualizar(Usuario model) throws DAOException {
		try {
			// abertura e fechamento de transacao eh feita pelo filtro.
			getSession().update(model);
		} catch (OptimisticLockException e) {
			rollback();
			beginTransaction();
			throw new LockException("Este registro acaba de ser atualizado por outro usuario. " + "Refaça a pesquisa.",
					e);
		} catch (Exception e) {
			throw new DAOException("Não foi possivel atualizar usuario. Erro: \n" + e.getMessage(), e);
		}

	}

	public void excluir(Usuario model) throws DAOException {
		try {
			// abertura e fechamento de transacao eh feita pelo filtro.
			getSession().delete(model);
		} catch (Exception e) {
			throw new DAOException("Não foi possivel excluir usuario. Erro: \n" + e.getMessage(), e);
		}
	}

	public Usuario obterPorId(Usuario filtro) {
		return getSession().get(Usuario.class, filtro.getCodigo());
	}

	public Usuario buscarPorLogin(String login) throws DAOException {
		try {
			Criteria criteria = getSession().createCriteria(Usuario.class);
			criteria.add(Restrictions.eq("login", login));
			return (Usuario) criteria.uniqueResult();
		} catch (HibernateException e) {
			throw new DAOException("Não foi possivel buscar usuario. Erro: \n" + e.getMessage(), e);
		}
	}

	public Usuario buscarPorLoginESenha(String login, String senha) throws DAOException {

		try {
			Criteria criteria = getSession().createCriteria(Usuario.class);
			criteria.add(Restrictions.eq("login", login));
			criteria.add(Restrictions.eq("senha", senha));
			return (Usuario) criteria.uniqueResult();
		} catch (HibernateException e) {
			throw new DAOException("Não foi possivel buscar usuario. Erro: \n" + e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> pesquisar(Usuario filtros) {
		Criteria criteria = getSession().createCriteria(Usuario.class);

		if (StringUtils.isNotBlank(filtros.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtros.getNome(), MatchMode.ANYWHERE));
		}

		return criteria.list();
	}

}

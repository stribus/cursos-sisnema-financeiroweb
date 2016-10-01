package br.com.sisnema.financeiroweb.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;

import br.com.sisnema.financeiroweb.exception.DAOException;
import br.com.sisnema.financeiroweb.util.JPAUtil;

/**
 * Classe abstrata que herdara os comportamentos de {@link IDAO} e conter�
 * atributos e funcionalidades gen�ricas a todas as filhas
 */
public abstract class DAO<T> implements IDAO<T> {

	/**
	 * Como todas as DAOS ir�o possuir uma sessao, criaremos a mesma
	 * na classe pai, sendo ela HERDADA pelas filhas....
	 */
    protected final EntityManager em;
	
	/**
	 * M�todo construtor de DAO para INICIALIZAR a sessao
	 * do hibernate
	 */
	public DAO() {
		em = JPAUtil.getEntityManager();
	}
	
	public void salvar(T model) throws DAOException {
		try {
			getSession().saveOrUpdate(model);
		} catch (Exception e) {
			throw new DAOException("N�o foi poss�vel persistir o registro. Erro:"+e.getMessage(),
					e);
		}
	}

	public void excluir(T model) throws DAOException {
		try {
			getSession().delete(model);
		} catch (Exception e) {
			throw new DAOException("N�o foi poss�vel excluir o registro. Erro:"+e.getMessage(),
					e);
		}
	}

	protected final Session getSession() {
		return (Session) em.unwrap(Session.class);
	}

	protected final void commit() {
		getSession().getTransaction().commit();
	}

	protected final void rollback() {
    	JPAUtil.getEntityManager().getTransaction().rollback();
	}
	
	protected final void beginTransaction() {
    	JPAUtil.getEntityManager().getTransaction().begin();
	}
}

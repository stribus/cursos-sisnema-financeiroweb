package br.com.sisnema.financeiroweb.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;

import br.com.sisnema.financeiroweb.util.JPAUtil;

/**
 * Classe abstrata que herdara os comportamentos de {@link IDAO} e conter�
 * atributos e funcionalidades gen�ricas a todas as filhas
 */
public abstract class DAO<T> implements IDAO<T> {

	/**
	 * Como todas as DAOS ir�o possuir uma sessao, criaremos a mesma na classe
	 * pai, sendo ela HERDADA pelas filhas....
	 */
	protected final EntityManager em;

	/**
	 * M�todo construtor de DAO para INICIALIZAR a sessao do hibernate
	 */
	public DAO() {
		em = JPAUtil.getEntityManager();
	}

	protected final Session getSession() {
		return (Session) em.unwrap(Session.class);
	}

	protected final void commit() {
		getSession().getTransaction().commit();
	}

	protected final void rollback() {
		getSession().getTransaction().rollback();
	}

	protected final void beginTransaction() {
		getSession().beginTransaction();
	}
}

package br.com.sisnema.financeiroweb.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Classe utilit�ria para disponibilizar as conex�es com o banco...
 * Conter� uma F�brica de Entity Manager (que conter� a sessao do Hibernate)
 */
public class JPAUtil {

	// Nome da persistence-unit registrada no arquivo persistence.xml --> Livro pg 29...
	private static final String PERSISTENCE_UNIT_NAME = "financeiro-persistence";

	// Criamos uma Thread para conter a EntityManager da requisi��o
	private static ThreadLocal<EntityManager> manager = new ThreadLocal<EntityManager>();

	private static EntityManagerFactory factory;
	
	static {
		// Vari�vel est�tica inicializada APENAS UMA VEZ que disponibilizar� instancias do EntityManager
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}


	public static boolean isEntityManagerOpen(){
		return JPAUtil.manager.get() != null && JPAUtil.manager.get().isOpen();
	}
	
	/**
	 * Disponibiliza um EntityManager utilizando a f�brica criada anteriormente
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = JPAUtil.manager.get();
		if (em == null || !em.isOpen()) {
			em = factory.createEntityManager();
			manager.set(em);
		}
		return em;
	}
	
	/**
	 * Que finaliza o EntityManager comitando a transa��o caso esteja ativa
	 */
	public static void closeEntityManager() {
		EntityManager em = JPAUtil.manager.get();
		if (em != null) {
			EntityTransaction tx = em.getTransaction();
			if (tx.isActive()) { 
				tx.commit();
			}
			em.close();
			JPAUtil.manager.set(null);
		}
	}
	
	/**
	 * Ao finalizar a aplica��o no container dever� se chamar este m�todo para
	 * fechar a f�brica de EntityManager
	 */
	public static void closeEntityManagerFactory(){
		closeEntityManager();
		JPAUtil.factory.close();
	}
}
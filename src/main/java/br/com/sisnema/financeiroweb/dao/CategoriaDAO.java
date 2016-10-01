package br.com.sisnema.financeiroweb.dao;

import java.util.List;

import javax.persistence.OptimisticLockException;

import org.hibernate.Query;

import br.com.sisnema.financeiroweb.exception.DAOException;
import br.com.sisnema.financeiroweb.exception.LockException;
import br.com.sisnema.financeiroweb.model.Categoria;

public class CategoriaDAO extends DAO<Categoria> {

	public Categoria salvarCategoria(Categoria categoriaDaTela) throws DAOException {
		Categoria persistido = null;
		try {
			persistido = (Categoria) getSession().merge(categoriaDaTela);
			getSession().flush();
			getSession().clear();
			// commit();
			// beginTransaction();
		} catch (OptimisticLockException ole) {
			rollback();
			beginTransaction();

			throw new LockException("Este registro acaba de ser atualizado por outro usuário. " + "Refaça a pesquisa",
					ole);

		} catch (Exception e) {
			rollback();
			beginTransaction();
			throw new DAOException("Não foi possível persistir o registro. Erro:" + e.getMessage(), e);
		}
		return persistido;
	}

	@Override
	public void excluir(Categoria filtro) throws DAOException {

		try {
			getSession().delete(obterPorId(filtro));

			commit();
			beginTransaction();

		} catch (OptimisticLockException ole) {
			rollback();
			beginTransaction();

			throw new LockException("Este registro acaba de ser atualizado por outro usuário. " + "Refaça a pesquisa",
					ole);

		} catch (Exception e) {
			rollback();
			beginTransaction();
			throw new DAOException("Não foi possível persistir o registro. Erro:" + e.getMessage(), e);
		}

	}

	public Categoria obterPorId(Categoria filtro) {
		return getSession().get(Categoria.class, filtro.getCodigo());
	}

	public List<Categoria> pesquisar(Categoria filtros) {
		// HQL --> Hibernate Query Language --> baseia-se no nome da classe e seus atributos...
		// SQL --> Structured Query Language --> baseia-se no nome da tabela e suas colunas...
		
		String hql = "select c from Categoria c where c.pai is null and c.usuario = :usuario";
		Query query = getSession().createQuery(hql);
		query.setParameter("usuario", filtros.getUsuario());
		return query.list();
	}

}

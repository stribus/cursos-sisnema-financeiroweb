package br.com.sisnema.financeiroweb.dao;

import java.util.List;

import javax.persistence.OptimisticLockException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.sisnema.financeiroweb.exception.DAOException;
import br.com.sisnema.financeiroweb.exception.LockException;
import br.com.sisnema.financeiroweb.model.Usuario;

public class UsuarioDAO extends DAO<Usuario> {


	public void atualizar(Usuario model) throws DAOException {
		try {

			// Caso no usuario que veio da tela não estejam armazenadas as
			// permissoes
			// vamos ao banco e buscamo-as...
			if (model.getPermissoes().isEmpty()) {
				Usuario user = obterPorId(model);
				// setamos as permissoes no usuario a ser salvo
				model.setPermissoes(user.getPermissoes());

				// removemos da sessao o objeto para não dar conflito
				// com o objeto a ser salvo proveniente do banco
				getSession().evict(user);
			}
			
			// abertura e fechamento de transacao eh feita pelo filtro.
			getSession().update(model);
			// adicionado para levantar exception de lock quando este ocorre
			commit();
			beginTransaction();

		} catch (OptimisticLockException e) {
			rollback();
			beginTransaction();
			throw new LockException("Este registro acaba de ser atualizado por outro usuario. " + "Refaça a pesquisa.",
					e);
		} catch (Exception e) {
			throw new DAOException("Não foi possivel atualizar usuario. Erro: \n" + e.getMessage(), e);
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

	public Usuario buscarPorLoginESenha(String login, String senha){
		Criteria criteria = getSession().createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("login", login));
		criteria.add(Restrictions.eq("senha", senha));

		Usuario user = (Usuario) criteria.uniqueResult();
		
		if(user != null){
			Hibernate.initialize(user.getPermissoes());
		}
		
		return user;
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

package com.bugtracker.services.implementation;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import com.bugtracker.entity.CommentModel;
import com.bugtracker.entity.CommentModel;
import com.bugtracker.entity.UserModel;
import com.bugtracker.services.interfaces.CommentServiceLocal;
import com.bugtracker.utils.HibernateUtils;

/**
 * Session Bean implementation class CommentService
 */
@Stateless
public class CommentService implements CommentServiceLocal {

	/**
	 * Default constructor.
	 */
	public CommentService() {
		// TODO Auto-generated constructor stub
	}

	 
    @PersistenceContext
	 protected EntityManager em;


	@Override
	public List<CommentModel> all() {
		// TODO Auto-generated method stub
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		
		CriteriaQuery<CommentModel> criteriaQuery = criteriaBuilder.createQuery(CommentModel.class);
		Root<CommentModel> from = criteriaQuery.from(CommentModel.class);
		
		criteriaQuery.orderBy(criteriaBuilder.asc(from.get("date")));
		criteriaQuery.select(from);
		
		TypedQuery<CommentModel> query = em.createQuery(criteriaQuery);
		
		return query.getResultList();

	}

	@Override
	public CommentModel getById(Long id) {
		// TODO Auto-generated method stub
		 try {
	            CommentModel instance = em.find(CommentModel.class, id);
	            return instance;
	        } catch (RuntimeException re) {
	            throw re;
	        }
	}

	@Override
	public CommentModel add(CommentModel entity) {
		// TODO Auto-generated method stub
		
		em.persist(entity);
		

		return entity;
	}

	@Override
	public CommentModel update(CommentModel entity) {
		// TODO Auto-generated method stub
		em.merge(entity);
		em.flush();
		return entity;
	}

	@Override
	public void delete(CommentModel entity) {
		// TODO Auto-generated method stub

		em.remove(entity);

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		CommentModel entity = getById(id);
		delete(entity);

	}
}

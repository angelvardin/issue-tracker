package com.bugtracker.services.implementation;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.MapAttribute;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.bugtracker.entity.UserModel;
import com.bugtracker.services.interfaces.UserServiceLocal;
import com.bugtracker.utils.HibernateUtils;

import sun.net.www.content.audio.x_aiff;

/**
 * Session Bean implementation class UserService
 */
@Stateless
public class UserService implements UserServiceLocal {

	
	 @PersistenceContext
	 protected EntityManager em;
	
    /**
     * Default constructor. 
     */
    public UserService() {
        // TODO Auto-generated constructor stub
    }


	@Override
	public List<UserModel> all() {
		// TODO Auto-generated method stub
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		
		CriteriaQuery<UserModel> criteriaQuery = criteriaBuilder.createQuery(UserModel.class);
		Root<UserModel> from = criteriaQuery.from(UserModel.class);
		
		criteriaQuery.select(from);
		
		TypedQuery<UserModel> query = em.createQuery(criteriaQuery);
		
		return query.getResultList();

	}

	@Override
	public UserModel getById(Long id) {
		// TODO Auto-generated method stub
		 try {
	            UserModel instance = em.find(UserModel.class, id);
	            return instance;
	        } catch (RuntimeException re) {
	            throw re;
	        }
	}

	@Override
	public UserModel add(UserModel entity) {
		// TODO Auto-generated method stub
		
		
		em.persist(entity);
		

		return entity;
	}

	@Override
	public UserModel update(UserModel entity) {
		// TODO Auto-generated method stub
		em.merge(entity);

		return entity;
	}

	@Override
	public void delete(UserModel entity) {
		// TODO Auto-generated method stub

		em.remove(entity);
		

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		UserModel entity = getById(id);
		delete(entity);

	}
	
	@Override
	public UserModel loginUser(String username, String password) {
		// TODO Auto-generated method stub
	    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<UserModel> criteriaQuery = criteriaBuilder.createQuery(UserModel.class);
		Root<UserModel> from = criteriaQuery.from(UserModel.class);
		
		
		
		criteriaQuery.where(criteriaBuilder.and(
				criteriaBuilder.equal(from.get("username"), username),
				criteriaBuilder.equal(from.get("password"), password)
				));
		
		criteriaQuery.select(from);
		
		TypedQuery<UserModel> query = em.createQuery(criteriaQuery);
		
		UserModel result;
		
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
			result=null;
		}
		
		return result;
	}

	@Override
	public UserModel checkUserExists(String username, Long id) {
		// TODO Auto-generated method stub
		if (username==null&&id==null) {
			return null;
		}
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<UserModel> criteriaQuery = criteriaBuilder.createQuery(UserModel.class);
		Root<UserModel> from = criteriaQuery.from(UserModel.class);
		
		criteriaQuery.where(criteriaBuilder.or(
				criteriaBuilder.equal(from.get("username"), username),
				criteriaBuilder.equal(from.get("id"), id)
				));
		
		criteriaQuery.select(from);
		
		TypedQuery<UserModel> query = em.createQuery(criteriaQuery);

		
		UserModel result;
		
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
			result=null;
		}
		
		return result;
		
	}
	

}

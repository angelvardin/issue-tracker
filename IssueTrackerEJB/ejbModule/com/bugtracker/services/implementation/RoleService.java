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
import com.bugtracker.entity.RoleModel;
import com.bugtracker.entity.UserModel;
import com.bugtracker.entity.RoleModel;
import com.bugtracker.entity.RoleModel;
import com.bugtracker.services.interfaces.RoleServiceLocal;
import com.bugtracker.utils.HibernateUtils;

/**
 * Session Bean implementation class RoleService
 */
@Stateless
public class RoleService implements RoleServiceLocal {

    /**
     * Default constructor. 
     */
    public RoleService() {
        // TODO Auto-generated constructor stub
    }

 
    @PersistenceContext
	 protected EntityManager em;


	@Override
	public List<RoleModel> all() {
		// TODO Auto-generated method stub
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		
		CriteriaQuery<RoleModel> criteriaQuery = criteriaBuilder.createQuery(RoleModel.class);
		Root<RoleModel> from = criteriaQuery.from(RoleModel.class);
		
		criteriaQuery.orderBy(criteriaBuilder.asc(from.get("name")));
		criteriaQuery.select(from);
		
		TypedQuery<RoleModel> query = em.createQuery(criteriaQuery);
		
		return query.getResultList();

	}

	@Override
	public RoleModel getById(Long id) {
		// TODO Auto-generated method stub
		 try {
	            RoleModel instance = em.find(RoleModel.class, id);
	            return instance;
	        } catch (RuntimeException re) {
	            throw re;
	        }
	}

	@Override
	public RoleModel add(RoleModel entity) {
		// TODO Auto-generated method stub
		
		em.persist(entity);
		
		return entity;
	}
	
	@Override
	public RoleModel getByName(String name) {
		if (name==null) {
			return null;
		}
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<RoleModel> criteriaQuery = criteriaBuilder.createQuery(RoleModel.class);
		Root<RoleModel> from = criteriaQuery.from(RoleModel.class);
		
		criteriaQuery.where(
				criteriaBuilder.equal(from.get("name"), name));
		
		criteriaQuery.select(from);
		
		TypedQuery<RoleModel> query = em.createQuery(criteriaQuery);

		
		RoleModel result;
		
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
			result=null;
		}
		
		return result;
	}

	@Override
	public RoleModel update(RoleModel entity) {
		// TODO Auto-generated method stub
		em.merge(entity);

		return entity;
	}

	@Override
	public void delete(RoleModel entity) {
		// TODO Auto-generated method stub

		em.remove(entity);

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		RoleModel entity = getById(id);
		delete(entity);

	}

}

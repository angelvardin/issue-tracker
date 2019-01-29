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

import com.bugtracker.entity.ProjectModel;
import com.bugtracker.entity.ProjectModel;
import com.bugtracker.entity.ProjectModel;
import com.bugtracker.services.interfaces.ProjectServiceLocal;
import com.bugtracker.utils.HibernateUtils;

/**
 * Session Bean implementation class ProjectService
 */
@Stateless
public class ProjectService implements ProjectServiceLocal {

    /**
     * Default constructor. 
     */
    public ProjectService() {
        // TODO Auto-generated constructor stub
    }


    
    @PersistenceContext
	 protected EntityManager em;


	@Override
	public List<ProjectModel> all() {
		// TODO Auto-generated method stub
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		
		CriteriaQuery<ProjectModel> criteriaQuery = criteriaBuilder.createQuery(ProjectModel.class);
		Root<ProjectModel> from = criteriaQuery.from(ProjectModel.class);
		
		criteriaQuery.orderBy(criteriaBuilder.asc(from.get("name")));
		criteriaQuery.select(from);
		
		TypedQuery<ProjectModel> query = em.createQuery(criteriaQuery);
		
		return query.getResultList();

	}

	@Override
	public ProjectModel getById(Long id) {
		// TODO Auto-generated method stub
		 try {
	            ProjectModel instance = em.find(ProjectModel.class, id);
	            return instance;
	        } catch (RuntimeException re) {
	            throw re;
	        }
	}

	@Override
	public ProjectModel add(ProjectModel entity) {
		// TODO Auto-generated method stub
		
		em.persist(entity);
		
		return entity;
	}

	@Override
	public ProjectModel update(ProjectModel entity) {
		// TODO Auto-generated method stub
		em.merge(entity);

		return entity;
	}

	@Override
	public void delete(ProjectModel entity) {
		// TODO Auto-generated method stub

		em.remove(entity);

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		ProjectModel entity = getById(id);
		delete(entity);

	}

}

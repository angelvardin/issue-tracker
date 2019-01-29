package com.bugtracker.db;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.bugtracker.entity.CommentModel;
import com.bugtracker.entity.base.BaseDomainObject;
import com.bugtracker.utils.HibernateUtils;

/**
 * Session Bean implementation class Repository
 */
//@Stateless
public class GenericRepositoryLocal<T extends BaseDomainObject> implements IGenericRepositoryLocal<T> {

    /**
     * Default constructor. 
     */
	
	private  Class<T> genericType;
	
    public GenericRepositoryLocal() {
        // TODO Auto-generated constructor stub
    	
    }
    
    
	@Override
	public void setType(Class<T> type) {
		// TODO Auto-generated method stub
		genericType = type;
		
	}

	@Override
	public List<T> listAll() {
		// TODO Auto-generated method stub
		Session session = HibernateUtils.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(genericType);
		List<T> comments = criteria.list();
		session.close();
		return comments;
	}

	@Override
	public T getById(Long id) {
		// TODO Auto-generated method stub
		Session session = HibernateUtils.getSessionFactory().openSession();
		T result = session.get(genericType, id);
		return result;
	}

	@Override
	public T add(T entity) {
		// TODO Auto-generated method stub
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();

		return entity;
	}

	@Override
	public T update(T entity) {
		// TODO Auto-generated method stub
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.merge(entity);
		tx.commit();
		session.close();

		return entity;
	}

	@Override
	public void delete(T entity) {

		delete(entity.getId());
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Session session = HibernateUtils.getSessionFactory().openSession();

		Transaction tx = session.beginTransaction();
		String className = genericType.getSimpleName();
		Query query = session.createQuery("delete from :ENTITY where id = :ID");
		query.setParameter("ENTITY", className);
		query.setParameter("ID", id);
		int result = query.executeUpdate();

		tx.commit();
		session.close();
		
	}


	@Override
	public Query all() {
		// TODO Auto-generated method stub
		
		String hql = "from "+genericType.getSimpleName();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Query query = session.createQuery(hql);
		session.close();
		return query;


	}





}

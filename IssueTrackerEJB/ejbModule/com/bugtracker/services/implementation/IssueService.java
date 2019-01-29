package com.bugtracker.services.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;



import com.bugtracker.entity.IssueModel;
import com.bugtracker.entity.base.Issuepriority;
import com.bugtracker.entity.base.State;
import com.bugtracker.entity.IssueModel;
import com.bugtracker.entity.IssueModel;
import com.bugtracker.services.interfaces.IssueServiceLocal;
import com.bugtracker.utils.HibernateUtils;
import com.sun.org.apache.regexp.internal.recompile;

/**
 * Session Bean implementation class IssueService
 */
@Stateless
public class IssueService implements IssueServiceLocal {

    /**
     * Default constructor. 
     * 
     * 
     */
	
	private Map<String, Integer> stateMapping;
	private Map<String, Integer> priorityMapping;
    public IssueService() {
        // TODO Auto-generated constructor stub
    	stateMapping = new HashMap<>();
    	stateMapping.put("New", 0);
    	stateMapping.put("open", 1);
    	stateMapping.put("closed", 2);
    	stateMapping.put("fixed", 3);
    	stateMapping.put("reopen", 4);
    	
    	priorityMapping = new HashMap<>();
    	
    	priorityMapping.put("hight",0 );
    	priorityMapping.put("medium",1 );
    	priorityMapping.put("low", 2);
    	priorityMapping.put("critical", 3);
  
   
    	
    }
    
    private void initPriorityMap() {
    	priorityMapping.put("hight",0 );
    	priorityMapping.put("medium",1 );
    	priorityMapping.put("low", 2);
    	priorityMapping.put("critical", 3);
	}


    
    
    @PersistenceContext
	 protected EntityManager em;


	@SuppressWarnings("unused")
	@Override
	public List<IssueModel> all() {
		// TODO Auto-generated method stub
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		
		CriteriaQuery<IssueModel> criteriaQuery = criteriaBuilder.createQuery(IssueModel.class);
		Root<IssueModel> from = criteriaQuery.from(IssueModel.class);
		
		criteriaQuery.orderBy(criteriaBuilder.asc(from.get("date")));
		criteriaQuery.select(from);
		
		 String query = "select model from IssueModel model order by upper(model.date) asc";
	        Query q = em.createQuery(query);
		
		return q.getResultList();

	}

	@Override
	public IssueModel getById(Long id) {
		// TODO Auto-generated method stub
		 try {
	            IssueModel instance = em.find(IssueModel.class, id);
	            return instance;
	        } catch (RuntimeException re) {
	            throw re;
	        }
	}

	@Override
	public IssueModel add(IssueModel entity) {
		// TODO Auto-generated method stub
		em.persist(entity);
		return entity;
	}

	@Override
	public IssueModel update(IssueModel entity) {
		// TODO Auto-generated method stub
		
		em.merge(entity);
		em.flush();
		return entity;
	}

	@Override
	public void delete(IssueModel entity) {
		// TODO Auto-generated method stub

	
		em.persist(entity);
		

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		IssueModel entity = getById(id);
		delete(entity);

	}

	@Override
	public List<IssueModel> getAllRecordsBetween(int startFrom, int pageSize, String orderBy) {
	
		
		String q = "SELECT c FROM IssueModel c ORDER BY :order";
		
		TypedQuery<IssueModel> query = em.createQuery(q,IssueModel.class)
				.setFirstResult(startFrom)
				.setMaxResults(pageSize);
		
		List<IssueModel> result = query.setParameter("order","c."+orderBy).getResultList(); 
		
		return result;
	}

	@Override
	public long Count() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("SELECT count(*) FROM IssueModel");
		long count = (long) query.getSingleResult();
		return count;
	}

	@Override
	public List<IssueModel> getAllRecordsBetween(int startFrom, int pageSize, Map<String, Object> contains, String orderBy,
			String state) {

		StringBuilder where = new StringBuilder();
		boolean whereIsAdded=false;
	
		String and = " AND ";
		boolean notSorted=true;
		if(!contains.keySet().isEmpty()){
			where.append(" WHERE");
			whereIsAdded=true;
		}
		
		if (state!=null) {
			if (!state.equals("all")) {
				
				if (whereIsAdded) {
					
					where.append(" model.state LIKE '%"+stateMapping.get(state)+"%'"+and);
				}else {
					
					where.append(" WHERE model.state LIKE '%"+stateMapping.get(state)+"%'");
				}
			}	
		}
		
		Iterator<Map.Entry<String, Object>> it = contains.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, Object> pair = (Map.Entry<String, Object>)it.next();
	        String key = pair.getKey();
	        if(key.equals("id")){
	        	where.append(" model.id LIKE '%"+pair.getValue()+"%')");
	        	if (it.hasNext()) {
					where.append(and);
				}
	        }//empty value exeption 
	        //int value
	        
	        if(key.equals("projectName")){
	        	where.append(" p.name LIKE '%"+pair.getValue()+"%'");
	        	if (it.hasNext()) {
					where.append(and);
				}
	        }
	        if(key.equals("submittedBy")){
	        	where.append(" u.username LIKE '%"+pair.getValue()+"%'");
	        	if (it.hasNext()) {
					where.append(and);
				}
	        }
	        if(key.equals("priority")){
	        	where.append(" model.issuepriority = "+containsPriority((String) pair.getValue()));
	        	if (it.hasNext()) {
					where.append(and);
				}
	        }
	        if(key.equals("state")){
	        	where.append(" model.state LIKE '%"+pair.getValue()+"%'");
	        	if (it.hasNext()) {
					where.append(and);
				}
	        }
	        if(key.equals("date")){
	        	where.append(" model.date LIKE '%"+pair.getValue()+"%'");
	        	if (it.hasNext()) {
					where.append(and);
				}
	        	
	        }
	        if(key.equals("title")){
	        	where.append(" model.title LIKE '%"+pair.getValue()+"%'");
	        	if (it.hasNext()) {
					where.append(and);
				}
	        	
	        }
	        it.remove(); // avoids a ConcurrentModificationException
	        
	    }
	    
	    StringBuilder sorting=new StringBuilder();
	    if(orderBy==null||orderBy.isEmpty()){
	    	sorting.setLength(0);
	    	sorting.append("model.date");
	    	
	    }
	    
	    if(orderBy.contains("id")){
	    	sorting.setLength(0);
	    	sorting.append("model.id");
	    	
        }
	    if(orderBy.contains("projectName")){
	    	sorting.setLength(0);
	    	sorting.append("p.name");
        	
        	
        }
        if(orderBy.contains("submittedBy")){
        	sorting.setLength(0);
        	sorting.append("u.username");
        	
        }
        if(orderBy.contains("priority")){
        	sorting.setLength(0);
        	sorting.append("model.issuepriority");
        
        }
        if(orderBy.contains("state")){
        	sorting.setLength(0);
        	sorting.append("model.state");
        	
        }
        if(orderBy.contains("date")){
        	sorting.setLength(0);
        	sorting.append("model.date");
        	
        }
        if(orderBy.contains("title")){
        	sorting.setLength(0);
        	sorting.append("model.title");
        
        }
	
		
		String q = "SELECT model FROM IssueModel model JOIN model.project p JOIN model.submittedBy u " + where.toString() + " ORDER BY " + sorting.toString();
		
		TypedQuery<IssueModel> query = em.createQuery(q,IssueModel.class)
				.setFirstResult(startFrom)
				.setMaxResults(pageSize);
		
		List<IssueModel> result = query.getResultList(); 
		
		return result;
	}
	
	private String containsPriority(String str) {
		
		Iterator<Map.Entry<String, Integer>> it = priorityMapping.entrySet().iterator();
		while (it.hasNext()) {
	        Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)it.next();
	        String key = pair.getKey();
	        if(key.startsWith(str)){
	        	return pair.getValue().toString();
	        }
	        
	        it.remove();
		}
		initPriorityMap();
		return ""+(-1);
	}


	

}

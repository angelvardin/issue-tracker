package com.bugtracker.db;

import java.util.List;

import javax.ejb.Local;

import org.hibernate.Query;

//@Local
public interface IGenericRepositoryLocal<T> {

	void setType(Class<T> type);
	
    List<T> listAll();
    
    Query all();
	
	T getById(Long id);
	
	T add(T entity);
	
	T update(T entity);
	
	void delete(T entity);
	
	void delete(Long id);
	
}

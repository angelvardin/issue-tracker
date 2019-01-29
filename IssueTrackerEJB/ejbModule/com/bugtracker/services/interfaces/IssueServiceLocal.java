package com.bugtracker.services.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.bugtracker.entity.*;


@Local
public interface IssueServiceLocal {

    List<IssueModel> all();
	
	IssueModel getById(Long id);
	
	IssueModel add(IssueModel entity);
	
	IssueModel update(IssueModel entity);
	
	void delete(IssueModel entity);
	
	void delete(Long id);

	List<IssueModel> getAllRecordsBetween(int startFrom, int pageSize, String orderBy);

	long Count();

	

	public List<IssueModel> getAllRecordsBetween(int startFrom, int pageSize, Map<String, Object> contains, String orderBy,
			String status);
}

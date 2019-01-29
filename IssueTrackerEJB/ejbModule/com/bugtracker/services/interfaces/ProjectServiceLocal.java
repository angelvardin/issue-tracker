package com.bugtracker.services.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.bugtracker.entity.CommentModel;
import com.bugtracker.entity.ProjectModel;

@Local
public interface ProjectServiceLocal {
	
    List<ProjectModel> all();
	
	ProjectModel getById(Long id);
	
	ProjectModel add(ProjectModel entity);
	
	ProjectModel update(ProjectModel entity);
	
	void delete(ProjectModel entity);
	
	void delete(Long id);

}

package com.bugtracker.services.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.bugtracker.entity.CommentModel;


@Local
public interface CommentServiceLocal {


	List<CommentModel> all();
	
	CommentModel getById(Long id);
	
	CommentModel add(CommentModel entity);
	
	CommentModel update(CommentModel entity);
	
	void delete(CommentModel entity);
	
	void delete(Long id);
	
}

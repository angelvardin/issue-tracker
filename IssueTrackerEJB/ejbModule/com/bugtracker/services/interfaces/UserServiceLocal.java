package com.bugtracker.services.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.bugtracker.entity.UserModel;

@Local
public interface UserServiceLocal {

    List<UserModel> all();
	
	UserModel getById(Long id);
	
	UserModel add(UserModel entity);
	
	UserModel update(UserModel entity);
	
	void delete(UserModel entity);
	
	void delete(Long id);

	UserModel loginUser(String username, String password);

	UserModel checkUserExists(String username, Long id);

	
}

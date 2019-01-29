package com.bugtracker.services.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.bugtracker.entity.RoleModel;

@Local
public interface RoleServiceLocal {

    List<RoleModel> all();
	
	RoleModel getById(Long id);
	
	RoleModel add(RoleModel entity);
	
	RoleModel update(RoleModel entity);
	
	void delete(RoleModel entity);
	
	void delete(Long id);

	RoleModel getByName(String name);
}

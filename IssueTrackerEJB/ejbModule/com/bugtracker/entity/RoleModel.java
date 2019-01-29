package com.bugtracker.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.bugtracker.entity.base.BaseDomainObject;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the role database table.
 * 
 */

@Entity
@Table(name="ROLE")
//@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class RoleModel extends BaseDomainObject  {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private List<UserModel> users;
	
	


	public RoleModel() {
		super();
		this.users = new ArrayList<>();
	}

	public RoleModel(int id, String name, List<UserModel> users) {
		super();
		this.id = id;
		this.name = name;
		this.users = users;
	}

	@Column(name="name",length=19, nullable=false )
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	//bi-directional many-to-one association to User
	@OneToMany(cascade=CascadeType.ALL,mappedBy="role",fetch = FetchType.LAZY)
	public List<UserModel> getUsers() {
		return this.users;
	}

	public void setUsers(List<UserModel> users) {
		this.users = users;
	}

	public UserModel addUser(UserModel user) {
		getUsers().add(user);
		user.setRole(this);

		return user;
	}

	public UserModel removeUser(UserModel user) {
		getUsers().remove(user);
		user.setRole(null);

		return user;
	}

}
package com.bugtracker.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.bugtracker.entity.base.BaseDomainObject;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="USER")
public class UserModel extends BaseDomainObject {
	private static final long serialVersionUID = 1L;

	private String email;
	private String username;
	private String firstname;
	private String lastname;
	private String password;
	private List<CommentModel> comments;
	private List<IssueModel> issues;
	private RoleModel role;
	
	

	

	public UserModel() {
		super();
		role = new RoleModel();
		comments=new ArrayList<>();
		issues = new ArrayList<>();
	}
	
	

	public UserModel(String email, String firstname, String lastname, String password, List<CommentModel> comments,
			List<IssueModel> issues, RoleModel role) {
		super();
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.comments = comments;
		this.issues = issues;
		this.role = role;
	}

	@Column(name="username",length=19, nullable=false )
	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	@Column(name="email",length=19, nullable=false )
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="firstname",length=19, nullable=false )
	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Column(name="lastname",length=19, nullable=false )
	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Column(name="password",length=19, nullable=false )
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	//bi-directional many-to-one association to Comment
	@OneToMany(cascade=CascadeType.ALL,mappedBy="author",fetch = FetchType.LAZY)
	public List<CommentModel> getComments() {
		return this.comments;
	}

	public void setComments(List<CommentModel> comments) {
		this.comments = comments;
	}

	public CommentModel addComment(CommentModel comment) {
		getComments().add(comment);
		comment.setAuthor(this);;

		return comment;
	}

	public CommentModel removeComment(CommentModel comment) {
		getComments().remove(comment);
		comment.setAuthor(null);

		return comment;
	}


	//bi-directional many-to-one association to Issue
	@OneToMany(cascade=CascadeType.ALL,mappedBy="submittedBy",fetch = FetchType.LAZY)
	public List<IssueModel> getIssues() {
		return this.issues;
	}

	public void setIssues(List<IssueModel> issues) {
		this.issues = issues;
	}

	public IssueModel addIssue(IssueModel issue) {
		getIssues().add(issue);
		issue.setSubmittedBy(this);

		return issue;
	}

	public IssueModel removeIssue(IssueModel issue) {
		getIssues().remove(issue);
		issue.setSubmittedBy(null);

		return issue;
	}


	//bi-directional many-to-one association to Role
	@JoinColumn(name="role_id",referencedColumnName="id")
	@ManyToOne
	public RoleModel getRole() {
		return this.role;
	}

	public void setRole(RoleModel role) {
		this.role = role;
	}

}
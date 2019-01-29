package com.bugtracker.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.bugtracker.entity.base.BaseDomainObject;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the project database table.
 * 
 */
@Entity
@Table(name="PROJECT")
public class ProjectModel extends BaseDomainObject {
	private static final long serialVersionUID = 1L;

	private String description;
	private String name;
	private List<IssueModel> issues;

	



	public ProjectModel() {
		super();
		issues = new ArrayList<>();
		
	}
	
	

	public ProjectModel(String description, String name, List<IssueModel> issues) {
		super();
		this.description = description;
		this.name = name;
		this.issues = issues;
	}



	@Column(name="description",length=19, nullable=false )
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="name",length=19, nullable=false )
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	
	//bi-directional many-to-one association to Issue
	@OneToMany(cascade=CascadeType.ALL,mappedBy="project",fetch = FetchType.LAZY)
	public List<IssueModel> getIssues() {
		return this.issues;
	}

	public void setIssues(List<IssueModel> issues) {
		this.issues = issues;
	}

	public IssueModel addIssue(IssueModel issue) {
		getIssues().add(issue);
		issue.setProject(this);

		return issue;
	}

	public IssueModel removeIssue(IssueModel issue) {
		getIssues().remove(issue);
		issue.setProject(null);

		return issue;
	}

}
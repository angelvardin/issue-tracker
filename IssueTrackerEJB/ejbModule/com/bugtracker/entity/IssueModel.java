package com.bugtracker.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bugtracker.entity.base.BaseDomainObject;
import com.bugtracker.entity.base.Issuepriority;
import com.bugtracker.entity.base.State;


@Entity
@Table(name="ISSUE")
public class IssueModel extends BaseDomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String description;
	private Issuepriority issuepriority;
	private State state;
	private ProjectModel project;
	private UserModel submittedBy;
	private List<CommentModel> comment;
	private Date date;
	private String title;
	


	public IssueModel() {
		super();
		this.comment = new ArrayList<>();
		this.submittedBy = new UserModel();
		this.project = new ProjectModel();
	}


	public IssueModel(String description, Issuepriority priority, State status, ProjectModel project, UserModel user, List<CommentModel> comment,
			Date date,String title) {
		super();
		this.description = description;
		this.issuepriority = priority;
		this.state = status;
		this.project = project;
		this.submittedBy = user;
		this.comment = comment;
		this.date = date;
		this.title=title;
	}


	@Column(name="description",length=19, nullable=false )
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	@Column(name="issuepriority", nullable=false)
	public Issuepriority getIssuepriority() {
		return issuepriority;
	}


	public void setIssuepriority(Issuepriority priority) {
		this.issuepriority = priority;
	}


	@Column(name="state", nullable=false)
	public State getState() {
		return state;
	}


	public void setState(State status) {
		this.state = status;
	}


	@JoinColumn(name="project_id",referencedColumnName="id")
	@ManyToOne
	public ProjectModel getProject() {
		return project;
	}


	
	public void setProject(ProjectModel project) {
		this.project = project;
	}

	@JoinColumn(name="user_id",referencedColumnName="id")
	@ManyToOne
	public UserModel getSubmittedBy() {
		return submittedBy;
	}


	public void setSubmittedBy(UserModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	@OneToMany(cascade=CascadeType.ALL,mappedBy="issue",fetch = FetchType.LAZY )
	public List<CommentModel> getComment() {
		return comment;
	}


	
	public void setComment(List<CommentModel> comment) {
		this.comment = comment;
	}

	@Column(name="date",length=19, nullable=false )
	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	/**
	 * @return the title
	 */
	@Column(name="title", nullable=false)
	public String getTitle() {
		return title;
	}


	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	
	

	
	
}

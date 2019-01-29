package com.bugtracker.web.beans.viewmodels;

import java.util.Date;

import com.bugtracker.entity.base.Issuepriority;
import com.bugtracker.entity.base.State;

public class IssueViewModel {
	
	private String projectName;
	private String submittedBy;
	private Issuepriority priority;
	private State state;
	private Date date;
	private String id;
	private String title;
	private String description;
	public IssueViewModel(String projectName, String submittedBy, Issuepriority priority, State status, Date date,String id,String title,
			String description) {
		super();
		this.projectName = projectName;
		this.submittedBy = submittedBy;
		this.priority = priority;
		this.state = status;
		this.date = date;
		this.id = id;
		this.title = title;
		this.description = description;
	}
	public IssueViewModel() {
		super();
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getSubmittedBy() {
		return submittedBy;
	}
	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}
	public Issuepriority getPriority() {
		return priority;
	}
	public void setPriority(Issuepriority priority) {
		this.priority = priority;
	}
	public State getState() {
		return state;
	}
	public void setState(State status) {
		this.state = status;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	

}

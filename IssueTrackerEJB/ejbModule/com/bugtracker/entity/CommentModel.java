package com.bugtracker.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bugtracker.entity.base.BaseDomainObject;

@Entity
@Table(name="COMMENT")
public class CommentModel extends BaseDomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String text;
	private String authorName;
	private Date date;
	private UserModel author;
	private IssueModel issue;
	public CommentModel() {
		super();
		this.author = new UserModel();
		this.issue = new IssueModel();
	}
	public CommentModel(String text, String username, UserModel author, IssueModel issueModel,Date date) {
		super();
		this.text = text;
		this.authorName = username;
		this.author = author;
		this.issue=issueModel;
		this.date = date;
	}
	@Column(name="text",length=19, nullable=false )
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Column(name="username",length=19, nullable=true )
	public String getUsername() {
		return authorName;
	}
	public void setUsername(String username) {
		this.authorName = username;
	}
	@Column(name="date",length=19, nullable=false )
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@JoinColumn(name="user_id",referencedColumnName="id",nullable=true)
	@ManyToOne
	public UserModel getAuthor() {
		return author;
	}
	public void setAuthor(UserModel author) {
		this.author = author;
	}
	@JoinColumn(name="issue_id",referencedColumnName="id",nullable=true)
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	public IssueModel getIssue() {
		return issue;
	}
	public void setIssue(IssueModel issue) {
		this.issue = issue;
	}
	
	
	

}

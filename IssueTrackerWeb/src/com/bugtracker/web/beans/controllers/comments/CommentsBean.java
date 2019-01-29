package com.bugtracker.web.beans.controllers.comments;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.bugtracker.entity.CommentModel;
import com.bugtracker.entity.IssueModel;
import com.bugtracker.services.interfaces.CommentServiceLocal;
import com.bugtracker.services.interfaces.IssueServiceLocal;

@ManagedBean(name = "commentsBean")
@ViewScoped
public class CommentsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private HttpServletRequest request;

	@EJB
	private IssueServiceLocal issueService;

	@EJB
	private CommentServiceLocal commentService;
	
    public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	private List<CommentModel> allComments;
	
	private String addCommentDescription;
	

	private String commentCreator;
	
	@PostConstruct
	private void init() {
		allComments = new ArrayList<>();

	}
	
/*	public List<CommentModel> getAllComments(String issueId) {
		allComments=new ArrayList<>();
		IssueModel selectedIssue = issueService.getById(Long.parseLong(issueId));
		allComments.addAll(selectedIssue.getComment());
		return allComments;
	}*/
	
	public void load(String issueId) {
		allComments=new ArrayList<>();
		IssueModel selectedIssue = issueService.getById(Long.parseLong(issueId));
		allComments.addAll(selectedIssue.getComment());
	}
	
	
	public void createComment(String issueId) {
		
		IssueModel selectedIssue = issueService.getById(Long.parseLong(issueId));		
		CommentModel m= new CommentModel(addCommentDescription, commentCreator, null, selectedIssue,new Date());
		commentService.add(m);
		allComments=new ArrayList<>();
		
		allComments.addAll(selectedIssue.getComment());
	}
	
	public IssueServiceLocal getIssueService() {
		return issueService;
	}

	public void setIssueService(IssueServiceLocal issueService) {
		this.issueService = issueService;
	}

	public CommentServiceLocal getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentServiceLocal commentService) {
		this.commentService = commentService;
	}

	public List<CommentModel> getAllComments() {
		return allComments;
	}

	public void setAllComments(List<CommentModel> allComments) {
		this.allComments = allComments;
	}

	public String getAddCommentDescription() {
		return addCommentDescription;
	}

	public void setAddCommentDescription(String addCommentDescription) {
		this.addCommentDescription = addCommentDescription;
	}

	public String getCommentCreator() {
		return commentCreator;
	}

	public void setCommentCreator(String commentCreator) {
		this.commentCreator = commentCreator;
	}

	

	
}

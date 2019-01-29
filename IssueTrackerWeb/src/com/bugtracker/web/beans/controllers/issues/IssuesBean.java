package com.bugtracker.web.beans.controllers.issues;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import com.bugtracker.entity.CommentModel;
import com.bugtracker.entity.IssueModel;
import com.bugtracker.entity.base.Issuepriority;
import com.bugtracker.entity.base.State;
import com.bugtracker.services.interfaces.CommentServiceLocal;
import com.bugtracker.services.interfaces.IssueServiceLocal;
import com.bugtracker.web.beans.primefaces.datamodels.LazyIssuesDataModel;
import com.bugtracker.web.beans.viewmodels.IssueViewModel;

@ManagedBean(name = "issuesBean")
@SessionScoped
public class IssuesBean implements Serializable {

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
	
	private IssueViewModel seletedIssue;

	private IssueViewModel editIssue;
	
	private List<CommentModel> allComments;
	
	private String addCommentDescription;
	
	private String commentCreator;
	
    private Issuepriority[] priorities;
	
	private State[] statuses;

	private LazyDataModel<IssueViewModel> lazyModel;

	@PostConstruct
	private void init() {
		lazyModel = new LazyIssuesDataModel(issueService);
	}
	
	public void createComment() {
			
		IssueModel selectedIssue = issueService.getById(Long.parseLong(editIssue.getId()));		
		CommentModel m= new CommentModel(addCommentDescription, commentCreator, null, selectedIssue,new Date());
		commentService.add(m);
		allComments=new ArrayList<>();
		
		allComments.addAll(selectedIssue.getComment());
	}
	
	public void setComment(CommentModel coment){
		allComments.add(coment);
	}
	
	public State[] getStatuses() {
		State[] result = State.values();
		for (State status : result) {
			status.setValue(status.name());
		}
        return result;
    }
	public Issuepriority[] getPriorities() {
		Issuepriority[] result = Issuepriority.values();
		for (Issuepriority priority : result) {
			priority.setValue(priority.name());
		}
        return result;
    }
	

	public String details(IssueViewModel model) {

		return "";
	}

	public String edit(IssueViewModel model) {
		
		editIssue=model;
		allComments=new ArrayList<>();
		IssueModel selectedIssue = issueService.getById(Long.parseLong(editIssue.getId()));
		allComments.addAll(selectedIssue.getComment());
		return "/page/editissues?faces-redirect=true";
	}
	
	public String update() {
		return "";
	}
	
	
	

	public IssueViewModel getEditIssue() {
		return editIssue;
	}

	public void setEditIssue(IssueViewModel editIssue) {
		this.editIssue = editIssue;
	}

	public IssueServiceLocal getIssueService() {
		return issueService;
	}

	public void setIssueService(IssueServiceLocal issueService) {
		this.issueService = issueService;
	}


	public LazyDataModel<IssueViewModel> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<IssueViewModel> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("Issues Selected", ((IssueViewModel) event.getObject()).getId());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public IssueViewModel getSeletedIssue() {
		return seletedIssue;
	}

	public void setSeletedIssue(IssueViewModel seletedIssue) {
		this.seletedIssue = seletedIssue;
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

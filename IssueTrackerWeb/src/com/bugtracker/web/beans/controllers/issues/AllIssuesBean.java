package com.bugtracker.web.beans.controllers.issues;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import com.bugtracker.entity.CommentModel;
import com.bugtracker.entity.UserModel;
import com.bugtracker.services.interfaces.IssueServiceLocal;
import com.bugtracker.web.beans.primefaces.datamodels.LazyIssuesDataModel;
import com.bugtracker.web.beans.viewmodels.IssueViewModel;

@ManagedBean(name = "allIssuesBean")
@SessionScoped
public class AllIssuesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private HttpServletRequest request;

	@EJB
	private IssueServiceLocal issueService;
	
	private IssueViewModel seletedIssue;
	
    
    private IssueViewModel issueDetails;
	
	private List<CommentModel> allComments;
	
	private String status;
	
	private LazyDataModel<IssueViewModel> lazyModel;
	
	private boolean logon;
	
	@PostConstruct
	private void init() {
		lazyModel = new LazyIssuesDataModel(issueService);
	}
	
	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("Issues Selected", ((IssueViewModel) event.getObject()).getId());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	

	public IssueServiceLocal getIssueService() {
		return issueService;
	}

	public void setIssueService(IssueServiceLocal issueService) {
		this.issueService = issueService;
	}

	public IssueViewModel getIssueDetails() {
		return issueDetails;
	}

	public void setIssueDetails(IssueViewModel issueDetails) {
		this.issueDetails = issueDetails;
	}

	public List<CommentModel> getAllComments() {
		return allComments;
	}

	public void setAllComments(List<CommentModel> allComments) {
		this.allComments = allComments;
	}

	public LazyDataModel<IssueViewModel> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<IssueViewModel> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public IssueViewModel getSeletedIssue() {
		return seletedIssue;
	}

	public void setSeletedIssue(IssueViewModel seletedIssue) {
		this.seletedIssue = seletedIssue;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		lazyModel = new LazyIssuesDataModel(issueService,status);
		this.status = status;
	}

	/**
	 * @return the logon
	 */
	public boolean getLogon() {
	
		HttpSession session = request.getSession();
		UserModel loggedUser = (UserModel) session.getAttribute("LOGGED_USER");
		if(loggedUser==null){
			return false;
		}
		
		return true;
		
	}
     
	public boolean logged() {
		HttpSession session = request.getSession();
		UserModel loggedUser = (UserModel) session.getAttribute("LOGGED_USER");
		if(loggedUser==null){
			return false;
		}
		
		return true;
	}
	/**
	 * @param logon the logon to set
	 */
	public void setLogon(boolean logon) {
		this.logon = logon;
	}

}

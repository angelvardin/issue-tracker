package com.bugtracker.web.beans.controllers.issues;

import java.io.Serializable;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.bugtracker.entity.IssueModel;
import com.bugtracker.entity.base.Issuepriority;
import com.bugtracker.entity.base.State;
import com.bugtracker.services.interfaces.IssueServiceLocal;
import com.bugtracker.web.beans.viewmodels.IssueViewModel;

@ManagedBean(name = "detailsIssuesBean")
@SessionScoped
public class DetailsIssieBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private HttpServletRequest request;

	@EJB
	private IssueServiceLocal issueService;

	private Issuepriority[] priorities;

	private State[] statuses;
	
	private IssueViewModel selectedIssue;

	@PostConstruct
	private void init() {

	}
	
   public String details(IssueViewModel model) {
	   selectedIssue = model;
		//IssueModel selectedIssue = issueService.getById(Long.parseLong(editIssue.getId()));
		return "/page/issuedetails?faces-redirect=true";
	}

	
   public boolean hasErrors() {
		Iterator<FacesMessage> messages = FacesContext.getCurrentInstance().getMessages();
		for (; messages.hasNext();) {
			FacesMessage message = messages.next();
			if (message.getSeverity().compareTo(FacesMessage.SEVERITY_ERROR) == 0) {
				return true;
			}
		}

		return false;
	}
	

	public IssueServiceLocal getIssueService() {
		return issueService;
	}

	public void setIssueService(IssueServiceLocal issueService) {
		this.issueService = issueService;
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

	/**
	 * @return the selectedIssue
	 */
	public IssueViewModel getSelectedIssue() {
		return selectedIssue;
	}

	/**
	 * @param selectedIssue the selectedIssue to set
	 */
	public void setSelectedIssue(IssueViewModel selectedIssue) {
		this.selectedIssue = selectedIssue;
	}
	

}

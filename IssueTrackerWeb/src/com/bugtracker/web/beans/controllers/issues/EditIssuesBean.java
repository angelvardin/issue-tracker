package com.bugtracker.web.beans.controllers.issues;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bugtracker.entity.IssueModel;
import com.bugtracker.entity.ProjectModel;
import com.bugtracker.entity.UserModel;
import com.bugtracker.entity.base.Issuepriority;
import com.bugtracker.entity.base.State;
import com.bugtracker.services.interfaces.IssueServiceLocal;
import com.bugtracker.services.interfaces.ProjectServiceLocal;
import com.bugtracker.web.beans.primefaces.datamodels.LazyIssuesDataModel;
import com.bugtracker.web.beans.viewmodels.IssueViewModel;
import com.bugtracker.web.utils.MessageUtils;

@ManagedBean(name = "editIssuesBean")
@SessionScoped
public class EditIssuesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EditIssuesBean() {
		// TODO Auto-generated constructor stub
	}

	@Inject
	private HttpServletRequest request;

	@EJB
	private IssueServiceLocal issueService;

	private Issuepriority[] priorities;

	private State[] statuses;
	
	private IssueViewModel editIssue;
	
	private List<SelectItem> allProjectNames;
	
	@EJB
	private ProjectServiceLocal projectService;


	@PostConstruct
	private void init() {

	}

	public String edit(IssueViewModel model) {

		editIssue = model;
		//IssueModel selectedIssue = issueService.getById(Long.parseLong(editIssue.getId()));
		return "/page/editissues?faces-redirect=true";
	}
	
	private boolean notValid() {
		boolean errors = false;
		if (empty(editIssue.getTitle())) {
			MessageUtils.addErrorMessage("error.required.title");
			errors = true;
		}
		if (empty(editIssue.getDescription())) {
			MessageUtils.addErrorMessage("error.required.description");
			errors = true;
		}
		if (editIssue.getDate() == null) {
			MessageUtils.addErrorMessage("error.required.date");
			errors = true;
		}
		if (empty(editIssue.getPriority().getValue())) {
			MessageUtils.addErrorMessage("error.required.priority");
			errors = true;
		}
		if (empty(editIssue.getState().getValue())) {
			MessageUtils.addErrorMessage("error.required.state");
			errors = true;
		}
		return errors;
	}
	private boolean empty(String s) {
		// Null-safe, short-circuit evaluation.
		return s == null || s.trim().isEmpty();
	}
	
	public String update() {
		if (notValid()) {
			return null;
		}
		UserModel user = getCurrentUser();
		if (user == null) {
			return null;
		}

		ProjectModel project = getProjectByName(editIssue.getProjectName());
		if (project == null) {
			return null;
		}
		
		IssueModel updated = new IssueModel(editIssue.getDescription(), editIssue.getPriority(), editIssue.getState(),
				project, user, null, editIssue.getDate(), editIssue.getTitle());
		updated.setId(new Long(editIssue.getId()));

		issueService.update(updated);

		return "/page/allissues?faces-redirect=true";
	}
	private UserModel getCurrentUser() {
		HttpSession session = request.getSession();
		UserModel loggedUser = (UserModel) session.getAttribute("LOGGED_USER");
		if (loggedUser == null) {
			return null;
		}

		return loggedUser;
	}
	
	public ProjectModel getProjectByName(String name) {
		List<ProjectModel> all = getAllProjects();
		for (ProjectModel projectModel : all) {
			if (projectModel.getName().equals(name)) {
				return projectModel;
			}
		}
		return null;
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

	public IssueViewModel getEditIssue() {
		return editIssue;
	}

	public void setEditIssue(IssueViewModel editIssue) {
		this.editIssue = editIssue;
	}
	
	public List<ProjectModel> getAllProjects() {
		List<ProjectModel> projects = projectService.all();
		return projects;
		
	}

	/**
	 * @return the allProjectNames
	 */
     public List<SelectItem> getAllProjectNames() {
		
		//projectNames.toArray(this.allProjectNames);
		List<ProjectModel> projects = getAllProjects();
		List<SelectItem> projectNames = new ArrayList<>();
		for (ProjectModel projectModel : projects) {
			projectNames.add(new SelectItem(projectModel.getName(),projectModel.getName()));
		}
		allProjectNames = projectNames;
		return allProjectNames;
	}

	/**
	 * @param allProjectNames the allProjectNames to set
	 */
	public void setAllProjectNames(List<SelectItem> allProjectNames) {
		this.allProjectNames = allProjectNames;
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

}

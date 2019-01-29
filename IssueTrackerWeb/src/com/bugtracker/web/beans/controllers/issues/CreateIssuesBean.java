package com.bugtracker.web.beans.controllers.issues;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.bugtracker.web.beans.viewmodels.IssueViewModel;
import com.bugtracker.web.utils.GeneralUtils;
import com.bugtracker.web.utils.MessageUtils;

import javafx.scene.layout.Priority;

@ManagedBean(name = "createIssuesBean")
@ViewScoped
public class CreateIssuesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CreateIssuesBean() {
		// TODO Auto-generated constructor stub
		
	}

	@Inject
	private HttpServletRequest request;

	@EJB
	private IssueServiceLocal issueService;

	@EJB
	private ProjectServiceLocal projectService;

	private Issuepriority[] priorities;

	private State[] statuses;

	//private ProjectModel[] allProjects;
	
	private List<SelectItem> allProjectNames;
	

	private IssueViewModel createIssue;

	private UserModel currentUser;
	
	private SelectItem selectedProject;
	
	private List<SelectItem> availableItems;
	
	private Map<String, Integer> convertState;
	
	private Map<String, Integer> convertPriority;

	@PostConstruct
	public void init() {
		createIssue = new IssueViewModel();	
	}

	public String edit(IssueViewModel model) {

		createIssue = model;
		// IssueModel selectedIssue =
		// issueService.getById(Long.parseLong(editIssue.getId()));
		return "/page/createissues?faces-redirect=true";
	}

	public String update() {
		if (notValide()) {
			return null;
		}
		UserModel user = getCurrentUser();
		if (user == null) {
			return null;
		}

		ProjectModel project = getProjectByName(createIssue.getProjectName());
		if (project == null) {
			return null;
		}

		issueService.add(new IssueModel(createIssue.getDescription(), createIssue.getPriority(), createIssue.getState(),
				project, user, null, createIssue.getDate(), createIssue.getTitle()));

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

	private boolean notValide() {
		boolean errors = false;
		if (empty(createIssue.getTitle())) {
			MessageUtils.addErrorMessage("error.required.title");
			errors = true;
		}
		if (empty(createIssue.getDescription())) {
			MessageUtils.addErrorMessage("error.required.description");
			errors = true;
		}
		if (createIssue.getDate() == null) {
			MessageUtils.addErrorMessage("error.required.date");
			errors = true;
		}
		if (empty(createIssue.getPriority().getValue())) {
			MessageUtils.addErrorMessage("error.required.priority");
			errors = true;
		}
		if (empty(createIssue.getState().getValue())) {
			MessageUtils.addErrorMessage("error.required.state");
			errors = true;
		}
		return errors;
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

	private boolean empty(String s) {
		// Null-safe, short-circuit evaluation.
		return s == null || s.trim().isEmpty();
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

	public List<ProjectModel> getAllProjects() {
		List<ProjectModel> projects = projectService.all();
		return projects;
		
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

	/**
	 * @return the createIssue
	 */
	public IssueViewModel getCreateIssue() {
		return createIssue;
	}

	/**
	 * @param createIssue
	 *            the createIssue to set
	 */
	public void setCreateIssue(IssueViewModel createIssue) {
		this.createIssue = createIssue;
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

	/**
	 * @return the selectedItem
	 */
	

	/**
	 * @return the availableItems
	 */
	public List<SelectItem> getAvailableItems() {
		return availableItems;
	}

	/**
	 * @param availableItems the availableItems to set
	 */
	public void setAvailableItems(List<SelectItem> availableItems) {
		this.availableItems = availableItems;
	}

	/**
	 * @return the selectedProject
	 */
	public SelectItem getSelectedProject() {
		return selectedProject;
	}

	/**
	 * @param selectedProject the selectedProject to set
	 */
	public void setSelectedProject(SelectItem selectedProject) {
		this.selectedProject = selectedProject;
	}

}

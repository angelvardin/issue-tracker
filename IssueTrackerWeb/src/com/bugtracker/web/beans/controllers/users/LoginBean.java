package com.bugtracker.web.beans.controllers.users;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bugtracker.entity.UserModel;
import com.bugtracker.services.interfaces.UserServiceLocal;
import com.bugtracker.web.utils.GeneralUtils;
import com.bugtracker.web.utils.MessageUtils;



@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	
	private static final String SUCCESS_LOGIN_REDIRECT = "/page/allissues?faces-redirect=true";
	private static final String LOGIN_PAGE_REDIRECT = "/page/allissues?faces-redirect=true";
	
	
	@Inject
	private HttpServletRequest request;
	
	@EJB
	private UserServiceLocal userService;
	
	private String username;
	private String password;
	
	private UserModel currentUser;
	
	private boolean logged;
	
	

	@PostConstruct
	public void init() {
		// TODO
		currentUser=null;
	}
	
	public String login() {
		
		String encryptedPass = GeneralUtils.encodeSha256Password(password);
		
		UserModel user = userService.loginUser(username, encryptedPass);
		

		if (null == user) {
			MessageUtils.addErrorMessage("login.error.invalid.credentials");

			return "";
		} else {
			request.getSession().setAttribute("LOGGED_USER", user);
			currentUser=user;
			return SUCCESS_LOGIN_REDIRECT;
		}
	}

	/**
	 * Implement application logout logic
	 * 
	 * @return
	 */
	public String logout() {
		request.getSession().invalidate();
		return LOGIN_PAGE_REDIRECT;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserServiceLocal getUserService() {
		return userService;
	}

	public void setUserService(UserServiceLocal usersBean) {
		this.userService = usersBean;
	}

	/**
	 * @return the currentUser
	 */
	public UserModel getCurrentUser() {
		return currentUser;
	}

	/**
	 * @param currentUser the currentUser to set
	 */
	public void setCurrentUser(UserModel currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * @return the logged
	 */
	public boolean isLogged() {
		HttpSession session = request.getSession();
		UserModel loggedUser = (UserModel) session.getAttribute("LOGGED_USER");
		if(loggedUser==null){
			return false;
		}
		
		return true;
	}

	/**
	 * @param logged the logged to set
	 */
	public void setLogged(boolean logged) {
		this.logged = logged;
	}
	
}

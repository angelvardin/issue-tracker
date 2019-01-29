package com.bugtracker.web.beans.controllers.users;

import java.util.Iterator;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bugtracker.entity.RoleModel;
import com.bugtracker.entity.UserModel;
import com.bugtracker.services.interfaces.UserServiceLocal;
import com.bugtracker.web.utils.GeneralUtils;
import com.bugtracker.web.utils.MessageUtils;

@ManagedBean(name = "editUserBean")
@ViewScoped
public class EditUserBean {

	@Inject
	HttpServletRequest request;

	private UserModel user;

	@EJB
	UserServiceLocal userService;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\."
			+ "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" + "(\\.[A-Za-z]{2,})$";
	private Pattern pattern;

	public EditUserBean() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void init() {
		setUser(getCurrentUser());
	}

	public String edit() {

		if (!isValid()) {
			return null;
		}

		String cryptedPassword = GeneralUtils.encodeSha256Password(user.getPassword());
		user.setPassword(cryptedPassword);

		userService.update(user);

		//String encryptedPass = GeneralUtils.encodeSha256Password(user.getPassword());

		return "/page/allissues?faces-redirect=true";

	}

	public UserModel getCurrentUser() {
		HttpSession session = request.getSession();
		UserModel loggedUser = (UserModel) session.getAttribute("LOGGED_USER");
		return loggedUser;
	}

	protected boolean isValid() {
		// TODO Auto-generated method stub
		boolean errors = false;
		if (!empty(user.getUsername())) {
			MessageUtils.addErrorMessage("error.required.username");
			errors = true;
		}
		if (!empty(user.getFirstname())) {
			MessageUtils.addErrorMessage("error.required.firstname");
			errors = true;
		}
		if (!empty(user.getLastname())) {
			MessageUtils.addErrorMessage("error.required.lastname");
			errors = true;
		}
		if (!empty(user.getPassword())) {
			MessageUtils.addErrorMessage("error.required.password");
			errors = true;
		}
		if (!empty(user.getEmail())) {
			MessageUtils.addErrorMessage("error.required.password");
			errors = true;
		}
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);

		if (!pattern.matcher(user.getEmail()).matches()) {
			MessageUtils.addErrorMessage("error.invalid.email");
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

	/**
	 * @return the user
	 */
	public UserModel getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(UserModel user) {
		this.user = user;
	}

	/**
	 * @return the pattern
	 */
	public Pattern getPattern() {
		return pattern;
	}

	/**
	 * @param pattern
	 *            the pattern to set
	 */
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	/**
	 * @return the emailPattern
	 */
	public static String getEmailPattern() {
		return EMAIL_PATTERN;
	}

}

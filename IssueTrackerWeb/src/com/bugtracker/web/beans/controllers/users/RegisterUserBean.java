package com.bugtracker.web.beans.controllers.users;

import java.util.Iterator;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.internal.xml.ValidatedByType;

import com.bugtracker.entity.RoleModel;
import com.bugtracker.entity.UserModel;
import com.bugtracker.services.interfaces.RoleServiceLocal;
import com.bugtracker.services.interfaces.UserServiceLocal;
import com.bugtracker.web.utils.GeneralUtils;
import com.bugtracker.web.utils.MessageUtils;


@ManagedBean(name = "registerUserBean")
@ViewScoped
public class RegisterUserBean {

	

    @Inject
    HttpServletRequest request;
    
    @EJB
    UserServiceLocal userService;
    
    @EJB
    RoleServiceLocal roleService;
    
    
    
    private UserModel user;
    
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\."
            + "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" + "(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
	
	public RegisterUserBean() {
		// TODO Auto-generated constructor stub
		
	}
	
	@PostConstruct
	public void init() {
		user = new UserModel();
	}
	
	public String register(){
		
		if(!isValid()){
			return null;
		}
		
		 String cryptedPassword = GeneralUtils.encodeSha256Password(user.getPassword());
	     user.setPassword(cryptedPassword);
	     
	     RoleModel roleModel = roleService.getByName("admin");
	     
	     user.setRole(roleModel);
	     
	     userService.add(user);
	     
	     //String encryptedPass = GeneralUtils.encodeSha256Password(user.getPassword());
			
		
		return "/page/login?faces-redirect=true";
		
	}

	protected boolean isValid() {
		// TODO Auto-generated method stub
		boolean errors=false;
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
	
	private boolean empty( String s ) {
		  // Null-safe, short-circuit evaluation.
		  return s == null || s.trim().isEmpty();
		}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	public static String getEmailPattern() {
		return EMAIL_PATTERN;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}
	

}

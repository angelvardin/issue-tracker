package com.bugtracker.web.beans.convertor;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.bugtracker.entity.IssueModel;
import com.bugtracker.services.interfaces.IssueServiceLocal;
import com.bugtracker.web.beans.viewmodels.IssueViewModel;

@ManagedBean
@FacesConverter(value = "issuesConverter")
public class IssuesViewModelConvertor implements Converter {

	@EJB
	IssueServiceLocal issuesSevice;
	
	

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		IssueModel model = issuesSevice.getById(new Long(value));
		IssueViewModel issueViewModel = new IssueViewModel(model.getProject().getName(),
				model.getSubmittedBy().getUsername(), model.getIssuepriority(), model.getState(), model.getDate(), 
				model.getId().toString(), model.getTitle(), model.getDescription());
		return issueViewModel;
		

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		IssueViewModel model = (IssueViewModel) value;

		return model.getId();

	}

}

package com.bugtracker.web.beans.primefaces.datamodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.bugtracker.entity.IssueModel;
import com.bugtracker.services.interfaces.IssueServiceLocal;
import com.bugtracker.web.beans.viewmodels.IssueViewModel;

public class LazyIssuesDataModel extends LazyDataModel<IssueViewModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<IssueViewModel> datasource;
	private String status;

	private IssueServiceLocal issueService;


	public LazyIssuesDataModel(IssueServiceLocal issueService) {
		datasource = new ArrayList<>();
		this.issueService = issueService;
		this.status=null;
	}
	
	public LazyIssuesDataModel(IssueServiceLocal issueService,String status) {
		datasource = new ArrayList<>();
		this.issueService = issueService;
		this.status=status;
	}

	@Override
	public Object getRowKey(IssueViewModel issue) {
		// TODO Auto-generated method stub
		return issue.getId();
	}

	@Override
	public IssueViewModel getRowData(String rowKey) {
		// TODO Auto-generated method stub`
		for (IssueViewModel issueViewModel : datasource) {
			if (issueViewModel.getId().equals(rowKey)) {
				return issueViewModel;
			}

		}

		return null;
	}

	@Override
	public List<IssueViewModel> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		// TODO Auto-generated method stub

		List<IssueViewModel> issues = new ArrayList<>();
		StringBuilder sortString = new StringBuilder();
		sortString.append("");

		if (sortField != null) {
			sortString.append(" " + sortField);
			sortString.append(sortOrder.equals(SortOrder.DESCENDING) ? " DESC " : " ASC ");
		}

		List<IssueModel> records;
		try {
			records = issueService.getAllRecordsBetween(first, pageSize, filters, sortString.toString(),status);
		} catch (Exception e) {
			// TODO: handle exception
			records = new ArrayList<>();
		}

		for (IssueModel issue : records) {
			issue.getIssuepriority().setValue(issue.getIssuepriority().name());
			issue.getState().setValue(issue.getState().name());
			issues.add(new IssueViewModel(issue.getProject().getName(), issue.getSubmittedBy().getUsername(),
					issue.getIssuepriority(), issue.getState(), issue.getDate(), issue.getId().toString(),
					issue.getTitle(),issue.getDescription()));

		}

		datasource = issues;

		// rowCount
		int dataSize = issues.size();
		this.setRowCount(dataSize);

		// paginate
		if (dataSize > pageSize) {
			try {
				return issues.subList(first, first + pageSize);
			} catch (IndexOutOfBoundsException e) {
				return issues.subList(first, first + (dataSize % pageSize));
			}
		} else {
			return issues;
		}

		// return issues;

	}

	/*
	 * @Override public List<IssueViewModel> load(int first, int pageSize,
	 * String sortField, SortOrder sortOrder,Map<String, Object> filters) {
	 * 
	 * List<IssueViewModel> data = new ArrayList<IssueViewModel>();
	 * 
	 * //filter for(IssueViewModel IssueViewModel : datasource) { boolean match
	 * = true;
	 * 
	 * if (filters != null) { for (Iterator<String> it =
	 * filters.keySet().iterator(); it.hasNext();) { try { String filterProperty
	 * = it.next(); Object filterValue = filters.get(filterProperty); String
	 * fieldValue =
	 * String.valueOf(IssueViewModel.getClass().getField(filterProperty).get(
	 * IssueViewModel));
	 * 
	 * if(filterValue == null || fieldValue.startsWith(filterValue.toString()))
	 * { match = true; } else { match = false; break; } } catch(Exception e) {
	 * match = false; } } }
	 * 
	 * if(match) { data.add(IssueViewModel); } }
	 * 
	 * //sort if(sortField != null) { Collections.sort(data, new
	 * LazySorter(sortField, sortOrder)); }
	 * 
	 * //rowCount int dataSize = data.size(); this.setRowCount(dataSize);
	 * 
	 * //paginate if(dataSize > pageSize) { try { return data.subList(first,
	 * first + pageSize); } catch(IndexOutOfBoundsException e) { return
	 * data.subList(first, first + (dataSize % pageSize)); } } else { return
	 * data; }
	 * 
	 * }
	 */

}

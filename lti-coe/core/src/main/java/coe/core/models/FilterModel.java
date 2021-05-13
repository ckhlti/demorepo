package coe.core.models;

import java.util.Set;

public class FilterModel {
	
	private String filterCategoryName;
	
	private Set<String> listItem;
	
	

	public void setFilterCategoryName(String filterCategoryName) {
		this.filterCategoryName = filterCategoryName;
	}

	public void setListItem(Set<String> listItem) {
		this.listItem = listItem;
	}

	public String getFilterCategoryName() {
		return filterCategoryName;
	}

	public Set<String> getListItem() {
		return listItem;
	}

	
	

}

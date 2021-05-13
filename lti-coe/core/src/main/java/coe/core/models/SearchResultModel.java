package coe.core.models;

public class SearchResultModel {
	
	private String title;
	private String path;
	private String pageTags;
	private String brandTags;
	private String colorTags;
	private String description;
	
	

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	public String getPageTags() {
		return pageTags;
	}

	public void setPageTags(String pageTags) {
		this.pageTags = pageTags;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

	public String getBrandTags() {
		return brandTags;
	}

	public void setBrandTags(String brandTags) {
		this.brandTags = brandTags;
	}

	public String getColorTags() {
		return colorTags;
	}

	public void setColorTags(String colorTags) {
		this.colorTags = colorTags;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}


}

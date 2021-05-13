package coe.core.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.jcr.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.ExporterOption;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.designer.Style;

import coe.core.utils.GlobalUtil;


@Model(adaptables = SlingHttpServletRequest.class, resourceType = { "/apps/coe/components/core/search/v1/search" })
@Exporter(name = "jackson", extensions = "json", options = {
		@ExporterOption(name = "SerializationFeature.WRITE_DATES_AS_TIMESTAMPS", value = "true") })

public class SearchModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchModel.class);

	
	

	@ScriptVariable
	private ValueMap properties;
	
	@Optional
	@ValueMapValue
	private String componentId;
	
	@ValueMapValue @Optional
    private String uniqueClassName;
	
	@SlingObject
    private Resource currentResource;

	@ScriptVariable
	private Style currentStyle;

	@ScriptVariable
	private Page currentPage;

	@SlingObject
	private ResourceResolver resourceResolver;

	@SlingObject
	private Resource resource;
	
	@Optional
	@ValueMapValue
	private String rootpath;
	
	@ValueMapValue
	@Optional
	private int onloadItemCount;
	
	@ValueMapValue
	@Optional
	private String showLoadMore;

	@Self
	private SlingHttpServletRequest request;

	private ArrayList<SearchResultModel> resultList;
	
	private List<FilterModel> filterList;

	protected java.util.List<Page> listItems;
	
	private Set<String> generalFilter;
	private Set<String> brandFilter;
	private Set<String> colorFilter;

	private Long totalHits;
	
	
	@PostConstruct
	private void initModel() {
		generateComponentId();
		String searchTerm=request.getParameter("fulltext");
		filterList = new ArrayList<FilterModel>();
		generalFilter = new TreeSet<>();
		brandFilter = new TreeSet<>();
		colorFilter = new TreeSet<>();
		
		 if (StringUtils.isNotBlank(rootpath) && StringUtils.isNotBlank(searchTerm)){
			 //pageManager = resourceResolver.adaptTo(PageManager.class);
				 resultList = new ArrayList<>();

		        Map<String, String> map = new HashMap<String, String>();

		        Session session = resourceResolver.adaptTo(Session.class);

		        QueryBuilder builder = resourceResolver.adaptTo(QueryBuilder.class);

		        map.put("path", rootpath);
		        map.put("type", "cq:Page");
		        map.put("fulltext", searchTerm);
		        map.put("p.limit","-1");
		        
		        Query query = builder.createQuery(PredicateGroup.create(map), session);
		        SearchResult result = query.getResult();
		        totalHits = result.getTotalMatches();
		        for (Hit hit : result.getHits()) {
		            try {
		            	SearchResultModel searchResultModel= new SearchResultModel();
		            	Page page = hit.getResource().adaptTo(Page.class);
		                ValueMap properties = page.getProperties();
		                searchResultModel.setPath(page.getPath().toString() + ".html");
		            	searchResultModel.setTitle(checkNullValue(properties.get("jcr:title", String.class)));
		            	searchResultModel.setDescription(checkNullValue(properties.get("jcr:description", String.class)));
		            	setFilters(page,properties,searchResultModel);
		            	resultList.add(searchResultModel);
		            }catch (Exception e){
		                LOGGER.error(e.getMessage());
		            }

		        }
		        if(generalFilter.size()>0) {
		        	FilterModel filterModel= new FilterModel();
		        	filterModel.setFilterCategoryName("General");
		        	filterModel.setListItem(generalFilter);
		        	filterList.add(filterModel);
		        }
		        if(brandFilter.size()>0) {
		        	FilterModel filterModel= new FilterModel();
		        	filterModel.setFilterCategoryName("Brand");
		        	filterModel.setListItem(brandFilter);
		        	filterList.add(filterModel);
		        }
		        if(colorFilter.size()>0) {
		        	FilterModel filterModel= new FilterModel();
		        	filterModel.setFilterCategoryName("Color");
		        	filterModel.setListItem(colorFilter);
		        	filterList.add(filterModel);
		        }
		        
		        
		    

		 }
		
	}
	
	private void setFilters(Page page,ValueMap properties, SearchResultModel searchResultModel) {
		
		
		List<Tag> brandTagList = getCustomTagList("cq:brandtags", page, resourceResolver);
		List<String> brandTagTitleList=new ArrayList<String>();
		setFilterSet(brandTagList, brandFilter, brandTagTitleList);
		String joinedBrandTags=String.join("|", brandTagTitleList);
		searchResultModel.setBrandTags(checkNullValue(joinedBrandTags));
		
		List<Tag> colorTagList = getCustomTagList("cq:colortags", page, resourceResolver);
		List<String> colorTagTitleList=new ArrayList<String>();
		setFilterSet(colorTagList, colorFilter, colorTagTitleList);
		String joinedColorTags=String.join("|", colorTagTitleList);
		searchResultModel.setColorTags(checkNullValue(joinedColorTags));
		
		
		Tag[] pageTags = page.getTags();
		List<Tag> generalTagList  = Arrays.asList(pageTags);
		List<String> generalTagTitleList=new ArrayList<String>();
		setFilterSet(generalTagList, generalFilter, generalTagTitleList);
        String joinedGeneralTags=String.join("|", generalTagTitleList);
        searchResultModel.setPageTags(checkNullValue(joinedGeneralTags));
		
		
	}
	
	private void setFilterSet(List<Tag> tagList, Set<String> filterSet,List<String> tagTitleList){
		
        String tagName="";
		if (!tagList.isEmpty()) {
       	
           for (Tag itemTag : tagList) {
               tagName = itemTag.getTitle();
               tagTitleList.add(tagName);
               filterSet.add(tagName);
               
           }
       }  
		
	}
	private void generateComponentId() {
		
		if (StringUtils.isEmpty(componentId) && currentResource != null) {

			componentId = GlobalUtil.generateUniqueHashId("searchcomponent", currentResource.getPath()); 	//Generate unique component Id

			LOGGER.debug("componentId generated :: path:: componentId {} , {}", currentResource.getPath(), componentId);

			if (StringUtils.isNotEmpty(componentId)) {

				try {
					
					Map<String,String> propertyMap = new HashMap<>();
					
					propertyMap.put("componentId", componentId);
					
					GlobalUtil.customPropertyWriter(currentResource.getResourceResolver(),currentResource.getPath(),propertyMap);
					
				} catch (PersistenceException ex) {

					LOGGER.error("Exception occured while Saving value in the node :: {}", ex);

				}
			}else {
				
				LOGGER.error("Unique Id Can not be Written Search Model ");
				
			}
		}
        else
        {
        	LOGGER.info("Component id " + componentId);
        	LOGGER.info("Component id not generated");
        }    
		
	}
	
	private String checkNullValue(String value) {
		if (StringUtils.isNotBlank(value)) {
			return value.trim();
		}
		return StringUtils.EMPTY;

	}


	/**
	 * @return the resultList
	 */
	public ArrayList<SearchResultModel> getResultList() {
		return resultList;
	}
	
	/**
	    *  return uniqueClassName
	    */
	    public String getUniqueClassName() {
	        return uniqueClassName;
	    }


	public String getComponentId() {
		return componentId;
	}
	public List<FilterModel> getFilterList() {
		return filterList;
	}


	public Long getTotalHits() {
		return totalHits;
	}
	
	 public static List<Tag> getCustomTagList(String tagListName, Page page, ResourceResolver resourceResolver) {
	        List<Tag> customTagList = new ArrayList<>();
	        ValueMap pageProperties = page.getProperties();
	        TagManager tagManager = resourceResolver.adaptTo(TagManager.class);

	        if (pageProperties != null) {
	            String[] tagsArray = (String[]) pageProperties.getOrDefault(tagListName, null);
	            if (tagsArray != null) {
	                if (tagsArray.length > 0) {
	                    for (int i = 0; i < tagsArray.length; i++) {
	                        String tagItem = tagsArray[i];
	                        Tag tag = tagManager.resolve(tagItem);
	                        if (tag != null) {
	                            customTagList.add(tag);
	                        }
	                    }
	                }
	            }
	        }
	        return customTagList;
	    }

	 
	public int getOnloadItemCount() {
		return onloadItemCount;
	}

	public String getShowLoadMore() {
		if(onloadItemCount>0) {
			showLoadMore="true";
			
		}else {
			showLoadMore="false";
		}
		
		return showLoadMore;
	}
	
	

	
}
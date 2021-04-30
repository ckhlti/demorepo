package coe.core.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.ExporterOption;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.designer.Style;
import com.day.cq.wcm.foundation.Image;

import coe.core.utils.GlobalUtil;


@Model(adaptables = SlingHttpServletRequest.class, resourceType = { "coe/components/core/summarylist/v1/summarylist" })
@Exporter(name = "jackson", extensions = "json", options = {
		@ExporterOption(name = "SerializationFeature.WRITE_DATES_AS_TIMESTAMPS", value = "true") })

public class SummaryListModel {

	private static final Logger LOG = LoggerFactory.getLogger(SummaryListModel.class);

	private static final int LIMIT_DEFAULT = 100;
	
	private static final int PN_DEPTH_DEFAULT = 1;

	public static final String PN_PARENT_PAGE = "parentPage";

	private String dateFormat = "dd MMM YYYY";
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

	@ScriptVariable
	private ValueMap properties;

	@ScriptVariable
	private Style currentStyle;

	@ScriptVariable
	private Page currentPage;

	@SlingObject
	private ResourceResolver resourceResolver;

	@SlingObject
	private Resource resource;

	@Self
	private SlingHttpServletRequest request;
	
	@SlingObject
    private Resource currentResource;

	@ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
	@Default(intValues = LIMIT_DEFAULT)
	private int limit;

	@ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
	@Default(intValues = PN_DEPTH_DEFAULT)
	private int childDepth;

	@ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
	@Default(intValues = 0)
	private int maxItems;
	
	@ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
	@Default(intValues = 0)
	private int maxImageItems;
	
	@ValueMapValue @Optional
    private String uniqueClassName;

	@Inject
	@Optional
	@Via("resource")
	public String componentId;
	
	@Inject
	@Optional
	@Via("resource")
	public String parentPage;
	
	private ArrayList<SummaryList> summaryCardList;
	
	private PageManager pageManager;

	protected java.util.List<Page> listItems;
	
	private boolean showScroll= false;

	@PostConstruct
	private void initModel() {
		
		if (StringUtils.isEmpty(componentId) && currentResource != null) {

			componentId = GlobalUtil.generateUniqueHashId("summarylist", currentResource.getPath()); 	//Generate unique component Id

			LOG.debug("componentId generated :: path:: componentId {} , {}", currentResource.getPath(), componentId);

			if (StringUtils.isNotEmpty(componentId)) {

				try {
					
					Map<String,String> propertyMap = new HashMap<>();
					
					propertyMap.put("componentId", componentId);
					
					GlobalUtil.customPropertyWriter(currentResource.getResourceResolver(),currentResource.getPath(),propertyMap);
					
				} catch (PersistenceException ex) {

					LOG.error("Exception occured while Saving value in the node :: {}", ex);

				}
			}else {
				
				LOG.error("Unique Id Can not be Written Accordion card Model ");
				
			}
		}
        else
        {
            LOG.info("Component id " + componentId);
            LOG.info("resource - " + currentResource.getPath().toString());
            LOG.info("Component id not generated");
        }    
		pageManager = resourceResolver.adaptTo(PageManager.class);
		listItems=populateChildListItems();
		summaryCardList=retrievePages(listItems);
		showScroll= summaryCardList.size()>maxImageItems;
		//listItems = setMaxItems();

	}
	
	private ArrayList<SummaryList> retrievePages(java.util.List<Page> listItems) {
		String imgAltText = null;
		ArrayList<SummaryList> summary = new ArrayList<>();

		for (Page listPage : listItems) {
			SummaryList gridObj = new SummaryList();
			if (listPage != null && summary.size()<maxItems) {
				ValueMap properties = listPage.getProperties();
				gridObj.setShortDescription(checkNullValue(properties.get("jcr:description", String.class)));
				gridObj.setTitle(checkNullValue(properties.get("jcr:title", String.class)));
				gridObj.setPath(listPage.getPath().toString() + ".html");
				gridObj.setImage(getImageSrc(listPage));
				summary.add(gridObj);
			}
		}

		return summary;

	}

	
	/**
	 * @return the gridList
	 */
	public ArrayList<SummaryList> getSummaryCardList() {
		return summaryCardList;
	}

	

	
	

	
	
	public int getMaxImageItems() {
		return maxImageItems;
	}

	public void setMaxImageItems(int maxImageItems) {
		this.maxImageItems = maxImageItems;
	}

	
	/**
	 * This method is used to check null and empty value and to trim non empty
	 * values
	 * 
	 * @param value
	 * @return value
	 */
	private String checkNullValue(String value) {
		if (StringUtils.isNotBlank(value)) {
			return value.trim();
		}
		return StringUtils.EMPTY;

	}


	/**
	 * This method is used to read all the child pages under the rootpath add it
	 * to list if it is news template
	 * 
	 * @return List of Pages
	 */
	private java.util.List<Page> populateChildListItems() {
		listItems = new ArrayList<>();
		Page rootPage = getRootPage(PN_PARENT_PAGE);
		if (rootPage != null) {
			listItems = collectChildren(rootPage.getDepth(), rootPage);
		}
		return listItems;
	}

	/**
	 * This method is used to read all the child pages and depth of the pages
	 * 
	 * @param startLevel
	 * @param parent
	 * @return List of Pages
	 */
	private java.util.List<Page> collectChildren(int startLevel, Page parent) {
		Iterator<Page> childIterator = parent.listChildren();
		while (childIterator.hasNext()) {
			Page child = childIterator.next();
				if (child != null) {
					listItems.add(child);
					if (child.getDepth() - startLevel < childDepth) {
						collectChildren(startLevel, child);
					}
				}

		}
		return listItems;
	}

	
	

	/**
	 * This method is used to get the root page
	 * 
	 * @param fieldName
	 * @return page
	 */
	private Page getRootPage(String fieldName) {
		String parentPath = properties.get(fieldName, currentPage.getPath());
		return pageManager.getContainingPage(resourceResolver.getResource(parentPath));
	}

	

	public static String getImageSrc(Page page){
        String imgSrc = StringUtils.EMPTY;
        Resource imageResource = getImageResource(page);
        if(imageResource != null){
            ValueMap imageProperties = imageResource.adaptTo(ValueMap.class);
            if(imageProperties != null){
                imgSrc = imageProperties.get("fileReference", StringUtils.EMPTY);
                if(StringUtils.isEmpty(imgSrc)){
                    Resource uploadedFile = imageResource.getChild("file");
                    if(uploadedFile != null){
                        Image image = new Image(uploadedFile);
                        imgSrc = (String)uploadedFile.getPath()+".img.png"+ image.getSuffix();
                    }
                }
            }
        }
        return imgSrc;
    }
	
	 /* function to get image resource */
    public static Resource getImageResource(Page page){
        Resource imageResource = null;
        if(imageResource == null){
            imageResource = getResourceByName("image", page);
        }
        return imageResource;
    }
    
    public static Resource getResourceByName(String resourceName, Page page){
        Resource foundResource = null;
        Resource contentResource = page.getContentResource();
        if(contentResource.hasChildren()){
            foundResource =  searchPageResources(resourceName, contentResource);
        }
        return foundResource;
    }

    /* recursive function to search thru page resource util it finds the passed in resource Type */
    public static Resource searchPageResources(String resourceType, Resource item){
        if(item.getResourceType().equals(resourceType) || item.getName().toLowerCase().equals(resourceType.toLowerCase())) {
            return item;
        }
        Iterator<Resource> contentResourceList = item.listChildren();
        Resource temp;
        if(contentResourceList.hasNext()) {
            while (contentResourceList.hasNext()) {
                Resource newItem = contentResourceList.next();
                temp =  searchPageResources(resourceType, newItem);
                if(temp != null)
                    return temp;
            }
        }
        return null;
    }

	public boolean isShowScroll() {
		return showScroll;
	}

	
		

	
}
package coe.core.models;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.*;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import coe.core.models.TitleImpl;
import java.util.HashMap;
import java.util.Map;
import coe.core.utils.GlobalUtil;


@Model(adaptables = SlingHttpServletRequest.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)

public class TitleImpl{
	
private static final Logger LOG = LoggerFactory.getLogger(TitleImpl.class);
	

	@SlingObject
	private Resource currentResource;

	@ValueMapValue 
    private String componentId;
	
	@Inject
	@Via("resource")
	String classname;
	
	@Inject
	@Via("resource")
	String title;
	
	@Inject
	@Via("resource")
	String type;
	
	@ScriptVariable
	Page currentPage;
	
	@PostConstruct
	protected void init() {
		
        if (StringUtils.isEmpty(componentId) && currentResource != null) {
        	
			componentId = GlobalUtil.generateUniqueHashId("title", currentResource.getPath()); 	//Generate unique component Id

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
				
				LOG.error("Unique Id Can not be Written in title model ");
				
			}
		}
        else
        {
            LOG.info("Component id " + componentId);
            LOG.info("resource - " + currentResource.getPath().toString());
            LOG.info("Component id not generated");
        }    
    }

    public String getPageTitle(){
		if(title!=null) 
		{
			return title;
		}
		else 
		{
        return currentPage.getTitle();
        }
    }

	
	public String getTitle() {
	return title;
	}
	
	
	public String getType() {		
	return type;
	}
	
	public String getComponentId() {
	return componentId;
	}
		
	public String getClassname() {
	return classname;
	}
		
}
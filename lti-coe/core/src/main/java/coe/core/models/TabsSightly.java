package coe.core.models;


import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import javax.inject.Inject;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.settings.SlingSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import coe.core.utils.GlobalUtil;
import java.util.*;
import javax.inject.Named;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TabsSightly {
	private static final Logger logger1 = LoggerFactory.getLogger(TabsSightly.class);

    @OSGiService
    private SlingSettingsService settings;
    @SlingObject
    private Resource currentResource;
    @SlingObject
    private ResourceResolver resourceResolver;

    @Inject
    private String headingmain,componentId, uniqueClassName;
 
    @Inject
    @Named("multifield/.")
    private List<TabsSightlyImpl> accordion;
 
    @PostConstruct
    protected void init() {
        if (StringUtils.isEmpty(componentId) && currentResource != null) {

			componentId = GlobalUtil.generateUniqueHashId("co-tabs", currentResource.getPath()); 	//Generate unique component Id

			logger1.debug("componentId generated :: path:: componentId {} , {}", currentResource.getPath(), componentId);

			if (StringUtils.isNotEmpty(componentId)) {

				try {
					
					Map<String,String> propertyMap = new HashMap<>();
					
					propertyMap.put("componentId", componentId);
					
					GlobalUtil.customPropertyWriter(currentResource.getResourceResolver(),currentResource.getPath(),propertyMap);
					
				} catch (PersistenceException ex) {

					logger1.error("Exception occured while Saving value in the node :: {}", ex);

				}
			}else {
				
				logger1.error("Unique Id Can not be Written Tabs Model ");
				
			}
		}
        else
        {
            logger1.info("Component id " + componentId);
            logger1.info("resource - " + currentResource.getPath().toString());
            logger1.info("Component id not generated");
        }    
    }

    public String getHeadingmain() {
        logger1.info("Inside heading main method");
        return headingmain;
    }

    public String getUniqueClassName() {
        return uniqueClassName;
    }    
    public String getComponentId() {
        return componentId;
    }     
    public List<TabsSightlyImpl> getAccordion() {
    	logger1.info("accordion >>>" + accordion.toString());
        return new ArrayList<>(accordion);
    }
 
 
}
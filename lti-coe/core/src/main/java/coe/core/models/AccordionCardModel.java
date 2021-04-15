
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

@Model(adaptables = SlingHttpServletRequest.class,  defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class AccordionCardModel {

	private static final Logger LOG = LoggerFactory.getLogger(AccordionCardModel.class);

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;

    @OSGiService
    private SlingSettingsService settings;
    @SlingObject
    private Resource currentResource;
    @SlingObject
    private ResourceResolver resourceResolver;

    @ValueMapValue 
    private String componentId;
    
    @ValueMapValue @Optional
    private String uniqueClassName;
    
    @ValueMapValue @Optional
    private String title;

    @PostConstruct
    protected void init() {
        if (StringUtils.isEmpty(componentId) && currentResource != null) {

			componentId = GlobalUtil.generateUniqueHashId("Accodion-Card", currentResource.getPath()); 	//Generate unique component Id

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
    }

    /**
    *  return componentID
    */
    public String getComponentId() {
        return componentId;
    }


    /**
    *  return uniqueClassName
    */
    public String getUniqueClassName() {
        return uniqueClassName;
    }

    /**
    *  return title
    */
    public String getTitle() {
        return title;
    }

}

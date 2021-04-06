/*
 *  SLing model for CTA Button 
 *
 */
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

@Model(adaptables = SlingHttpServletRequest.class)
public class CTAButton {

	private static final Logger LOG = LoggerFactory.getLogger(CTAButton.class);

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
    
    @ValueMapValue
    private String uniqueClassName;
    
    @ValueMapValue
    private String ariaLabel;
    @ValueMapValue
    private String function;
    @ValueMapValue
    private String appearance;
    @ValueMapValue
    private String alignment;
    @ValueMapValue
    private String buttonText;
    @ValueMapValue
    private String modalID;
    @ValueMapValue
    private String targetUrl;
    @ValueMapValue
    private String target;
    @ValueMapValue
    private String iconClass;

    @PostConstruct
    protected void init() {
        if (StringUtils.isEmpty(componentId) && currentResource != null) {

			componentId = GlobalUtil.generateUniqueHashId("cta", currentResource.getPath()); 	//Generate unique component Id

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
				
				LOG.error("Unique Id Can not be Written CTA Model ");
				
			}
		}
        else
        {
            LOG.info("Component id for CTA Button" + componentId);
            LOG.info("Component id not generated");
        }    
    }

	/**
    *  return modalID
    */
    public String getModalID() {
        return modalID;
    }

    /**
    *  return targetUrl
    */
    public String getTargetUrl() {
        return targetUrl;
    }

    /**
    *  return target
    */
    public String getTarget() {
        return target;
    }

    /**
    *  return iconClass
    */
    public String getIconClass() {
        return iconClass;
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
    *  return ariaLabel
    */
    public String getAriaLabel() {
        return ariaLabel;
    }

    /**
    *  return function
    */
    public String getFunction() {
        return function;
    }

    /**
    *  return appearance
    */
    public String getAppearance() {
        return appearance;
    }

    /**
    *  return buttonText
    */
    public String getButtonText() {
        return buttonText;
    }

    /**
    *  return alignment
    */
    public String getAlignment() {
        return alignment;
    }

}

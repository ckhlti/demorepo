package coe.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.engine.SlingSettingsService;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Optional;
import coe.core.utils.GlobalUtil;

import javax.inject.Inject;
//import javax.inject.Named;
//import java.util.*;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SocialFeedModel {
	private static final Logger log = LoggerFactory.getLogger(SocialFeedModel.class);
    
    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;
    
    @OSGiService
    private SlingSettingsService settings;
    @SlingObject
    private Resource currentResource;
    @SlingObject
    private ResourceResolver resourceResolver;
 
    @PostConstruct
    protected void init() {
        if (StringUtils.isEmpty(componentId) && currentResource != null) {
 
            componentId = GlobalUtil.generateUniqueHashId("Social-Feed", currentResource.getPath());     //Generate unique component Id
 
            log.debug("componentId generated :: path:: componentId {} , {}", currentResource.getPath(), componentId);
 
            if (StringUtils.isNotEmpty(componentId)) {
 
                try {
                    
                    Map<String,String> propertyMap = new HashMap<>();
                    
                    propertyMap.put("componentId", componentId);
                    
                    GlobalUtil.customPropertyWriter(currentResource.getResourceResolver(),currentResource.getPath(),propertyMap);
                    
                } catch (PersistenceException ex) {
 
                    log.error("Exception occured while Saving value in the node :: {}", ex);
 
                }
            }else {
                
                log.error("Unique Id Can not be Written Social Feed Model ");
                
            }
        }
        else
        {
            log.info("Component id " + componentId);
            log.info("resource - " + currentResource.getPath().toString());
            log.info("Component id not generated");
        }    
    }

    @ValueMapValue
    private String componentId;

    @ValueMapValue
    private String uniqueClassName;

    @Inject
    private String socialurl;
 
    @Inject
    private String socialtitle;

    @Inject
    private String socialsummary;

    @Inject
    private String fileReference;

    @Inject
    private String fb;

    @Inject
    private String twitter;

    @Inject
    private String linkedin;

    public String getSocialurl() {
        log.info("Inside url");
        return socialurl;
    }
    
    public String getSocialtitle() {
        log.info("Inside title");
        return socialtitle;
    }

    public String getSocialsummary() {
        log.info("Inside summary");
        return socialsummary;
    }

    public String getFilereference() {
        log.info("Inside image");
        return fileReference;
    }

    public String getFb() {
        return fb;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public String getComponentId(){
        return componentId;
    }
    public String getUniqueClassName(){
        return uniqueClassName;
    }    
}
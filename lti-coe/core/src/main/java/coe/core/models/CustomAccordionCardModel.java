package coe.core.models;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.settings.SlingSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import coe.core.utils.GlobalUtil;

import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

@Model(adaptables = SlingHttpServletRequest.class,  defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class CustomAccordionCardModel {
	private static final Logger LOG = LoggerFactory.getLogger(CustomAccordionCardModel.class);
	
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
	    private String cardId;
	    
	    @ValueMapValue
	    private String uniqueClassName;
	    
	    @ValueMapValue @Optional
	    private String accordionCardTitle;
	    
	    @PostConstruct
	    protected void init() {
	    	if (StringUtils.isEmpty(cardId) && currentResource != null) {
	    		cardId = GlobalUtil.generateUniqueHashId("Accordion_Card_V2", currentResource.getPath()); // generates card ID automatically when left empty by author.
	    		
	    		LOG.debug("componentId generated :: path:: cardId {} , {}", currentResource.getPath(), cardId);

	    		if (StringUtils.isNotEmpty(cardId)) {
	    			try {
	    				Map<String,String> propertyMap = new HashMap<>();
	    				propertyMap.put("cardId", cardId);
	    				GlobalUtil.customPropertyWriter(currentResource.getResourceResolver(),currentResource.getPath(),propertyMap);
	    				
	    			}catch (PersistenceException ex) {
	    				LOG.error("Exception occured while Saving value in the node :: {}", ex);
	    			}
	    			
	    		}else {
	    			LOG.error("Unique Id Can not be Written Accordion card Model ");
	    		}

	    	}else {
	    		LOG.info("Card id " + cardId);
	            LOG.info("resource - " + currentResource.getPath().toString());
	            LOG.info("Card id not generated");
	    	}
	    }

	    /* returns accordion card id */
		public String getCardId() {
			return cardId;
		}

		/* returns unique class name for accordion card */
		public String getUniqueClassName() {
			return uniqueClassName;
		}

		/* returns accordion card title */
		public String getAccordionCardTitle() {
			return accordionCardTitle;
		}

	    
	  

}
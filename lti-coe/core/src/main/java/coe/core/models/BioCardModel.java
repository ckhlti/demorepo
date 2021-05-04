package coe.core.models;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
/*
 *  Sling model for Bio 
 *
 */
import org.apache.sling.settings.SlingSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import coe.core.utils.GlobalUtil;

@Model(
        adaptables = Resource.class,
        resourceType = "coe/components/core/bio/v1/bio",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = "jackson", extensions = "json")

public class BioCardModel {
	private static final Logger LOG = LoggerFactory.getLogger(BioCardModel.class);
	
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
	    private String noOfBios;
	    
	    @ValueMapValue
	    private String noOfRows;
	    
	    @ValueMapValue
	    private String showMoreCaptionCTA;
	    
	    @ValueMapValue
	    private String showMoreDetailsCTA;
	    
	    @Inject
	    @Optional
	    private List<BioCardDetails> biocards;

		
	    
	    public String getComponentId() {
			return componentId;
		}

		public String getUniqueClassName() {
			return uniqueClassName;
		}

		public String getNoOfBios() {
			return noOfBios;
		}

		public String getNoOfRows() {
			return noOfRows;
		}

		public String getShowMoreCaptionCTA() {
			return showMoreCaptionCTA;
		}

		public String getShowMoreDetailsCTA() {
			return showMoreDetailsCTA;
		}

	    public List<BioCardDetails> getBiocards() {
			return biocards;
		}
	    
	  public String getJson() throws RepositoryException {
	    ObjectMapper objectMapper = new ObjectMapper();
	    String tStr = "";
	    try {
	        tStr = objectMapper.writeValueAsString(this.getBiocards());
	        LOG.error(tStr);
	    }
	    catch (JsonProcessingException ex) {
	        LOG.error("Cannot do it: {}", ex.getMessage());
	    }
	    return tStr;
	   }

		@PostConstruct
	    protected void init() {
	    	LOG.debug("In init of BioCardModel");
	  	
	    	 if (StringUtils.isEmpty(componentId) && currentResource != null) {
	 			componentId = GlobalUtil.generateUniqueHashId("Bio_Card", currentResource.getPath()); 	//Generate unique component Id

	 			if (StringUtils.isNotEmpty(componentId)) {
	 				try {	 					
	 					Map<String,String> propertyMap = new HashMap<>();
	 					
	 					propertyMap.put("componentId", componentId);
	 					
	 					GlobalUtil.customPropertyWriter(currentResource.getResourceResolver(),currentResource.getPath(),propertyMap);
	 					
	 				} catch (PersistenceException ex) {
	 					LOG.error("Exception occured while Saving value in the node :: {}", ex);
	 				}
	 			}else {	 				
	 				LOG.error("Unique Id Can not be Written for Bio Card Model ");	 				
	 			}
	 		}
	         else
	         {
	             LOG.info("Component id for Bio Card Component::" + componentId);
	             LOG.info("Component id not generated");
	         }    
	    }
}
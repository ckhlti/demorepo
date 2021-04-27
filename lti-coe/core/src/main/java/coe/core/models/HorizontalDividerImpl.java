package coe.core.models;

import coe.core.utils.GlobalUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.models.annotations.*;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

	@Model(adaptables = SlingHttpServletRequest.class,
	        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
	)

	public class HorizontalDividerImpl {
		
		private static final Logger LOG = LoggerFactory.getLogger(CTAButton.class);
		@SlingObject
	    private Resource currentResource;
	    @SlingObject
	    private ResourceResolver resourceResolver;
	    @ValueMapValue 
	    private String componentId;				
	    @ValueMapValue 
	    private String classname;		
	    @ValueMapValue 
	    private String height;		
	    @ValueMapValue 
	    private String backgroundcolor;		
	    @ValueMapValue 
	    private String bordercolor;
		
		@PostConstruct
	    protected void init() {
	        if (StringUtils.isEmpty(componentId) && currentResource != null) {

				componentId = GlobalUtil.generateUniqueHashId("horizontaldiv", currentResource.getPath()); 	//Generate unique component Id

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
					
					LOG.error("Unique Id Can not be Written horizontal divider model ");
					
				}
			}
	        else
	        {
	            LOG.info("Component id " + componentId);
	            LOG.info("resource - " + currentResource.getPath().toString());
	            LOG.info("Component id not generated");
	        }    
	    }

		
		public String getBackgroundcolor() {
		return backgroundcolor;
		}
		public String getBordercolor() {
		return bordercolor;
		}
		public String getHeight() {
		return height;
		}	
		public String getComponentId() {
		return componentId;
		}			
		public String getClassname() {
		return classname;
		}
			
	}

package coe.core.models;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import coe.core.utils.GlobalUtil;

@Model(adaptables = SlingHttpServletRequest.class,  defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class VideoPlayerModel {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VideoPlayerModel.class);

	@ValueMapValue
	@Optional
	private String componentId;
	
	@ValueMapValue @Optional
    private String uniqueClassName;
	
	@SlingObject
    private Resource currentResource;
	
	@ValueMapValue @Optional
    private String videoSourceType;
	
	@ValueMapValue @Optional
    private String controlbar;
	
	@ValueMapValue @Optional
    private String loop;
	
	@ValueMapValue @Optional
    private String autoplay;
	
	@ValueMapValue @Optional
    private String damvideopath;
	
	@ValueMapValue @Optional
    private String videoyoutubeurl;
	
	@PostConstruct
	protected void initModel() {
		if (StringUtils.isEmpty(componentId) && currentResource != null) {

			componentId = GlobalUtil.generateUniqueHashId("vidoplayercomponent", currentResource.getPath()); 	//Generate unique component Id

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
				
				LOGGER.error("Unique Id Can not be Written Accordion card Model ");
				
			}
		}
        else
        {
        	LOGGER.info("Component id " + componentId);
        	LOGGER.info("Component id not generated");
        }    
	}

	public String getComponentId() {
		return componentId;
	}

	public String getUniqueClassName() {
		return uniqueClassName;
	}

	public String getVideoSourceType() {
		return videoSourceType;
	}

	public String isControlbar() {
		return controlbar;
	}

	public String isLoop() {
		return loop;
	}

	public String isAutoplay() {
		return autoplay;
	}

	public String getDamvideopath() {
		return damvideopath;
	}

	public String getVideoyoutubeurl() {
		return videoyoutubeurl;
	}
	
	
	
	
}

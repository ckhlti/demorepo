package coe.core.models;

import org.apache.sling.api.SlingHttpServletRequest; 
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.api.resource.PersistenceException;
import javax.inject.Inject;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.settings.SlingSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import java.util.HashMap;
import java.util.Map;
import coe.core.utils.GlobalUtil;
//@Model(adaptables=SlingHttpServletRequest.class)
//public class ClickableImageModel {
//	@Inject
//	String altText;
//	
//	@Inject
//	String link;
//	
//	@Inject
//	String imagePath;
//	
//
//}


@Model(adaptables = SlingHttpServletRequest.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ClickableImageModel {
	private static final Logger LOG = LoggerFactory.getLogger(ClickableImageModel.class);

@SlingObject
private Resource currentResource;

@ValueMapValue @Optional
private String componentId;

@ValueMapValue @Optional
private String uniqueClassName;

 @ValueMapValue
private String altText;
 
@ValueMapValue
private String link;

@ValueMapValue
private String linkTarget;

@ValueMapValue
private String fileReference;


@PostConstruct
protected void init() {
	if (StringUtils.isEmpty(componentId) && currentResource != null) {

		componentId = GlobalUtil.generateUniqueHashId("ClickableImage", currentResource.getPath()); 	//Generate unique component Id


		if (StringUtils.isNotEmpty(componentId)) {

			try {
				
				Map<String,String> propertyMap = new HashMap<>();
				
				propertyMap.put("componentId", componentId);
				
				GlobalUtil.customPropertyWriter(currentResource.getResourceResolver(),currentResource.getPath(),propertyMap);
				
			} catch (PersistenceException ex) {

				LOG.error("Exception occured while Saving value in the node :: {}", ex);

			}
		}else {
			
			LOG.error("Unique Id Can not be Written Custom Carousel Model ");
			
		}
	}
	else
	{
		LOG.info("Component id for Custom Carousel" + componentId);
		LOG.info("Component id not generated");
	}    
}


public String getAltText() {
	return altText;
}

public String getLink() {
	return link;
}

public String getLinkTarget() {
	return linkTarget;
}

public String getFileReference() {
	return fileReference;
}


public String getComponentId() {
	return componentId;
}


public String getUniqueClassName() {
	return uniqueClassName;
}



}
package coe.core.models;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.engine.SlingSettingsService;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import coe.core.utils.GlobalUtil;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CaseStudyImpl {
	private static final Logger LOG = LoggerFactory.getLogger(CaseStudyImpl.class);

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

			componentId = GlobalUtil.generateUniqueHashId("Case-Study", currentResource.getPath()); 	//Generate unique component Id

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
				
				LOG.error("Unique Id Can not be Written Case Study Model ");
				
			}
		}
        else
        {
            LOG.info("Component id " + componentId);
            LOG.info("resource - " + currentResource.getPath().toString());
            LOG.info("Component id not generated");
        }    
    }
	
	@ValueMapValue 
    private String componentId;
    
    @ValueMapValue @Optional
    private String uniqueClassName;

    @ValueMapValue
    private String title;
    
    @ValueMapValue
    private String summary;
    
    @ValueMapValue
    private String btntxt;
    
    @ValueMapValue
	private String link;
	
	@ValueMapValue
	private String newPage;
	
	@ValueMapValue
	private String fileReference;
	
	@ValueMapValue
	private String position;
	
	@ValueMapValue
	private String backgroundColor;
	
	@ValueMapValue
	private String foregroundColor;

	@ValueMapValue
	private String foregroundColorBtn;

	@ValueMapValue
	private String backgroundColorBtn;
	
	public String getComponentId() {
        return componentId;
    }

    public String getUniqueClassName() {
        return uniqueClassName;
    }

	public String getTitle() {
		return title;
	}

	public String getSummary() {
		return summary;
	}

	public String getBtntxt() {
		return btntxt;
	}

	public String getLink() {
		return link;
	}

	public String getNewPage() {
		return newPage;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public String getForegroundColor() {
		return foregroundColor;
	}

    public String getFileReference() {
		return fileReference;
    }
    
    public String getPosition() {
		return position;
	}

	public String getForegroundColorBtn() {
		return foregroundColorBtn;
	}

	public String getBackgroundColorBtn() {
		return backgroundColorBtn;
	}
}

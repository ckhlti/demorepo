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
import java.util.List;
import java.util.ArrayList;

@Model(adaptables = Resource.class,  defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class CarouselModel {

	private static final Logger LOG = LoggerFactory.getLogger(CarouselModel.class);

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;

    @OSGiService
    private SlingSettingsService settings;
    @SlingObject
    private Resource currentResource;
    @SlingObject
    private ResourceResolver resourceResolver;

    @ValueMapValue @Optional
    private String componentId;
    
    @ValueMapValue @Optional
    private String uniqueClassName;

    @ValueMapValue @Optional
    private String indexType, displayArrows, autoPlay, delay, autopauseDisabled;

    @Inject
    private List<CarouselSlideModels> slides;

    @PostConstruct
    protected void init() {
        if (StringUtils.isEmpty(componentId) && currentResource != null) {

			componentId = GlobalUtil.generateUniqueHashId("Custom-Carousel", currentResource.getPath()); 	//Generate unique component Id


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

	public String getComponentId() {        return componentId;    }
    public String getUniqueClassName() {        return uniqueClassName;    }
    public String getIndexType() { return indexType;}
    public String getDisplayArrows() {return displayArrows;}
    public String getAutoPlay() { return autoPlay;}
    public String getDelay() { return delay;}
    public String getAutopauseDisabled() { return autopauseDisabled;}
    public List<CarouselSlideModels> getSlides() {
        List<CarouselSlideModels> validSlides = new ArrayList<CarouselSlideModels>();
        for (CarouselSlideModels slideModel : slides) {
            if (slideModel.getSkipSlide().equals("false")) {validSlides.add(slideModel);}
        }
        LOG.debug(validSlides.toString());
        return validSlides;
        }

}

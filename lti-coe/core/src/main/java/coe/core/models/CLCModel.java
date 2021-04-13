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

@Model(adaptables = Resource.class,  defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class CLCModel {

	private static final Logger LOG = LoggerFactory.getLogger(CLCModel.class);

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)

    @SlingObject
    private Resource currentResource;


    @ValueMapValue @Optional
    private String componentId;
    
    @ValueMapValue @Optional
    private String uniqueClassName;
    
    @ValueMapValue @Optional
    private String backgroundType;
    @ValueMapValue @Optional
    private String desktopFileReference;
    @ValueMapValue @Optional
    private String mobileSetting;
    @ValueMapValue @Optional
    private String mobileFileReference;

    @ValueMapValue @Optional
    private String desktopBackgroundColor;
    @ValueMapValue @Optional
    private String desktopFontColor;
    @ValueMapValue @Optional
    private String mobileBackgroundColorSetting;
    @ValueMapValue @Optional
    private String mobileBackgroundColor;
    @ValueMapValue @Optional
    private String mobileFontColor;

    @ValueMapValue @Optional
    private String  gradientType;
    @ValueMapValue @Optional
    private String linearGradientType;
    @ValueMapValue @Optional
    private String radialDegree;
    @Inject 
    private List<CLCGradientColorModel> gradientColors;
    @ValueMapValue @Optional
    private String desktopGradientFontColor;
    @ValueMapValue @Optional
    private String mobileBackgroundGradient;
    @ValueMapValue @Optional
    private String mobileGradientType;
    @ValueMapValue @Optional
    private String mobileLinearGradientType;
    @ValueMapValue @Optional
    private String mobileRadialDegree;
    @Inject 
    private List<CLCGradientColorModel> mobileGradientColors;
    @ValueMapValue @Optional
    private String mobileGradientFontColor;

    @PostConstruct
    protected void init() {
        LOG.debug("CLC model initiat");
        if (StringUtils.isEmpty(componentId) && currentResource != null) {

			componentId = GlobalUtil.generateUniqueHashId("Custom Layout Container", currentResource.getPath()); 	//Generate unique component Id

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
				
				LOG.error("Unique Id Can not be Written Custom Layout Container Model ");
				
			}
		}
        else
        {
            LOG.info("Component id for Custom Layout Container Button" + componentId);
            LOG.info("Component id not generated");
        }    
    }

	/**
    *  return modalID
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

    public String getBackgroundType() {
        return backgroundType;
    }
    public String getDesktopFileReference() {
        return desktopFileReference;
    }
    public String getMobileSetting() {
        return mobileSetting;
    }
    public String getMobileFileReference() {
        return mobileFileReference;
    }
    public String getDesktopBackgroundColor() {
        return desktopBackgroundColor;
    }
    public String getDesktopFontColor() {
        return desktopFontColor;
    }
    public String getMobileBackgroundColorSetting() {
        return mobileBackgroundColorSetting;
    }
    public String getMobileBackgroundColor() {
        return mobileBackgroundColor;
    }
    public String getMobileFontColor() {
        return mobileFontColor;
    }
    public String getGradientType() {
        return gradientType;
    }
    public String getLinearGradientType() {
        return linearGradientType;
    }
    public String getRadialDegree() {
        return radialDegree;
    }
    public List<CLCGradientColorModel> getGradientColors() {
        LOG.debug("Gradient Colors for desktop : " + gradientColors.toString());
        return gradientColors;
    }
    public String getDesktopGradientFontColor() {
        return desktopGradientFontColor;
    }
    public String getMobileBackgroundGradient() {
        return mobileBackgroundGradient;
    }
    public String getMobileGradientType() {
        return mobileGradientType;
    }
    public String getMobileLinearGradientType() {
        return mobileLinearGradientType;
    }
    public String getMobileRadialDegree() {
        return mobileRadialDegree;
    }
    public List<CLCGradientColorModel> getMobileGradientColors() {
                LOG.debug("Gradient Colors for mobile : " + mobileGradientColors.toString());

        return mobileGradientColors;
    }
    public String getMobileGradientFontColor() {
        return mobileGradientFontColor;
    }

}

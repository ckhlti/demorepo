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


@Model(adaptables = Resource.class,  defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class NewsItems {

	private static final Logger LOG = LoggerFactory.getLogger(NewsItems.class);

    @ValueMapValue @Optional
    private String description;



    @PostConstruct
    protected void init() {
 
    }

	/**
    *  return modalID
    */
    public String getDescription() {
        LOG.debug("Description - " + description);
        return description;
    }

    

}

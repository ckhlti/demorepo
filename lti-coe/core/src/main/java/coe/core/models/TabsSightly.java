package coe.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TabsSightly {
	private static final Logger logger1 = LoggerFactory.getLogger(TabsSightly.class);
    
    @Inject
    private String headingmain;
 
    @Inject
    @Named("multifield/.")
    private List<TabsSightlyImpl> accordion;
 
    public String getHeadingmain() {
        logger1.info("Inside heading main method");
        return headingmain;
    }

    public List<TabsSightlyImpl> getAccordion() {
    	logger1.info("accordion >>>" + accordion.toString());
        return new ArrayList<>(accordion);
    }
 
 
}
package coe.core.models;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TabsSightlyImpl {
    
    @ValueMapValue
    private String tabid;
    
    @ValueMapValue
    private String tabname;
    
    @ValueMapValue
    private String tabdesc;

    public String getTabid() {
        return tabid;
    }

    public String getTabname() {
        return tabname;
    }

    public String getTabdesc() {
        return tabdesc;
    }

}

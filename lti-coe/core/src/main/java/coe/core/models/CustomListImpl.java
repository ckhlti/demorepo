package coe.core.models;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CustomListImpl{
    
    @ValueMapValue
    private String point;
    
    @ValueMapValue
    private String link;
    
    @ValueMapValue
    private String newPage;

    public String getPoint() {
        return point;
    }

    public String getLink() {
        return link;
    }

    public String getNewPage() {
        return newPage;
    }

}
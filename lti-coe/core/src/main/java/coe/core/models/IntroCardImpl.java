package coe.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class IntroCardImpl {

    @ValueMapValue
    private String title;
    
    @ValueMapValue
    private String des;
    
    @ValueMapValue
    private String btntxt;
    
    @ValueMapValue
    private String link;

	public String getTitle() {
		return title;
	}

	public String getDes() {
		return des;
	}

	public String getBtntxt() {
		return btntxt;
	}

	public String getLink() {
		return link;
	}

    
}


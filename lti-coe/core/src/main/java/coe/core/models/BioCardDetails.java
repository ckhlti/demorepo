package coe.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class, defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class BioCardDetails {
	private static final Logger LOG = LoggerFactory.getLogger(BioCardDetails.class);
    
	 @ValueMapValue @Optional
	private String name;
	
	 @ValueMapValue @Optional
    private String title;
	
	 @ValueMapValue @Optional
	private String summary;
	
	 @ValueMapValue @Optional
    private String email;
	
	 @ValueMapValue @Optional
    private String social_fb;
	
	 @ValueMapValue @Optional
    private String social_ln;
	
	 @ValueMapValue @Optional
    private String social_twitter;
	
	 @ValueMapValue @Optional
    private String image;
	
	 @ValueMapValue @Optional
    private String details_type;
	
	 @ValueMapValue @Optional
    private String bioLink;
	
	 @ValueMapValue @Optional
    private String details;
	
	 @ValueMapValue @Optional
    private String fragment_details;
      
	 @PostConstruct
	    protected void init() {
	        LOG.debug("In init of BioCardsHelper Model");
	    }

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public String getSummary() {
		return summary;
	}

	public String getEmail() {
		return email;
	}

	public String getSocial_fb() {
		return social_fb;
	}

	public String getSocial_ln() {
		return social_ln;
	}

	public String getSocial_twitter() {
		return social_twitter;
	}

	public String getImage() {
		return image;
	}

	public String getDetails_type() {
		return details_type;
	}

	public String getBioLink() {
		return bioLink;
	}

	public String getDetails() {
		return details;
	}

	public String getFragment_details() {
		return fragment_details;
	}
    	
	
}

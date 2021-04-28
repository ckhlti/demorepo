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
public class SocialFeedModel {
	private static final Logger log = LoggerFactory.getLogger(SocialFeedModel.class);
    
    @Inject
    private String socialurl;
 
    @Inject
    private String socialtitle;

    @Inject
    private String socialsummary;

    @Inject
    private String fileReference;

    @Inject
    private String fb;

    @Inject
    private String insta;

    @Inject
    private String twitter;

    @Inject
    private String linkedin;

    public String getSocialurl() {
        log.info("Inside url");
        return socialurl;
    }
    
    public String getSocialtitle() {
        log.info("Inside title");
        return socialtitle;
    }

    public String getSocialsummary() {
        log.info("Inside summary");
        return socialsummary;
    }

    public String getFilereference() {
        log.info("Inside image");
        return fileReference;
    }

    public String getFb() {
        return fb;
    }

    public String getInsta() {
        return insta;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getLinkedin() {
        return linkedin;
    }

}
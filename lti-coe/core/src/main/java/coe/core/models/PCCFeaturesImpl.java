package coe.core.models;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class, defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)

public class PCCFeaturesImpl{
	
	private static final Logger LOG = LoggerFactory.getLogger(PCCFeaturesImpl.class);
	
	@ValueMapValue @Optional
	private String featureName;
	
	@PostConstruct
    protected void init() {
        LOG.debug("In init of PCC Features Impl");
    }

	public String getFeatureName() {
		return featureName;
	}
	
	
}
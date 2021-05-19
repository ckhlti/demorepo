package coe.core.models;


import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Model(adaptables = Resource.class, defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)

public class PCCProductsImpl {
	private static final Logger LOG = LoggerFactory.getLogger(PCCProductsImpl.class);
	
	@ValueMapValue @Optional
	private String name;
	
	@ValueMapValue @Optional
	private String summary;
	
	@ValueMapValue @Optional
	private String ctaText;
	
	@ValueMapValue @Optional
	private String ctaTarget;
	
	@Inject
	@Optional
	private List<PCCFeatureDetailsImpl> featureDetails;
	
	@Inject @Optional
	private Map<String, String> features;

	
	public String getName() {
		return name;
	}

	public String getSummary() {
		return summary;
	}

	public String getCtaText() {
		return ctaText;
	}

	public String getCtaTarget() {
		return ctaTarget;
	}

	@JsonIgnore
	public List<PCCFeatureDetailsImpl> getFeatureDetails() {
		return featureDetails;
	}
	
	public Map<String, String> getFeatures() {
       List<PCCFeatureDetailsImpl> featureList = getFeatureDetails();
		
       features = featureList.stream().collect(
				Collectors.toMap(
						PCCFeatureDetailsImpl::getFeature_type, PCCFeatureDetailsImpl::getFeatureDescription,
						(oldValue, newValue) -> oldValue,
						LinkedHashMap::new
						));
		return features;
	}
	
	@PostConstruct
    protected void init() {
        LOG.debug("In init of PCC Products Impl");
    }


}
package coe.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

//@Model(adaptables=SlingHttpServletRequest.class)
//public class ClickableImageModel {
//	@Inject
//	String altText;
//	
//	@Inject
//	String link;
//	
//	@Inject
//	String imagePath;
//	
//
//}


@Model(adaptables = SlingHttpServletRequest.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ClickableImageModel {

 @ValueMapValue
private String altText;
 
@ValueMapValue
private String link;

@ValueMapValue
private String linkTarget;

@ValueMapValue
private String fileReference;

public String getAltText() {
	return altText;
}

public String getLink() {
	return link;
}

public String getLinkTarget() {
	return linkTarget;
}

public String getFileReference() {
	return fileReference;
}



}
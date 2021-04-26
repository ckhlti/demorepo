package coe.core.models;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.*;
import javax.inject.Inject;

@Model(adaptables = SlingHttpServletRequest.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)

public class TitleImpl{
	
	@Inject
	@Via("resource")
	String title;
	
	@Inject
	@Via("resource")
	String type;
	
	@ScriptVariable
	Page currentPage;
		

    public String getPageTitle(){
		if(title!=null) 
		{
			return title;
		}
		else 
		{
        return currentPage.getTitle();
        }
    }

	
	public String getTitle() {
	return title;
	}
	
	
	public String getType() {		
	return type;
	}
}

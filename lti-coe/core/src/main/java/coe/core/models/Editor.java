package coe.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.components.Component;
import com.day.cq.wcm.api.components.ComponentManager;

/**
 * Defines an {@code Editor} Sling Model used by the {@code /apps/core/wcm/components/commons/editor/dialog/childreneditor/v1/childreneditor} dialog component.
 *
 * @since com.adobe.cq.wcm.core.components.commons.editor.dialog.childreneditor 1.0.0
 */
@Model(adaptables = {SlingHttpServletRequest.class})
public class Editor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Editor.class);

    @Self
    private SlingHttpServletRequest request;

    private Resource container;

    private List<Item> items;

    @PostConstruct
    private void initModel() {
        readChildren();
    }

    private void readChildren() {
    	
        items = new ArrayList<>();
        String containerPath = request.getRequestPathInfo().getSuffix();
        if (StringUtils.isNotEmpty(containerPath)) {
            ResourceResolver resolver = request.getResourceResolver();
            LOGGER.info("containerPath ---->" +containerPath);
            container = resolver.getResource(containerPath);
            if (container != null) {
            	
                ComponentManager componentManager = request.getResourceResolver().adaptTo(ComponentManager.class);
                Node node = container.adaptTo(Node.class);
                
                for (Resource resource : container.getChildren()) {
                    if (resource != null) {
                        Component component = componentManager.getComponentOfResource(resource);
                        if (component != null) {
                            items.add(new Item(request, resource));
                        }
                    }
                }
                
                if (null != node) {
                	String propertyName = "skipSlide";
                    try {
						if (node.hasProperty(propertyName)) {
						    Property property = node.getProperty(propertyName);
						    if (property.isMultiple()) {
						    	Value[] values = property.getValues();
						    	  LOGGER.info("skip slide count -->" + values.length);
						    	for(Value value : values) {
				                	LOGGER.info("value string-->" + value.toString());
				                	setSkipSlideFlag(value);
				                }
						    }else {
						        Value value = property.getValue();
						        setSkipSlideFlag(value);
						    }
						}
					} catch (PathNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ValueFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RepositoryException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        }
    }

	private void setSkipSlideFlag(Value value) {
		for(Item item : items) {
			if(StringUtils.equalsIgnoreCase(value.toString(), item.name)) {
				item.setSkipSlide(true);
				break;
			}
		}
	}

    /**
     * Retrieves the child items associated with this children editor.
     *
     * @return a list of child items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Retrieves the container resource associated with this children editor.
     *
     * @return the container resource, or {@code null} if no container can be found
     */
    public Resource getContainer() {
        return container;
    }
}

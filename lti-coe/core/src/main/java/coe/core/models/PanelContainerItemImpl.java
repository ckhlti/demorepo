package coe.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.jetbrains.annotations.NotNull;

import com.adobe.cq.wcm.core.components.models.ListItem;
import com.day.cq.commons.jcr.JcrConstants;

public class PanelContainerItemImpl extends ResourceListItemImpl implements ListItem {

   public static final String PN_PANEL_TITLE = "cq:panelTitle";

   public PanelContainerItemImpl(@NotNull SlingHttpServletRequest request, @NotNull Resource resource) {
       super(request, resource);
       ValueMap valueMap = resource.adaptTo(ValueMap.class);
       if (valueMap != null) {
           String jcrTitle = valueMap.get(JcrConstants.JCR_TITLE, String.class);
           title = valueMap.get(PN_PANEL_TITLE, jcrTitle);
       }
   }
}

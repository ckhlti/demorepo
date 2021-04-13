package coe.core.models;

import java.util.Calendar;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.wcm.core.components.models.ListItem;
import com.day.cq.commons.jcr.JcrConstants;

public class ResourceListItemImpl implements ListItem {

   private static final Logger LOGGER = LoggerFactory.getLogger(ResourceListItemImpl.class);

   protected String url;
   protected String title;
   protected String description;
   protected Calendar lastModified;
   protected String path;
   protected String name;

   public ResourceListItemImpl(@NotNull SlingHttpServletRequest request, @NotNull Resource resource) {
       ValueMap valueMap = resource.adaptTo(ValueMap.class);
       if (valueMap != null) {
           title = valueMap.get(JcrConstants.JCR_TITLE, String.class);
           description = valueMap.get(JcrConstants.JCR_DESCRIPTION, String.class);
           lastModified = valueMap.get(JcrConstants.JCR_LASTMODIFIED, Calendar.class);
       }
       path = resource.getPath();
       name = resource.getName();
       url = null;
   }

   @Override
   public String getURL() {
       return url;
   }

   @Override
   public String getTitle() {
       return title;
   }

   @Override
   public String getDescription() {
       return description;
   }

   @Override
   public Calendar getLastModified() {
       return lastModified;
   }

   @Override
   public String getPath() {
       return path;
   }

   @Override
   public String getName() {
       return name;
   }
}

package coe.core.models;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.adobe.cq.wcm.core.components.models.Component;

/**
* Abstract class that can be used as a base class for {@link Component} implementations.
*/
public abstract class AbstractComponentImpl implements Component {

   @SlingObject
   protected Resource resource;

   private String id;

   @Nullable
   @Override
   public String getId() {
       if (id == null) {
           if (resource != null) {
               ValueMap properties = resource.getValueMap();
               id = properties.get(Component.PN_ID, String.class);
           }
       }
       return id;
   }

   @NotNull
   @Override
   public String getExportedType() {
       return resource.getResourceType();
   }
}


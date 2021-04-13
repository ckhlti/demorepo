package coe.core.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.jetbrains.annotations.NotNull;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.wcm.core.components.models.Container;
import com.adobe.cq.wcm.core.components.models.ListItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class PanelContainerImpl extends AbstractContainerImpl implements Container {

   @Override
   protected List<ListItem> readItems() {
       List<ListItem> items = new LinkedList<>();
       getChildren().forEach(res -> {
           items.add(new PanelContainerItemImpl(request, res));
       });
       return items;
   }

   @Override
   protected Map<String, ComponentExporter> getItemModels(@NotNull SlingHttpServletRequest request,
                                                          @NotNull Class<ComponentExporter> modelClass) {
       Map<String, ComponentExporter> models = super.getItemModels(request, modelClass);
       models.entrySet().forEach(entry -> {
           ListItem match = getItems().stream()
               .filter(item -> item != null && StringUtils.isNotEmpty(item.getName()) && StringUtils.equals(item.getName(), entry.getKey()))
               .findFirst().get();
           if (match != null) {
               entry.setValue(new JsonWrapper(entry.getValue(), match));
           }
       });
       return models;
   }

   /**
    * Wrapper class used to add specific properties of the container items to the JSON serialization of the underlying container item model
    *
    */
   static class JsonWrapper implements ComponentExporter {
       private ComponentExporter inner;
       private String panelTitle;

       JsonWrapper(@NotNull ComponentExporter inner, ListItem item) {
           this.inner = inner;
           this.panelTitle = item.getTitle();
       }

       /**
        * @return the underlying container item model
        */
       @JsonUnwrapped
       public ComponentExporter getInner() {
           return inner;
       }

       /**
        * @return the container item title
        */
       @JsonProperty(PanelContainerItemImpl.PN_PANEL_TITLE)
       @JsonInclude(JsonInclude.Include.ALWAYS)
       public String getPanelTitle() {
           return panelTitle;
       }

       @NotNull
       @Override
       public String getExportedType() {
           if (inner != null) {
               return inner.getExportedType();
           } else {
               return "";
           }
       }
   }
}

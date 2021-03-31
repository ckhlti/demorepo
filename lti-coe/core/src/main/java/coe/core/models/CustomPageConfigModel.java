/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package coe.core.models;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import javax.annotation.PostConstruct;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;

@Model(adaptables = SlingHttpServletRequest.class)
public class CustomPageConfigModel {
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomPageConfigModel.class);
	
    @ScriptVariable(name="currentPage")
    private Page page;

    @SlingObject
    Resource resource;
    
    @ValueMapValue
    @Optional
    List<String> headerCSSFiles;
    
    @ValueMapValue
    @Optional
    List<String> headerJSFiles;
    
    @ValueMapValue
    @Optional
    List<String> footerCSSFiles;

    @ValueMapValue
    @Optional
    List<String> footerJSFiles;
    
    List<Map<String, Object>> metaTagList;

    Map<String, Object> metaTagMap;
    
    @PostConstruct
    public void init() {
    	LOG.info("Entered init method of Custom Page Config Model");
    	Node node = resource.adaptTo(Node.class);

        try {
        	metaTagList = new ArrayList<>();

            if (node.hasNode("metatags")) {
                
            	NodeIterator metaTagsIterator = node.getNode("metatags").getNodes();
                
                while (metaTagsIterator.hasNext()) {
                    Node metaTagNode = metaTagsIterator.nextNode();
                    metaTagMap = new HashMap<>();
                    
                    if ((metaTagNode.hasProperty("metaName"))) {
                    	metaTagMap.put("metaName", metaTagNode.getProperty("metaName").getString().trim());
                    }

                    if ((metaTagNode.hasProperty("metaValue"))) {
                    	metaTagMap.put("metaValue", metaTagNode.getProperty("metaValue").getString().trim());
                    }
                    
                    metaTagList.add(metaTagMap);
                }
            }

        } catch (RepositoryException e) {

            LOG.info("Exception in  init {} ", e);

        }
        LOG.info("metaTagList size" + metaTagList.size());
    }
    
    public String getSiteClientLibName() {
    	LOG.info("CLientLibName -->" + page.getPath().split("/")[2]);
    	return page.getPath().split("/")[2];
    }
    
    public List<String> getHeaderCSSFiles() {
       return headerCSSFiles;
    }
    
    public List<String> getHeaderJSFiles() {
    	return headerJSFiles;
    }
    
    public List<String> getFooterCSSFiles() {
    	return footerCSSFiles;
    }
    
    public List<String> getFooterJSFiles() {
    	return footerJSFiles;
    }   
    
    public List<Map<String, Object>> getMetaTagList() {
		return metaTagList;
	}

	public void setMetaTagList(List<Map<String, Object>> metaTagList) {
		this.metaTagList = metaTagList;
	}
}

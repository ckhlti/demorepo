package coe.core.config;


import java.util.Dictionary;
import java.util.List;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;
import coe.core.utils.GlobalUtil;

/**
 * Implementation of ConfigService provides OsgiConfig parameters.
 */
@Service(value = { GlobalConfig.class })

@Component(immediate = true, metatype = false)

public class GlobalConfig {

    private Dictionary<String, Object> properties;

    protected void activate(ComponentContext context) {
        properties = context.getProperties();
    }

    /**
     * Sets property as null
     * 
     * @param context
     */
    protected void deactivate(ComponentContext context) {
        properties = null;
    }

    /**
     * Return key value
     * 
     * @param key
     * @return String
     */
    public String getPropertyValue(String key) {
        return GlobalUtil.getPropertyValue(key,properties);
    }

    /**
     * Returns values as ArrayList for given key
     * 
     * @param key
     * @return ArrayList
     */
    public List<String> getPropertyArray(String key) {
        return GlobalUtil.getPropertyArray(key,properties);
    }
}
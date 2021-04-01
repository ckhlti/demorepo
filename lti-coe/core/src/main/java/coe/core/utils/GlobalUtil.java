package coe.core.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.apache.sling.settings.SlingSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import coe.core.config.GlobalConfig;
import coe.core.constants.GlobalConstants;

public class GlobalUtil {

	private GlobalUtil() {
		// hiding the default constructor
	}

	private static final Logger logger = LoggerFactory.getLogger(GlobalUtil.class.getName());

	/**
	 * This method returns an unique hash id to its consumer.
	 * 
	 * @param prefix  The prefix of generated id.
	 * @param postfix The actual input string for hash calculation.
	 * @return String generated hash Id.
	 */
	public static String generateUniqueHashId(String prefix, String postfix) {

		String generatedId = StringUtils.EMPTY;

		if (StringUtils.isNotEmpty(postfix) && StringUtils.isNotEmpty(prefix)) {

			generatedId = StringUtils.join(prefix, GlobalConstants.HYPHEN_DELIMITTER,
					String.valueOf(Math.abs(postfix.hashCode() - 1)));

			logger.debug("Generated Name Returned From $GlobalUtil#generateUniqueHashId :: {}", generatedId);

		}

		return generatedId;

	}

	public static String generateUniqueHashIdDLL(String prefix, String postfix) {

		String generatedId = StringUtils.EMPTY;

		if (StringUtils.isNotEmpty(postfix) && StringUtils.isNotEmpty(prefix)) {

			generatedId = StringUtils.join(prefix, String.valueOf(Math.abs(postfix.hashCode() - 1)));

			logger.debug("Generated Name Returned From $GlobalUtil#generateUniqueHashId :: {}", generatedId);

		}

		return generatedId;

	}

	public static void customPropertyWriter(ResourceResolver resolver, String reseourcePath,
			Map<String, String> customProperty) throws PersistenceException {

		if (resolver != null && customProperty != null) {

			Resource resource = resolver.getResource(reseourcePath);

			if (resource != null) {

				ModifiableValueMap map = resource.adaptTo(ModifiableValueMap.class);

				for (Map.Entry<String, String> entry : customProperty.entrySet()) {
					if (map != null) {

						map.put(entry.getKey(), entry.getValue());

					}

				}

				resource.getResourceResolver().commit();
			}
		}

	}

	public static boolean isAssetPath(String path) {

		if (StringUtils.isNotEmpty(path) && StringUtils.contains(path, GlobalConstants.DAM_PATH)) {

			return Boolean.TRUE;

		} else {

			return Boolean.FALSE;

		}

	}

	public static Boolean isContentPath(String path) {

		if (StringUtils.isNotEmpty(path) && !StringUtils.contains(path, GlobalConstants.XF_PATH)
				&& !StringUtils.contains(path, GlobalConstants.DAM_PATH)) {

			return Boolean.TRUE;

		} else {

			return Boolean.FALSE;

		}

	}

	public static String getApplicationName(String requestPath) {

		String[] requestSplit = requestPath.split(GlobalConstants.FORWARD_SLASH_DELIMITTER);

		if (requestSplit != null && requestSplit.length >= 3) {

			String applicationName = requestSplit[2];

			if (applicationName.contains(".html")) {

				applicationName = applicationName.replace(".html", "");

			}

			return applicationName;

		}

		return StringUtils.EMPTY;
	}
	   public static String getPropertyValue(String key,Dictionary<String, Object> properties) {
	        if (null != properties) {
	            return PropertiesUtil.toString(properties.get(key), "");
	        }
	        return "";
	    }

	    public static ArrayList<String> getPropertyArray(String key,Dictionary<String, Object> properties) {
	        ArrayList<String> arrLst = new ArrayList<String>();
	        if (null != properties) {
	            String[] value = PropertiesUtil.toStringArray(properties.get(key));
	            if (value != null) {
	                Collections.addAll(arrLst, value);
	            }
	        }
	        return arrLst;
	    }
	/*	public static String getSiteRootContextpath(Page currentPage, GlobalConfig globalConfig) {
			if(currentPage != null && null != globalConfig){
			//Get the site name from page path. If its null, default to truist
			String siteName = currentPage.getAbsoluteParent(1).getName()!=null?currentPage.getAbsoluteParent(1).getName():GlobalConstants.DEFAULT_APPLICATION;
			//Get the root path from property that needs to be truncated
			String siteRootContextPath = globalConfig.getPropertyValue(siteName+GlobalConstants.ROOT_CONTEXT_SUFFIX);
			return siteRootContextPath;
			}
			return null;
		}
		
		*/
		
		
		
	    public static String getSiteRootContextpath(Page currentPage,GlobalConfig globalConfig) {
			String siteRootContextPath = null;
			if (currentPage != null && null != globalConfig) {
				if(null != currentPage.getAbsoluteParent(1)){
					// Get the site name from page path. If its null, default to truist
					logger.info("AbsoluteParent: " + currentPage.getAbsoluteParent(1));
					String siteName = currentPage.getAbsoluteParent(1).getName() != null ? currentPage.getAbsoluteParent(1).getName(): GlobalConstants.DEFAULT_APPLICATION;
					logger.info("siteName: " + currentPage.getAbsoluteParent(1).getName());
					// Get the root path from property that needs to be truncated
					logger.info("globalConfig.getPropertyValue(siteName + GlobalConstants.ROOT_CONTEXT_SUFFIX: " + globalConfig.getPropertyValue(siteName + GlobalConstants.ROOT_CONTEXT_SUFFIX));
					siteRootContextPath = globalConfig.getPropertyValue(siteName + GlobalConstants.ROOT_CONTEXT_SUFFIX);
				}else{
					logger.info("globalConfig.getPropertyValue(GlobalConstants.DEFAULT_APPLICATION + GlobalConstants.ROOT_CONTEXT_SUFFIX: " + globalConfig.getPropertyValue(GlobalConstants.DEFAULT_APPLICATION + GlobalConstants.ROOT_CONTEXT_SUFFIX));
					siteRootContextPath = globalConfig.getPropertyValue(GlobalConstants.DEFAULT_APPLICATION + GlobalConstants.ROOT_CONTEXT_SUFFIX);
				}
			} 
			logger.info("siteRootContextPath: " + siteRootContextPath);
			return siteRootContextPath;
		}

		
		
		public static String  getUrlLink(SlingSettingsService slingSettingsService, String siteRootPath, String pagePath ){
			String shortenPath;
			if(slingSettingsService.getRunModes().contains(GlobalConstants.PUBLISH_INSTANCE)){
				shortenPath=pagePath.contains(siteRootPath)?pagePath.replace(siteRootPath,""):pagePath;
				
			}else{
				shortenPath=pagePath.contains(siteRootPath)? pagePath.concat(GlobalConstants.HTML_EXTENSION):pagePath;	
			}
			logger.info("Short URL: "+shortenPath);
			return shortenPath;
			
	}
}

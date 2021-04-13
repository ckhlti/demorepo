package coe.core.models;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ContainerExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.models.Carousel;
import com.adobe.cq.wcm.core.components.models.ListItem;
import coe.core.models.PanelContainerImpl;
import coe.core.utils.GlobalUtil;

@Model(adaptables = SlingHttpServletRequest.class, adapters = { Carousel.class, ComponentExporter.class,
		ContainerExporter.class }, resourceType = CustomCarousel.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CustomCarousel extends PanelContainerImpl implements Carousel {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomCarousel.class);

	public static final String RESOURCE_TYPE = "/coe/components/core/custom-carousel/v1/custom-carousel";
	protected static final Long DEFAULT_DELAY = 5000L; // milliseconds

	@Optional
	@ValueMapValue
	private String componentId;

	@ScriptVariable
	protected ValueMap properties;

	@ValueMapValue(optional = true)
	protected String accessibilityLabel;

	protected boolean autoplay;
	protected Long delay;
	protected boolean autopauseDisabled;

	@Optional
	@ValueMapValue
	protected List<String> skipSlide;

	@Optional
	@ValueMapValue
	@Default(values = "carousel-type-one") 
	private String carouselType;

	@PostConstruct
	protected void initModel() {
		autoplay = properties.get(PN_AUTOPLAY, currentStyle.get(PN_AUTOPLAY, false));
		delay = properties.get(PN_DELAY, currentStyle.get(PN_DELAY, DEFAULT_DELAY));
		autopauseDisabled = properties.get(PN_AUTOPAUSE_DISABLED, currentStyle.get(PN_AUTOPAUSE_DISABLED, false));
	}

	@Override
	public boolean getAutoplay() {
		return autoplay;
	}

	@Override
	public Long getDelay() {
		return delay;
	}

	@Override
	public boolean getAutopauseDisabled() {
		return autopauseDisabled;
	}

	@Override
	protected List<ListItem> readItems() {
		LOGGER.info("readItems method of CarouselImpl");
		List<ListItem> items = super.readItems();
		List<String> skipItems = getSkipSlide();
		if (skipItems != null) {
			LOGGER.info("skipItems size--->" + skipItems.size());

			for (String skipItem : skipItems) {
				LOGGER.info("skipItem--->" + skipItem);
				for (int i = 0; i < items.size(); i++) {
					if (StringUtils.equalsIgnoreCase(skipItem, items.get(i).getName())) {
						items.remove(i);
						break;
					}
				}
			}
		}
		return items;
	}

	@PostConstruct
	protected void init() {

		if (StringUtils.isEmpty(componentId) && resource != null) {

			componentId = GlobalUtil.generateUniqueHashId("custom-carousel", resource.getPath()); // Generate unique
																									// component Id

			LOGGER.debug("componentId generated :: path:: componentId {} , {}", resource.getPath(), componentId);

			if (StringUtils.isNotEmpty(componentId)) {

				try {

					Map<String, String> propertyMap = new HashMap<>();

					propertyMap.put("componentId", componentId);

					GlobalUtil.customPropertyWriter(resource.getResourceResolver(), resource.getPath(), propertyMap);

				} catch (PersistenceException ex) {

					LOGGER.error("Exception occured while Saving value in the node :: {}", ex);

				}
			} else {

				LOGGER.error("Unique Id Can not be Written CustomCarousel Model ");

			}
		} else {
			LOGGER.info("Component id " + componentId);
			LOGGER.info("resource - " + resource.getPath().toString());
			LOGGER.info("Component id not generated");
		}

	}

	public String getComponentId() {
		return componentId;
	}

	public String getCarouselType() {
		return carouselType;
	}

	@Override
	public String getAccessibilityLabel() {
		return accessibilityLabel;
	}

	public List<String> getSkipSlide() {
		return skipSlide;
	}
}

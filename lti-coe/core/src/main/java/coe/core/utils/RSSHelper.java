package coe.core.utils;

import coe.core.models.RSSItem;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RSSHelper {
	private static final Logger LOG = LoggerFactory.getLogger(RSSHelper.class);
	public static List<RSSItem> read(String url){

		List<RSSItem> listItems = new ArrayList<RSSItem>();
		try {

			// to obtain a parser that produces DOM object trees from XML documents.
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(url);
			//XPath stands for XML Path Language
			//XPath uses "path like" syntax to identify and navigate nodes in an XML document
			XPath xpPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xpPath.compile("//item").evaluate(document, XPathConstants.NODESET);
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				RSSItem item = new RSSItem();
				item.setCategory(xpPath.compile("./category").evaluate(nodeList.item(i), XPathConstants.STRING).toString());
				item.setDescription(xpPath.compile("./description").evaluate(nodeList.item(i), XPathConstants.STRING).toString());
				item.setGuid(xpPath.compile("./guid").evaluate(nodeList.item(i), XPathConstants.STRING).toString());
				item.setLink(xpPath.compile("./link").evaluate(nodeList.item(i), XPathConstants.STRING).toString());
				item.setPubDate(xpPath.compile("./pubDate").evaluate(nodeList.item(i), XPathConstants.STRING).toString());
				item.setTitle(xpPath.compile("./title").evaluate(nodeList.item(i), XPathConstants.STRING).toString());
//				System.out.println();
				listItems.add(item);
			}
		} catch (UnsupportedEncodingException|UnsupportedDataTypeException|RuntimeException e) {
			listItems = null;
			LOG.debug("error in parsing RSS - " + e.getMessage());
		}

		 
//		 System.out.println(listItems.get(1).getTitle());
//		 System.out.println( Arrays.toString(listItems.get(0).toArray()) );
//		System.out.println(listItems.item(1));
//		System.out.println(listItems);
		return listItems;
	}
}

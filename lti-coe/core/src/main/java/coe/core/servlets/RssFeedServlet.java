package coe.core.servlets;

import coe.core.models.RSSItem;
import coe.core.utils.RSSHelper;
import com.day.cq.commons.jcr.JcrConstants;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// @Component(
// 		service=Servlet.class,
// 		property={
// 				"sling.servlet.paths="+ "/bin/rssapp/rssfeed"
// 		}
// )

@Component(service = { Servlet.class })
@SlingServletResourceTypes(
        resourceTypes="coe/components/rss",
        methods=HttpConstants.METHOD_GET,
        extensions="txt")
@ServiceDescription("RSS Feed Servlet")
public class RssFeedServlet extends SlingSafeMethodsServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(RssFeedServlet.class);
	@Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
		
		LOG.debug("initiating rSS Feed servlet");
//		String rssurl = request.getParameter("rssurl");
		String url = request.getParameter("rssurl");
		LOG.debug("RSS URL - " + url);
//		PrintWriter out = response.getWriter();
		//response.setHeader("Content-Type", "text/html");
		List<RSSItem> alist = RSSHelper.read(url);
		String json = new Gson().toJson(alist);
		LOG.debug("JSON - " + json);
//		response.setStatus(200);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
		    
//		for (int i = 0; i < alist.size(); i++) { 
//			response.getWriter().print("<p> <b>Title : </b>"+ alist.get(i).getTitle()+"</p>");
//			response.getWriter().print("<p> <b>Date : </b>"+ alist.get(i).getPubDate()+"</p>");
//			response.getWriter().print("<p> <b>Link : </b>"+ alist.get(i).getLink()+"</p>");
//			response.getWriter().print("<p> <b>Description : </b> "+ alist.get(i).getDescription()+"</p>");
//			response.getWriter().print("<hr>");
//
//	    } 
//		response.setHeader("Content-Type", "text/html");
//    	response.getWriter().print("<h1>rss Servlet Called</h1>"); 
//		response.getWriter().close();

    }   

}
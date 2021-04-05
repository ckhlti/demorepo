package coe.core.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import coe.core.models.RSSItem;
import coe.core.utils.RSSHelper;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
		service=Servlet.class,
		property={
				"sling.servlet.paths="+ "/bin/rssapp/rssfeed"
		}
)
public class RssFeedServlet extends SlingSafeMethodsServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(RssFeedServlet.class);
	@Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
		
		
//		String rssurl = request.getParameter("rssurl");
		String url = request.getParameter("rssurl");
//		PrintWriter out = response.getWriter();
		//response.setHeader("Content-Type", "text/html");
		List<RSSItem> alist = RSSHelper.read(url);
		String json = new Gson().toJson(alist);

//		response.setStatus(200);
		response.setContentType("application/json");
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
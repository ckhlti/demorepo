package com.lti.aem.core.servlets;
//@SlingServlet(paths="/bin/trainingproject/testservlet")  --Felix SCR Annotation

import java.io.IOException; 

//import javax.jcr.Repository;
import javax.jcr.Session;
//import javax.jcr.SimpleCredentials;
import javax.jcr.Node;

//import org.apache.jackrabbit.commons.JcrUtils;
//import org.apache.jackrabbit.core.TransientRepository;

//import java.io.IOException; 

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
//import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lti.aem.core.util.AES;

//OSGI Declarative Service Annotation
@Component(
		service=Servlet.class,
		property={
				"sling.servlet.paths="+ "/bin/demo/encryform"
		}
)

public class EncryptedForm extends SlingSafeMethodsServlet {
	private static final Logger log = LoggerFactory.getLogger(EncryptedForm.class);
	private static final long serialVersionUID = 1L;

	@Override
protected void doGet(SlingHttpServletRequest request, 
		  SlingHttpServletResponse response) 
		  throws ServletException, IOException {
	response.setHeader("Content-Type", "text/html");
	//response.getWriter().print("<h1>Encrypt form Sling Servlet Called</h1>");
	log.info("start");
	String id = request.getParameter("id");
	String firstName = request.getParameter("firstname");
	String lastName = request.getParameter("lastname");
	String dob = request.getParameter("dob");
	String gender = request.getParameter("gender");
	String mob = request.getParameter("mob");
	String email = request.getParameter("email");
	String pan = request.getParameter("pan");
	String aadhar = request.getParameter("aadhar");
	String address = request.getParameter("address");
	String city = request.getParameter("city");
	String state = request.getParameter("state");
	
	final String secretKey = "ssshhhhhhhhhhh!!!!"; 
	

    String encryptedPan=AES.encrypt(pan, secretKey) ;
    String encryptedAdhar=AES.encrypt(aadhar, secretKey) ;
    String encryptedMob=AES.encrypt(mob, secretKey) ;
    
	//response.getWriter().print("<br/><br/> first name is--"+firstName +"<br/>");
	
	try {
	//	response.getWriter().print("getting resource resolver     ");
		ResourceResolver resourceResolver = request.getResourceResolver();
		
		//Resource resource = request.getResourceResolver().getResource("/");
		//Session session = resource.adaptTo(Session.class);
		//response.getWriter().print("getting session     ");
		Session session = resourceResolver.adaptTo(Session.class);
		
		//response.getWriter().print("getting root node     ");
		Node root = session.getRootNode();
		
		//response.getWriter().print("adding new node      ");
		
		String rootpath = root.getPath();
		Node newNode =  null;
		
		//response.getWriter().print("root path is   ->  "+rootpath+"<--");
		if(!session.nodeExists("/FormInfo"))
		{
			response.getWriter().print("adding Form info node      ");
			newNode = root.addNode("FormInfo");
		}else
		{
			newNode = root.getNode("FormInfo");
		}
		
		
		
		
		Node newUser = newNode.addNode("user-"+id);
		Node Duser = newNode.addNode("Duser-"+id);
		
		response.getWriter().print("<h2> data saved successfully ! </h2><br>  ");
		
		
		newUser.setProperty("firstName", firstName);
		newUser.setProperty("lastName", lastName);
		newUser.setProperty("dob", dob);
		newUser.setProperty("gender", gender);
		//newUser.setProperty("mob", mob);
		newUser.setProperty("email", email);
		//newUser.setProperty("pan", pan);
		//newUser.setProperty("aadhar", aadhar);
		newUser.setProperty("address", address);
		newUser.setProperty("city", city);
		newUser.setProperty("state", state);
		
		 newUser.setProperty("encrytedpan", encryptedPan);
         newUser.setProperty("encrytedaadhar", encryptedAdhar);
         newUser.setProperty("encrytedmob", encryptedMob);
		
         String decryptedPan=AES.decrypt(encryptedPan, secretKey) ;
         String decryptedAdhar=AES.decrypt(encryptedAdhar, secretKey) ;
         String decryptedMob=AES.decrypt(encryptedMob, secretKey) ;
         
         Duser.setProperty("decrytedpan", decryptedPan);
         Duser.setProperty("decrytedaadhar", decryptedAdhar);
         Duser.setProperty("decrytedmob", decryptedMob);
		
		
	//	 response.getWriter().print(session.toString());
	//	 Node root = session.getNode(resource.getPath());
	//	 Node adobe = root.addNode("adobe4");
	//	 response.getWriter().print("start 1..0");
//	  //Create a connection to the CQ repository running on local host
//	  Repository repository = JcrUtils.getRepository
//			  ("http://localhost:4502/crx/server");
//	  response.getWriter().print("first step clear");
//	   //Create a Session
//	  javax.jcr.Session session = repository.login( 
//			  new SimpleCredentials("admin", "admin".toCharArray()));

	  //Create a node that represents the root node
		
//	  Node root = session.getRootNode();
		 
	//  response.getWriter().print("start 2..0");
	  // Store content
	//  Node adobe = root.addNode("adobe4");
	//  Node day = adobe.addNode("day");
	//  day.setProperty("message", "Adobe CQ is part"
	 // 		+ " of the Adobe Digital Marketing Suite!");

	  // Retrieve content
//	  Node node = root.getNode("adobe/day");
//	  System.out.println(node.getPath());
//	  System.out.println(node.getProperty("message").getString());

	  // Save the session changes and log out
	  session.save();
	  session.logout();
	  }
	 catch(Exception e){
		 
	  e.printStackTrace();
	  response.getWriter().print("error "+e.toString()+e.getStackTrace());
	  System.out.println("not working");
	  }
	
//	response.getWriter().print("first name :"+request.getParameter("fname")+
//			"last name :"+request.getParameter("lname"));
	response.getWriter().close();
}   
}
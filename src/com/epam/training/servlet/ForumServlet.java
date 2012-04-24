package com.epam.training.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ForumServlet
 */
public class ForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForumServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    Map<String,String> getForum() {
        Map<String,String> messages = (Map<String,String>) getServletContext().getAttribute("messages");
        if (messages == null) {
            System.out.println("creating new FORUM");
            messages = new ConcurrentHashMap<String,String>();
            getServletContext().setAttribute("messages", messages);
        } else {
            System.out.println("FORUM found num of messages:" + messages.keySet().size());
        }
        
        return messages;
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {    
        generateMessageListHtml(response);
	}

    private void generateMessageListHtml(HttpServletResponse response)
            throws IOException {
        response.getWriter().println("<div>");
            Map<String, String> forum = getForum();
            
            for (String nextKey : forum.keySet()) {
                String nextMessage = forum.get(nextKey);
                response.getWriter().println("<div style='background:#aaa; margin:10px; border:1px solid black;'><span style='background:#ccc'>" 
                + nextKey + " : </span>" + nextMessage + " </div>");
            }
          response.getWriter().println("</div>");
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = (String) request.getSession().getAttribute("username");
		String message = request.getParameter("message").replaceAll("<", "&lt;");
		
		if (user == null || user.trim().length() < 1) {
	        response.getWriter().println("<div>");
		    response.getWriter().println("you are not logged in !!!");
	        response.getWriter().println("</div>");
		} else {
		    Map<String, String> forum = getForum();
		    String timestamp = sdf.format(new Date());
		    forum.put(timestamp + "===" + user, message);
		    
		    generateMessageListHtml(response);
		}

	}

}

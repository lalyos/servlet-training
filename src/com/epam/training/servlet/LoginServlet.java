package com.epam.training.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        response.getWriter().println("<div>");
        if (password.equalsIgnoreCase("secret" + user)) {
            request.getSession().setAttribute("username", user);
            response.getWriter().println("login succes: " + user);

        } else {
            response.getWriter().println("login failed");            
        }
        response.getWriter().println("<a href=\"/\" >home</a>");
        response.getWriter().println("</div>");

	}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException {
        
        String host = "mekbuk.epam.com";
        response.getWriter().println("<div>");
        response.getWriter().println("<a href=\"https://www.facebook.com/dialog/oauth?client_id=229865077115394&redirect_uri=http://" + host +":8080/fbauth&scope=&state=abc123\">Log in via Facebook</a>");
        response.getWriter().println("</div>");

    }

}

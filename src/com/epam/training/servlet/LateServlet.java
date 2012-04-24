package com.epam.training.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LateServlet
 */
public class LateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Map<String, Integer> hallOfFame = new ConcurrentHashMap<String, Integer>(); 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        int minutes = 0;
        try {
            minutes = Integer.parseInt(request.getParameter("minutes"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        
        Integer prevValue = hallOfFame.get(name);
        if (prevValue!= null ){
            prevValue += minutes;
        } else {
            prevValue = minutes;
        }
        hallOfFame.put(name, prevValue);
        
        response.getWriter().println("<TABLE border=\"1\"><tr><th>Name</th><th>Minutes<th></tr>");
		for (String nextName : hallOfFame.keySet()) {
            Integer nextMinutes = hallOfFame.get(nextName);
            response.getWriter().println("<tr><td>" + nextName + "</td><td>" + nextMinutes + "</td></tr>");
        }
        response.getWriter().println("</TABLE>");
	}

}

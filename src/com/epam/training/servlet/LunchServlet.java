package com.epam.training.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LunchServlet
 */
public class LunchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy.MM.dd HH:mm");

    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            long nowTime = new Date().getTime();
            long lunchTime = sdf.parse(getInitParameter("lunchtimepattern")).getTime();
            String message = getInitParameter("message");
            
            long minutes = (lunchTime - nowTime) / 60000;
            response.getWriter()
                    .println(
                            "<HTML><HEAD><meta http-equiv=\"refresh\" content=\"1\"></HEAD>");
            response.getWriter().println("<BODY>");
            if (minutes > 0) {
                long seconds = ((lunchTime - nowTime) / 1000) % 60;
                response.getWriter().println(
                        "<h1>" + message + minutes + ":"
                                + seconds + "</h1>");
                response.getWriter()
                        .println(
                                "<h2>If you are late you have to perform 10 pushups per minute!!!</h2>");
            } else {
                minutes *= -1;
                long seconds = ((nowTime - lunchTime) / 1000) % 60;

                response.getWriter().println(
                        "<h1>The lesson started  " + minutes + ":" + seconds
                                + " ago</h1>");
                response.getWriter()
                        .println(
                                "<h2>You are late you have to perform 10 pushups per minute!!!</h2>");
                response.getWriter()
                        .println(
                                "<h1>Please perform: " + minutes * 10
                                        + " pushups</h1>");
            }
            response.getWriter().println("</body></html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

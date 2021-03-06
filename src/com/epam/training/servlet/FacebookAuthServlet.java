package com.epam.training.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class FacebookAuthServlet
 */
public class FacebookAuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FacebookAuthServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String host = "mekbuk.epam.com";
	    response.getWriter().println("<div>");
	    String code = request.getParameter("code");
	    System.out.println("### [step-1] code received from FB:" + code );
		if(code != null && code.length() > 1) {
		    String fbResp1 = getUrlResponse("https://graph.facebook.com/oauth/access_token?client_id=229865077115394&redirect_uri=http://" + host + ":8080/fbauth&client_secret=ead2f07de710df681e60428b027cc382&code=" + code);
		    String fbtoken = fbResp1.substring(13, fbResp1.indexOf("&expires"));

		    System.out.println("### [step-2] full response:" + fbResp1);
		    System.out.println("### [step-2] fbtoken:" + fbtoken);

		    request.getSession().setAttribute("fbtoken", fbtoken);

		    String json = getUrlResponse("https://graph.facebook.com/me?access_token=" + fbtoken);
		    try {
                JSONObject jsonObj = new JSONObject(json);
                String username = jsonObj.getString("name");
                String fbId = jsonObj.getString("id");
                request.getSession().setAttribute("username", username);
                request.getSession().setAttribute("fbid", fbId);
                response.getWriter().println("<h1>Welcome " + username + "</h1>");
                response.getWriter().println("<img src='http://graph.facebook.com/" + fbId + "/picture' />");

            } catch (JSONException e) {
                e.printStackTrace();
                response.getWriter().println("JSON error: " + e.getMessage());
            }
		}
	    response.getWriter().println("</div>");

	}

    private String getUrlResponse(String url) throws MalformedURLException,
            IOException {
        URL fbUrl = new URL(url);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fbUrl.openStream()));

        String resp = "";
        String nextLine;
        while ((nextLine = reader.readLine()) != null) {
            resp += nextLine;
        }
        
        return resp;
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

package com.whoami;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class hg
 */
@WebServlet("/hg")
public class hg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public hg() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String code = request.getParameter("code");
	        try {
	            URL url = new URL("https://openapi.baidu.com/social/api/2.0/user/info?access_token=" + code);
	            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	            connection.setDoInput(true);
	            connection.setDoOutput(true);
	            connection.setRequestMethod("GET");
	            connection.setUseCaches(false);
	            connection.setInstanceFollowRedirects(true);
	            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	            connection.connect();
	            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
	            JSONObject obj = new JSONObject();
	             
	            String token = "d5f224c9f83874da5b5025794c773e8e";
	            obj.put("token", token);
	            out.writeBytes(obj.toString());
	            out.flush();
	            out.close();
	             
	            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            String lines;
	            StringBuffer sbf = new StringBuffer();
	             while ((lines = reader.readLine()) != null) {
	                    lines = new String(lines.getBytes(), "utf-8");
	                    sbf.append(lines);
	                }
	                System.out.println(sbf);
	                reader.close();
	                // 断开连接
	                connection.disconnect();
	        } catch (MalformedURLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

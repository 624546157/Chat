package com.whoami;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ManageData md = new ManageData();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			HttpSession session =request.getSession();
			ServletContext application = request.getSession().getServletContext();
			PrintWriter out =response.getWriter();
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			if(md.userLogin(username, password, session, application)){
				out.print("success");
			}else{
				out.print("false");
			}
			
			
			
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

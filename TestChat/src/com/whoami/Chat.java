package com.whoami;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Chat extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ManageData md =new ManageData();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session =request.getSession();
		ServletContext application = request.getSession().getServletContext();
		PrintWriter out =response.getWriter();
		String action = request.getParameter("action");
		if("getOnlineUser".equals(action)){
			out.print(md.GetUserOnLine(application));
		}
		if("sendMessage".equals(action)){
			String strMess =request.getParameter("strMess");
			out.print(md.addMessage(strMess, session, application));
		}
		if("getMessageList".equals(action)){
			out.print(md.GetMessageList(application));
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

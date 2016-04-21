package com.whoami;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class ManageData {
	
	
		public boolean userLogin(String username,String password,HttpSession session,ServletContext application){
			
			Properties pro = new Properties();	
			try {
				File file = new File("C:\\Users\\ZhangYong\\workspace\\TestChat\\User.properties");
				pro.load(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (pro.containsKey(username)) {
				if(pro.getProperty(username).equals(password)){
					
					session.setAttribute("username", username);
					@SuppressWarnings("unchecked")
					List<String> users  =(List<String>)application.getAttribute("users");
					
					if(users==null){
						users = new ArrayList<String>();
					}
					users.add(username);
					application.setAttribute("users", users);
					return true;
				}else{
					return false;
					}	
			}else{
				return false;
			}
			
			
		}
		@SuppressWarnings("unchecked")
		public String addMessage(String message,HttpSession session,ServletContext application){
			List<String> strMess = (List<String>)application.getAttribute("messList");
			if(strMess ==null){
				strMess = new ArrayList<String>();
			}
			String username = (String) session.getAttribute("username");
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
			Date date =new Date();
			message=message.replace("<:", "<img src='image/");
			message=message.replace(":>", ".gif' />"); 
			String messages = username+" at "+formatter.format(date)+" Say: "+message;
			strMess.add(messages);
			application.setAttribute("messList", strMess);
			return "true";
			
		}
		//获取在线信息
		public String  GetUserOnLine(ServletContext application){
			StringBuffer sb = new StringBuffer();
			@SuppressWarnings("unchecked")
			List<String> list= (List<String>)application.getAttribute("users");
			if(list!=null){
				for (String string : list) {
					sb.append(string+"<br/>");
				}
			}
			return sb.toString();
			
		}
		//获取信息
		public String GetMessageList(ServletContext application){
			StringBuffer sb =new StringBuffer();
			@SuppressWarnings("unchecked")
			List<String> messList =(List<String>) application.getAttribute("messList");
			if(messList!=null){
				for (String string : messList) {
					sb.append(string+"<br />");
				}
			}
			return sb.toString();
		}
}

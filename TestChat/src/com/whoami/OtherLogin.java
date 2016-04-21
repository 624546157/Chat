package com.whoami;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class OtherLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 获取URL的链接
	HttpURLConnection getConnection(URL url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestMethod("GET");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.connect();
		return connection;
	}

	// 构建URL
	String genParameter(Map<String, String> map) throws UnsupportedEncodingException {
		String body = "";
		boolean first = true;
		for (String param : map.keySet()) {
			if (first) {
				first = false;
			} else {
				body += "&";
			}
			String value = map.get(param);
			body += URLEncoder.encode(param, "UTF-8") + "=";
			body += URLEncoder.encode(value, "UTF-8");
		}
		return body;
	}

	// 获取access_token的值
	void putParameter(HttpURLConnection conn, Map<String, String> map) throws IOException {
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(genParameter(map).getBytes());
		out.flush();
		out.close();
	}

	// 获取请求URL的返回值
	String getResult(HttpURLConnection conn) throws UnsupportedEncodingException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String lines;
		StringBuffer sbf = new StringBuffer();
		while ((lines = reader.readLine()) != null) {
			lines = new String(lines.getBytes(), "utf-8");
			sbf.append(lines);
		}
		reader.close();
		conn.disconnect();
		return sbf.toString();
	}

	// 获取
	String getAuthorizationCode(String code, String url_get_access_token) throws IOException {
		URL url = new URL(url_get_access_token);
		Map<String, String> map = new HashMap<String, String>();
		map.put("grant_type", "authorization_code");
		map.put("code", code);
		map.put("client_id", "cSGQQt5Wi6tUcs9zsdeEuP8k");
		map.put("client_secret", "sPksvdvAQCE8T11uOknjms5zOYhwYHYS");
		map.put("redirect_uri", "http://127.0.0.1:8080/TestChat/otherLogin");
		HttpURLConnection conn = getConnection(url);
		putParameter(conn, map);
		String json = getResult(conn);
		return JSONObject.fromObject(json).get("access_token").toString();
	}

	// 获取用户信息
	String getUserInfo(String access_token, String url_get_userInfo) throws IOException {
		URL url = new URL(url_get_userInfo);
		Map<String, String> map = new HashMap<String, String>();
		map.put("access_token", access_token);

		HttpURLConnection conn = getConnection(url);
		putParameter(conn, map);
		return getResult(conn);
	}

	// 获取详细信息，拆分json
	String getUserInfo(String access_token, String url_get_userInfo,String key) throws IOException{
		
		URL url = new URL(url_get_userInfo);
		Map<String, String> map = new HashMap<String, String>();
		map.put("access_token", access_token);

		HttpURLConnection conn = getConnection(url);
		putParameter(conn, map);
		return JSONObject.fromObject(getResult(conn)).get(key).toString();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String code = request.getParameter("code");
		String url_get_access_token = "https://openapi.baidu.com/social/oauth/2.0/token?";
		String access_token = getAuthorizationCode(code, url_get_access_token);
		String url_get_userInfo = "https://openapi.baidu.com/social/api/2.0/user/info?";
		out.print(getUserInfo(access_token, url_get_userInfo,"username"));
		// response.sendRedirect("http://localhost:8080/TestChat/index.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

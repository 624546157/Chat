package com.whoami;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class test {
public static void main(String[] args) {

	Properties pro = new Properties();
	File file =new File("user/User.properties");
	try {
		pro.load(new FileInputStream(file));
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	System.out.println(pro.getProperty("whoami"));
}
}

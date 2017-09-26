package com.factory.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;


public class WebUtil {
	private WebUtil(){}
	
	
    public static void AJAXMsg(String msg){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain");//文本
		response.setHeader("Cache-Control", "no-store");
		response.setCharacterEncoding("utf-8");
		PrintWriter pw=null;
		try {
			pw=response.getWriter();
			pw.write(msg);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(pw!=null){
				pw.close();
			}
		}
    }
}

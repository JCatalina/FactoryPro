package com.factory.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.struts2.ServletActionContext;


public class DownFileUtil {
	
	/**
	 * 下载文件工具
	 * @param path 完整路径（可用simpleToof工具获取）
	 */
	public static void DownFile(String path){
		InputStream fis = null;
		OutputStream toClient = null;
		try {
			
			String filePath = path;
		
			File downfile = new File(filePath);
			String filename = new String(downfile.getName().getBytes(), "ISO8859-1");
			fis = new BufferedInputStream(new FileInputStream(downfile));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			// fis.close();
			ServletActionContext.getResponse().reset();
			ServletActionContext.getResponse().addHeader("Content-Disposition", "attachment;filename=" + filename);
			ServletActionContext.getResponse().addHeader("Content-Length", "" + downfile.length());
			toClient = new BufferedOutputStream(ServletActionContext
					.getResponse().getOutputStream());
			ServletActionContext.getResponse().setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			// toClient.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					if (toClient != null) {
						try {
							toClient.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
		}
	}
	
}

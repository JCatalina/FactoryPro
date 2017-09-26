package com.factory.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 绝对路径
 * @author mgchen
 *
 */
public class PathUtil {

	private static PathUtil instance;
	private String realPath;
	
	private PathUtil()
	{
		
	}
	
	public static PathUtil getInstance()
	{
		if(instance == null)
		{
			instance = new PathUtil();
		}
		return instance;
	}
	
	/**
	 * 获取上传文件的根路径
	 * @return
	 */
	public static String getRootPath()
	{
		return getInstance().getFileRealPath();
	}
	
	/**
	 * 获取当前webapp的根目录
	 * @return
	 */
	public String getRealPath()
	{
		if(!SimpleToof.checkNotNull(realPath))
		{
			realPath = "";
			String classPath = this.getClass().getClassLoader().getResource("/").getPath();
		     try {
		        classPath =URLDecoder.decode(classPath, "gb2312");
	         } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	         }
		     String[] strPath = classPath.split("/");
		     for(int i = 1;i < strPath.length - 3; i++){
		    		realPath = realPath + strPath[i]+"/";
		     }
		}
		
		return realPath;
	}

	/**
	 * 获取上传文件的根目录
	 * @return
	 */
	public String getFileRealPath()
	{
		return getRealPath() + "factory_file/";
	}
	
	/**
	 * 获取上传文件带域名的访问路径
	 * @return
	 */
	public String getFileRelativePath()
	{
		return "/factory_file/";
	}
	
	/**
	 * 过滤域名
	 * @return
	 */
	public static String replaceDomain(String path)
	{
		return path.replaceAll("/factory_file/", "");
	}
	
	/**
	 * 从相对路径获取真实路径
	 * @param path
	 * @return
	 */
	public static String getRealPath(String path)
	{
		return getInstance().getFileRealPath() + replaceDomain(path);
	}
}

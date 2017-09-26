package com.factory.utils;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.UUID;

import org.aspectj.util.FileUtil;
import org.json.JSONObject;


public class SimpleToof {
	
	public static DecimalFormat fmt = new DecimalFormat("0");
	
	/**
	 * 构建返回值格式
	 * @param status 返回状态
	 * @param errMsg 返回附带信息
	 * @param inf    返回实体值
	 * @return JSONObject 特定格式的JSONObject
	 */
	public static JSONObject res(Integer status,String errMsg,JSONObject inf){
		JSONObject res = new JSONObject();
		JSONObject fObj = new JSONObject();
		try{
			res.put("status", status);
			res.put("errMsg", errMsg);
			
			fObj.put("res", res);
			fObj.put("inf", inf);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return fObj;
	} 

	
	/**
	 * 判断是否为空
	 * */
	public static boolean checkNotNull(Object o){
		boolean b = false;
		if(o!=null && !"".equals(o))
			b = true;
		return b;
	}
	
	
	/**
	 * 获取指定格式的Double 类型数据
	 * @param type 转换格式的类型  默认为   "#.##"
	 * @param e 要转换的数据源
	 * **/
	public static Double getTypeDouble(Double e){
		fmt.setRoundingMode(RoundingMode.HALF_UP); //保留小数点后两位并四舍五入，确保价钱准确
		Double d = Double.valueOf(fmt.format(e));
		System.out.println(d);
		return d;
	}
	
	
	/**
	 * 获取uuid
	 * */
	public static String getUUIDName() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 上传excel文件 
	 * @param file
	 * @param fileType
	 * @return
	 */
	public static String uploadRealExcelFile(File file, String path, String fileType) throws Exception
	{
		//找到指定目录，没有则创建
		//String realPath = PathUtil.getRootPath() + path;
		String realPath = path+"/";
		File cp = new File(realPath);
		if(!cp.exists())
			cp.mkdirs();
		
		if(!checkNotNull(fileType))
			throw new RuntimeException("上传文件不能为空");
		
		String uName = getUUIDName();
		cp = new File(cp, uName + "." + fileType);
		FileUtil.copyFile(file, cp);
		
		return realPath + uName + "." + fileType;
	}
	
	
	/***创建文件（自动生成文件夹。）(需要真实路径)**/
	public static File createFile(String mkdirPath,String fileName) throws IOException {
		//System.out.println(mkdirPath+fileName);
		//mkdirPath = "E:\\work\\soft\\MyEclipse\\apache-tomcat-7.0.61\\webapps\\ROOT\\";
		
		
		File f = new File(mkdirPath);
		if (!f.exists()) {
			f.mkdirs();
		}
		File file = new File(f, fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		
		//System.out.println(file.getAbsolutePath());
		return file;
	}
	
}

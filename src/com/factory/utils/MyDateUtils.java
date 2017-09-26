package com.factory.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateUtils {

	public static Date formatDateToYearMonth(String dateStr) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date= sdf.parse(dateStr);
		return date;
	}
	
	public static Date formatDateToYYYYMMDD(String dateStr) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date= sdf.parse(dateStr);
		return date;
	}
	
}

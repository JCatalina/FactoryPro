package com.factory.common;


public interface Constants {
	
	public static int MACHINE_NUM=20;
	public static final int ERROR_CODE = 404;
	public static final double LITTLE_PERCENTAGE= 0.0054; //小倍率
	public static final double BIG_PERCENTAGE= 0.0059; //大倍率
	public static final double MIDDLE_PERCENTAGE= 0.0054; //中倍率
	//////////////////////////////////////////////
	
	interface Common {
		/**
		 * 前端用户登录session/店铺中心登录的用户session
		 */
		String SESSION_USER = "login_user";

		String SESSION_ADMIN = "login_admin";

	}
	
	
	

	
}

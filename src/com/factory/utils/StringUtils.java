package com.factory.utils;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";
    
    /**
     * 转换为字节数组
     * @param str
     * @return
     */
    public static byte[] getBytes(String str){
    	if (str != null){
    		try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
    	}else{
    		return null;
    	}
    }
    
    /**
     * 只取后两位
     * @param str
     * @return
     */
    public static String getLastTwoChars( String str ) {
    	if ( null != str && str.trim().length() > 0 ) {
    		str = str.trim();
    		int len = str.length();
    		if ( len == 2 ) {
    			return str;
    		} else if ( len >  2) {
    			return str.substring( len - 2 );
    		}
    	}
    	return "";
    }
    
    
    /**
     * 只取后三位
     * @param str
     * @return
     */
    public static String getLastThreeChars( String str ) {
    	if ( null != str && str.trim().length() > 0 ) {
    		str = str.trim();
    		int len = str.length();
    		if ( len == 3 ) {
    			return str;
    		} else if ( len >  3) {
    			return str.substring( len - 3 );
    		}
    	}
    	return "";
    }
    
	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * @param html
	 * @return
	 */
	public static String replaceMobileHtml(String html){
		if (html == null){
			return "";
		}
		return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
	}
	
	/**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    
    /**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 根据规则regExpr,判断字符内容是否匹配
     * <p>
     * 利用正则表达式规则判断，待验证的字符内容是否符合要求。
     * <br>
     * 比如'[0-9]*'即判断是否为数字的规则,
     *    '^[a-z0-9_-]{3,15}$'即判断是否为用户名的规则,<br>
     *    '((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})'即判断是否为密码的规则,<br>
     *    '^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$'即email规则,<br>
     *    '([^\s]+(\.(?i)(jpg|png|gif|bmp))$)'即判断某图片文件格式,<br>
     *    '^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$'即IP规则.
     *    
     *    @see {@link 详细正则表达式的信息请阅  https://en.wikipedia.org/wiki/Regular_expression}  
     *    
     * @param regExpr 正则表达式 
     * @param content 待验证的字符内容
     * @return boolean
     */
	public static boolean isMatch(String regExpr, String content) {
		if (StringUtils.isEmpty(regExpr, content))
			return false;

		Pattern pattern = Pattern.compile(regExpr);
		Matcher matcher = pattern.matcher(content);

		return matcher.matches();
	}
    
	/**
	 * 正则判断纯数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ) {
              return false;
        }
        return true;
	}
	
	
	public static boolean isEmpty( String str ) {
		return null == str || str.trim().length() == 0 ;
	}
	
	public static boolean isNotEmpty( String str ) {
		return null != str && str.trim().length() != 0 ;
	}

	/**
	 * 判断字符内容中是否存在一个为空的字符串
	 * <p>
	 * 如果在所有的字符串内容中，只要存在某一个字符串为空，则返回为true,
	 * 如果所有的字符串内容中，不存在一个空字符串，则返回为false.
	 * 
	 * @param strings
	 * @return boolean
	 */
	public static boolean isEmpty(String... strings) {
		if (strings == null)
			return true;

		for (String str : strings) {
			if (StringUtils.isEmpty(str))
				return true;
		}

		return false;
	}
	
	public static boolean isAllEmpty(String... strings) {
		if (strings == null)
			return true;

		for (String str : strings) {
			if (!StringUtils.isEmpty(str))
				return false;
		}

		return true;
	}
	
	/**
	 * @param strings 该数组是否有值
	 * @return
	 */
	public static boolean hasContent(String[] strings) {
		if (strings != null && strings.length > 0)
			return true;

		return false;
	}
	
	/**
	 * @param params 该map是否有值
	 * @return
	 */
	public static boolean hasContent(Map<?, ?> params) {
		if(params != null && params.size() > 0)
			return true;

		return false;
	}
	
	public static boolean isNumeric2(String str) {
		if ( null == str ||  isEmpty( str ) ) { return false ; }
        char[] chars = str.toCharArray();
        for ( char c : chars ) {
    	   if ( !Character.isDigit( c ) ) return false;
        }
        return true;
	}

	public static int parseInt(String string) {
		if ( null != string  && isNumeric2( string ) ) {
			return Integer.parseInt( string ) ;
		}
		return 0;
	}
}

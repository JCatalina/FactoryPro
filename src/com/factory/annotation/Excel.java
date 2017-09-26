package com.factory.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)   
@Target(ElementType.FIELD)
public @interface Excel {
//导入时，对应数据库的字段 主要是用户区分每个字段，不能有annocation重名�?
//导出时的列名   导出排序跟定义了annotation的字段的顺序有关  
public String exportName();
//导出时在excel中每个列的宽  单位为字符，�?��汉字=2个字�?
//以列名列内容中较合�?的长�?  例如姓名�? 【姓名一般三个字 性别�?【男女占1，但是列标题两个汉字�?
//限制1-255
public int exportFieldWidth();
//导出时是否进行字段转�?  例如 性别用int存储，导出时可能转换为男，女
//若是sign�?,则需要在pojo中加入一个方�?get字段名Convert(),注意在方法上加上@Transient注解
//例如，字段sex ，需要加�?public String getSexConvert()  返回值为string
//若是sign�?,则不必管
public int exportConvertSign();
//导入数据是否�?��转化      �?对已有的excel，是否需要将字段转为对应的数�?
//若是sign�?,则需要在pojo中加�?  void set字段名Convert(String text)
public int importConvertSign();
}


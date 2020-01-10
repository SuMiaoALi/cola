package com.baoyun.ins.utils.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 孔垂云 on 2017/12/24.
 */
public class StringUtil {
    /**
     * 根据从request中获取body的参数值
     *
     * @param br bufferreader
     * @return 字符串
     */
    public static String getBodyString(BufferedReader br) {
        String inputLine;
        String str = "";
        try {
            while ((inputLine = br.readLine()) != null) {
                str += inputLine;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return str;
    }

    /**
     * 取得header的所有属性
     *
     * @param headers 请求的headers
     * @param request request
     * @return 把header拼成字符串
     */
    public static String getHeaderValue(Enumeration<String> headers, HttpServletRequest request) {
        String str = "";
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            str += header + "=" + request.getHeader(header) + "&";
        }
        return str;
    }

    /**
     * 判断字符串是null或空，null或""都返回true
     *
     * @param str 字符串
     * @return 是否
     */
    public static boolean isNullOrEmpty(String str) {
        if (str == null || "".equals(str) || "null".equals(str)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 在controller或action里面打印字符串，返回给前台
     *
     * @param response response
     * @param str      字符串
     */
    public static void out(HttpServletResponse response, String str) {
        try {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 判断字符串不是null或空
     *
     * @param str 字符串
     * @return 是否
     */
    public static boolean isNotNullOrEmpty(String str) {
        if (str != null && !str.equals("") && !str.equals("null")) {
            return true;
        } else {
            return false;
        }
    }
    
    public static String phoneForName(String phone) {
    	if(phone.length() == 11) {
    		return phone.substring(0, 3).concat("...").concat(phone.substring(7, 10));
    	}
    	return "";
    }
    
    public static String phoneFoart(String phone) {
    	if(phone.length() == 11) {
    		return phone.substring(0, 3).concat("-").concat(phone.substring(3, 7)).concat("-").concat(phone.substring(7, 11));
    	}
    	return "";
    }
    
    public static Date getDateByString(String time) {
    	Date date = null;
    	if (time == null)
    	    return date;
    	String date_format = "yyyy-MM-dd HH:mm:ss";
    	SimpleDateFormat format = new SimpleDateFormat(date_format);
    	try {
    	    date = format.parse(time);
    	} catch (ParseException e) {
    	    e.printStackTrace();
    	}
    	return date;
   }
    
  //Timestamp转化为String:
    public static String timestampToStr(long dateline){
    	Timestamp timestamp = new Timestamp(dateline*1000);
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
    	return df.format(timestamp);
	}
    
    public static String getShortTime(long dateline) {
    	String shortstring = null;
    	String time = timestampToStr(dateline);
    	Date date = getDateByString(time);
    	if(date == null) return shortstring;
    	
    	long now = Calendar.getInstance().getTimeInMillis();
    	long deltime = (now - date.getTime())/1000;
    	if(deltime > 365*24*60*60) {
    		shortstring = (int)(deltime/(365*24*60*60)) + "年前";
    	} else if(deltime > 24*60*60) {
    	    shortstring = (int)(deltime/(24*60*60)) + "天前";
    	} else if(deltime > 60*60) {
    		shortstring = (int)(deltime/(60*60)) + "小时前";
    	} else if(deltime > 60) {
    		shortstring = (int)(deltime/(60)) + "分前";
    	} else if(deltime > 1) {
    		shortstring = deltime + "秒前";
    	} else {
    	    shortstring = "1秒前";
    	}
    		return shortstring;
	}
    
}
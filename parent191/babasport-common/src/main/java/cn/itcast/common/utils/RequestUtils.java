/**
 * 
 */
package cn.itcast.common.utils;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author bobo
 *获取CESSIONID
 */
public class RequestUtils {

    //获取
    public static String getCESSIONID(HttpServletRequest request,HttpServletResponse response){
	//1:取出Cookie
	Cookie[] cookies = request.getCookies();
	if(null != cookies && cookies.length > 0){
	    for (Cookie cookie : cookies) {
		//2:判断Cookie中是否有CSESSIONID
		if("CSESSIONID".equals(cookie.getName())){
		  //3:有 直接使用
		    return cookie.getValue();
		}
	    }
	}
	//4:没有 创建一个CSESSIONID 并保存到COOKUE中 同时把此COOKIE写回浏览器 使用此生成的CSESSIONID
	String csessionid = UUID.randomUUID().toString().replaceAll("-", "");
	Cookie cookie = new Cookie("CSESSIONID",csessionid);
	//设置存活时间
	cookie.setMaxAge(-1);
	//设置路径  都会携带cookie  不管是/login /portal都会
	cookie.setPath("/");
	//设置跨域localhost == www.babasport.com  www.jd.com  search.jd.com  item.jd.com 3个访问就是跨域 cookie.setDomain(".jd.com");
	/*cookie.setDomain("");*/
	
	response.addCookie(cookie);
	return csessionid;
	
	
    }
    
}

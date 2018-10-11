/**
 * 
 */
package cn.itcast.core.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.common.utils.RequestUtils;
import cn.itcast.core.service.user.SessionProvider;

/**
 * 拦截Controller层之前 springmvc Handler处理器
 * 在执行controller方法前后处理
 * @author bobo
 *
 */
public class CustomInterceptor implements HandlerInterceptor{

    @Autowired
    private SessionProvider sessionProvider;
    
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	    throws Exception {
	//必须登陆
	String username = sessionProvider.getAttributeForUsername(RequestUtils.getCESSIONID(request, response));
	if(null == username){
	    //未登录
	    //重定向到登陆页面
	    response.sendRedirect("http://localhost:8081/login.aspx?returnUrl=http://localhost:8082/");
	    return false;
	}
	
	return false;
    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
	    ModelAndView modelAndView) throws Exception {
	// TODO Auto-generated method stub
	
    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
	    throws Exception {
	// TODO Auto-generated method stub
	
    }

}

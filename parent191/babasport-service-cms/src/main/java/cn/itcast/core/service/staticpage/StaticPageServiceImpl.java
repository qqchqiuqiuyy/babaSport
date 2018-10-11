/**
 * 
 */
package cn.itcast.core.service.staticpage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


/**
 * @author bobo
 * 接口 ServletContextAware
 */
public class StaticPageServiceImpl implements StaticPageService,ServletContextAware{
    
    //声明
    private Configuration conf;
    

    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.conf = freeMarkerConfigurer.getConfiguration();
    }





    /**
     * @return the servletContext
     */
    public ServletContext getServletContext() {
        return servletContext;
    }





    //静态化商品
    public void productStaticPage(Map<String, Object> root,String id){
	//加载模板
	Writer out = null;
	Template template;
	//输出的路径
	String path = getPath("/html/product/" + id +".html");
	File file = new File(path);
	File parentFile = file.getParentFile();
	if (!parentFile.exists()) {
	    parentFile.mkdirs();
	}
	
	try {
	    //读文件 utf-8
	    
	    template = conf.getTemplate("product.html");	
	     //输出 utf-8
	    	out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8") ;
	    		
	    	template.process(root, out);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (TemplateException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}finally {
	    if(out != null){
		try {
		    out.close();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	}

    }


    //获取路径
    private String getPath(String name){
	return servletContext.getRealPath(name);
    }


 
    //声明
    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
	this.servletContext = servletContext;
	
    }
    
    //静态化
    
    
}

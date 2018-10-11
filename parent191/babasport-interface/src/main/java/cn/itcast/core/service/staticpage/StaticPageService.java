/**
 * 
 */
package cn.itcast.core.service.staticpage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;

/**
 * @author bobo
 *
 */
public interface StaticPageService{

    

  //静态化商品
    public void productStaticPage(Map<String, Object> root,String id);


    
}

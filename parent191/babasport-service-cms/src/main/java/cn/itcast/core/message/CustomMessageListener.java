/**
 * 
 */
package cn.itcast.core.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.core.bean.product.Color;
import cn.itcast.core.bean.product.Product;
import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.service.product.CmsService;
import cn.itcast.core.service.product.SearchService;
import cn.itcast.core.service.staticpage.StaticPageService;


/**
 * 处理类
 * @author bobo
 *
 */
public class CustomMessageListener implements MessageListener{

    @Autowired
    private StaticPageService staticPageService;
    @Autowired
    private CmsService cmsService;
    
    @Override
    public void onMessage(Message message) {
	// TODO Auto-generated method stub
	ActiveMQTextMessage am = (ActiveMQTextMessage)message;
	try {
	    System.out.println("ActiveMQ中的消息是-cms:"+am.getText());
	    String id = am.getText();
	    //数据
	    Map<String, Object> root = new HashMap<>();
	  //商品
		Product product = cmsService.selectProductById(Long.parseLong(id));
		//sku
		List<Sku> skus = cmsService.selectSkuListByProductId(Long.parseLong(id));
		  
		//遍历一次 相同不要
		HashSet<Color> colors = new HashSet<Color>();
		for (Sku sku : skus) {
		    colors.add(sku.getColor());
		}
		
		
		root.put("product", product);
		root.put("skus", skus);
		root.put("colors", colors);
	    
	    
	   //静态化
	    staticPageService.productStaticPage(root, id);
	    
	    
	    
	} catch (JMSException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}

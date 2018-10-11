/**
 * 
 */
package cn.itcast.core.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.core.service.product.SearchService;


/**
 * 处理类
 * @author bobo
 *
 */
public class CustomMessageListener implements MessageListener{

    @Autowired
    private SearchService searchService;
    
    @Override
    public void onMessage(Message message) {
	// TODO Auto-generated method stub
	ActiveMQTextMessage am = (ActiveMQTextMessage)message;
	try {
	    System.out.println("ActiveMQ中的消息是-solr:"+am.getText());
	    //保存商品信息到Solr服务器
	    searchService.insertProductToSolr(Long.parseLong(am.getText()));
	} catch (JMSException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}

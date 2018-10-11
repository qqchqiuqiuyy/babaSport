/**
 * 
 */
package cn.itcast.core.service;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.ietf.jgss.Oid;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.core.bean.user.Buyer;
import redis.clients.jedis.Jedis;
import sun.tools.jar.resources.jar;

/**
 * @author bobo
 *
 */

public class TestJson {
    

    //对象json互转
    @Test
    public void testJSON() throws Exception{
	//Springmvc @Request @RespinseBody json与对象转换
	Buyer buyer = new Buyer();
	buyer.setUsername("范冰冰");
	ObjectMapper om = new ObjectMapper();
	Writer w = new StringWriter();
	//不要NULL  不转
	om.setSerializationInclusion(Include.NON_NULL);
	om.writeValue(w,buyer);
	System.out.println(w.toString());
	//转回对象
	Buyer r = om.readValue(w.toString(), Buyer.class);
	System.out.println(r);
    }
    
    

}

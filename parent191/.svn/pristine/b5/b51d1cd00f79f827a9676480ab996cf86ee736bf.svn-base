/**
 * 
 */
package cn.itcast.core.service;

import java.io.IOException;

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

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import redis.clients.jedis.Jedis;
import sun.tools.jar.resources.jar;

/**
 * @author bobo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context.xml")
public class TestSolr {
    
    @Autowired
    private SolrServer solrServer;
    
    @Test
    public void testSolrjSpring() throws SolrServerException, IOException{
	
	
	
	SolrInputDocument document = new SolrInputDocument();
	document.setField("id", 4);	
	document.setField("name", "范冰冰");
	solrServer.add( document);
	
	solrServer.commit();
    }
    
    
    
    
   /* @Test
    public void testSolrJ() throws SolrServerException, IOException{
	String baseUrl =  "http://192.168.200.128:8080/solr";
	SolrServer solrServer = new HttpSolrServer(baseUrl);
	SolrInputDocument document = new SolrInputDocument();
	document.setField("id", 3);	
	document.setField("name", "范冰冰");
	solrServer.add( document);
	
	solrServer.commit();
	
    }*/
}

/**
 * 
 */
package cn.itcast.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;
import sun.tools.jar.resources.jar;

/**
 * @author bobo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class TestRedis {
    
    @Autowired
    private Jedis jedis;
    @Test
    public void testSpringJedis(){
	jedis.set("000", "aaa");
    }
    
    
    @Test
    public void testRedis(){
	
	Jedis jedis = new Jedis("172.16.15.255",6379);
	jedis.set("eclipse", "test");
	Long pno = jedis.incr("pno");
	System.out.println(pno);
	
	jedis.close();
    }
}

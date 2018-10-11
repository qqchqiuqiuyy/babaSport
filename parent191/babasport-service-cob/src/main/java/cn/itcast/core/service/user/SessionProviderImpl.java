/**
 * 
 */
package cn.itcast.core.service.user;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.common.web.Constants;
import redis.clients.jedis.Jedis;

/**
 * 保存用户名或验证码到redis
 * Session共享
 * @author bobo
 *
 */
public class SessionProviderImpl implements SessionProvider{

    @Autowired
    private Jedis jedis;
    private Integer exp = 30;
    
    /**
     * @param exp the exp to set
     */
    public void setExp(Integer exp) {
        this.exp = exp;
    }

    @Override
    public void setAttribuerForUsername(String name, String value) {
	//保存用户名到Redis中
	jedis.set(name + ":" +Constants.USER_NAME, value);
	//时间
	jedis.expire(name + ":" +Constants.USER_NAME, 60 * exp);
    }

    /* (non-Javadoc)
     * @see cn.itcast.core.service.user.SessionProvider#getAttributeForUsername(java.lang.String)
     */
    @Override
    public String getAttributeForUsername(String name) {
	// TODO Auto-generated method stub
	String value = jedis.get(name + ":" +Constants.USER_NAME);
	if(null != value){
	  //时间  重新设置时间 最后一次操作后的一段时间之后才消失
	    jedis.expire(name + ":" +Constants.USER_NAME, 60 * exp);
	}
	return value;
    }

    
}

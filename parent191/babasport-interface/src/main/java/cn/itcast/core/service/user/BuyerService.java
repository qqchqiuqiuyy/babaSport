/**
 * 
 */
package cn.itcast.core.service.user;

import org.springframework.stereotype.Service;

import cn.itcast.core.bean.BuyerCart;
import cn.itcast.core.bean.order.Order;
import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.bean.user.Buyer;

/**
 * @author bobo
 *
 */
@Service("buyerService")
public interface BuyerService{
    //通过用户名查询用户对象
    public Buyer selectBuyerByUsername(String username);
    
  //保存订单
    public void insertOrder(Order order,String username);
  //取出购物车从Redis 
    public BuyerCart selectBuyerCartFromRedis(String username );
  //通过SKUID查询对象
    public Sku selectSkuById(Long id );
}

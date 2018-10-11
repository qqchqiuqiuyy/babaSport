/**
 * 
 */
package cn.itcast.core.service.product;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.itcast.core.bean.BuyerCart;
import cn.itcast.core.bean.product.Sku;

/**
 * @author bobo
 *
 */
public interface SkuService { 
    //商品ID 查询 库存结果集
    public List<Sku> selectSkuListByProductId(Long productId);
    
  //修改
    public  void updateSkuById(Sku sku);
    
    //通过SKUID查询对象
    public Sku selectSkuById(Long id );
  //保存商品到Redis中
    public void insertBuyerCartToRedis(BuyerCart buyerCart,String username);
  //取出购物车从Redis 
    public BuyerCart selectBuyerCartFromRedis(String username );
    
}

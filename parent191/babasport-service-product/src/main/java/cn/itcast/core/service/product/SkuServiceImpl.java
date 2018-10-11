/**
 * 
 */
package cn.itcast.core.service.product;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.core.bean.BuyerCart;
import cn.itcast.core.bean.BuyerItem;
import cn.itcast.core.bean.product.Product;
import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.bean.product.SkuQuery;
import cn.itcast.core.dao.product.ColorDao;
import cn.itcast.core.dao.product.ProductDao;
import cn.itcast.core.dao.product.SkuDao;
import redis.clients.jedis.Jedis;

/**
 * @author bobo
 *
 */
@Service("skuService") //这里要有skuService是因为给dubbo注册
public class SkuServiceImpl implements SkuService{ 

    @Autowired
    private SkuDao skuDao;
    @Autowired
    private ColorDao colorDao;
    
    
    //商品ID 查询 库存结果集
    public List<Sku> selectSkuListByProductId(Long productId){
	SkuQuery skuQuery = new SkuQuery();
	skuQuery.createCriteria().andProductIdEqualTo(productId);
	List<Sku> skus = skuDao.selectByExample(skuQuery);
	for(Sku sku : skus){
	  sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId())); 
	}
	return skus;	
    }
    
    //修改
    public  void updateSkuById(Sku sku){
	
   	skuDao.updateByPrimaryKey(sku);
     }
    
    @Autowired
    private ProductDao productDao;
    
    //通过SKUID查询对象
    public Sku selectSkuById(Long id ){
	//Sku对象
	Sku sku = skuDao.selectByPrimaryKey(id);
	//商品对象
	Product product = productDao.selectByPrimaryKey(sku.getProductId());
	sku.setProduct(product);
	//颜色对象
	sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId()));
	
	return sku;
    }
    
    @Autowired
    private Jedis jedis;
    
    //保存商品到Redis中
    public void insertBuyerCartToRedis(BuyerCart buyerCart,String username){
	
	//判断购物项长度大于0
	List<BuyerItem> items = buyerCart.getItems();
	if(items.size() > 0 ){
	    for (BuyerItem buyerItem : items) {
		//redis的key
		String key = "buyerCart:" + username ; 
		//key 里面的 属性
		String field = String.valueOf(buyerItem.getSku().getId()) ;
		//key里面属性对应的值
		String value = String.valueOf(buyerItem.getAmount()) ;
		//判断是否已经存在
		if(jedis.hexists(key, field)){
		    //在有的直接加数量
		    jedis.hincrBy(key, field, buyerItem.getAmount());

		}else{
		    jedis.hset(key,field ,value);
		}
		
	    }
	}
	
    }
    
    //取出购物车从Redis 
    public BuyerCart selectBuyerCartFromRedis(String username ){
	BuyerCart buyerCart = new BuyerCart();
	
	Map<String, String> hgetAll = jedis.hgetAll("buyerCart:" + username);
	
	if(null != hgetAll){
	   Set<Entry<String, String>> entrySet = hgetAll.entrySet();
	   for (Entry<String, String> entry : entrySet) {
	       // K skuId  V amount
	     //5 追加当前商品到购物车
		  Sku sku = new Sku();
		  sku.setId(Long.parseLong(entry.getKey()));
		  BuyerItem buyerItem = new BuyerItem();
		  buyerItem.setSku(sku);
		  //Amount
		  buyerItem.setAmount(Integer.parseInt(entry.getValue()));
		  //追加商品到购物车
		  buyerCart.addItem(buyerItem);
	   }
	}
	return buyerCart;
    }
    
}

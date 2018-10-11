/**
 * 
 */
package cn.itcast.core.service.user;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.dialect.odps.visitor.OdpsASTVisitor;

import cn.itcast.core.bean.BuyerCart;
import cn.itcast.core.bean.BuyerItem;
import cn.itcast.core.bean.order.Detail;
import cn.itcast.core.bean.order.Order;
import cn.itcast.core.bean.product.Product;
import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.bean.user.Buyer;
import cn.itcast.core.bean.user.BuyerQuery;
import cn.itcast.core.dao.order.DetailDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.product.ColorDao;
import cn.itcast.core.dao.product.ProductDao;
import cn.itcast.core.dao.product.SkuDao;
import cn.itcast.core.dao.user.BuyerDao;
import cn.itcast.core.service.product.SkuService;
import redis.clients.jedis.Jedis;

/**
 * @author bobo
 *
 */
@Service("buyerService")
public class BuyerServiceImpl implements BuyerService{
    
    @Autowired
    private BuyerDao buyerDao;
    //通过用户名查询用户对象
    public Buyer selectBuyerByUsername(String username){
	BuyerQuery buyerQuery = new BuyerQuery();
	buyerQuery.createCriteria().andUsernameEqualTo(username);
	
	List<Buyer> buyers = buyerDao.selectByExample(buyerQuery);
	if(null != buyers && buyers.size() > 0){
	    return buyers.get(0);
	}
	return null;
    }
    
    @Autowired
    private Jedis jedis;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private SkuService skuService;
    @Autowired
    private DetailDao detailDao;
    
    //保存订单
    public void insertOrder(Order order,String username){
	
	// id 订单编号  全国唯一Redis
	Long id = jedis.incr("oid");
	order.setId(id);
	//加载购物车
	BuyerCart buyerCart = selectBuyerCartFromRedis(username);
	List<BuyerItem> items = buyerCart.getItems();
	    for (BuyerItem buyerItem : items) {
		Long skuId = buyerItem.getSku().getId();
		Sku sku = skuService.selectSkuById(skuId);
		buyerItem.setSku(sku);
	    }
	//运费 购物车提供
	    order.setDeliverFee(buyerCart.getFee());  
	//总价
	    order.setTotalPrice(buyerCart.getTotalPrice());
	//订单金额
	    order.setOrderPrice(buyerCart.getProductPrice());
	//支付状态 0 到付 1 待付 2 已付款 3.待退款 4.退款成功 5.退款失败
	   if(order.getPaymentWay() == 1){
	       order.setIsPaiy(0);
	   }else{
	       order.setIsPaiy(1);
	   }
	    //订单状态 0:提交订单 1:仓库配货 2:商品出货  3:等待收货  4:完成 5:待退款 6:已退款
	   order.setOrderState(0);
	   //时间 后台程序自己写
	   order.setCreateDate(new Date());
	   //用户ID  前台用户做注册  (用户名 密码) 用户ID  →Redis 生成 用户ID: 用户名保存到Redis中
	   String uid = jedis.get(username);
	//保存订单
	    orderDao.insertSelective(order);
	    //保存订单详情
	    for (BuyerItem buyerItem : items) {
		Detail detail = new Detail();
		//ID
		
		//订单id
		detail.setOrderId(id);
		//商品编号
		detail.setProductId(buyerItem.getSku().getProductId());
		//商品名称
		detail.setProductName(buyerItem.getSku().getProduct().getName());
		//颜色
		detail.setColor(buyerItem.getSku().getColor().getName());
		//尺码
		detail.setSize(buyerItem.getSku().getSize());
		//价格
		detail.setPrice(buyerItem.getSku().getPrice());
		//数量
		detail.setAmount(buyerItem.getAmount());
		//购物车提供
		detailDao.insertSelective(detail);
		
	    }
	    
	    //情况购物车
	    jedis.del("buyerCart:fbb2016");
	    //练习hash 指定K
	    
	//保存订单详情

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
    
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private ColorDao colorDao;
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
}

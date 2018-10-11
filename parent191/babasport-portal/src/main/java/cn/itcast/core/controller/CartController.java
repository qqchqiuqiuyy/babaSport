/**
 * 
 */
package cn.itcast.core.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.common.utils.RequestUtils;
import cn.itcast.common.web.Constants;
import cn.itcast.core.bean.BuyerCart;
import cn.itcast.core.bean.BuyerItem;
import cn.itcast.core.bean.order.Order;
import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.service.product.SkuService;
import cn.itcast.core.service.user.BuyerService;
import cn.itcast.core.service.user.SessionProvider;

/**
 * @author bobo
 *购物车
 *去购物车页面
 *
 */
@Controller
public class CartController {

    
    @Autowired
    private SessionProvider sessionProvider;

    //加入购物车
    
    @RequestMapping(value = "/addCart")
    public String addCart(Long skuId, Integer amount,Model model,HttpServletRequest request,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException{
	BuyerCart buyerCart = null;
	//对象转json  json转对象
	ObjectMapper om = new ObjectMapper();
	//不要NULL  不转
	om.setSerializationInclusion(Include.NON_NULL);
	
	//1.从Request中取Cookies
	Cookie[] cookies = request.getCookies();
	if(null != cookies && cookies.length > 0){
	    //遍历Cookie 取出之前购物车
	   for (Cookie cookie : cookies) {
	       //2.遍历Cookie中有没有购物车
	       if(Constants.BUYER_CART.equals(cookie.getName())){
		   String value = cookie.getValue();
		   //转回对象
		   buyerCart = om.readValue(value, BuyerCart.class);
		   break;
	       }
	   }
	}
	
	//判断购物车是否为null
	if(null == buyerCart){
	   buyerCart = new BuyerCart();
	}
	 //5 追加当前商品到购物车
	  Sku sku = new Sku();
	  sku.setId(skuId);
	  BuyerItem buyerItem = new BuyerItem();
	  buyerItem.setSku(sku);
	  //Amount
	  buyerItem.setAmount(amount);
	  //追加商品到购物车
	  buyerCart.addItem(buyerItem);
	
	//判断用户是否登陆
	String username = sessionProvider.getAttributeForUsername(RequestUtils.getCESSIONID(request, response));
	if(username != null){	
	    if(null != buyerCart){
		 // 3.有 把购物车中商品添加到Redis购物车中  
		skuService.insertBuyerCartToRedis(buyerCart, username);
		//清理之前的cookie
		Cookie cookie = new Cookie(Constants.BUYER_CART,null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	    }        
	}else{
		//6.创建Cookie 把新购物车放进去
		Writer w = new StringWriter();
		om.writeValue(w, buyerCart);
		Cookie cookie = new Cookie(Constants.BUYER_CART,w.toString() );
		//设置时间 1天
		cookie.setMaxAge(60 * 60 * 24);
		//设置路径
		cookie.setPath("/");
		//上线后 申请域名 跨域
		//7 保存写回浏览器
		response.addCookie(cookie);
	}

	return "redirect:/toCart";
    }
    @Autowired
    private SkuService skuService;
    
    //去购物车页面
    @RequestMapping(value = "/toCart")
    public String toCart(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
	BuyerCart buyerCart = null;
	ObjectMapper om = new ObjectMapper();
	//不要NULL  不转
	om.setSerializationInclusion(Include.NON_NULL);
	
	//1.从Request中取Cookies
	Cookie[] cookies = request.getCookies();
	if(null != cookies && cookies.length > 0){
	    //遍历Cookie 取出之前购物车
	   for (Cookie cookie : cookies) {
	       //2.遍历Cookie中有没有购物车
	       if(Constants.BUYER_CART.equals(cookie.getName())){
		   String value = cookie.getValue();
		   //转回对象
		   buyerCart = om.readValue(value, BuyerCart.class);
		   break;
	       }
	   }
	}
	
	//判断用户是否登陆
	String username = sessionProvider.getAttributeForUsername(RequestUtils.getCESSIONID(request, response));
	if(username != null){
	    //登陆
	    // 2.有 把购物车中商品添加到Redis购物车中  
	    	if(null != buyerCart){
            	    	skuService.insertBuyerCartToRedis(buyerCart, username);
            		//清理之前的cookie
            		Cookie cookie = new Cookie(Constants.BUYER_CART,null);
            		cookie.setMaxAge(0);
            		cookie.setPath("/");
            		response.addCookie(cookie);
	    	}
		
	     //3.从Redis中取出所有的购物车 Service层
		
		buyerCart = skuService.selectBuyerCartFromRedis(username);
		
	}
	
	
	if(null != buyerCart){
	    //2 有 购物车数量及SkuId  将满购物车
	    List<BuyerItem> items = buyerCart.getItems();
	    for (BuyerItem buyerItem : items) {
		Long id = buyerItem.getSku().getId();
		Sku sku = skuService.selectSkuById(id);
		buyerItem.setSku(sku);
	    }
	}
	//3没有
	//回显购物车内容
	model.addAttribute("buyerCart", buyerCart);
	//跳转到购物车页面
	
	
	return "cart";
    }
    
    //结算
    @RequestMapping(value = "/buyer/trueBuy")
    public String trueBuy(Long[] skuIds,Model model,HttpServletRequest request,HttpServletResponse response){
	String username = sessionProvider.getAttributeForUsername(RequestUtils.getCESSIONID(request, response));
	if(username != null){
	  //1 . 购物车中必须有商品
	    BuyerCart buyerCart = skuService.selectBuyerCartFromRedis(username);
	    List<BuyerItem> items = buyerCart.getItems();
	    //标记 
	    Boolean flag = false;
	    if(items.size() > 0 ){
		//2 有 购物车数量及SkuId
		    for (BuyerItem buyerItem : items) {
			Long id = buyerItem.getSku().getId();
			Sku sku = skuService.selectSkuById(id);
			buyerItem.setSku(sku);
			//2 . 购物车中必须有库存
			if(buyerItem.getAmount() > buyerItem.getSku().getStock()){
			    //进来表示无货
			    buyerItem.setIsHave(false);
			    flag = true;
			}
		    }    
		    //至少一款无货
		    if(flag){
			//视图 有一个无货不能进入下个订单页面
			model.addAttribute("buyerCart", buyerCart);
			return "cart";
		    }
	    }else{
		return "redirect:/toCart";
	    }
	}

	//视图 如果都有货 进入下个订单页面
	return "order";
    }
    
    @Autowired
    private BuyerService buyerService;
    
    //提交订单
    @RequestMapping(value = "/buyer/submitOrder")
    public String submitOrder(Order order,Model model,HttpServletRequest request,HttpServletResponse response){
	String username = sessionProvider.getAttributeForUsername(RequestUtils.getCESSIONID(request, response));
	//用户名
	buyerService.insertOrder(order, username);
	
	
	return "success";
    }
}

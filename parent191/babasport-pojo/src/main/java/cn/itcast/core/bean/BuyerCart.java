/**
 * 
 */
package cn.itcast.core.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 购物车
 * @author bobo
 *
 */
public class BuyerCart  implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    //1.商品结果集List<BuyerItem>
    private List<BuyerItem> items = new ArrayList<>();
     
    //添加购物项到购物车中
    public void addItem(BuyerItem item){
	//判断同款 用equal判断要重写equal
	if(items.contains(item)){
	    for (BuyerItem buyerItem : items) {
		//追加购物车如果是同款就直接增加数量	要重写equal方法
		if(buyerItem.equals(item)){
		    Integer rInteger = item.getAmount() + buyerItem.getAmount();
		    buyerItem.setAmount(rInteger);
		}
	    }
	    
	}else{
	    items.add(item);
	}
	
    }

    /**
     * @return the items
     */
    public List<BuyerItem> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<BuyerItem> items) {
        this.items = items;
    }
    /*
     * 下面的方法不标准 在转换成json时候会报错 所以忽略掉
     */
    //2:小计 (商品数据 商品金额 运费 总计)
    //商品数量
    @JsonIgnore
    public Integer getProductAmount(){
	Integer result = 0;
	//计算过程
	for (BuyerItem buyerItem : items) {
	    result += buyerItem.getAmount();
	}
	
	return result;
    }
    //商品金额
    @JsonIgnore
    public Float getProductPrice(){
	Float result = 0f;
	//计算过程
	for (BuyerItem buyerItem : items) {
	    result += buyerItem.getAmount() * buyerItem.getSku().getPrice();
	}
	return result;
    }
    
    //运费
    @JsonIgnore
    public Float getFee(){
	Float result = 0f;
	if(getProductPrice() < 79){
	    result = 5f;
	}
	return result;
    }
    //总金额
    @JsonIgnore
    public Float getTotalPrice(){
	return getProductPrice() + getFee();
    }
}

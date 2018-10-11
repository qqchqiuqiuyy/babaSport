/**
 * 
 */
package cn.itcast.core.bean;

import java.io.Serializable;

import org.springframework.util.SocketUtils;

import cn.itcast.core.bean.product.Sku;

/**
 * 购物项
 * @author bobo
 *
 */
public class BuyerItem implements Serializable{
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((sku == null) ? 0 : sku.hashCode());
	return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	BuyerItem other = (BuyerItem) obj;
	if (sku == null) {
	    if (other.sku != null)
		return false;
	} else if (!sku.getId().equals(other.sku.getId()))
	    return false;
	return true;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    //1.SKUID sku对象里面有个自己的id
    private Sku sku;
    
    //2.Boolean isHave 是否有货
    private Boolean isHave = true;
    
    //3.数量
    private Integer amount = 1;

    /**
     * @return the sku
     */
    public Sku getSku() {
        return sku;
    }

    /**
     * @param sku the sku to set
     */
    public void setSku(Sku sku) {
        this.sku = sku;
    }

    /**
     * @return the isHave
     */
    public Boolean getIsHave() {
        return isHave;
    }

    /**
     * @param isHave the isHave to set
     */
    public void setIsHave(Boolean isHave) {
        this.isHave = isHave;
    }

    /**
     * @return the amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    
}

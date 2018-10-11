package cn.itcast.core.bean.product;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import cn.itcast.common.web.Constants;

public class Product implements Serializable {
    /**
     * ID或商品编号
     */
    private Long id;

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 重量 单位:克
     */
    private Float weight;

    /**
     * 是否新品:0:旧品,1:新品
     */
    private Boolean isNew;

    /**
     * 是否热销:0,否 1:是
     */
    private Boolean isHot;

    /**
     * 推荐 1推荐 0 不推荐
     */
    private Boolean isCommend;

    /**
     * 上下架:0否 1是
     */
    private Boolean isShow;

    /**
     * 商品图片集
     */
    private String imgUrl;

    
   //附加方法
    public String[] getImges() {
        return imgUrl.split(",");
    }

    //附加字段
    private Float price;  //最低价


    /**
     * @return the price
     */
    public Float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * 是否删除:0删除,1,没删除
     */
    private Boolean isDel;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 包装清单
     */
    private String packageList;

    /**
     * 颜色集
     */
    private String colors;

    /**
     * 尺寸集
     */
    private String sizes;

    /**
     * 添加时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Boolean getIsHot() {
        return isHot;
    }

    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public Boolean getIsCommend() {
        return isCommend;
    }

    public void setIsCommend(Boolean isCommend) {
        this.isCommend = isCommend;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public String getImgUrl() {
        return  imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackageList() {
        return packageList;
    }

    public void setPackageList(String packageList) {
        this.packageList = packageList;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors == null ? null : colors.trim();
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes == null ? null : sizes.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Product [id=" + id + ", brandId=" + brandId + ", name=" + name + ", weight=" + weight + ", isNew="
		+ isNew + ", isHot=" + isHot + ", isCommend=" + isCommend + ", isShow=" + isShow + ", imgUrl=" + imgUrl
		+ ", price=" + price + ", isDel=" + isDel + ", description=" + description + ", packageList="
		+ packageList + ", colors=" + colors + ", sizes=" + sizes + ", createTime=" + createTime
		+ ", getImges()=" + Arrays.toString(getImges()) + ", getPrice()=" + getPrice() + ", getId()=" + getId()
		+ ", getBrandId()=" + getBrandId() + ", getName()=" + getName() + ", getWeight()=" + getWeight()
		+ ", getIsNew()=" + getIsNew() + ", getIsHot()=" + getIsHot() + ", getIsCommend()=" + getIsCommend()
		+ ", getIsShow()=" + getIsShow() + ", getImgUrl()=" + getImgUrl() + ", getIsDel()=" + getIsDel()
		+ ", getDescription()=" + getDescription() + ", getPackageList()=" + getPackageList() + ", getColors()="
		+ getColors() + ", getSizes()=" + getSizes() + ", getCreateTime()=" + getCreateTime() + ", getClass()="
		+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }
}
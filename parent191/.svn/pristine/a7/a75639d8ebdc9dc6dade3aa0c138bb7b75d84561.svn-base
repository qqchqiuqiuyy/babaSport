/**
 * 
 */
package cn.itcast.core.bean.product;

import java.io.Serializable;

/**
 * 品牌
 * @author lx
 *
 */
public class BrandQuery implements Serializable{

	/**
	 * 默认的ID
	 */
	private static final long serialVersionUID = 1L;
	
	//品牌ID  bigint
	private Long id;
	//品牌名称
	private String name;
	//描述
	private String description;
	//图片URL
	private String imgUrl;
	//排序  越大越靠前   
	private Integer sort;
	//是否可用   0 不可用 1 可用
	private Integer isDisplay;//is_display
	/**
	 * @return the id
	 */
	public Long getId() {
	    return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
	    this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
	    return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
	    this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
	    return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
	    this.description = description;
	}
	/**
	 * @return the imgUrl
	 */
	public String getImgUrl() {
	    return imgUrl;
	}
	/**
	 * @param imgUrl the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
	    this.imgUrl = imgUrl;
	}
	/**
	 * @return the sort
	 */
	public Integer getSort() {
	    return sort;
	}
	/**
	 * @param sort the sort to set
	 */
	public void setSort(Integer sort) {
	    this.sort = sort;
	}


	/**
	 * @return the isDisplay
	 */
	public Integer getIsDisplay() {
	    return isDisplay;
	}
	/**
	 * @param isDisplay the isDisplay to set
	 */
	public void setIsDisplay(Integer isDisplay) {
	    this.isDisplay = isDisplay;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
	    return serialVersionUID;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	    return "Brand [id=" + id + ", name=" + name + ", description=" + description + ", imgUrl=" + imgUrl
		    + ", sort=" + sort + ", isDisplay=" + isDisplay + "]";
	}
	
	
	//附加
	private Integer pageNo = 1;
	private Integer pageSize = 5;
	//开始行
	private Integer startRow ;


	/**
	 * @return the pageNo
	 */
	public Integer getPageNo() {
	    return pageNo;
	}
	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(Integer pageNo) {
	    this.startRow = (pageNo - 1) * pageSize;
	    this.pageNo = pageNo;
	}
	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
	    return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
	    this.startRow = (pageNo - 1) * pageSize;
	    this.pageSize = pageSize;
	}
	/**
	 * @return the startRow
	 */
	public Integer getStartRow() {
	    return startRow;
	}
	/**
	 * @param startRow the startRow to set
	 */
	public void setStartRow(Integer startRow) {
	    this.startRow = startRow;
	}
}


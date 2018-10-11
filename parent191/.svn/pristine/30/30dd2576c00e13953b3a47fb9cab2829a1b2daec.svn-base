/**
 * 
 */
package cn.itcast.core.service.product;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.Color;
import cn.itcast.core.bean.product.Product;

/**
 * 商品
 * @author bobo
 *
 */
public interface ProductService{
  //分页对象
    public Pagination selectPagination(Integer pageNo,String name,Long brandId, Boolean isShow);
    //颜色结果集
    public List<Color> selectColorList();
    
    public void insertProduct(Product product);
    //上架
  	public void isShow(Long[] ids) throws SolrServerException, IOException;
}

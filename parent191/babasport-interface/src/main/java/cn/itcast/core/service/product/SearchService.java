/**
 * 
 */
package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.Product;

/**
 * @author bobo
 *
 */
public interface SearchService {
  //全文搜索
    public Pagination selectPaginationByQuery(Integer pageNo,String keyword,Long brandId,String price) throws Exception;
  //保存商品信息到solr服务器
public void insertProductToSolr(Long id );

}

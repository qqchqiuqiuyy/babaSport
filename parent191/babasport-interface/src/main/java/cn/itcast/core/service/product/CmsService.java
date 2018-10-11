/**
 * 
 */
package cn.itcast.core.service.product;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.itcast.core.bean.product.Product;
import cn.itcast.core.bean.product.Sku;

/**
 * 评论
 * 晒单
 * 广告
 * 静态化
 * @author bobo
 *
 */
@Service("cmsService")
public interface CmsService {
  //查询商品
    public Product selectProductById(Long productId);
  //查询Sku结果集 (包括颜色) 有货
    public List<Sku> selectSkuListByProductId(Long productId);
}

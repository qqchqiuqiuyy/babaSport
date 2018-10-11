/**
 * 
 */
package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.common.page.Paginable;
import cn.itcast.core.bean.product.Brand;
import cn.itcast.core.bean.product.BrandQuery;

/**
 * @author bobo
 *
 */
public interface BrandService {
    //查询分页对象
    public Paginable selectPaginationByQuery(String name,Integer isDisplay,Integer pageNo);
    
    //查询结果集	
    public List<Brand> selectBrandListByQuery(Integer isDisplay);
    //通过ID查询品牌
    public Brand selectBrandById(long id);
    public Brand selectBrand(long id);
    //修改
    public void updateBrandById(Brand brand);
  //批量删除
    public void deletes(long[] ids);
    
    //查询 从Redis中
    public List<Brand> selectBrandListFromRedis();
  
}

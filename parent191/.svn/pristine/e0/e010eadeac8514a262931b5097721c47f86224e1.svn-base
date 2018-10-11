/**
 * 
 */
package cn.itcast.core.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.service.product.SkuService;


/**
 * 库存管理
 * 去库存页面
 * 修改
 * 保存
 * @author bobo
 *
 */
@Controller
public class SkuController {
    
    @Autowired
    private SkuService skuService;
    //去库存页面
    @RequestMapping(value = "/sku/list.do")
    public String list(Long productId, Model model){
	
	List<Sku> skus = skuService.selectSkuListByProductId(productId);
	model.addAttribute("skus", skus);
	return "sku/list";
    }
    //修改异步ajax
    @RequestMapping(value = "/sku/addSku.do")
    public  void updateSkuById(Sku sku,HttpServletResponse httpServletResponse) throws IOException{
	skuService.updateSkuById(sku);
	
	JSONObject jsonObject = new JSONObject();
	
	jsonObject.put("message", "保存成功");
	
	httpServletResponse.setContentType("application/json;charset=UTF-8");
	httpServletResponse.getWriter().write(jsonObject.toString());
    }
 
    
    
}


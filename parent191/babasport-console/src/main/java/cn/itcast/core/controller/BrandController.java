/**
 * 
 */
package cn.itcast.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Paginable;
import cn.itcast.core.bean.product.Brand;
import cn.itcast.core.service.product.BrandService;

/**
 * @author bobo
 *
 */
@Controller
public class BrandController {

    @Autowired
    private BrandService brandService;
    
    //查询列表
    @RequestMapping(value = "/brand/list.do")
    public String list(String name,Integer isDisplay,Integer pageNo,Model model){
	
	Paginable pagination= null;
	pagination = brandService.selectPaginationByQuery(name, isDisplay, pageNo);
	
	model.addAttribute("pagination",pagination);
	model.addAttribute("name",name);
	if(isDisplay != null){
	    model.addAttribute("isDisplay",isDisplay);
	}else{
	    model.addAttribute("isDisplay",1);
	}
	
	return "brand/list";
    }
    
    //去修改页面
    @RequestMapping(value = "/brand/toEdit.do")
    public String list(long id,Model model){
	Brand brand = brandService.selectBrandById(id); //shift alt L
	model.addAttribute("brand", brand);
	return "brand/edit";
    }
    
    //修改
    @RequestMapping(value = "/brand/edit.do")
    public String edit(Brand brand){
	brandService.updateBrandById(brand);
	return "redirect:/brand/list.do";
    }
    
  //删除
    @RequestMapping(value = "/brand/deletes.do")
    public String deletes(long[] ids){
	brandService.deletes(ids);
	
	return "forward:/brand/list.do";
    }
    
    
}

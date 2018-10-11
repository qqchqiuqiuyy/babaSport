/**
 * 
 */
package cn.itcast.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author bobo
 *
 */
@Controller
@RequestMapping(value = "/control")
public class CenterController {

    /*
     * ModelAndView :跳转视图 + 数据
     * void : 异步时ajax
     * String : 跳转视图 + MOdel
     */
    
  //入口
    @RequestMapping(value = "/index.do")
    public String ToIndex(Model model){
	return "index";
    }
    @RequestMapping(value = "/top.do")
    public String ToTop(Model model){
	return "top";
    }
    @RequestMapping(value = "/main.do")
    public String ToMain(Model model){
	return "main";
    }
    @RequestMapping(value = "/left.do")
    public String ToLeft(Model model){
	return "left";
    }
    @RequestMapping(value = "/right.do")
    public String ToRight(Model model){
	return "right";
    }
    
    @RequestMapping(value = "/frame/product_main.do")
    public String ToProduct_main(Model model){
	return "frame/product_main";
    }
    @RequestMapping(value = "/frame/product_left.do")
    public String ToProduct_left(Model model){
	return "frame/product_left";
    }
    @RequestMapping(value = "/product/list.do")
    public String ToProductList(Model model){
	return "product/list";
    }
    
    
    
  /*  @Autowired
    private TestTbService testTbservice;
    
    
    @RequestMapping(value = "/test/index.do")
    public String index(Model model){
	TestTb testTb = new TestTb();
	testTb.setName("fbb");
	testTbservice.insertTestTb(testTb);
	return "index";
    }*/

    
    
 
}

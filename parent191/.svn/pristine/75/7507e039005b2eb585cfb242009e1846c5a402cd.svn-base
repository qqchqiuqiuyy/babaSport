/**
 * 
 */
package cn.itcast.core.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.mysql.jdbc.PingTarget;

import cn.itcast.common.web.Constants;
import cn.itcast.core.service.product.UploadService;

/**
 * @author bobo
 *
 *上传图片
 *
 */
@Controller
public class UploadController {
    
    @Autowired
    private UploadService uploadService;
    
    
    //上传图片
    @RequestMapping(value = "/upload/uploadPic.do")
    public void uploadPic(@RequestParam(required = false) MultipartFile pic, HttpServletResponse response) throws IOException{ 

	    String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
	    
	    String url = Constants.IMG_URL + path;
	
	    JSONObject jObject = new JSONObject();
	    jObject.put("url", url);
	    response.setContentType("application/json;charset=UTF-8");
	    response.getWriter().write(jObject.toString());;
    }
    
    //富文本图片
    @RequestMapping(value = "/upload/uploadFck.do")
    public void uploadFck(HttpServletRequest request,HttpServletResponse response) throws IOException{
	//无敌版接受
	//强转Spring提供MultipartRequest
	MultipartRequest mRequest = (MultipartRequest)request;
	Map<String, MultipartFile> fileMap = mRequest.getFileMap();
	Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
	for(Entry<String, MultipartFile> entry : entrySet){
	    MultipartFile pic = entry.getValue();
	    String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(),pic.getSize());
	    
	    String url = Constants.IMG_URL + path;
	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("error", 0);
	    jsonObject.put("url", url);
	    
	    response.setContentType("application/json;charset=UTF-8");
	    response.getWriter().write(jsonObject.toString());
	}

    }
    
    //批量上传图片
    @RequestMapping(value = "/upload/uploadPics.do")
    public @ResponseBody
    List<String> uploadPics(@RequestParam(required = false) MultipartFile[] pics, HttpServletResponse response) throws IOException{ 
		
	List<String> urls = new ArrayList<String>();
	for(MultipartFile pic: pics){
	    String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
	    String url = Constants.IMG_URL + path;
	
	    urls.add(url);
	}
	return urls;
	   
    }
    
    
    
    
    
    
    
}

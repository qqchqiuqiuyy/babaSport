/**
 * 
 */
package cn.itcast.common.conversion;

import org.springframework.core.convert.converter.Converter;

/**
 * 自定义转换器
 * 去掉前后空格
 * <S, T> : S页面上的类型  T是转换后类型
 * @author bobo
 *
 */
public class CustomConverter implements Converter<String, String>{

    //去掉前后空格
    @Override
    public String convert(String source) {
	try {
	    if (source != null) {
		source = source.trim();
		if(!"".equals(source)){
		    return source;
		}
	    }
	} catch (Exception e) {
	    // TODO: handle exception
	}
	
	return null;
    }

}

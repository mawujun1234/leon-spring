package com.mawujun.controller.spring.mvc.exception;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.mawujun.exception.BusinessException;

public class MappingExceptionResolver extends SimpleMappingExceptionResolver {
	
	private String errorMsgAttribute = "errorMsg";//异常消息的属性名称，可以自定义,也就是对异常进行文字化描述，而不是其他信息
	private String defaultErrorMsg="操作失败，请稍候重试!如果多次操作无效,请联系管理员!";
	/**
	 * key是viewname，value是错误消息
	 */
	private Map<Class<? extends Exception>, String> errorMsgs = new HashMap<Class<? extends Exception>, String>();
	
//	public void setErrorMsgs(Properties errorMsgs) {
//		for (Enumeration<?> enumeration = errorMsgs.propertyNames(); enumeration.hasMoreElements();) {
//			String viewName = (String) enumeration.nextElement();
//			String errorMsg = errorMsgs.getProperty(viewName);
//			this.errorMsgs.put(viewName, errorMsg);
//		}
//	}

	/**
	 * 设置对应的viewname中的异常信息
	 * 
	 */
	public void addErrorMsg(Class<? extends Exception> exception, String errorMsg) {
		this.errorMsgs.put(exception, errorMsg);
	}
	/**
	 * 返回默认的错误消息
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param request
	 * @param viewName
	 * @return
	 */
	protected String determineErrorMsg( String viewName, Exception ex) {
		if(ex instanceof BusinessException){
			return ((BusinessException)ex).getMessage();
		} else if (this.errorMsgs.containsKey(ex.getClass())) {
			return this.errorMsgs.get(ex.getClass());
		} else if(ex.getMessage()!=null && !"".equals(ex.getMessage())){
			return ex.getMessage();
		} 
		//只有在ExceptionMappings没有定义过的时候才会走到这里
		return this.defaultErrorMsg;
		
//		if(ex instanceof BusinessException){
//			return ((BusinessException)ex).getMessage();
//		} else if (this.errorMsgs.containsKey(viewName)) {
//			return this.errorMsgs.get(viewName);
//		} else if(ex.getMessage()!=null && !"".equals(ex.getMessage())){
//			return ex.getMessage();
//		} 
//		//只有在ExceptionMappings没有定义过的时候才会走到这里
//		return this.defaultErrorMsg;
	}
	
	@Override
	protected ModelAndView getModelAndView(String viewName, Exception ex) {

		//调用父的getModelAndView获取到viewname，并且把异常信息放到exception属性中
		ModelAndView mv=super.getModelAndView(viewName, ex);//是否增加，一个条件，如果是移动端的话，就不返回异常stack，只返回异常信息
		String errorMsg=determineErrorMsg(viewName, ex);
		mv.addObject(this.errorMsgAttribute, errorMsg);
		return mv;
	}

	public void setErrorMsgAttribute(String errorMsgAttribute) {
		this.errorMsgAttribute = errorMsgAttribute;
	}

}

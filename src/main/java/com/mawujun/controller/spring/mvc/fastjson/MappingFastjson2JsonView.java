package com.mawujun.controller.spring.mvc.fastjson;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.AbstractView;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.exception.BusinessException;
import com.mawujun.exception.ExceptionCode;

/**
 * 处理headers:{ 'Accept':'application/json;'}的异常和正常信息返回
 * 走的是view的路线
 * @author mawujun email:160649888@163.com qq:16064988
 *
 */
public class MappingFastjson2JsonView extends AbstractView {
	public static final String DEFAULT_CONTENT_TYPE = "application/json";
	
	private Set<String> modelKeys;
	
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private boolean extractValueFromSingleKeyModel = true;
	
	final static Logger logger = LoggerFactory.getLogger(MappingFastjson2JsonView.class);   
	
	ResourceBundle bundle = ResourceBundle.getBundle("com.mawujun.exception.exceptions");
	
	public MappingFastjson2JsonView() {
		setContentType(DEFAULT_CONTENT_TYPE);
		setExposePathVariables(false);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding(DEFAULT_CHARSET.toString());
		response.setContentType(DEFAULT_CONTENT_TYPE);
		// TODO Auto-generated method stub
		if(model.get(SimpleMappingExceptionResolver.DEFAULT_EXCEPTION_ATTRIBUTE)!=null){
			Exception exception=(Exception)model.get(SimpleMappingExceptionResolver.DEFAULT_EXCEPTION_ATTRIBUTE);
			Map<String,Object> map=new  HashMap<String, Object>();
			map.put(JsonConfigHolder.getSuccessName(), false);

			if(exception instanceof BusinessException){
				if( exception.getMessage()!=null){
					map.put(JsonConfigHolder.getMsgName(), exception.getMessage());
				} else {
					ExceptionCode errorCode=((BusinessException) exception).getErrorCode();
					String key = errorCode.getClass().getSimpleName() + "." + errorCode;
					try {
						map.put(JsonConfigHolder.getMsgName(), bundle.getString(key));
					} catch(MissingResourceException e){
						map.put(JsonConfigHolder.getMsgName(),exception.getMessage());
					}
				}
				
				

				//还要监听验证异常，从hibernate后台抛出来的
			} else if(exception instanceof ConstraintViolationException ){
				ConstraintViolationException ex=(ConstraintViolationException)exception;
				//map.put(JsonConfigHolder.getMsgName(),ex.getMessage());
				
				//StringBuffer detailMsg=new StringBuffer();
				Map<String,String> errorsMap=new HashMap<String,String>();
				Set<? extends ConstraintViolation> constraintViolations=ex.getConstraintViolations();
				for (ConstraintViolation violation : constraintViolations) {
					//detailMsg.append(","+violation.getPropertyPath().toString()+":\""+violation.getMessage()+"\"");
					errorsMap.put(violation.getPropertyPath().toString(), violation.getMessage());
				}
				
				map.put(JsonConfigHolder.getErrorsName(),errorsMap);
			} 
			else {
				
				//map.put(JsonConfigHolder.getMsgName(), exception.getMessage());
				map.put(JsonConfigHolder.getMsgName(), "后台发生系统异常，请联系管理员或重试!");
			}
			//model=map;
			
			logger.error(exception.getMessage(),exception);
			
			JsonConfigHolder.setAutoWrap(false);
			String jsonStr=FastJsonToStringUtils.getJsonString(map);
			
			writeToResponse(jsonStr,response);
		} else {
			//其他走JsonView路线的功能
			Object value = filterModel(model);
			String jsonStr=FastJsonToStringUtils.getJsonString(value);
			
			writeToResponse(jsonStr,response);
		}
		JsonConfigHolder.remove();

	}
	
	public void writeToResponse(String jsonStr, HttpServletResponse response) throws IOException{
		Writer write=response.getWriter();
		write.write(jsonStr);
		write.close();
	}
	
	/**
	 * Filter out undesired attributes from the given model.
	 * The return value can be either another {@link Map} or a single value object.
	 * <p>The default implementation removes {@link BindingResult} instances and entries
	 * not included in the {@link #setRenderedAttributes renderedAttributes} property.
	 * @param model the model, as passed on to {@link #renderMergedOutputModel}
	 * @return the value to be rendered
	 */
	protected Object filterModel(Map<String, Object> model) {
		Map<String, Object> result = new HashMap<String, Object>(model.size());
		Set<String> renderedAttributes = (!CollectionUtils.isEmpty(this.modelKeys) ? this.modelKeys : model.keySet());
		for (Map.Entry<String, Object> entry : model.entrySet()) {
			if (!(entry.getValue() instanceof BindingResult) && renderedAttributes.contains(entry.getKey())) {
				result.put(entry.getKey(), entry.getValue());
			}
		}
		return (this.extractValueFromSingleKeyModel && result.size() == 1 ? result.values().iterator().next() : result);
	}

}
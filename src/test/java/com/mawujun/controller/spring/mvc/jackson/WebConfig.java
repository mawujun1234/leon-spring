package com.mawujun.controller.spring.mvc.jackson;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;


@Configuration
@ComponentScan(basePackages="com.mawujun.controller.spring.mvc.jackson",
	includeFilters = @Filter(type = FilterType.ANNOTATION, value = {Controller.class}))
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	/**
	 * 主要用于@ResponseBody和@RequestBody的时候，或者请求发过来的content-type是applicaiton/json的时候
	 */
	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                //.modulesToInstall(new ParameterNamesModule());
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));

    }
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.mediaType("json", MediaType.APPLICATION_JSON);
	}


	/**
	 * 视图解析器
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		//默认使用jackson作为视图解析器
		registry.enableContentNegotiation(new MappingJackson2JsonView());
		registry.jsp();
	}

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/bootstrap/**").addResourceLocations("/bootstrap/").setCachePeriod(31556926);
//        registry.addResourceHandler("/echarts-2.2.7/**").addResourceLocations("/echarts-2.2.7/").setCachePeriod(31556926);
//        registry.addResourceHandler("/jquery/**").addResourceLocations("/jquery/").setCachePeriod(31556926);
//        registry.addResourceHandler("/static/**").addResourceLocations("/static/").setCachePeriod(31556926);
    }



}

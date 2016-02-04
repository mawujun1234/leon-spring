package com.mawujun.controller.spring.mvc.jackson;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JacksonController {

	@RequestMapping("/jackson/param_bind1.do")
	public Jackson param_bind(Jackson jackson){
//		System.out.println(jackson.getId());
//		System.out.println(jackson.getName());
//		System.out.println(jackson.getCreateDate());
//		System.out.println(jackson.getOtherDate());
		return jackson;
		
	}
	
	@RequestMapping("/jackson/param_bind2.do")
	public Jackson param_bind(Integer id,String name,@DateTimeFormat(iso=ISO.DATE)Date createDate,
			@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")Date otherDate){
//		System.out.println(createDate);
//		System.out.println(otherDate);
		Jackson jackson=new Jackson();
		jackson.setId(id);
		jackson.setName(name);
		jackson.setCreateDate(createDate);
		jackson.setOtherDate(otherDate);
		return jackson;
		
	}
	
	@RequestMapping("/jackson/jsonp.do")
	public Jackson jsonp(Integer id,String name,@DateTimeFormat(iso=ISO.DATE)Date createDate,Date otherDate){
		Jackson jackson=new Jackson();
		jackson.setId(id);
		jackson.setName(name);
		jackson.setCreateDate(createDate);
		jackson.setOtherDate(otherDate);
		return jackson;
		
	}
	
	
	@RequestMapping("/jackson/voidreturn.do")
	public void voidreturn(Integer id,String name,@DateTimeFormat(iso=ISO.DATE)Date createDate,Date otherDate){
		Jackson jackson=new Jackson();
		jackson.setId(id);
		jackson.setName(name);
		jackson.setCreateDate(createDate);
		jackson.setOtherDate(otherDate);
		return;
		
	}
	
	@RequestMapping("/jackson/stringreturn.do")
	public String stringreturn(Integer id,String name,@DateTimeFormat(iso=ISO.DATE)Date createDate,Date otherDate){
		Jackson jackson=new Jackson();
		jackson.setId(id);
		jackson.setName(name);
		jackson.setCreateDate(createDate);
		jackson.setOtherDate(otherDate);
		return "success";
		
	}
	
	@RequestMapping("/jackson/extenProp.do")
	public String extenProp(Integer id,String name,@DateTimeFormat(iso=ISO.DATE)Date createDate,Date otherDate,Model model){
		Jackson jackson=new Jackson();
		jackson.setId(id);
		jackson.setName(name);
		jackson.setCreateDate(createDate);
		jackson.setOtherDate(otherDate);
		
		model.addAttribute("extenProp", 1);
		model.addAttribute("jackson", jackson);
		return "success";
		
	}

	
	
	@RequestMapping("/jackson/exception.do")
	public void exception() throws Exception{
		throw new Exception();
		
	}
	
	@RequestMapping("/jackson/exception1.do")
	public void exception1() throws Exception{
		throw new Exception("系统发生异常1");
		
	}

	@RequestMapping("/jackson/message_bind1.do")
	@ResponseBody
	public Jackson message_bind1(@RequestBody Jackson jackson){
		return jackson;
		
	}
	@RequestMapping("/jackson/message_bind2.do")
	public Jackson message_bind2(@RequestBody Jackson jackson){
		return jackson;
		
	}
	
	@RequestMapping("/jackson/message_jsonp.do")
	@ResponseBody
	public MappingJacksonValue message_jsonp(@RequestBody Jackson jackson,String callback){
		MappingJacksonValue aa=new MappingJacksonValue(jackson);
		aa.setJsonpFunction(callback);
		
		return aa;
		
	}
	
	@RequestMapping("/jackson/message_voidreturn.do")
	@ResponseBody
	public String message_voidreturn(@RequestBody Jackson jackson){

		return "success";
		
	}
	
	@RequestMapping("/jackson/message_exception.do")
	@ResponseBody
	public void message_exception() throws Exception{
		throw new Exception();
		
	}
	
	@RequestMapping("/jackson/message_extenProp.do")
	@ResponseBody
	public String message_extenProp(Integer id,String name,@DateTimeFormat(iso=ISO.DATE)Date createDate,Date otherDate,Model model){
		Jackson jackson=new Jackson();
		jackson.setId(id);
		jackson.setName(name);
		jackson.setCreateDate(createDate);
		jackson.setOtherDate(otherDate);
		
		model.addAttribute("extenProp", 1);
		return "success";
		
	}
	
	@RequestMapping("/jackson/message_extenProp1.do")
	@ResponseBody
	public Model message_extenProp1(Integer id,String name,@DateTimeFormat(iso=ISO.DATE)Date createDate,Date otherDate,Model model){
		Jackson jackson=new Jackson();
		jackson.setId(id);
		jackson.setName(name);
		jackson.setCreateDate(createDate);
		jackson.setOtherDate(otherDate);
		
		model.addAttribute("extenProp", 1);
		model.addAttribute("jackson", jackson);
		return model;
		
	}
	
}

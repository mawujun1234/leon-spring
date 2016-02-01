package com.mawujun.controller.spring.mvc.jackson;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JacksonController {

	@RequestMapping("/jackson/param_bind.do")
	public Jackson param_bind(Jackson jackson){
		System.out.println(jackson.getId());
		System.out.println(jackson.getName());
		System.out.println(jackson.getCreateDate());
		return jackson;
		
	}
}

package com.mawujun.controller.spring.mvc.jackson;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mawujun.utils.string.StringUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={MvcConfig.class})
public class JacksonControllerTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void param_bind1() throws Exception {//?id=1&name=11&createDate=2015-02-01
        this.mockMvc.perform(get("/jackson/param_bind1.do").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
        		.param("id", "1").param("name", "11").param("createDate", "2015-02-02").param("otherDate", "2015-02-01 12:12:12")
        	)
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.jackson.name").value("11"))
            .andExpect(jsonPath("$.jackson.id").value(1))
            .andExpect(jsonPath("$.jackson.createDate").value("2015-02-02"))
            .andExpect(jsonPath("$.jackson.otherDate").value("2015-02-01 12:12:12"));
    }
    @Test
    public void param_bind2() throws Exception {//?id=1&name=11&createDate=2015-02-01
        this.mockMvc.perform(get("/jackson/param_bind2.do").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
        		.param("id", "1").param("name", "11").param("createDate", "2015-02-02").param("otherDate", "2015-02-01 12:12:12")
        	)
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.jackson.name").value("11"))
            .andExpect(jsonPath("$.jackson.id").value(1))
            .andExpect(jsonPath("$.jackson.createDate").value("2015-02-02"))
            .andExpect(jsonPath("$.jackson.otherDate").value("2015-02-01 12:12:12"));
    }
    
    @Test
    public void jsonp() throws Exception {//?id=1&name=11&createDate=2015-02-01
        this.mockMvc.perform(get("/jackson/jsonp.do").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
        		.param("id", "1").param("name", "11").param("createDate", "2015-02-02").param("otherDate", "2015-02-01 12:12:12")
        		.param("callback", "callback111")
        	)
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/javascript"))
            //.andExpect(content().string("callback111({\n  \"jackson\" : {\n    \"id\" : 1,\n    \"name\" : \"11\",\n    \"createDate\" : \"2015-02-02\",\n    \"otherDate\" : \"2015-02-01 12:12:12\"\n  }\n});"));
            .andExpect(new ResultMatcher() {
    			@Override
    			public void match(MvcResult result) throws Exception {
    				AssertionErrors.assertEquals("Response content",
    						"callback111({\"jackson\":{\"id\":1,\"name\":\"11\",\"createDate\":\"2015-02-02\",\"otherDate\":\"2015-02-0112:12:12\"}});",
    						StringUtils.trimAllWhitespace(result.getResponse().getContentAsString()));
    			}
    		});
    }
    
    @Test
    public void voidreturn() throws Exception {
    	this.mockMvc.perform(get("/jackson/voidreturn.do").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
        		.param("id", "1").param("name", "11").param("createDate", "2015-02-02").param("otherDate", "2015-02-01 12:12:12")
        	)
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(content().string("{ }"));
    }
    
    @Test
    public void stringreturn() throws Exception {
    	this.mockMvc.perform(get("/jackson/stringreturn.do").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
        		.param("id", "1").param("name", "11").param("createDate", "2015-02-02").param("otherDate", "2015-02-01 12:12:12")
        	)
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(view().name("success"));
    }
    
    @Test
    public void extenProp() throws Exception {
    	this.mockMvc.perform(get("/jackson/extenProp.do").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
        		.param("id", "1").param("name", "11").param("createDate", "2015-02-02").param("otherDate", "2015-02-01 12:12:12")
        	)
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(view().name("success"))
            .andExpect(jsonPath("$.extenProp").value(1))
            .andExpect(jsonPath("$.jackson.name").value("11"))
            .andExpect(jsonPath("$.jackson.id").value(1))
            .andExpect(jsonPath("$.jackson.createDate").value("2015-02-02"))
            .andExpect(jsonPath("$.jackson.otherDate").value("2015-02-01 12:12:12"));

    }
    

    
    @Test
    public void message_extenProp() throws Exception {
    	this.mockMvc.perform(get("/jackson/message_extenProp.do").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
        		.param("id", "1").param("name", "11").param("createDate", "2015-02-02").param("otherDate", "2015-02-01 12:12:12")
        	)
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(content().string("\"success\""))
            .andExpect(jsonPath("$.extenProp").doesNotExist());

    }
    
    @Test
    public void message_extenProp1() throws Exception {
    	this.mockMvc.perform(get("/jackson/message_extenProp1.do").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
        		.param("id", "1").param("name", "11").param("createDate", "2015-02-02").param("otherDate", "2015-02-01 12:12:12")
        	)
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.extenProp").value(1))
            .andExpect(jsonPath("$.jackson.name").value("11"))
            .andExpect(jsonPath("$.jackson.id").value(1))
            .andExpect(jsonPath("$.jackson.createDate").value("2015-02-02"))
            .andExpect(jsonPath("$.jackson.otherDate").value("2015-02-01 12:12:12"));

    }
    
    
    //测试异常信息没有指定的时候，即使用默认异常
    @Test
    public void exception() throws Exception {
    	this.mockMvc.perform(get("/jackson/exception.do").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            //.andExpect(content().string("系统发生异常"))
            .andExpect(jsonPath("$.exception").exists())
            .andExpect(jsonPath("$.errorMsg").value("系统发生异常"));
    }
    
    //测试异常msg自己指定
    @Test
    public void exception1() throws Exception {
    	this.mockMvc.perform(get("/jackson/exception1.do").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            //.andExpect(content().string("系统发生异常"))
            .andExpect(jsonPath("$.exception").exists())
            .andExpect(jsonPath("$.errorMsg").value("系统发生异常"));
    }
    
    @Test
    public void exception_notjson() throws Exception {
    	this.mockMvc.perform(get("/jackson/exception.do"))
            .andExpect(status().is5xxServerError())
            .andExpect(view().name("common_error"))
            .andExpect(model().attributeExists("exception"))
            .andExpect(model().attributeExists("errorMsg"));
    }
    

    //=============================================================
    @Test
    public void message_bind1() throws Exception {//?id=1&name=11&createDate=2015-02-01
        this.mockMvc.perform(post("/jackson/message_bind1.do").contentType(MediaType.APPLICATION_JSON).accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
        		.content("{id:1,name:11,createDate:'2015-02-02',otherDate:'2015-02-01 12:12:12'}")
        	)
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            //.andExpect(content().string("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.name").value("11"))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.createDate").value("2015-02-02"))
            .andExpect(jsonPath("$.otherDate").value("2015-02-01 12:12:12"));
    }
    

    /**
     * 请求时候是RequestBody，返回的时候不是ResponseBody
     * @author mawujun email:160649888@163.com qq:16064988
     * @throws Exception
     */
    @Test
    public void message_bind2() throws Exception {//?id=1&name=11&createDate=2015-02-01
        this.mockMvc.perform(post("/jackson/message_bind2.do").contentType(MediaType.APPLICATION_JSON).accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
        		.content("{id:1,name:11,createDate:'2015-02-02',otherDate:'2015-02-01 12:12:12'}")
        	)
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            //.andExpect(content().string("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.jackson.name").value("11"))
            .andExpect(jsonPath("$.jackson.id").value(1))
            .andExpect(jsonPath("$.jackson.createDate").value("2015-02-02"))
            .andExpect(jsonPath("$.jackson.otherDate").value("2015-02-01 12:12:12"));
    }
    
  @Test
  public void message_jsonp() throws Exception {//?id=1&name=11&createDate=2015-02-01
	    this.mockMvc.perform(post("/jackson/message_jsonp.do").contentType(MediaType.APPLICATION_JSON).accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
			.content("{id:1,name:11,createDate:'2015-02-02',otherDate:'2015-02-01 12:12:12'}")
			.param("callback", "callback111")
		)
          .andExpect(status().isOk())
          .andExpect(content().contentType("application/json;charset=UTF-8"))
          .andExpect(new ResultMatcher() {
  			@Override
  			public void match(MvcResult result) throws Exception {
  				AssertionErrors.assertEquals("Response content",
  						"callback111({\"id\":1,\"name\":\"11\",\"createDate\":\"2015-02-02\",\"otherDate\":\"2015-02-0112:12:12\"});",
  						StringUtils.trimAllWhitespace(result.getResponse().getContentAsString()));
  			}
  		});
  }
  
  @Test
  public void message_jsonp1() throws Exception {//?id=1&name=11&createDate=2015-02-01
	    this.mockMvc.perform(post("/jackson/message_jsonp.do").contentType(MediaType.APPLICATION_JSON).accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
			.content("{id:1,name:11,createDate:'2015-02-02',otherDate:'2015-02-01 12:12:12'}")
			//.param("callback", "callback111")
		)
          .andExpect(status().isOk())
          .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.name").value("11"))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.createDate").value("2015-02-02"))
            .andExpect(jsonPath("$.otherDate").value("2015-02-01 12:12:12"));
  }
  
	@Test
	public void message_voidreturn() throws Exception {
		this.mockMvc.perform(get("/jackson/message_voidreturn.do").contentType(MediaType.APPLICATION_JSON).accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
				.content("{id:1,name:11,createDate:'2015-02-02',otherDate:'2015-02-01 12:12:12'}")
	    	)
	        .andExpect(status().isOk())
	        .andExpect(content().contentType("application/json;charset=UTF-8"))
	        .andExpect(content().string("\"success\""));
	}
    
  @Test
  public void message_exception() throws Exception {
  	this.mockMvc.perform(get("/jackson/message_exception.do").contentType(MediaType.APPLICATION_JSON).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
          .andExpect(status().is5xxServerError())
          .andExpect(content().contentType("application/json;charset=UTF-8"))
          //.andExpect(content().string("系统发生异常"))
          .andExpect(jsonPath("$.exception").exists())
          .andExpect(jsonPath("$.errorMsg").value("系统发生异常"));
  }


}

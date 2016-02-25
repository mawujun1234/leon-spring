package com.mawujun.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class GreetingController {
	
	@Autowired
	private SimpMessagingTemplate template;


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
    	//System.out.println(message.getName());
    	 if(true){
         	//throw new RuntimeException("测试异常!");
         }
        Thread.sleep(3000); // simulated delay
       
        
        return new Greeting("Hello,11111 " + message.getName() + "!");
    }
    
    @RequestMapping("/sendmsg.do")
    public void sendmsg(String greeting) {
        String text = "{\"content\":\"测试消息\"}";
        this.template.convertAndSend("/topic/greetings", text);
        //System.out.println("11111111111");
    }
    
//    @MessageExceptionHandler
//	@SendToUser("/topic/errors")
//	public String handleException(Throwable exception) {
//		return exception.getMessage();
//	}



}

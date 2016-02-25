package com.mawujun.controller.spring.mvc.jackson;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @EnableAsync 开启异步任务方法调用
 * @EnableScheduling  开启定时调用
 * @author mawujun
 *
 */
@Configuration
@EnableAsync
@EnableScheduling
public class TaskScheduleConfig implements SchedulingConfigurer ,AsyncConfigurer{
	//http://www.cnblogs.com/interdrp/p/3599917.html
	//http://blog.csdn.net/blueheart20/article/details/44648667
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		// TODO Auto-generated method stub
		taskRegistrar.setScheduler(taskExecutor());
	}

	@Bean(destroyMethod="shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(5);
    }
	/**
	 * <beans>
     <task:annotation-driven executor="myExecutor" exception-handler="exceptionHandler"/>
     <task:executor id="myExecutor" pool-size="7-42" queue-capacity="11"/>
     <bean id="asyncBean" class="com.foo.MyAsyncBean"/>
     <bean id="exceptionHandler" class="com.foo.MyAsyncUncaughtExceptionHandler"/>
 </beans>
	 */
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(8);
        //executor.setThreadNamePrefix("MyExecutor-");
        executor.initialize();
        return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		// TODO Auto-generated method stub
		return null;
	}

}

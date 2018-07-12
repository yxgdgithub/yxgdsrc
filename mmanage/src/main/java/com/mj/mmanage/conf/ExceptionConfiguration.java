package com.mj.mmanage.conf;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.mj.mmanage.core.AppMappingExceptionResolver;

@Configuration
@EnableWebMvc
public class ExceptionConfiguration extends WebMvcConfigurerAdapter {
	@Bean(name="simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
		AppMappingExceptionResolver r = new AppMappingExceptionResolver();
		
		r.setOrder(Ordered.HIGHEST_PRECEDENCE);//设为最优先处理
		
        Properties mappings = new Properties();
        /*mappings.setProperty("Exception","exception");
        mappings.setProperty("DatabaseException", "databaseError");
        mappings.setProperty("InvalidCreditCardException", "creditCardError");*/
        
        mappings.setProperty(NoHandlerFoundException.class.getName(),"404");

        r.setExceptionMappings(mappings);  // 默认为空
        r.setDefaultErrorView("error");    // 默认没有
        r.setExceptionAttribute("ex"); 
        r.setWarnLogCategory("example.MvcLogger");  //将异常交给log4j来记录到文件
        return r;
    }
}

package com.mj.mmanage.conf;

import java.util.List;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Configuration
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter {
	@Bean(name="simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
		SelfMappingExceptionResolver r = new SelfMappingExceptionResolver();

        /*Properties mappings = new Properties();
        mappings.setProperty("Exception","exception");
        mappings.setProperty("DatabaseException", "databaseError");
        mappings.setProperty("InvalidCreditCardException", "creditCardError");

        r.setExceptionMappings(mappings);  // 默认为空
*/        r.setDefaultErrorView("error");    // 默认没有
        r.setExceptionAttribute("ex"); 
        r.setWarnLogCategory("example.MvcLogger"); 
        return r;
    }
	
	

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// TODO Auto-generated method stub
		converters.clear();
		converters.add(new GdHttpMessageConverter());
		//super.extendMessageConverters(new GdHttpMessageConverter());
	}



/*	@Bean(name="gdHttpMessageConverter")
	public GdHttpMessageConverter httpMessageConverter(){
		return new GdHttpMessageConverter();
	}*/
	
}

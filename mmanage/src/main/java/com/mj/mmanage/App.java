package com.mj.mmanage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;


/**
 * Hello world!
 *
 */
@Controller
@EnableWebMvc
@SpringBootApplication
public class App  extends WebMvcConfigurerAdapter
{
	
	private static Logger logger = LogManager.getLogger(App.class);
	
	@RequestMapping("/")
	public String home(){
		return "redirect:login/toLogin";
		//return "index";
	}
	
    public static void main( String[] args )
    {
    	logger.info("系统启动......");
    	
        SpringApplication.run(App.class, args);
       
        
        logger.info("系统启动成功...");
    }
    
    
}

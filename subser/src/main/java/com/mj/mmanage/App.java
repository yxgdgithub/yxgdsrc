package com.mj.mmanage;


import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Hello world!
 *
 */
@Controller
@EnableWebMvc
@SpringBootApplication
public class App  extends WebMvcConfigurerAdapter
{
	
	private static Logger logger = Logger.getLogger(App.class);
	
	@RequestMapping("/")
	public String home(){
		return "redirect:login/toLogin";
	}
	
    public static void main( String[] args )
    {
    	logger.info("系统开始启动......");
    	System.out.println("系统开始启动......");
        SpringApplication.run(App.class, args);
        
		System.out.println("系统启动成功...");
        logger.info("系统启动成功...");
    }
}

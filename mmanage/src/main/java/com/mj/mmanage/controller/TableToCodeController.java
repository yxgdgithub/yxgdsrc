package com.mj.mmanage.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tables")
public class TableToCodeController {

	
	public String main(){
		return "tableMain";
	}
	
}

/**
 *@Title：ParamaterFilter.java
 *@Package:com.mj.mmanage.conf
 */
package com.mj.mmanage.conf;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * <p> Description:过滤器处理上送参数为空的情况下 变为null </p>
 * <p> Copyright: Copyright (c) 2017 </p>
 * <p> Create Date: 2018-4-18</p>
 * <p> Company: YUSYS </p> 
 *@author:YX-LiAnDong
 *@version:ParamaterFilter,v1.0 YX-LiAnDong 
 */
public class ParamaterFilter implements Filter{

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(new ParaHttpServletRequestWrapper((HttpServletRequest)request), response);
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

 

}

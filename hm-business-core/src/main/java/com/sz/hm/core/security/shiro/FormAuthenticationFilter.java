/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/mokylin/cabal">cabal</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *//*
package com.sz.hm.core.security.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

*//**
 * 表单验证（包含验证码）过滤类
 * @author 稻草鸟人
 * @version 2014-5-19
 *//*
@Service
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	public static final String DEFAULT_USERNAME_PARAM = "mobilePhone";
	public static final String DEFAULT_PASSWORD_PARAM = "password";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private String usernameParam = DEFAULT_USERNAME_PARAM;
	private String passwordParam = DEFAULT_PASSWORD_PARAM;

	public String getCaptchaParam() {
		return captchaParam;
	}
	public String getUsernameParam() {
		return usernameParam;
	}
	public String getPasswordParam() {
		return passwordParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}
	
	protected String getUsername(ServletRequest request) {
		return WebUtils.getCleanParam(request, getUsernameParam());
	}
	
	protected String getPassword(ServletRequest request) {
		return WebUtils.getCleanParam(request, getPasswordParam());
	}

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		System.out.println("拦截login");
		String username = getUsername(request);
		String password = getPassword(request);
		if (password==null){
			password = "";
		}
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		String captcha = getCaptcha(request);
		return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha);
	}

}*/
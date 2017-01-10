package com.sz.hm.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sz.hm.core.base.model.ResultJson;
import com.sz.hm.core.login.service.impl.LoginService;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	/**
	 * 登录
	 * @param mobilePhone
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "check")
	public ResultJson login(String mobilePhone,String password){
		return loginService.login(mobilePhone, password);
	}
}

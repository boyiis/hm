package com.sz.hm.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sz.hm.core.base.model.ResultJson;
import com.sz.hm.core.base.util.UserUtils;
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
	@ResponseBody
	public ResultJson login(String mobilePhone,String password){
		return loginService.login(mobilePhone, password);
	}
	
	/**
	 * test
	 * @param mobilePhone
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "check1")
	@ResponseBody
	public ResultJson login1(){
		ResultJson json = new ResultJson(true);
		json.setMsg(UserUtils.getUser().getId());
		return json;
	}
}

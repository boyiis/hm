package com.sz.hm.login.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
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
		
			UsernamePasswordToken token = new UsernamePasswordToken(mobilePhone, password); 
            Subject currentUser = SecurityUtils.getSubject();  
            if (!currentUser.isAuthenticated()){
                //使用shiro来验证  
                token.setRememberMe(true);  
                currentUser.login(token);//验证角色和权限  
            } 
            currentUser.getSession().setAttribute("mobilePhone", mobilePhone);
		
		return loginService.login(mobilePhone, password);
	}
	
	/**
	 * test
	 * @param mobilePhone
	 * @param password
	 * @return
	 */
	@RequiresUser
	@RequestMapping(value = "check1")
	@ResponseBody
	public ResultJson login1(){
		ResultJson json = new ResultJson(true);
		json.setMsg(String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("mobilePhone")));
		return json;
	}
}

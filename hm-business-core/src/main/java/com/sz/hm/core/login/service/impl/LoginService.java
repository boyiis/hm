package com.sz.hm.core.login.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sz.hm.core.base.model.ResultJson;
import com.sz.hm.core.base.service.CommonAppService;
import com.sz.hm.core.base.util.UserUtils;
import com.sz.hm.core.login.service.ILoginService;
import com.sz.hm.core.user.model.User;
import com.sz.hm.core.user.service.IUserService;
@Service
public class LoginService extends CommonAppService implements ILoginService {
	private static final Logger log = LogManager.getLogger(LoginService.class);
	
	@Autowired
	private IUserService userService;
	@Override
	public ResultJson login(String mobilePhone,String password) {
		User user = userService.findByMobilePhoneAndPassword(mobilePhone, password);
		if(null==user){
			return new ResultJson(false);
		}
		UserUtils.setUser(user);
		log.info(UserUtils.getUser().getId());
		return new ResultJson(true);
	}

}

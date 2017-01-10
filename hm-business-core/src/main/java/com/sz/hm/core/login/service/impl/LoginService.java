package com.sz.hm.core.login.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sz.hm.core.base.model.ResultJson;
import com.sz.hm.core.base.service.CommonAppService;
import com.sz.hm.core.base.util.CollectionUtils;
import com.sz.hm.core.base.util.UserUtils;
import com.sz.hm.core.login.service.ILoginService;
import com.sz.hm.core.user.model.User;
@Service
public class LoginService extends CommonAppService implements ILoginService {
	private static final Logger log = LogManager.getLogger(LoginService.class);
	@Override
	public ResultJson login(String mobilePhone,String password) {
		User user = find(mobilePhone,password);
		if(null==user){
			return new ResultJson(false);
		}
		UserUtils.setUser(user);
		log.info(UserUtils.getUser().getId());
		return new ResultJson(true);
	}
	
	private User find(String mobilePhone,String password){
		String hql = " from User where mobilePhone = ? and password = ?";
		User user = findUnique(hql, CollectionUtils.newList(mobilePhone,password), User.class);
		return user;
	}

}

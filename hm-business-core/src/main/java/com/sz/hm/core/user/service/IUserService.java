package com.sz.hm.core.user.service;

import com.sz.hm.core.user.model.User;

public interface IUserService {
	/**
	 * 通过手机号和密码查用户
	 * @param mobilePhone
	 * @param password
	 * @return
	 */
	User findByMobilePhoneAndPassword(String mobilePhone,String password);
	
	/**
	 * 通过手机号查用户
	 * @param mobilePhone
	 * @return
	 */
	User findByMobilePhone(String mobilePhone);
}

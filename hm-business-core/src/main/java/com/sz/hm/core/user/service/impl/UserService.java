package com.sz.hm.core.user.service.impl;

import org.springframework.stereotype.Service;

import com.sz.hm.core.base.service.CommonAppService;
import com.sz.hm.core.base.util.CollectionUtils;
import com.sz.hm.core.user.model.User;
import com.sz.hm.core.user.service.IUserService;
@Service
public class UserService extends CommonAppService implements IUserService{

	@Override
	public User findByMobilePhoneAndPassword(String mobilePhone, String password) {
		String hql = " from User where mobilePhone = ? and password = ?";
		return findUnique(hql, CollectionUtils.newList(mobilePhone,password), User.class);
	}

	@Override
	public User findByMobilePhone(String mobilePhone) {
		String hql = " from User where mobilePhone = ?";
		return findUnique(hql, CollectionUtils.newList(mobilePhone), User.class);
	}

}

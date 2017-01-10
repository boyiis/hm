package com.sz.hm.core.login.service;

import com.sz.hm.core.base.model.ResultJson;

public interface ILoginService {
	ResultJson login(String mobilePhone,String password);
}

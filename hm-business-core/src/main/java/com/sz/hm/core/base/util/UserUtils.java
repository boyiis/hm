package com.sz.hm.core.base.util;

import com.sz.hm.core.user.model.User;

public class UserUtils {
	private static ThreadLocal<User> threadLocalUserInfo = new ThreadLocal<User>();
	
	public static User getUser() {
		return threadLocalUserInfo.get();
	}

	public static void setUser(User user) {
		threadLocalUserInfo.set(user);
	}
}

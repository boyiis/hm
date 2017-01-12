/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/mokylin/cabal">cabal</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sz.hm.core.security.shiro;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sz.hm.core.base.util.Encodes;
import com.sz.hm.core.user.model.User;
import com.sz.hm.core.user.service.IUserService;

/**
 * 系统安全认证实现类
 * @author 稻草鸟人
 * @version 2014-5-29
 */
@Service
public class SystemAuthorizingRealm extends AuthorizingRealm {
	
	@Autowired
	private IUserService userService;

	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		System.out.println("登录验证");
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
/*		
		if (LoginController.isValidateCodeLogin(token.getUsername(), false, false)){
			// 判断验证码
			Session session = SecurityUtils.getSubject().getSession();
			String code = (String)session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
			if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)){
				throw new CaptchaException("验证码错误.");
			}
		}*/
		
		User user = userService.findByMobilePhone(token.getUsername());
		if (user != null) {
			//byte[] salt = Encodes.decodeHex(user.getPassword().substring(0,16));
			return new SimpleAuthenticationInfo(user.getMobilePhone(), 
					user.getPassword(), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String mobilePhone = (String) principals.fromRealm(getName()).iterator().next();
		//Principal principal = (Principal) getAvailablePrincipal(principals);
		User user = userService.findByMobilePhone(mobilePhone);
		if (user != null) {
			//UserUtils.putCache("user", user);
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			/*List list = 
			for (Menu menu : list){
				if (StringUtils.isNotBlank(menu.getPermission())){
					// 添加基于Permission的权限信息
					for (String permission : StringUtils.split(menu.getPermission(),",")){
						info.addStringPermission(permission);
					}
				}
			}*/
			// 更新登录IP和时间
			
			return info;
		} else {
			return null;
		}
	}
	
	/**
	 * 设定密码校验的Hash算法与迭代次数
	 *//*
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("SHA-1");
		matcher.setHashIterations(1024);
		setCredentialsMatcher(matcher);
	}
	*/
	/**
	 * 清空用户关联权限认证，待下次使用时重新加载
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清空所有关联认证
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}


	
	/**
	 * 授权用户信息
	 *//*
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private String id;
		private String loginName;
		private String name;
		private Map<String, Object> cacheMap;

		public Principal(User user) {
			this.id = user.getId();
			this.loginName = user.getMobilePhone();
			this.name = user.getMobilePhone();
		}

		public String getId() {
			return id;
		}

		public String getLoginName() {
			return loginName;
		}

		public String getName() {
			return name;
		}

		public Map<String, Object> getCacheMap() {
			if (cacheMap==null){
				cacheMap = new HashMap<String, Object>();
			}
			return cacheMap;
		}

	}*/
}

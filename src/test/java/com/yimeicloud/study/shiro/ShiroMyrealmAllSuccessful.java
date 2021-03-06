package com.yimeicloud.study.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroMyrealmAllSuccessful {
	private static final transient Logger log = LoggerFactory.getLogger(ShiroMyrealmAllSuccessful.class);

	// 所有方法执行一次
	@BeforeClass
	public static void beforeClass() {
	};

	// 所有方法执行一次
	@AfterClass
	public static void afterClass() {
	};

	// 每个测试方法执行之前都要执行一次。
	@Before
	public void before() {
	}

	// 每个测试方法执行之后要执行一次。
	@After
	public void after() {
	}

	@Test
	public void runTest() {

		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-myrealm-all-success.ini");

		SecurityManager securityManager = factory.getInstance();

		// 将SecurityManager设置到SecurityUtils 方便全局使用
		SecurityUtils.setSecurityManager(securityManager);

		// 获取当前用户
		Subject currentUser = SecurityUtils.getSubject();

		// 创建用户登录凭证
		UsernamePasswordToken token = new UsernamePasswordToken("myrealm", "myrealm");

		// 登入
		try {
			log.info("认证用户：" + token.getUsername());
			currentUser.login(token);
		} catch (UnknownAccountException e) {
			log.info("用户名错误");
		} catch (IncorrectCredentialsException e) {
			log.info("密码错误");
		} catch (LockedAccountException e) {
			log.info("帐户被锁");
		} catch (AuthenticationException e) {
			log.info("认证异常");
		} 

		// 认证 
		if (currentUser.isAuthenticated()) {
			log.info("认证通过...");
		} else {
			log.info("认证未通过...");
		}

		currentUser.logout();
	}
}

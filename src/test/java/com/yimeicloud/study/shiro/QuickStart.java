package com.yimeicloud.study.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuickStart {
	
	private static final transient Logger log = LoggerFactory.getLogger(QuickStart.class);
	
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
		// 获取SecurityManager工厂，使用ini配置文件初始化SecurityManager
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		// 获取SecurityManager实例，并绑定到SecurityUtils
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		
		// 获取当前用户
		Subject currentUser = SecurityUtils.getSubject();
		// 操作session
		Session session = currentUser.getSession();
		session.setAttribute("someKey", "aValue");
		String value = (String)session.getAttribute("someKey");
		if("aValue".equals(value)) {
			log.info("Retrieved the correct value![" + value + "]");
		}
		
		// 创建用户登录凭证
		UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
		
		// 登入
		try {
			currentUser.login(token);
		} catch(UnknownAccountException e) {
			log.info("用户名错误");
		} catch(IncorrectCredentialsException e) {
			log.info("密码错误");
		} catch(LockedAccountException e) {
			log.info("帐户被锁");
		} catch(AuthenticationException e) {
			log.info("认证异常");
		}
		
		// 认证
		if(currentUser.isAuthenticated()) {
			log.info("认证通过...");
		} else {
			log.info("认证未通过...");
		}
		
		// test role
		if(currentUser.hasRole("goodguy")) {
			log.info("May the Schwartz be with you!");
		} else {
			log.info("Hello, mere mortal.");
		}
		
		//test a typed permission (not instance-level)
        if (currentUser.isPermitted("lightsaber:weild")) {
            log.info("You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }

        //a (very powerful) Instance Level permission:
        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
            log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }
        
		// 退出
		currentUser.logout();
	}
}

package com.yimeicloud.study.shiro;

import java.util.Arrays;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;

public class ShiroJdbc {
	private static final transient Logger log = LoggerFactory.getLogger(ShiroJdbc.class);

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

		// 设置Realm
		DruidDataSource ds = new DruidDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/test");
		ds.setUsername("yimei");
		ds.setPassword("yymm1234");

		JdbcRealm jdbcRealm = new JdbcRealm();
		jdbcRealm.setDataSource(ds);
		jdbcRealm.setPermissionsLookupEnabled(true);
		jdbcRealm.setAuthenticationQuery("SELECT password FROM users WHERE user_name = ?");
		jdbcRealm.setUserRolesQuery(
				"SELECT role_name from roles where role_id = (select role_id from users where user_name = ?)");
		jdbcRealm.setPermissionsQuery(
				"SELECT permission_name FROM permissions WHERE permission_id IN (SELECT permission_id FROM roles_permissions WHERE role_id = (select role_id FROM roles WHERE role_name = ?))");

		DefaultSecurityManager securityManager = new DefaultSecurityManager();

		securityManager.setRealms(Arrays.asList((Realm) jdbcRealm));
		SecurityUtils.setSecurityManager(securityManager);

		// 获取当前用户
		Subject currentUser = SecurityUtils.getSubject();

		// 创建用户登录凭证
		UsernamePasswordToken token = new UsernamePasswordToken("aTom", "aTom");
		//UsernamePasswordToken token = new UsernamePasswordToken("uPitter", "uPitter");
		//UsernamePasswordToken token = new UsernamePasswordToken("gLily", "gLily");

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

			// 角色
			if (currentUser.hasRole("admin")) {
				log.info("管理员角色...");
			} else if (currentUser.hasRole("user")) {
				log.info("普通用户角色...");
			} else if (currentUser.hasRole("guest")) {
				log.info("访客角色...");
			} else {
				log.info("未知角色...");
			}

			// 权限
			if (currentUser.isPermitted("delete")) {
				log.info("有删除权限...");
			} else {
				log.info("无删除权限...");
			}
		} else {
			log.info("认证未通过...");
		}
		currentUser.logout();
	}
}

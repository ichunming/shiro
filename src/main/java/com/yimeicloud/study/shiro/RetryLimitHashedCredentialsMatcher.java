package com.yimeicloud.study.shiro;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

	private Ehcache passwordRetryCache;
	private static int DEFAULT_RETRY_COUNT = 5;
	
	public RetryLimitHashedCredentialsMatcher() {
		CacheManager manager = CacheManager.newInstance(CacheManager.class.getClassLoader().getResource("ehcache.xml"));
		passwordRetryCache = manager.getCache("passwordRetryCache");
	}
	
	@Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        // username
		String username = (String)token.getPrincipal();
        
        // cache
		Element element = passwordRetryCache.get(username);
		
		if(null == element) {
			element = new Element(username, new AtomicInteger(0));
			passwordRetryCache.put(element);
		}
		
		// retry count
		AtomicInteger retry = (AtomicInteger)element.getObjectValue();
		
		// retry count check
		if(retry.incrementAndGet() > DEFAULT_RETRY_COUNT) {
			// throw exception
			throw new ExcessiveAttemptsException();
		}
		
		boolean matches = super.doCredentialsMatch(token, info);
		
		if(matches) {
			// clear retry count
			passwordRetryCache.remove(username);
		}
		
        return matches;
    }
}

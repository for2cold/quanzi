package com.jasme.quanzi.api.userinfo.service;

import javax.annotation.PostConstruct;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jasme.quanzi.api.userinfo.exception.UserPasswordNotMatchException;
import com.jasme.quanzi.api.userinfo.exception.UserPasswordRetryLimitExceedException;
import com.jasme.quanzi.core.component.user.entity.User;
import com.jasme.swiiket.common.util.security.Md5Utils;

@Service
public class PasswordService {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(PasswordService.class);
	
	private static final String LOGIN_RECORD_CACHE = "loginRecordCache";
	
	@Autowired
	private CacheManager cacheManager;
	
	private Cache loginRecordCache;
	
	@Value(value = "${user.password.maxRetryCount}")
    private int maxRetryCount = 10;
	
	@PostConstruct
	public void init() {
		loginRecordCache = cacheManager.getCache(LOGIN_RECORD_CACHE);
	}
	
	/**
	 * 校验密码
	 * 
	 * @param user
	 * @param password
	 * @param autoLogin
	 */
	public void validate(User user, String password, boolean autoLogin) {
		
		String username = user.getUsername();

        int retryCount = 0;

        Element cacheElement = loginRecordCache.get(username);
        if (cacheElement != null) {
            retryCount = (Integer) cacheElement.getObjectValue();
            if (retryCount >= maxRetryCount) {
            	LOGGER.info("password error, retry limit exceed! password: {" + password + "}, max retry count {" + maxRetryCount + "}");
                throw new UserPasswordRetryLimitExceedException(maxRetryCount);
            }
        }
        
        if (!matches(user, password, autoLogin)) {
        	
        	loginRecordCache.put(new Element(username, ++retryCount));
        	throw new UserPasswordNotMatchException();
        } else {
        	clearLoginRecordCache(username);
        }
	}
	
	public boolean matches(User user, String newPassword, boolean autoLogin) {
    	if (autoLogin) {
    		return user.getPassword().equals(newPassword);
    	}
    	String password = encryptPassword(newPassword, user.getSalt());
        return user.getPassword().equals(password);
    }

    public void clearLoginRecordCache(String username) {
        loginRecordCache.remove(username);
    }

    public String encryptPassword(String password, String salt) {
    	return Md5Utils.hash(password + salt);
    }
}

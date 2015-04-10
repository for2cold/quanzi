package org.apache.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.jasme.quanzi.api.userinfo.exception.UserBlockedException;
import com.jasme.quanzi.api.userinfo.exception.UserException;
import com.jasme.quanzi.api.userinfo.exception.UserNotExistsException;
import com.jasme.quanzi.api.userinfo.exception.UserPasswordRetryLimitExceedException;
import com.jasme.quanzi.api.userinfo.service.UserService;
import com.jasme.quanzi.core.component.user.entity.User;
import com.jasme.quanzi.core.oauth2.entity.OAuthAuthenticationToken;
import com.jasme.quanzi.core.oauth2.entity.OAuthToken;
import com.jasme.swiiket.common.repository.support.SimpleBaseRepositoryFactoryBean;

/**
 * 
 * <p>Author: Beiming
 * <p>Date: 2014年7月24日 下午6:11:19
 * <p>Version: 1.0
 */
public class OAuth2Realm extends AuthorizingRealm {

	private static final Logger LOG = LoggerFactory.getLogger("error");
	
	@Autowired
    private UserService userService;
	
	@Autowired
	public OAuth2Realm(ApplicationContext ctx) {
		// TODO Auto-generated constructor stub
		super();
        ctx.getBeansOfType(SimpleBaseRepositoryFactoryBean.class);
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		
		OAuthAuthenticationToken oauthToken = (OAuthAuthenticationToken) token;
		String username = oauthToken.getUsername();
		String password = null;
		if (oauthToken.getPassword() != null) {
			password = new String(oauthToken.getPassword());
		}
		
		User user = null;
		
		try {
			user = userService.login(username, password, oauthToken.isAutoLogin());
		} catch (UserNotExistsException e) {
			
            throw new UnknownAccountException(e.getMessage(), e);
        } catch (UserPasswordRetryLimitExceedException e) {
        	
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        } catch (UserBlockedException e) {
        	
            throw new LockedAccountException(e.getMessage(), e);
        } catch (Exception e) {
        	
            LOG.error("login error", e);
            throw new AuthenticationException(new UserException("user.unknown.error"));
        }
		
		OAuthToken tokenInfo = new OAuthToken(oauthToken.getAccessToken(), user);
		
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(tokenInfo, password.toCharArray(), getName());
		
        return info;
	}

}

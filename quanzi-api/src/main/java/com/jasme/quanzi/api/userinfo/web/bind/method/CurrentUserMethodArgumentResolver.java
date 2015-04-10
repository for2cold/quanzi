package com.jasme.quanzi.api.userinfo.web.bind.method;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.jasme.quanzi.api.userinfo.web.bind.annotation.CurrentUser;
import com.jasme.quanzi.core.oauth2.entity.OAuthToken;

/**
 * 用于绑定@CurrentUser的方法参数解析器
 * <p>Author: Beiming
 * <p>Date: 2014-7-2 下午2:09:00
 * <p>Version: 1.0
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    public CurrentUserMethodArgumentResolver() {
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
    	
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    	
    	
    	Subject subject = SecurityUtils.getSubject();
		if (!subject.isAuthenticated()) {
			throw new UnauthenticatedException();
		}
		OAuthToken token = subject.getPrincipals().oneByType(OAuthToken.class);
    	if (token != null) {
    		return token.getUser();
    	}
    	return null;
    }
}

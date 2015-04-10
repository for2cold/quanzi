package org.apache.shiro.web.filter.user;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.jasme.quanzi.api.userinfo.service.UserService;
import com.jasme.quanzi.core.component.user.entity.User;
import com.jasme.quanzi.core.component.user.enums.UserStatus;
import com.jasme.quanzi.core.oauth2.entity.OAuthToken;
import com.jasme.swiiket.common.plugin.http.json.response.ResponseEntity;
import com.jasme.swiiket.common.plugin.http.json.response.ResponseStatusCode;
import com.jasme.swiiket.common.util.MessageUtils;

/**
 * 验证用户过滤器
 * 1、用户是否删除
 * 2、用户是否锁定
 * 3、用户是否未完善资料
 * 4、用户是否未审核通过
 * <p>User: WeiGHuang
 * <p>Date: 2014年8月14日 下午3:06:57
 * <p>Version: 1.0
 */
public class AccessUserFilter extends AccessControlFilter {

	private UserService userService;
	
	private Map<String, Object> error = Maps.newHashMap();
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		// TODO Auto-generated method stub
		// 验证用户状态
		Subject subject = getSubject(request, response);
		if (subject.isAuthenticated()) {
			
			OAuthToken token = subject.getPrincipals().oneByType(OAuthToken.class);
			
			User user = userService.findOne(token.getUser().getId());
			
			UserStatus status = user.getStatus();
			
			error.put("status", status);
			
			String errorMsg = null;
			
			// 封禁状态
			if (UserStatus.BLOCKED == status) { 
				
				errorMsg = MessageUtils.message("user.blocked");
				error.put("errorMsg", errorMsg);
				return false;
			}
			
			return true;
			
		}
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		// 验证不通过响应
		
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		PrintWriter out = httpResponse.getWriter();
		
		ResponseEntity responseEntity = new ResponseEntity();
		responseEntity.setCode(ResponseStatusCode.CHECK_USER_STATUS.getCode());
		responseEntity.setError(error);
		
		out.print(JSON.toJSONString(responseEntity));
		out.flush();
		out.close();
		
		return false;
	}

}

package com.jasme.quanzi.api.oauth2.constants;

public interface OAuthConstants {

	String INVALID_CLIENT_DESCRIPTION = "客户端认证失败，错误的client_id或client_secret";
	
	String INVALID_CLIENT_GRANT_TYPE_DESCRIPTION = "客户端认证失败，错误的grant_type";
	
	String INVALID_TOKEN_DESCRIPTION = "客户端验证失败，access_token无效";
	
	String INVALID_USERNAME_OR_PASSWORD = "登录账号或登录密码不正确";
	
	String INVALID_PASSWORD_LIMIT = "密码输入错误次数太多，帐户锁定10分钟";
	
	String OAUTH2_ERROR_DESCRIPTION = "客户端认证失败，认证中心异常";
	
	String OAUTH2_ERROR = "server_error";
	
	String OAUTH2_RESOURCE_SERVER_REALM = "oauth2-realm";
}

package springDemo.oauth.service;

import springDemo.oauth.vo.OauthClick;

public interface OauthService {
	public OauthClick findByClientId(String ClientId);
	
	public OauthClick findByClientSecret(String ClientSecret);
	
	public boolean checkClientId(String ClientId);
	
	public boolean checkClientSecret(String ClientSecret);
}

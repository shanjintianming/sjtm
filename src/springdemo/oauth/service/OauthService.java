package springdemo.oauth.service;

import java.util.List;

import springdemo.oauth.vo.OauthClient;
import springdemo.oauth.vo.OauthClientAuthorize;

public interface OauthService {
	public OauthClient findByClientId(String clientId);
	
	public OauthClient findByClientSecret(String clientSecret);
	
	public boolean checkClientId(String clientId);
	
	public boolean checkClientSecret(String clientSecret);
	
	public List<OauthClientAuthorize> findAuthorizeInfoByUserAndClient(OauthClientAuthorize info);
	
	public int BatchInsetAuthorizeInfo(List<OauthClientAuthorize> infoLst);
}

package springdemo.user.service;

import springdemo.user.vo.OauthUser;

public interface UserService {
	public OauthUser selectByUserName(String userName);
}

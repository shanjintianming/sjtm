package springDemo.user.service;

import springDemo.user.vo.OauthUser;

public interface UserService {
	public OauthUser selectByUserName(String userName);
}

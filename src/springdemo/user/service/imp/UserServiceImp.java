package springdemo.user.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springdemo.user.dao.OauthUserMapper;
import springdemo.user.service.UserService;
import springdemo.user.vo.OauthUser;
import springdemo.user.vo.OauthUserExample;

@Service(value="userService")
public class UserServiceImp implements UserService {

	@Autowired
	private OauthUserMapper userMapper;
	
	@Override
	public OauthUser selectByUserName(String userName) {
		
		OauthUser oauthUser = new OauthUser();
		
		OauthUserExample oauthUserExample = new OauthUserExample();
		oauthUserExample.createCriteria().andUserNameEqualTo(userName);
		List<OauthUser> oauthUserLst = userMapper.selectByExample(oauthUserExample);
		
		if (oauthUserLst != null && oauthUserLst.size() > 0) {
			oauthUser = oauthUserLst.get(0);
		}
		
		return oauthUser;
	}

}

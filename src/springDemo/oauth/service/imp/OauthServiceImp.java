package springDemo.oauth.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springDemo.oauth.dao.OauthClickMapper;
import springDemo.oauth.service.OauthService;
import springDemo.oauth.vo.OauthClick;
import springDemo.oauth.vo.OauthClickExample;

@Service
public class OauthServiceImp implements OauthService {

	@Autowired
	private OauthClickMapper oauthClickMapper;
	
	@Override
	public OauthClick findByClientId(String clientId) {
		
		OauthClickExample example = new OauthClickExample();
		example.createCriteria().andClickIdEqualTo(clientId);
		List<OauthClick> oauthClickLst = oauthClickMapper.selectByExample(example);
		
		if (oauthClickLst != null && oauthClickLst.size() > 0) {
			return oauthClickLst.get(0);
		}
		
		return null;
	}

	@Override
	public boolean checkClientId(String clientId) {
		
		OauthClick oauthClick = findByClientId(clientId);

		return oauthClick != null;
	}

	@Override
	public OauthClick findByClientSecret(String ClientSecret) {
		OauthClickExample example = new OauthClickExample();
		example.createCriteria().andClientSecretEqualTo(ClientSecret);
		List<OauthClick> oauthClickLst = oauthClickMapper.selectByExample(example);
		
		if (oauthClickLst != null && oauthClickLst.size() > 0) {
			return oauthClickLst.get(0);
		}
		
		return null;
	}

	@Override
	public boolean checkClientSecret(String ClientSecret) {
		OauthClick oauthClick = findByClientSecret(ClientSecret);

		return oauthClick != null;
	}
}

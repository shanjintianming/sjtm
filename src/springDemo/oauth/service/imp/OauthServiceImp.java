package springDemo.oauth.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springDemo.oauth.dao.OauthClientAuthorizeMapper;
import springDemo.oauth.dao.OauthClientMapper;
import springDemo.oauth.service.OauthService;
import springDemo.oauth.vo.OauthClientAuthorize;
import springDemo.oauth.vo.OauthClientAuthorizeExample;
import springDemo.oauth.vo.OauthClient;
import springDemo.oauth.vo.OauthClientExample;

@Service
public class OauthServiceImp implements OauthService {

	@Autowired
	private OauthClientMapper oauthClientMapper;
	
	@Autowired
	private OauthClientAuthorizeMapper oauthClientAuthorizeMapper;
	
	@Override
	public OauthClient findByClientId(String clientId) {
		
		OauthClientExample example = new OauthClientExample();
		example.createCriteria().andClientIdEqualTo(clientId);
		List<OauthClient> oauthClientLst = oauthClientMapper.selectByExample(example);
		
		if (oauthClientLst != null && oauthClientLst.size() > 0) {
			return oauthClientLst.get(0);
		}
		
		return null;
	}

	@Override
	public boolean checkClientId(String clientId) {
		
		OauthClient oauthClient = findByClientId(clientId);

		return oauthClient != null;
	}

	@Override
	public OauthClient findByClientSecret(String ClientSecret) {
		OauthClientExample example = new OauthClientExample();
		example.createCriteria().andClientSecretEqualTo(ClientSecret);
		List<OauthClient> oauthClientLst = oauthClientMapper.selectByExample(example);
		
		if (oauthClientLst != null && oauthClientLst.size() > 0) {
			return oauthClientLst.get(0);
		}
		
		return null;
	}

	@Override
	public boolean checkClientSecret(String ClientSecret) {
		OauthClient oauthClient = findByClientSecret(ClientSecret);

		return oauthClient != null;
	}

	@Override
	public List<OauthClientAuthorize> findAuthorizeInfoByUserAndClient(OauthClientAuthorize info) {
		
		OauthClientAuthorizeExample example = new OauthClientAuthorizeExample();
		OauthClientAuthorizeExample.Criteria criteria = example.createCriteria();
		criteria.andClientIdEqualTo(info.getClientId());
		criteria.andUserIdEqualTo(info.getUserId());	
		List<OauthClientAuthorize> OauthClientAuthorizeLst = oauthClientAuthorizeMapper.selectByExample(example);
		
		if (OauthClientAuthorizeLst != null && OauthClientAuthorizeLst.size() > 0) {
			return OauthClientAuthorizeLst;
		}
		
		return null;
	}

	@Transactional
	@Override
	public int BatchInsetAuthorizeInfo(List<OauthClientAuthorize> infoLst) {
		return oauthClientAuthorizeMapper.batchInsert(infoLst);
	}
}

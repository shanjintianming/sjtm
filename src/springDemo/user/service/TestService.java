package springDemo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import springDemo.user.dao.TestDao;

@Service()
public class TestService {
	
	@Autowired
	private TestDao testDao;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public String getViewName() {
		
		testDao.selectCount();
		
		return "test";
	}
}

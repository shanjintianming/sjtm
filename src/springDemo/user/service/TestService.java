package springDemo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import springDemo.user.dao.TestDao;
import springDemo.user.entry.UserEntry;

@Service()
public class TestService {
	
	@Autowired
	private TestDao testDao;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public String getViewName(UserEntry entry) {
		testDao.selectCount();
		return "test";
	}
}

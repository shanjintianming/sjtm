package springDemo.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springDemo.user.entry.UserEntry;
import springDemo.user.service.TestService;

@Controller
@RequestMapping("index")
public class TestController {
	
	@Autowired
	private TestService testService;
	
	@RequestMapping("index")
	public String toView() {
		String viewName = "";
		try {
			UserEntry userEntry = new UserEntry();
			userEntry.setId("1");
			viewName = testService.getViewName(userEntry);		
		} catch (Exception e) {
			
		} 
		return viewName;
	}
}

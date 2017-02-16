package springDemo.core.beetl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.ext.web.WebRenderExt;

public class GlobalExt implements WebRenderExt {

	@Override
	public void modify(Template paramTemplate, GroupTemplate paramGroupTemplate,
			HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) {

		String path = paramHttpServletRequest.getContextPath();
		String basePath = paramHttpServletRequest.getScheme() + "://"
				+ paramHttpServletRequest.getServerName() + ":" 
				+ paramHttpServletRequest.getServerPort()
				+ path + "/";
		
		paramTemplate.binding("basePath", basePath);
	}


}
